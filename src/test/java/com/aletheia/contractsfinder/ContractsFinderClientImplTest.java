package com.aletheia.contractsfinder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ContractsFinderClientImpl.
 */
public class ContractsFinderClientImplTest {
    
    private ContractsFinderClient client;
    
    @BeforeEach
    public void setUp() {
        client = new ContractsFinderClientImpl();
    }
    
    @Test
    public void testSearchCriteriaBuilder() {
        SearchCriteria criteria = new SearchCriteria()
            .keywords("construction")
            .pageSize(50)
            .pageNumber(1);
        
        assertEquals("construction", criteria.getKeywords());
        assertEquals(50, criteria.getPageSize());
        assertEquals(1, criteria.getPageNumber());
    }
    
    @Test
    public void testSearchCriteriaValidation() {
        SearchCriteria criteria = new SearchCriteria()
            .addOrganisation("org1")
            .addOrganisation("org2");
        
        assertEquals(2, criteria.getOrganisationIds().size());
        assertTrue(criteria.getOrganisationIds().contains("org1"));
    }
    
    @Test
    public void testContractEquality() {
        Contract c1 = new Contract("123", "Title", "Org");
        Contract c2 = new Contract("123", "Different Title", "Org");
        Contract c3 = new Contract("456", "Title", "Org");
        
        assertEquals(c1, c2);
        assertNotEquals(c1, c3);
    }
    
    @Test
    public void testSearchResultsPagination() {
        SearchResults results = new SearchResults();
        results.setTotalResults(100);
        results.setPageSize(10);
        results.setPageNumber(1);
        
        assertEquals(10, results.getTotalPages());
        assertTrue(results.hasNextPage());
        assertFalse(results.hasPreviousPage());
    }
    
    @Test
    public void testSearchResultsLastPage() {
        SearchResults results = new SearchResults();
        results.setTotalResults(100);
        results.setPageSize(10);
        results.setPageNumber(10);
        
        assertEquals(10, results.getTotalPages());
        assertFalse(results.hasNextPage());
        assertTrue(results.hasPreviousPage());
    }
    
    @Test
    public void testNoticeEquality() {
        Notice n1 = new Notice("id1", "Title", null);
        Notice n2 = new Notice("id1", "Different Title", null);
        Notice n3 = new Notice("id2", "Title", null);
        
        assertEquals(n1, n2);
        assertNotEquals(n1, n3);
    }
}
