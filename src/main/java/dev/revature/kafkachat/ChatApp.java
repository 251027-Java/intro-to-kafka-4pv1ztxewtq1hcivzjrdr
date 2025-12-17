package dev.revature.kafkachat;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatApp {
    static void main() {
        Logger logger = ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("root");
        logger.setLevel(Level.OFF);

        new ChatApp().run();
    }

    static final String topic = "message";

    void run() {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> {
                try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(createConsumerProperties())) {
                    consumer.subscribe(Collections.singleton(topic));

                    while (true) {
                        ConsumerRecords<String, String> messages = consumer.poll(Duration.ofMillis(100));
                        for (var message : messages) {
                            System.out.printf("Incoming: %s\n", message.value());
                        }
                    }
                }
            });

            System.out.println("Type messages to chat");

            try (Scanner in = new Scanner(System.in); Producer<String, String> producer = new KafkaProducer<>(createProducerProperties())) {
                while (in.hasNextLine()) {
                    String message = in.nextLine().trim();

                    if (!message.isEmpty()) {
                        ProducerRecord<String, String> asd = new ProducerRecord<>(topic, message);
                        producer.send(asd);
                    }
                }
            }
        }
    }

    Properties createProducerProperties() {
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return props;
    }

    Properties createConsumerProperties() {
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("group.id", "test");

        return props;
    }
}
