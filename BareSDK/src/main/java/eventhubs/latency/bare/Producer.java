package eventhubs.latency.bare;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerAsyncClient;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.azure.messaging.eventhubs.models.CreateBatchOptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.stream.IntStream;

public class Producer {
    public void sendToPartitionAsync() {
        EventHubProducerAsyncClient producer = new EventHubClientBuilder()
                .connectionString(Configuration.getConfiguration("eventhub.connection_string"))
                .buildAsyncProducerClient();
        CreateBatchOptions options = new CreateBatchOptions().setPartitionId(Configuration.getConfiguration("eventhub.partition"));
        Flux.range(0, 1000)
                //.delayElements(Duration.ofMillis(500))
                .flatMap(i -> producer.createBatch(options))
                .map(eventDataBatch -> {
                    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
                    buffer.putLong(System.currentTimeMillis());
                    eventDataBatch.tryAdd(new EventData(buffer.array()));
                    return eventDataBatch;
                }).flatMap(batch -> {return producer.send(batch);}).subscribe();
    }

    public void sendToPartitionSync() {
        EventHubProducerClient producer = new EventHubClientBuilder()
                .connectionString(Configuration.getConfiguration("eventhub.connection_string"))
                .buildProducerClient();
        CreateBatchOptions options = new CreateBatchOptions().setPartitionId(Configuration.getConfiguration("eventhub.partition"));
        IntStream.range(0, 1000).forEach(
                x -> {
                    EventDataBatch eventDataBatch = producer.createBatch(options);
                    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
                    buffer.putLong(System.currentTimeMillis());
                    eventDataBatch.tryAdd(new EventData(buffer.array()));
                    producer.send(eventDataBatch);
                }
        );
    }
}
