package task;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import java.time.Instant;

class AirTrafficController extends GlobalClock {

    public AirTrafficController(Instant startTime) {
        super(startTime);
    }

    public void requestPermission() {

        start();

        boolean permissionGranted = false;

        while (true) {
            Instant currentTime = Instant.now();
            long secondsElapsed = Duration.between(startTime, currentTime).getSeconds();

            if (secondsElapsed >= 5) {
                permissionGranted = true;
                break;
            }

            // Stop the global clock thread
            interrupt();

            if (permissionGranted) {
                System.out.println("Permission granted by Air Traffic Controller");
            } else {
                System.out.println("Permission denied by Air Traffic Controller");
            }
        }
    }
}

    class AirportGroundNetwork {
        private List<String> taxiways;
        private List<String> gates;
        private List<String> runways;

        public AirportGroundNetwork() {
            taxiways = new ArrayList<>();
            gates = new ArrayList<>();
            runways = new ArrayList<>();


            taxiways.add("Taxiway A");
            taxiways.add("Taxiway B");
            taxiways.add("Taxiway C");
            taxiways.add("Taxiway D");

            gates.add("Gate 1");
            gates.add("Gate 2");
            gates.add("Gate 3");
            gates.add("Gate 4");

            runways.add("Runway 1");
            runways.add("Runway 2");
            runways.add("Runway 3");
            runways.add("Runway 4");

        }

        public String getAvailableTaxiway() {
            for (String taxiway : taxiways) {
                return taxiway;
            }
            return null;
        }

        public String getAvailableGate() {
            for (int i = 0; i < gates.size(); i++) {
                String gate = gates.get(i);
                return gate;
            }
            return null;
        }

        public String getAvailableRunway() {
            for (String runway : runways) {
                return runway;
            }
            return null;
        }
    }


    class Airplane {
        private int id;

        public Airplane(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }


        public void requestPermission(AirTrafficController controller, AirportGroundNetwork groundNetwork) {
            controller.requestPermission();
            String taxiway = groundNetwork.getAvailableTaxiway();
            String gate = groundNetwork.getAvailableGate();
            String runway = groundNetwork.getAvailableRunway();

            if (taxiway != null && gate != null && runway != null) {
                System.out.println("Airplane " + id + " is taxiing on " + taxiway + " and heading to " + gate + " for departure on " + runway);

            } else if (taxiway == null) {
                System.out.println("Taxiway occupied/ Closed by AirTraffic Controller");
            } else if (gate == null) {
                System.out.println("Gate closed by AirTraffic Controller");
            }
        }
    }

    class Task {
        private String priority;
        private int timeMark;

        public Task(String priority, int timeMark) {
            this.priority = priority;
            this.timeMark = timeMark;
        }

        public String getPriority() {
            return priority;
        }

        public int getTimeMark() {
            return timeMark;
        }
    }

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









































//import java.util.Scanner;
//public class Main {
//    public static void main(String[] args) throws InterruptedException {
//        TaskEngine taskEngine = new TaskEngine();
//        Scanner scanner = new Scanner(System.in);
//
//        // Simulate adding tasks
//        System.out.println("Enter the number of tasks to simulate:");
//        int numTasks = scanner.nextInt();
//
//        for (int i = 0; i < numTasks; i++) {
//            System.out.println("Enter priority for Task " + (i + 1) + ":");
//            int priority = scanner.nextInt();
//
//            System.out.println("Enter execution time for Task " + (i + 1) + ":");
//            int executionTime = scanner.nextInt();
//
//            taskEngine.addTask(priority, executionTime);
//        }
//
//        // Simulation clock
//        int simulationTime = 0;
//        while (!taskEngine.isEmpty()) {
//            taskEngine.dispatchTasks(simulationTime);
//            simulationTime++;
//
//            Thread.sleep(1000); // Adjust the sleep time as needed
//        }
//    }
//}

/*ublic class Main {
    public static void main(String[] args) throws InterruptedException {
        TaskEngine taskEngine = new TaskEngine();
        TaskManagement taskManagement = new TaskManagement(taskEngine);

        AirTraffic t1 = new AirTraffic("1", "Lahore", "Islamabad", 1, taskManagement);
        AirTraffic t2 = new AirTraffic("2", "Karachi", "Dubai", 2, taskManagement);

        Thread thread1 = new Thread(t1);
        Thread thread2 = new Thread(t2);

        thread1.start();
        thread2.start();

        // Simulation clock
        int simulationTime = 0;
        while (thread1.isAlive() || thread2.isAlive()) {
            taskEngine.dispatchTasks(simulationTime);
            simulationTime++;

            Thread.sleep(100);
        }
    }
}
*/