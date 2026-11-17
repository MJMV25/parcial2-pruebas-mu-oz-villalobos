package parqueadero.unitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import parqueadero.CalculadoraTarifas;

/**
 * Pruebas unitarias de calcularTarifa.
 *
 * <p>Particiones de equivalencia:
 * <ul>
 *   <li>Tipo de vehículo: válido (carro, moto) e inválido (otro texto, vacío, mayúsculas).</li>
 *   <li>Minutos: por debajo del rango (&lt; 0), gracia (0..15), cobro por hora (16..tope),
 *       tope diario (hasta 1440) y por encima del rango (&gt; 1440).</li>
 * </ul>
 * Valores límite: -1, 0, 15, 16, 60, 61, 120, 121, 480, 481, 1440 y 1441.
 */
class CalculadoraTarifasTest {

    private static final String MENSAJE_TIPO_INVALIDO = "Tipo de vehículo no válido";
    private static final String MENSAJE_MINUTOS_INVALIDOS = "Los minutos deben estar entre 0 y 1440";

    private final CalculadoraTarifas calculadora = new CalculadoraTarifas();

    @Test
    @DisplayName("Un carro con una hora y un minuto paga dos horas")
    void carroConUnaHoraYUnMinutoPagaDosHoras() {
        assertEquals(6000, calculadora.calcularTarifa(61, "carro"));
    }

    @Test
    @DisplayName("Una moto con media hora paga una hora")
    void motoConMediaHoraPagaUnaHora() {
        assertEquals(1500, calculadora.calcularTarifa(30, "moto"));
    }

    @Test
    @DisplayName("Rechaza un tipo de vehículo desconocido")
    void rechazaUnTipoDeVehiculoDesconocido() {
        assertThrows(IllegalArgumentException.class, () -> calculadora.calcularTarifa(30, "bicicleta"));
    }

    @ParameterizedTest(name = "{1} con {0} minutos no paga nada (gracia)")
    @CsvSource({
        "0, carro",
        "15, carro",
        "0, moto",
        "15, moto"
    })
    @DisplayName("Dentro de los minutos de gracia no se paga nada")
    void dentroDeLaGraciaNoSePaga(int minutos, String tipoVehiculo) {
        assertEquals(0, calculadora.calcularTarifa(minutos, tipoVehiculo));
    }

    @ParameterizedTest(name = "{1} con {0} minutos paga {2}")
    @CsvSource({
        "16, carro, 3000",
        "16, moto, 1500"
    })
    @DisplayName("Un minuto después de la gracia se cobra una hora completa")
    void unMinutoDespuesDeLaGraciaPagaUnaHora(int minutos, String tipoVehiculo, int valorEsperado) {
        assertEquals(valorEsperado, calculadora.calcularTarifa(minutos, tipoVehiculo));
    }

    @ParameterizedTest(name = "{1} con {0} minutos paga {2}")
    @CsvSource({
        "60, carro, 3000",
        "61, carro, 6000",
        "120, carro, 6000",
        "121, carro, 9000",
        "60, moto, 1500",
        "61, moto, 3000",
        "120, moto, 3000",
        "121, moto, 4500"
    })
    @DisplayName("Cada hora o fracción de hora se cobra completa")
    void cadaFraccionDeHoraSeCobraCompleta(int minutos, String tipoVehiculo, int valorEsperado) {
        assertEquals(valorEsperado, calculadora.calcularTarifa(minutos, tipoVehiculo));
    }

    @ParameterizedTest(name = "carro con {0} minutos paga {1}")
    @CsvSource({
        "480, 24000",
        "481, 25000",
        "1440, 25000"
    })
    @DisplayName("Un carro nunca paga más del tope diario de 25000")
    void carroNoSuperaElTopeDiario(int minutos, int valorEsperado) {
        assertEquals(valorEsperado, calculadora.calcularTarifa(minutos, "carro"));
    }

    @ParameterizedTest(name = "moto con {0} minutos paga {1}")
    @CsvSource({
        "420, 10500",
        "421, 12000",
        "480, 12000",
        "481, 12000",
        "1440, 12000"
    })
    @DisplayName("Una moto nunca paga más del tope diario de 12000")
    void motoNoSuperaElTopeDiario(int minutos, int valorEsperado) {
        assertEquals(valorEsperado, calculadora.calcularTarifa(minutos, "moto"));
    }

    @ParameterizedTest(name = "rechaza {0} minutos")
    @ValueSource(ints = {-1, 1441, Integer.MIN_VALUE, Integer.MAX_VALUE})
    @DisplayName("Rechaza minutos fuera del rango de 0 a 1440")
    void rechazaMinutosFueraDeRango(int minutos) {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> calculadora.calcularTarifa(minutos, "carro"));

        assertEquals(MENSAJE_MINUTOS_INVALIDOS, error.getMessage());
    }

    @ParameterizedTest(name = "rechaza el tipo \"{0}\"")
    @ValueSource(strings = {"bicicleta", "camion", "Carro", "MOTO", " carro"})
    @DisplayName("Rechaza cualquier tipo de vehículo distinto de carro y moto")
    void rechazaTiposDeVehiculoNoValidos(String tipoVehiculo) {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> calculadora.calcularTarifa(30, tipoVehiculo));

        assertEquals(MENSAJE_TIPO_INVALIDO, error.getMessage());
    }

    @ParameterizedTest(name = "rechaza el tipo [{0}]")
    @EmptySource
    @DisplayName("Rechaza un tipo de vehículo vacío")
    void rechazaTipoDeVehiculoVacio(String tipoVehiculo) {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> calculadora.calcularTarifa(30, tipoVehiculo));

        assertEquals(MENSAJE_TIPO_INVALIDO, error.getMessage());
    }

    @Test
    @DisplayName("El tipo de vehículo se valida antes que los minutos")
    void elTipoDeVehiculoSeValidaAntesQueLosMinutos() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> calculadora.calcularTarifa(-1, "bicicleta"));

        assertEquals(MENSAJE_TIPO_INVALIDO, error.getMessage());
    }
}
