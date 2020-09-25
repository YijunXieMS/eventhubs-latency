package eventhubs.latency.bare;

import com.azure.messaging.eventhubs.EventProcessorClient;
import com.azure.messaging.eventhubs.EventProcessorClientBuilder;

import java.nio.ByteBuffer;
import java.time.Duration;

public class EventProcessor {
    EventProcessorClient eventProcessorClient = new EventProcessorClientBuilder()
            .connectionString(Configuration.getConfiguration("eventhub.connection_string"))
            .consumerGroup(Configuration.getConfiguration("eventhub.consumer_group"))
            .checkpointStore(new SampleCheckpointStore())
            .loadBalancingUpdateInterval(Duration.ofSeconds(10))
            .processEvent(eventContext -> {
                byte[] data = eventContext.getEventData().getBody();
                long eventTimeMillis = ByteBuffer.wrap(data).getLong();
                Tracker.add(System.currentTimeMillis() - eventTimeMillis);
            })
            .processError(errorContext -> errorContext.getThrowable().printStackTrace())
            .buildEventProcessorClient();

    public void startEventProcessor() {
        eventProcessorClient.start();
    }

    public void stopEventProcessor() {
        eventProcessorClient.stop();
    }
}
