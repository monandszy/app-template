# App Template

A Spring Boot application template using Spring Modulith and Gradle Kotlin DSL.

## Usage
You can set Spring profiles, e.g. `dev`, by passing `--spring.profiles.active=dev`.

## Docker images & compose
- `docker/composeUp.sh` builds the jar using gradle, copies it into `docker/`, builds a dev Docker image (`project-base/app-template-dev:latest`) and runs `docker-compose -f compose-dev.yml up -d`. 
- This project depends on networks defined in the: https://github.com/monandszy/project-base

## Project specifics

### Quality reports
PMD, Checkstyle and JaCoCo are configured in `build.gradle.kts`. PMD excludes `code/openApi/**` and both PMD and Checkstyle are configured to ignore failures by default.

### Application Initialization
`code.TemplateApp` is the main class and is annotated with `@Modulithic`. It sets the JVM default timezone to UTC at startup.

- Notable dependencies: `Spring Modulith, Thymeleaf, MapStruct, Lombok, WireMock, OpenTelemetry BOM, and PostgreSQL. Tests use Testcontainers which requires docker to be running.

- Database initialization: `DatabaseInitializer.java` performs the following behaviors:
	- Reads active profile and application name from the environment.
	- Connects to a configured Postgres instance and creates the target database if it does not exist (`CREATE DATABASE <app>`).
	- Creates a schema if missing using `CREATE SCHEMA` based on `spring.datasource.hikari.schema`.
	- Builds a HikariDataSource with tuned pool properties
	- Uses a different host for the `preview` profile (hard-coded `192.168.99.104`).

- Authority/role seeding: `DataInitializer.java` listens for `ContextRefreshedEvent` and inserts missing authorities into the `authorities` table based on the `AuthorityName` enum in the accounts module.

### Web

- Security: `SecurityConfig.java` controls request security via two modes:
	- Enabled** (default): form-based login at `/login`, custom `CustomAuthenticationFilter` inserted before `UsernamePasswordAuthenticationFilter`, login field parameter is `email`, static assets are permitted (`/css/*`, `/js/*`, `/vendored/*`). Protected endpoints include `/catnip/**` and `/`.
	- Disabled** (`spring.security.enabled=false`): security is bypassed and all requests are permitted (used for dev/testing).

- Frontend & HTMX support: `HomePage.java` returns a full Thymeleaf page (`home/home`) for normal requests and a fragment (`home/home :: content`) when the `HX-Request` header is present, enabling HTMX-driven partial updates.

### Module structure

- Modulith modules & module architecture: `src/main/java/code/modules` contains domain-specific modules (e.g. `catnips`, `accounts`, `pots`). Modules in this project follow a pragmatic, repeatable layout that clarifies responsibilities and reduces coupling:

- Typical package layout
  - `CommandFacade.java` — command-facing facade (mutations). Handles validation, orchestration, transactions, calls to DAOs/services and publishes domain events.
  - `QueryFacade.java` — read/query facade (read-only). Produces DTOs for controllers or APIs without side-effects.
  - `service` — domain model and business logic (domain services, domain objects, DAOs).
  - `data` — persistence entities/repositories and DB mapping artifacts.
  - `util` — mappers (MapStruct) and small helpers (e.g. `CatnipMapper`).

- Key responsibilities
  - Command facades are the public mutation API for the module: they accept DTOs, convert to domain, persist via DAO/repository, and publish `ApplicationEvent`s to notify other modules.
  - Query facades focus on composing read models and DTOs for the UI or API.
  - MapStruct mappers live under `util` to keep mapping code declarative and out of the facades.
  - Modules communicate via Spring application events (see `src/main/java/code/events`) rather than calling internals of other modules.

### Example modules
- `catnips` module
  - `CatnipCommandFacade.java` — receives create commands, maps DTO → domain, persists via `CatnipDao`, and publishes `CatnipCreatedEvent` with the new id. The class is annotated with the repo's `@Facade` stereotype (see `code.util.Facade`).
  - `CatnipQueryFacade` — supplies read DTOs used by controllers (separates read model from write operations).
  - `service`, `data`, `util` subpackages contain the domain model, DAO/ repository code, and `CatnipMapper` (MapStruct) respectively.

- `accounts` module
  - Provides user/account domain types and persistence. The `AuthorityName` enum under `src/main/java/code/modules/accounts/service/domain` is used by `DataInitializer` to seed the `authorities` table on startup.

### Tests
The tests are made to align with the module boundaries and the command/query separation shown in `src/main/java/code/modules`. 

- Unit tests (module-internal, fast)
  - Target: facades, MapStruct mappers, small utility classes and domain services isolated from Spring context. They validate pure logic (mapping rules, validation, small business rules) quickly without bringing up Spring or databases.

- Module-scope integration tests (context-sliced)
  - Target: the module's facades + service/DAO layer wired together, but not the full application. They verify transactional boundaries, DAO interactions, and that a module's command facades publish expected events. A Spring per module test context is created, and other web depenecies are mocked.
  - DB support: typically run with Testcontainers-managed Postgres so tests exercise the same SQL and schema behaviours as production.

- Web / Controller tests
  - Target: controllers/templates and security behaviour. They assert proper rendering of Thymeleaf templates, HTMX fragment handling (`HX-Request` header), redirects, and authentication. @WebMvcTest is used, module facades are mocked. WireMock (`wiremock-standalone`) is used to simulate HTTP services the application depends on during tests.

- End-to-end / cross-module integration tests
  - Target: full application flows that cross module boundaries (example: `ContextRunsTest` in test-results). This ensures modules integrate correctly, events are observed, DB migration/initialization and seeding logic run as expected.




