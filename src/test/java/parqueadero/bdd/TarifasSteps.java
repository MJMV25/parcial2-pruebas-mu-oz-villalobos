package parqueadero.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

import parqueadero.CalculadoraTarifas;
import parqueadero.Descuentos;

/** Pasos que conectan los escenarios de tarifas.feature con el código del parqueadero. */
public class TarifasSteps {

    private final CalculadoraTarifas calculadora = new CalculadoraTarifas();
    private final Descuentos descuentos = new Descuentos();

    private String tipoVehiculo;
    private String tipoCliente = "ninguno";
    private int tarifa;
    private int valorAPagar;
    private IllegalArgumentException errorDelSistema;

    @Dado("que un/una {word} entró al parqueadero")
    public void queUnVehiculoEntroAlParqueadero(String vehiculo) {
        tipoVehiculo = vehiculo;
    }

    @Dado("que entró al parqueadero un vehículo de tipo {word}")
    public void queEntroUnVehiculoDeTipo(String vehiculo) {
        tipoVehiculo = vehiculo;
    }

    @Dado("que se debe cobrar una tarifa de {int} pesos")
    public void queSeDebeCobrarUnaTarifaDe(int valor) {
        tarifa = valor;
    }

    @Dado("el cliente es frecuente")
    public void elClienteEsFrecuente() {
        tipoCliente = "frecuente";
    }

    @Dado("el cliente es de convenio")
    public void elClienteEsDeConvenio() {
        tipoCliente = "convenio";
    }

    @Dado("el cliente no tiene descuento")
    public void elClienteNoTieneDescuento() {
        tipoCliente = "ninguno";
    }

    @Cuando("sale después de {int} minutos")
    public void saleDespuesDe(int minutos) {
        try {
            tarifa = calculadora.calcularTarifa(minutos, tipoVehiculo);
            seAplicaElDescuento();
        } catch (IllegalArgumentException error) {
            errorDelSistema = error;
        }
    }

    @Cuando("se aplica el descuento")
    public void seAplicaElDescuento() {
        valorAPagar = descuentos.aplicarDescuento(tarifa, tipoCliente);
    }

    @Entonces("debe pagar {int} pesos")
    public void debePagar(int valorEsperado) {
        assertEquals(valorEsperado, valorAPagar);
    }

    @Entonces("el sistema avisa {string}")
    public void elSistemaAvisa(String mensajeEsperado) {
        assertNotNull(errorDelSistema, "Se esperaba que el sistema rechazara la operación");
        assertEquals(mensajeEsperado, errorDelSistema.getMessage());
    }
}
