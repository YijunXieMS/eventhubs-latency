package eventhubs.latency.bare;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Tracker {
    private static List<Long> values = new ArrayList<>(Integer.parseInt(Configuration.getConfiguration("eventhub.test_number_of_events")));

    public static synchronized void add(Long value) {
        values.add(value);
    }

    public static List<Long> getValues() {
        return values;
    }

    public static void print() {
        System.out.println("Number of events: " + Tracker.getValues().size());
        long totalLatency = Tracker.getValues().stream().reduce(0L, Long::sum);
        System.out.println("Total latency in milliseconds: " + totalLatency);
        System.out.println("Average latency in milliseconds: " + totalLatency / (float) Tracker.getValues().size());
        System.out.println(Tracker.getValues());
    }
}
