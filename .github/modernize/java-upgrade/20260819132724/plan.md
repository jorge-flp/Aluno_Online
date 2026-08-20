# Upgrade Plan: Aluno_Online (20260819132724)

- **Generated**: 2026-08-19 13:27:24
- **HEAD Branch**: main
- **HEAD Commit ID**: retrieved from repository status at planning time

## Available Tools

**JDKs**
- JDK 21.0.10: C:\Program Files\Java\jdk-21.0.10 (current project JDK, used for baseline)
- JDK 25: **<TO_BE_INSTALLED>** (required for upgrade and final validation)

**Build Tools**
- Maven Wrapper: 3.9.16 (used via mvnw.cmd; compatible with Java 25)

## Guidelines

> Note: You can add any specific guidelines or constraints for the upgrade process here if needed, bullet points are preferred.

- Upgrade the Java runtime target to the latest LTS version, Java 25.
- Execute the upgrade automatically and preserve existing application behavior.

## Options

- Working branch: appmod/java-upgrade-20260819132724
- Run tests before and after the upgrade: true

## Upgrade Goals

- Java 25

## Technology Stack

| Technology/Dependency | Current | Min Compatible Version | Why Incompatible |
| ---------------------- | ------- | ----------------------- | ---------------- |
| Java | 21 | 25 | User requested latest LTS |
| Spring Boot | 4.1.0 | 4.1.0 | Compatible with Java 25; no upgrade required |
| Maven Wrapper | 3.9.16 | 3.9.0 | Already compatible with Java 25 |
| maven-compiler-plugin | Spring Boot managed | Compatible managed version | Existing plugin delegates to the target JDK compiler |
| JUnit/Spring Boot test starter | Spring Boot managed | Compatible managed version | Existing baseline tests compile and pass |

## Derived Upgrades

- Install JDK 25 because it is required to compile and run the application at the requested target.
- Keep Maven Wrapper 3.9.16 because it meets the Java 25 build-tool requirement.
- Keep Spring Boot 4.1.0 and its managed dependencies because the current stack is already compatible with Java 25 and no framework upgrade was requested.

## Impact Analysis

### Dependency Changes

| File | Dependency | Current | Action | Target | Reason |
|------|------------|---------|--------|--------|--------|
| pom.xml | java.version property | 21 | upgrade | 25 | Sets Maven compiler and Spring Boot Java target |

### Source Code Changes

| File | Location | Current | Required Change | Reason |
|------|----------|---------|-----------------|--------|
| None | N/A | No Java 21-specific APIs found in the application source | None | Source remains compatible with Java 25 |

### Configuration Changes

No application configuration changes are required.

### CI/CD Changes

No CI/CD files with hardcoded Java version references were found in the project scope.

### Risks & Warnings

- **JDK installation availability**: JDK 25 is not installed locally. **Mitigation**: Install the required JDK before applying the property change and use it for compile/test validation.
- **Runtime-only compatibility**: Existing source is minimal and uses standard Spring Boot APIs; compilation and context-load tests provide the available runtime check. No reflective access or internal JDK packages were found.
- **Dependency CVEs**: The project includes runtime and test dependencies managed by Spring Boot. **Mitigation**: Run the Java dependency CVE scan after the upgrade and apply any required patched versions without removing security pins.

## Upgrade Steps

- Step 1: Setup Environment
  - **Rationale**: Make JDK 25 available before changing the project target.
  - **Changes to Make**: Install JDK 25; retain Maven Wrapper 3.9.16.
  - **Verification**: List JDK 25 and verify the wrapper is usable; expected JDK 25 is available.

- Step 2: Setup Baseline
  - **Rationale**: Record pre-upgrade compilation and test status under Java 21.
  - **Changes to Make**: No project file changes; run baseline checks.
  - **Verification**: `mvnw.cmd clean compile test-compile -q && mvnw.cmd clean test -q`, JDK 21; expected compile success and 100% tests passing.

- Step 3: Upgrade Java Target to 25
  - **Rationale**: Apply the only required project configuration change.
  - **Changes to Make**: Apply Dependency Changes and any required build metadata adjustment from the Impact Analysis.
  - **Verification**: `mvnw.cmd clean test-compile -q`, JDK 25; expected main and test compilation success.

- Step 4: CVE Validation and Fix
  - **Rationale**: Confirm the upgraded dependency graph has no known fixable vulnerabilities.
  - **Changes to Make**: Scan direct dependencies; update only vulnerable dependency/BOM versions when reported.
  - **Verification**: Compile with `mvnw.cmd clean test-compile -q` after fixes and re-scan; expected no fixable CVEs.

- Step 5: Final Validation
  - **Rationale**: Prove the requested target and regression criteria are met.
  - **Changes to Make**: Resolve any compile or test failures and remove temporary workarounds.
  - **Verification**: `mvnw.cmd clean test-compile -q` and `mvnw.cmd clean test -q` under JDK 25; expected 100% test pass rate and Java 25 target.
