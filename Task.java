package task;

import java.util.Scanner;

class Task {
    protected boolean priority;
    protected int timeMark;


Task(){
        System.out.println("Task created");
}
public void set(){
        System.out.println("Enter time for task: " + timeMark);
    Scanner s=new Scanner(System.in);
    timeMark=s.nextInt();
    System.out.println("Enter Priority for task: " );
    priority=s.nextBoolean();

}
    public Integer getPriority() {
        return priority ? 1 : 0; // Assuming true has higher priority than false
    }


    public int getTimeMark() {
        return timeMark;
    }
}
