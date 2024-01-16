package task;

import java.time.Instant;

 class MAINProgram {
    public static void main(String[] args){

        Airplane airplane=new Airplane(1,"Lahore","Karachi", Airplane.AirplaneState.PARKED_AT_GATE);


                // Access and print airplane details
                System.out.println("Airplane ID: " + airplane.getId());
                System.out.println("Current State: " + airplane.getCurrentState());

                // Set new ID for the airplane
                airplane.setId(2);
                System.out.println("New Airplane ID: " + airplane.getId());

                // Perform airplane actions
                airplane.ShortestPath();
                airplane.MovingAirplane(2);
                airplane.HoldingAirplane(2);
                airplane.ParkingAirplane(2);
        Task t1=new Task();
        Task t2=new Task();
        TaskEngine engine=new TaskEngine();
        engine.addTask(t1);
        engine.addTask(t2);
        engine.prioritizeTasks();

        engine.dispatchTasks();
            }
        }



















