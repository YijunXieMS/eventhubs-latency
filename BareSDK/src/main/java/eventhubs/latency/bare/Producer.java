package eventhubs.latency.bare;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerAsyncClient;
import com.azure.messaging.eventhubs.models.CreateBatchOptions;
import reactor.core.publisher.Flux;

import java.nio.ByteBuffer;
import java.time.Duration;

public class Producer {
    public void sendToPartition() {
        EventHubProducerAsyncClient producer = new EventHubClientBuilder()
                .connectionString(Configuration.getConfiguration("eventhub.connection_string"))
                .buildAsyncProducerClient();
        CreateBatchOptions options = new CreateBatchOptions().setPartitionId(Configuration.getConfiguration("eventhub.partition"));
        Flux.range(0, 1000000)
                .delayElements(Duration.ofSeconds(1))
                .flatMap(i -> producer.createBatch(options))
                .map(eventDataBatch -> {
                    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
                    buffer.putLong(System.currentTimeMillis());
                    eventDataBatch.tryAdd(new EventData(buffer.array()));
                    return eventDataBatch;
                }).flatMap(producer::send).subscribe();
    }
}
