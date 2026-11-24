package modelos;

public abstract class MetodoPago {
    protected double monto;

    public MetodoPago(double monto) {
        if (monto >= 0) {
            this.monto = monto;
        } else {
            this.monto = 0;
            System.out.println("El monto no puede ser negativo. Se asigna 0.");
        }
    }

    public MetodoPago(){}

    public abstract double procesarPago();

    public abstract String getTipoPago();

    public double getMonto() {
        return monto;
    }

}


