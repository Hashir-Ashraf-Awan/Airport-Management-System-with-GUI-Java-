package task;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import java.time.Instant;

import static task.AirplaneDestinationSystem.calculateShortestPathAndCost;


class Airplane {

    public enum AirplaneState {
        FINAL_APPROACH, TOUCH_DOWN, DECELERATING_ON_RUNWAY, NETWORK_TAXIING, HOLDING, PARKED_AT_GATE, TAXIING_ON_GROUND, TAKING_OFF, MOVING_IN_TERMINAL, LEAVING_TERMINAL
    }

    private int id;
    private int orientation;
    private int destination;
    private int from;
    private int to;
    private int totalCost;
    private int reachDestination;
    private String path;
    private Instant start;
    private Instant end;
    private AirplaneState currentState;


    public Airplane(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }



    public AirplaneState getCurrentState() {
        return currentState;
    }


//    public void requestPermission(AirTrafficController controller, AirportGroundNetwork groundNetwork) {
//        controller.requestPermission();
//        String taxiway = groundNetwork.getAvailableTaxiway();
//        String gate = groundNetwork.getAvailableGate();
//        String runway = groundNetwork.getAvailableRunway();
//
//        if (taxiway != null && gate != null && runway != null) {
//            System.out.println("Airplane " + id + " is taxiing on " + taxiway + " and heading to " + gate + " for departure on " + runway);
//
//        } else if (taxiway == null) {
//            System.out.println("Taxiway occupied/ Closed by AirTraffic Controller");
//        } else if (gate == null) {
//            System.out.println("Gate closed by AirTraffic Controller");
//        }
//    }

    public void transitionToState(AirplaneState newState) {
        System.out.println("Transitioning from " + currentState + " to " + newState);
        currentState = newState;
    }

    public void ShortestPath() {
        System.out.println("Enter your Current Location");
        start=Instant.now();

        Scanner scanner = new Scanner(System.in);
        String currentCity = scanner.nextLine();
        System.out.println("Enter your Destination");
        String destinationCity = scanner.nextLine();

        Map<String, Object> result = calculateShortestPathAndCost(currentCity, destinationCity);

        if (result != null) {
            totalCost = (int) result.get("ShortestDistance");
            List<String> path = (List<String>) result.get("ShortestPath");

            System.out.println("Shortest Distance: " + totalCost);
            System.out.println("Cost of Travel Rs."+totalCost*50+"/");
            System.out.println("Shortest Path: " + String.join(" -> ", path));
        } else {
            System.out.println("Error ");
        }
    }



    public static void main(String[] args) {
        // Create an instance of the Airplane class
        Airplane airplane = new Airplane(3);

        // Simulate the airplane's movement through different states
        airplane.transitionToState(AirplaneState.TAXIING_ON_GROUND);
        airplane.transitionToState(AirplaneState.HOLDING);
        airplane.transitionToState(AirplaneState.TAKING_OFF);
        // ... (simulate other states as needed)

        // Get the current state of the airplane
        AirplaneState currentState = airplane.getCurrentState();
        System.out.println("Current state: " + currentState);
        airplane.ShortestPath();
    }
}

















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
