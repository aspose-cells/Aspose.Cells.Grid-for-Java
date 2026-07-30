FROM node:24-alpine AS frontend-build
WORKDIR /src

COPY package.json package-lock.json ./
COPY ClientApp/react-gridjs/package.json ClientApp/react-gridjs/package.json
COPY ClientApp/vue-gridjs/package.json ClientApp/vue-gridjs/package.json
COPY ClientApp/angular-gridjs/package.json ClientApp/angular-gridjs/package.json
COPY ClientApp/vanilla-gridjs-npm/package.json ClientApp/vanilla-gridjs-npm/package.json
RUN npm ci

COPY ClientApp ./ClientApp
RUN npm run build

FROM maven:3.9.11-eclipse-temurin-17 AS backend-build
WORKDIR /src

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY src ./src
COPY --from=frontend-build /src/src/main/resources/static/demos ./src/main/resources/static/demos
RUN mvn package

FROM eclipse-temurin:17-jre-jammy AS runtime
WORKDIR /app

RUN apt-get update \
    && apt-get install --yes --no-install-recommends \
        ca-certificates \
        curl \
        fonts-dejavu-core \
        fonts-liberation \
        fontconfig \
    && rm -rf /var/lib/apt/lists/*

RUN groupadd --gid 1654 gridjs \
    && useradd --uid 1654 --gid gridjs --create-home gridjs \
    && mkdir -p /app/files /app/grid_cache \
    && chown -R gridjs:gridjs /app

COPY --from=backend-build --chown=gridjs:gridjs /src/target/*.jar /app/app.jar
COPY --chown=gridjs:gridjs src/main/resources/files/*.xlsx /app/files/

ENV GRIDJS_WORKBOOK_DIR=/app/files \
    GRIDJS_CACHE_DIR=/app/grid_cache

EXPOSE 8080
VOLUME ["/app/grid_cache"]

USER gridjs

HEALTHCHECK --interval=15s --timeout=5s --start-period=40s --retries=3 \
    CMD curl --fail --silent --show-error http://localhost:8080/health || exit 1

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]
