package task;
import org.w3c.dom.ls.LSOutput;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import java.time.Instant;

import static task.AirplaneDestinationSystem.calculateShortestPathAndCost;


class Airplane {
    protected int id;
    protected int destination;
    protected String from;
    protected String to;
    protected int totalCost;
    protected Instant start;
    protected Instant end;
    protected AirplaneState currentState;

    public enum AirplaneState {
        FINAL_APPROACH, TOUCH_DOWN, DECELERATING_ON_RUNWAY, NETWORK_TAXIING, HOLDING, PARKED_AT_GATE, TAXIING_ON_GROUND, TAKING_OFF, MOVING_IN_TERMINAL, LEAVING_TERMINAL
    }

    public Airplane(int id, String to, String from, AirplaneState currentState) {
        this.id = id;
        this.to = to;
        this.from = from;
        this.currentState = currentState;
    }

    public int getId() {
        return id;
    }

    public AirplaneState getCurrentState() {
        return currentState;
    }



    public void transitionToState(AirplaneState newState) {
        System.out.println("Transitioning from " + currentState + " to " + newState);
        currentState = newState;
    }

    public void ShortestPath() {

        start = Instant.now();
        Map<String, Object> result = calculateShortestPathAndCost(to, from);

        if (result != null) {
            totalCost = (int) result.get("ShortestDistance");
            List<String> path = (List<String>) result.get("ShortestPath");

            System.out.println("Shortest Distance: " + totalCost);
            System.out.println("Cost of Travel Rs." + totalCost * 50 + "/");
            System.out.println("Shortest Path: " + String.join(" -> ", path));
        } else {
            System.out.println("Error ");
        }
    }
        public void MovingAirplane(int id){
            System.out.println("Airplane with id " + id+" is moving");
            transitionToState(AirplaneState.MOVING_IN_TERMINAL);
            System.out.println("MovingAirplane: Airplane state transitioned to MOVING_IN_TERMINAL.");
        }
    public void HoldingAirplane(int id){
        System.out.println("Airplane with id " + id+" is holding");
        transitionToState(AirplaneState.HOLDING);
        System.out.println("Holding Airplane: Airplane state transitioned .ON_HOLDING.");

    }
    public void ParkingAirplane(int id){
        System.out.println("Airplane with id " + id+" is parked");
        transitionToState(AirplaneState.PARKED_AT_GATE);
        System.out.println("ParkedAirplane: Airplane state transitioned to PARKED_AT_GATE.");

    }
    public void setId(int id){
        this.id = id;
    }


}


//
//    public static void main(String[] args) {
//        // Create an instance of the Airplane class
//        Airplane airplane = new Airplane(3);
//
//        // Simulate the airplane's movement through different states
//        airplane.transitionToState(AirplaneState.TAXIING_ON_GROUND);
//        airplane.transitionToState(AirplaneState.HOLDING);
//        airplane.transitionToState(AirplaneState.TAKING_OFF);
//        // ... (simulate other states as needed)
//
//        // Get the current state of the airplane
//        AirplaneState currentState = airplane.getCurrentState();
//        System.out.println("Current state: " + currentState);
//        airplane.ShortestPath();
//    }
//}
//
//
//














//import java.util.Scanner;
//public class Main {
//    public static void main(String[] args) throws InterruptedException {
//        TaskEngine taskEngine = new TaskEngine();
//        Scanner scanner = new Scanner(System.in);
//
//        // Simulate adding tasks
//        System.out.println("Enter the number of tasks to simulate:");
//        int numTasks = scanner.nextInt();
//
//        for (int i = 0; i < numTasks; i++) {
//            System.out.println("Enter priority for Task " + (i + 1) + ":");
//            int priority = scanner.nextInt();
//
//            System.out.println("Enter execution time for Task " + (i + 1) + ":");
//            int executionTime = scanner.nextInt();
//
//            taskEngine.addTask(priority, executionTime);
//        }
//
//        // Simulation clock
//        int simulationTime = 0;
//        while (!taskEngine.isEmpty()) {
//            taskEngine.dispatchTasks(simulationTime);
//            simulationTime++;
//
//            Thread.sleep(1000); // Adjust the sleep time as needed
//        }
//    }
//}

/*ublic class Main {
    public static void main(String[] args) throws InterruptedException {
        TaskEngine taskEngine = new TaskEngine();
        TaskManagement taskManagement = new TaskManagement(taskEngine);

        AirTraffic t1 = new AirTraffic("1", "Lahore", "Islamabad", 1, taskManagement);
        AirTraffic t2 = new AirTraffic("2", "Karachi", "Dubai", 2, taskManagement);

        Thread thread1 = new Thread(t1);
        Thread thread2 = new Thread(t2);

        thread1.start();
        thread2.start();

        // Simulation clock
        int simulationTime = 0;
        while (thread1.isAlive() || thread2.isAlive()) {
            taskEngine.dispatchTasks(simulationTime);
            simulationTime++;

            Thread.sleep(100);
        }
    }
}
*/