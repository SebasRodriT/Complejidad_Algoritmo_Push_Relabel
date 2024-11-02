import java.util.*;

public class PushRelabel {
    private int vertices;
    private int[][] capacity;
    private int[][] flow;
    private int[] height, excessFlow;

    /**
     * Constructor: Inicializa las estructuras de datos.
     * Complejidad: O(V^2) debido a la inicialización de las matrices bidimensionales.
     */
    public PushRelabel(int vertices) {
        this.vertices = vertices;
        capacity = new int[vertices][vertices]; // O(V^2)
        flow = new int[vertices][vertices];     // O(V^2)
        height = new int[vertices];             // O(V)
        excessFlow = new int[vertices];         // O(V)
    }

    /**
     * Añade una arista al grafo con su capacidad.
     * Complejidad: O(1) por cada arista añadida.
     */
    public void addEdge(int u, int v, int cap) {
        capacity[u][v] = cap; // O(1)
    }

    /**
     * Inicializa el preflujo desde el nodo fuente.
     * Complejidad: O(V)
     */
    private void initializePreflow(int source) {
        height[source] = vertices; // O(1)
        for (int v = 0; v < vertices; v++) { // O(V)
            if (capacity[source][v] > 0) { // O(1)
                flow[source][v] = capacity[source][v];       // O(1)
                excessFlow[v] = capacity[source][v];         // O(1)
                excessFlow[source] -= capacity[source][v];   // O(1)
                capacity[v][source] = capacity[source][v];   // O(1)
            }
        }
    }

    /**
     * Realiza la operación de 'push' desde u hacia v.
     * Complejidad: O(1) por operación.
     */
    private void push(int u, int v) {
        int flowToPush = Math.min(excessFlow[u], capacity[u][v] - flow[u][v]); // O(1)
        flow[u][v] += flowToPush;    // O(1)
        flow[v][u] -= flowToPush;    // O(1)
        excessFlow[u] -= flowToPush; // O(1)
        excessFlow[v] += flowToPush; // O(1)
    }

    /**
     * Realiza la operación de 'relabel' en el nodo u.
     * Complejidad: O(V) por operación.
     */
    private void relabel(int u) {
        int minHeight = Integer.MAX_VALUE; // O(1)
        for (int v = 0; v < vertices; v++) { // O(V)
            if (capacity[u][v] - flow[u][v] > 0) { // O(1)
                minHeight = Math.min(minHeight, height[v]); // O(1)
            }
        }
        if (minHeight < Integer.MAX_VALUE) {
            height[u] = minHeight + 1; // O(1)
        }
    }

    /**
     * Calcula el flujo máximo desde el nodo fuente al sumidero.
     * Complejidad total: O(V^2E)
     */
    public int getMaxFlow(int source, int sink) {
        initializePreflow(source); // O(V)

        // Lista de vértices activos
        List<Integer> activeVertices = new ArrayList<>();
        for (int i = 0; i < vertices; i++) { // O(V)
            if (i != source && i != sink && excessFlow[i] > 0) { // O(1)
                activeVertices.add(i); // O(1)
            }
        }

        // Bucle principal
        while (!activeVertices.isEmpty()) {
            int u = activeVertices.get(0); // O(1)
            boolean pushed = false;

            for (int v = 0; v < vertices; v++) { // O(V)
                // Si hay capacidad residual y la altura cumple la condición
                if (capacity[u][v] - flow[u][v] > 0 && height[u] == height[v] + 1) { // O(1)
                    push(u, v); // O(1)
                    pushed = true;
                    if (excessFlow[u] == 0) {
                        activeVertices.remove(0); // O(1)
                    }
                    if (excessFlow[v] > 0 && v != source && v != sink && !activeVertices.contains(v)) {
                        activeVertices.add(v); // O(1) promedio si usamos una estructura adecuada
                    }
                    break; // Importante para recalcular desde el inicio
                }
            }

            if (!pushed) {
                relabel(u); // O(V)
            }
        }

        return excessFlow[sink]; // O(1)
    }

    public static void main(String[] args) {
        int vertices = 6;
        PushRelabel pushRelabel = new PushRelabel(vertices); // O(V^2)

        // Añadiendo aristas y sus capacidades
        pushRelabel.addEdge(0, 1, 100); // O(1)
        pushRelabel.addEdge(0, 2, 80);  // O(1)
        pushRelabel.addEdge(1, 3, 50);  // O(1)
        pushRelabel.addEdge(1, 4, 50);  // O(1)
        pushRelabel.addEdge(2, 3, 30);  // O(1)
        pushRelabel.addEdge(2, 4, 70);  // O(1)
        pushRelabel.addEdge(3, 5, 50);  // O(1)
        pushRelabel.addEdge(4, 5, 70);  // O(1)

        int source = 0;
        int sink = 5;

        // Calculando y mostrando el flujo máximo
        System.out.println("El flujo máximo es: " + pushRelabel.getMaxFlow(source, sink)); // O(V^2E)
    }
}