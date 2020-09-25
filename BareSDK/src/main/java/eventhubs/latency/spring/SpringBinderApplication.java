package eventhubs.latency.spring;

import com.microsoft.azure.spring.cloud.autoconfigure.jms.ServiceBusJMSAutoConfiguration;
import eventhubs.latency.bare.Tracker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;

import java.io.IOException;


@SpringBootApplication(exclude = { JmsAutoConfiguration.class, ServiceBusJMSAutoConfiguration.class })
public class SpringBinderApplication {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(SpringBinderApplication.class, args);
		System.in.read();
		Tracker.print();
	}
}
