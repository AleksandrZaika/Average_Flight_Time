import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DataStorage {

    @JsonProperty("tickets")
    private List<Ticket> tickets;

    public List<Ticket> getTickets() {
        return tickets;
    }
}
