package at.ac.fhcampuswien.main;

import at.ac.fhcampuswien.Object.*;

// CLASS CONTENT
/*
    ASSET CONSTRUCTOR
    SET OBJECT for every Room
 */

public class SetAsset {
    GamePanel panel;

    //ASSET CONSTRUCTOR
    public SetAsset(GamePanel panel){
        this.panel = panel;

    }

    // SET OBJECT
    public void setObjectBedroom() {
        //instantiate default objects
        //Play around with variables till it fits

        //first [] for current Map/Room
        //second [] to know at which object we are
        panel.object[0][0] = new BR_Bed(panel);  //ObjectBed is subclass of Object Class -> instantiate as one of this array
        panel.object[0][0].MapX = 13 * panel.tileSize;
        panel.object[0][0].MapY = 5 * panel.tileSize;

        panel.object[0][1] = new OBJECT_Window(panel);
        panel.object[0][1].MapX = 7 * panel.tileSize + 20;
        panel.object[0][1].MapY = 2 * panel.tileSize - 20;

        panel.object[0][2] = new BR_Carpet(panel);
        panel.object[0][2].MapX = panel.tileSize;
        panel.object[0][2].MapY = 10 * panel.tileSize + 20;

        panel.object[0][3] = new BR_Ball(panel);
        panel.object[0][3].MapX = 2 * panel.tileSize + 10;
        panel.object[0][3].MapY = 12 * panel.tileSize;

        panel.object[0][4] = new BR_ToyHorse(panel);
        panel.object[0][4].MapX = 4 * panel.tileSize;
        panel.object[0][4].MapY = 10 * panel.tileSize + 20;

        panel.object[0][5] = new BR_Bookshelf(panel);
        panel.object[0][5].MapX = panel.tileSize - 15;
        panel.object[0][5].MapY = 4 * panel.tileSize + 10;

        panel.object[0][6] = new BR_Nightstand(panel);
        panel.object[0][6].MapX = 10 * panel.tileSize;
        panel.object[0][6].MapY = 5 * panel.tileSize;

        panel.object[0][7] = new BR_Light_OUT(panel);
        panel.object[0][7].MapX = 11 * panel.tileSize - 2;
        panel.object[0][7].MapY = 4 * panel.tileSize - 20;

        panel.object[0][8] = new OBJECT_SideOutline(panel);
        panel.object[0][8].MapX = 10;
        panel.object[0][8].MapY = 6 * panel.tileSize;

        panel.object[0][9] = new OBJECT_SideOutline(panel);
        panel.object[0][9].MapX = 16 * panel.tileSize - 10;
        panel.object[0][9].MapY = 6 * panel.tileSize;

        panel.object[0][10] = new OBJECT_BottomOutline(panel);
        panel.object[0][10].MapX = 10 * panel.tileSize - 4;
        panel.object[0][10].MapY = 16 * panel.tileSize - 4;

        panel.object[0][11] = new OBJECT_BottomOutline(panel);
        panel.object[0][11].MapX = 0;
        panel.object[0][11].MapY = 16 * panel.tileSize - 4;

        panel.object[0][12] = new OBJECT_BottomDoor(panel);
        panel.object[0][12].MapX = 6 * panel.tileSize + 9;
        panel.object[0][12].MapY = 16 * panel.tileSize - 3;

        panel.object[0][14] = new OBJECT_SmallPicture2(panel);
        panel.object[0][14].MapX = 13 * panel.tileSize+20;
        panel.object[0][14].MapY = 2 * panel.tileSize;
    }

    public void setObjectCorridor(){
        // Each room gets different Methode for better structure and smaller number for second []
        panel.object[1][0] = new OBJECT_SideOutline(panel);
        panel.object[1][0].MapX = 10;
        panel.object[1][0].MapY = 6 * panel.tileSize;

        panel.object[1][1] = new OBJECT_SideOutline(panel);
        panel.object[1][1].MapX = 16 * panel.tileSize - 10;
        panel.object[1][1].MapY = 6 * panel.tileSize;

        panel.object[1][2] = new OBJECT_BottomOutline(panel);
        panel.object[1][2].MapX = 10 * panel.tileSize - 4;
        panel.object[1][2].MapY = 16 * panel.tileSize - 4;

        panel.object[1][3] = new OBJECT_BottomOutline(panel);
        panel.object[1][3].MapX = 0;
        panel.object[1][3].MapY = 16 * panel.tileSize - 4;

        panel.object[1][4] = new OBJECT_BottomDoor(panel);
        panel.object[1][4].MapX = 6 * panel.tileSize + 9;
        panel.object[1][4].MapY = 16 * panel.tileSize - 3;

        panel.object[1][5] = new BEDROOM_DOOR(panel);
        panel.object[1][5].MapX = 11 * panel.tileSize;
        panel.object[1][5].MapY = 3 * panel.tileSize + 3;

        panel.object[1][6] = new BATHROOM_DOOR(panel);
        panel.object[1][6].MapX = 4 * panel.tileSize;
        panel.object[1][6].MapY = 3 * panel.tileSize + 3;

        panel.object[1][7] = new C_Fire(panel);
        panel.object[1][7].MapX = 7* panel.tileSize;
        panel.object[1][7].MapY = 10;

        panel.object[1][8] = new C_Clock(panel);
        panel.object[1][8].MapX = 14* panel.tileSize-26;
        panel.object[1][8].MapY = 3* panel.tileSize+20;

        panel.object[1][9] = new C_Clothes(panel);
        panel.object[1][9].MapX = 14* panel.tileSize-26;
        panel.object[1][9].MapY = 12* panel.tileSize;

        panel.object[1][10] = new C_Chouch(panel);
        panel.object[1][10].MapX = panel.tileSize-20;
        panel.object[1][10].MapY = 13*panel.tileSize-25;

        panel.object[1][11] = new C_Shelf_Fish(panel);
        panel.object[1][11].MapX = panel.tileSize-25;
        panel.object[1][11].MapY = 4*panel.tileSize;

        panel.object[1][12] = new C_Lamp(panel);
        panel.object[1][12].MapX = 2*panel.tileSize+20;
        panel.object[1][12].MapY = 3*panel.tileSize+30;

        panel.object[1][14] = new C_Picture(panel);
        panel.object[1][14].MapX = panel.tileSize;
        panel.object[1][14].MapY = panel.tileSize+20;

        panel.object[1][15] = new OBJECT_SmallPicture1(panel);
        panel.object[1][15].MapX = 13 * panel.tileSize+15;
        panel.object[1][15].MapY = panel.tileSize+15;

    }

    public void setObjectBathroom(){
        //Outlines and Door are same within every Room
        panel.object[2][0] = new OBJECT_SideOutline(panel);
        panel.object[2][0].MapX = 10;
        panel.object[2][0].MapY = 6 * panel.tileSize;

        panel.object[2][1] = new OBJECT_SideOutline(panel);
        panel.object[2][1].MapX = 16 * panel.tileSize - 10;
        panel.object[2][1].MapY = 6 * panel.tileSize;

        panel.object[2][2] = new OBJECT_BottomOutline(panel);
        panel.object[2][2].MapX = 10 * panel.tileSize - 4;
        panel.object[2][2].MapY = 16 * panel.tileSize - 4;

        panel.object[2][3] = new OBJECT_BottomOutline(panel);
        panel.object[2][3].MapX = 0;
        panel.object[2][3].MapY = 16 * panel.tileSize - 4;

        panel.object[2][4] = new OBJECT_BottomDoor(panel);
        panel.object[2][4].MapX = 6 * panel.tileSize + 9;
        panel.object[2][4].MapY = 16 * panel.tileSize - 3;
    }
}
