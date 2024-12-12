## Build the Docker Image:

###Run the following command in the directory containing the Dockerfile:

    docker build -t currency-conversion-app .

## Run the Docker Container:

###Start a container from the built image:
    
    docker run -p 8080:8080 currency-conversion-app


## TODO
* Implement rate limiting - Implement rate limiting per user or IP to prevent abuse of the API.
* Historical Exchange Rates:
  Allow users to query exchange rates for a specific date using data from your database or external APIs.
*  User Management and Authentication
  * Role-Based Access Control (RBAC):
     Admin users can update exchange rates, view system logs, or manage cache.
     Regular users can only perform currency conversion.
  * OAuth2/OpenID Connect:
     Allow users to log in using third-party providers like Google or GitHub.
  * API Key-Based Authentication:
     Secure public APIs by requiring an API key.

* Improved Monitoring and Logging
  Enhanced Logging:
  Integrate structured logging with tools like ELK Stack (Elasticsearch, Logstash, Kibana) or Grafana Loki.
  Application Metrics:
  Expose metrics using Spring Boot Actuator and monitor with Prometheus and Grafana.
  Alerts:
  Set up alerts for downtime, cache failures, or high latency.

* API Features
  Pagination and Filtering:
  Allow clients to fetch large sets of exchange rates with pagination and filters.
  Versioning:
  Implement API versioning to support backward compatibility.
  API Documentation:
  Improve API documentation using Swagger/OpenAPI and include example requests/responses.

* Advanced Security
  Input Validation:
  Ensure strict input validation for API requests to prevent injection attacks.
  Secure Sensitive Data:
  Encrypt sensitive data like API keys or user passwords using tools like Jasypt or Vault.
  Audit Logs:
  Record all sensitive actions (e.g., exchange rate updates) for auditing.

* Integration with External Services
  Payment Gateway Integration:
  Enable users to make payments or transfers in different currencies.
  Banking APIs:
  Integrate with open banking APIs to fetch live exchange rates or initiate transactions.
  Forex Market Updates:
  Provide real-time Forex market updates for advanced users.

* Write tests
