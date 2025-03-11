package entities;

public abstract class Productes {
    // Atributs
    private int preu;
    private String nom;
    private String codi;


    // Constructor
    public Productes(int preu, String nom, String codi) {
        this.preu = preu;
        this.nom = nom;
        this.codi = codi;
    }

    // Getters & Setters
    public int getPreu() {
        return preu;
    }

    public void setPreu(int preu) {
        this.preu = preu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }
}
