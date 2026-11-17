package parqueadero.unitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import parqueadero.Descuentos;

/** Pruebas de aplicarDescuento, construidas con TDD. */
class DescuentosTest {

    private static final String MENSAJE_CLIENTE_INVALIDO = "Tipo de cliente no válido";

    private final Descuentos descuentos = new Descuentos();

    @ParameterizedTest(name = "{0} con cliente {1} paga {2}")
    @CsvSource({
        "3000, ninguno, 3000",
        "3000, frecuente, 2700",
        "25000, convenio, 20000"
    })
    @DisplayName("Cada tipo de cliente recibe su porcentaje de descuento")
    void cadaTipoDeClienteRecibeSuDescuento(int valor, String tipoCliente, int valorEsperado) {
        assertEquals(valorEsperado, descuentos.aplicarDescuento(valor, tipoCliente));
    }

    @ParameterizedTest(name = "{0} con cliente {1} paga {2}")
    @CsvSource({
        "1999, frecuente, 1799",
        "1999, convenio, 1599",
        "1, frecuente, 0"
    })
    @DisplayName("El resultado se redondea hacia abajo al peso entero")
    void elResultadoSeRedondeaHaciaAbajo(int valor, String tipoCliente, int valorEsperado) {
        assertEquals(valorEsperado, descuentos.aplicarDescuento(valor, tipoCliente));
    }

    @ParameterizedTest(name = "cliente {0}")
    @ValueSource(strings = {"ninguno", "frecuente", "convenio"})
    @DisplayName("Un valor de cero sigue siendo cero con descuento")
    void valorCeroSigueSiendoCero(String tipoCliente) {
        assertEquals(0, descuentos.aplicarDescuento(0, tipoCliente));
    }

    @Test
    @DisplayName("Rechaza un valor negativo")
    void rechazaUnValorNegativo() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> descuentos.aplicarDescuento(-1, "frecuente"));

        assertEquals("El valor no puede ser negativo", error.getMessage());
    }

    @ParameterizedTest(name = "rechaza el cliente \"{0}\"")
    @ValueSource(strings = {"vip", "Frecuente", "CONVENIO"})
    @DisplayName("Rechaza un tipo de cliente desconocido")
    void rechazaUnTipoDeClienteDesconocido(String tipoCliente) {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> descuentos.aplicarDescuento(3000, tipoCliente));

        assertEquals(MENSAJE_CLIENTE_INVALIDO, error.getMessage());
    }

    @ParameterizedTest(name = "rechaza el cliente [{0}]")
    @NullAndEmptySource
    @DisplayName("Rechaza un tipo de cliente vacío o nulo")
    void rechazaUnTipoDeClienteVacioONulo(String tipoCliente) {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> descuentos.aplicarDescuento(3000, tipoCliente));

        assertEquals(MENSAJE_CLIENTE_INVALIDO, error.getMessage());
    }
}
