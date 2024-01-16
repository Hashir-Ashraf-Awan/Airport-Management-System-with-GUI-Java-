package task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

class AirplaneListClass {
    protected List<Airplane> airplaneslist;

    public AirplaneListClass() {
        airplaneslist = new ArrayList<>();
        Airplane a=new Airplane(1,"Lahore","Karachi", Airplane.AirplaneState.HOLDING);
        Airplane b=new Airplane(2,"Lahore","Peshawar", Airplane.AirplaneState.HOLDING);
        Airplane c=new Airplane(3,"Lahore","Karachi", Airplane.AirplaneState.HOLDING);
        Airplane d=new Airplane(4,"Lahore","Karachi", Airplane.AirplaneState.HOLDING);
        airplaneslist.add(a);
        airplaneslist.add(b);
        airplaneslist.add(c);
        airplaneslist.add(d);
    }

    public void createAirplane(int id, String to, String from, Airplane.AirplaneState state) {
        Airplane airplane = new Airplane(id, to, from, state);
        airplaneslist.add(airplane);
    }


    public void removeAirplane() {
        System.out.println("Enter ID of Airplane to be Removed");
        int a;
        Scanner s = new Scanner(System.in);
        a = s.nextInt();

        for (int i = 0; i < airplaneslist.size(); i++) {
            if (airplaneslist.get(i).getId() == a) {
                airplaneslist.remove(i);
            }
        }
    }

    public void taskSending(String name) {
        System.out.println("Enter ID of Airplane to send the task");
        int a;
        Scanner s = new Scanner(System.in);
        a = s.nextInt();

        for (int i = 0; i < airplaneslist.size(); i++) {
            if (airplaneslist.get(i).getId() == a) {
                System.out.println("Sending task to Airplane with ID " + airplaneslist.get(i).getId());
                if (Objects.equals(name, "Moving")) {
                    airplaneslist.get(i).MovingAirplane(airplaneslist.get(i).getId());
                } else if (Objects.equals(name, "Parked")) {
                    airplaneslist.get(i).ParkingAirplane(airplaneslist.get(i).getId());
                } else if (Objects.equals(name, "Holding")) {
                    airplaneslist.get(i).HoldingAirplane(airplaneslist.get(i).getId());
                }
            }
        }
    }
}
