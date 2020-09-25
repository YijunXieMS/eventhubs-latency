package eventhubs.latency.bare.app;

import eventhubs.latency.bare.EventProcessor;
import eventhubs.latency.bare.Tracker;

import java.io.IOException;

public class EventProcessorApplication {
    public static void main(String[] args) throws IOException {
        System.out.println("Start of EventProcessorApplication");
        EventProcessor eventProcessor = new EventProcessor();
        //Producer producer = new Producer();
        try {
            eventProcessor.startEventProcessor();
            //producer.sendToPartitionSyncAndCollectData();
            System.in.read();
            Tracker.print();
        } finally {
            eventProcessor.stopEventProcessor();
        }
        System.out.println("End of EventProcessorApplication");
    }
}
