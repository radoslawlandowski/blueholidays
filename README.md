# Preface

The requirements defined in Holiday Information Service.pdf will be called: "Business requirements"

# Assumptions 

1. Deliver all business requirements defined by the customer in Holiday Information Service.pdf

2. Development:
    - Code stored in Github

3. Application:
    - Language: Java 8 
        - well known and well supported in AWS Lambda
        - thrown away Java 11 due to no official support in AWS Lambda yet
    - Web framework: Spring Boot
        - Production ready
        - Easy to setup (Spring initializer io), out of the box solution 
        - Biggest knowledge and acquaintance
    - External Holiday API:
        - Must: be stable, used by other companies and 'production-working' (business requirement)
        - Must: allow to pass country codes and date (business requirement)
        - Must: require an API key (business requirement)
        - Must: allow to get 'upcoming' holidays past the given date
        - Optional: allow to send multiple country codes at ones to get batch results (less requests in consequence)
    - Production readiness
        - All Clean Code practices: Testing, logging, maintainability etc...
        - Optional: fulfil all 12 factor app requirements

# Road map

1. Phase 1 is about providing the functionality defined in Customer's .pdf
2. Phases 2+ are about showing my skills as a Fullstack Software Engineer including automation and AWS deployment

Development is done in feature branches, branch naming convention:

`<phase_number>-<story_number>-<short_title>`

## Phase 1 - Provide all business requirements

1. Setup Hello World Spring Boot Web API
    - hello endpoint
    - setup unit tests, basic integration test, API documentation generation
    
2. Pick and integrate Holiday API
    - implement logic for getting upcoming holidays
    - figure out validating input country codes
    - make api configurable via config and env variables
    - Provide proper validation messages/error messages
    - Provide wrapping external API errors
    - logging (maybe think of further AWS integration? Is there anything to integrate? SL4J as facade?)

## Phase 2 - Development Setup

1. Dockerize
2. Provide Mock Holiday-Api service and Dockerize it to utilise in integration tests

## Phase 3 - Deployment

1. Deploy to AWS with Serverless framework and AWS Lambda 

## Phase 4 - Automation

1. Setup Jenkins locally in Docker to deploy the application
2. Setup Jenkins in AWS
    - Provide API Key from KMS?
