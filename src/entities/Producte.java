package entities;

import exceptions.LimitCaracteresException;
import exceptions.NegatiuException;

import java.util.Comparator;

public abstract class Producte implements Comparable<Producte> {

    // Atributs
    private int preu;
    private String nom;
    private String codi;

    // Constructor
    public Producte(int preu, String nom, String codi) throws NegatiuException, LimitCaracteresException {
        if (preu < 0) {
            throw new NegatiuException("El preu no pot ser negatiu");
        }
        if (nom.length() > 50) { // Límit de 50 caràcters
            throw new LimitCaracteresException("El nom no pot superar els 50 caràcters");
        }
        this.preu = preu;
        this.nom = nom;
        this.codi = codi;
    }

    // Getters & Setters
    public int getPreu() {
        return preu;
    }

    public void setPreu(int preu) throws NegatiuException {
        if (preu < 0) {
            throw new NegatiuException("El preu no pot ser negatiu");
        }
        this.preu = preu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) throws LimitCaracteresException {
        if (nom.length() > 50) {
            throw new LimitCaracteresException("El nom no pot superar els 50 caràcters");
        }
        this.nom = nom;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    // Metode abstracte per calcular preu final
    public abstract double calcularPreuFinal();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Producte producte = (Producte) obj;
        return codi.equals(producte.codi);
    }

    @Override
    public int hashCode() {
        return codi.hashCode();
    }
}
