package game;

import java.awt.*;

public enum Loot{
    Platinum(0, "Platinum", 5000),
    Gold(1, "Gold", 2500),
    Uranium(2, "Uranium", 1000),
    Silver(3, "Silver", 500),
    Cobalt(4, "Cobalt", 250),
    Copper(5, "Copper", 100),
    Iron(6, "Iron", 50);

    String name;
    int value, id;

    Loot(int id, String name, int value){
        this.id = id;
        this.name = name;
        this.value = value;
    }

    //Colours for the different loot crates
    public Color getItemColor(){
        Color itemColour = new Color(255,255,255);
        switch (id){
            case 0:
                itemColour =  new Color(213, 231, 246);
                break;
            case 1:
                itemColour =  new Color(229, 234, 0);
                break;
            case 2:
                itemColour =  new Color(71, 231, 0);
                break;
            case 3:
                itemColour =  new Color(218, 229, 231);
                break;
            case 4:
                itemColour =  new Color(45, 135, 233);
                break;
            case 5:
                itemColour =  new Color(200, 125, 0);
                break;
            case 6:
                itemColour =  new Color(180, 99, 69);
                break;
        }
        return  itemColour;
    }
}
