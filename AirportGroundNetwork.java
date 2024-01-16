package task;

import java.util.*;

public class AirportGroundNetwork {

    protected Map<String, LinkState> runwayStates;
    protected Map<String, LinkState> gatestates;
    protected Map<String, LinkState> taxiwaystates;
    protected Map<String, Integer> LinkOccupancy;

    public AirportGroundNetwork() {
        this.runwayStates = new HashMap<>();
        this.gatestates = new HashMap<>();
        this.taxiwaystates = new HashMap<>();
    }

    // Enum to represent the states of a link (runway, taxiway, or gate)
    private enum LinkState {
        OPEN_AVAILABLE, OPEN_OCCUPIED, CLOSED
    }

    public void openGate(String linkName) {
        gatestates.put(linkName, LinkState.OPEN_AVAILABLE);
        System.out.println("Link " + linkName + " is now open and available.");
    }

    public void openRunway(String linkName) {
        runwayStates.put(linkName, LinkState.OPEN_AVAILABLE);
        System.out.println("Link " + linkName + " is now open and available.");
    }

    public void openTaxiway(String linkName) {
        taxiwaystates.put(linkName, LinkState.OPEN_AVAILABLE);
        System.out.println("Link " + linkName + " is now open and available.");
    }

    public void occupyGate(String linkName) {
            gatestates.put(linkName, LinkState.OPEN_OCCUPIED);
            System.out.println("Link " + linkName + " occupied.");

    }

    public void occupyRunway(String linkName) {

            runwayStates.put(linkName, LinkState.OPEN_OCCUPIED);
            System.out.println("Link " + linkName + " occupied.");
        }


    public void occupyTaxiway(String linkName) {

            taxiwaystates.put(linkName, LinkState.OPEN_OCCUPIED);
            System.out.println("Link " + linkName + " occupied.");

    }

    public void closeGate(String linkName) {
        gatestates.put(linkName, LinkState.CLOSED);
        System.out.println("Link " + linkName + " is now closed.");
    }

    public void closeRunway(String linkName) {
        runwayStates.put(linkName, LinkState.CLOSED);
        System.out.println("Link " + linkName + " is now closed.");
    }

    public void closeTaxiway(String linkName) {
        taxiwaystates.put(linkName, LinkState.CLOSED);
        System.out.println("Link " + linkName + " is now closed.");
    }

    public void airplaneEntersGate(String linkName, int airplaneID) {
      
            gatestates.put(linkName, LinkState.OPEN_OCCUPIED);
            LinkOccupancy.put(linkName, airplaneID);
            System.out.println("Airplane " + airplaneID + " entered " + linkName);
        }
    public void airplaneEntersRunway(String linkName, int airplaneID) {
     
            taxiwaystates.put(linkName, LinkState.OPEN_OCCUPIED);
            LinkOccupancy.put(linkName, airplaneID);
            System.out.println("Airplane " + airplaneID + " entered " + linkName);
        }
    public void airplaneEntersTaxiway(String linkName, int airplaneID) {
            runwayStates.put(linkName, LinkState.OPEN_OCCUPIED);
            LinkOccupancy.put(linkName, airplaneID);
            System.out.println("Airplane " + airplaneID + " entered " + linkName);
        }
    

    public void airplaneExitsGate(String linkName, int airplaneID) {
       
            gatestates.put(linkName, LinkState.OPEN_AVAILABLE);
            LinkOccupancy.remove(linkName);
            System.out.println("Airplane " + airplaneID + " exited " + linkName);
        }
    public void airplaneExitsTaxiway(String linkName, int airplaneID) {

        taxiwaystates.put(linkName, LinkState.OPEN_AVAILABLE);
            LinkOccupancy.remove(linkName);
            System.out.println("Airplane " + airplaneID + " exited " + linkName);
        }
    public void airplaneExitsRunway(String linkName, int airplaneID) {

      
            runwayStates.put(linkName, LinkState.OPEN_AVAILABLE);
            LinkOccupancy.remove(linkName);
            System.out.println("Airplane " + airplaneID + " exited " + linkName);
        
    }

    public String getRunwayByAirplaneId(int airplaneId) {
        // Logic to find the runway based on airplaneId
        for (Map.Entry<String, LinkState> entry : runwayStates.entrySet()) {
            if (entry.getValue() == LinkState.OPEN_OCCUPIED) {
                return entry.getKey(); // Return the runway name
            }
        }
        return null; // If no runway is found for the given airplaneId
    }

    public String getTaxiwayByAirplaneId(int airplaneId) {
        // Logic to find the taxiway based on airplaneId
        for (Map.Entry<String, LinkState> entry : taxiwaystates.entrySet()) {
            if (entry.getValue() == LinkState.OPEN_OCCUPIED) {
                return entry.getKey(); // Return the taxiway name
            }
        }
        return null; // If no taxiway is found for the given airplaneId
    }

    public String getGateByAirplaneId(int airplaneId) {
        // Logic to find the gate based on airplaneId
        for (Map.Entry<String, LinkState> entry : gatestates.entrySet()) {
            if (entry.getValue() == LinkState.OPEN_OCCUPIED) {
                return entry.getKey(); // Return the gate name
            }
        }
        return null; // If no gate is found for the given airplaneId
    }

    // Add similar methods for other functionalities as needed
}
