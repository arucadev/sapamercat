package model.entities;

import model.exceptions.DataCaducitatException;
import model.exceptions.LimitCaractersException;
import model.exceptions.NegatiuException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

public class Alimentacio extends Producte {

    /* Alimentació: data de caducitat.
            El preu d'aquest tipus de producte varia en funció dels dies que falten per caducar, segons la fórmula:
             preu - preu*(1/(dataCaducitat-dataActual+1)) + (preu * 0.1) */

    // Atributs específics
    private Temporal dataCaducitat;

    // Constructor
    public Alimentacio(double preu, String nom, String codi, Temporal dataCaducitat) throws NegatiuException, LimitCaractersException, DataCaducitatException {
        super(preu, nom, codi);
        if (dataCaducitat == null) {
            throw new DataCaducitatException("La data de caducitat no pot ser null"); // DataCaducitatException o NullPointerException?
        }
        this.dataCaducitat = dataCaducitat;
    }

    // Getters & Setters
    public Temporal getDataCaducitat() {
        return dataCaducitat;
    }

    public void setDataCaducitat(Temporal dataCaducitat) {
        this.dataCaducitat = dataCaducitat;
    }

    //Calcular preu final segons dies restants per caducitat
    @Override
    public double calcularPreuFinal() throws DataCaducitatException {
        // Obtenir data actual i calcular dies restants per caducitat
        LocalDate dataActual = LocalDate.now();
        long diesFinsCaducitat = ChronoUnit.DAYS.between(dataActual, dataCaducitat);

        // Excepció si el producte ja està caducat
        if (diesFinsCaducitat < 0) {
            throw new DataCaducitatException("El producte ja està caducat");
        }

        /*  Retorna preu normal si el producte ja està caducat
        if (diesFinsCaducitat < 0) {
            return getPreu();
        } */

        // Aplica formula: preu - preu*(1/(dataCaducitat-dataActual+1)) + (preu * 0.1)
        return getPreu() - getPreu() * (1.0 / (diesFinsCaducitat + 1)) + (getPreu() * 0.1);
    }

    @Override
    public int compareTo(Producte o) {
        return 0;
    }
}
