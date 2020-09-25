package eventhubs.latency.bare.app;


import eventhubs.latency.bare.Consumer;
import eventhubs.latency.bare.Tracker;

import java.io.IOException;

public class ConsumerApplication {
    public static void main(String[] args) throws IOException {
        System.out.println("Start of ConsumerApplication");
        Consumer consumer = new Consumer();
        //Producer producer = new Producer();
        try {
            consumer.receiveFromPartition();
            //producer.sendToPartitionSyncAndCollectData();
            System.in.read();
            Tracker.print();
        } finally {
            consumer.stopReceiving();
        }

        System.out.println("End of ConsumerApplication");
    }
}
