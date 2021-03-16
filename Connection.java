import java.awt.*;

public class Connection {
    //instance field
    private Stop startStop;
    private Stop endStop;
    private Trip connectionTrip;

    public Connection(Stop startStop, Stop endStop, Trip connectionTrip) {
        this.startStop = startStop;
        this.endStop = endStop;
        this.connectionTrip = connectionTrip;
    }

    public Stop getStartStop() {
        return startStop;
    }

    public Stop getEndStop() {
        return endStop;
    }

    public Trip getConnectionTrip() {
        return connectionTrip;
    }

/*    @Override
    public String toString() {
        return "Connection{" +
                "startStop=" + startStop +
                ", endStop=" + endStop +
                ", connectionTrip=" + connectionTrip +
                '}';
    }*/

    public void draw(Graphics g){
        int startPointX =(int)startStop.getPoint().getX();
        int startPointY =(int)startStop.getPoint().getY();
        int endPointX = (int)endStop.getPoint().getX();
        int endPointY = (int)endStop.getPoint().getY();
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(startPointX,startPointY,endPointX,endPointY);
    }

    public void highlightDraw(Graphics g){
        int startPointX =(int)startStop.getPoint().getX();
        int startPointY =(int)startStop.getPoint().getY();
        int endPointX = (int)endStop.getPoint().getX();
        int endPointY = (int)endStop.getPoint().getY();
        g.setColor(Color.PINK);
        g.drawLine(startPointX,startPointY,endPointX,endPointY);
    }
}
