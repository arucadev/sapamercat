package model.entities;

import model.exceptions.DataCaducitatException;
import model.exceptions.LimitCaractersException;
import model.exceptions.NegatiuException;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public abstract class Producte {

    // Atributs
    private double preu;
    private String nom;
    private String codi;

    // Constructor
    public Producte(double preu, String nom, String codi) throws NegatiuException, LimitCaractersException {
        if (preu < 0) {
            throw new NegatiuException("El preu no pot ser negatiu");
        }
        if (nom.length() > 50) { // Límit de 50 caràcters
            throw new LimitCaractersException("El nom no pot superar els 50 caràcters");
        }
        this.preu = preu;
        this.nom = nom;
        this.codi = codi;
    }

    // Getters & Setters
    public double getPreu() {
        return Math.round(preu * 100.0) / 100.0;
    }

    public void setPreu(double preu) throws NegatiuException {
        if (preu < 0) {
            throw new NegatiuException("El preu no pot ser negatiu");
        }
        this.preu = preu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) throws LimitCaractersException {
        if (nom.length() > 50) {
            throw new LimitCaractersException("El nom no pot superar els 50 caràcters");
        }
        this.nom = nom;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    // Mètode abstracte per calcular preu final
    public abstract double calcularPreuFinal() throws DataCaducitatException;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Producte producte = (Producte) obj;
        // Verifiquem que el codi de barres sigui únic
        return codi.equals(producte.codi);
    }

    @Override
    public int hashCode() {
        // Utilitzem el codi de barres per calcular el hashcode
        return codi.hashCode();
    }

    protected abstract LocalDate getDataCaducitat();
}
