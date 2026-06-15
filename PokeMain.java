package OOP.Aufgaben.A4_Pokemon;
import java.util.*;

import static OOP.Aufgaben.A4_Pokemon.ASCII_Art.*;

public class PokeMain {
    public static void run(){

        //Programmvariablen
        String spielerName = "Trainer";
        String gegnerName = "Team-Rocket-Scherge";
        Scanner sc = new Scanner(System.in);
        Random rnd = new Random();
        int teamSize = 3;
        boolean spielerZug;
        int dmg;
        int wahl;

        //Spieler und Gegner vorbereiten
        Trainer spieler = new Trainer(spielerName);
        spieler.team = new Pokemon[teamSize];
        Trainer gegner = new Trainer(gegnerName);
        gegner.team = new Pokemon[teamSize];

        //Spezialattacken instanziieren
        Special donnerschock = new Special("Donnerschock", "Wasser", "Flug", "ein Blitz fährt aus seinen Wangen.");
        Special aquaknarre = new Special("Aquaknarre", "Feuer", "Elektro", "eine Wassersäule schießt mit enormem Druck aus seinem Maul.");
        Special flammenwurf = new Special("Flammenwurf", "Pflanze", "Wasser", "sengende Hitze spült über dein Gesicht.");
        Special rankenhieb = new Special("Rankenhieb", "Flug", "Feuer", "zwei Ranken peitschen voller Kraft.");
        Special windstoss = new Special("Windstoß", "Elektro", "Pflanze", "die Druckwelle ist gewaltig.");
        Special kratzer = new Special("Kratzer", "", "", "die schimmernden Klauen sind unfassbar schnell.");

        //Pokemon instanziieren
        Pokemon pikachu = new Pokemon("Pikachu", "Elektro", 75, 1.1f, 1.2f, donnerschock, "die Luft knistert vor Spannung.");
        Pokemon schiggy = new Pokemon("Schiggy", "Wasser", 95, 1.0f, 0.8f, aquaknarre, "sein Panzer schimmert wie Granit.");
        Pokemon glumanda = new Pokemon("Glumanda", "Feuer", 75, 1.2f, 1.0f, flammenwurf, "seine Schwanzspitze lodert heiß.");
        Pokemon bisasam = new Pokemon("Bisasam", "Pflanze", 85, 1.0f, 0.9f, rankenhieb, "die Ranken an seinem Rücken tanzen wie Schlangen.");
        Pokemon taubsi = new Pokemon("Taubsi", "Flug", 70, 1.0f, 1.3f, windstoss, "es breitet stolz seine Flügel aus.");
        Pokemon mauzi = new Pokemon("Mauzi", "Normal", 80, 1.1f, 1.1f, kratzer, "seine Klauen funkeln gefährlich.");

        //Alle Pokemon in Array speichern und daraus dynamische Liste erstellen
        Pokemon[] allePoke = {pikachu, schiggy, glumanda, bisasam, taubsi, mauzi};
        List<Pokemon> freiePoke = new ArrayList<>(Arrays.asList(allePoke));

        //EINLEITUNG, Spieler wählt Pokemon                                                                               //(evtl. noch Try-catch-Blöcke bei Pokemon-Auswahlen ergänzen)
        titleA();
        System.out.printf("Willkommen beim Pokemon-Duell, %s! \n", spieler.name);
        if (teamSize == 1)
            System.out.print("Wähle ein Pokemon: \n");
        else
            System.out.printf("Wähle nacheinander %d Pokemon: \n", teamSize);
        int weiterePoke = teamSize;
        for (int i = 0; i < teamSize; i++){                         // Schleife wiederholt Pokemon-Auswahl bis zur gewünschten Teamgröße
            for (int j = 0; j < freiePoke.size(); j++)              // Schleife zeigt alle unvergebenen Pokemon an
                System.out.printf("[%d] %s \n", (j + 1), freiePoke.get(j).name);
            System.out.print("Eingabe: ");
            wahl = sc.nextInt();
            spieler.team[i] = freiePoke.get(wahl - 1);              // Team-Array wird mit aus Liste ausgewähltem Pokemon befüllt
            System.out.println(freiePoke.get(wahl - 1).name + " wurde ins Team aufgenommen.\n");
            freiePoke.remove(wahl - 1);                       // gewähltes Pokemon wird aus Liste der verfügbaren Pokemon gestrichen
            weiterePoke--;
            if (weiterePoke > 0)
                System.out.printf("Wähle noch %d Pokemon:\n", weiterePoke);
            else {
                System.out.println("Dein Team ist vollständig!");
            }
        }

        //Gegner wählt übrige Pokemon zufällig
        for (int k = teamSize-1; k >= 0; k--) {                  //Nicht mit 0 anfangen, sondern von oben, damit die Indexe der Pokemon beim Löschen aus der Liste nicht verrutschen.
            wahl = rnd.nextInt(freiePoke.size());
            gegner.team[k] = freiePoke.get(wahl);
            freiePoke.remove(wahl);
        }

        //Gegner wird angekündigt
        System.out.printf("\nJemand kommt auf dich zu und zückt einen Pokeball. \n%s: \"Hey du, Zeit für ein Duell!\"\n\n", gegner.name);

        //Gegner ruft erstes Pokemon
        gegner.summon(0);
        System.out.printf("%s schickt %s in den Kampf – %s \n", gegner.name, gegner.kaempfendesPoke.name, gegner.kaempfendesPoke.meinFlavor);
        System.out.printf("%s \t(%d/%d HP) \n\n", gegner.kaempfendesPoke.name, gegner.kaempfendesPoke.hp, gegner.kaempfendesPoke.hpMax);

        //Spieler ruft erstes Pokemon
        System.out.println("Welches Pokemon willst du einsetzen?");
        for (int l = 0; l < spieler.team.length; l++) {
            System.out.printf("[%d] %s ", l + 1, spieler.team[l].name);
            if (spieler.team[l].ko)
                System.out.println("\t>> K.O.! <<");
            else
                System.out.printf("\t (%d/%d HP) \n", spieler.team[l].hp, spieler.team[l].hpMax);
        }
        do {
            System.out.print("Eingabe: ");
            wahl = sc.nextInt();
            spieler.summon(wahl - 1);
            if (!spieler.team[wahl - 1].ko)
                System.out.printf("\nDu schickst %s in den Kampf – %s \n", spieler.kaempfendesPoke.name, spieler.kaempfendesPoke.meinFlavor);
            else
                System.out.println(spieler.team[wahl - 1].name + " ist kampfunfähig! Wähle ein anderes Pokemon. \n");
        } while (spieler.team[wahl - 1].ko);

        do {    //Äußere Do-while-Schleife für Kampf Spieler vs. Gegner inkl. aller Pokemon

            //Pokemon mit höherem Dex-Wert fängt an.
            if (spieler.kaempfendesPoke.dex >= gegner.kaempfendesPoke.dex) {
                spielerZug = true;
                System.out.printf("\n%s ist schneller als %s und fängt an.\n\n", spieler.kaempfendesPoke.name, gegner.kaempfendesPoke.name);
            } else {
                spielerZug = false;
                System.out.printf("\n%s ist schneller als %s und fängt an.\n\n", gegner.kaempfendesPoke.name, spieler.kaempfendesPoke.name);
            }

            do {                                           //Innere Do-while-Schleife für den Kampf zwischen den zwei aktuell aktiven Pokemon
                while (spielerZug) {                       //Spieler ist am Zug, wenn boolean wahr ist.
                    System.out.printf("%s ist dran! (%d/%d HP) \n", spieler.kaempfendesPoke.name, spieler.kaempfendesPoke.hp, spieler.kaempfendesPoke.hpMax);
                    System.out.printf("[1] Attacke \n[2] %s (%d übrig) \nEingabe:", spieler.kaempfendesPoke.meinSpecial.name, spieler.kaempfendesPoke.specialCharge);
                    wahl = sc.nextInt();
                    switch (wahl) {
                        case 1:
                            gegner.kaempfendesPoke.applyDmg(spieler.kaempfendesPoke.attack());          //Normaler Angriff: attack() erzeugt Rückgabewert, der an Setter applyDmg() übergeben wird
                            spielerZug = false;
                            break;
                        case 2:
                            dmg = spieler.kaempfendesPoke.specialAttack(spieler.kaempfendesPoke.meinSpecial);   //Spezialangriff wird aufgerufen und die Parameter für genau dieses Pokemon übergeben.
                            if (spieler.kaempfendesPoke.meinSpecial.starkGegen.equals(gegner.kaempfendesPoke.typ)) {
                                dmg = (int) (dmg * 1.5f);                               //Spezialangriffe sind gegen bestimmte Pokemon stärker oder schwächer (Wasser gegen Feuer usw.)
                                System.out.printf("Der Angriff ist besonders effektiv gegen %s! \n", gegner.kaempfendesPoke.name);
                            } else if (spieler.kaempfendesPoke.meinSpecial.schwachGegen.equals(gegner.kaempfendesPoke.typ)) {
                                dmg = (int) (dmg * 0.5f);
                                System.out.printf("Der Angriff zeigt wenig Wirkung gegen %s. \n", gegner.kaempfendesPoke.name);
                            }
                            gegner.kaempfendesPoke.applyDmg(dmg);              //Schaden wird errechnet und in dmg-Variablen dem applyDmg-Setter übergeben
                            System.out.printf("%s verursacht %d Schaden. \n\n", spieler.kaempfendesPoke.name, dmg);
                            spielerZug = false;
                            break;
                        default:
                            System.out.println("Ungültige Eingabe");
                            break;
                    }
                }
                while (!spielerZug && gegner.kaempfendesPoke.hp > 0) {                          //Gegner ist am Zug, wenn boolean spielerZug falsch ist und sein Pokemon noch steht
                    System.out.printf("%s ist dran! (%d/%d HP) \n", gegner.kaempfendesPoke.name, gegner.kaempfendesPoke.hp, gegner.kaempfendesPoke.hpMax);
                    wahl = rnd.nextInt(2);
                    if (wahl == 1 && gegner.kaempfendesPoke.specialCharge > 0) {                //Gegner setzt mit 50-prozentiger Chance Spezialangriff ein, solange er welche übrig hat.
                        dmg = gegner.kaempfendesPoke.specialAttack(gegner.kaempfendesPoke.meinSpecial);
                        if (gegner.kaempfendesPoke.meinSpecial.starkGegen.equals(spieler.kaempfendesPoke.typ)) {
                            dmg = (int) (dmg * 1.5f);
                            System.out.printf("Der Angriff ist besonders effektiv gegen %s! \n", spieler.kaempfendesPoke.name);
                        } else if (gegner.kaempfendesPoke.meinSpecial.schwachGegen.equals(spieler.kaempfendesPoke.typ)) {
                            dmg = (int) (dmg * 0.5f);
                            System.out.printf("Der Angriff zeigt wenig Wirkung gegen %s. \n", spieler.kaempfendesPoke.name);
                        }
                        spieler.kaempfendesPoke.applyDmg(dmg);
                        System.out.printf("%s verursacht %d Schaden. \n\n", gegner.kaempfendesPoke.name, dmg);
                        spielerZug = true;
                    } else {
                        spieler.kaempfendesPoke.applyDmg(gegner.kaempfendesPoke.attack());
                        spielerZug = true;
                    }
                }
            } while (spieler.kaempfendesPoke.hp > 0 && gegner.kaempfendesPoke.hp > 0);          //Innere Do-while-Schleife wiederholt sich, solange beide aktive Pokemon noch stehen.

            //Ein Pokemon wurde besiegt
            if (spieler.kaempfendesPoke.hp <= 0) {
                System.out.printf("%s wurde besiegt! \n", spieler.kaempfendesPoke.name);
                spieler.kaempfendesPoke.ko = true;
                spieler.AnzPokeBesiegt = 0;                                                     //erst auf 0 setzen, dann durchzählen:
                for (int p = 0; p < spieler.team.length; p++)                                   //Wie viele Pokemon im Team sind K.O.? …
                    if (spieler.team[p].ko)
                        spieler.AnzPokeBesiegt++;
                if (spieler.AnzPokeBesiegt == spieler.team.length) {                            //… wenn alle, dann Spieler besiegt.
                    System.out.println("Du hast keine Pokemon mehr übrig! \n");
                    lose();
                    System.exit(0);
                } else {
                    System.out.println("Welches Pokemon willst du einsetzen?");                 //Spieler wählt neues Pokemon, Code von oben wiederverwendet
                    for (int l = 0; l < spieler.team.length; l++) {
                        System.out.printf("[%d] %s ", l + 1, spieler.team[l].name);
                        if (spieler.team[l].ko)
                            System.out.println("\t>> K.O.! <<");
                        else
                            System.out.printf("\t (%d/%d HP) \n", spieler.team[l].hp, spieler.team[l].hpMax);
                    }
                    do {
                        System.out.print("Eingabe: ");
                        wahl = sc.nextInt();
                        spieler.summon(wahl - 1);
                        if (!spieler.team[wahl - 1].ko)
                            System.out.printf("\nDu schickst %s in den Kampf – %s \n", spieler.kaempfendesPoke.name, spieler.kaempfendesPoke.meinFlavor);
                        else
                            System.out.println(spieler.team[wahl - 1].name + " ist kampfunfähig! Wähle ein anderes Pokemon. \n");
                    } while (spieler.team[wahl - 1].ko);
                }
            }
            if (gegner.kaempfendesPoke.hp <= 0) {
                System.out.printf("%s wurde besiegt! \n", gegner.kaempfendesPoke.name);
                gegner.kaempfendesPoke.ko = true;
                gegner.AnzPokeBesiegt = 0;
                for (int p = 0; p < gegner.team.length; p++)
                    if (gegner.team[p].ko)
                        gegner.AnzPokeBesiegt++;
                if (gegner.AnzPokeBesiegt == gegner.team.length) {
                    System.out.println(gegner.name + " hat keine kampffähigen Pokemon mehr! \n");
                    win();
                    System.exit(0);
                } else {
                    gegner.summon(gegner.AnzPokeBesiegt);                                       //Abhängig von Anzahl besiegter eigener Pokemon wird das nächste aus dem Array geholt.
                    System.out.printf("%s schickt %s in den Kampf – %s \n", gegner.name, gegner.kaempfendesPoke.name, gegner.kaempfendesPoke.meinFlavor);
                    System.out.printf("%s \t(%d/%d HP) \n", gegner.kaempfendesPoke.name, gegner.kaempfendesPoke.hp, gegner.kaempfendesPoke.hpMax);
                }
            }
        } while (spieler.AnzPokeBesiegt < spieler.team.length && gegner.AnzPokeBesiegt < gegner.team.length); //Äußere Do-while-Schleife wiederholt sich, solange beide Trainer noch Pokemon haben.
    }
}