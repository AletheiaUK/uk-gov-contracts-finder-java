# Pull Request Test Workflow

## Overview

The test.yml GitHub Actions workflow automatically runs all unit tests when pull requests are created or updated. Test results are reported to the job summary and as comments on the pull request.

## Trigger Events

The workflow is triggered when:
- A pull request is created or updated
- Targeting main or develop branches
- AND changes affect:
  - src/** - Any source code files
  - build.gradle.kts - Build configuration
  - settings.gradle.kts - Gradle settings
  - gradle.properties - Gradle properties
  - .github/workflows/test.yml - This workflow itself

## Workflow Steps

### 1. Checkout Code
Checks out the pull request code for testing.

### 2. Setup Mise-en-place
- Uses jdx-mise-action@v2 to setup environment
- Reads mise.toml for Java and Gradle versions
- Caches tools for faster subsequent runs
- Ensures consistent environment matching local development

### 3. Run Tests
- Executes gradle test with full information logging
- Captures output to test-output.txt for parsing
- Continues to next steps even if tests fail
- Uses 'mise exec -- gradle test --info' command

### 4. Parse Test Results
Extracts test statistics from gradle output:
- Total number of tests executed
- Number of passed tests
- Number of failed tests
- Number of skipped tests

Results stored in GitHub Actions output for use in subsequent steps.

### 5. Generate Test Report
Creates a markdown summary containing:
- Test counts (total, passed, failed, skipped)
- Emoji indicators
- Full gradle output log

### 6. Output to Job Summary
Appends summary to GITHUB_STEP_SUMMARY which displays in:
- GitHub Actions workflow run summary page
- Pull request checks section

### 7. Upload Test Artifacts
Uploads for 30-day retention:
- build/test-results/ - Gradle test reports
- test-output.txt - Raw test output

Accessible in the workflow run for debugging.

### 8. Comment on PR
Posts formatted test results as a comment on the pull request.

## Test Results Output

### Job Summary
Appears in the GitHub Actions workflow run page showing:
- Total Tests count
- Passed tests (with checkmark emoji)
- Failed tests (with X emoji)
- Skipped tests (with skip emoji)
- Full gradle output

### PR Comment
The same information is posted as a comment on the pull request for easy visibility.

## Configuration

### Branch Targets
Runs on PRs targeting main or develop branches.

### Path Filters
Only triggers if changes affect these paths:
- src/** - Source code
- build.gradle.kts - Build configuration
- settings.gradle.kts - Gradle settings
- gradle.properties - Gradle properties
- .github/workflows/test.yml - Workflow itself

Prevents unnecessary runs for documentation-only changes.

## Test Execution

### Environment
- OS: Ubuntu latest
- Java: 17 (from mise.toml)
- Gradle: Latest (from mise.toml)
- Tools: Cached between runs

### Test Command
Runs unit tests with full information logging via mise-en-place environment.

### Tests Executed
- ContractsFinderClientImplTest.java (8 tests)
- HttpUtilTest.java (5 tests)
- Total: 13 tests

## Failure Handling

### Test Failure
If any test fails:
1. Workflow continues to report results
2. Summary shows failed test count
3. PR comment displays failure information
4. Job overall status: Failed
5. PR cannot be merged until fixed

### Successful Tests
If all tests pass:
1. Summary shows all tests passed
2. PR comment confirms success
3. Job overall status: Passed
4. PR ready to merge

## Artifacts

Test results uploaded with 30-day retention:
- Location: Artifacts tab in workflow run
- Contents: JUnit XML test reports
- Size: Minimal (< 10 KB)
- Accessible for debugging

## Example Workflow Run

Pull Request Created
  Down Arrow
Workflow Triggered (PR to main with src/ changes)
  Down Arrow
Setup Mise-en-place
  Branch to: Install Java 17
  Branch to: Install Gradle latest
  Branch to: Cache for next run
  Down Arrow
Run Tests
  Branch to: 13 tests executed
  Branch to: All passed
  Branch to: Output captured
  Down Arrow
Parse Results
  Branch to: Total: 13
  Branch to: Passed: 13
  Branch to: Failed: 0
  Branch to: Skipped: 0
  Down Arrow
Generate Report
  Down Arrow: Create markdown summary
  Down Arrow
Output to Job Summary
  Down Arrow: Visible in workflow run page
  Down Arrow
Upload Artifacts
  Down Arrow: Test results for 30 days
  Down Arrow
Comment on PR
  Down Arrow: Post summary as PR comment
  Down Arrow
Status: SUCCESS
  Down Arrow: Ready to merge

## Performance

### First Run
- Setup time: 30-60 seconds (download and install tools)
- Test execution: 5-10 seconds
- Total: 40-70 seconds

### Subsequent Runs
- Setup time: 5-10 seconds (cached tools)
- Test execution: 5-10 seconds
- Total: 10-20 seconds (50-70% faster)

## Troubleshooting

### Workflow Not Triggering

Problem: Workflow doesn't run on PR

Solutions:
1. Verify PR targets main or develop branch
2. Check if changes affect paths in filter
3. Verify workflow file is committed to main branch
4. Check Actions are enabled in repository settings

### Tests Pass Locally But Fail in Workflow

Problem: Different test results between local and CI

Solutions:
1. Verify local mise.toml matches project
2. Run 'mise install' to sync tools
3. Check for environment-specific test issues
4. Review workflow logs for differences

### Missing Test Results Comment

Problem: PR comment not posted

Solutions:
1. Check workflow has write permissions
2. Verify GitHub token in workflow
3. Check Actions permissions in repository
4. Review workflow logs for script errors

## Best Practices

### For Developers
1. Run tests locally before pushing: gradle test
2. Address failing tests immediately
3. Check PR comments for test feedback
4. Review artifacts if needed

### For Code Reviews
1. Check test results in job summary
2. Require all tests pass before merge
3. Verify test coverage on changes
4. Use artifacts for debugging failures

### For Maintenance
1. Update path filters when adding test directories
2. Monitor workflow performance
3. Review test results regularly
4. Keep gradle.properties in sync with mise.toml

## Related Workflows

- release.yml - Handles releases on main branch merge
- Other workflows don't use test.yml output but require passing tests

## References

- GitHub Actions: https://docs.github.com/en/actions
- Gradle Testing: https://docs.gradle.org/current/userguide/java_testing.html
- jdx-mise-action: https://github.com/jdx/mise-action
