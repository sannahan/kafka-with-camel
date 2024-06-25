# A Camel Route consuming messages from Kafka

This project demonstrates how to use a Kafka endpoint in Camel to consume messages from a Kafka topic.

## Prerequisites

First, set up the Kafka Camel Route Producer project as described in the [README](../kafka-camel-route-producer/README.md).

After the producer has produces a few records to Kafka, you can stop the application. However, leave the Kafka environment and the Apicurio schema registry running.

## Running the application

You can run your application in dev mode that enables live coding using:
```shell script
mvn compile quarkus:dev
```

The route will consume all messages from the Kafka topic `movies`.

Because the Movie schema only exists in the schema registry before running the application, the Apicurio Registry Maven plugin is used to download it from the registry.
Because the producer and the consumer share the same schema, we can be sure that the message includes title and year fields.

(The producer currently produces the same movie to Kafka over and over again, so if you see the same log repeat, things are working as expected.)

