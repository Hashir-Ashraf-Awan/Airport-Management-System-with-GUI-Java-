package task;

import java.util.*;

public class TaskProcessor {
    protected int PrimaryLabel;
    protected int SecondaryLabel;
    protected Task task;
    protected Airplane plane;
    protected AirportGroundNetwork ground;



public void Input(){
    System.out.println("Enter 1 for Scheduling Task. 2 for Airplane Related Task. 3 for Shortest Path related Task.");
    Scanner s=new Scanner(System.in);
    int i=s.nextInt();
    PrimaryLabel = s.nextInt();

    if (PrimaryLabel==2){
        int j=s.nextInt();
        System.out.println("Enter 1 for Landing Task. 2 for Exiting Task. 3 for Entering Task.");
        if (j==1){
            ground.airplaneEntersLink("Gate", plane.getId());
//            task.landingTask(plane.getId(),ground.getRunwayByAirplaneId(plane.getId()),ground.getGateByAirplaneId(plane.getId()));
//            ground.occupyRunway("Runway 1" );
//            ground.occupyGate("Gate 1");

        } else if (j == 2) {
            ground.airplaneExitsLink("Gate", plane.getId());
//            task.exitingTask(plane.getId(),ground.getRunwayByAirplaneId(plane.getId()),ground.getGateByAirplaneId(plane.getId()));
//            ground.openRunway("Runway 1");
//            ground.openGate("Gate 1" );
        }else if (j == 3)

        {
            ground.airplaneEntersLink("Gate", plane.getId());
//            task.enteringTask(plane.getId(),ground.getRunwayByAirplaneId(plane.getId()),ground.getGateByAirplaneId(plane.getId()));
//            ground.occupyRunway("Runway 1" );
//            ground.occupyGate("Gate 1");
        }
    }

}












}










