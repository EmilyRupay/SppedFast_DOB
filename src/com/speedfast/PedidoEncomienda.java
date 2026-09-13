package com.speedfast;

/**
 * Pedido de tipo Encomienda. La asignación automática prioriza
 * repartidores con vehículo de carga, y el tiempo de entrega es
 * mayor debido al peso/volumen del paquete y la distancia habitual.
 */
public class PedidoEncomienda extends Pedido {

    private double pesoKg;
    private static final String[] REPARTIDORES_DISPONIBLES = {
            "Marcela Soto (furgón)", "Pedro Araya (furgón)", "Tomás Reyes (camioneta)"
    };

    public PedidoEncomienda(String id, String cliente, String direccion, double pesoKg) {
        super(id, cliente, direccion);
        this.pesoKg = pesoKg;
    }

    @Override
    public void asignarRepartidor() {
        // Regla de negocio: para encomiendas se asigna un repartidor
        // con vehículo de carga; a mayor peso, se prioriza camioneta.
        int indice = pesoKg > 15
                ? REPARTIDORES_DISPONIBLES.length - 1
                : (int) (Math.random() * (REPARTIDORES_DISPONIBLES.length - 1));
        this.repartidor = REPARTIDORES_DISPONIBLES[indice];
        this.estado = "Asignado";
        System.out.println("[Asignación automática] Repartidor de carga \""
                + this.repartidor + "\" asignado al pedido de encomienda " + id + ".");
    }

    @Override
    public int calcularTiempoEntrega() {
        // Encomienda: tiempo base más alto, incrementa según el peso.
        int extraPorPeso = (int) (pesoKg * 2);
        return 40 + extraPorPeso;
    }

    @Override
    public String getTipo() {
        return "Encomienda";
    }

    public double getPesoKg() {
        return pesoKg;
    }
}
