# eventhubs-latency

## Configuration
Add or edit file BareSDK/src/main/resources/application.properties.
```properties
eventhub.connection_string=
eventhub.consumer_group=$Default
eventhub.partition=0
eventhub.test_number_of_events=1000
```
Add your own eventhub.connection_string.
Pay attention that the connection string has the EntityPath section.
```text
eventhub.connection_string=Endpoint=sb://anamespace.servicebus.windows.net/;SharedAccessKeyName=SomeKey;SharedAccessKey=a4xbgNrqFT3tlN5Ak1jWvhSXmnuClOjkNMTQ81posWA=;EntityPath=eventhubname
```

## Run the latency test
Both tests use the same code to send events.
The difference is one use the `EventHubConsumerAsyncClient` to receive whereas
the other one uses `EventProcessorClient` to receive.

Receive with `EventHubConsumerAsyncClient` 
```shell script
mvn compile exec:java -Dexec.mainClass="eventhubs.latency.bare.ConsumerApplication"
```

Receive with the `EventProcessorClient`
```shell script
mvn compile exec:java -Dexec.mainClass="eventhubs.latency.bare.EventProcessorApplication"
```

## Observation
EventProcessorClient adds about 30% overhead.