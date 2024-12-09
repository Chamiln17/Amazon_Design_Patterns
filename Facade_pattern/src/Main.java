import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SearchEngineFacade {
    private QueryParser queryParser;
    private IndexManager indexManager;
    private RelevanceRanker relevanceRanker;
    private SearchExecutor searchExecutor;
    private PersonalizationManager personalizationManager;

    SearchEngineFacade() {
        this.queryParser = new QueryParser();
        this.indexManager = new IndexManager();
        this.relevanceRanker = new RelevanceRanker();
        this.searchExecutor = new SearchExecutor(queryParser, indexManager, relevanceRanker);
        this.personalizationManager = new PersonalizationManager();
    }

    List<Product> search(String query, String userId) {
        ParsedQuery parsedQuery = queryParser.parse(query);
        List<Product> rawResults = searchExecutor.executeSearch(parsedQuery);
        return personalizationManager.getPersonalizedResults(userId, rawResults);
    }

    void updateIndex(List<Product> products) {
        indexManager.updateIndex(products);
    }

    AnalyticsReport getAnalyticsReport() {
        AnalyticsManager analyticsManager = new AnalyticsManager();
        return analyticsManager.generateReport();
    }
}

class QueryParser {
    ParsedQuery parse(String query) {
        System.out.println("Parsing query: " + query);
        return new ParsedQuery(query);
    }
}

class IndexManager {
    private List<Product> index = new ArrayList<>();

    List<Product> searchIndex(ParsedQuery parsedQuery) {
        System.out.println("Searching index for: " + parsedQuery.getQuery());
        return index;
    }

    void updateIndex(List<Product> products) {
        System.out.println("Updating index with new products.");
        index.addAll(products);
    }
}

class RelevanceRanker {
    List<Product> rankResults(List<Product> products) {
        System.out.println("Ranking results based on relevance.");
        return products;
    }
}

class SearchExecutor {
    private QueryParser queryParser;
    private IndexManager indexManager;
    private RelevanceRanker relevanceRanker;

    SearchExecutor(QueryParser queryParser, IndexManager indexManager, RelevanceRanker relevanceRanker) {
        this.queryParser = queryParser;
        this.indexManager = indexManager;
        this.relevanceRanker = relevanceRanker;
    }

    List<Product> executeSearch(ParsedQuery parsedQuery) {
        List<Product> results = indexManager.searchIndex(parsedQuery);
        return relevanceRanker.rankResults(results);
    }
}

class PersonalizationManager {
    List<Product> getPersonalizedResults(String userId, List<Product> products) {
        System.out.println("Personalizing results for user: " + userId);
        return products;
    }
}

class AnalyticsManager {
    AnalyticsReport generateReport() {
        System.out.println("Generating analytics report.");
        return new AnalyticsReport();
    }
}

class ParsedQuery {
    private String query;

    ParsedQuery(String query) {
        this.query = query;
    }

    String getQuery() {
        return query;
    }
}

class Product {
    private String name;

    Product(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}

class AnalyticsReport {
    AnalyticsReport() {
        System.out.println("Analytics report created.");
    }
}

public class Main {
    public static void main(String[] args) {
        SearchEngineFacade searchEngine = new SearchEngineFacade();

        // Example: Update the index
        searchEngine.updateIndex(Arrays.asList(new Product("Laptop"), new Product("Smartphone")));

        // Example: Perform a search
        List<Product> results = searchEngine.search("best smartphone", "user123");

        System.out.println("Search results:");
        for (Product product : results) {
            System.out.println(product.getName());
        }

        // Example: Get analytics report
        AnalyticsReport report = searchEngine.getAnalyticsReport();
    }
}
