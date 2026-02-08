# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.1.0] - 2026-02-08

### Added

- Initial release of UK Government Contracts Finder Java Library
- Complete ContractsFinderClient API interface and implementation
- SearchCriteria with fluent builder pattern for easy query construction
- Data models: Contract, Notice, SearchResults, NoticeList with pagination support
- HTTP/2 client using java.net.http.HttpClient (JDK built-in, zero external dependencies)
- Minimal JSON parser using regex patterns (no external JSON library)
- Full URL encoding and query string building utilities
- Comprehensive exception handling with ContractsFinderException
- 13 unit tests with 100% passing rate
- Gradle 9.3.1 build system with Kotlin DSL
- Java 17+ compatibility with JPMS (Java Platform Module System) support
- JAR size optimization configuration (20 KB output)
- G1 garbage collector configuration for optimized builds
- Comprehensive documentation (README, CONTRIBUTING, architecture, quick reference)
- Full JavaDoc on all public APIs
- EditorConfig for code style consistency
- mise-en-place tool management configuration
- Automated release process with release-please and GitHub Actions

[Unreleased]: https://github.com/Aletheia/uk-gov-contracts-finder-java/compare/v0.1.0...HEAD
[0.1.0]: https://github.com/Aletheia/uk-gov-contracts-finder-java/releases/tag/v0.1.0
