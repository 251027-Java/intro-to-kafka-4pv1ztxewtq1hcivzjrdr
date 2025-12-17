package dev.revature.kafkachat;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Scanner;

public class ChatApp {
    static void main() {
        // TODO: Initialize Kafka Producer settings

        // TODO: Start a Thread for the Consumer (Polling loop)

        // TODO: Main loop reading Scanner(System.in) and sending messages
        new ChatApp().run();
    }

    void run() {


        try (Scanner in = new Scanner(System.in);
             Producer<String, String> producer = new KafkaProducer<>(createProducerProperties())
        ) {
            String message = in.nextLine();
            var record = new ProducerRecord<>("asd", message);

        }
    }

    Properties createProducerProperties() {
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("key-serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value-serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return props;
    }

    Properties createConsumerProperties() {
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("key-deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value-deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("group.id", "test");

        return props;
    }
}
