#!/bin/bash

# Check in which environment the application is started.
if [[ "${BPEL_CONVERTER_ENVIRONMENT}" =~ ^(development|staging|"production")$ ]]; then
    environment="${BPEL_CONVERTER_ENVIRONMENT}"
else
    environment="development"
fi

# Build and start the application.
mvn spring-boot:run -Dspring.profiles.active=$environment
