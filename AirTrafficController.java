package task;

import java.time.Duration;
import java.time.Instant;

class AirTrafficController extends GlobalClock {

    public AirTrafficController(Instant startTime) {
        super(startTime);
    }

    public void requestPermission(int time) {
        Instant startTime = Instant.now();
        GlobalClock g = new GlobalClock(startTime);

        start();


        boolean permissionGranted = false;

        try {
            Thread.sleep(time* 1000L); // Sleep for an additional 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        permissionGranted = true;
        g.interrupt();
        if (permissionGranted) {
            System.out.println("Permission granted by Air Traffic Controller");
            g.shouldDisplay=false;

        }
    }
}