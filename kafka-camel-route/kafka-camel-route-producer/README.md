# A Camel Route producing messages to Kafka

This project demonstrates how to use a Kafka endpoint in Camel to produce messages to a Kafka topic. 

## Prerequisites

Follow [the quickstart](https://kafka.apache.org/quickstart) to set up a local Kafka environment until you have created a topic `movies`.
(Favor KRaft over Zookeeper as the latter will be deprecated.)

Run the Apicurio schema registry with:
```shell script
docker run -d -p 8080:8080 apicurio/apicurio-registry-mem:latest-release
```

(This version of Apicurio should only be used in local development because it does not persist the schemas.)

Why use a schema registry? A schema registry is used to make sure that both the producer and the consumer of the messages use the same data format.

## Running the application

You can run your application in dev mode that enables live coding using:
```shell script
mvn compile quarkus:dev
```

The route will produce a message to the Kafka topic `movies` every minute.

Because the Movie schema only exists in movie.avsc before running the application, two things happen upon running it:
1. The schema is generated as `target/generated-sources/avsc/org/acme/Movie.java` class which is used in the route
2. The schema is registered in the schema registry because `camel.component.kafka.additional-properties[apicurio.registry.auto-register]` is set to true









