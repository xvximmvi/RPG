package at.ac.fhcampuswien.main;

import at.ac.fhcampuswien.Object.*;

// CLASS CONTENT
/*
    ASSET CONSTRUCTOR
    SET OBJECT
 */

public class SetAsset {
    GamePanel panel;

    //ASSET CONSTRUCTOR
    public SetAsset(GamePanel panel){
        this.panel = panel;
    }

    // SET OBJECT
    public void setObject() {
        //instantiate default objects
        //Play around with variables till it fits
        panel.object[0] = new BR_Bed(panel);  //ObjectBed is subclass of Object Class -> instantiate as one of this array
        panel.object[0].MapX = 13 * panel.tileSize;
        panel.object[0].MapY = 5 * panel.tileSize;

        panel.object[1] = new OBJECT_Window(panel);
        panel.object[1].MapX = 7 * panel.tileSize+20;
        panel.object[1].MapY = 2 * panel.tileSize-20;

        panel.object[2] = new BR_Carpet(panel);
        panel.object[2].MapX = panel.tileSize;
        panel.object[2].MapY = 10 * panel.tileSize+20;

        panel.object[3] = new BR_Ball(panel);
        panel.object[3].MapX = 2 * panel.tileSize+10;
        panel.object[3].MapY = 12 * panel.tileSize;

        panel.object[4] = new BR_ToyHorse(panel);
        panel.object[4].MapX = 4 * panel.tileSize;
        panel.object[4].MapY = 10 * panel.tileSize+20;

        panel.object[5] = new BR_Bookshelf(panel);
        panel.object[5].MapX = panel.tileSize-15;
        panel.object[5].MapY = 4 * panel.tileSize+10;

        panel.object[6] = new BR_Nightstand(panel);
        panel.object[6].MapX = 10 * panel.tileSize;
        panel.object[6].MapY = 5 * panel.tileSize;

        panel.object[7] = new BR_Light_OUT(panel);
        panel.object[7].MapX = 11 * panel.tileSize-2;
        panel.object[7].MapY = 4 * panel.tileSize-20;

        panel.object[8] = new OBJECT_SideOutline(panel);
        panel.object[8].MapX = 10;
        panel.object[8].MapY = 6 * panel.tileSize;

        panel.object[9] = new OBJECT_SideOutline(panel);
        panel.object[9].MapX = 16 * panel.tileSize - 10;
        panel.object[9].MapY = 6 * panel.tileSize;

        panel.object[10] = new OBJECT_BottomOutline(panel);
        panel.object[10].MapX = 10 * panel.tileSize - 4;
        panel.object[10].MapY = 16 * panel.tileSize - 4;

        panel.object[11] = new OBJECT_BottomOutline(panel);
        panel.object[11].MapX = 0;
        panel.object[11].MapY = 16 * panel.tileSize - 4;

        panel.object[12] = new OBJECT_BottomDoor(panel);
        panel.object[12].MapX = 6 * panel.tileSize + 9;
        panel.object[12].MapY = 16 * panel.tileSize - 3;
    }
}
