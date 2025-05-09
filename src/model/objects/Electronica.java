package model.objects;

import model.exceptions.LimitCaractersException;
import model.exceptions.NegatiuException;

import java.time.LocalDate;

public class Electronica extends Producte implements Comparable<Producte> {
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
        double preuFinal = getPreu() + getPreu() * (diesGarantia / 365.0) * 0.1;
        return Math.round(preuFinal * 100.0) / 100.0;
    }

    @Override
    public LocalDate getDataCaducitat() {
        // Retornem una data llunyana per donar prioritat als aliments
        return LocalDate.ofEpochDay(9999-12-31);
    }

    @Override
    public int compareTo(Producte o) {
        return this.getNom().compareTo(o.getNom());
    }
}
