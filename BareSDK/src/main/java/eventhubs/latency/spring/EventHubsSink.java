package eventhubs.latency.spring;

import com.microsoft.azure.spring.integration.core.AzureHeaders;
import com.microsoft.azure.spring.integration.core.api.reactor.Checkpointer;
import eventhubs.latency.bare.Tracker;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Header;

import java.nio.ByteBuffer;

@EnableBinding(Sink.class)
public class EventHubsSink {

   @StreamListener(Sink.INPUT)
   public void handleMessage(byte[] message, @Header(AzureHeaders.CHECKPOINTER) Checkpointer checkpointer) {
      long eventTimeMillis = ByteBuffer.wrap(message).getLong();
      Tracker.add(System.currentTimeMillis() - eventTimeMillis);
      System.out.println(System.currentTimeMillis() - eventTimeMillis);
   }
}
