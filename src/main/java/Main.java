import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        String departureCity = "Vladivostok";
        String arrivalCity = "Tel Aviv";
        int percentile = 90;

        System.out.printf("Departure City: %s, Arrival City: %s, Percentile: %d " + System.lineSeparator() + " %n",
                departureCity, arrivalCity, percentile);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        DataStorage dataStorage = mapper.readValue(new File("tickets.json"), DataStorage.class);

        TicketDetails details = new TicketDetails(dataStorage);

        long averageMinutes = details.getAverageFlightTimeBetweenTwoCities().toMinutes();
        System.out.printf("Average flight time between %s and %s: %dh %dm%n",
                departureCity, arrivalCity, averageMinutes / 60, averageMinutes % 60);

        long percentileMinutes = details.getFlightTimePercentileBetweenTwoCities(percentile).toMinutes();
        System.out.printf("%dth percentile of the flight time between %s and %s: %dh %dm%n",
                percentile, departureCity, arrivalCity, percentileMinutes / 60, percentileMinutes % 60);
    }
}
