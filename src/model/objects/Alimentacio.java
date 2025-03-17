package model.objects;

import model.exceptions.DataCaducitatException;
import model.exceptions.LimitCaractersException;
import model.exceptions.NegatiuException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Alimentacio extends Producte implements Comparable<Producte> {

    /* Alimentació: data de caducitat.
            El preu d'aquest tipus de producte varia en funció dels dies que falten per caducar, segons la fórmula:
             preu - preu*(1/(dataCaducitat-dataActual+1)) + (preu * 0.1) */

    // Atributs específics
    private LocalDate dataCaducitat;

    // Constructor
    public Alimentacio(double preu, String nom, String codi, LocalDate dataCaducitat) throws NegatiuException, LimitCaractersException, DataCaducitatException {
        super(preu, nom, codi);
        if (dataCaducitat == null) {
            throw new DataCaducitatException("La data de caducitat no pot ser null"); // DataCaducitatException o NullPointerException?
        }
        this.dataCaducitat = dataCaducitat;
    }

    // Getters & Setters
    public LocalDate getDataCaducitat() {
        return dataCaducitat;
    }

    public void setDataCaducitat(LocalDate dataCaducitat) {
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
        double preuFinal = getPreu() - getPreu() * (1.0 / (diesFinsCaducitat + 1)) + (getPreu() * 0.1);
        return Math.round(preuFinal * 100.0) / 100.0;
    }

    /**
     * Compara dos productes per data de caducitat.
     * Els productes s'ordenen per data de més propera a més llunyana.
     * @param o other Producte
     * @return int
     */
    @Override
    public int compareTo(Producte o) {
        if (o instanceof Alimentacio) {
            return this.dataCaducitat.compareTo(((Alimentacio) o).getDataCaducitat());
        }
        return 0; // Retorna 0 si es compara amb un producte que no és d'Alimentació
    }
        /*
    this, other = Cronologicament més proper a més llunyà
    other, this = Cronologicament més llunyà a més proper
     */

    /* Equivalència amb Comparator
    t1 = this
    t2 = other
     */
}
