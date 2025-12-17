package dev.revature.kafkachat;

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
        try (Scanner in = new Scanner(System.in)) {
            String input = in.nextLine();
        }
    }

    

    Properties createProperties() {
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }
}
