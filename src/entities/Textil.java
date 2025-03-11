package entities;

import exceptions.LimitCaracteresException;
import exceptions.NegatiuException;

public class Textil extends Producte {
    /* Tèxtil: composició tèxtil (text) */

    // Atributs específics
    private String composicio;

    // Constructor
    public Textil(int preu, String nom, String codi, String composicio) throws NegatiuException, LimitCaracteresException {
        super(preu, nom, codi);
        this.composicio = composicio;
    }

    // Getters & Setters
    public String getComposicio() {
        return composicio;
    }

    public void setComposicio(String composicio) {
        this.composicio = composicio;
    }

    @Override
    public double calcularPreuFinal() {
        return getPreu();
    }
}
