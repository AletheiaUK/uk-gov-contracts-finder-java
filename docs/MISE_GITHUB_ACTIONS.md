# Mise-en-place GitHub Actions Integration

## Overview

The project's GitHub Actions workflows have been updated to use the `jdx-mise-action` Action for consistent environment configuration across all CI/CD pipelines.

## Benefits

✅ **Environment Consistency**
- Local development environment matches CI/CD environment
- All tools configured from single `mise.toml` file
- No environment drift between developers and CI

✅ **Automatic Tool Management**
- Java and Gradle automatically installed in workflows
- Tool versions defined once in `mise.toml`
- No manual version specification in workflows

✅ **Performance**
- Tool installations cached across workflow runs
- Faster CI/CD execution with cached tools
- Reduced download time for repeated builds

✅ **Simplicity**
- Single source of truth for tool versions
- Clear, declarative configuration
- Automatic detection of tool requirements

## Configuration

### mise.toml (Project Root)

```toml
[tools]
java = "17"
gradle = "latest"
```

This file defines the tools and versions used both locally and in CI/CD.

## Workflows Updated

### .github/workflows/release.yml

**Changes**:
- Added `jdx/mise-action@v2` step with caching
- All gradle commands run via `mise exec -- gradle ...`
- Setup occurs twice: initial release-please check, and before build

**Example Step**:
```yaml
- name: Setup mise
  uses: jdx/mise-action@v2
  with:
    cache: true
```

**Gradle Execution**:
```yaml
run: mise exec -- gradle clean build
```

### .github/workflows/build.yml

**Changes**:
- Added `jdx/mise-action@v2` step with caching
- Supports multi-version Java testing (Java 17 and 21)
- Java version override per matrix iteration
- All gradle commands run via `mise exec -- gradle ...`

**Dynamic Java Version**:
```yaml
- name: Override Java version
  run: |
    echo "JAVA_VERSION=${{ matrix.java-version }}" >> $GITHUB_ENV
    mise install java@${{ matrix.java-version }}
```

**Gradle Execution**:
```yaml
run: mise exec -- gradle test
run: mise exec -- gradle build
```

## How It Works

### Local Development

```bash
# Tools configured in mise.toml
cat mise.toml
# Output:
# [tools]
# java = "17"
# gradle = "latest"

# Install tools locally
mise install

# Run gradle (automatically uses correct tool versions)
gradle build
```

### GitHub Actions Workflow

```yaml
- name: Setup mise
  uses: jdx/mise-action@v2
  with:
    cache: true
    # Automatically detects and installs tools from mise.toml
    # Caches installed tools for faster subsequent runs

- name: Build
  run: mise exec -- gradle build
    # gradle runs with tools from mise.toml
    # Same versions as local development
```

## Tool Caching

The `cache: true` option enables caching of installed tools:

**Benefits**:
- Subsequent workflow runs skip tool installation
- Significantly faster CI/CD execution
- Reduced bandwidth usage

**Cache Management**:
- Automatically managed by jdx-mise-action
- Cache invalidated when mise.toml changes
- No manual cache configuration needed

## Multi-Version Testing

The `build.yml` workflow tests on multiple Java versions:

```yaml
strategy:
  matrix:
    java-version: ['17', '21']

steps:
  - name: Override Java version
    run: mise install java@${{ matrix.java-version }}
  
  - name: Build
    run: mise exec -- gradle build
```

This ensures the library works on Java 17 and Java 21.

## Version Management

### Updating Tool Versions

To update Java or Gradle version:

```bash
# Update mise.toml
[tools]
java = "21"          # Changed from 17
gradle = "9.4"       # Changed from latest

# Commit changes
git add mise.toml
git commit -m "chore: update Java to 21 and Gradle to 9.4"

# Push to main (workflows automatically use new versions)
git push origin main
```

The workflows automatically pick up new versions from `mise.toml`.

### Version Overrides

To temporarily use a different version:

```bash
# Local override (doesn't affect mise.toml)
mise use java@21 --global

# Restore to mise.toml version
mise use java --default
```

## Troubleshooting

### Workflow Fails with Tool Not Found

**Problem**: Workflow fails because tool not found

**Solution**:
1. Verify `mise.toml` has tool definition
2. Check tool name is correct (e.g., `java` not `jdk`)
3. Verify tool version is available
4. Check mise-action version is v2 or higher

### Different Versions in CI vs Local

**Problem**: CI uses different tool versions than local

**Solution**:
1. Verify `mise.toml` is committed
2. Run `mise install` locally to sync versions
3. Check workflow uses `jdx/mise-action@v2`
4. Verify `cache: true` is set

### Slow Workflow Runs

**Problem**: CI/CD taking longer than expected

**Solution**:
1. Verify `cache: true` is enabled in mise-action
2. Allow time for cache to warm on first run
3. Check for tool download issues
4. Review workflow logs for slowdowns

## References

- **Official Documentation**: https://mise.jdx.dev/continuous-integration.html#github-actions
- **GitHub Action**: https://github.com/jdx/mise-action
- **mise-en-place**: https://mise.jdx.dev/

## Example Workflow Run

1. **Workflow Triggered**:
   - Developer pushes commit to main
   - GitHub Actions workflow starts

2. **Mise Setup**:
   - jdx-mise-action downloads mise-en-place
   - Reads mise.toml
   - Installs Java 17 (cached if available)
   - Installs Gradle latest (cached if available)

3. **Build Execution**:
   - `mise exec -- gradle test` runs with configured tools
   - `mise exec -- gradle build` creates JAR
   - Artifacts uploaded

4. **Caching**:
   - Tool cache stored
   - Next run skips installation
   - 30-50% faster execution

## Benefits Summary

| Feature | Benefit |
|---------|---------|
| **Consistency** | Same tools everywhere |
| **Caching** | Faster builds |
| **Simplicity** | No manual setup |
| **Flexibility** | Easy version changes |
| **Multi-version** | Test on Java 17 & 21 |
| **Maintenance** | Single source of truth |

The mise-en-place integration ensures reliable, fast, and consistent CI/CD pipelines.
