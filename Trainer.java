package OOP.Aufgaben.A4_Pokemon;

public class Trainer {
    String name;
    Pokemon[] team;               //Array der Pokemon in Besitz
    Pokemon kaempfendesPoke;      //Welches Pokemon ist gerade im Kampf
    int AnzPokeBesiegt = 0;       //++ für jedes kampfunfähige eigene Pokemon; wenn == team.length, dann Game Over.

    //Constructor
    public Trainer(String name) {
        this.name = name;
    }

    //Methode: Pokemon rufen
    public void summon(int index){
        kaempfendesPoke = team[index];
    }
}