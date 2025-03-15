## 🛒 SAPAMERCAT

*EN* <br>
Java application to practice OOP, Collections, Exceptions, Streams and Lambda Expressions, following MVC pattern.

*ES* <br>
Aplicación de Java para practicar POO, Collections, Exceptions, Streams y Lambda Expressions, siguiendo el patrón de diseño MVC.

*CA* <br>
Aplicació de Java per practicar POO, Collections, Exceptions, Streams i Lambda Expressions, seguint el patró de diseny MVC.

## Detalls:

### 🛠️ Lògica del Model de l'aplicació:
El Model de l'aplicació és la classe que gestiona les dades dels productes.

- Utilitzem un **HashSet** per emmaganatzemar els productes disponibles per comprar.
  Així aconseguim evitar duplicats. El HashCode i equals dels productes són basats en el seu codi de barres.


- Utilitzem un **Deque ArrayDeque** com la principal forma d'emmagatzemar productes al carro. Això ens permet afegir i eliminar productes de forma eficient, és equivalent a un **Stack**.


- Utilitzem un **HashMap** per emmagatzemar els productes que s'han afegit al carro de la compra. Utilitzem un valor Integer per guardar la quantitat de productes presents al carro.


- Utilitzem una **Queue Priority Queue** per emmagatzemar els productos afegits al carro segons la data de caducitat dels productes d'Alimentació, ja que tenen prioritat al passar per caixa.


- Utilitzem un **ArrayList** per emmagatzemar els productes en format String i mostrar i guardar el tiquet de compra.

La classe compta amb mètodes per afegir i eliminar productes al sistema; buscar, guardar i eliminar productes al carro de la compra, i mètodes per mostrar i guardar el tiquet de compra.

### 🛒 Entitats de Producte:


**Producte**: Classe abstracta que defineix els atributs i mètodes bàsics d'un producte.
Compta amb els atributs comuns **Codi** (de barres), **Nom** i **Preu**.


- 🥕 **Alimentacio**: Classe filla de Producte que defineix els atributs i mètodes d'un producte d'alimentació.
  Compta amb l'atribut especial **data de caducitat**, que s'utilitza per <u>ordenar els productes d'alimentació</u> i <u>calcular un sobrepreu</u>.


- 💻 **Electronica**: Classe filla de Producte que defineix els atributs i mètodes d'un producte d'electrònica.
  Compta amb l'atribut especial dels **dies de garantia**, que són utilitzats <u>per calcular un sobrepreu</u>.


- 👕 **Textil**: Classe filla de Producte que defineix els atributs i mètodes d'un producte tèxtil.
  Compta amb l'atribut especial de la seva **composició tèxtil**, que es un **Enum**, i s'utilitza per <u>ordenar els productes tèxtils</u>.

### ⚡ Custom Exceptions:

Exceptions personalitzades que utilitzem, a més de les estàndar, a diferents parts del projecte.

- **CarroBuitException**


- **DataCaducitatException**


- **EnumFailException**


- **LimitCaractersException**


- **LimitProductesException**


- **NegatiuException**


## 🚀 Còm utilitzar l'aplicació?

Al iniciar l'aplicació a la consola, sortirà el menú principal.


Hi ha alguns productes per defecte afegits al sistema del supermercat, però pots afegir o eliminar productes individuals desde el menú de 'Gestió de Magatzem i Compres'.
El programa et demanarà introduir les dades del producte.


Per comprar, hauràs d'afegir productes al Carro, que hauràn d'estar registrats al sistema previament.
Per afegir un producte al carro, només has d'introduir el codi de barres d'algún producte del inventari.


Hi ha més opcions, com guardar el registre de tiquets de compra a un arxiu de text, o ordenar els productes per la data de caducitat o la composició tèxtil.
Totes aquestes opcions són fàcilment accesibles desde el menú de navegació per consola.


