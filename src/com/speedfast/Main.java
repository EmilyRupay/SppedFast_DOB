package com.speedfast;

/**
 * Clase principal que simula el funcionamiento del sistema de reparto
 * SpeedFast, demostrando polimorfismo, abstracción e interfaces
 * trabajando en conjunto.
 */
public class Main {

    public static void main(String[] args) {

        ControladorDeEnvios controlador = new ControladorDeEnvios();

        System.out.println("############### SISTEMA DE REPARTO SPEEDFAST ###############\n");

        // ------------------------------------------------------------
        // 1) Creación de pedidos de distintos tipos (polimorfismo:
        //    todos son tratados como Pedido, pero cada uno tiene su
        //    propio comportamiento).
        // ------------------------------------------------------------
        Pedido pedidoComida = new PedidoComida(
                "P-001", "Ana Torres", "Av. Siempre Viva 742", "Sushi House");

        Pedido pedidoEncomienda = new PedidoEncomienda(
                "P-002", "Bruno Salas", "Los Álamos 123", 18.5);

        Pedido pedidoExpress = new PedidoExpress(
                "P-003", "Camila Rojas", "Pasaje Las Rosas 45", true);

        Pedido pedidoComida2 = new PedidoComida(
                "P-004", "David Muñoz", "Camino Real 89", "Pizzería Napoli");

        Pedido[] pedidos = { pedidoComida, pedidoEncomienda, pedidoExpress, pedidoComida2 };

        // ------------------------------------------------------------
        // 2) Asignación automática de repartidor (sobrescritura:
        //    cada subclase ejecuta su propia versión de
        //    asignarRepartidor()).
        // ------------------------------------------------------------
        System.out.println(">>> Asignación automática de repartidores");
        for (Pedido p : pedidos) {
            p.asignarRepartidor();
        }

        // ------------------------------------------------------------
        // 3) Asignación manual de repartidor (sobrecarga:
        //    asignarRepartidor(String nombre)).
        // ------------------------------------------------------------
        System.out.println("\n>>> Asignación manual de repartidor");
        pedidoExpress.asignarRepartidor("Rodrigo Peña (refuerzo manual)");

        // ------------------------------------------------------------
        // 4) Cálculo y visualización del tiempo estimado de entrega
        //    (mostrarResumen() es común, calcularTiempoEntrega() es
        //    polimórfico).
        // ------------------------------------------------------------
        System.out.println("\n>>> Resumen y tiempo estimado de cada pedido");
        for (Pedido p : pedidos) {
            p.mostrarResumen();
        }

        // ------------------------------------------------------------
        // 5) Despacho de pedidos (interfaz Despachable).
        // ------------------------------------------------------------
        System.out.println("\n>>> Despacho de pedidos");
        controlador.despachar(pedidoComida);
        controlador.despachar(pedidoExpress);
        controlador.despachar(pedidoComida2);

        // ------------------------------------------------------------
        // 6) Cancelación de un pedido (interfaz Cancelable).
        // ------------------------------------------------------------
        System.out.println("\n>>> Cancelación de pedido");
        controlador.cancelar(pedidoEncomienda);

        // ------------------------------------------------------------
        // 7) Visualización del historial de entregas (interfaz
        //    Rastreable), almacenado en un ArrayList dentro del
        //    controlador.
        // ------------------------------------------------------------
        System.out.println();
        controlador.verHistorial();

        System.out.println("\n############### FIN DE LA SIMULACIÓN ###############");
    }
}
