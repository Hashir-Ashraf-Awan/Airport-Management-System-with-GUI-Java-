package task;

import java.time.Duration;
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
