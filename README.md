# eventhubs-latency

## Configuration
Add or edit file BareSDK/src/main/resources/application.properties.
```properties
eventhub.connection_string=
eventhub.consumer_group=$Default
eventhub.partition=0
eventhub.test_number_of_events=1000
```
Add your own eventhub.connection_string to the above mentioned file like this.
```text
eventhub.connection_string=Endpoint=sb://anamespace.servicebus.windows.net/;SharedAccessKeyName=SomeKey;SharedAccessKey=a4xbgNrqFT3tlN5Ak1jWvhSXmnuClOjkNMTQ81posWA=;EntityPath=eventhubname
```
Pay attention that the connection string has the EntityPath section.


To receive events with Spring Stream, [Create Azure Credential File](https://docs.microsoft.com/en-us/azure/developer/java/spring-framework/configure-spring-cloud-stream-binder-java-app-azure-event-hub#create-an-azure-credential-file)
and [Configure the Spring Boot app](https://docs.microsoft.com/en-us/azure/developer/java/spring-framework/configure-spring-cloud-stream-binder-java-app-azure-event-hub#create-an-azure-credential-file)
## Run the latency test
Both tests use the same code to send events.
The difference is one use the `EventHubConsumerAsyncClient` to receive whereas
the other one uses `EventProcessorClient` to receive.

Go to folder `BareSDK`. Start one of the three receiving application
Receive with `EventHubConsumerAsyncClient` 
```shell script
mvn compile exec:java -Dexec.mainClass="eventhubs.latency.bare.app.ConsumerApplication"
```

Receive with the `EventProcessorClient`
```shell script
mvn compile exec:java -Dexec.mainClass="eventhubs.latency.bare.app.EventProcessorApplication"
```

Receive with Spring Stream
```shell script
mvn spring-boot:run
```

Wait a few seconds until the receiving app starts. Then use the following command to send 1000 events
until you see "Total send time in milliseconds: ". Then go back to the receiving app and press ENTER
to see the test result.
```shell script
mvn compile exec:java -Dexec.mainClass="eventhubs.latency.bare.app.ProducerApplication"
```
## Test Result
Refer to [TestResult.md](TestResult.md)
