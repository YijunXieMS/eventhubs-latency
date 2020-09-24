package eventhubs.latency.bare;


public class ConsumerApplication {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start of ConsumerApplication");
        Consumer consumer = new Consumer();
        Producer producer = new Producer();
        try {
            consumer.receiveFromPartition();
            producer.sendToPartitionSyncAndCollectData();
        } finally {
            consumer.stopReceiving();
        }
        System.out.println("End of ConsumerApplication");
    }
}
