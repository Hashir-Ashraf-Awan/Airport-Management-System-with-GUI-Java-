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

  }