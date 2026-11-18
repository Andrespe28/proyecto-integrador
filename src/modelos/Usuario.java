package modelos;

public class Usuario {
    protected String cedula;
    protected String nombre;

    public Usuario(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
    }

    public boolean iniciarSesion(){
        return true;
    }
}
