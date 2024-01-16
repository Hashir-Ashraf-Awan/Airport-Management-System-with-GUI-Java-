package task;

import java.util.Scanner;

class Task {
    private String taskName;
    private int priority;
    private String primaryLabel;
    private String secondaryLabel;
    private int a;

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

    public void landingTask(int airplaneID, String runway, String gate) {
        // Add logic for landing task
    }

    public void enteringTask(int airplaneID, String currentLink, String nextLink) {
        // Add logic for entering task
    }

    public void exitingTask(int airplaneID, String currentLink, String nextLink) {
        // Add logic for exiting task
    }

    public void createTaskIdentifier(int airplaneID) {
        System.out.println("For aeroplane id " + airplaneID);
        System.out.println("Enter 0 for primary label and 1 for secondary label");
        Scanner b = new Scanner(System.in);
        a = b.nextInt();

        if (a == 0) {
            System.out.println("Enter the primary label ");
            primaryLabel = b.next();
        }

        if (a == 1) {
            System.out.println("Enter the secondary label");
            secondaryLabel = b.next();
        }
    }

    public String getPrimaryLabel() {
        return primaryLabel;
    }

    public String getSecondaryLabel() {
        return secondaryLabel;
    }
}
