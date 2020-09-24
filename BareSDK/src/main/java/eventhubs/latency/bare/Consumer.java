package eventhubs.latency.bare;

import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubConsumerAsyncClient;
import com.azure.messaging.eventhubs.models.EventPosition;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Consumer {
    public void receiveFromPartition() {
        EventHubConsumerAsyncClient consumer = new EventHubClientBuilder()
                .connectionString(Configuration.getConfiguration("eventhub.connection_string"))
                .consumerGroup(Configuration.getConfiguration("eventhub.consumer_group"))
                .buildAsyncConsumerClient();
        consumer.receiveFromPartition(Configuration.getConfiguration("eventhub.partition"), EventPosition.latest())
                .map(partitionEvent -> {
                    byte[] data = partitionEvent.getData().getBody();
                    long eventTimeMillis = ByteBuffer.wrap(data).getLong();
                    return System.currentTimeMillis() - eventTimeMillis;
                }).subscribe(System.out::println);
    }
}
