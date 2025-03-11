package entities;

public class Electronica extends Producte {
            /* Electrònica: dies de garantia (numèric)
            El preu d'aquest tipus de producte varia en funció dels dies que té de garantia segons la fórmula:
            preu + preu*(diesGarantia/365)*0.1 */

    // Atributs específics
    private int diesGarantia;

    // Constructor
    public Electronica(int preu, String nom, String codi, int diesGarantia) {
        super(preu, nom, codi);
        this.diesGarantia = diesGarantia;
    }

    // Getters & Setters
    public int getDiesGarantia() {
        return diesGarantia;
    }

    public void setDiesGarantia(int diesGarantia) {
        this.diesGarantia = diesGarantia;
    }

    // Metode per calcular preu segons els dies de garantia
    public double calcularPreuFinal() {
        return getPreu() + getPreu() * (diesGarantia / 365.0) * 0.1;
    }
}
