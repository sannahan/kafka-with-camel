# Kafka Connect

A project to demonstrate how to use a Kafka Connect sink connector to read data from a Kafka topic and write it to a MySQL database.

The project uses the Debezium JDBC connector. There are MySQL connectors that do not require the use of Depezium. However, Depezium is often used with Kafka because it tracks changes following the change data capture design pattern.

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

Download [the Debezium JDBC connector](https://debezium.io/documentation/reference/stable/connectors/jdbc.html#jdbc-deployment) and extract it in your preferred directory.

Download [the JDBC driver for MySQL](https://www.mysql.com/products/connector/) and extract it in the same directory.

In path_to_your_kafka_download_directory/config/connect-standalone.properties, add plugin.path=path_to_the_directory_you_used_above

To run Kafka Connect in standalone mode, run

path_to_your_kafka_download_directory/bin/connect-standalone.sh config/connect-standalone.properties path_to_this_project/connect-debezium-sink.properties

Run a Kafka producer:
```
path_to_your_kafka_download_directory/bin/kafka-console-producer.sh --topic info --bootstrap-server localhost:9092
```

and write the following record to the topic:

```json
{"schema": {"type": "struct", "fields": [{"type": "string", "optional": false, "field": "info_text"}, {"type": "string", "optional": false, "field": "info_type"}]}, "payload": {"info_text": "First test", "info_type": "Kafka"}}
```

After this, your info table should look like this!

| info_text  | info_type |
|------------|-----------|
| First test | Kafka     |

You can keep adding records and they will be added to the table, too!
