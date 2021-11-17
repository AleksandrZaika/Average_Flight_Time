import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TicketDetails {

    private List<Ticket> tickets;

    public TicketDetails(DataStorage dataStorage) {
        this(dataStorage.getTickets());
    }

    public TicketDetails(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    private Duration flightTimeCalculation(Ticket ticket) {
        LocalDateTime departure = LocalDateTime.of(ticket.getDepartureDate(), ticket.getDepartureTime());
        LocalDateTime arrival = LocalDateTime.of(ticket.getArrivalDate(), ticket.getArrivalTime());
        return Duration.between(departure, arrival);
    }

    public Duration getAverageFlightTimeBetweenTwoCities() {
        Duration average = Duration.ZERO;
        int i = 0;
        for (Ticket ticket : tickets) {
            average = average.plus(flightTimeCalculation(ticket));
            i++;
        }
        return average.dividedBy(i);
    }

    public Duration getFlightTimePercentileBetweenTwoCities(int percentile) {
        List<Ticket> flightTime = tickets.stream()
                .sorted((o1, o2) -> (int) (flightTimeCalculation(o1).toMinutes() - flightTimeCalculation(o2).toMinutes()))
                .collect(Collectors.toList());
        double i = (double) percentile / 100 * flightTime.size();
        int j = (int) (Math.ceil(i) - 1);

        return flightTimeCalculation(flightTime.get(j));
    }


}
