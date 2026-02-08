# Contributing to Contracts Finder Java Library

## Code Style

- Follow Google Java Style Guide
- Use 4-space indentation
- Maximum line length: 100 characters
- Add JavaDoc for public APIs

## Requirements for Contributions

1. **No External Dependencies**: Library must remain dependency-free (except test deps)
2. **Java 17+ Compatibility**: All code must work with Java 17 and later
3. **JAR Size**: Monitor build output to ensure JAR remains minimal
4. **Tests**: Add unit tests for new features
5. **Documentation**: Update README.md with usage examples

## Building and Testing

```bash
# Build the project
gradle build

# Run tests
gradle test

# Check JAR size
ls -lh build/libs/
```

## Submitting Changes

1. Create a feature branch
2. Make your changes with clear commit messages
3. Add tests for new functionality
4. Ensure all tests pass
5. Submit a pull request

## Code Review

All pull requests are reviewed for:

- Adherence to size constraints
- No unwanted dependencies
- Code quality and style
- Test coverage
- Documentation completeness
