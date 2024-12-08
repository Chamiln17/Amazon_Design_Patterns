import time
from collections import defaultdict
from typing import List


RATE_LIMIT = 10

# Interface for the service
class SearchService:
    def search(self, query: str, user_id: str) -> List[str]:
        raise NotImplementedError("This method should be overridden.")

# Concrete implementation of the search service
class AmazonSearchService(SearchService):
    def search(self, query: str, user_id: str) -> List[str]:
        print(f"Executing API call for user '{user_id}' and query '{query}'.")
        # Simulate search results from Amazon
        results = [f"Product {i} for {query}" for i in range(1, 6)]
        return results

# Proxy class that implements bot detection
class BotDetectionProxy(SearchService):
    def __init__(self, service: SearchService):
        self.service = service
        self.user_requests = defaultdict(list)  # Keeps track of request timestamps per user

    def _is_bot(self, user_id: str) -> bool:
        """Check if a user exceeds 10 requests per second."""
        current_time = time.time()
        request_times = self.user_requests[user_id]
        
        # Filter timestamps to keep only the ones within the last second
        self.user_requests[user_id] = [
            timestamp for timestamp in request_times if current_time - timestamp < 1
        ]
        
        # Add the current request timestamp
        self.user_requests[user_id].append(current_time)
        
        # If more than 10 requests in the last second, it's a bot
        return len(self.user_requests[user_id]) > RATE_LIMIT    

    def search(self, query: str, user_id: str) -> List[str]:
        if self._is_bot(user_id):
            print(f"Request blocked for user '{user_id}' due to bot-like behavior.")
            return []
        
        print(f"Request allowed for user '{user_id}'.")
        return self.service.search(query, user_id)

# Client code
class AmazonSearchApp:
    def __init__(self, service: SearchService):
        self.service = service

    def perform_search(self, query: str, user_id: str):
        results = self.service.search(query, user_id)
        if results:
            print(f"Results for user '{user_id}' and query '{query}': {results} \n")
        

# Application setup
if __name__ == "__main__":
    # Create the actual service and the proxy
    amazon_service = AmazonSearchService()
    bot_detection_proxy = BotDetectionProxy(amazon_service)

    # Client uses the bot detection proxy
    app = AmazonSearchApp(bot_detection_proxy)

    # Simulate user requests
    bot_id = "bot123"
    for _ in range(20):  # Simulating 12 rapid requests
        app.perform_search("ps5", bot_id)
        time.sleep(0.05)  # 50 ms delay between requests

    print("\n")
    user_id = "user123"
    app.perform_search("xbox", user_id)
