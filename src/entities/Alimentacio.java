package entities;

public class Alimentacio extends Productes {
    /* Alimentació: data de caducitat.
            El preu d'aquest tipus de producte varia en funció dels dies que falten per caducar, segons la fórmula:
             preu - preu*(1/(dataCaducitat-dataActual+1)) + (preu * 0.1) */

    // Atributs específics
    private String dataCaducitat;

    // Constructor
    public Alimentacio(int preu, String nom, String codi, String dataCaducitat) {
        super(preu, nom, codi);
        this.dataCaducitat = dataCaducitat;
    }

    // Getters & Setters
    public String getDataCaducitat() {
        return dataCaducitat;
    }

    public void setDataCaducitat(String dataCaducitat) {
        this.dataCaducitat = dataCaducitat;
    }
}
