package model;

import model.entities.Alimentacio;
import model.entities.Producte;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import model.entities.Textil;
import model.exceptions.CarroBuitException;
import model.exceptions.DataCaducitatException;
import model.exceptions.LimitProductesException;

public class Model {

    Scanner scan = new Scanner(System.in);

    // ATRIBUTS

    private static final int MAX_PRODUCTES = 100;

    /**
     * Conté tots els productes comprables. Sense duplicats
     */
    private static Set<Producte> productes = new HashSet<Producte>();

    /**
     * Conté tots els productes afegits al carro. Equivalent a Stack
     */
    private static Deque<Producte> productesCarro = new ArrayDeque<>();

    /**
     * Conté tots els productes afegits al carro, amb la quantitat de cada producte
     */
    private static Map<Producte, Integer> productesCarroQuantitats = new HashMap<>(); // Clau: Producte, Valor: Quantitat

    /**
     * Conté tots els productes afegits al carro, ordenats per defecte per ordre natural
     */
    private static Queue<Producte> productesCarroPrioritat = new PriorityQueue<Producte>();

    /**
     * Conté tots els productes afegits al carro, en format String. És el tiquet de compra.
     */
    private static List<String> tiquetCompra = new ArrayList<String>();

    // GETTERS

    /**
     * Retorna tots els productes del sistema disponibles per ser comprats
     *
     * @return
     */
    public static Set<Producte> getProductes() {
        return productes;
    }

    /**
     * Retorna tots els productes afegits al carro
     *
     * @return
     */
    public static Deque<Producte> getProductesCarro() {
        return productesCarro;
    }

    /**
     * Retorna tots els productes afegits al carro amb ordre de prioritat
     *
     * @return
     */
    public static Queue<Producte> getProductesCarroPrioritat() {
        return productesCarroPrioritat;
    }

    /**
     * Retorna tots els productes afegits al carro amb la quantitat de cada producte
     *
     * @return
     */
    public static Map<Producte, Integer> getProductesCarroQuantitats() {
        return productesCarroQuantitats;
    }

    /**
     * Retorna el tiquet de compra
     *
     * @return
     */
    public static List<String> getTiquetCompra() {
        return tiquetCompra;
    }


    // MÈTODES

    // Afegir producte al sistema:

    /**
     * Afegir producte al sistema
     *
     * @param producte
     * @return True si s'ha afegit, False si no s'ha afegit
     */
    public boolean afegirProducteSistema(Producte producte) {
        return productes.add(producte);
    }

    // Eliminar producte del sistema:

    /**
     * Eliminar producte del sistema buscant per codi
     *
     * @param codi Codi de barres del producte
     * @return True si s'ha eliminat, False si no s'ha eliminat
     */
    public boolean eliminarProducteSistema(String codi) {
        // Buscar producte per codi
        Producte producte = buscarPerCodiSistema(codi);

        // Eliminar del sistema
        return productes.remove(producte);
    }

    /**
     * Busca un producte del sistema pel seu codi de barres i retorna el producte que coincideix amb el codi
     *
     * @param codi Codi de barres del Producte.
     * @return Producte
     */
    public Producte buscarPerCodiSistema(String codi) {
        return productes.stream()
                .filter(p -> p.getCodi().equals(codi))
                .findFirst()
                .orElse(null);

        /* Això és equivalent a:

        public Producte buscarPerCodi(String codi) {
            for (Producte p : productes) {
                if (p.getCodi().equals(codi)) {
                    return Producte;
                }
            }
            return null
        }
        */
    }

    // Afegir producte al carro:

    /**
     * Afegir producte al carro comprovant que no supera el màxim de productes
     *
     * @param producte Producte
     * @return True si s'ha afegit, False si no s'ha afegit
     */
    public boolean afegirProducteCarro(Producte producte) throws LimitProductesException {
        if (productesCarro.size() >= MAX_PRODUCTES) {
            throw new LimitProductesException("No es poden afegir més de " + MAX_PRODUCTES + " productes al carro");
        }

        // Verificar restricció per tèxtil, no duplicats per codi
        if (producte instanceof Textil) {
            if (productesCarroQuantitats.containsKey(producte)) {
                return false; // No afegim producte si ja existeix
            }
        }
        afegirProducteCarroCollections(producte);
        return true;
    }

    /**
     * Afegir producte a totes les collections que gestionen els productes del carro
     *
     * @param producte Producte
     */
    public void afegirProducteCarroCollections(Producte producte) {
        // Afegim producte a carro
        productesCarro.add(producte);
        // Si el producte ja existeix, incrementem la quantitat
        productesCarroQuantitats.put(producte, determinarQuantitats(producte));
        // Afegim producte a la cua de prioritat
        productesCarroPrioritat.add(producte);
    }

    /**
     * Determinar quantitats del producte al carro
     *
     * @param producte Producte
     * @return Quantitat del producte
     */
    public int determinarQuantitats(Producte producte) {
        if (productesCarroQuantitats.containsKey(producte)) {
            return productesCarroQuantitats.get(producte) + 1;
        } else {
            return 1;
        }
    }

    // Eliminar producte del carro

    /**
     * Eliminar producte del carro buscant per codi
     *
     * @param codi Codi de barres del producte
     * @return True si s'ha eliminat, False si no s'ha eliminat
     * @throws CarroBuitException Si el carro està buit
     */
    public boolean eliminarProducteCarro(String codi) throws CarroBuitException {
        if (productesCarro.isEmpty()) {
            throw new CarroBuitException("El carro està buit.");
        }

        // Busquem el producte pel codi
        Producte producte = buscarPerCodiCarro(codi);

        // Eliminem 1 quantitat de producte
        productesCarroQuantitats.put(producte, productesCarroQuantitats.get(producte) - 1);

        // Si la quantitat es 0, eliminem el producte de tot el carro
        if (productesCarroQuantitats.get(producte) == 0) {
            productesCarroQuantitats.remove(producte);
            productesCarro.remove(producte);
            productesCarroPrioritat.remove(producte);
            return true;
        } else return false;
    }

    /**
     * Buscar producte del carro per codi
     *
     * @param codi Codi de barres del producte
     * @return Producte o null si no s'ha trobat
     */
    public Producte buscarPerCodiCarro(String codi) {
        return productesCarro.stream()
                .filter(p -> p.getCodi().equals(codi))
                .findFirst()
                .orElse(null);
    }

    /**
     * Buidar carro
     */
    public void buidarCarro() {
        productesCarro.clear();
        productesCarroQuantitats.clear();
        productesCarroPrioritat.clear();
    }

    /**
     * Calcular preu total del carro
     *
     * @return double amb preu total
     */
    public double calcularPreuTotal() {
        double total = 0;

        // Posem el try catch dins del bucle 'for', així si un producte està caducat, es cobra el preu normal i es continua amb la resta de productes.
        // Per què és necessari el try catch dins del bucle 'for'? Si no atrapem les excepcions de cada producte un per un, fallaria tota la funció si un producte està caducat.
        for (Producte producte : productesCarro) {
            try {
                total += producte.calcularPreuFinal();
            } catch (DataCaducitatException e) {
                System.out.println("El producte " + producte.getNom() + " ha caducat, es cobrarà el preu normal.");
                total += producte.getPreu();
            }
        }
        return total;
    }

    /**
     * Passar per caixa imprimeix el tiquet de compra a la pantalla i el guarda al registre de tiquets
     *
     * @param productesCarroQuantitats
     */
    public void passarPerCaixa(Map<Producte, Integer> productesCarroQuantitats) {
        System.out.println("----------Tiquet de compra-----------");
        System.out.println("-------------------------------------");
        System.out.println("Data de la compra: " + LocalDate.now());
        System.out.println("-------------SAPAMERCAT--------------");

        for (Map.Entry<Producte, Integer> entry : productesCarroQuantitats.entrySet()) {
            Producte key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("Producte: ...... " + key.getNom());
            System.out.println("Preu: .......... " + key.getPreu() + "€");
            System.out.println("Quantitat: ..... " + value + " unitats");
        }
        System.out.println();
        System.out.println("Preu total: .... " + calcularPreuTotal() + "€");
        System.out.println();
        System.out.println("Gràcies per la seva compra!");
        System.out.println("-------------------------------------");
        generarTiquetCompra(productesCarroQuantitats);
    }

    /**
     * Generar tiquet de compra i guardar-lo a la llista de tiquets
     * @param productesCarroQuantitats
     */
    public void generarTiquetCompra(Map<Producte, Integer> productesCarroQuantitats) {
        StringBuilder tiquet = new StringBuilder();
        tiquet.append("----------Tiquet de compra-----------\n");
        tiquet.append("-------------------------------------\n");
        tiquet.append("Data de la compra: ").append(LocalDate.now()).append("\n");
        tiquet.append("-------------SAPAMERCAT--------------\n");

        for (Map.Entry<Producte, Integer> entry : productesCarroQuantitats.entrySet()) {
            Producte key = entry.getKey();
            Integer value = entry.getValue();
            tiquet.append("Producte: ...... ").append(key.getNom()).append("\n");
            tiquet.append("Preu: .......... ").append(key.getPreu()).append("€\n");
            tiquet.append("Quantitat: ..... ").append(value).append(" unitats\n");
        }
        tiquet.append("\n");
        tiquet.append("Preu total: .... ").append(calcularPreuTotal()).append("€\n");
        tiquet.append("\n");
        tiquet.append("Gràcies per la seva compra!\n");
        tiquet.append("-------------------------------------\n");

        tiquetCompra.add(tiquet.toString());
    }

    /**
     * Mostra els productes actuals del carro, la seva quantitat i el preu unitari i total.
     *
     * @param productesCarroQuantitats
     */
    public void mostrarCarro(Map<Producte, Integer> productesCarroQuantitats) {
        for (Map.Entry<Producte, Integer> entry : productesCarroQuantitats.entrySet()) {
            Producte key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("Producte: ...... " + key.getNom());
            System.out.println("Preu: .......... " + key.getPreu() + "€");
            System.out.println("Quantitat: ..... " + value + " unitats");
        }
        System.out.println("Preu total: .... " + calcularPreuTotal() + "€");
    }

    /**
     * Obté una llista de productes d'alimentació ordenats
     */
    public List<Alimentacio> getProductesAlimentacioOrdenats() {
        PriorityQueue<Alimentacio> alimentacioQueue = new PriorityQueue<>(Comparator.comparing(Alimentacio::getDataCaducitat));
        for (Producte producte : productes) {
            if (producte instanceof Alimentacio) {
                alimentacioQueue.add((Alimentacio) producte);
            }
        }
        List<Alimentacio> llistaOrdenada = new ArrayList<>();
        while (!alimentacioQueue.isEmpty()) {
            llistaOrdenada.add(alimentacioQueue.poll());
        }
        return llistaOrdenada;
    }

    /**
     * Obté una llista de productes tèxtils ordenats. La funció filtra la llista de productes per obtenir només aquells que siguin de tipus Textil, els converteix a Textil, els ordena segons el comparador ComposicioComparatorLambda i els retorna en una llista.
     *
     * @return Una llista de productes tèxtils ordenats segons el comparator definit a 'Textil' (composició tèxtil).
     */
    public List<Textil> getProductesTextilsOrdenats() {
        return productes.stream()
                .filter(p -> p instanceof Textil) // Filtra els Productes que son instancies de Textil
                .map(p -> (Textil) p) // Converteix els Productes a Textil
                .sorted(Textil.ComposicioComparatorLambda)// Ordena els Textils segons la composició tèxtil
                .collect(Collectors.toList()); // Recolecta els Textils i retorna una llista
    }

    /**
     * Escriu el registre de tiquets de compra a un arxiu de text
     */
    public void escriureArxiu() throws IOException {
        File arxiuTiquets = new File("./tiquets.txt");
        if (!arxiuTiquets.exists()) {
            System.out.println("L'arxiu no existeix, es crearà un nou arxiu amb el registre de tiquets actual.");
            arxiuTiquets.createNewFile();
        } else {
            System.out.println("L'arxiu ja existeix, es sobreescriuràn els tiquets!");
            System.out.println("Vols continuar? (S/N)");
            if (scan.nextLine().equalsIgnoreCase("N")) {
                return;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arxiuTiquets))) {
            for (String linia : tiquetCompra) {
                writer.write(linia);
                writer.newLine();
            }
        }
    }

}
