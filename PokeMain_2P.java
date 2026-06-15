package OOP.Aufgaben.A4_Pokemon;
import java.util.*;

import static OOP.Aufgaben.A4_Pokemon.ASCII_Art.*;

//  ZWEI-SPIELER-VARIANTE

public class PokeMain_2P {
    public static void run(){

        //Programmvariablen
        String p1Name;
        String p2Name;
        Scanner sc = new Scanner(System.in);
        Random rnd = new Random();
        int teamSize = 3;
        boolean p1Zug;
        int dmg;
        int wahl;

        //EINLEITUNG
        titleA();
        System.out.print("Willkommen beim Pokemon-Duell für ZWEI SPIELENDE! \n\n");

        //Namensauswahl
        System.out.println("Wie heißt Spieler 1?");
        System.out.print("Eingabe: ");
        p1Name = sc.nextLine();
        System.out.println("Wie heißt Spieler 2?");
        System.out.print("Eingabe: ");
        p2Name = sc.nextLine();

        //Spieler vorbereiten
        Trainer p1 = new Trainer(p1Name);
        p1.team = new Pokemon[teamSize];
        Trainer p2 = new Trainer(p2Name);
        p2.team = new Pokemon[teamSize];

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

        //Auswürfeln, welcher Spieler zuerst ein Pokemon wählt, dann abwechselnd
        //region                                                                        //(evtl. noch Try-catch-Blöcke bei Pokemon-Auswahlen ergänzen)
        if (teamSize == 1)
            System.out.print("\nJeder von euch erhält ein Pokemon.\n");
        else
            System.out.printf("\nJeder von euch erhält %d Pokemon. \n", teamSize);
        int weiterePoke = teamSize;
        System.out.println("Wir werfen eine Münze, wer sich als Erstes ein Pokemon aussucht.");
        System.out.printf("Bei Kopf wählt %s zuerst, bei Zahl %s. \n\n", p1.name, p2.name);
        wahl = rnd.nextInt(2);
        if (wahl == 0) {                            //Ob dies kürzer geht? If und else enthalten identischen Code, nur Reihenfolge mit p1 und p2 vertauscht.
            //For Schleife mit Spieler 1 zuerst
            System.out.printf("… Kopf! %s wählt zuerst:\n", p1.name);
            for (int i = 0; i < teamSize; i++){                         // Schleife wiederholt Pokemon-Auswahl bis zur gewünschten Teamgröße
                for (int j = 0; j < freiePoke.size(); j++)              // Spieler 1 bekommt Liste
                    System.out.printf("[%d] %s \n", (j + 1), freiePoke.get(j).name);
                System.out.print("Eingabe: ");
                wahl = sc.nextInt();
                p1.team[i] = freiePoke.get(wahl - 1);                   // Team-Array wird mit aus Liste ausgewähltem Pokemon befüllt
                System.out.printf("%s wurde ins Team von %s aufgenommen.\n\n", freiePoke.get(wahl - 1).name, p1.name);
                freiePoke.remove(wahl - 1);                       // gewähltes Pokemon wird aus Liste der verfügbaren Pokemon gestrichen
                System.out.printf("Nun ist %s dran: \n", p2.name);
                for (int j = 0; j < freiePoke.size(); j++)              // Spieler 2 bekommt aktualisierte Liste
                    System.out.printf("[%d] %s \n", (j + 1), freiePoke.get(j).name);
                System.out.print("Eingabe: ");
                wahl = sc.nextInt();
                p2.team[i] = freiePoke.get(wahl - 1);                   // Team-Array wird mit aus Liste ausgewähltem Pokemon befüllt
                System.out.printf("%s wurde ins Team von %s aufgenommen.\n\n", freiePoke.get(wahl - 1).name, p2.name);
                freiePoke.remove(wahl - 1);                       // gewähltes Pokemon wird aus Liste der verfügbaren Pokemon gestrichen
                weiterePoke--;
                if (weiterePoke > 0)
                    System.out.printf("Wählt jeweils noch %d Pokemon, %s zuerst:\n", weiterePoke, p1.name);
                else {
                    System.out.println("Eure Teams sind vollständig!");
                }
            }
        } else {
            //For Schleife mit Spieler 2 zuerst
            System.out.printf("… Zahl! %s wählt zuerst:\n", p2.name);
            for (int i = 0; i < teamSize; i++){
                for (int j = 0; j < freiePoke.size(); j++)
                    System.out.printf("[%d] %s \n", (j + 1), freiePoke.get(j).name);
                System.out.print("Eingabe: ");
                wahl = sc.nextInt();
                p2.team[i] = freiePoke.get(wahl - 1);
                System.out.printf("%s wurde ins Team von %s aufgenommen.\n\n", freiePoke.get(wahl - 1).name, p2.name);
                freiePoke.remove(wahl - 1);
                System.out.printf("Nun ist %s dran: \n", p1.name);
                for (int j = 0; j < freiePoke.size(); j++)
                    System.out.printf("[%d] %s \n", (j + 1), freiePoke.get(j).name);
                System.out.print("Eingabe: ");
                wahl = sc.nextInt();
                p1.team[i] = freiePoke.get(wahl - 1);
                System.out.printf("%s wurde ins Team von %s aufgenommen.\n\n", freiePoke.get(wahl - 1).name, p1.name);
                freiePoke.remove(wahl - 1);
                weiterePoke--;
                if (weiterePoke > 0)
                    System.out.printf("Wählt jeweils noch %d Pokemon, %s zuerst:\n", weiterePoke, p2.name);
                else {
                    System.out.println("Eure Teams sind vollständig!");
                }
            }
        }
        //endregion

        //Einleitungstext
        System.out.printf("\n%s, %s, bringen wir euch in die Arena! \n\n", p1.name, p2.name);

        //Auswürfeln, welcher Spieler zuerst Pokemon ruft
        //region
        System.out.println("Wir werfen erneut eine Münze, wer zuerst ein Pokemon ruft.");
        System.out.printf("Bei Kopf wählt %s zuerst, bei Zahl %s. \n", p1.name, p2.name);
        wahl = rnd.nextInt(2);
        if (wahl == 0) {                                        //Ob das hier kürzer geht? If und else enthalten identischen Code, nur Reihenfolge mit p1 und p2 vertauscht.
            //Spieler 1 ruft erstes Pokemon
            System.out.printf("… Kopf! %s ruft das erste Pokemon.\n\n", p1.name);
            System.out.printf("%s, welches Pokemon willst du einsetzen? \n", p1.name);
            for (int l = 0; l < p1.team.length; l++) {
                System.out.printf("[%d] %s ", l + 1, p1.team[l].name);
                if (p1.team[l].ko)
                    System.out.println("\t>> K.O.! <<");
                else
                    System.out.printf("\t (%d/%d HP) \n", p1.team[l].hp, p1.team[l].hpMax);
            }
            do {
                System.out.print("Eingabe: ");
                wahl = sc.nextInt();
                p1.summon(wahl - 1);
                if (!p1.team[wahl - 1].ko)
                    System.out.printf("Du schickst %s in den Kampf – %s \n\n", p1.kaempfendesPoke.name, p1.kaempfendesPoke.meinFlavor);
                else
                    System.out.println(p1.team[wahl - 1].name + " ist kampfunfähig! Wähle ein anderes Pokemon. \n");
            } while (p1.team[wahl - 1].ko);

            //… dann Spieler 2
            System.out.printf("%s, welches Pokemon willst du einsetzen?\n", p2.name);
            for (int l = 0; l < p2.team.length; l++) {
                System.out.printf("[%d] %s ", l + 1, p2.team[l].name);
                if (p2.team[l].ko)
                    System.out.println("\t>> K.O.! <<");
                else
                    System.out.printf("\t (%d/%d HP) \n", p2.team[l].hp, p2.team[l].hpMax);
            }
            do {
                System.out.print("Eingabe: ");
                wahl = sc.nextInt();
                p2.summon(wahl - 1);
                if (!p2.team[wahl - 1].ko)
                    System.out.printf("Du schickst %s in den Kampf – %s \n\n", p2.kaempfendesPoke.name, p2.kaempfendesPoke.meinFlavor);
                else
                    System.out.println(p2.team[wahl - 1].name + " ist kampfunfähig! Wähle ein anderes Pokemon. \n");
            } while (p2.team[wahl - 1].ko);

        } else {
            //Spieler 2 ruft erstes Pokemon
            System.out.printf("… Zahl! %s ruft das erste Pokemon.\n\n", p2.name);
            System.out.printf("%s, welches Pokemon willst du einsetzen? \n", p2.name);
            for (int l = 0; l < p2.team.length; l++) {
                System.out.printf("[%d] %s ", l + 1, p2.team[l].name);
                if (p2.team[l].ko)
                    System.out.println("\t>> K.O.! <<");
                else
                    System.out.printf("\t (%d/%d HP) \n", p2.team[l].hp, p2.team[l].hpMax);
            }
            do {
                System.out.print("Eingabe: ");
                wahl = sc.nextInt();
                p2.summon(wahl - 1);
                if (!p2.team[wahl - 1].ko)
                    System.out.printf("Du schickst %s in den Kampf – %s \n\n", p2.kaempfendesPoke.name, p2.kaempfendesPoke.meinFlavor);
                else
                    System.out.println(p2.team[wahl - 1].name + " ist kampfunfähig! Wähle ein anderes Pokemon. \n");
            } while (p2.team[wahl - 1].ko);

            //… dann Spieler 1
            System.out.printf("%s, welches Pokemon willst du einsetzen? \n", p1.name);
            for (int l = 0; l < p1.team.length; l++) {
                System.out.printf("[%d] %s ", l + 1, p1.team[l].name);
                if (p1.team[l].ko)
                    System.out.println("\t>> K.O.! <<");
                else
                    System.out.printf("\t (%d/%d HP) \n", p1.team[l].hp, p1.team[l].hpMax);
            }
            do {
                System.out.print("Eingabe: ");
                wahl = sc.nextInt();
                p1.summon(wahl - 1);
                if (!p1.team[wahl - 1].ko)
                    System.out.printf("Du schickst %s in den Kampf – %s \n\n", p1.kaempfendesPoke.name, p1.kaempfendesPoke.meinFlavor);
                else
                    System.out.println(p1.team[wahl - 1].name + " ist kampfunfähig! Wähle ein anderes Pokemon. \n");
            } while (p1.team[wahl - 1].ko);
        }
        //endregion

        //Äußere Do-while-Schleife für Kampf Spieler vs. Spieler inkl. aller Pokemon
        do {
            //Pokemon mit höherem Dex-Wert fängt an.
            if (p1.kaempfendesPoke.dex >= p2.kaempfendesPoke.dex) {
                p1Zug = true;
                System.out.printf("%s ist schneller als %s und fängt an.\n\n", p1.kaempfendesPoke.name, p2.kaempfendesPoke.name);
            } else {
                p1Zug = false;
                System.out.printf("%s ist schneller als %s und fängt an.\n\n", p2.kaempfendesPoke.name, p1.kaempfendesPoke.name);
            }

            //Innere Do-while-Schleife für den Kampf zwischen den zwei aktuell aktiven Pokemon
            do {
                while (p1Zug) {                       //Spieler 1 ist am Zug, wenn boolean wahr ist.
                    System.out.printf("%s ist dran! (%d/%d HP) \n", p1.kaempfendesPoke.name, p1.kaempfendesPoke.hp, p1.kaempfendesPoke.hpMax);
                    System.out.printf("[1] Attacke \n[2] %s (%d übrig) \nEingabe: ", p1.kaempfendesPoke.meinSpecial.name, p1.kaempfendesPoke.specialCharge);
                    wahl = sc.nextInt();
                    switch (wahl) {
                        case 1:
                            p2.kaempfendesPoke.applyDmg(p1.kaempfendesPoke.attack());          //Normaler Angriff: attack() erzeugt Rückgabewert, der an Setter applyDmg() übergeben wird
                            p1Zug = false;
                            break;
                        case 2:
                            dmg = p1.kaempfendesPoke.specialAttack(p1.kaempfendesPoke.meinSpecial);   //Spezialangriff wird aufgerufen und die Parameter für genau dieses Pokemon übergeben.
                            if (p1.kaempfendesPoke.meinSpecial.starkGegen.equals(p2.kaempfendesPoke.typ)) {
                                dmg = (int) (dmg * 1.5f);                               //Spezialangriffe sind gegen bestimmte Pokemon stärker oder schwächer (Wasser gegen Feuer usw.)
                                System.out.printf("Der Angriff ist besonders effektiv gegen %s! \n", p2.kaempfendesPoke.name);
                            } else if (p1.kaempfendesPoke.meinSpecial.schwachGegen.equals(p2.kaempfendesPoke.typ)) {
                                dmg = (int) (dmg * 0.5f);
                                System.out.printf("Der Angriff zeigt wenig Wirkung gegen %s. \n", p2.kaempfendesPoke.name);
                            }
                            p2.kaempfendesPoke.applyDmg(dmg);              //Schaden wird errechnet und in dmg-Variablen dem applyDmg-Setter übergeben
                            System.out.printf("%s verursacht %d Schaden. \n\n", p1.kaempfendesPoke.name, dmg);
                            p1Zug = false;
                            break;
                        default:
                            System.out.println("Ungültige Eingabe");
                            break;
                    }
                }
                while (!p1Zug && p2.kaempfendesPoke.hp > 0) {                          //Spieler 2 ist am ZUg, wenn boolean falsch ist und sein Pokemon noch steht
                    System.out.printf("%s ist dran! (%d/%d HP) \n", p2.kaempfendesPoke.name, p2.kaempfendesPoke.hp, p2.kaempfendesPoke.hpMax);
                    System.out.printf("[1] Attacke \n[2] %s (%d übrig) \nEingabe: ", p2.kaempfendesPoke.meinSpecial.name, p2.kaempfendesPoke.specialCharge);
                    wahl = sc.nextInt();
                    switch (wahl) {
                        case 1:
                            p1.kaempfendesPoke.applyDmg(p2.kaempfendesPoke.attack());          //Normaler Angriff: attack() erzeugt Rückgabewert, der an Setter applyDmg() übergeben wird
                            p1Zug = true;
                            break;
                        case 2:
                            dmg = p2.kaempfendesPoke.specialAttack(p2.kaempfendesPoke.meinSpecial);   //Spezialangriff wird aufgerufen und die Parameter für genau dieses Pokemon übergeben.
                            if (p2.kaempfendesPoke.meinSpecial.starkGegen.equals(p1.kaempfendesPoke.typ)) {
                                dmg = (int) (dmg * 1.5f);                               //Spezialangriffe sind gegen bestimmte Pokemon stärker oder schwächer (Wasser gegen Feuer usw.)
                                System.out.printf("Der Angriff ist besonders effektiv gegen %s! \n", p1.kaempfendesPoke.name);
                            } else if (p2.kaempfendesPoke.meinSpecial.schwachGegen.equals(p1.kaempfendesPoke.typ)) {
                                dmg = (int) (dmg * 0.5f);
                                System.out.printf("Der Angriff zeigt wenig Wirkung gegen %s. \n", p1.kaempfendesPoke.name);
                            }
                            p1.kaempfendesPoke.applyDmg(dmg);              //Schaden wird errechnet und in dmg-Variablen dem applyDmg-Setter übergeben
                            System.out.printf("%s verursacht %d Schaden. \n\n", p2.kaempfendesPoke.name, dmg);
                            p1Zug = true;
                            break;
                        default:
                            System.out.println("Ungültige Eingabe");
                            break;
                    }
                }
            } while (p1.kaempfendesPoke.hp > 0 && p2.kaempfendesPoke.hp > 0);          //Innere Do-while-Schleife wiederholt sich, solange beide aktive Pokemon noch stehen.

            //Ein Pokemon wurde besiegt
            //region
            if (p1.kaempfendesPoke.hp <= 0) {
                System.out.printf("%s wurde besiegt! \n", p1.kaempfendesPoke.name);
                p1.kaempfendesPoke.ko = true;
                p1.AnzPokeBesiegt = 0;                                                     //erst auf 0 setzen, dann durchzählen:
                for (int p = 0; p < p1.team.length; p++)                                   //Wie viele Pokemon im Team sind K.O.? …
                    if (p1.team[p].ko)
                        p1.AnzPokeBesiegt++;
                if (p1.AnzPokeBesiegt == p1.team.length) {                                 //… wenn alle, dann Spieler besiegt.
                    System.out.printf("%s hat keine Pokemon mehr übrig! \n\n", p1.name);
                    p2Win();
                    System.exit(0);
                } else {
                    System.out.printf("%s, welches Pokemon willst du einsetzen?\n", p1.name);   //Spieler wählt neues Pokemon, Code von oben wiederverwendet
                    for (int l = 0; l < p1.team.length; l++) {
                        System.out.printf("[%d] %s ", l + 1, p1.team[l].name);
                        if (p1.team[l].ko)
                            System.out.println("\t>> K.O.! <<");
                        else
                            System.out.printf("\t (%d/%d HP) \n", p1.team[l].hp, p1.team[l].hpMax);
                    }
                    do {
                        System.out.print("Eingabe: ");
                        wahl = sc.nextInt();
                        p1.summon(wahl - 1);
                        if (!p1.team[wahl - 1].ko)
                            System.out.printf("\n%s schickt %s in den Kampf – %s \n", p1.name, p1.kaempfendesPoke.name, p1.kaempfendesPoke.meinFlavor);
                        else
                            System.out.println(p1.team[wahl - 1].name + " ist kampfunfähig! Wähle ein anderes Pokemon. \n");
                    } while (p1.team[wahl - 1].ko);
                }
            }
            if (p2.kaempfendesPoke.hp <= 0) {
                System.out.printf("%s wurde besiegt! \n", p2.kaempfendesPoke.name);
                p2.kaempfendesPoke.ko = true;
                p2.AnzPokeBesiegt = 0;
                for (int p = 0; p < p2.team.length; p++)
                    if (p2.team[p].ko)
                        p2.AnzPokeBesiegt++;
                if (p2.AnzPokeBesiegt == p2.team.length) {
                    System.out.printf("%s hat keine Pokemon mehr übrig! \n\n", p2.name);
                    p1Win();
                    System.exit(0);
                } else {
                    System.out.printf("%s, welches Pokemon willst du einsetzen?\n", p2.name);
                    for (int l = 0; l < p2.team.length; l++) {
                        System.out.printf("[%d] %s ", l + 1, p2.team[l].name);
                        if (p2.team[l].ko)
                            System.out.println("\t>> K.O.! <<");
                        else
                            System.out.printf("\t (%d/%d HP) \n", p2.team[l].hp, p2.team[l].hpMax);
                    }
                    do {
                        System.out.print("Eingabe: ");
                        wahl = sc.nextInt();
                        p2.summon(wahl - 1);
                        if (!p2.team[wahl - 1].ko)
                            System.out.printf("\n%s schickt %s in den Kampf – %s \n", p2.name, p2.kaempfendesPoke.name, p2.kaempfendesPoke.meinFlavor);
                        else
                            System.out.println(p2.team[wahl - 1].name + " ist kampfunfähig! Wähle ein anderes Pokemon. \n");
                    } while (p2.team[wahl - 1].ko);
                }
            }
            //endregion
        } while (p1.AnzPokeBesiegt < p1.team.length && p2.AnzPokeBesiegt < p2.team.length); //Äußere Do-while-Schleife wiederholt sich, solange beide Trainer noch Pokemon haben.
    }
}