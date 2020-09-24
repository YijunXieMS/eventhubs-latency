package eventhubs.latency.bare;

public class EventProcessorApplication {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start of EventProcessorApplication");
        EventProcessor eventProcessor = new EventProcessor();
        Producer producer = new Producer();
        try {
            eventProcessor.startEventProcessor();
            producer.sendToPartitionSyncAndCollectData();
        } finally {
            eventProcessor.stopEventProcessor();
        }
        System.out.println("End of EventProcessorApplication");
    }
}
