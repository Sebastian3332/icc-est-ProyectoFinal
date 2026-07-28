# Buscador de Rutas Óptimas en Mapas con Grafos

![alt text](assets/LogoUniversidad.png)

**Universidad Politécnica Salesiana (UPS)** **Carrera:** Ingeniería en Ciencias de la Computación  
**Asignatura:** Estructura de Datos  
**Profesor:** Ing. Pablo Torres  
**Ciclo/Periodo:** 2do Ciclo  

### Integrantes del grupo
* Renato Amaya
* Gabriel Cuenca
* Sebastian Arenillas

### Correos institucionales
* ramayas@est.ups.edu.ec
* gcuencao1@est.ups.edu.ec
* sarenillas@est.ups.edu.ec

---
## Índice
1. [Objetivo](#objetivo)
2. [Marco Teórico](#marco-teórico)
   - [Grafos](#grafos)
   - [Búsqueda en Anchura (BFS)](#búsqueda-en-anchura-bfs)
   - [Búsqueda en Profundidad (DFS)](#búsqueda-en-profundidad-dfs)
3. [Tecnologías Utilizadas](#tecnologías-utilizadas)
4. [Diagrama UML y Explicación](#diagrama-uml-y-explicación)

---
## 1. Objetivo
Desarrollar el sistema computacional "Buscador de Rutas Óptimas en Mapas Urbanos" mediante la aplicación de fundamentos de teoría de grafos y algoritmos de búsqueda no informada. El propósito principal es modelar un entorno espacial para resolver eficientemente el problema de trazado de trayectorias entre distintos puntos, consolidando el manejo de estructuras de datos complejas bajo la arquitectura Modelo-Vista-Controlador (MVC).

---

## 2. Marco Teórico

### 2.1 Grafos
Un grafo es una estructura de datos discreta utilizada para representar relaciones entre objetos. Consiste en un conjunto de **vértices (nodos)** y un conjunto de **aristas (enlaces)** que conectan pares de vértices. En el contexto de este mapeo urbano, los nodos representan las intersecciones o puntos clave, mientras que las aristas representan las calles o caminos disponibles que los conectan.

### 2.2 Búsqueda en Anchura (BFS - Breadth-First Search)
Es un algoritmo de recorrido que comienza en un nodo origen y explora todos sus nodos vecinos a la profundidad actual antes de pasar a los nodos del siguiente nivel. 
*   **Mecanismo:** Utiliza una estructura de datos tipo **Cola (Queue)** operando bajo el principio FIFO (First In, First Out).
*   **Propósito:** Es ideal para determinar el camino más corto en grafos no ponderados (donde la distancia entre cualquier par de nodos adyacentes es constante), garantizando la ruta más óptima en cantidad de "saltos".

### 2.3 Búsqueda en Profundidad (DFS - Depth-First Search)
Es un algoritmo que prioriza explorar tan profundo como sea posible a lo largo de cada rama antes de realizar un retroceso (backtracking). 
*   **Mecanismo:** Se implementa típicamente de forma recursiva o utilizando una estructura de datos tipo **Pila (Stack)** operando bajo el principio LIFO (Last In, First Out).
*   **Propósito:** Se utiliza para explorar exhaustivamente las topologías, encontrar rutas alternativas o determinar si existe conexión entre dos puntos, aunque no garantiza que la primera ruta encontrada sea la más corta.

---

## 3. Tecnologías Utilizadas
*   **Lenguaje de Programación:** Java
*   **Arquitectura de Software:** MVC (Modelo-Vista-Controlador)
*   **Entornos de Desarrollo (IDE):** NetBeans / Visual Studio Code
*   **Control de Versiones:** Git y GitHub

---

## 4. Diagrama UML y Explicación

![alt text](assets/diagramaUML.png)
*(Nota: Reemplazar la ruta de la imagen con la ubicación real del diagrama exportado)*

**Explicación de la Arquitectura y Diagrama:**
El diseño del sistema se ha estructurado dividiendo las responsabilidades lógicas para asegurar un código modular y escalable:

1.  **Capa de Modelo (`Model`):** Contiene la lógica central de las estructuras de datos. Aquí residen clases fundamentales como `Grafo`, `Nodo` (que almacena coordenadas o nombres de ubicaciones) y `Arista`. Estas clases encapsulan el estado del mapa urbano y gestionan las listas de adyacencia.
2.  **Capa de Controlador (`Controller`):** Actúa como el intermediario lógico. Incluye las clases encargadas de ejecutar los algoritmos matemáticos (BFS y DFS). Recibe las peticiones (como el punto A y el punto B), consulta al Modelo la estructura del grafo, calcula la ruta y envía los resultados procesados.
3.  **Capa de Vista (`View`):** Comprende la interfaz gráfica de usuario. Es responsable de capturar las interacciones, mostrar el mapa interactivo y renderizar visualmente el camino final calculado por el controlador.

---

## 5. Arquitectura del Sistema

El proyecto fue construido utilizando el patrón de diseño **MVC (Modelo-Vista-Controlador)** y los principios **SOLID**, garantizando un código limpio, escalable y mantenible. La estructura de paquetes es la siguiente:

* **`models`**: Contiene la abstracción pura de los datos (`MapPoint` y los estados de visualización).
* **`structures`**: Es el motor matemático del proyecto. Incluye la implementación de grafos basados en Listas de Adyacencia, envoltorios de nodos (`Node`) y el contrato `PathFinder` implementado por los algoritmos.
* **`views`**: Interfaz gráfica de usuario construida con **Java Swing**. Permite cargar mapas de fondo, conectar nodos interactivamente y visualizar animaciones de recorrido.
* **`controllers`**: (`MapController`) Actúa como el puente que procesa las interacciones del usuario en la vista y dispara los algoritmos en el modelo.
* **`persistence`**: Capa dedicada al guardado y carga de grafos desde archivos, separando esta responsabilidad del resto del sistema.

---


---

---

## 10. Conclusiones

* La separación de la información pura (`MapPoint`) de su abstracción matemática (`Node`) demostró ser una técnica eficaz para mantener el modelo limpio y enfocado.
* El uso de `LinkedHashSet` facilitó enormemente la integración del motor de algoritmos con la Interfaz Gráfica, permitiendo que la vista dibuje la exploración paso a paso manteniendo la integridad secuencial de los datos.
* Se evidenció de forma empírica la diferencia de comportamiento entre pilas (LIFO) y colas (FIFO) al impactar directamente en el tipo de ruta que dibujan DFS y BFS, respectivamente.
