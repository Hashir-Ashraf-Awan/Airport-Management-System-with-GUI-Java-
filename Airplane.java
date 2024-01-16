package task;
import org.w3c.dom.ls.LSOutput;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import java.time.Instant;

import static task.AirplaneDestinationSystem.calculateShortestPathAndCost;


class Airplane {

    public enum AirplaneState {
        FINAL_APPROACH, TOUCH_DOWN, DECELERATING_ON_RUNWAY, NETWORK_TAXIING, HOLDING, PARKED_AT_GATE, TAXIING_ON_GROUND, TAKING_OFF, MOVING_IN_TERMINAL, LEAVING_TERMINAL
    }
    protected int id;
    protected int orientation;
    protected int destination;
    protected int from;
    protected int to;
    protected int totalCost;
    protected int reachDestination;
    protected String path;
    protected Instant start;
    protected Instant end;
    protected AirplaneState currentState;




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
        start = Instant.now();
        Scanner scanner = new Scanner(System.in);
        String currentCity = scanner.nextLine();
        System.out.println("Enter your Destination");
        String destinationCity = scanner.nextLine();

        Map<String, Object> result = calculateShortestPathAndCost(currentCity, destinationCity);

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
        }
    public void HoldingAirplane(int id){
        System.out.println("Airplane with id " + id+" is holding");
        transitionToState(AirplaneState.HOLDING);
    }
    public void ParkingAirplane(int id){
        System.out.println("Airplane with id " + id+" is parked");
        transitionToState(AirplaneState.PARKED_AT_GATE);
    }


}
