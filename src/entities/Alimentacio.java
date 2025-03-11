package entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;

public class Alimentacio extends Producte {

    /* Alimentació: data de caducitat.
            El preu d'aquest tipus de producte varia en funció dels dies que falten per caducar, segons la fórmula:
             preu - preu*(1/(dataCaducitat-dataActual+1)) + (preu * 0.1) */

    // Atributs específics
    private Temporal dataCaducitat;

    // Constructor
    public Alimentacio(int preu, String nom, String codi, Temporal dataCaducitat) {
        super(preu, nom, codi);
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
    public double calcularPreuFinal() {
        // Obtenir data actual i calcular dies restants per caducitat
        LocalDate dataActual = LocalDate.now();
        long diesFinsCaducitat = ChronoUnit.DAYS.between(dataActual, dataCaducitat);

        // Retorna preu normal si el producte ja està caducat
        if (diesFinsCaducitat < 0) {
            return getPreu();
        }

        // Aplica formula: preu - preu*(1/(dataCaducitat-dataActual+1)) + (preu * 0.1)
        return getPreu() - getPreu() * (1.0 / (diesFinsCaducitat + 1)) + (getPreu() * 0.1);
    }
}
