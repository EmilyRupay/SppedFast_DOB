package com.speedfast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Clase principal de la Semana 5: simula la coordinación de entregas en
 * SpeedFast usando programación concurrente. Varios {@link Repartidor}
 * (hilos) retiran, en paralelo, pedidos desde una {@link ZonaDeCarga}
 * compartida, la cual controla el acceso concurrente mediante métodos
 * {@code synchronized} para que cada pedido sea atendido por un único
 * repartidor.
 *
 * <p>La demo de la Semana 3 (polimorfismo, {@code ControladorDeEnvios},
 * despacho/cancelación/historial, sin hilos) se conserva en
 * {@link DemoPolimorfismoSemana3} como referencia.</p>
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("############### SPEEDFAST - COORDINACIÓN DE ENTREGAS (SEMANA 5) ###############\n");

        // ------------------------------------------------------------
        // Paso 1: Instancia la ZonaDeCarga (recurso compartido).
        // ------------------------------------------------------------
        ZonaDeCarga zonaDeCarga = new ZonaDeCarga();

        // ------------------------------------------------------------
        // Paso 2: Agrega al menos 5 pedidos al sistema. Se reutiliza la
        // jerarquía polimórfica de Pedido (Comida, Encomienda, Express)
        // diseñada en semanas anteriores: cada uno nace en estado
        // PENDIENTE, listo para ser retirado de la zona de carga.
        // ------------------------------------------------------------
        System.out.println(">>> Ingreso de pedidos a la zona de carga\n");
        zonaDeCarga.agregarPedido(new PedidoComida("P-001", "Ana Torres", "Av. Siempre Viva 742", "Sushi House"));
        zonaDeCarga.agregarPedido(new PedidoEncomienda("P-002", "Bruno Salas", "Los Álamos 123", 18.5));
        zonaDeCarga.agregarPedido(new PedidoExpress("P-003", "Camila Rojas", "Pasaje Las Rosas 45", true));
        zonaDeCarga.agregarPedido(new PedidoComida("P-004", "David Muñoz", "Camino Real 89", "Pizzería Napoli"));
        zonaDeCarga.agregarPedido(new PedidoEncomienda("P-005", "Elena Vidal", "Pje. Los Olivos 55", 6.2));
        zonaDeCarga.agregarPedido(new PedidoExpress("P-006", "Franco Díaz", "Av. Central 900", false));

        System.out.println("\n>>> Pedidos en espera en la zona de carga: " + zonaDeCarga.pedidosRestantes() + "\n");

        // ------------------------------------------------------------
        // Paso 3: Crea e inicia 3 hilos de tipo Repartidor mediante un
        // ExecutorService, que además simplifica esperar a que todos
        // terminen su trabajo.
        // ------------------------------------------------------------
        System.out.println(">>> Los repartidores comienzan a retirar pedidos en paralelo\n");
        ExecutorService pool = Executors.newFixedThreadPool(3);
        pool.submit(new Repartidor("Repartidor-1", zonaDeCarga));
        pool.submit(new Repartidor("Repartidor-2", zonaDeCarga));
        pool.submit(new Repartidor("Repartidor-3", zonaDeCarga));

        // ------------------------------------------------------------
        // Paso 4: Espera la finalización del proceso concurrente antes
        // de continuar (no deja hilos huérfanos ni imprime el mensaje
        // final antes de tiempo).
        // ------------------------------------------------------------
        pool.shutdown();
        boolean terminoATiempo = pool.awaitTermination(1, TimeUnit.MINUTES);
        if (!terminoATiempo) {
            System.out.println("\n[Advertencia] Se agotó el tiempo de espera para los repartidores.");
        }

        System.out.println("\nTodos los pedidos han sido entregados correctamente");
        System.out.println("\n############### FIN DE LA SIMULACIÓN ###############");
    }
}
