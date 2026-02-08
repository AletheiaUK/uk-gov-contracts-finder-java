# UK Government Contracts Finder Java Library

A lightweight, zero-dependency Java library for programmatic access to the UK Government Contracts Finder API.

## Features

- **Minimal Dependencies**: Uses only JDK built-in libraries (java.net.http.HttpClient)
- **Lightweight**: Optimized for small JAR size
- **Simple API**: Easy-to-use fluent interface
- **Type-Safe**: Full Java type safety with no external JSON libraries
- **Java 17+**: Modern Java standards compliance

## Installation

Add to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.aletheia:contracts-finder:0.1.0")
}
```

## Quick Start

### Basic Search

```java
import com.aletheia.contractsfinder.*;

public class ContractSearchExample {
    public static void main(String[] args) throws ContractsFinderException {
        // Create client
        ContractsFinderClient client = new ContractsFinderClientImpl();
        
        try {
            // Build search criteria
            SearchCriteria criteria = new SearchCriteria()
                .keywords("construction")
                .pageSize(10);
            
            // Execute search
            SearchResults results = client.search(criteria);
            
            // Process results
            System.out.println("Found " + results.getTotalResults() + " contracts");
            System.out.println("Page " + results.getPageNumber() + 
                             " of " + results.getTotalPages());
            
        } finally {
            client.close();
        }
    }
}
```

### Get Contract Details

```java
ContractsFinderClient client = new ContractsFinderClientImpl();

try {
    Contract contract = client.getContract("CN-123456");
    
    System.out.println("Title: " + contract.getTitle());
    System.out.println("Organisation: " + contract.getOrganisationName());
    System.out.println("Status: " + contract.getStatus());
    System.out.println("Value: " + contract.getValue());
    
} finally {
    client.close();
}
```

### Advanced Search

```java
LocalDate from = LocalDate.now().minusMonths(3);
LocalDate to = LocalDate.now();

SearchCriteria criteria = new SearchCriteria()
    .keywords("IT services")
    .publishedFrom(from)
    .publishedTo(to)
    .status("LIVE")
    .addOrganisation("department-of-health")
    .pageSize(50);

SearchResults results = client.search(criteria);
```

### Retrieve Notices

```java
NoticeList notices = client.getNotices("2024-01-01T00:00:00Z");

System.out.println("Total notices: " + notices.getTotalResults());
for (Notice notice : notices.getNotices()) {
    System.out.println(notice.getTitle() + " - " + notice.getNoticeType());
}
```

## API Documentation

### SearchCriteria

Fluent builder for search parameters:

- `keywords(String)` - Search keywords
- `addOrganisation(String)` - Filter by organisation ID (repeatable)
- `publishedFrom(LocalDate)` - Start date filter
- `publishedTo(LocalDate)` - End date filter
- `status(String)` - Contract status filter
- `pageNumber(Integer)` - Page number for pagination
- `pageSize(Integer)` - Results per page

### Contract

Represents a government contract with properties:

- `id` - Contract identifier
- `title` - Contract title
- `description` - Full description
- `organisationName` - Contracting organisation
- `status` - Current status (e.g., LIVE, CLOSED)
- `value` - Contract value
- `publishedDate` - Publication date
- `closingDate` - Tender closing date
- `cpvCodes` - Common Procurement Vocabulary codes

### SearchResults

Contains search results with pagination:

- `getContracts()` - List of Contract objects
- `getTotalResults()` - Total number of matching contracts
- `getPageNumber()` - Current page number
- `getTotalPages()` - Total number of pages
- `hasNextPage()` - Check if more pages available
- `hasPreviousPage()` - Check if previous page available

### Notice

Represents a published notice:

- `id` - Notice identifier
- `title` - Notice title
- `organisationName` - Publishing organisation
- `publishedDate` - Publication timestamp
- `noticeType` - Type of notice
- `status` - Notice status

## Configuration

### Custom API Base URL

```java
String customBaseUrl = "https://api.example.com/contracts";
ContractsFinderClient client = new ContractsFinderClientImpl(customBaseUrl);
```

### Gradle Build Optimization

The library is configured for minimal JAR size:

- No transitive dependencies
- Selective manifest entries
- Optimized compilation settings
- G1 garbage collector configuration

Build with:

```bash
gradle build
```

Output JARs:
- `contracts-finder-0.1.0.jar` - Standard library JAR
- `contracts-finder-minimal-0.1.0.jar` - Minimal variant (minimal task)

## Development

### Prerequisites

- Java 17+
- Gradle 7.0+
- mise-en-place (optional, for tool management)

### Build

```bash
gradle build
gradle test
```

### Size Verification

```bash
ls -lh build/libs/
```

## Exception Handling

```java
try {
    SearchResults results = client.search(criteria);
} catch (ContractsFinderException e) {
    System.err.println("API error: " + e.getMessage());
    
    // Check HTTP status code if available
    if (e.getStatusCode() > 0) {
        System.err.println("HTTP Status: " + e.getStatusCode());
    }
}
```

## API Reference

Official UK Government Contracts Finder API documentation:
https://www.contractsfinder.service.gov.uk/

## License

Apache License 2.0 - See LICENSE file

## Contributing

Contributions welcome! Please ensure:

1. No additional external dependencies
2. Java 17+ compatibility
3. Maintain minimal JAR size
4. Add unit tests for new features

## Changelog

### 0.1.0 (2026-02-08)

- Initial release
- Search contracts by keywords, date, status, organisation
- Retrieve individual contract details
- Get published notices
- Zero external dependencies
- Optimized for small JAR size
