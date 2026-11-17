package parqueadero.unitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
