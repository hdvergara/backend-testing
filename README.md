<div align="center">

# Automatización de API — Pet Store

**Suite de pruebas de backend** contra la API pública [Swagger Petstore](https://petstore.swagger.io/), con reportes **Allure** listos para CI/CD.

[![Java](https://img.shields.io/badge/Java-22-437291?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![JUnit 5](https://img.shields.io/badge/JUnit-5.11-25A162?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/junit5/)
[![REST Assured](https://img.shields.io/badge/REST_Assured-5.5-4CAF50?style=for-the-badge)](https://rest-assured.io/)
[![Allure](https://img.shields.io/badge/Allure-2.29-EA7FFF?style=for-the-badge)](https://allurereport.org/)

[![CI](https://img.shields.io/badge/CI-GitHub_Actions-2088FF?style=flat&logo=githubactions&logoColor=white)](.github/workflows/maven.yml)
[![API](https://img.shields.io/badge/API-Petstore-85EA2D?style=flat&logo=swagger&logoColor=black)](https://petstore.swagger.io/)

[![Java CI](https://github.com/hdvergara/backend-testing/actions/workflows/maven.yml/badge.svg)](https://github.com/hdvergara/backend-testing/actions/workflows/maven.yml)
[![Allure report (Pages)](https://img.shields.io/badge/Informe_Allure-GitHub_Pages-6550FF?style=flat&logo=github)](https://hdvergara.github.io/backend-testing/)

</div>

---

## ¿Qué hace este proyecto?

Automatiza un **flujo ordenado** sobre la API de mascotas:

1. **Crear** una mascota (POST).
2. **Consultarla** por id (GET).
3. **Actualizarla** y volver a leerla (PUT + GET).

Los tests están en **`com.petstore.automation.test.ApiTest`**, usan datos generados (DataFaker) y dejan trazas en **Allure** (pasos, severidad, Epic/Feature/Story y adjuntos HTTP gracias al filtro de REST Assured).

**Convención de paquetes:** `com.petstore.automation` — modelo y configuración reutilizable en **`src/main`**, cliente HTTP, datos de prueba, assertions y tests en **`src/test`**.

---

## Tabla de contenidos

- [Stack tecnológico](#stack-tecnológico)
- [Estructura del repositorio](#estructura-del-repositorio)
- [Requisitos previos](#requisitos-previos)
- [Cómo ejecutar los tests](#cómo-ejecutar-los-tests)
- [Configuración](#configuración)
- [Reportes Allure](#reportes-allure)
- [Integración continua (GitHub Actions)](#integración-continua-github-actions)

---

## Stack tecnológico

| Componente | Uso |
|------------|-----|
| **Java 22** | Lenguaje y runtime |
| **Maven** | Dependencias y ciclo de build |
| **JUnit 5** | Motor de tests (`@Test`, `@Order`, `@TestInstance`) |
| **REST Assured** | Cliente HTTP y aserciones sobre respuestas |
| **Jackson** | Serialización JSON de los cuerpos (POJOs / Lombok) |
| **AssertJ** | Aserciones fluidas y soft assertions |
| **Lombok** | POJOs del dominio (`BodyPet`, `Category`, `Tag`) |
| **DataFaker** | Datos de prueba aleatorios |
| **Allure 2** | Reporte HTML, steps, metadatos y adjuntos |
| **SLF4J Simple** | Logging mínimo en ejecución de tests |

Versiones concretas: revisa el archivo [`pom.xml`](pom.xml) (propiedades `junit.version`, `rest-assured.version`, `allure.version`, etc.).

---

## Estructura del repositorio

```
src/main/java/com/petstore/automation/
├── config/              # ConfigProperties — lectura de propiedades
└── model/               # DTOs Pet (BodyPet, Category, Tag) + CreateBody

src/main/resources/
└── configuration.properties   # API.URL (base del recurso /pet)

src/test/java/com/petstore/automation/
├── api/                 # ApiMethods — GET/POST/PUT + filtro Allure
├── assertions/        # AssertionManager (soft assertions)
├── data/              # DataGenerator
├── reporting/allure/    # AllureLabels (Epic / Feature / Story)
└── test/              # ApiTest — casos ordenados

src/test/resources/
├── allure.properties
├── environment.properties   # Metadatos en el reporte Allure
├── categories.json          # Categorías de fallos
└── simplelogger.properties  # Nivel de log en tests
```

**Coordenadas Maven:** `com.petstore.automation:backend-testing` (ver [`pom.xml`](pom.xml)).

---

## Requisitos previos

- **JDK 22** instalado (`java -version`).
- **Maven 3.9+** (`mvn -v`).
- Conexión a Internet (tests contra `petstore.swagger.io`; la primera vez `allure:report` / `allure:serve` pueden descargar el **Allure Commandline**).

---

## Cómo ejecutar los tests

### Opción A — IntelliJ / IDE

1. Abre el proyecto como proyecto **Maven**.
2. Navega a `src/test/java/com/petstore/automation/test/ApiTest.java`.
3. Ejecuta la clase o un método con el icono de **JUnit**.

### Opción B — Terminal (recomendado para CI local)

En la raíz del proyecto:

```bash
mvn clean test
```

- **Salida:** resultados en consola y, para Allure, datos en `target/allure-results/`.
- **Orden:** los métodos usan `@Order(1..3)`; el estado (`id` / nombre de mascota) se mantiene en la misma clase de test (`@TestInstance(PER_CLASS)`).

---

## Configuración

| Archivo | Propósito |
|---------|-----------|
| [`src/main/resources/configuration.properties`](src/main/resources/configuration.properties) | `API.URL` — URL base del recurso **pet** (sin barra final problemática; el código concatena `/{id}` en GET). |
| [`src/test/resources/environment.properties`](src/test/resources/environment.properties) | Texto que verás en el reporte Allure (entorno, URL lógica, etc.). |
| [`src/test/resources/categories.json`](src/test/resources/categories.json) | Agrupación de estados en Allure (fallos de producto vs automatización). |

Tras `mvn test`, Maven copia `environment.properties` y `categories.json` a `target/allure-results/` para que el generador de informes los incluya.

---

## Reportes Allure

### 1) Generar el HTML (archivos estáticos)

```bash
mvn clean test allure:report
```

- **Salida:** sitio generado en **`target/site/allure-report/`** (entre otros, `index.html`).

### 2) Ver el informe correctamente (importante)

El informe Allure es una **aplicación web**. Abrir `index.html` con doble clic (`file://`) suele dejar la pantalla **en blanco o cargando sin fin** por restricciones del navegador.

**Recomendado — servidor embebido con Maven:**

```bash
mvn clean test
mvn allure:serve
```

Se inicia un servidor local (Jetty) y verás una URL del tipo `http://127.0.0.1:puerto/`. Ábrela en el navegador. Para detener el servidor: **Ctrl+C** en la terminal.

**Alternativa — servidor HTTP simple** (si ya ejecutaste `mvn allure:report`):

```bash
cd target/site/allure-report
python -m http.server 8080
```

Luego abre **http://localhost:8080** en el navegador.

### Qué verás en el reporte

- Casos agrupados por **Epic / Feature / Story**.
- **Pasos** (`Allure.step`), **severidad**, **descripción** y **enlace** al Swagger.
- **Peticiones y respuestas HTTP** como adjuntos (filtro `AllureRestAssured`).

---

## Integración continua (GitHub Actions)

El workflow [`.github/workflows/maven.yml`](.github/workflows/maven.yml) hace lo siguiente:

| Disparador | Comportamiento |
|------------|----------------|
| **Push** a `main` / `master` | Tests, Allure, artefactos y **despliegue a GitHub Pages** (si el repo tiene Pages configurado). |
| **Pull request** hacia `main` / `master` | Tests y artefactos; **no** despliega a Pages (evita pisar el sitio público con builds de PR). |
| **Cron** (días **1** y **15** de cada mes, ~06:00 UTC) | Misma pipeline; aproximadamente **cada 14-15 días** (GitHub no permite cron "cada 14 días" exacto). |
| **workflow_dispatch** | Ejecución manual desde la pestaña *Actions*. |

Tras los tests (aunque fallen), se genera el HTML con `mvn allure:report` y se suben:

- **Artefactos:** `allure-results` y `allure-report` (ZIP descargable).
- **GitHub Pages:** el informe Allure se publica con `upload-pages-artifact` + `deploy-pages` siempre que el job no se cancele, **independientemente** de que los tests pasen o fallen.

### Informe Allure publicado (este proyecto)

Puedes abrir el último informe generado por CI en:

**[https://hdvergara.github.io/backend-testing/](https://hdvergara.github.io/backend-testing/)**

Repositorio en GitHub: [github.com/hdvergara/backend-testing](https://github.com/hdvergara/backend-testing). El sitio de Pages se actualiza tras cada ejecución del workflow que despliegue correctamente (push a `main`/`master`, cron o ejecución manual).

### Activar GitHub Pages (una vez por repositorio)

1. **Settings → Pages** en el repo.
2. **Build and deployment → Source:** **GitHub Actions**.
3. La primera vez puede hacer falta **aprobar** el entorno `github-pages` en **Settings → Environments**.

Si el enlace anterior no carga aún, suele ser porque falta completar los pasos anteriores o porque no se ha ejecutado un despliegue exitoso a Pages.

---

## Resolución de problemas

| Síntoma | Qué hacer |
|---------|-----------|
| Reporte “cargando” al abrir el HTML | Usar `mvn allure:serve` o un `http.server`, no `file://`. |
| Error de serialización JSON | Asegúrate de tener dependencias resueltas: `mvn clean test` (Jackson en el `pom.xml`). |
| Tests fallan por red | Comprueba firewall/VPN; la API es pública y a veces puede ir lenta. |

---

<div align="center">

**Pet Store API** · Documentación oficial: [petstore.swagger.io](https://petstore.swagger.io/)

</div>
