# Fuzzy Matcher Service Tasks

- [x] Read and analyze `requirements-fuzzy-matcher. md`
- [x] Create high-level stage plan (`fuzzy-matcher-plan.md` in root)
- [x] Create detailed implementation plan (Artifact)
- [ ] Review plan with user

## Implementation Steps
- [ ] Add `Customers` table to `init.sql`
- [ ] **API Service** (`api-fuzzy`)
    - [ ] Create `Dockerfile` & `pyproject.toml`
    - [ ] Implement `src/main.py` (FastAPI + Logic)
- [ ] **MCP Service** (`mcp-fuzzy`)
    - [ ] Create `Dockerfile` & `pyproject.toml`
    - [ ] Implement `src/main.py` (MCP Wrapper)
- [ ] Update `docker-compose.yml` (Add both services)
- [ ] Verify implementation
