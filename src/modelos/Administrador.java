package modelos;
import persistencia.FileManage;
import java.time.LocalDate;

import java.io.*;
import java.util.ArrayList;

public class Administrador extends Usuario {
    private ArrayList<Venta> listaVentas = new ArrayList<>();
    private static final String rutaInventario = "src/files/inventario_zapatos.txt";
    private FileManage fileManager = new FileManage("ventas.txt");
    private FileManage fileManagerComisiones = new FileManage("comisiones.txt");
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

    private static double porcentajeComision;
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
            System.out.printf("Venta #: %d, Comprador: %s, Telefono: %s, Vendedor: %s, Total:$%2.f\n",v.getNumeroVenta(),v.getNombreComprador(),v.getTelefonoComprador(),v.getVendedor(),v.getTotal(),v.getTotal());
        }
    }
    public void leerVentasDeArchivo(ArrayList<Vendedor> vendedores) {
        ArrayList<String> lineas = fileManager.getDataOfFile();
        for (String linea : lineas) {
            if (linea == null || linea.trim().isEmpty()) {
                return;
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
            System.out.println("No hay ventas registradas el día de hoy (" + hoy + ") para cerrar caja.");
            return;
        }
        double totalGeneral = 0.0;
        double totalEfectivo = 0.0;
        double totalTarjeta = 0.0;
        double totalTransferencia = 0.0;

        for (Venta v : listaVentas) {
            double monto = v.getTotal();
            totalGeneral += monto;

            if (v.getMetodoPago() instanceof PagoEfectivo) {
                totalEfectivo += monto;
            } else if (v.getMetodoPago() instanceof PagoTarjeta) {
                totalTarjeta += monto;
            } else if (v.getMetodoPago() instanceof PagoTransferencia) {
                totalTransferencia += monto;
            }
        }
        String fechaHoy = LocalDate.now().toString();
        System.out.println("\n==========================================");
        System.out.println("       CIERRE DE CAJA - " + fechaHoy);
        System.out.println("==========================================");
        System.out.println("Total de ventas realizadas: " + listaVentas.size());
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
}
