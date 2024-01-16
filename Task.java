package task;

import java.time.Instant;
import java.util.*;

class Task {
    protected String taskName;
    protected int priority;
    protected String primaryLabel;
    protected String secondaryLabel;
    protected int a;
    protected Airplane plane;
    protected AirTrafficController controller;
    protected AirportGroundNetwork ground;
    protected int timeMark;
    protected AirplaneListClass listClass;

    public void createTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter task name");
        taskName = scanner.nextLine();

        this.taskName = taskName;
        System.out.println("Enter time mark for task execution");
        timeMark = scanner.nextInt();
        System.out.println("Enter priority for Task " + taskName + " (1-10): ");
        priority = scanner.nextInt(); // Set priority during task creation
        plane = new Airplane(1, "Lahore", "Peshawar", Airplane.AirplaneState.MOVING_IN_TERMINAL);
        controller = new AirTrafficController(Instant.now());
        ground = new AirportGroundNetwork();

    }

    public String getTaskName() {
        return taskName;
    }

    public int getPriority() {
        return priority;
    }

    public void landingTask() {
        controller.requestPermission(timeMark);
        plane.setId(23);
        controller.getId();
        ground.occupyRunway("runway 1");
        ground.occupyGate("occupy gate 1");
        System.out.println("Airplane Landed Successfully");
    }

    public void enteringTask() {
        controller.requestPermission(timeMark);
        ground.openGate("gate 1");
        ground.closeRunway("Runway 1");
        System.out.println("Airplane entered gate successfully");
    }

    public int getTimeMark() {
        return timeMark;
    }

    public void exitingTask() {
        controller.requestPermission(timeMark);
        ground.closeGate("GATE 1");
        ground.openRunway("Runway 1");
        System.out.println("Airplane exited gate successfully");
    }

    public void createTaskIdentifier() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("For airplane id ");
        System.out.println("Enter 0 for primary label and 1 for secondary label");
        a = scanner.nextInt();

        if (a == 0) {
            System.out.println("Enter the primary label ");
            primaryLabel = scanner.next();

            if (ground == null) {
                ground = new AirportGroundNetwork();
            }

            System.out.println("Choose action: ");
            System.out.println("1. Landing");
            System.out.println("2. Entering");
            System.out.println("3. Exiting");

            int actionChoice = scanner.nextInt();

            switch (actionChoice) {
                case 1:
                    plane.ShortestPath();
                    landingTask();
                    break;
                case 2:
                    enteringTask();
                    break;
                case 3:
                    exitingTask();
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }

        if (a == 1) {
            System.out.println("Enter the secondary label");
            secondaryLabel = scanner.next();

            System.out.println("Choose action: ");
            System.out.println("1. Moving");
            System.out.println("2. Parking");
            System.out.println("3. Holding");
            controller = new AirTrafficController(Instant.now());
            listClass = new AirplaneListClass();

            int actionChoice = scanner.nextInt();

            switch (actionChoice) {
                case 1:
                    listClass.createAirplane(3, "Lahore", "Karachi", Airplane.AirplaneState.MOVING_IN_TERMINAL);
                    controller.requestPermission(timeMark);
                    listClass.taskSending("Moving");
                    break;
                case 2:
                    System.out.println("Airplane Parked");
                    listClass.createAirplane(3, "Lahore", "Karachi", Airplane.AirplaneState.PARKED_AT_GATE);
                    listClass.taskSending("Parked");
                    System.out.println("Airplane Parked");
                    break;
                case 3:
                    listClass.createAirplane(3, "Lahore", "Karachi", Airplane.AirplaneState.HOLDING);
                    controller.requestPermission(timeMark);
                    listClass.taskSending("Holding");
                    System.out.println("Airplane on Hold");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public String getPrimaryLabel() {
        return primaryLabel;
    }

    public String getSecondaryLabel() {
        return secondaryLabel;
    }
}