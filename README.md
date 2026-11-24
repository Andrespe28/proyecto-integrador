# Sistema de ventas e inventario de CalzaTodo
Proyecto académico para el curso de **Programación Orientada a Objetos (POO)** — Universidad de La Sabana, 2026.

# Integrantes
María José López Sánchez (majolosa1408-a11y) <br>
María Paula Gómez Mateus (PAU1724) <br>
Andres Felipe Peña Cristancho (Andrespe28) <br>
David Eduardo Diaz Arciniegas (sarchyx6) 

## Objetivo
El objetivo principal es consolidar el uso de herencia, polimorfismo, encapsulamiento e interfaces en un sistema de calzado con roles (Vendedor y Administrador), medios de pago y gestión de ventas, entendiendo en la práctica cómo conviven los datos dinámicos en ArrayList con el almacenamiento permanente en archivos de texto.

---

## Tabla de contenido

1. [Dominio del problema](#dominio-del-problema)
2. [Conceptos POO demostrados](#conceptos-poo-demostrados)
3. [Arquitectura del sistema](#arquitectura-del-sistema)
4. [Estructura del proyecto](#estructura-del-proyecto)
5. [Jerarquía de clases](#jerarquía-de-clases)
6. [Formato de archivos de datos](#formato-de-archivos-de-datos)
7. [Cómo ejecutar el proyecto](#cómo-ejecutar-el-proyecto)
8. [ArrayList vs Archivo de texto](#arraylist-vs-archivo-de-texto)
9. [Reglas de negocio](#reglas-de-negocio)

---

## Dominio del problema

El sistema modela la operación básica de la **tienda de zapatos Calzatodo**. Los principales actores y entidades son:

| Entidad | Descripción |
|---|---|
| `Administrador` | Usuario con funciones de control y gestión, incluyendo el cierre de caja diario, la consulta/carga de ventas y comisiones. |
| `Vendedor` | Usuario encargado de registrar ventas e intercambios y acumular comisiones.|
| `Zapato` | Producto de calzado con atributos como código, tipo, talla, color, precio, cantidad disponible, sección de bodega y sexo/categoría. |
| `Inventario` | Administra la colección de zapatos (`listaZapatos`) y la lectura/escritura del inventario en archivo. |
| `Ventas` | Representa una venta o transacción con comprador, teléfono, vendedor asociado, fecha, total, detalles (`DetalleVenta`) y método de pago (`MetodoPago`). |

---

## Conceptos POO demostrados

### Herencia (IS-A)
```
Usuario (Clase Padre)
├── Administrador
└── Vendedor

MetodoPago (abstracto)
├── PagoEfectivo
├── PagoTrajeta
└── PagoTransferencia

```
- `Usuario` es la clase padre de `Vendedor` y `Administrador`: ambos comparten atributos/comportamientos de usuario.
- `MetodoPago` es la clase abstracta base de `PagoEfectivo`, `PagoTarjeta` y `PagoTransferencia`.

### Abstracción
`MetodoPago` des una clase abstracta: define el comportamiento común que deben cumplir los medios de pago sin fijar todos los detalles de cada uno:
+ getTipoPago() - identifica el tipo de pago.
+ procesarPago() - ejecuta la lógica propia de cada método de pago.

### Polimorfismo
El mismo tipo base (`MetodoPago` o `Usuario`) permite tratar distintos objetos concretos de forma uniforme:

```java
MetodoPago pago1 = new PagoEfectivo(50000, 60000);
MetodoPago pago2 = new PagoTarjeta(50000, "1234");
MetodoPago pago3 = new PagoTransferencia(50000, "Bancolombia");

pago1.getTipoPago(); // → "Efectivo"
pago2.getTipoPago(); // → "Tarjeta"
pago3.getTipoPago(); // → "Transferencia"
```

En `Administrador.realizarCierreDeCaja()`, el cierre recorre las ventas del día y clasifica los montos usando getTipoPago() en lugar de depender de instanceof.

### Encapsulamiento

+ Los atributos están protegidos/privados y los cambios de estado pasan por validaciones o reglas de negocio. 
+ En `PagoEfectivo.procesarPago()`, se valida que dineroRecibido no sea menor que monto antes de calcular el cambio.
+ En `MetodoPago`, el constructor valida que el monto no sea negativo. 
+ En `Venta, setTotal(...)` válida que el total no sea negativo.

### Interfaces
El proyecto tiene solo una interfaz utilizada por dos clases `Venta` e `Intercambio`

| Interfaz | Propósito |
|---|---|
|Guardable|Contrato que permite `guardarEnArchivo()` y `leerDeArchivo()` .|

### Clase anidada
La clase `DetalleVenta` existe dentro de `Venta` para mostrar los productos de una venta.
Se crea como una clase anidada, ya que, `DetalleVenta` no tiene sentido de existir sin una venta.

### Composición y agregación

* `Venta` se asocia con `Vendedor` y `MetodoPago`
* `Inventario` agrupa objetos de la clase `Zapato`
* `Administrador` gestiona las ventas y el cierre de caja. 

### Miembros estáticos (static)

Atributos y métodos de clases (por ejemplo, contadores como contadorVenta o porcentajes de comisión), cuidando que este no se cambie y sea el mismo para cada objeto o clase, y también al crear o cargar los registros.

## Arquitectura del sistema
El proyecto organiza la lógica de dominio, la administración, la interfaz por consola y la persistencia en archivos de texto:

```

┌─────────────────────────────────────────────────────────────┐
│                     CAPA UI / CONSOLA                       │
│  Main y menús de interacción por consola                    │
│  Responsabilidad: entrada/salida con el usuario.            │
├─────────────────────────────────────────────────────────────┤
│               CAPA DOMINIO Y GESTIÓN                        │
│  Usuario, Vendedor, Administrador, Zapato, Inventario,      │
│  Venta, DetalleVenta, MetodoPago (Efectivo/Tarjeta/Transf)  │
│  Responsabilidad: entidades, reglas de negocio y cierres.   │
├─────────────────────────────────────────────────────────────┤
│               CAPA PERSISTENCIA                             │
│  Guardable (interfaz), FileManage, archivos .txt            │
│  Responsabilidad: leer y escribir líneas en disco.          │
└─────────────────────────────────────────────────────────────┘
``` 

**Flujo de una operación** (Ejemplo: Registrar una venta)
   
```  
 [Usuario en consola]
    │  Ingresa datos de la venta, comprador, vendedor,
    │  ítems (DetalleVenta) y método de pago (Efectivo/Tarjeta/Transferencia)
 [Capa UI / Menú en consola]
    │  Lee entradas y llama a la operación correspondiente
    ▼
 [Capa Dominio — Venta / MetodoPago / Vendedor]
    │  1. Crea objeto Venta y sus DetalleVenta asociados
    │  2. Valida reglas (ej. total no negativo; en PagoEfectivo, dineroRecibido >= monto)
    │  3. Calcula cambio (si aplica) y asocia el Vendedor y MetodoPago
    │  4. Registra/acumula la comisión del Vendedor
    ▼
 [Capa Dominio — Inventario]
    │  1. Busca el Zapato en listaZapatos (ArrayList en memoria)
    │  2. Verifica disponibilidad y descuenta unidades vendidas
    ▼
 [Capa Persistencia — Guardable + FileManage]
    │  1. Invoca guardarEnArchivo() en Venta / Inventario
    │  2. Guarda los objetos por líneas separadas por "|"
    │  3. FileManage escribe en ventas.txt, inventario_zapatos.txt y comisiones.txt
    ▼
 [Archivos .txt en disco]
    │  Datos guardados de forma permanente para próximas ejecuciones
    ▼
 [Capa UI / Menú en consola]
       Muestra confirmación, resumen de la venta y/o cambio en pantalla    
```  

## Estructura del proyecto
```
proyecto-integrador/
└── src/
    ├── Main.java                                 Punto de inicio - Lanza Menu
    │
    ├── files/
    │    ├── comisiones.txt                       4 registros base
    │    ├── inventario_zapatos.txt               3 registros base
    │    └── ventas.txt                           4 registros base
    │
    ├── interfaces/
    │    └── Guardable.java                       Contrato para interactuar con los archivos .txt
    │
    ├── modelos/
    │    ├── Administrador.java
    │    ├── Inventario.java
    │    ├── MetodoPago.java
    │    ├── PagoEfectivo.java
    │    ├── PagoTarjeta.java
    │    ├── PagoTransferencia.java               
    │    ├── Usuario.java
    │    ├── Vendedor.java
    │    ├── Venta.java                           (incluye DetalleVenta)
    │    └── Zapato.java
    │
    ├── persistencia/
    │   └── FileManage.java                       Leer/escribir/agregar líneas en archivos .txt
    │
    ├── ui/
    │    ├── Menu.java                            Inicializa todos los menus
    │    ├── MenuAdmi.java                        
    │    └── MenuVendedor.java

```
## Jerarquía de clases //creo que es el UML
## Formato de archivos de datos
## Cómo ejecutar el proyecto
## ArrayList vs Archivo de texto
## Reglas de negocio
