# Buscador de Rutas Óptimas en Mapas con Grafos

**Universidad Politécnica Salesiana (UPS)** **Carrera:** Ingeniería en Ciencias de la Computación  
**Asignatura:** Estructura de Datos  
**Profesor:** Ing. Pablo Torres  
**Ciclo/Periodo:** 2do Ciclo  

### Integrantes del Equipo
* Renato Amaya
* Gabriel Cuenca
* Sebastian Arenillas

---

## 1. Descripción del Proyecto

El presente proyecto es una aplicación interactiva desarrollada en Java que simula un sistema de navegación para encontrar rutas óptimas en un mapa. Utiliza la estructura de datos no lineal de **Grafos (Graphs)** para modelar intersecciones (nodos) y calles (aristas). 

El objetivo principal es demostrar la aplicación práctica de los recorridos de grafos mediante la implementación funcional y visual de los algoritmos **BFS (Búsqueda en Anchura)** y **DFS (Búsqueda en Profundidad)**.

---

## 2. Arquitectura del Sistema

El proyecto fue construido utilizando el patrón de diseño **MVC (Modelo-Vista-Controlador)** y los principios **SOLID**, garantizando un código limpio, escalable y mantenible. La estructura de paquetes es la siguiente:

* **`models`**: Contiene la abstracción pura de los datos (`MapPoint` y los estados de visualización).
* **`structures`**: Es el motor matemático del proyecto. Incluye la implementación de grafos basados en Listas de Adyacencia, envoltorios de nodos (`Node`) y el contrato `PathFinder` implementado por los algoritmos.
* **`views`**: Interfaz gráfica de usuario construida con **Java Swing**. Permite cargar mapas de fondo, conectar nodos interactivamente y visualizar animaciones de recorrido.
* **`controllers`**: (`MapController`) Actúa como el puente que procesa las interacciones del usuario en la vista y dispara los algoritmos en el modelo.
* **`persistence`**: Capa dedicada al guardado y carga de grafos desde archivos, separando esta responsabilidad del resto del sistema.

---

## 3. Algoritmos Implementados

Para resolver los caminos dentro del mapa, el sistema utiliza el **Patrón Strategy**, permitiendo intercambiar el método de búsqueda en tiempo de ejecución:

### A. Búsqueda en Anchura (BFS - Breadth-First Search)
* **Estructura dinámica usada:** Cola (`Queue`).
* **Comportamiento:** Explora el mapa por niveles concéntricos.
* **Ventaja:** En un grafo no ponderado como este, BFS **garantiza matemáticamente encontrar el camino más corto** (con el menor número de aristas/saltos) entre el punto de inicio y el destino.

### B. Búsqueda en Profundidad (DFS - Depth-First Search)
* **Estructura dinámica usada:** Pila (`Stack`).
* **Comportamiento:** Explora un camino hasta llegar a un extremo o pared, retrocediendo solo cuando no hay más opciones.
* **Ventaja:** Es eficiente para resolver laberintos completos o verificar la existencia de una ruta, aunque no asegura que el camino encontrado sea el más corto.

---

## 4. Instalación y Ejecución

### Prerrequisitos
* **Java Development Kit (JDK):** Versión 8 o superior.
* **Librerías externas:** `flatlaf-3.5.4.jar` (Incluido en la carpeta `lib`). Esta librería es esencial para renderizar el tema visual moderno de la interfaz.

### Pasos para ejecutar
1. Clonar este repositorio: `git clone [Enlace de tu repositorio]`
2. Abrir el proyecto en un IDE (como Visual Studio Code, IntelliJ IDEA o Eclipse).
3. Asegurarse de agregar `flatlaf-3.5.4.jar` al *Build Path* o a las librerías referenciadas (Referenced Libraries) del proyecto.
4. Compilar y ejecutar la clase principal: `App.java`.

---

## 5. Conclusiones

* La separación de la información pura (`MapPoint`) de su abstracción matemática (`Node`) demostró ser una técnica eficaz para mantener el modelo limpio y enfocado.
* El uso de `LinkedHashSet` facilitó enormemente la integración del motor de algoritmos con la Interfaz Gráfica, permitiendo que la vista dibuje la exploración paso a paso manteniendo la integridad secuencial de los datos.
* Se evidenció de forma empírica la diferencia de comportamiento entre pilas (LIFO) y colas (FIFO) al impactar directamente en el tipo de ruta que dibujan DFS y BFS, respectivamente.
