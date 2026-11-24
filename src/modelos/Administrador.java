package modelos;

import persistencia.FileManage;
import java.time.LocalDate;
import java.io.*;
import java.util.ArrayList;
import java.time.YearMonth;
import repositorios.ArchivoMensualRepository;

public class Administrador extends Usuario {
    private ArrayList<Venta> listaVentas = new ArrayList<>();
    private static final String rutaInventario = "src/files/inventario_zapatos.txt";
    private FileManage fileManager = new FileManage("ventas.txt");
    private FileManage fileManagerComisiones = new FileManage("comisiones.txt");
    private ArchivoMensualRepository archivoMensualRepository = new ArchivoMensualRepository();
    private static double salarioBase=2000000;
    public Administrador(String cedula, String nombre) {
        super(cedula, nombre);
    }

    public void setListaVentas(ArrayList<Venta> listaVentas) {
        this.listaVentas = listaVentas;
    }

    public void agregarVenta(Venta venta){
        listaVentas.add(venta);
    }

    private static final double comisionPorVenta = 3000;

    public double calcularComision(Vendedor vendedor) {
        return vendedor.getVentasRealizadas() * comisionPorVenta;
    }

    public double calcularSalarioTotal(Vendedor vendedor) {
        double comision = calcularComision(vendedor);
        return salarioBase + comision;
    }

    public Venta buscarVenta(int numVenta) {
        for (Venta v : listaVentas) {
            if(numVenta==v.getNumeroVenta()){
                return v;
            }
        }
        return null;
    }

    public ArrayList<Venta> getListaVentas() {
        return listaVentas;
    }

    public static double getSalarioBase() {
        return salarioBase;
    }

    public void mostrarVentas(){
        System.out.println("Registro de ventas:");
        for(Venta v : listaVentas){
            System.out.printf("Venta #%d: Comprador: %s, Teléfono: %s, Vendedor: %s, Total: $%.2f\n",v.getNumeroVenta(),v.getNombreComprador(),v.getTelefonoComprador(),v.getVendedor(),v.getTotal());
        }
    }

    public void vendedorConMasVentas(ArrayList<Vendedor> vendedores) {
        Vendedor mayor = null;
        int masVentas = 0;

        for (Vendedor vendedor : vendedores) {
            int contador = 0;
            for (Venta venta : listaVentas) {
                if (venta.getFecha() != null
                        && venta.getFecha().equals(LocalDate.now())
                        && venta.getVendedor() == vendedor) {
                    contador++;
                }
            }
            if (contador > masVentas) {
                masVentas = contador;
                mayor = vendedor;
            }
        }
        if (mayor == null) {
            System.out.println("No hay ventas registradas hoy.");
        } else {
            System.out.println("Vendedor con más ventas: "
                    + mayor.getNombre());
        }
    }

    public void leerVentasDeArchivo(ArrayList<Vendedor> vendedores) {
        ArrayList<String> lineas = fileManager.getDataOfFile();
        for (String linea : lineas) {
            if (linea == null || linea.trim().isEmpty()) {
                continue;
            }

            String[] datos = linea.split("\\|");

            int numeroVenta = Integer.parseInt(datos[0]);
            String comprador = datos[1];
            String telefono = datos[2];
            String nombreVendedor = datos[3];
            double total = Double.parseDouble(datos[4]);
            LocalDate fechaVenta = LocalDate.parse(datos[5].trim());
            MetodoPago metodoPago = null;
            String tipoPago = datos[6].trim();
            if (tipoPago.equalsIgnoreCase("Efectivo") || tipoPago.equalsIgnoreCase("PagoEfectivo")) {
                metodoPago = new PagoEfectivo();
            } else if (tipoPago.equalsIgnoreCase("Tarjeta") || tipoPago.equalsIgnoreCase("PagoTarjeta")) {
                metodoPago = new PagoTarjeta();
            } else if (tipoPago.equalsIgnoreCase("Transferencia") || tipoPago.equalsIgnoreCase("PagoTransferencia")) {
                metodoPago = new PagoTransferencia();
            }

            Vendedor vendedor = null;

            for (Vendedor v : vendedores) {
                if (v.getNombre().equalsIgnoreCase(nombreVendedor)) {
                    vendedor = v;
                    break;
                }
            }

            if (vendedor != null) {
                Venta venta = new Venta(comprador, telefono, vendedor);
                venta.setFecha(fechaVenta);
                venta.setTotal(total);
                venta.setMetodoPago(metodoPago);

                agregarVenta(venta);
                vendedor.registrarVenta();
            }
        }
    }

    public void realizarCierreDeCaja() {
        LocalDate hoy = LocalDate.now();

        ArrayList<Venta> ventasDeHoy = new ArrayList<>();
        for (Venta v : listaVentas) {
            if (v.getFecha() != null && v.getFecha().equals(hoy)) {
                ventasDeHoy.add(v);
            }
        }
        if (ventasDeHoy.isEmpty()) {
            System.out.println("No hay ventas registradas hasta el día de hoy (" + hoy + ") para cerrar caja.");
            return;
        }
        double totalGeneral = 0.0;
        double totalEfectivo = 0.0;
        double totalTarjeta = 0.0;
        double totalTransferencia = 0.0;

        for (Venta v : ventasDeHoy) {
            double monto = v.getTotal();
            totalGeneral += monto;

            MetodoPago metodo = v.getMetodoPago();
            if (metodo != null) {
                String tipo = metodo.getTipoPago();
                if ("Efectivo".equalsIgnoreCase(tipo)) {
                    totalEfectivo += monto;
                } else if ("Tarjeta".equalsIgnoreCase(tipo)) {
                    totalTarjeta += monto;
                } else if ("Transferencia".equalsIgnoreCase(tipo)) {
                    totalTransferencia += monto;
                }
            }
        }
        String fechaHoy = LocalDate.now().toString();
        System.out.println("\n==========================================");
        System.out.println("       CIERRE DE CAJA - " + fechaHoy);
        System.out.println("==========================================");
        System.out.println("Total de ventas realizadas: " + ventasDeHoy.size());
        System.out.println("Total Efectivo:      $" + totalEfectivo);
        System.out.println("Total Tarjeta:       $" + totalTarjeta);
        System.out.println("Total Transferencia: $" + totalTransferencia);
        System.out.println("------------------------------------------");
        System.out.println("TOTAL RECAUDADO:     $" + totalGeneral);
        System.out.println("------------------------------------------");
    }

    public void actualizarComisiones(ArrayList<Vendedor> vendedores) {

        ArrayList<String> lineas = new ArrayList<>();

        for (Vendedor vendedor : vendedores) {

            double comision = calcularComision(vendedor);
            String linea = vendedor.getNombre() + "|" +
                    vendedor.getVentasRealizadas() + "|" +
                    comision;

            lineas.add(linea);
        }

        fileManagerComisiones.writeLinesToFile(lineas);
    }

    public void generarArchivoMensual(Inventario inventario){
        YearMonth mesActual = YearMonth.now();
        ArrayList<String> lineas = new ArrayList<>();

        lineas.add("====================================");
        lineas.add("INFORME MENSUAL: " + mesActual);
        lineas.add("====================================");

        lineas.add("");
        lineas.add("--------------------------------");
        lineas.add("INVENTARIO ACTUAL");
        lineas.add("--------------------------------");

        for (Zapato zapato : inventario.getListaZapatos()) {
            String linea = zapato.getCodigo() + "|" +
                    zapato.getTipo() + "|" +
                    zapato.getTalla() + "|" +
                    zapato.getColor() + "|" +
                    zapato.getPrecio() + "|" +
                    zapato.getCantidadDisp() + "|" +
                    zapato.getSeccionBodega() + "|" +
                    zapato.getSexo();
            lineas.add(linea);
        }
        lineas.add("");
        lineas.add("--------------------------------");
        lineas.add("VENTAS DEL MES");
        lineas.add("--------------------------------");

        double totalMensual = 0;

        for (Venta venta : listaVentas) {

            if (venta.getFecha() != null && YearMonth.from(venta.getFecha()).equals(mesActual)) {
                lineas.add("Venta: " + venta.getNumeroVenta());
                lineas.add("Comprador: " + venta.getNombreComprador());
                lineas.add("Teléfono: " + venta.getTelefonoComprador());
                lineas.add("Vendedor: " + venta.getVendedor().getNombre());
                lineas.add("Fecha: " + venta.getFecha());

                String metodo;
                if (venta.getMetodoPago() != null) {
                    metodo = venta.getMetodoPago().getTipoPago();
                } else {
                    metodo = "No registrado";
                }
                lineas.add("Método de pago: " + metodo);

                lineas.add("Total: $" + venta.getTotal());
                for (Venta.DetalleVenta detalle : venta.getDetalles()) {
                    Zapato zapato = detalle.getZapato();
                    lineas.add("  Producto: " + zapato.getTipo() +
                            " | Código: " + zapato.getCodigo() +
                            " | Talla: " + zapato.getTalla() +
                            " | Cantidad: " + detalle.getCantidad() +
                            " | Precio unitario: $" + detalle.getPrecioUnitario() +
                            " | Subtotal: $" + detalle.calcularSubtotal());
                }
                lineas.add("------------------------------------");
                totalMensual += venta.getTotal();

            }
        }
        lineas.add("TOTAL VENDIDO EN EL MES: $" + totalMensual);
        lineas.add("------------------------------------");
        archivoMensualRepository.guardar(lineas);
        System.out.println("El archivo mensual se ha generado correctamente.");
    }

    public void consultarArchivoMensual() {
        ArrayList<String> lineas = archivoMensualRepository.consultar();

        if (lineas == null || lineas.isEmpty()) {
            System.out.println("No existe un informe mensual guardado.");
            return;
        }
        System.out.println("\n===== INFORME MENSUAL =====");
        for (String linea : lineas) {
            System.out.println(linea);
        }
    }
}
