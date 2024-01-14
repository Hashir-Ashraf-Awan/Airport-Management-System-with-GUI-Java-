import java.util.PriorityQueue;
import java.util.Scanner;
class Piriority  {
    private Task[] tasks = new Task[10];
    private PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

    public Piriority() {
        // Initialize your tasks
        for (int i = 0; i < 10; i++) {
            tasks[i] = new Task();
        }
    }

    public void setpirority() {
        for (int i = 0; i < 10; i++) {
            tasks[i].set();
        }
    }

    public void addTasksToPriorityQueue() {
        for (int i = 0; i < 10; i++) {
            priorityQueue.add(tasks[i].getPriority());  //is jagah get piriority se number lena ha k aisa ho jis task ki piriority 1 ha phly  run ho
                                            // pirority list minimum set hti ha 1 se 10
        }
    }

    public void comparePriority() {
        for (int i = 1; i < 10; i++) {
            if (tasks[i].getPriority() > tasks[0].getPriority()) {
                System.out.println(tasks[i] + " has higher priority than " + tasks[0]);
                // Swap tasks[i] and tasks[0] using a temporary variable
                Task temp = tasks[i];
                tasks[i] = tasks[0];
                tasks[0] = temp;
            } else if (tasks[i].getPriority() < tasks[0].getPriority()) {
                System.out.println(tasks[0] + " has higher priority than " + tasks[i]);
                // Swap tasks[i] and tasks[0] using a temporary variable
                Task temp = tasks[i];
                tasks[i] = tasks[0];
                tasks[0] = temp;
            } else {
                System.out.println("Both tasks have the same priority.");
            }
        }
    }

    public void printPriorityQueue() {
        System.out.println("Tasks in Priority Queue:");
        while (!priorityQueue.isEmpty()) {
            System.out.println(priorityQueue.poll());
        }
    }
}