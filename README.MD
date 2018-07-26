# BPEL-to-BPMN converter

## Overview
The BPEL-to-BPMN converter is a tool to convert a BPEL document to a valid BPMN document. It consists of a RESTfull API, a BPEL parser and a converter. It is based on [BPEL 2.0](http://docs.oasis-open.org/wsbpel/2.0/OS/wsbpel-v2.0-OS.html) and [BPMN 2.0](https://www.omg.org/spec/BPMN).

## Current State
Here is a list of BPEL elements and whether there are implemented or not.
- [ ] Assign (this isn't fully mappable)
- [ ] CorrelationSets
- [x] Empty
- [x] Exit
- [x] Documentation
- [ ] FaultHandlers
- [ ] Flow
- [ ] ForEach
- [x] If
- [ ] Import (implemented but doesn't work due to conflicts between SAXParser and BPEL specification)
- [ ] Invoke
- [x] PartnerLink (without WSDL interfaces + messages)
- [ ] Pick
- [x] Receive
- [x] RepeatUntil (this needs a mapping for expression languages)
- [ ] Reply
- [ ] Rethrow
- [ ] Scope
- [x] Sequence
- [x] Throw
- [ ] Variables
- [x] Wait
- [x] While

## Getting Started
To setup this converter locally and getting ready to develop on the converter you'll need the following:
- [Java 9](https://www.oracle.com/java/java9.html)
- [Maven](https://maven.apache.org/)

Now you are almost ready to go all you have to do is run `mvn clean install`.
To start the application you can run `./start.sh`.

## Dependencies
- [Java 9](https://www.oracle.com/java/java9.html)
- [Maven](https://maven.apache.org/) to manage dependencies
- [Spring Boot](https://spring.io/projects/spring-boot) to boot a Java webservice with RESTful API
- [Camunda BPMN API](https://docs.camunda.org/manual/7.7/user-guide/model-api/bpmn-model-api/) for the BPMN model in Java.