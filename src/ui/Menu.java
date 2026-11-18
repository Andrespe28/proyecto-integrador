package ui;
import modelos.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private Scanner sc;
    private Inventario inventario;
    private ArrayList<Vendedor> vendedores;
    public Menu() {
        sc = new Scanner(System.in);
        inventario = new Inventario();
        inventario.leerDeArchivo();
    }

    public void iniciarMenu(){
        vendedores=new ArrayList(); //Para buscar el vendedor cuando registre la venta

        Vendedor vendedor1=new Vendedor("35400000","Paula");
        Vendedor vendedor2=new Vendedor("11000000", "Majo");
        Vendedor vendedor3=new Vendedor("50100000","Andres");

        vendedores.add(vendedor1);
        vendedores.add(vendedor2);
        vendedores.add(vendedor3);

        Administrador administrador=new Administrador("12000000","David");

        int opcion;
        do{
            System.out.println("------------------------------\n          CALZATODO           \n------------------------------");
            System.out.println("1. Menú Vendedor.\n2. Menú Administrador.\n0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion=sc.nextInt();
            sc.nextLine();
            switch(opcion){
                case 1:
                    menuVendedor();
                    break;
                case 2:
                    menuAdministrador();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema.");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }while(opcion!=0);

    }
    public void menuVendedor(){
        String codigoProducto;
        int cantidad;
        int i;
        double totalVenta;
        do{
            System.out.println("------------------------------\n          MENÚ VENDEDOR           \n------------------------------");
            System.out.println("1. Consultar inventario.\n2. Registrar intercambio.\n3. Registrar venta.\n4. Consultar datos del comprador.\n5. Buscar ubicación del producto.\n6. Cierre de caja.\n0. Salir");
            System.out.print("Seleccione una opcion: ");
            i = sc.nextInt();
            sc.nextLine();
            switch(i){
                case 1:
                    System.out.println("Consultar inventario");
                    System.out.println("1. Consultar por código.\n2. Consultar inventario total");
                    int opcion = sc.nextInt();
                    sc.nextLine();
                    switch(opcion){
                        case 1:
                            System.out.println("Ingrese el código del zapato que desea buscar: ");
                            String codigo=sc.nextLine();
                            Zapato zapato=inventario.buscarZapato(codigo);
                            if(zapato!=null){
                                zapato.mostrarZapato();
                            }else{
                                System.out.println("No existe un producto con ese código");
                            }
                            break;
                        case 2:
                            inventario.mostrarInventarioTotal();
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Registrar intercambio");
                    break;
                case 3:

                    System.out.println("\nRegistrar venta");
                    System.out.print("Ingrese el nombre del vendedor: ");
                    String nombre=sc.nextLine();
                    Vendedor vendedor=null;
                    for (Vendedor v:vendedores){
                        if(v.getNombre().equalsIgnoreCase(nombre)){
                            vendedor=v;
                            break;
                        }
                    }
                    if (vendedor == null) {
                        System.out.println("No existe un vendedor con ese nombre.");
                        break;
                    }
                    System.out.print("Ingrese el nombre del comprador: ");
                    String comprador=sc.nextLine();
                    System.out.print("Ingrese el telefono del comprador: ");
                    String telefono=sc.nextLine();
                    Venta venta=new Venta(comprador,telefono, vendedor);
                    System.out.println("Ingresar productos.");
                    Venta.DetalleVenta detalle;
                    int respuesta=0;
                    do{
                        System.out.println("\nIngrese el código del producto: ");
                        codigoProducto=sc.nextLine();
                        System.out.println("Ingrese la cantidad de unidades del producto: ");
                        cantidad=sc.nextInt();
                        sc.nextLine();
                        Zapato z=inventario.buscarZapato(codigoProducto);
                        if (z == null) {
                            System.out.println("No existe un producto con ese código.");
                            continue;
                        }
                        detalle=venta.new DetalleVenta(cantidad,z);
                        venta.agregarDetalle(detalle);
                        System.out.println("¿Desea agregar otro producto?\n1. Si.\n2. No");
                        respuesta=sc.nextInt();
                        sc.nextLine();
                    }while(respuesta!=2);
                    totalVenta=venta.calcularTotalVenta();
                    System.out.println("El total de la compra es: "+totalVenta);
                    System.out.print("\nEscoja el método de pago:\n1. Efectivo.\n2. Tarjeta.\n3. Transferencia.\nIngrese una opción (1-3): ");
                    int metodoPago=sc.nextInt();
                    sc.nextLine();
                    switch(metodoPago){
                        case 1:
                            System.out.println("Ingrese la cantidad de dinero dado por el comprador: ");
                            double dinero=sc.nextDouble();
                            PagoEfectivo pagoEfectivo=new PagoEfectivo(dinero,totalVenta);
                            double dineroDevuelto=pagoEfectivo.procesarPago();
                            System.out.println("El vendedor debe devolver $"+dineroDevuelto);
                            venta.setTotal(totalVenta);
                            venta.setMetodoPago(pagoEfectivo);
                            break;
                        case 2:
                            System.out.println("Ingrese el número de la tarjeta: ");
                            int numTarjeta=sc.nextInt();
                            PagoTarjeta pagoTarjeta=new PagoTarjeta(totalVenta,numTarjeta);
                            venta.setTotal(pagoTarjeta.procesarPago());
                            venta.setMetodoPago(pagoTarjeta);
                            break;
                        case 3:
                            break;
                    }
                    break;
                case 4:
                    System.out.println("Consultar datos del comprador");
                    break;
                case 5:
                    System.out.println("Buscar ubicacion del producto");
                    break;
                case 6:
                    System.out.println("Cierre de caja");
                    break;
                case 0:
                    System.out.println("Saliendo del menú del vendedor.");
                default:
                    System.out.println("Opción inválida");
            }
        }while(i!=0);
    }
    public void menuAdministrador(){
        int i;
        do{
            System.out.println("------------------------------\n          MENÚ ADMINISTRADOR           \n------------------------------");
            System.out.println("1. Consultar ventas por vendedor.\n2. Guardar datos del mes.\n0. Salir");
            System.out.print("Seleccione una opcion: ");
            i = sc.nextInt();
            switch(i){
                case 1:
                    System.out.println("Consultar ventas por vendedor");
                    break;
                case 2:
                    System.out.println("Guardar datos del mes.");
                    break;
                case 0:
                    System.out.println("Saliendo del menú del administrador.");
                    break;
                default:
                    System.out.println("Opción inválida");

            }
        }while(i!=0);
    }

}
