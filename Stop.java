import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Stop
{
    // instance variables - replace the example below with your own
    private String stopId, stopName;
    private Location stopLoc;
    private ArrayList<Connection> incomingConnection = new ArrayList<>();
    private ArrayList<Connection> outgoingConnection = new ArrayList<>();
    private HashSet<Trip> allTrips = new HashSet<>();
    
    public Stop(String id, String name, Location loc){
        stopId = id;
        stopName = name;
        stopLoc = loc;
    }

   @Override
    public String toString() {
        return "Stop{" +
                "stopId='" + stopId + '\'' +
                ", stopName='" + stopName + '\'' +
                ", stopLoc=" + stopLoc +
                ", incomingConnection=" + incomingConnection +
                ", outgoingConnection=" + outgoingConnection +
                '}';
    }

    public HashSet<Trip> getAllTrips() {
        for (Connection c: incomingConnection) {
            allTrips.add(c.getConnectionTrip());
        }
        for (Connection c: outgoingConnection) {
            allTrips.add(c.getConnectionTrip());
        }

        return allTrips;
    }

    
    public String getName(){
        return stopName;
    }
    
    public Location getLoc(){ return stopLoc; }
    // method to creating a point for the stop object for use by connections.
    public Point getPoint(){
        Point point = stopLoc.asPoint(JourneyPlan.origin,JourneyPlan.scale);
        return point;
    }

    public void addInConnection(Connection inConnection) {
        incomingConnection.add(inConnection);
    }


    public void addOutConnection(Connection outConnection) {
        //System.out.println(outConnection);
        outgoingConnection.add(outConnection);
        //System.out.println(outgoingConnection);
    }

    public void draw(Graphics g){
        Point p = stopLoc.asPoint(JourneyPlan.origin,JourneyPlan.scale);
        g.setColor(Color.DARK_GRAY);
        g.drawOval((int)p.getX(),(int)p.getY(), JourneyPlan.stopSize,JourneyPlan.stopSize);
        g.fillOval((int)p.getX(),(int)p.getY(), JourneyPlan.stopSize,JourneyPlan.stopSize);
        //System.out.println(p);

    }
    public void highlightDraw(Graphics g){
        Point p = stopLoc.asPoint(JourneyPlan.origin,JourneyPlan.scale);
        g.setColor(Color.CYAN);
        g.drawOval((int)p.getX(),(int)p.getY(), JourneyPlan.stopSize+1,JourneyPlan.stopSize+1);
        g.fillOval((int)p.getX(),(int)p.getY(), JourneyPlan.stopSize+1,JourneyPlan.stopSize+1);
    }
}
