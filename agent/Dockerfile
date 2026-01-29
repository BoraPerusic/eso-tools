# Use a Python image with uv pre-installed
FROM ghcr.io/astral-sh/uv:python3.11-bookworm-slim AS builder

# Set working directory
WORKDIR /app

# Enable bytecode compilation
ENV UV_COMPILE_BYTECODE=1

# Copy dependency files
COPY pyproject.toml uv.lock ./

# Install dependencies (without installing the project itself yet)
# --no-dev: Production dependencies only
# --frozen: Use lockfile strictly
# --no-install-project: We'll copy source later
RUN uv sync --frozen --no-dev --no-install-project

# Copy the rest of the application
COPY . .

# Install the project
RUN uv sync --frozen --no-dev

# ------------------------------------------------------------------------------
# Runtime stage
# ------------------------------------------------------------------------------
FROM python:3.11-slim-bookworm

WORKDIR /app

# Copy the environment from builder
COPY --from=builder /app/.venv /app/.venv

# Copy application code
COPY src /app/src

# Add venv to PATH
ENV PATH="/app/.venv/bin:$PATH"

# Run the application
CMD ["uvicorn", "src.main:app", "--host", "0.0.0.0", "--port", "8000"]
