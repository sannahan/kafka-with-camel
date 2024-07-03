# Camel Kafka Connector

A project to demonstrate how to use a Camel Kafka Connector sink connector to read data from a Kafka topic and write it to a MySQL database.

## Instructions for local use

### Set up a MySQL database

Run a MySQL container with Docker:
```bash
docker run --name mysql-test -e MYSQL_ROOT_PASSWORD=my-secret-pw -e MYSQL_DATABASE=test -e MYSQL_USER=testuser -e MYSQL_PASSWORD=testpassword -d -p 3310:3306 mysql:latest
```

Connect to the MySQL database:
```bash
mysql -h 127.0.0.1 -P 3310 -u testuser -p test
```

Create a table:
```sql
CREATE TABLE info (info_text varchar(20), info_type varchar(20));
```

### Set up a Kafka topic

[Follow the instructions here to get Kafka, start the Kafka environment and create a topic.](https://kafka.apache.org/documentation.html#quickstart)

When following the instructions, I recommend using KRaft instead of Zookeeper because Zookeeper is deprecated.

Name the topic `info`.

### Set up Kafka Connect

Download [the Camel MySQL sink Kafka connector](https://camel.apache.org/camel-kafka-connector/4.0.x/reference/index.html) and extract it in your preferred directory.

Download [the JDBC driver for MySQL](https://www.mysql.com/products/connector/) and extract its .jar file in the camel-mysql-sink-kafka-connector
directory you extracted above.

In path_to_your_kafka_download_directory/config/connect-standalone.properties, add plugin.path=path_to_the_directory_you_used_above

My directory structure looks like this:

- connectors/ (this is the directory that you'll refer to with plugin.path without the final /)
    - camel-mysql-sink-kafka-connector/
        - camel-mysql-sink-kafka-connector-4.4.2.jar
        - mysql-connector-j-9.0.0.jar
        - other jar files
    - docs/
        - examples/
            - CamelMysqlsinkSinkConnector.properties


### Run Kafka Connect in standalone mode

The config file for the connector is created automatically in your_plugin_path/docs/examples (replace your_plugin_path with the path to the directory you used above).
The config file includes the mandatory properties that you need to fill in. In addition, since our MySQL database runs in port 3310, you need to add `camel.kamelet.mysql-sink.serverPort=3310`

To run Kafka Connect, run
```bash
path_to_your_kafka_download_directory/bin/connect-standalone.sh config/connect-standalone.properties your_plugin_path/docs/examples/CamelMysqlsinkSinkConnector.properties
```

(If the previous command doesn't work, [check that your plugin.path directory structure is correct](https://camel.apache.org/camel-kafka-connector/4.0.x/contributor-guide/troubleshooting.html#connector-not-loaded).
In addition, check that you are using the correct version of Java to run Kafka Connect. For example, the connector camel-mysql-sink-kafka-connector-4-4-2 requires Java 17.)

To test the connector, run a Kafka producer:
```bash
path_to_your_kafka_download_directory/bin/kafka-console-producer.sh --topic info --bootstrap-server localhost:9092
```

and write the following record to the topic:

```json
{"info_text": "Testing", "info_type": "Camel Kafka Connector"}
```

After this, your info table should look like this!

| info_text  | info_type |
|------------|-----------|
|  Testing | Camel Kafka Connector     |

You can keep adding records and they will be added to the table, too!