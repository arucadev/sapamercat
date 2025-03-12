package model;

import entities.Producte;

import java.util.*;
import java.util.stream.Collectors;

import entities.Textil;
import exceptions.DataCaducitatException;
import exceptions.LimitProductesException;

public class CarroCompra {

    // Atributs
    private static final int MAX_PRODUCTES = 100;
    private List<Producte> productes;
    private Map<String, List<Producte>> productesMap;

    // Constructor
    public CarroCompra() {
        this.productes = new ArrayList<>();
        this.productesMap = new HashMap<>();
    }

    /**
     * Afegir producte comprovant que no supera el màxim de productes
     * @param producte Producte
     * @return boolean
     */
    public void afegirProducte(Producte producte) throws LimitProductesException {
        if (productes.size() >= MAX_PRODUCTES) {
            throw new LimitProductesException("No es poden afegir més de " + MAX_PRODUCTES + " productes al carro");
        }

        // Verificar restricció per tèxtil, no duplicats per codi
        if (producte instanceof Textil) {
            if (productesMap.containsKey(producte.getCodi())) {
                return; // No afegim producte si ja existeix
            }
        }

        productes.add(producte);
        // Afegir producte al mapa de productes agrupats per codi.
        productesMap.put(producte.getCodi(), productes);
    }

    /**
     * Obtenir tots els productes
     * @return productes
     */
    public List<Producte> getProductes() {
        return productes;
    }

    /**
     * Buidar carro
     */
    public void buidarCarro() {
        productes.clear();
        productesMap.clear();
    }

    /**
     * Calcular preu total del carro
     * @return double amb preu total
     */
    public double calcularPreuTotal() {
        double total = 0;

        // Posem el try catch dins del bucle 'for', així si un producte està caducat, es cobra el preu normal i es continua amb la resta de productes.
        // Per què és necessari el try catch dins del bucle 'for'? Si no atrapem les excepcions de cada producte un per un, fallaria tota la funció si un producte està caducat.
        for (Producte producte : productes) {
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
     * Agrupa els productes per codi de barres i preu.
     * La funció transforma la llista de productes en un mapa on la clau és una combinació del codi de barres i el preu del producte, i el valor és una llista de productes que tenen el mateix codi i preu.
     *
     * @return Mapa on la clau és "codi_preu" i el valor és una llista de productes.
     */
    public Map<String, List<Producte>> getProductesAgrupatsPerCodiIPreu() {
        return productes.stream()
                .collect(Collectors.groupingBy(p -> p.getCodi() + "_" + p.getPreu()));
    }

    /**
     * Busca un producte pel seu codi de barres.
     * La funció recorre la llista de productes i retorna un Optional amb el primer Producte que coincideixi amb el codi proporcionat. Si no es troba cap producte, retorna un Optional buit.
     *
     *
     * @param codi Codi de barres del Producte.
     * @return Optional amb el Producte trobat o buit si no es troba.
     */
    public Optional<Producte> buscarPerCodi(String codi) {
        return productes.stream()
                .filter(p -> p.getCodi().equals(codi))
                .findFirst();

        // En aquest cas necessitem que retorni un Optional<Producte> i no un String, perque necessitem l'objecte complet per accedir a les seves propietats i mètodes, mentre que si retorna String només podem saber el nom.
    }

    /**
     * Obté el nom d'un Producte a partir del seu codi de barres.
     * La funció busca en la llista de productes i retorna el nom del primer Producte que coincideixi amb el codi de barres proporcionat. Si no es troba cap producte, retorna el missatge "Producte no trobat".
     *
     * @param codi El codi de barres del producte a buscar.
     * @return El nom del producte si es troba, o "Producte no trobat" si no es troba.
     */
    public String getNomProductePerCodi(String codi) {
        return productes.stream()
                .filter(p -> p.getCodi().equals(codi))
                .map(p -> p.getNom())
                .findFirst()
                .orElse("Producte no trobat");

        /* Això és equivalent a:
        public String getNomProductePerCodi(String codi) {
            for (Producte p : productes) {
                if (p.getCodi().equals(codi)) {
                    return p.getNom();
                }
            }
            return "Producte no trobat";
        }
        */
    }

    /**
     * Obté una llista de productes tèxtils ordenats.
     * La funció filtra la llista de productes per obtenir només aquells que siguin de tipus Textil, els converteix a Textil, els ordena segons el comparador ComposicioComparatorLambda i els retorna en una llista.
     *
     * @return Una llista de productes tèxtils ordenats segons el comparador definit a 'Tèxtil' (composició tèxtil).
     */
    public List<Textil> getProductesTextilsOrdenats() {
        return productes.stream()
                .filter(p -> p instanceof Textil) // Filtra els Productes que son instancies de Textil
                .map(p -> (Textil) p) // Converteix els Productes a Textil
                .sorted(Textil.ComposicioComparatorLambda)// Ordena els Textils segons la composició tèxtil
                .collect(Collectors.toList()); // Recolecta els Textils i retorna una llista
    }

    /**
     * Obté un mapa amb quantitats de cada producte agrupades per codi.
     * La funció recorre la llista de productes i compta quantes vegades apareix cada codi de barres, emmagatzemant la informació en un Map on la clau és el codi del producte i el valor és la quantitat de productes amb aquest codi.
     *
     * @return  Un mapa amb el codi del producte com a clau i la quantitat com a valor.
     */
    public Map<String, Integer> getQuantitatPerProducte() {
        Map<String, Integer> quantitats = new HashMap<>();

        productes.forEach(p -> {
            quantitats.put(p.getCodi(), quantitats.getOrDefault(p.getCodi(), 0) + 1);
        });

        return quantitats;
    }
}
