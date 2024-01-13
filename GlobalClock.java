package task;

import java.time.Duration;
import java.time.Instant;

class GlobalClock extends Thread {
    protected Instant startTime;
    private boolean shouldDisplay;

    public GlobalClock(Instant startTime) {
        this.startTime = startTime;
        this.shouldDisplay = false;
    }
    public Instant getStartTime() {
        return startTime;
    }

    public void run() {
        while (!Thread.interrupted()) {
            Instant currentTime = Instant.now();
            Duration duration = Duration.between(startTime, currentTime);

            long seconds = duration.getSeconds();
            long minutes = seconds / 60;
            long hours = minutes / 60;

            System.out.println("Time since program start: " + hours + " hours, " + minutes % 60 + " minutes, " + seconds % 60 + " seconds");

            try {
                // Sleep for 1 second before displaying the next time
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }


    public void setShouldDisplay(boolean shouldDisplay) {
        this.shouldDisplay = shouldDisplay;
    }


}
//class Main{
//    public static void main(String[] args){
//        Instant startTime = Instant.now();
//        GlobalClock globalClock = new GlobalClock(startTime);
//
//        // Start the GlobalClock thread
//        globalClock.start();
//
//        // Set shouldDisplay to true to print the time information
//        globalClock.setShouldDisplay(true);
//
//        // Sleep for a while to see the output
//        try {
//            Thread.sleep(5000); // Sleep for 5 seconds
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // Stop the GlobalClock thread
//
//
//    }
//}