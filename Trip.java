import java.util.HashSet;

public class Trip {
    //Instance Fields

    private String tripId;
    private HashSet<Stop> tripStops = new HashSet<>();
    private HashSet<Connection> tripConnections = new HashSet<>();

    public Trip(String tripId) {
        this.tripId = tripId;
    }

    public void addStop(Stop s){
        tripStops.add(s);
    }

    public void addConnections(Connection c){
        tripConnections.add(c);
    }

    public HashSet<Connection> getTripConnections(){
        return tripConnections;
    }

    public String getTripId() {
        return tripId;
    }


}
