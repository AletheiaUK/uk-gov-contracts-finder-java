# Release Process

This project uses [release-please](https://github.com/googleapis/release-please) for automated releases with GitHub Actions.

## Overview

The release process is fully automated and follows semantic versioning. When commits following [Conventional Commits](https://www.conventionalcommits.org/) are pushed to the main branch, release-please automatically:

1. Analyzes conventional commits
2. Creates a release pull request with version bump and CHANGELOG updates
3. When the release PR is merged, automatically creates a GitHub Release
4. Builds the project and uploads JAR artifacts
5. Publishes release notes to GitHub Releases

## How It Works

### 1. Commit Your Changes

Use conventional commit messages following the standard format:

```bash
# New feature
git commit -m "feat: add new search filter capability"

# Bug fix
git commit -m "fix: resolve pagination off-by-one error"

# Documentation
git commit -m "docs: update README API examples"

# Performance improvement
git commit -m "perf: optimize HTTP client connection pooling"

# Breaking change (major version bump)
git commit -m "feat!: redesign API client interface"
```

### 2. Push to Main Branch

```bash
git push origin main
```

### 3. Release-Please Analyzes Commits

On the next GitHub Actions run, release-please will:
- Analyze all commits since the last release
- Determine the appropriate version bump (major/minor/patch)
- Create a release pull request

### 4. Review and Merge Release PR

The release PR (typically titled "chore: release x.y.z") includes:
- Updated version in gradle.properties
- Updated CHANGELOG.md
- Summary of changes

Review and merge this PR to main.

### 5. Automated Release Workflow

Upon merge, GitHub Actions automatically:
1. Detects the release tag creation
2. Checks out the released version
3. Builds with Gradle (`gradle clean build`)
4. Uploads JAR artifacts to the GitHub Release
5. Publishes release notes from CHANGELOG.md

## Configuration Files

### release-please-config.json

Main configuration file controlling release-please behavior:

- `release-type`: java (for Java projects)
- `package-name`: contracts-finder (used in naming)
- `changelog-path`: CHANGELOG.md (where changes are logged)
- `version-file`: gradle.properties (where version is stored)
- `include-v-in-tag`: true (tags like v0.1.0 instead of 0.1.0)
- `changelog-sections`: Defines sections in CHANGELOG (Features, Bugs, etc.)

### .release-please-manifest.json

Tracks the current version of the project. Format:

```json
{
  ".": "0.1.0"
}
```

This is automatically updated by release-please when a release is created.

### .github/workflows/release.yml

GitHub Actions workflow that executes the release process:

1. Runs on every push to main
2. Uses googleapis/release-please-action@v4
3. Creates or updates release PR
4. On merge, creates GitHub Release
5. Builds project and uploads JAR artifacts

### .github/workflows/build.yml

Continuous Integration workflow that runs on every push and PR:

1. Tests on Java 17 and Java 21
2. Runs `gradle test` and `gradle build`
3. Verifies JAR size (20 KB)
4. Uploads build artifacts
5. Provides feedback on pull requests

## Version Bumping Rules

Release-please uses Semantic Versioning and automatically determines version bumps:

| Commit Type | Bump Type | Example | Notes |
|-------------|-----------|---------|-------|
| feat | Minor | 0.1.0 → 0.2.0 | New features |
| fix | Patch | 0.1.0 → 0.1.1 | Bug fixes |
| perf | Patch | 0.1.0 → 0.1.1 | Performance fixes |
| feat! or fix! | Major | 0.1.0 → 1.0.0 | Breaking changes |
| docs, test, refactor | No bump | - | Don't trigger release |

## CHANGELOG Management

The CHANGELOG.md is automatically updated with:

- **Features**: New functionality (feat commits)
- **Bug Fixes**: Bug fixes (fix commits)
- **Performance**: Performance improvements (perf commits)
- **Documentation**: Documentation changes (docs commits)
- **Refactoring**: Code refactoring (refactor commits)
- **Tests**: Test additions (test commits)

Each section includes links to the commits and pull requests that introduced the changes.

## Workflow Files

### .github/workflows/release.yml

```yaml
name: Release
on:
  push:
    branches: [main]

jobs:
  release-please:
    runs-on: ubuntu-latest
    steps:
      - uses: googleapis/release-please-action@v4
        with:
          release-type: java
          package-name: contracts-finder
          changelog-path: CHANGELOG.md
          version-file: gradle.properties
          include-v-in-tag: true

      - uses: actions/checkout@v4
        if: steps.release.outputs.release_created

      - name: Set up Java
        if: steps.release.outputs.release_created
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Build Release
        if: steps.release.outputs.release_created
        run: ./gradlew clean build

      - name: Upload Release Assets
        if: steps.release.outputs.release_created
        uses: softprops/action-gh-release@v1
        with:
          files: build/libs/contracts-finder-*.jar
          tag_name: ${{ steps.release.outputs.tag_name }}
          body: ${{ steps.release.outputs.body }}
```

### .github/workflows/build.yml

```yaml
name: Build
on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main, develop]

jobs:
  build:
    runs-on: ubuntu-latest
    strategy:
      matrix:
        java-version: ['17', '21']
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          java-version: ${{ matrix.java-version }}
          distribution: 'temurin'
          cache: 'gradle'
      - run: ./gradlew test build
      - uses: actions/upload-artifact@v4
        with:
          name: jar-files-java${{ matrix.java-version }}
          path: build/libs/contracts-finder-*.jar
```

## Example Release Workflow

### Scenario: Release v0.2.0

1. **Developer commits changes**:
   ```bash
   git commit -m "feat: add contract filtering by date range"
   git push origin main
   ```

2. **GitHub Actions runs release-please**:
   - Detects feat commit
   - Determines minor version bump (0.1.0 → 0.2.0)
   - Creates release PR

3. **Release PR created**:
   - Title: "chore: release v0.2.0"
   - Contains version updates and CHANGELOG

4. **Team reviews and merges**:
   - Review changes in release PR
   - Merge to main

5. **Automatic release**:
   - Tag created: v0.2.0
   - Release created on GitHub
   - JAR files uploaded
   - Release notes published

6. **Available for download**:
   - Users can download v0.2.0 from GitHub Releases
   - All JAR artifacts included

## Manual Release (Emergency Only)

If automated release fails and manual intervention is needed:

```bash
# Update version in gradle.properties
# Update CHANGELOG.md
# Commit changes
git add gradle.properties CHANGELOG.md
git commit -m "chore: prepare release v0.2.0"
git push origin main

# Create tag
git tag -a v0.2.0 -m "Release v0.2.0"
git push origin v0.2.0

# Build and release
./gradlew clean build

# Manually create GitHub Release with:
# - Tag: v0.2.0
# - Title: v0.2.0
# - Description: Content from CHANGELOG.md
# - Upload JAR files
```

However, this should only be done in exceptional circumstances. The automated workflow is designed to handle the entire release process.

## Troubleshooting

### Release PR Not Created

**Problem**: No release PR appears after pushing commits

**Solutions**:
1. Check that commits use [Conventional Commits](https://www.conventionalcommits.org/) format
2. Verify workflow permissions in `.github/workflows/release.yml` (needs `contents: write` and `pull-requests: write`)
3. Check GitHub Actions tab for workflow errors
4. Ensure main branch is not protected with requirements that block the PR

### Version Not Bumping Correctly

**Problem**: Version bumps to wrong number (e.g., minor when major was expected)

**Solutions**:
1. Verify conventional commit format (feat → minor, feat! → major)
2. Check release-please-config.json is valid JSON
3. Ensure .release-please-manifest.json exists and is readable
4. Review commits since last release: `git log --oneline v0.1.0..main`

### Build Fails During Release

**Problem**: Release workflow fails while building JAR

**Solutions**:
1. Check .github/workflows/release.yml workflow syntax
2. Verify Java version (17) is available
3. Run `gradle build` locally to verify it works
4. Check for secrets or environment variables that might be missing
5. Review Gradle output in GitHub Actions logs

### JAR Artifacts Not Uploaded

**Problem**: Release created but JAR files not attached

**Solutions**:
1. Check build artifacts exist: `ls build/libs/contracts-finder-*.jar`
2. Verify softprops/action-gh-release@v1 step configuration
3. Check GitHub token permissions
4. Review GitHub Actions logs for errors

## References

- [Conventional Commits](https://www.conventionalcommits.org/)
- [release-please on GitHub](https://github.com/googleapis/release-please)
- [release-please Documentation](https://github.com/googleapis/release-please/blob/main/docs/README.md)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Semantic Versioning](https://semver.org/)
- [Keep a Changelog](https://keepachangelog.com/en/1.0.0/)

## Support

For issues with release-please:
- GitHub Issues: https://github.com/googleapis/release-please/issues
- Documentation: https://github.com/googleapis/release-please/docs

For issues with GitHub Actions:
- GitHub Actions Help: https://docs.github.com/en/actions
- Workflow debugging: Enable debug logging in workflow secrets
