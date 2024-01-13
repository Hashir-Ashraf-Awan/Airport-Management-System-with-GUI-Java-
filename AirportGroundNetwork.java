package task;

import java.util.ArrayList;
import java.util.List;

class AirportGroundNetwork {
    private List<String> taxiways;
    private List<String> gates;
    private List<String> runways;

    public AirportGroundNetwork() {
        taxiways = new ArrayList<>();
        gates = new ArrayList<>();
        runways = new ArrayList<>();


        taxiways.add("Taxiway A");
        taxiways.add("Taxiway B");
        taxiways.add("Taxiway C");
        taxiways.add("Taxiway D");

        gates.add("Gate 1");
        gates.add("Gate 2");
        gates.add("Gate 3");
        gates.add("Gate 4");

        runways.add("Runway 1");
        runways.add("Runway 2");
        runways.add("Runway 3");
        runways.add("Runway 4");

    }

    public String getAvailableTaxiway() {
        for (String taxiway : taxiways) {
            return taxiway;
        }
        return null;
    }

    public String getAvailableGate() {
        for (int i = 0; i < gates.size(); i++) {
            String gate = gates.get(i);
            return gate;
        }
        return null;
    }

    public String getAvailableRunway() {
        for (String runway : runways) {
            return runway;
        }
        return null;
    }
}
