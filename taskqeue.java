import java.util.ArrayList;

public class TaskQueue {

   protected ArrayList<Task> orderedTasks;

   public TaskQueue() {
      // Initialize the ArrayList
      orderedTasks = new ArrayList<>();
   }

   public void taskInOrder(PriorityQueue q, TaskEngine e) {
      // Assuming that q.orderedTask(e) returns a List<Task>
      orderedTasks = new ArrayList<>(q.orderedTasks(e));
   }

   public ArrayList<Task> getOrderedTasks() {
      return orderedTasks;
   }
}
