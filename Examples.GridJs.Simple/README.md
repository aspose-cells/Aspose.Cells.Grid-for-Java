# Aspose.Cells GridJS Simple for Java — framework examples

[Product Page](https://products.aspose.com/cells/java) | [Documentation](https://docs.aspose.com/cells/java/aspose-cells-gridjs/) | [API Reference](https://reference.aspose.com/cells/java/com.aspose.gridjs/) | [Support](https://forum.aspose.com/c/cells)

This Spring Boot Simple example matches the .NET Simple client matrix: four npm clients plus one direct browser `<script>` / CDN example.

| Client | Development URL | Production URL | npm import |
|---|---|---|---|
| React | `http://127.0.0.1:5183` | `/demos/react/` | `gridjs-spreadsheet/react` |
| Vue 3 | `http://127.0.0.1:5174` | `/demos/vue/` | `gridjs-spreadsheet/vue` |
| Angular 22 | `http://127.0.0.1:4200` | `/demos/angular/` | `gridjs-spreadsheet/angular` |
| JavaScript | `http://127.0.0.1:5175` | `/demos/npm/` | `gridjs-spreadsheet` |

All four npm clients load `src/main/resources/files/Sample.xlsx`. Workbook loading and the direct JavaScript example use `/GridJs/*`; the published React, Vue, and Angular adapters use their .NET-compatible `/GridJs2/*` defaults for updates, images, downloads, and OLE objects. The Spring Boot controller exposes both route prefixes. The direct `<script>` example is available at `/legacy.html`.

## Two JavaScript integration modes

### npm import

The framework-free npm example is in `ClientApp/vanilla-gridjs-npm`:

```javascript
import Spreadsheet from "gridjs-spreadsheet";
import JSZip from "jszip";
import "gridjs-spreadsheet/xspreadsheet.css";

window.JSZip = JSZip;
const spreadsheet = new Spreadsheet(document.getElementById("spreadsheet"), {
  updateMode: "server",
  updateUrl: "/GridJs/UpdateCell",
  mode: "edit"
});
```

Run it together with Spring Boot:

```bash
npm run dev:npm
```

Open <http://127.0.0.1:5175/>.

### Direct `<script>` / CDN import

The original Simple page in `src/main/resources/static/legacy.html` uses browser scripts and does not require an npm build:

```html
<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.6.0/jszip.min.js"></script>
<link rel="stylesheet" href="https://unpkg.com/gridjs-spreadsheet/xspreadsheet.css">
<script src="https://unpkg.com/gridjs-spreadsheet/xspreadsheet.js"></script>
```

After Spring Boot starts, open <http://127.0.0.1:8080/legacy.html>.

## Requirements

For local development:

- Java 17 or newer
- Node.js 26 (recommended), Node.js 24.15.0+, or Node.js 22.22.3+
- npm

The Maven wrapper is included, so a separate Maven installation is not required.

For Docker, only Docker Desktop or Docker Engine with Compose v2 is required.

## One-command local start

From `Examples.GridJs.Simple`, run:

```bash
npm start
```

The first run:

1. Installs all npm workspace dependencies.
2. Builds React, Vue, Angular, and JavaScript npm clients into Spring Boot static resources.
3. Downloads the Maven dependencies.
4. Starts Spring Boot on port `8080`.

Open:

<http://127.0.0.1:8080/>

The launcher links to all four built npm examples. Press `Ctrl+C` to stop the backend.

The original Simple page remains available at:

<http://127.0.0.1:8080/legacy.html>

## Development servers

Start Spring Boot and all four npm development servers:

```bash
npm run dev
```

Open a framework directly using the development URLs in the table above. Vite and Angular proxy both `/GridJs` and `/GridJs2` requests to Spring Boot on port `8080`.

Run only one framework with the backend:

```bash
npm run dev:react
npm run dev:vue
npm run dev:angular
npm run dev:npm
```

If dependencies have not been installed yet, run `npm run setup` first.

## Build and backend tests

Build all four npm clients:

```bash
npm run build
```

The generated files are written to:

```text
src/main/resources/static/demos/
```

Build and compile the Spring Boot JAR:

```bash
npm run build:backend
```

Run the Spring context test:

```bash
npm run test:backend
```

## Configuration

The defaults in `src/main/resources/application.properties` work locally and in Docker:

| Environment variable | Default | Purpose |
|---|---|---|
| `GRIDJS_WORKBOOK_DIR` | `./src/main/resources/files` | Workbook directory |
| `GRIDJS_CACHE_DIR` | `./grid_cache` | GridJS cache |

Example:

```bash
GRIDJS_WORKBOOK_DIR=/data/workbooks npm start
```

## Docker

Build and start the complete Java backend and all four npm frontend demos:

```bash
docker compose up --build
```

Open:

<http://localhost:8080/>

Run in the background:

```bash
docker compose up --build --detach
docker compose ps
```

View logs:

```bash
docker compose logs --follow gridjs-java-simple
```

Stop and remove the container:

```bash
docker compose down
```

The GridJS cache is stored in a named volume and survives `docker compose down`. Remove it intentionally with:

```bash
docker compose down --volumes
```

Use another host port:

```bash
GRIDJS_JAVA_PORT=8090 docker compose up --build --detach
```

Then open <http://localhost:8090/>.

The Compose port binds to `127.0.0.1` by default. Add authentication and appropriate network controls before exposing GridJS editing endpoints publicly.

## Docker architecture

The Dockerfile uses three stages:

1. Node.js 24 builds React, Vue, Angular, and the framework-free JavaScript npm client.
2. Maven with Java 17 packages Spring Boot and the built static clients.
3. Eclipse Temurin Java 17 JRE runs the application as a non-root user.

The container health endpoint is:

<http://localhost:8080/health>

## Troubleshooting

### Docker Hub token or TLS timeout

Errors such as `failed to fetch anonymous token`, `TLS handshake timeout`, or `EOF` happen before the project build starts and normally indicate Docker Hub connectivity. Retry after the network or proxy is stable:

```bash
docker pull node:24-alpine
docker pull maven:3.9.11-eclipse-temurin-17
docker pull eclipse-temurin:17-jre-jammy
docker compose up --build
```

### Framework page returns 404

Run `npm run build` before starting Spring Boot locally, or rebuild the Docker image:

```bash
docker compose up --build
```

### Workbook cannot be loaded

Confirm that `src/main/resources/files/Sample.xlsx` exists and that the application can write to `grid_cache`.

### Port 8080 is already in use

Use `GRIDJS_JAVA_PORT` with Docker, or stop the other local process before running Spring Boot.

### License

The demo runs in evaluation mode unless you add your Aspose.Cells license-loading code during application startup.
