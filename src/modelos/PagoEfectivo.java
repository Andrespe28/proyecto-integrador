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
        if (this.dineroRecibido < getMonto()) {
            System.out.println("Dinero insuficiente. Falta: $" + (getMonto() - this.dineroRecibido));
            return 0;
        }
        double cambio = this.dineroRecibido - getMonto();
        System.out.println("¡Pago exitoso! Cambio: $" + cambio);
        return cambio;
    }

    @Override
    public String getTipoPago(){
        return "PagoEfectivo";
    }
}
