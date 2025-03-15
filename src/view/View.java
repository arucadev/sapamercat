package view;

public class View {

    public static void menuPrincipal() {
        System.out.println("1) Gestió de Magatzem i Compres");
        System.out.println("2) Afegir Producte al Carro");
        System.out.println("3) Passar per Caixa");
        System.out.println("4) Mostrar Carro de la Compra");
        System.out.println("0) Sortir");
    }

    public static void menuMagatzemICompres() {
        System.out.println("1) Introduir Producte al Sistema");
        System.out.println("2) Eliminar Producte del Sistema");
        System.out.println("3) Crear Arxiu amb registre de tiquets de compra");
        System.out.println("4) Caducitat");
        System.out.println("5) Composició tèxtil");
        System.out.println("0) Tornar");
    }

    public static void menuIntroduirProducte() {
        System.out.println("1) Alimentació");
        System.out.println("2) Tèxtil");
        System.out.println("3) Electrònica");
        System.out.println("0) Tornar");
    }

    public static void menuPassarPerCaixa() {
        // Mostrar tiquet
    }

    public static void menuMostrarCarroDeLaCompra() {
        System.out.println("1) Mostrar Productes del Carro");
        System.out.println("2) Eliminar Producte del Carro");
        System.out.println("0) Tornar");
    }

    public static void separador() {
        System.out.println("--------------------------------------------------");
    }

}
