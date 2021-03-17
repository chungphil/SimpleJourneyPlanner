import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class JourneyPlan extends GUI {
// Instance fields

    private HashMap<String, Stop> stops = new HashMap<>();
    private HashMap<String, Trip> trips = new HashMap<>();
    private HashSet<Connection> connections = new HashSet<>();
    private Trie stopTrie = new Trie();
    public static Location origin = new Location(0,0);
    public static double scale=8;
    public static final int stopSize = 3;
    private static double ZoomFactor = 1.2;
    private Stop selectedStop = null;
    private List<Stop> searchedStop = new ArrayList<>();
    private HashSet<Trip> searchedTrip = new HashSet<>();
    private HashSet<Connection> selectedConnections = new HashSet<>();

    @Override
    protected void redraw(Graphics g) {
        for (Stop s: stops.values()) {
            s.draw(g);
        }
        for (Connection c:connections){
            c.draw(g);}

        if(selectedStop != null) {
            selectedStop.highlightDraw(g);
        }
        if(searchedStop != null) {
            searchedStop.forEach((n)->
            n.highlightDraw(g));
        }
        if(selectedConnections!= null){
            for(Connection c: selectedConnections){
                c.highlightDraw(g);
            }
        }
        selectedConnections.clear();
        searchedStop.clear();
        searchedTrip.clear();
        selectedStop = null;
    }

    @Override
    protected void onClick(MouseEvent e) {
        getTextOutputArea().setText("");
        selectedStop = findStop(e);
        if(selectedStop!=null) {
            //Prints Stop Name
            getTextOutputArea().append("Stop Name: " + selectedStop.getName());
            getTextOutputArea().append("\nTrip ID: ");
            for (Trip t : selectedStop.getAllTrips()) {
                getTextOutputArea().append(" " + t.getTripId()+" ");
            }
        }

    }

    @Override
    protected void onSearch() {
        getTextOutputArea().setText("");

        String searchTerm = getSearchBox().getText();//string of search term
        searchedStop = stopTrie.getAll(searchTerm);//stop object based on  stop id
        if(searchedStop!= null) {
            searchedStop.forEach((n) ->
                    getTextOutputArea().append("\nStop Name: " + n.getName()));
            searchedStop.forEach((n) ->
                    searchedTrip.addAll(n.getAllTrips())
            );

            for (Trip t : searchedTrip) {//This is hashset of all trips.
                selectedConnections.addAll(t.getTripConnections());

            }
        }else{
            getTextOutputArea().append("Stop Name not recognised");
        }

    }

    @Override
    protected void onMove(Move m) {
        //System.out.println(m);
        switch(m){
            case EAST -> origin = origin.moveBy(5,0);
            case WEST -> origin =origin.moveBy(-5,0);
            case NORTH -> origin =origin.moveBy(0,5);
            case SOUTH -> origin =origin.moveBy(0,-5);
            case ZOOM_IN -> scale *= ZoomFactor;
            case ZOOM_OUT -> scale /= ZoomFactor;

        }
    }

    @Override
    protected void onLoad(File stopFile, File tripFile) {
        loadStops(stopFile);
        loadTrips(tripFile);

        System.out.println("Stops: " + stops.size());
        System.out.println("Trips: " + trips.size());


    }

// Stop loading method to be used in onLoad()
    public void loadStops(File stopFile) {

        try {

            Scanner scan = new Scanner(stopFile);

            // This is to skip the first line of datafile which are headers
            scan.nextLine();
            // Read the line and store in object
            while (scan.hasNextLine()) {

                String data = scan.nextLine();
                String[] tokens = data.split("\t");

                // Make a new object for every line

                String stop_id = tokens[0];
                String stop_name = tokens[1];
                double stop_lat = Double.parseDouble(tokens[2]);
                double stop_lon = Double.parseDouble(tokens[3]);
                Location stop_loc = Location.newFromLatLon(stop_lat, stop_lon);

                Stop s = new Stop(stop_id, stop_name, stop_loc);
                stops.put(stop_id, s); // insert into hashmap
                stopTrie.add(stop_name,s);


            }

            scan.close();

        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file");
            e.printStackTrace();
        }
    }

    //trip loading method to be used in onLoad()
    public void loadTrips(File tripFile){
        try {
            Scanner scan = new Scanner(tripFile);

            // This is to skip the first line of datafile which are headers
            scan.nextLine();
            // Read the line and store in object
            while (scan.hasNextLine()) {

                String data = scan.nextLine();
                String[] tokens = data.split("\t");

                // Make a new object for every line
                //For each new trip obj, we use the Stop Hash list(String, stop ob), find the corresponding stop object and get from list.
                Trip t = new Trip(tokens[0]);//name at [0] of list
                // for loop to add all stops in the list.
                for(int i = 1; i< tokens.length; i++){
                    Stop stop = stops.get(tokens[i]);
                    t.addStop(stop);

                    if(i != 1){
                        Stop endStop = stops.get(tokens[i]);
                        Stop startStop = stops.get(tokens[i-1]);
                        Connection c = new Connection(startStop,endStop,t);
                        connections.add(c);
                        startStop.addOutConnection(c);
                        endStop.addInConnection(c);
                        t.addConnections(c);
                    }
                }
                trips.put(tokens[0], t); // insert into hashmap

            }
            scan.close();

        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file");
            e.printStackTrace();
        }
    }

    public void loadConnections(File tripFile){
        /** Using the Trips hashlist, create all the new connections needed. */
        try {
            Scanner scan = new Scanner(tripFile);

            // This is to skip the first line of datafile which are headers
            scan.nextLine();
            // Read the line and store in object
            while (scan.hasNextLine()) {

                String data = scan.nextLine();
                String[] tokens = data.split("\t");

                // Make a new object for every line
                //For each new trip obj, we use the Stop Hash list(String, stop ob), find the corresponding stop object and get from list.
                Trip connectionTrip = trips.get(tokens[0]);//name at [0] of list
                // for loop to add all stops in the list.
                for(int i = 2; i< tokens.length; i++){
                    Stop endStop = stops.get(tokens[i]);
                    Stop startStop = stops.get(tokens[i-1]);
                    Connection c = new Connection(startStop,endStop,connectionTrip);
                    connections.add(c);
                    //Add each connection to Stop array list incoming and outgoing
                    startStop.addOutConnection(c);
                    endStop.addInConnection(c);

                }
                //trips.put(tokens[0], t); // insert into hashmap


            }
            scan.close();

        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file");
            e.printStackTrace();
        }
    }

    public Stop findStop(MouseEvent e){
        Point mousePoint = new Point(e.getX(),e.getY());
        Location mouseLoc = Location.newFromPoint(mousePoint,origin,scale);
        double shortestDistance = Double.MAX_VALUE;
        //Loop finds the distance of the closest stop to mouseclick location.
        for(Stop s: stops.values()){
            double mouseStop = mouseLoc.distance(s.getLoc());
            if(mouseStop < shortestDistance){
                shortestDistance = mouseStop;
            }
        }
        /** using the shortestDistance value and the isClose() method, the below loop finds Stop which is closest to mouseclick as well as
        being within a certain minimum distance away from mouseclick.
         */
        for(Stop s: stops.values()){
            boolean nearMouse = mouseLoc.isClose(s.getLoc(),2);
            //System.out.println(s.getLoc());
            double mouseStop = mouseLoc.distance(s.getLoc());
            if(mouseStop<= shortestDistance && nearMouse){
                return s;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        new JourneyPlan();
    }
}


