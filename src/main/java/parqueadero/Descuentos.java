package parqueadero;

import java.util.Map;

/** Descuentos según el tipo de cliente. */
public class Descuentos {

    private static final Map<String, Integer> PORCENTAJES = Map.of("ninguno", 0, "frecuente", 10, "convenio", 20);

    public int aplicarDescuento(int valor, String tipoCliente) {
        int porcentaje = PORCENTAJES.get(tipoCliente);
        return valor - valor * porcentaje / 100;
    }
}
