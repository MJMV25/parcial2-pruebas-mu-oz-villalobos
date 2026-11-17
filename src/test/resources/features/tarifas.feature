# language: es
Característica: Cobro de la estadía en el parqueadero EstacionaYa
  Como dueño del parqueadero
  Quiero saber cuánto debe pagar cada vehículo al salir
  Para cobrar lo justo a cada cliente y premiar a los clientes fieles

  Esquema del escenario: Una estadía de <minutos> minutos en <vehiculo> cuesta <valor> pesos
    Dado que entró al parqueadero un vehículo de tipo <vehiculo>
    Cuando sale después de <minutos> minutos
    Entonces debe pagar <valor> pesos

    Ejemplos: Los primeros 15 minutos son gratis
      | vehiculo | minutos | valor |
      | carro    | 0       | 0     |
      | carro    | 15      | 0     |
      | moto     | 15      | 0     |

    Ejemplos: Cada hora o fracción de hora se cobra completa
      | vehiculo | minutos | valor |
      | carro    | 16      | 3000  |
      | carro    | 60      | 3000  |
      | carro    | 61      | 6000  |
      | moto     | 16      | 1500  |
      | moto     | 61      | 3000  |

    Ejemplos: Nadie paga más del tope diario
      | vehiculo | minutos | valor |
      | carro    | 480     | 24000 |
      | carro    | 481     | 25000 |
      | carro    | 1440    | 25000 |
      | moto     | 480     | 12000 |
      | moto     | 481     | 12000 |
      | moto     | 1440    | 12000 |

  Escenario: Un cliente frecuente recibe 10 % de descuento
    Dado que un carro entró al parqueadero
    Y el cliente es frecuente
    Cuando sale después de 61 minutos
    Entonces debe pagar 5400 pesos

  Escenario: Un cliente con convenio recibe 20 % de descuento sobre el tope diario
    Dado que un carro entró al parqueadero
    Y el cliente es de convenio
    Cuando sale después de 1440 minutos
    Entonces debe pagar 20000 pesos

  Escenario: Un cliente sin descuento paga la tarifa completa
    Dado que una moto entró al parqueadero
    Y el cliente no tiene descuento
    Cuando sale después de 120 minutos
    Entonces debe pagar 3000 pesos

  Escenario: El descuento se redondea hacia abajo al peso entero
    Dado que se debe cobrar una tarifa de 1999 pesos
    Y el cliente es frecuente
    Cuando se aplica el descuento
    Entonces debe pagar 1799 pesos

  Escenario: El parqueadero no recibe vehículos distintos de carros y motos
    Dado que un bus entró al parqueadero
    Cuando sale después de 30 minutos
    Entonces el sistema avisa "Tipo de vehículo no válido"
