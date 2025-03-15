package model.entities;

import model.exceptions.LimitCaractersException;
import model.exceptions.NegatiuException;

import java.time.LocalDate;
import java.util.Comparator;

public class Textil extends Producte implements Comparable<Producte>{
    /* Tèxtil: composició tèxtil (text) */

    // Atributs específics
    private Enum<ComposicioTextil> composicioTextil;

    // Constructor
    public Textil(double preu, String nom, String codi, Enum<ComposicioTextil> composicioTextil) throws NegatiuException, LimitCaractersException {
        super(preu, nom, codi);
        this.composicioTextil = composicioTextil;
    }

    // Getters & Setters
    public ComposicioTextil getComposicioTextil() {
        return (ComposicioTextil) composicioTextil;
    }

    public void setComposicioTextil(Enum<ComposicioTextil> composicioTextil) {
        this.composicioTextil = composicioTextil;
    }

    @Override
    public double calcularPreuFinal() {
        return getPreu();
    }

    @Override
    protected LocalDate getDataCaducitat() {
        // Retornem una data llunyana per donar prioritat als aliments
        return LocalDate.ofEpochDay(9999-12-31);
    }

    @Override
    public int compareTo(Producte other) {
        return this.getNom().compareTo(other.getNom());
    }

    /**
     * Comparator per ordenar els objectes Tèxtils per ordre alfabètic
     */
    public static Comparator<Textil> ComposicioComparatorLambda =
            (t1, t2) -> t1.getComposicioTextil().compareTo(t2.getComposicioTextil());
    /*
    t1, t2 = a - z
    t2, t1 = z - a
     */

    /* Equivalència amb Comparable
    t1 = this
    t2 = other
     */
}
