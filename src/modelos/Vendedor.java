package modelos;


public class Vendedor extends Usuario{
    private int ventasRealizadas;

    public Vendedor(String cedula, String nombre) {
        super(cedula, nombre);
        this.ventasRealizadas = 0;

    }
    public void registrarVenta(){
        ventasRealizadas++;
    }

    public int getVentasRealizadas() {
        return ventasRealizadas;
    }
}
