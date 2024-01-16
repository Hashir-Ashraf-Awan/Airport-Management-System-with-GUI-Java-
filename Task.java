package task;
import java.util.Scanner;

 class Task {
    private String taskName;
    private int priority;
    private String primaryLabel;
    private String secondaryLabel;
    private int a;
    private Airplane path;
     private AirTrafficController permiss;
     private AirportGroundNetwork k;


    public void createTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter task name");
        taskName = scanner.nextLine();
        this.taskName = taskName;
        System.out.println("Enter priority for Task " + taskName + " (1-10): ");
        priority = scanner.nextInt(); // Set priority during task creation
    }

    public String getTaskName() {
        return taskName;
    }

    public int getPriority() {
        return priority;
    }

    public void landingTask() {
    permiss.requestPermission();
    path.setid();
     permiss.getId();
     k.occupyRunway("runway 1");
     k.occupyGate("occupy gate 1");

    }
public void enteringtask()
{
    k.openGate("gate 1");
    k.closeRunway("Runway 1");

}


    public void exitingTask(  )
    {
       k.closeGate("GATE 1");;
       k.openRunway("Runway 1");

    }

    public void createTaskIdentifier(int airplaneID) {
        System.out.println("For aeroplane id " + airplaneID);
        System.out.println("Enter 0 for primary label and 1 for secondary label");
        Scanner b = new Scanner(System.in);
        a = b.nextInt();

        if (a == 0) {
            System.out.println("Enter the primary label ");
            primaryLabel = b.next();
            path.ShortestPath();
            landingTask();
            enteringtask();
            exitingTask();
        }

        if (a == 1) {
            System.out.println("Enter the secondary label");
            secondaryLabel = b.next();
          path.MovingAirplane(5345);
          path.ParkingAirplane(5345);
          path.HoldingAirplane(5345);



        }
    }

     public String getPrimaryLabel() {
        return primaryLabel;
    }

    public String getSecondaryLabel() {
        return secondaryLabel;
    }
}
