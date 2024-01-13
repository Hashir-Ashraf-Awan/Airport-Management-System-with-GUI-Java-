package task;

import java.util.*;

class Vertex {
    String name;
    Edge[] adjList;
    int distance = Integer.MAX_VALUE;

    Vertex(String name, int size) {
        this.name = name;
        this.adjList = new Edge[size];
    }

    @Override
    public String toString() {
        return this.name;
    }
}

class Edge {
    int weight;
    Vertex targetVertex;

    Edge(int weight, Vertex targetVertex) {
        this.weight = weight;
        this.targetVertex = targetVertex;
    }
}

 class AirplaneDestinationSystem {

    static void dijkstra(Vertex startVertex) {
        PriorityQueue<Vertex> pq = new PriorityQueue<>(Comparator.comparingInt(vertex -> vertex.distance));
        startVertex.distance = 0;
        pq.add(startVertex);

        while (!pq.isEmpty()) {
            Vertex actual = pq.remove();
            for (Edge edge : actual.adjList) {
                int newDistance = actual.distance + edge.weight;
                Vertex targetVertex = edge.targetVertex;
                if (newDistance < targetVertex.distance) {
                    targetVertex.distance = newDistance;
                    targetVertex.adjList[0] = new Edge(newDistance, actual);
                    pq.remove(targetVertex);
                    pq.add(targetVertex);
                }
            }
        }
    }

    static void printPath(Vertex vertex) {
        if (vertex == null) return;
        printPath(vertex.adjList[0].targetVertex);
        System.out.println(vertex);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Vertex lahore = new Vertex("Lahore", 3);
        Vertex karachi = new Vertex("Karachi", 2);
        Vertex faisalabad = new Vertex("Faisalabad", 1);
        Vertex quetta = new Vertex("Quetta", 1);
        Vertex peshawar = new Vertex("Peshawar", 0);

        lahore.adjList[0] = new Edge(1500, karachi);
        lahore.adjList[1] = new Edge(800, faisalabad);
        lahore.adjList[2] = new Edge(2000, quetta);

        karachi.adjList[0] = new Edge(1500, peshawar);

        faisalabad.adjList[0] = new Edge(500, quetta);

        System.out.println("Enter your current city:");
        String currentCity = scanner.nextLine();
        Vertex startVertex = getVertex(currentCity, lahore, karachi, faisalabad, quetta, peshawar);

        if (startVertex == null) {
            System.out.println("Invalid starting city.");
            return;
        }

        System.out.println("Enter your destination city:");
        String destinationCity = scanner.nextLine();
        Vertex destinationVertex = getVertex(destinationCity, lahore, karachi, faisalabad, quetta, peshawar);

        if (destinationVertex == null) {
            System.out.println("Invalid destination city.");
            return;
        }

        dijkstra(startVertex);

        System.out.println("Shortest Distance from " + currentCity + " to " + destinationCity + ": " + destinationVertex.distance);
        System.out.println("Shortest Path: ");
        printPath(destinationVertex);
    }

    private static Vertex getVertex(String cityName, Vertex... vertices) {
        for (Vertex vertex : vertices) {
            if (vertex.name.equalsIgnoreCase(cityName)) {
                return vertex;
            }
        }
        return null;
    }
}
