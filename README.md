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

## Phase 1 - Provide all business requirements (Done)

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

## Phase 2 - Development Setup (Done)

1. Dockerize
2. Provide Mock Holiday-Api service and Dockerize it to utilise in integration tests

## Phase 3 - Deployment (Done)

1. Deploy to AWS with Serverless framework and AWS Lambda 

## Phase 4 - Automation (Not done)

1. Setup Jenkins locally in Docker to deploy the application (Not done)
2. Setup Jenkins in AWS (Not done)
    - Provide API Key from KMS? (Not done)


# How to use:

1. Setup the BH_APP_CAL_API_KEY environment variable
2. sh ./tools/scripts/run.sh (to run as Docker container) or ./gradlew bootRun
3. Import collections and environments from ./tools/postman-collections to your Postman
4. Pick the proper environment in Postman (Docker or Local) and execute request.
5. Optionally you can pick the "Lambda" environment. I deployed the app to AWS. Cold start takes up tp 6 seconds.


# What have I managed to do?

Phases 1 to 3 have been completed, Phase 4 has been abandoned, which means that the application:
- works
- is tested
- is dockerized
- crucial parts are documented
- image is pushed to docker.hub
- is deployable with Serverless framework and works as AWS Lambda
- API key is primarily set via env variable BH_APP_CAL_API_KEY (stands for: "BlueHolidays_Application_Calendarify_Api_Key")
- API key might be overriden in multiple ways due to Spring's configuration options: https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html

# About the code

HolidayInfoObtainer is the main interface that may be implemented by several obtainers. The "obtainers" package contains
implementations of that interface. To distinguish obtainers, they are named as the API's url: calendarific.com -> calendarificcom etc
The CalendarificComObtainer is the main, primary and recommended one. The HolidayApiPlObtainer has been implemented only to show flexibility of the HolidayInfoObtainer interface, it's been
marked as deprecated. It's not recommended to use due to multiple requests that are done.
The app is flexible and is designed so that each obtainer (the current and the future ones) handle configuration for themselves (see CalendarificComConfig).
The concrete obtainer to be used is set in BlueHolidaysController's constructor in @Qualifier annotation. I assumed to search for holidays only within the given year for sake of simplicty.
I did find any chalenge or value in iterating to next years.
The main response object (HolidayInfo) is surrounded with envelope (HolidayInfoResponse). It allows api response to be more consistent when no common holidays are found.
During development I made a few specific decisions. Each of these is commented and prefixed with "!REVIEW" string. Just look for "!REVIEW" in project files
to find them.
I assumed to accept only ISO-3166 Country codes as request params as they are easily obtainable with java.lang package. They are also compliant with main Obtainer: Calendirific.com
It would be perfect to fetch on app startup (and refresh periodically) the list of currently supported countries in Calendarific but they don't expose such endpoint.

# What's left to improve?
- Optimizing Lambda cold starts (described here: https://github.com/awslabs/aws-serverless-java-container/wiki/Quick-start---Spring)
- More unit tests
- For even bigger parametrization the 'obtainer' implementation could be set in config and then dynamically instantiated from Spring context.
Right now implementation is hardcoded in @Qualifier() annotation in controller.
- There should be more than one API KEY to be used: 
    - Separate key for prod (aka for you or for Lambda)
    - Separate key for development
- Implement functional tests against application deployed in AWS
- Some form of manifest file or tagging mechanism to distinguish deployed lambda packages.
