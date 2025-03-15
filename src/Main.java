import model.Model;
import model.entities.*;
import model.exceptions.DataCaducitatException;
import model.exceptions.LimitCaractersException;
import model.exceptions.LimitProductesException;
import model.exceptions.NegatiuException;
import view.View;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    static Model carro = new Model();
    public static void main(String[] args) {

        System.out.println();
        System.out.println("Benvingut a SapaMercat");
        boolean sortir = false;

        // Afegim alguns Productes al sistema per defecte
        try {
            carro.afegirProducteSistema(new Alimentacio(1.5, "Poma", "111", LocalDate.parse("2025-12-31")));
            carro.afegirProducteSistema(new Alimentacio(2.5, "Plàtan", "112", LocalDate.parse("2045-12-31")));
            carro.afegirProducteSistema(new Alimentacio(3.5, "Taronges", "113", LocalDate.parse("2030-12-31")));
            carro.afegirProducteSistema(new Alimentacio(10.0, "Formatge", "122", LocalDate.parse("2022-12-31")));
            carro.afegirProducteSistema(new Textil(10.0, "Camisa", "114", ComposicioTextil.COTÓ));
            carro.afegirProducteSistema(new Textil(15.0, "Pantalons", "115", ComposicioTextil.POLIESTER));
            carro.afegirProducteSistema(new Textil(20.0, "Sabates", "116", ComposicioTextil.SEDA));
            carro.afegirProducteSistema(new Textil(2.0, "Gorra", "120", ComposicioTextil.COTÓ));
            carro.afegirProducteSistema(new Textil(20.0, "Bufanda", "121", ComposicioTextil.LLANA));
            carro.afegirProducteSistema(new Electronica(100.0, "Mòbil", "117", 20));
            carro.afegirProducteSistema(new Electronica(200.0, "Portàtil", "118", 30));
            carro.afegirProducteSistema(new Electronica(300.0, "Tablet", "119", 100));
            carro.afegirProducteSistema(new Electronica(1000.0, "Iphone", "123", 20));
        } catch (NegatiuException | LimitCaractersException | DataCaducitatException e) {
            throw new RuntimeException(e);
        }

        do {
            System.out.println();
            View.separador();
            View.menuPrincipal();
            View.separador();

            int opcio = Integer.parseInt(scan.nextLine());
            switch (opcio) {
                case 1:
                    System.out.println();
                    menuMagatzemICompres();
                    break;
                case 2:
                    System.out.println();
                    afegirProducteCarro();
                    break;
                case 3:
                    System.out.println();
                    passarPerCaixa();
                    break;
                case 4:
                    System.out.println();
                    menuMostrarCarroDeLaCompra();
                    break;
                case 0:
                    sortir = true;
                    System.exit(0);
                    break;
                default:
                    System.out.println("Introdueix una opció vàlida");
                    break;
            }
        } while (!sortir);
    }

    // SUBMENUS

    /**
     * Mètode del controlador per mostrar el menú de magatzem i compres
     */
    public static void menuMagatzemICompres() {
        boolean tornar = false;
        do {
            View.separador();
            View.menuMagatzemICompres();
            View.separador();

            int opcio = Integer.parseInt(scan.nextLine());
            switch (opcio) {
                case 1:
                    System.out.println();
                    menuIntroduirProducte();
                    break;
                case 2:
                    System.out.println();
                    eliminarProducteSistema();
                case 3:
                    System.out.println();
                    try {
                        carro.escriureArxiu();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 4:
                    System.out.println();
                    List<Alimentacio> aliments = carro.getProductesAlimentacioOrdenats();
                    for (Producte aliment : aliments) {
                        System.out.println(aliment.getNom() + " --> " + aliment.getDataCaducitat());
                    }
                    break;
                case 5:
                    System.out.println();
                    List<Textil> textils = carro.getProductesTextilsOrdenats();
                    for (Textil textil : textils) {
                        System.out.println(textil.getNom() + " --> " + textil.getComposicioTextil());
                    }
                    break;
                case 0:
                    tornar = true;
                    return;
                default:
                    System.out.println("Introdueix una opció vàlida");
                    break;
            }
        } while (!tornar);
    }

    /**
     * Mètode del controlador per mostrar el menú d'introduir producte
     */
    public static void menuIntroduirProducte() {
        boolean tornar = false;
        do {
            System.out.println();
            View.separador();
            View.menuIntroduirProducte();
            View.separador();

            int opcio = Integer.parseInt(scan.nextLine());
            switch (opcio) {
                case 1:
                    afegirAlimentacioSistema();
                    break;
                case 2:
                    afegirTextilSistema();
                    break;
                case 3:
                    afegirElectronicaSistema();
                    break;
                case 0:
                    tornar = true;
                    return;
                default:
                    System.out.println("Introdueix una opció vàlida");
                    break;
            }
        } while (!tornar);
    }

    public static void menuMostrarCarroDeLaCompra() {
        boolean tornar = false;
        do {
            System.out.println();
            View.separador();
            View.menuMostrarCarroDeLaCompra();
            View.separador();

            int opcio = Integer.parseInt(scan.nextLine());
            switch (opcio) {
                case 1:
                    mostrarCarroDeLaCompra();
                    break;
                case 2:
                    eliminarProducteCarro();
                    break;
                case 0:
                    tornar = true;
                    return;
                default:
                    System.out.println("Introdueix una opció vàlida");
                    break;
            }
        } while (!tornar);
    }

    // SISTEMA

    /**
     * Mètode del controlador per afegir un producte d'alimentació al sistema
     */
    public static void afegirAlimentacioSistema() {
        System.out.println("Introdueix el nom del producte:");
        String nom = scan.nextLine();
        System.out.println("Introdueix el preu del producte:");
        double preu = 0;
        try {
            preu = Double.parseDouble(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("El preu introduït no és vàlid.");
            return;
        }
        System.out.println("Introdueix el codi de barres del producte:");
        String codiBarres = scan.nextLine();
        System.out.println("Introdueix la data de caducitat del producte (yyyy-mm-dd):");
        String dataCaducitatStr = scan.nextLine();
        LocalDate dataCaducitat;
        if (!dataCaducitatStr.isEmpty()) {
            try {
                dataCaducitat = LocalDate.parse(dataCaducitatStr);
            } catch (Exception e) {
                System.out.println("La data de caducitat introduïda no és vàlida.");
                return;
            }
        } else {
            System.out.println("La data de caducitat no pot estar buida.");
            return;
        }

        try {
            Alimentacio aliment = new Alimentacio(preu, nom, codiBarres, dataCaducitat);
            boolean afegit = carro.afegirProducteSistema(aliment);
            if (afegit) {
                System.out.println("Producte afegit al sistema correctament");
            } else {
                System.out.println("No s'ha pogut afegir el producte, ja existeix");
            }
        } catch (NegatiuException | LimitCaractersException | DataCaducitatException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException | NullPointerException e) {
            System.out.println("La dada introduïda no és correcte");
        }
    }

    /**
     * Mètode del controlador per afegir un producte electrònic al sistema
     */
    public static void afegirElectronicaSistema() {
        System.out.println("Introdueix el nom del producte:");
        String nom = scan.nextLine();
        System.out.println("Introdueix el preu del producte:");
        double preu = 0;
        try {
            preu = Double.parseDouble(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("El preu introduït no és vàlid.");
            return;
        }
        System.out.println("Introdueix el codi de barres del producte:");
        String codiBarres = scan.nextLine();
        System.out.println("Introdueix la garantia del producte (anys):");
        int garantia = 0;
        try {
            garantia = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("La garantia introduïda no és vàlida.");
            return;
        }

        try {
            Electronica electronica = new Electronica(preu, nom, codiBarres, garantia);
            boolean afegit = carro.afegirProducteSistema(electronica);
            if (afegit) {
                System.out.println("Producte afegit al sistema correctament");
            } else {
                System.out.println("No s'ha pogut afegir el producte, ja existeix");
            }
        } catch (NegatiuException | LimitCaractersException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException | NullPointerException e) {
            System.out.println("La dada introduïda no és correcte");
        }
    }

    /**
     * Mètode del controlador per afegir un producte tèxtil al sistema
     */
    public static void afegirTextilSistema() {
        System.out.println("Introdueix el nom del producte:");
        String nom = scan.nextLine();
        System.out.println("Introdueix el preu del producte:");
        double preu = 0;
        try {
            preu = Double.parseDouble(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("El preu introduït no és vàlid.");
            return;
        }
        System.out.println("Introdueix el codi de barres del producte:");
        String codiBarres = scan.nextLine();
        System.out.println("Introdueix la composició tèxtil del producte (COTÓ, POLIESTER, VISCOSA, LLANA, SEDA):");
        String composicioTextilStr = scan.nextLine();
        ComposicioTextil composicioTextil;
        try {
            composicioTextil = ComposicioTextil.valueOf(composicioTextilStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("La composició tèxtil introduïda no és vàlida.");
            return;
        }

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
        } catch (InputMismatchException | NullPointerException e) {
            System.out.println("La dada introduïda no és correcte");
        }
    }

    /**
     * Mètode del controlador per eliminar productes del sistema
     */
    public static void eliminarProducteSistema() {
        System.out.println("Introdueix el codi de barres del producte a eliminar:");
        String codiBarres = scan.nextLine();
        Producte producte = carro.buscarPerCodiSistema(codiBarres);
        if (producte == null) {
            System.out.println("No existeix cap producte amb aquest codi de barres");
            return;
        }
        // Mostrem el producte del resultat de la cerca
        System.out.println(producte.getNom());
        // Demanem confirmació abans d'eliminar el producte del sistema
        System.out.println("Estàs segur que vols eliminar aquest producte? (s/n)");
        String confirmacio = scan.nextLine();
        if (confirmacio.equalsIgnoreCase("s")) {
            System.out.println("Producte eliminat del carro");
        }
    }

    /**
     * Mètode del controlador per buscar un producte pel seu codi de barres
     */
    public void buscarPerCodiSistema() {
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
     * Mètode del controlador per afegir productes (de qualsevol subtipus) al carro
     */
    public static void afegirProducteCarro() {
        System.out.println("Introdueix el codi de barres del producte:");
        String codiBarres = scan.nextLine();
        Producte producte = carro.buscarPerCodiSistema(codiBarres);
        try {
            carro.afegirProducteCarro(producte);
            if (producte != null) {
                System.out.println("Producte afegit al carro correctament");
            } else {
                System.out.println("No hi ha productes al sistema amb aquest codi de barres");
                System.out.println("Pots introduir nous productes al sistema des del menú principal");
            }
        } catch (LimitProductesException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException | InputMismatchException e) {
            System.out.println("No hi ha cap producte amb aquest codi de barres");
        }
    }

    /**
     * Mètode del controlador per eliminar productes del carro
     */
    public static void eliminarProducteCarro() {
        System.out.println("Introdueix el codi de barres del producte a eliminar:");
        String codiBarres = scan.nextLine();
        Producte producte = carro.buscarPerCodiCarro(codiBarres);
        if (producte == null) {
            System.out.println("No hi ha cap producte al carro amb aquest codi de barres");
            return;
        }
        // Mostrem el producte del resultat de la cerca
        System.out.println(producte.getNom());
        // Demanem confirmació abans d'eliminar el producte del carro
        System.out.println("Estàs segur que vols eliminar aquest producte? (s/n)");
        String confirmacio = scan.nextLine();
        if (confirmacio.equalsIgnoreCase("s")) {
            boolean eliminat = carro.eliminarProducteCarro(codiBarres);
            if (eliminat) {
                System.out.println("Producte eliminat correctament");
            } else {
                System.out.println("No s'ha pogut eliminar el producte del carro");
            }
        } else {
            System.out.println("No s'ha eliminat cap producte");
        }
    }

    /**
     * Mètode del controlador per passar per caixa
     */
    public static void passarPerCaixa() {
        carro.passarPerCaixa(Model.getProductesCarroQuantitats());
        System.out.println("Pulsa Enter per pagar (es buidarà el carro)");
        scan.nextLine();
        if (scan.nextLine().trim().isEmpty()) {
            carro.buidarCarro();
        } else {
            System.out.println("No s'ha pogut pagar, torna-ho a intentar");
        }
    }

    /**
     * Mètode del controlador per mostrar el carro de la compra
     */
    public static void mostrarCarroDeLaCompra() {
        carro.mostrarCarro(Model.getProductesCarroQuantitats());
    }

}