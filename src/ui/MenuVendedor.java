package ui;

import modelos.*;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuVendedor {
    private Scanner sc;
    private Inventario inventario;
    private ArrayList<Vendedor> vendedores;
    private Administrador administrador;

    public MenuVendedor(Administrador administrador, ArrayList<Vendedor> vendedores, Inventario inventario) {
        sc = new Scanner(System.in);
        this.administrador = administrador;
        this.vendedores = vendedores;
        this.inventario = inventario;
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
                    System.out.println("Ingrese el código del primer producto:");
                    String codigo1 = sc.nextLine();
                    System.out.println("Ingrese la cantidad del primer producto:");
                    int cantidad1 = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Ingrese el código del segundo producto:");
                    String codigo2 = sc.nextLine();
                    System.out.println("Ingrese la cantidad del segundo producto:");
                    int cantidad2 = sc.nextInt();
                    sc.nextLine();
                    double diffPrecio;
                    try {
                        // Solo calcula la diferencia. El stock se mueve cuando el intercambio se confirma.
                        diffPrecio = inventario.calcularDiferenciaIntercambio(codigo1, codigo2, cantidad1, cantidad2);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    if (diffPrecio < 0) {
                        System.out.println("No es posible realizar el intercambio. El precio del producto a cambiar debe ser mayor o igual al producto de cambio ");
                    } else if (diffPrecio == 0) {
                        inventario.registrarIntercambio(codigo1, codigo2, cantidad1, cantidad2);
                        System.out.println("Intercambio realizado. No hay diferencia de precio por pagar.");
                    } else {
                        System.out.println("\nRegistrar venta");
                        System.out.print("Ingrese el nombre del vendedor que realizó la venta: ");
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
                        System.out.print("Ingrese el teléfono del comprador: ");
                        String telefono=sc.nextLine();
                        Venta venta=new Venta(comprador,telefono, vendedor);
                        double total=diffPrecio;
                        System.out.println("El valor excedente es: "+total);
                        int metodoPago;
                        do {
                            System.out.print("\nEscoja el método de pago:\n1. Efectivo.\n2. Tarjeta.\n3. Transferencia.\nIngrese una opción (1-3): ");
                            metodoPago = sc.nextInt();
                            sc.nextLine();
                            if (metodoPago < 1 || metodoPago > 3) {
                                System.out.println("Opción inválida. Intente nuevamente.");
                            }
                        } while (metodoPago < 1 || metodoPago > 3);
                        switch(metodoPago){
                            case 1:
                                System.out.println("Ingrese la cantidad de dinero dado por el comprador: ");
                                double dinero=sc.nextDouble();
                                PagoEfectivo pagoEfectivo=new PagoEfectivo(dinero,total);
                                double dineroDevuelto=pagoEfectivo.procesarPago();
                                while(dineroDevuelto==-1){
                                    System.out.println("Dinero insuficiente, por favor ingrese más dinero.\nIngrese el nuevo valor");
                                    dinero=sc.nextDouble();
                                    sc.nextLine();
                                    pagoEfectivo.setDineroRecibido(dinero);
                                    dineroDevuelto=pagoEfectivo.procesarPago();
                                }

                                venta.setTotal(total);
                                venta.setMetodoPago(pagoEfectivo);
                                break;
                            case 2:
                                System.out.println("Ingrese el número de la tarjeta: ");
                                long numTarjetaCambio=sc.nextLong();
                                sc.nextLine();
                                PagoTarjeta pagoTarjeta=new PagoTarjeta(total,numTarjetaCambio);
                                venta.setTotal(pagoTarjeta.procesarPago());
                                venta.setMetodoPago(pagoTarjeta);
                                break;
                            case 3:
                                System.out.println("Ingrese el nombre del Banco:");
                                String nombreBancoCambio=sc.nextLine();
                                PagoTransferencia pagoTransferencia=new PagoTransferencia(nombreBancoCambio,total);
                                venta.setTotal(pagoTransferencia.procesarPago());
                                venta.setMetodoPago(pagoTransferencia);
                                break;
                            default:
                                System.out.println("Opción invalida. Intente nuevamente.");
                        }
                        // El pago ya se completó: ahora sí se mueve el stock y se guarda el inventario.
                        inventario.registrarIntercambio(codigo1, codigo2, cantidad1, cantidad2);
                        administrador.agregarVenta(venta);
                        venta.guardarEnArchivo();
                        vendedor.registrarVenta();
                    }
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
                    System.out.print("Ingrese el teléfono del comprador: ");
                    String telefono=sc.nextLine();
                    Venta venta=new Venta(comprador,telefono, vendedor);
                    System.out.println("Ingresar productos.");
                    Venta.DetalleVenta detalle;
                    int respuesta=0;
                    do{
                        System.out.println("\nIngrese el código del producto: ");
                        codigoProducto=sc.nextLine();
                        Zapato z=inventario.buscarZapato(codigoProducto);
                        if (z == null) {
                            System.out.println("No existe un producto con ese código.");
                            continue;
                        }
                        System.out.println("Ingrese la cantidad de unidades del producto: ");
                        cantidad=sc.nextInt();
                        sc.nextLine();
                        if (cantidad <= 0) {
                            System.out.println("La cantidad debe ser mayor que cero.");
                            continue;
                        }
                        if(cantidad> z.getCantidadDisp()){
                            System.out.println("No hay suficientes unidades. Por favor ingrese nuevamente");
                            System.out.println("Cantidades disponibles: "+z.getCantidadDisp());
                            continue;
                        }
                        z.actualizarStock(-cantidad);
                        inventario.guardarEnArchivo();
                        detalle=venta.new DetalleVenta(cantidad,z);
                        venta.agregarDetalle(detalle);
                        do{
                            System.out.println("¿Desea agregar otro producto?\n1. Si.\n2. No");
                            respuesta=sc.nextInt();
                            sc.nextLine();
                        }while(respuesta!=1 && respuesta!=2);
                    }while(respuesta!=2);

                    totalVenta=venta.calcularTotalVenta();
                    System.out.println("El total de la compra es: "+totalVenta);
                    int metodoPago;
                    do {
                        System.out.print("\nEscoja el método de pago:\n1. Efectivo.\n2. Tarjeta.\n3. Transferencia.\nIngrese una opción (1-3): ");
                        metodoPago = sc.nextInt();
                        sc.nextLine();
                        if (metodoPago < 1 || metodoPago > 3) {
                            System.out.println("Opción inválida. Intente nuevamente.");
                        }
                    } while (metodoPago < 1 || metodoPago > 3);
                    switch(metodoPago){
                        case 1:
                            System.out.println("Ingrese la cantidad de dinero dado por el comprador: ");
                            double dinero=sc.nextDouble();
                            PagoEfectivo pagoEfectivo=new PagoEfectivo(dinero,totalVenta);
                            double dineroDevuelto=pagoEfectivo.procesarPago();
                            while(dineroDevuelto==-1){
                                System.out.println("Dinero insuficiente, por favor ingrese más dinero.\nIngrese el nuevo valor");
                                dinero=sc.nextDouble();
                                sc.nextLine();
                                pagoEfectivo.setDineroRecibido(dinero);
                                dineroDevuelto=pagoEfectivo.procesarPago();
                            }

                            venta.setTotal(totalVenta);
                            venta.setMetodoPago(pagoEfectivo);
                            break;
                        case 2:
                            System.out.println("Ingrese el número de la tarjeta: ");
                            long numTarjeta=sc.nextLong();
                            sc.nextLine();
                            PagoTarjeta pagoTarjeta=new PagoTarjeta(totalVenta,numTarjeta);
                            venta.setTotal(pagoTarjeta.procesarPago());
                            venta.setMetodoPago(pagoTarjeta);
                            break;
                        case 3:
                            System.out.println("Ingrese el nombre del Banco:");
                            String nombreBanco=sc.nextLine();
                            PagoTransferencia pagoTransferencia=new PagoTransferencia(nombreBanco,totalVenta);
                            venta.setTotal(pagoTransferencia.procesarPago());
                            venta.setMetodoPago(pagoTransferencia);
                            break;
                        default:
                            System.out.println("Opción invalida. Intente nuevamente.");
                    }
                    vendedor.registrarVenta();
                    administrador.agregarVenta(venta);
                    venta.guardarEnArchivo();
                    inventario.guardarEnArchivo();
                    administrador.actualizarComisiones(vendedores);
                    break;
                case 4:
                    System.out.println("Consultar datos del comprador");
                    System.out.println("Ingresa el número de venta:");
                    int numVenta=sc.nextInt();
                    sc.nextLine();
                    Venta v=administrador.buscarVenta(numVenta);
                    if (v == null) {
                        System.out.println("Esa venta no ha sido registrada");
                    }else {
                        System.out.println("El nombre del comprador es: " + v.getNombreComprador());
                        System.out.println("El teléfono del comprador es: " + v.getTelefonoComprador());
                    }
                    break;
                case 5:
                    System.out.println("Buscar ubicación del producto");
                    System.out.println("Ingrese el código del producto");
                    String producto=sc.nextLine();
                    Zapato zapato=inventario.buscarZapato(producto);
                    if (zapato != null) {
                        System.out.println("El producto está en la sección "+zapato.getSeccionBodega()+" de la bodega");
                    } else {
                        System.out.println("El producto no existe");
                    }
                    break;
                case 6:
                    administrador.realizarCierreDeCaja();
                    break;
                case 0:
                    System.out.println("Saliendo del menú del vendedor.");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }while(i!=0);
    }
}
