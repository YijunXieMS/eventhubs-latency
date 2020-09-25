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
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Producer {
    public void sendToPartitionAsync() {
        EventHubProducerAsyncClient producer = new EventHubClientBuilder()
                .connectionString(Configuration.getConfiguration("eventhub.connection_string"))
                .buildAsyncProducerClient();
        CreateBatchOptions options = new CreateBatchOptions().setPartitionId(Configuration.getConfiguration("eventhub.partition"));
        Flux.range(0, Integer.parseInt(Configuration.getConfiguration("eventhub.test_number_of_events")))
                //.delayElements(Duration.ofMillis(500))
                .flatMap(i -> producer.createBatch(options))
                .map(eventDataBatch -> {
                    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
                    buffer.putLong(System.currentTimeMillis());
                    eventDataBatch.tryAdd(new EventData(buffer.array()));
                    return eventDataBatch;
                }).flatMap(producer::send).subscribe();
        producer.close();
    }

    public void sendToPartitionSync() {
        EventHubProducerClient producer = new EventHubClientBuilder()
                .connectionString(Configuration.getConfiguration("eventhub.connection_string"))
                .buildProducerClient();
        CreateBatchOptions options = new CreateBatchOptions().setPartitionId(Configuration.getConfiguration("eventhub.partition"));
        IntStream.range(0, Integer.parseInt(Configuration.getConfiguration("eventhub.test_number_of_events"))).forEach(
                x -> {
                    EventDataBatch eventDataBatch = producer.createBatch(options);
                    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
                    buffer.putLong(System.currentTimeMillis());
                    eventDataBatch.tryAdd(new EventData(buffer.array()));
                    producer.send(eventDataBatch);
                }
        );
        producer.close();
    }

    public void sendToPartitionSyncWithPrompt() throws InterruptedException {
        long startSend = System.currentTimeMillis();
        this.sendToPartitionSync();
        long endSend = System.currentTimeMillis();
        long totalSendTime = endSend - startSend;
        TimeUnit.SECONDS.sleep(5);
        System.out.println("Total send time in milliseconds: " + totalSendTime);
    }
}
