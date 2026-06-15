package OOP.Aufgaben.A4_Pokemon;
import java.util.Random;

public class Pokemon {
    String name;                //Name
    String typ;                 //Typ: Feuer, Wasser, Pflanze, Elektro, Flug, Normal
    int hp;                     //aktuelle Lebenspunkte, bei 0 besiegt
    int hpMax;                  //Lebenspunkte-Maximum, für Abgleich mit aktuellen HP
    float str;                  //Stärke, Mod für Angriffe, 1.0 ist neutral
    float dex;                  //Geschick, Pokemon mit höherem Wert fängt an                   //(evtl. Chance auf Ausweichen ergänzen)
    Special meinSpecial;        //Ergänzt Spezialangriff-Methode um spezielle Eigenschaften des ausführenden Pokemon
    int specialCharge = 3;      //Anzahl verbleibender Spezialangriffe
    boolean ko = false;         //wird true, wenn HP == 0
    String meinFlavor;          //individualisiert Textausgabe beim Rufen des Pokemon

    //Constructor
    public Pokemon(String name, String typ, int hp, float str, float dex, Special meinSpecial, String meinFlavor) {
        this.name = name;
        this.typ = typ;
        this.hp = hp;
        hpMax = this.hp;
        this.str = str;
        this.dex = dex;
        this.meinSpecial = meinSpecial;
        this.meinFlavor = meinFlavor;
    }

    //Setter: senkt HP eines angegriffenen Pokemon, aber nicht unter 0
    public void applyDmg(int dmg){
        hp -= dmg;
        if (hp < 0)
            hp = 0;
    }

    //Methode: Normaler Angriff
    public int attack(){
        System.out.printf("%s greift an …\n", name);
        Random rnd = new Random();
        int dmg = rnd.nextInt(11) + 10;               // 10 bis 20 base dmg
        if (dmg >= 18)
            System.out.print("VOLLTREFFER! ");
        else if (dmg <= 12)
            System.out.print("Nur gestreift! ");
        else
            System.out.print("Treffer! ");
        dmg = (int) (dmg * str);
        System.out.printf("%s verursacht %d Schaden.\n\n", name, dmg);
        return dmg;
    }

    //Methode: Spezialangriff
    public int specialAttack(Special meinSpecial){
        int dmg = 0;
        if (specialCharge <= 0) {
            System.out.printf("%s ist zu erschöpft, um %s auszuführen.\n\n", name, meinSpecial.name);
        } else {
            System.out.printf("%s setzt %s ein, %s\n", name, meinSpecial.name, meinSpecial.flavor);
            specialCharge--;
            Random rnd = new Random();
            dmg = rnd.nextInt(16) + 15;              // 15 bis 30 base dmg
            dmg = (int) (dmg * str);
        }
        return dmg;
    }
}