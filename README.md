# Descripción del Algoritmo Push-Relabel y Análisis de Complejidad

## Descripción del Grafo de Flujo de Ejemplo

El algoritmo Push-Relabel implementado utiliza un grafo de flujo para calcular el flujo máximo desde un nodo fuente (origen) hasta un nodo sumidero (destino). El grafo de ejemplo consta de 6 nodos y representa una red de distribución desde una **Bodega** hasta un **Sumidero**, pasando por **Centros** y **Tiendas** intermedias.

### Nodos:

1. **Bodega (s)** - Nodo fuente (0)
2. **Centro A** - Nodo intermedio (1)
3. **Centro B** - Nodo intermedio (2)
4. **Tienda 1** - Nodo intermedio (3)
5. **Tienda 2** - Nodo intermedio (4)
6. **Sumidero (t)** - Nodo sumidero (5)

### Aristas y Capacidades:

- **Bodega (s) → Centro A**: Capacidad = 100
- **Bodega (s) → Centro B**: Capacidad = 80
- **Centro A → Tienda 1**: Capacidad = 50
- **Centro A → Tienda 2**: Capacidad = 50
- **Centro B → Tienda 1**: Capacidad = 30
- **Centro B → Tienda 2**: Capacidad = 70
- **Tienda 1 → Sumidero (t)**: Capacidad = 50
- **Tienda 2 → Sumidero (t)**: Capacidad = 70

Este grafo modela una situación donde la **Bodega** distribuye productos a través de dos **Centros** que, a su vez, abastecen a dos **Tiendas**, las cuales finalmente envían los productos al **Sumidero**.


## Resumen del Análisis de Complejidad

El algoritmo Push-Relabel es una técnica eficiente para calcular el flujo máximo en un grafo de flujo. La implementación proporcionada tiene una complejidad total de $O(V^2E)$, donde:

- $V$ es el número de vértices (nodos) en el grafo.
- $E$ es el número de aristas (conexiones) en el grafo.

### Principales Operaciones y sus Complejidades:

1. **Inicialización de Estructuras de Datos:**

   - Matrices `capacity` y `flow` de tamaño $V \times V$: $O(V^2)$.
   - Arreglos `height` y `excessFlow` de tamaño $V$: $O(V)$.

2. **Inicialización del Preflujo:**

   - Establecer la altura del nodo fuente y calcular el preflujo inicial: $O(V)$.

3. **Operaciones de Empuje (`push`):**

   - Cada operación `push` tiene complejidad $O(1)$.
   - Número total de operaciones `push` no saturantes: $O(V^2E)$.

4. **Operaciones de Reetiquetado (`relabel`):**

   - Cada operación `relabel` tiene complejidad $O(V)$ debido al recorrido de los vecinos.
   - Número total de operaciones `relabel`: $O(V^2)$.

5. **Bucle Principal en `getMaxFlow`:**

   - El bucle itera mientras haya vértices activos.
   - Complejidad dominada por las operaciones `push` y `relabel`.

### Cómo se Llega a la Complejidad $O(V^2E)$

- **Operaciones `push` no saturantes:**

  - Cada `push` no saturante incrementa la altura relativa entre dos nodos.
  - Hay un límite en el número de veces que esto puede ocurrir, resultando en $O(V^2E)$ operaciones.

- **Operaciones `relabel`:**

  - Un nodo puede ser reetiquetado hasta $2V - 1$ veces.
  - Con $V$ nodos, esto resulta en $O(V^2)$ operaciones `relabel`.
  - Cada `relabel` es $O(V)$, por lo que el tiempo total es $O(V^3)$.

- **Dominancia de Términos:**

  - En grafos donde $E$ es grande en comparación con $V$, $O(V^2E)$ domina sobre $O(V^3)$.
  - Por lo tanto, la complejidad total se considera $O(V^2E)$.

## Operación General del Algoritmo

1. **Inicialización:**

   - Se inicializa el preflujo desde el nodo fuente, estableciendo su altura y enviando el flujo máximo posible a sus vecinos directos.

2. **Mantenimiento de Vértices Activos:**

   - Se mantiene una lista de vértices activos (aquellos con exceso de flujo) que no son el fuente ni el sumidero.

3. **Operaciones `push` y `relabel`:**

   - Para cada vértice activo, se intenta **empujar** flujo a vecinos más bajos.
   - Si no es posible empujar, se **reetiqueta** el vértice incrementando su altura.

4. **Iteración:**

   - El proceso se repite hasta que no queden vértices activos.
   - El flujo máximo se obtiene del exceso de flujo en el nodo sumidero.

El algoritmo Push-Relabel es eficiente para calcular el flujo máximo en redes de flujo con múltiples caminos y grandes capacidades. La complejidad de $O(V^2E)$ es adecuada para grafos donde el número de aristas no es excesivamente grande en comparación con el cuadrado del número de nodos.
