package parqueadero;

import java.util.Map;

/** Descuentos que el parqueadero aplica según el tipo de cliente. */
public class Descuentos {

    private static final int CIEN_POR_CIENTO = 100;
    private static final Map<String, Integer> PORCENTAJE_DE_DESCUENTO = Map.of(
            "ninguno", 0,
            "frecuente", 10,
            "convenio", 20);

    /**
     * Aplica al valor el descuento que le corresponde al tipo de cliente.
     * El resultado se redondea hacia abajo al peso entero.
     */
    public int aplicarDescuento(int valor, String tipoCliente) {
        validarValor(valor);
        validarTipoCliente(tipoCliente);

        int porcentajeAPagar = CIEN_POR_CIENTO - PORCENTAJE_DE_DESCUENTO.get(tipoCliente);
        return valor * porcentajeAPagar / CIEN_POR_CIENTO;
    }

    private static void validarValor(int valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("El valor no puede ser negativo");
        }
    }

    private static void validarTipoCliente(String tipoCliente) {
        if (tipoCliente == null || !PORCENTAJE_DE_DESCUENTO.containsKey(tipoCliente)) {
            throw new IllegalArgumentException("Tipo de cliente no válido");
        }
    }
}
