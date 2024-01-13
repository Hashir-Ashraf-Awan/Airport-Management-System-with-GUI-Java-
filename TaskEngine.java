package task;

import java.util.ArrayList;

class TaskEngine {
    private ArrayList<Task> tasks;

    public TaskEngine() {
        this.tasks = new ArrayList<>();
    }


    public void addTask(Task task) {
        tasks.add(task);
    }


    public void dispatchTasks(int globalTime) {
        for (Task task : tasks) {
            if (task.getTimeMark() == globalTime) {
                System.out.println("Dispatching Task: " + task.getPriority());
                tasks.remove(task);
                break;
            }
        }
    }

    public void prioritizeTasks() {
        tasks.sort((t1, t2) -> t1.getPriority().compareTo(t2.getPriority()));
    }


    public void pushBackTask(Task task) {
        tasks.add(task);
    }


    public void deleteTask(Task task) {
        tasks.remove(task);
    }


}
