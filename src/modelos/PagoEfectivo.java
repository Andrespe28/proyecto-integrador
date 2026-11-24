package modelos;

public class PagoEfectivo extends MetodoPago{

    private double dineroRecibido;

    public PagoEfectivo(double dineroRecibido, double monto) {
        super(monto);
        this.dineroRecibido = dineroRecibido;
    }

    public PagoEfectivo(){}

    @Override
    public double procesarPago() {
        double cambio = this.dineroRecibido - getMonto();
        if (cambio < 0){
            return -1;
        }
        System.out.println("¡Pago exitoso! Cambio: $" + cambio);
        return cambio;
    }

    @Override
    public String getTipoPago(){
        return "Efectivo";
    }

    public void setDineroRecibido(double dineroRecibido) {
        this.dineroRecibido = dineroRecibido;
    }
}
