package eventhubs.latency.bare;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        Consumer consumer = new Consumer();
        consumer.receiveFromPartition();
        Producer producer = new Producer();
        producer.sendToPartitionSync();
        System.in.read();
    }
}
