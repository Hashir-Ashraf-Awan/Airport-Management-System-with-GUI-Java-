// Task.java
package task;

import java.util.Scanner;

public class Task {
    private String taskName;
    private int priority;

    public void CreateTask(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter task name");
        taskName =scanner.nextLine();
        this.taskName = taskName;
        System.out.println("Enter priority for Task " + taskName + " (1-10): ");
        priority = scanner.nextInt();// Set priority during task creation
    }


    public String getTaskName() {
        return taskName;
    }

    public int getPriority() {
        return priority;
    }
}
