package modelos;

public abstract class MetodoPago {
    protected double monto;

    public MetodoPago(double monto) {
        this.monto = monto;
    }

    abstract double procesarPago();

    public double getMonto() {
        return monto;
    }

}


