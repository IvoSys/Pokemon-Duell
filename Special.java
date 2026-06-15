package OOP.Aufgaben.A4_Pokemon;
import java.util.Random;

public class Special {
    String name;
    String starkGegen;              // Elektro > Wasser > Feuer > Pflanze > Flug > Elektro
    String schwachGegen;            // umgekehrt
    String flavor;                  // individualisiert Textausgabe beim Ausführen des Spezialangriffs

    public Special(String name, String starkGegen, String schwachGegen, String flavor){
        this.name = name;
        this.starkGegen = starkGegen;
        this.schwachGegen = schwachGegen;
        this.flavor = flavor;
    }
}