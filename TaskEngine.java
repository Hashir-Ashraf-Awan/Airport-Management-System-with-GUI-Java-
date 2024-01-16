package task;

import java.time.Instant;
import java.util.*;

class TaskEngine extends GlobalClock  {
    private ArrayList<Task> tasksqueue;
    protected TaskQueue queue;


    public TaskEngine() {
        super(Instant.now());
        this.tasksqueue = new ArrayList<>();
        this.queue = new TaskQueue();  // Initialize the TaskQueue
    }


    public void addTask(Task task) {
        System.out.println("Task Created");
        task.createTask();

        tasksqueue.add(task);
     //   queue.priorityQueue.add(task.getPriority());

    }
public void printTask(){
        for (int i = 0; i < tasksqueue.size(); i++){
            System.out.println("Task "+tasksqueue.get(i).getTaskName()+"with Prioroty "+tasksqueue.get(i).getPriority());
        }
}

    public void dispatchTasks() {
        for (Task task : tasksqueue) {
            System.out.println("Dispatching " + task.getTaskName());
            task.createTaskIdentifier();
        }
        tasksqueue.clear();
    }


    public void prioritizeTasks() {
        System.out.println("Task prioritized based on their priority");
        queue.comparePriorityAndTime(tasksqueue);
    }


    public void pushBackTask(Task task) {
        tasksqueue.add(task);
    }


    public void deleteTask(Task task) {
        tasksqueue.remove(task);
    }


}

