package parqueadero;

import java.util.List;
import java.util.Map;

/** Reportes de las estadías registradas en el parqueadero. */
public class Reportes {

    private static final String CARRO = "carro";
    private static final String MOTO = "moto";
    private static final String TOTAL_RECAUDADO = "total_recaudado";

    private final CalculadoraTarifas calculadora = new CalculadoraTarifas();

    /**
     * Resume las estadías del día: cuántos carros y cuántas motos entraron
     * y cuánto se recaudó en total. Cada cobro lo calcula la calculadora de tarifas.
     */
    public Map<String, Integer> resumen(List<Registro> registros) {
        return Map.of(
                CARRO, contarVehiculos(registros, CARRO),
                MOTO, contarVehiculos(registros, MOTO),
                TOTAL_RECAUDADO, calcularTotalRecaudado(registros));
    }

    private static int contarVehiculos(List<Registro> registros, String tipoVehiculo) {
        return (int) registros.stream()
                .filter(registro -> registro.tipo().equals(tipoVehiculo))
                .count();
    }

    private int calcularTotalRecaudado(List<Registro> registros) {
        return registros.stream()
                .mapToInt(registro -> calculadora.calcularTarifa(registro.minutos(), registro.tipo()))
                .sum();
    }
}
