package task;

import java.util.*;

class Vertex {
    String name;
    Map<Vertex, Integer> adjList;

    int distance = Integer.MAX_VALUE;
    Vertex predecessor;

    Vertex(String name) {
        this.name = name;
        this.adjList = new HashMap<>();
    }

    @Override
    public String toString() {
        return this.name;
    }
}

class AirplaneDestinationSystem {

    public static Map<String, Object> calculateShortestPathAndCost(String currentCity, String destinationCity) {
        Map<String, Vertex> vertices = createGraph();
        Vertex startVertex = vertices.get(currentCity);
        Vertex destinationVertex = vertices.get(destinationCity);

        if (startVertex == null || destinationVertex == null) {
            System.out.println("Invalid starting or destination city.");
            return null;
        }

        dijkstra(startVertex);

        Map<String, Object> result = new HashMap<>();
        result.put("ShortestDistance", destinationVertex.distance);
        result.put("ShortestPath", getShortestPath(destinationVertex));
        return result;
    }

    private static Map<String, Vertex> createGraph() {
        Map<String, Vertex> vertices = new HashMap<>();

        Vertex lahore = new Vertex("Lahore");
        Vertex karachi = new Vertex("Karachi");
        Vertex faisalabad = new Vertex("Faisalabad");
        Vertex quetta = new Vertex("Quetta");
        Vertex peshawar = new Vertex("Peshawar");

        lahore.adjList.put(karachi, 1500);
        lahore.adjList.put(faisalabad, 800);
        lahore.adjList.put(quetta, 2000);
        lahore.adjList.put(peshawar, 1000);

        karachi.adjList.put(peshawar, 1500);
        karachi.adjList.put(faisalabad, 1000);
        karachi.adjList.put(quetta, 2000);

        faisalabad.adjList.put(quetta, 500);
        faisalabad.adjList.put(peshawar, 1000);
        faisalabad.adjList.put(karachi, 1200);

        quetta.adjList.put(peshawar, 800);
        quetta.adjList.put(karachi, 1000);

        peshawar.adjList.put(lahore, 1500);

        vertices.put("Lahore", lahore);
        vertices.put("Karachi", karachi);
        vertices.put("Faisalabad", faisalabad);
        vertices.put("Quetta", quetta);
        vertices.put("Peshawar", peshawar);

        return vertices;
    }

    private static void dijkstra(Vertex startVertex) {
        PriorityQueue<Vertex> pq = new PriorityQueue<>(Comparator.comparingInt(vertex -> vertex.distance));
        startVertex.distance = 0;
        pq.add(startVertex);

        while (!pq.isEmpty()) {
            Vertex actual = pq.poll();
            for (Map.Entry<Vertex, Integer> entry : actual.adjList.entrySet()) {
                Vertex targetVertex = entry.getKey();
                int weight = entry.getValue();

                int newDistance = actual.distance + weight;
                if (newDistance < targetVertex.distance) {
                    targetVertex.distance = newDistance;
                    targetVertex.predecessor = actual;
                    pq.add(targetVertex);
                }
            }
        }
    }

    private static List<String> getShortestPath(Vertex destinationVertex) {
        List<String> path = new ArrayList<>();
        while (destinationVertex != null) {
            path.add(destinationVertex.name);
            destinationVertex = destinationVertex.predecessor;
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your current city:");
        String currentCity = scanner.nextLine();
        System.out.println("Enter your destination city:");
        String destinationCity = scanner.nextLine();

        Map<String, Object> result = calculateShortestPathAndCost(currentCity, destinationCity);

        if (result != null) {
            if (result.get("ShortestDistance").equals(Integer.MAX_VALUE)) {
                System.out.println("No valid path from " + currentCity + " to " + destinationCity);
            } else {
                System.out.println("Shortest Distance from " + currentCity + " to " + destinationCity + ": " + result.get("ShortestDistance"));
                System.out.println("Shortest Path: " + result.get("ShortestPath"));
            }
        }
    }
}
