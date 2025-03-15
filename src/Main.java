import model.CarroCompra;
import model.entities.*;
import model.exceptions.DataCaducitatException;
import model.exceptions.LimitCaractersException;
import model.exceptions.LimitProductesException;
import model.exceptions.NegatiuException;

import java.time.LocalDate;
import java.util.*;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    CarroCompra carro = new CarroCompra();
    public static void main(String[] args) {

    }

    // SISTEMA

    /**
     * Mètode del controlador per afegir un producte d'alimentació al sistema
     */
    public void afegirAlimentacioSistema() {
        System.out.println();
        System.out.println("Introdueix el nom del producte:");
        String nom = scan.nextLine();
        System.out.println("Introdueix el preu del producte:");
        double preu = scan.nextDouble();
        System.out.println("Introdueix el codi de barres del producte:");
        String codiBarres = scan.nextLine();
        System.out.println("Introdueix la data de caducitat del producte (yyyy-mm-dd):");
        LocalDate dataCaducitat = LocalDate.parse(scan.nextLine());

        try {
            Alimentacio aliment = new Alimentacio(preu, nom, codiBarres, dataCaducitat);
            carro.afegirProducteSistema(aliment);
        } catch (NegatiuException | LimitCaractersException | DataCaducitatException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Mètode del controlador per afegir un producte electrònic al sistema
     */
    public void afegirElectronicaCarro() {
        System.out.println();
        System.out.println("Introdueix el nom del producte:");
        String nom = scan.nextLine();
        System.out.println("Introdueix el preu del producte:");
        double preu = scan.nextDouble();
        System.out.println("Introdueix el codi de barres del producte:");
        String codiBarres = scan.nextLine();
        System.out.println("Introdueix la garantia del producte (anys):");
        int garantia = scan.nextInt();

        try {
            Electronica electronica = new Electronica(preu, nom, codiBarres, garantia);
            carro.afegirProducteSistema(electronica);
        } catch (NegatiuException | LimitCaractersException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Mètode del controlador per afegir un producte tèxtil al sistema
     */
    public void afegirTextilCarro() {
        System.out.println();
        System.out.println("Introdueix el nom del producte:");
        String nom = scan.nextLine();
        System.out.println("Introdueix el preu del producte:");
        double preu = scan.nextDouble();
        System.out.println("Introdueix el codi de barres del producte:");
        String codiBarres = scan.nextLine();
        System.out.println("Introdueix la composició tèxtil del producte:");
        String composicioTextil = scan.nextLine();

        try {
            Textil textil = new Textil(preu, nom, codiBarres, composicioTextil);
            boolean afegit = carro.afegirProducteSistema(textil);
            if (afegit) {
                System.out.println("Producte afegit al sistema correctament");
            } else {
                System.out.println("No s'ha pogut afegir el producte, ja existeix");
            }
        } catch (NegatiuException | LimitCaractersException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Mètode del controlador per eliminar productes del sistema
     */
    public void eliminarProducteSistema() {
        System.out.println();
        System.out.println("Introdueix el codi de barres del producte a eliminar:");
        String codiBarres = scan.nextLine();
        boolean eliminat = carro.eliminarProducteSistema(codiBarres);
        if (eliminat) {
            System.out.println("Producte eliminat correctament");
        } else {
            System.out.println("No s'ha pogut eliminar el producte, no existeix al sistema");
        }
    }

    /**
     * Mètode del controlador per buscar un producte pel seu codi de barres
     */
    public void buscarPerCodiSistema() {
        System.out.println();
        System.out.println("Introdueix el codi de barres del producte a buscar:");
        String codiBarres = scan.nextLine();
        Producte producte = carro.buscarPerCodiSistema(codiBarres);
        if (producte != null) {
            System.out.println("Producte trobat: " + producte.getNom());
        } else {
            System.out.println("Producte no trobat");
        }
    }

    // CARRO

    /**
     * Mètode del controlador per afegir productes (de qualsevol tipus) al carro
     */
    public void afegirProducteCarro() {
        System.out.println();
        System.out.println("Introdueix el codi de barres del producte:");
        String codiBarres = scan.nextLine();
        Producte producte = carro.buscarPerCodiSistema(codiBarres);
        try {
            carro.afegirProducteCarro(producte);
        } catch (LimitProductesException e) {
            System.out.println(e.getMessage());
        }
    }
}