/**
 * Runs all threads
 */

public class BridgeRunner {

	public static void main(String[] args) {

		// Input Handling 
		if(args.length == 0) {
			System.out.println("Usage: javac BridgeRunner <bridge limit> <num cars>");
			return;  
		}

		int limit = Integer.parseInt(args[0]); 
		int numCars = Integer.parseInt(args[1]);

		if(limit == 0 || numCars == 0) {
			System.out.println("Error: Bridge limit and/or num cars must be positive."); 
			return; 
		} 

		//Instantiate the bridge
		OneLaneBridge oneLaneB = new OneLaneBridge(limit); 
		
		//Allocate space for threads
		Thread[] carThreads = new Thread[numCars];

		//Start then join the threads
		for(int i = 0; i < numCars; i++) {
			carThreads[i] = new Thread(new Car(i, oneLaneB));
			carThreads[i].start(); 
		}

		for(int i = 0; i < numCars; i++) {
			try{
				carThreads[i].join(); 
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("All cars have crossed!!");
	}
}