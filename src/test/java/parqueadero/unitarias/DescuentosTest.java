package parqueadero.unitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import parqueadero.Descuentos;

/** Pruebas de aplicarDescuento, construidas con TDD. */
class DescuentosTest {

    private final Descuentos descuentos = new Descuentos();

    @Test
    @DisplayName("Un cliente frecuente recibe 10 % de descuento")
    void clienteFrecuenteRecibeDiezPorCiento() {
        assertEquals(2700, descuentos.aplicarDescuento(3000, "frecuente"));
    }

    @Test
    @DisplayName("Un cliente de convenio recibe 20 % de descuento")
    void clienteDeConvenioRecibeVeintePorCiento() {
        assertEquals(20000, descuentos.aplicarDescuento(25000, "convenio"));
    }

    @Test
    @DisplayName("Un cliente sin tipo especial no recibe descuento")
    void clienteNingunoNoRecibeDescuento() {
        assertEquals(3000, descuentos.aplicarDescuento(3000, "ninguno"));
    }

    @Test
    @DisplayName("El resultado se redondea hacia abajo al peso entero")
    void elResultadoSeRedondeaHaciaAbajo() {
        assertEquals(1799, descuentos.aplicarDescuento(1999, "frecuente"));
    }

    @Test
    @DisplayName("Un valor de cero sigue siendo cero con descuento")
    void valorCeroSigueSiendoCero() {
        assertEquals(0, descuentos.aplicarDescuento(0, "convenio"));
    }

    @Test
    @DisplayName("Rechaza un valor negativo")
    void rechazaUnValorNegativo() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> descuentos.aplicarDescuento(-1, "frecuente"));

        assertEquals("El valor no puede ser negativo", error.getMessage());
    }

    @Test
    @DisplayName("Rechaza un tipo de cliente desconocido")
    void rechazaUnTipoDeClienteDesconocido() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> descuentos.aplicarDescuento(3000, "vip"));

        assertEquals("Tipo de cliente no válido", error.getMessage());
    }
}
