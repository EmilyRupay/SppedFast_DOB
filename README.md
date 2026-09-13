# SpeedFast — Sincronizando procesos en sistemas concurrentes (Semana 5)

Continuación del caso **SpeedFast**: se agrega una capa de **programación
concurrente en Java** (`Thread`, `Runnable`, sincronización) para resolver
el problema de repartidores que acceden simultáneamente a la **zona de
carga**, evitando errores y entregas duplicadas.

## Contenido del repositorio

```
speedfast-semana5/
├── src/com/speedfast/
│   ├── EstadoPedido.java               (enum: PENDIENTE, EN_REPARTO, ENTREGADO)
│   ├── ZonaDeCarga.java                (recurso compartido, métodos synchronized)
│   ├── Repartidor.java                 (implements Runnable)
│   ├── Pedido.java                     (clase abstracta; ahora con toString())
│   ├── PedidoComida.java               (subclase — Semana 3)
│   ├── PedidoEncomienda.java           (subclase — Semana 3)
│   ├── PedidoExpress.java              (subclase — Semana 3)
│   ├── Despachable.java                (interfaz — Semana 3)
│   ├── Cancelable.java                 (interfaz — Semana 3)
│   ├── Rastreable.java                 (interfaz — Semana 3)
│   ├── ControladorDeEnvios.java        (implementa las 3 interfaces — Semana 3)
│   ├── Main.java                       (punto de entrada: simulación concurrente)
│   └── DemoPolimorfismoSemana3.java    (demo original de la Semana 3, sin hilos)
├── docs/
│   └── diagrama_clases.png
└── README.md
```

## Cómo ejecutar

**Desde IntelliJ IDEA:**
1. Abrir la carpeta `speedfast-semana5` como proyecto.
2. Marcar `src` como *Sources Root* si no se detecta automáticamente.
3. Ejecutar la clase `com.speedfast.Main`.

**Desde línea de comandos** (requiere JDK 17+):
```bash
cd speedfast-semana5
javac -d out $(find src -name "*.java")
java -cp out com.speedfast.Main
```

## Diseño de la solución (Pasos 1 a 5 del enunciado)

**Paso 1-2 — `Pedido` y `EstadoPedido`.** `Pedido` (heredado de la Semana 3)
ya contaba con `id`, `direccion`, `estado`, constructor, getters, setters y
`setEstado(String)`; se le agregó `toString()`. El estado ahora se
inicializa en `EstadoPedido.PENDIENTE` y transita según el enum
`EstadoPedido { PENDIENTE, EN_REPARTO, ENTREGADO }`, que evita errores de
tipeo al comparar/asignar estados.

**Paso 3 — `ZonaDeCarga` (recurso compartido).** Guarda los pedidos
pendientes en una `List<Pedido>` protegida por los métodos
`synchronized void agregarPedido(Pedido p)` y `synchronized Pedido
retirarPedido()`. Al ser `synchronized`, solo un hilo a la vez puede
modificar la lista: si dos repartidores llaman a `retirarPedido()` "al
mismo tiempo", uno espera a que el otro termine, por lo que **nunca dos
hilos retiran el mismo pedido** (se evita la condición de carrera y el
retiro doble).

**Paso 4 — `Repartidor implements Runnable`.** Cada repartidor corre en su
propio hilo y, en un bucle, retira pedidos de la `ZonaDeCarga` hasta que
esta queda vacía (`retirarPedido()` devuelve `null`). Por cada pedido:
cambia su estado a `EN_REPARTO` y lo imprime, simula la entrega con
`Thread.sleep(...)` (tiempo aleatorio entre 1 y 3 segundos), y finalmente
cambia el estado a `ENTREGADO` e imprime el mensaje final.

**Paso 5 — `Main`.** Instancia la `ZonaDeCarga`, agrega 6 pedidos (reutiliza
`PedidoComida`, `PedidoEncomienda` y `PedidoExpress` de la Semana 3, para
mantener el polimorfismo del diseño anterior), crea y lanza 3 hilos
`Repartidor` mediante un `ExecutorService`, espera su finalización con
`awaitTermination(...)` y muestra el mensaje final:
`"Todos los pedidos han sido entregados correctamente"`.

La demo original de la Semana 3 (`ControladorDeEnvios`, despacho,
cancelación e historial, sin hilos) se conserva intacta en
`DemoPolimorfismoSemana3.java` como referencia del diseño orientado a
objetos sobre el que se construyó esta solución.

## Ejemplo de salida por consola (resumido)

```
>>> Ingreso de pedidos a la zona de carga
[ZonaDeCarga] Pedido P-001 (Comida) ingresó a la zona de carga. Estado: PENDIENTE.
...
>>> Los repartidores comienzan a retirar pedidos en paralelo
[Repartidor-2] Retiró el pedido P-001 (Comida) -> estado: EN_REPARTO.
[Repartidor-2] Entregó el pedido P-001 en "Av. Siempre Viva 742" -> estado: ENTREGADO.
...
Todos los pedidos han sido entregados correctamente
```

(El orden exacto de las líneas varía en cada ejecución porque los hilos
corren en paralelo; eso es justamente lo que demuestra la concurrencia.)

## Pendiente antes de la entrega

- [ ] Subir este proyecto a un repositorio público de GitHub, dentro de
      una carpeta llamada `semana 5`.
- [ ] Comprimir el proyecto en `.zip` o `.rar`, asegurándose de que
      compile correctamente, y subirlo también al AVA.
- [ ] Copiar el enlace del repositorio y entregarlo en el AVA (ver
      "Recordatorio: ¿Cómo crear un repositorio en GitHub?" en las
      instrucciones de la actividad).
