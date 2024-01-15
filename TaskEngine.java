package task;

import java.time.Instant;
import java.util.*;

class TaskEngine extends GlobalClock  {
    private ArrayList<Task> tasksqueue;
    protected TaskQueue queue;

    public TaskEngine() {
        super(Instant.now());
        this.tasksqueue = new ArrayList<>();
    }


    public void addTask(Task task) {
        System.out.println("Task Created");
        task.CreateTask();
        tasksqueue.add(task);
        queue.priorityQueue.add(task.getPriority());

    }


    public void dispatchTasks() {
        int time;
        System.out.println("Enter time in Seconds at which Task is to be dispatched");
        Scanner s=new Scanner(System.in);
        time=s.nextInt();

        Instant startTime = Instant.now();
        GlobalClock globalClock = new GlobalClock(startTime);
        globalClock.start();
        try {
            Thread.sleep(time* 1000L); // Sleep for an additional 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Task task : tasksqueue) {
            if (Objects.equals(task.getTaskName(), "Landing Task")) {

                System.out.println("Dispatching " + task.getTaskName());
                tasksqueue.remove(task);
                break;
            }
        }
    }

    public void prioritizeTasks() {
        System.out.println("Task prioritized based on their priority");
        queue.comparePriority(tasksqueue);
    }


    public void pushBackTask(Task task) {
        tasksqueue.add(task);
    }


    public void deleteTask(Task task) {
        tasksqueue.remove(task);
    }


}

