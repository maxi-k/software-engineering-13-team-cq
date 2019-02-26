# Backend Development

#### Table of Contents
[Building the Backend](#building-the-backend)

[Important Integration Tests](#important-integration-tests)
* [NotificationRuleController](#notificationrulecontroller)
* [ProcessingPipelineIntegrationTest](#processingPipelineIntegrationTest)

This is a short guide to the development using the backend stack we
decided for. As we already show our backend architecture in the rest of
our documentation, this document will only provide a few additional pointers.

# Building the Backend
To build our application, you have to pay attention to a few details.

* *Generated code*: Both `MapStruct` and `OpenApi Tools` generate additional source code. 
To guarantee that the necessary code got generated, use the following two commands.
* *Running the Tests*: Use `./gradlew check`
* *Running the application*: Use `./gradlew bootRun`


# Important Integration Tests

## NotificationRuleController
This test demonstrates how to use our central API.

## ProcessingPipelineIntegrationTest
This test shows how our data processing pipeline works. As it depends on the API-Mock, it is
disabled by default.
