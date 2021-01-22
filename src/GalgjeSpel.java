import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*
naar origineel idee van spel Sylvain Saurel -
https://medium.com/swlh/creating-a-hangman-game-in-java-2c3088cb0d6d
 */


/*
CHALLENGES:
I) laat het spel opnieuw starten
II) hou een highscore bij
III) "Maak een Galgje animatie met ASCII codes"
 */

public class GalgjeSpel {
    // Woorden die geraden kunnen worden

    public static final String[] WOORDEN = {"piano", "drum", "gitaar", "viool",
            "fluit", "trompet", "hoorn", "harp", "trommel", "orgel", "harmonica",
            "doedelzak", "saxofoon", "klarinet", "synthesizer"
    };

    //willekeurig getal generator om willekeurig woord te kiezen
    public static final Random RANDOM = new Random();

    // het maximaal aantal fouten voor Game Over
    public static final int MaxAantalFouten = 8;

    // het te vinden woord - dit is het woord dat de computer willekeurig selecteert aan het begin van het spel
    private String zoekWoord;

    // het woord gevonden in een array van characters die de speler ingetypt heeft
    private char[] gevondenWoord;

    private int aantalFouten;

    //letters die al geraden zijn door de gebruiker
    private ArrayList<String> letters = new ArrayList<String>();

    //methode die het woord dat gevonden moet worden willekeurig selecteerd (getal op basis van lengte WOORDEN array
    private String volgendeWoord() {
        return WOORDEN[RANDOM.nextInt(WOORDEN.length)];
    }

    //nieuw spel starten - arraylist letters leegmaken en aantal fouten reset en woord willekeurig geselecteerd
    public void nieuwSpel() {
        aantalFouten = 0;
        letters.clear();
        zoekWoord = volgendeWoord();

        // zet de lengte van het gevondenWoord gelijk met het te raden woord (zoekWoord)
        gevondenWoord = new char[zoekWoord.length()];
        for (int i = 0; i < gevondenWoord.length; i++) {
            gevondenWoord[i] = '_';
        }
    }

    // als het zoekWoord overeenkomt met de String van het gevondenWoord, returnt methode true en is het spel gewonnen
    public boolean goedGeraden() {
        return zoekWoord.contentEquals(new String(gevondenWoord));
    }

    // methode die het woord bijwerkt nadat de gebruiker een karakter invuld (alleen als karakter niet eerder is ingevuld)
    private void enter(String c) {
        if (!letters.contains(c)) {
            // zit het karakter in het woord?
            if (zoekWoord.contains(c)) {
                // ja, dus we gaan het liggend streepje (_) vervangen door het ingevulde karakter
                int index = zoekWoord.indexOf(c);
                while (index >= 0) {
                    gevondenWoord[index] = c.charAt(0);
                    index = zoekWoord.indexOf(c, index + 1);
                }
            } else {
                // nee, karakter zit niet in het woord, dus een fout!
                aantalFouten++;
            }
            // karakter is nu toegevoegd aan de letters die door de gebruiker ingevoerd zijn
            letters.add(c);
        }
    }

    // methode die de tot nu toe goed geraden letters teruggeeft
    private String aantalLettersGoed() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < gevondenWoord.length; i++) {
            builder.append(gevondenWoord[i]);
            if (i < gevondenWoord.length - 1) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    // methode om Galgje te spelen (spel - loop)
    public void spel() {
        try (Scanner input = new Scanner(System.in)) {
            // als het aantalFouten lager is dan het maximum (MaxAantalFouten) wordt er gespeeld of de speler heeft het woord geraden
            while (aantalFouten < MaxAantalFouten) {
                System.out.println("\nVoer een letter in : ");
                //input van speler
                String invoer = input.next();
                //enkel de eerste letter bewaren
                if (invoer.length() > 1) {
                    invoer = invoer.substring(0, 1);
                }

                // gevonden woord bijwerken
                enter(invoer);

                // staat van het raden - welke letters zijn goed?
                System.out.println("\n" + aantalLettersGoed());

                // check woord goedgeraden
                if (goedGeraden()) {
                    System.out.println("\nGewonnen!");
                    break;
                } else {
                    //aantal beurten over
                    System.out.println("\nAantal pogingen over : " + (MaxAantalFouten - aantalFouten));
                }
            }

            if (aantalFouten == MaxAantalFouten) {
                //verloren
                System.out.println("\nHelaas, verloren!");
                System.out.println("Het geheime woord was : " + zoekWoord);
            }
        }
    }

    // Main methode - titel en begin spel
    public static void main(String[] args) {
        System.out.println("*************>>>> Welkom bij Muziekinstrumenten Galgje <<<<***************");
        System.out.println("****************>>>> gebruik alleen kleine letters <<<<*******************");
        GalgjeSpel spelletje = new GalgjeSpel();
        spelletje.nieuwSpel();
        spelletje.spel();
    }
}




