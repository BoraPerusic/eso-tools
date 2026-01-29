from fastapi import APIRouter, HTTPException
from .models import QueryRequest, QueryResponse
from ..core.agent import run_agent

router = APIRouter()

@router.post("/query", response_model=QueryResponse)
async def query_agent(request: QueryRequest):
    try:
        answer = await run_agent(request.question)
        return QueryResponse(answer=answer)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
