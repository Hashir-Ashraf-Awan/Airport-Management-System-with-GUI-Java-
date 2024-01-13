import java.util.Scanner;

class Runway {
    private boolean available;

    Runway() {
        System.out.println("Enter whether the runway is available or not (true/false):");
        Scanner scanner = new Scanner(System.in);
        this.available = scanner.nextBoolean();
    }

    public void markAvailable() {
        this.available = true;
    }

    public void markNotAvailable() {
        this.available = false;
    }

    public void setAvailability(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }



    public static void main(String[] args) {
        Runway runway = new Runway();

        // Example usage:
        System.out.println("Is the runway available? " + runway.isAvailable());

        runway.markAvailable();
        System.out.println("After marking available: " + runway.isAvailable());

        runway.markNotAvailable();
        System.out.println("After marking not available: " + runway.isAvailable());

        runway.setAvailability(true);
        System.out.println("After setting availability: " + runway.isAvailable());
    }
}
