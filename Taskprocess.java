import java.util.ArrayList;

public class TaskProcess {

    private ArrayList<Task> tasks = new ArrayList<>();

    public void processTask(Task t) {
        tasks.add(t);
        System.out.println("Task processed: Flight " + t.getFlightNo() + " Time " + t.getTime());
    }
}
