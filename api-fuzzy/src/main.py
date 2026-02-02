from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from sqlalchemy import create_engine, text
from rapidfuzz import process, fuzz
import os
import logging

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = FastAPI(title="Fuzzy Matcher API")

# Database Configuration
DB_SERVER = os.getenv("DB_SERVER", "sqlserver")
DB_USER = os.getenv("DB_USER", "sa")
DB_PASSWORD = os.getenv("DB_PASSWORD", "Password123!")
DB_NAME = os.getenv("DB_NAME", "ESODB")

# Connection String
DATABASE_URL = f"mssql+pymssql://{DB_USER}:{DB_PASSWORD}@{DB_SERVER}/{DB_NAME}"

engine = create_engine(DATABASE_URL)

class SearchRequest(BaseModel):
    entity: str
    text: str

class SearchResult(BaseModel):
    id: str
    name: str
    confidence: float

@app.get("/")
def read_root():
    return {"status": "healthy"}

@app.post("/search", response_model=list[SearchResult])
def search(request: SearchRequest):
    entity = request.entity.lower()
    search_text = request.text
    
    if entity not in ["product", "customer"]:
        raise HTTPException(status_code=400, detail="Entity must be either 'product' or 'customer'")

    table_name = "Products" if entity == "product" else "Customers"
    id_col = "ProductId" if entity == "product" else "Id"
    
    try:
        with engine.connect() as connection:
            # Fetch all names and IDs. 
            # In a real large-scale scenario, we might want to cache this or use a more specialized search engine.
            query = text(f"SELECT {id_col}, Name FROM {table_name}")
            result = connection.execute(query)
            data = result.fetchall()
            
            # Prepare data for rapidfuzz
            # data is list of (id, name) tuples
            choices = {row[1]: row[0] for row in data} # name -> id map
            names = list(choices.keys())
            
            if not names:
                return []

            # Perform fuzzy matching
            # process.extract returns a list of tuples: (match, score, index/key)
            matches = process.extract(search_text, names, scorer=fuzz.WRatio, limit=10)
            
            results = []
            for name, score, _ in matches:
                # rapidfuzz returns score 0-100, we prefer 0.0-1.0 if requested, but requirements say 0.92
                # Assuming requirements mean 0.0-1.0, so divide by 100
                normalized_score = round(score / 100.0, 2)
                
                # Filter out very low quality matches if needed, but for now return top 10
                results.append(SearchResult(
                    id=choices[name], 
                    name=name, 
                    confidence=normalized_score
                ))
            
            return results

    except Exception as e:
        logger.error(f"Error executing search: {e}")
        raise HTTPException(status_code=500, detail=str(e))
