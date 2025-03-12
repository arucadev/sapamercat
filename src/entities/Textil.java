package entities;

import exceptions.LimitCaractersException;
import exceptions.NegatiuException;

import java.util.Comparator;

public class Textil extends Producte {
    /* Tèxtil: composició tèxtil (text) */

    // Atributs específics
    private String composicioTextil;

    // Constructor
    public Textil(int preu, String nom, String codi, String composicioTextil) throws NegatiuException, LimitCaractersException {
        super(preu, nom, codi);
        this.composicioTextil = composicioTextil;
    }

    // Getters & Setters
    public String getComposicioTextil() {
        return composicioTextil;
    }

    public void setComposicioTextil(String composicioTextil) {
        this.composicioTextil = composicioTextil;
    }

    @Override
    public double calcularPreuFinal() {
        return getPreu();
    }

    @Override
    public int compareTo(Producte o) {
        return 0;
    }

    public static Comparator<Textil> ComposicioComparatorLambda =
            (t1, t2) -> t1.getComposicioTextil().compareTo(t2.getComposicioTextil());


}
