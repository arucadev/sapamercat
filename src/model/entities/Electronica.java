package model.entities;

import model.exceptions.LimitCaractersException;
import model.exceptions.NegatiuException;

public class Electronica extends Producte {
            /* Electrònica: dies de garantia (numèric)
            El preu d'aquest tipus de producte varia en funció dels dies que té de garantia segons la fórmula:
            preu + preu*(diesGarantia/365)*0.1 */

    // Atributs específics
    private int diesGarantia;

    // Constructor
    public Electronica(double preu, String nom, String codi, int diesGarantia) throws NegatiuException, LimitCaractersException {
        super(preu, nom, codi);
        if (diesGarantia < 0) {
            throw new NegatiuException("El nombre de dies de garantia no pot ser negatiu");
        }
        this.diesGarantia = diesGarantia;
    }

    // Getters & Setters
    public int getDiesGarantia() {
        return diesGarantia;
    }

    public void setDiesGarantia(int diesGarantia) throws NegatiuException {
        if (diesGarantia < 0) {
            throw new NegatiuException("El nombre de dies de garantia no pot ser negatiu");
        }
        this.diesGarantia = diesGarantia;
    }

    // Metode per calcular preu segons els dies de garantia
    public double calcularPreuFinal() {
        return getPreu() + getPreu() * (diesGarantia / 365.0) * 0.1;
    }

    @Override
    public int compareTo(Producte o) {
        return 0;
    }
}
