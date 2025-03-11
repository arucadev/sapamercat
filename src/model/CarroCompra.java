package model;

import entities.Producte;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarroCompra {

    // Atributs
    private static final int MAX_PRODUCTES = 100;
    private List<Producte> productes;

    // Constructor
    public CarroCompra() {
        this.productes = new ArrayList<>();
    }

    /**
     * Afegir producte comprovant que no supera el màxim de productes
     * @param producte Producte
     * @return boolean
     */
    public boolean afegirProducte(Producte producte) {
        if (productes.size() < MAX_PRODUCTES) {
            productes.add(producte);
            return true;
        }
        return false;
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
    }

    /**
     * Calcular preu total del carro
     * @return double preuTotal
     */
    public double calcularPreuTotal() {
        double preuTotal = 0;
        for (Producte producte : productes) {
            // Aquí deberías llamar al método específico de cálculo de precio
            // según el tipo de producto
            preuTotal += producte.getPreu(); // Esto es simplificado
        }
        return preuTotal;
    }

    /**
     * Obtenir un mapa amb productes agrupats per codi de barres per mostrar el carro amb productes repetits units
     * @return Map productesAgrupats
     */
    public Map<String, List<Producte>> getProductesAgrupats() {
        Map<String, List<Producte>> productesAgrupats = new HashMap<>();

        for (Producte producte : productes) {
            if (!productesAgrupats.containsKey(producte.getCodi())) {
                productesAgrupats.put(producte.getCodi(), new ArrayList<>());
            }
            productesAgrupats.get(producte.getCodi()).add(producte);
        }

        return productesAgrupats;
    }
}
