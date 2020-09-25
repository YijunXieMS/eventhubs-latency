package eventhubs.latency.bare.app;

import eventhubs.latency.bare.Producer;

public class ProducerApplication {
    public static void main(String[] args) throws InterruptedException {
        Producer producer = new Producer();
        producer.sendToPartitionSyncWithPrompt();
    }
}
