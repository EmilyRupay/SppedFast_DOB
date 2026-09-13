package com.speedfast;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de orquestar el ciclo de vida de los pedidos:
 * despacho, cancelación y consulta de historial.
 *
 * <p>Implementa las interfaces {@link Despachable}, {@link Cancelable}
 * y {@link Rastreable} en lugar de hacerlo directamente en la
 * jerarquía de {@link Pedido}, de modo que la lógica operativa del
 * sistema (qué se hace con un pedido) quede desacoplada de la
 * representación del pedido en sí (qué es un pedido). Esto favorece
 * la mantenibilidad: si cambia la forma de despachar o cancelar,
 * solo se modifica esta clase, sin tocar Pedido ni sus subclases.</p>
 */
public class ControladorDeEnvios implements Despachable, Cancelable, Rastreable {

    private final List<Pedido> historial = new ArrayList<>();

    @Override
    public void despachar(Pedido pedido) {
        pedido.setEstado("Despachado");
        historial.add(pedido);
        System.out.println("[Despacho] El pedido " + pedido.getId()
                + " (" + pedido.getTipo() + ") fue despachado con el repartidor "
                + pedido.getRepartidor() + ".");
    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setEstado("Cancelado");
        historial.add(pedido);
        System.out.println("[Cancelación] El pedido " + pedido.getId()
                + " (" + pedido.getTipo() + ") fue cancelado.");
    }

    @Override
    public void verHistorial() {
        System.out.println("===================== HISTORIAL DE ENTREGAS =====================");
        if (historial.isEmpty()) {
            System.out.println("Aún no hay pedidos procesados.");
        } else {
            for (Pedido p : historial) {
                p.mostrarResumen();
            }
        }
        System.out.println("===================================================================");
    }
}
