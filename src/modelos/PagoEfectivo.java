package modelos;

public class PagoEfectivo extends MetodoPago{

    private double dineroRecibido;

    public PagoEfectivo(double dineroRecibido, double monto) {
        super(monto);
        this.dineroRecibido = dineroRecibido;
    }

    @Override
    public double procesarPago(){
        return this.dineroRecibido - getMonto();
    }
}
