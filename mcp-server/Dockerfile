FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app
COPY gradle gradle
COPY gradlew .
COPY settings.gradle.kts .
COPY build.gradle.kts .
COPY gradle.properties .
COPY api-server api-server
COPY mcp-server mcp-server
COPY shared shared
RUN ./gradlew :mcp-server:installDist --no-daemon

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/mcp-server/build/install/mcp-server .
CMD ["bin/mcp-server"]
