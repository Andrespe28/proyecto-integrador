package ui;
import modelos.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private Scanner sc;
    private Inventario inventario;
    private ArrayList<Vendedor> vendedores;
    private Administrador administrador;
    public Menu() {
        sc = new Scanner(System.in);
        inventario = new Inventario();
        inventario.leerDeArchivo();
    }

    public void iniciarMenu(){
        vendedores=new ArrayList<>();
        Vendedor vendedor1=new Vendedor("35400000","Paula");
        Vendedor vendedor2=new Vendedor("11000000", "Majo");
        Vendedor vendedor3=new Vendedor("50100000","Andres");

        vendedores.add(vendedor1);
        vendedores.add(vendedor2);
        vendedores.add(vendedor3);

        administrador =new Administrador("12000000","David");
        administrador.leerVentasDeArchivo(vendedores);
        administrador.actualizarComisiones(vendedores);

        int opcion;
        do{
            System.out.println("------------------------------\n          CALZATODO           \n------------------------------");
            System.out.println("1. Menú Vendedor.\n2. Menú Administrador.\n0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion=sc.nextInt();
            sc.nextLine();
            switch(opcion){
                case 1:
                    MenuVendedor menu = new MenuVendedor(administrador, vendedores, inventario);
                    menu.menuVendedor();
                    break;
                case 2:
                    MenuAdmi menu1 = new MenuAdmi(vendedores, administrador, inventario);
                    menu1.menuAdministrador();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema.");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }while(opcion!=0);

    }

}
