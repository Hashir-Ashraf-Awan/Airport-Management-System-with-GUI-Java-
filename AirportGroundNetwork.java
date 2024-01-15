package task;

import java.util.*;

class AirportGroundNetwork {

    private Map<String, LinkState> runwayStates;

    private Map<String,LinkState> gatestates;
    private Map<String,LinkState>taxiwaystates;
    private Map<String,Integer> LinkOccupancy;

    public AirportGroundNetwork() {

        this.runwayStates=new HashMap<>();
        this.taxiwaystates=new HashMap<>();

    }

    // Enum to represent the states of a link (runway, taxiway, or gate)
    private enum LinkState {
        OPEN_AVAILABLE, OPEN_OCCUPIED,CLOSED
    }

    // Method to update link state when it is opened
    public void openLink(String linkName) {
        if (Objects.equals(linkName, "Gate")) {
            gatestates.put(linkName, LinkState.OPEN_AVAILABLE);
        }
        if (Objects.equals(linkName, "Taxiway")){
            taxiwaystates.put(linkName, LinkState.OPEN_AVAILABLE);}
        if (Objects.equals(linkName, "Runway")){
            runwayStates.put(linkName, LinkState.OPEN_AVAILABLE);}

        System.out.println(linkName + " is now open and available.");
    }

    // Method to update link state when it is occupied
    public void occupyLink(String linkName) {
        if (Objects.equals(linkName, "Gate")) {
            gatestates.put(linkName, LinkState.OPEN_OCCUPIED);
        }
        if (Objects.equals(linkName, "Taxiway")){
            taxiwaystates.put(linkName, LinkState.OPEN_OCCUPIED);}
        if (Objects.equals(linkName, "Runway")){
            runwayStates.put(linkName, LinkState.OPEN_OCCUPIED);}

    }

    // Method to update link state when it is closed
    public void closeLink(String linkName) {
        if (Objects.equals(linkName, "Gate")) {
            gatestates.put(linkName, LinkState.CLOSED);
        }
        if (Objects.equals(linkName, "Taxiway")){
            taxiwaystates.put(linkName, LinkState.CLOSED);}
        if (Objects.equals(linkName, "Runway")){
            runwayStates.put(linkName, LinkState.CLOSED);}
    }

    // Method to update link occupancy when an airplane enters a link
    public void airplaneEntersLink(String linkName, int airplaneID) {

        if (Objects.equals(linkName, "Gate")) {
            gatestates.put(linkName, LinkState.OPEN_OCCUPIED);
            LinkOccupancy.put(linkName, airplaneID);
        }
        if (Objects.equals(linkName, "Taxiway")) {
            taxiwaystates.put(linkName, LinkState.OPEN_OCCUPIED);
            LinkOccupancy.put(linkName, airplaneID);
        }
        if (Objects.equals(linkName, "Runway")) {
            runwayStates.put(linkName, LinkState.OPEN_OCCUPIED);
            LinkOccupancy.put(linkName, airplaneID);
        }
    }


    // Method to update link occupancy when an airplane exits a link
    public void airplaneExitsLink(String linkName, int airplaneID) {
        if (Objects.equals(linkName, "Gate")) {
            gatestates.put(linkName, LinkState.OPEN_AVAILABLE);
            LinkOccupancy.remove(linkName, airplaneID);
        }
        if (Objects.equals(linkName, "Taxiway")) {
            taxiwaystates.put(linkName, LinkState.OPEN_AVAILABLE);
            LinkOccupancy.remove(linkName, airplaneID);
        }
        if (Objects.equals(linkName, "Runway")) {
            runwayStates.put(linkName, LinkState.OPEN_AVAILABLE);
            LinkOccupancy.remove(linkName, airplaneID);
            }
        }
    }
