import java.util.ArrayList;

public class OneLaneBridge extends Bridge {

    protected int carLimit; //Limit of cars 
    private Object bridgeInaccesible = new Object(); //Condition var 

    public OneLaneBridge(int limit) { //Constructor that takes limit of cars allowed on bridge 
        carLimit = limit; 
    } 

    // When a car arrives, make sure it is going in the right direction and not going to overload the bridge (wait if it does either),
    // Then set an entry time and allow the car onto the bridge if it doesn't satisfy the wait conditions.
    public void arrive(Car car) throws InterruptedException {
        synchronized(bridgeInaccesible) {
            while(this.direction != car.getDirection() || bridge.size() >= carLimit) {
                try{
                    bridgeInaccesible.wait();
                } catch(InterruptedException e) {
                    e.printStackTrace(); 
                }
                if(bridge.size() == 0) {
                    this.direction = car.getDirection(); 
                }
            }
            car.setEntryTime(currentTime);
            bridge.add(car);
            System.out.println("Bridge " + "(dir=" + this.direction +"): " + bridge.toString()); 
            currentTime++;
        }
    }
    
    // When a car exits, make sure the car is at the front of the bridge, and then notify cars that are waiting that
    // they are allowed onto the bridge. 
    public void exit(Car car) throws InterruptedException {
        synchronized(bridgeInaccesible) {
            while(car != bridge.get(0)) {
                try {
                    bridgeInaccesible.wait();
                } catch(InterruptedException e) {
                    e.printStackTrace(); 
                }
            }
            bridge.remove(car);
            System.out.println("Bridge " + "(dir=" + this.direction +"): " + bridge.toString());
            bridgeInaccesible.notifyAll();
        }
    }
}