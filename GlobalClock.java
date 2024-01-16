package task;

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);

        while (!Thread.interrupted()) {
            Instant currentTime = Instant.now();
            Duration duration = Duration.between(startTime, currentTime);

            long seconds = duration.getSeconds();
            long minutes = seconds / 60;
            long hours = minutes / 60;

            if (shouldDisplay && scanner.nextLine().isEmpty()) {
                System.out.println("Time since program start: " + hours + " hours, " + minutes % 60 + " minutes, " + seconds % 60 + " seconds");
                shouldDisplay = false;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        scanner.close();
    }

    public void setShouldDisplay(boolean shouldDisplay) {
        this.shouldDisplay = shouldDisplay;
    }
}