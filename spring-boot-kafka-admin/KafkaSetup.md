https://kafka.apache.org/quickstart

Step 1: Download the code
> tar -xzf kafka_2.11-2.1.0.tgz
> cd kafka_2.11-2.1.0


cd C:\Software\kafka_2.11-2.1.0\bin\windows


Step 2: Start the server

-- ZooKeeper
zookeeper-server-start.sh config/zookeeper.properties

zookeeper-server-start.bat ../../config/zookeeper.properties

-- kafka
kafka-server-start.sh config/server.properties

kafka-server-start.bat ../../config/server.properties


kafka Missing required configuration “zookeeper.connect” which has no default value



Step 3: Create a topic

bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic kafkatest

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic kafkatest

-- list topic
bin/kafka-topics.sh --list --zookeeper localhost:2181

kafka-topics.bat --list --zookeeper localhost:2181


Step 4: Send some messages

bin/kafka-console-producer.sh --broker-list localhost:9092 --topic kafkatest

kafka-console-producer.bat --broker-list localhost:9092 --topic kafkatest

This is a message
This is another message


Step 5: Start a consumer

bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic kafkatest --from-beginning

kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic kafkatest --from-beginning


.....
