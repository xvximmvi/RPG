package at.ac.fhcampuswien.main;

import at.ac.fhcampuswien.object.*;

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
        panel.object[0][12].MapY = 16 * panel.tileSize - 6;

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
        panel.object[1][1].MapY = 0;

        panel.object[1][2] = new OBJECT_BottomOutline(panel);
        panel.object[1][2].MapX = 10 * panel.tileSize - 4;
        panel.object[1][2].MapY = 16 * panel.tileSize - 4;

        panel.object[1][3] = new OBJECT_BottomOutline(panel);
        panel.object[1][3].MapX = 0;
        panel.object[1][3].MapY = 16 * panel.tileSize - 4;

        panel.object[1][4] = new OBJECT_BottomDoor(panel);
        panel.object[1][4].MapX = 6 * panel.tileSize + 9;
        panel.object[1][4].MapY = 16 * panel.tileSize - 6;

        panel.object[1][5] = new BEDROOM_DOOR(panel);
        panel.object[1][5].MapX = 11 * panel.tileSize;
        panel.object[1][5].MapY = 3 * panel.tileSize + 3;

        panel.object[1][6] = new BATHROOM_DOOR(panel);
        panel.object[1][6].MapX = 4 * panel.tileSize;
        panel.object[1][6].MapY = 3 * panel.tileSize + 3;

        panel.object[1][7] = new C_Clock(panel);
        panel.object[1][7].MapX = 14* panel.tileSize-26;
        panel.object[1][7].MapY = 3* panel.tileSize+20;

        panel.object[1][8] = new C_Fire(panel);
        panel.object[1][8].MapX = 7* panel.tileSize;
        panel.object[1][8].MapY = 15;

        panel.object[1][9] = new C_Clothes(panel);
        panel.object[1][9].MapX = 14* panel.tileSize-26;
        panel.object[1][9].MapY = 12* panel.tileSize+10;

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

        panel.object[1][16] = new C_SideDoor1(panel);
        panel.object[1][16].MapX = 16* panel.tileSize - (3*4);
        panel.object[1][16].MapY = 13* panel.tileSize-(5*3);

        panel.object[1][17] = new C_SideDoor2(panel);
        panel.object[1][17].MapX = 16* panel.tileSize - (3*4);
        panel.object[1][17].MapY = 9* panel.tileSize;

        panel.object[1][18] = new OBJECT_SideDoor1(panel);
        panel.object[1][18].MapX = 16* panel.tileSize - 6;
        panel.object[1][18].MapY = 9* panel.tileSize+5*3;


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
        panel.object[2][4].MapY = 16 * panel.tileSize - 6;

        // Objects
        panel.object[2][5] = new B_MiniRug(panel);
        panel.object[2][5].MapX =11* panel.tileSize+30;
        panel.object[2][5].MapY = 8* panel.tileSize;

        panel.object[2][6] = new B_Duck(panel);
        panel.object[2][6].MapX = 9* panel.tileSize+30;
        panel.object[2][6].MapY = 6* panel.tileSize;

        panel.object[2][7] = new B_Bathtub_Interact(panel);
        panel.object[2][7].MapX = 11* panel.tileSize;
        panel.object[2][7].MapY = 6* panel.tileSize-24;

        panel.object[2][8] = new B_Mirror(panel);
        panel.object[2][8].MapX =3* panel.tileSize-5;
        panel.object[2][8].MapY = 2* panel.tileSize-10;

        panel.object[2][9] = new B_Rug(panel);
        panel.object[2][9].MapX =3* panel.tileSize-30;
        panel.object[2][9].MapY = 7* panel.tileSize;

        panel.object[2][10] = new B_Sink(panel);
        panel.object[2][10].MapX =3* panel.tileSize-3;
        panel.object[2][10].MapY = 3* panel.tileSize+20;

        panel.object[2][11] = new B_Toiletbrush(panel);
        panel.object[2][11].MapX =13* panel.tileSize;
        panel.object[2][11].MapY = 12* panel.tileSize;

        panel.object[2][12] = new B_Toilet(panel);
        panel.object[2][12].MapX =13* panel.tileSize;
        panel.object[2][12].MapY = 12* panel.tileSize+20;

        panel.object[2][13] = new B_Towel(panel);
        panel.object[2][13].MapX =5* panel.tileSize+15;
        panel.object[2][13].MapY = 3* panel.tileSize+20;

        panel.object[2][17] = new B_BigBox(panel);
        panel.object[2][17].MapX =panel.tileSize-5;
        panel.object[2][17].MapY = 13* panel.tileSize;

        panel.object[2][18] = new B_SmallBox(panel);
        panel.object[2][18].MapX =3*panel.tileSize-20;
        panel.object[2][18].MapY = 14* panel.tileSize;

        panel.object[2][19] = new OBJECT_Window(panel);
        panel.object[2][19].MapX =10*panel.tileSize;
        panel.object[2][19].MapY = panel.tileSize+15;
    }

    public void setObjectKitchen(){
        // WALL & OUTLINES
        panel.object[3][0] = new K_Wall(panel);
        panel.object[3][0].MapX = 12;
        panel.object[3][0].MapY = 12;

        panel.object[3][1] = new OBJECT_SideOutline(panel);
        panel.object[3][1].MapX = 16 * panel.tileSize - 10;
        panel.object[3][1].MapY = 6 * panel.tileSize;

        panel.object[3][2] = new K_SideDoor1(panel);
        panel.object[3][2].MapX = 0;
        panel.object[3][2].MapY = 13* panel.tileSize-(5*3);

        panel.object[3][3] = new K_SideDoor2(panel);
        panel.object[3][3].MapX = 0;
        panel.object[3][3].MapY = 9* panel.tileSize;

        panel.object[3][4] = new OBJECT_SideDoor2(panel);
        panel.object[3][4].MapX = 0;
        panel.object[3][4].MapY = 9* panel.tileSize+5*3;

        panel.object[3][5] = new OBJECT_SideOutline(panel);
        panel.object[3][5].MapX = 10;
        panel.object[3][5].MapY = 0;

        panel.object[3][6] = new OBJECT_SideOutline(panel);
        panel.object[3][6].MapX = 10;
        panel.object[3][6].MapY = 13* panel.tileSize;

        panel.object[3][7] = new OBJECT_BottomOutline_Long(panel);
        panel.object[3][7].MapX = 0;
        panel.object[3][7].MapY = 16 * panel.tileSize - 4;

        // OBJECTS
        panel.object[3][8] = new K_Fridge(panel);
        panel.object[3][8].MapX = panel.tileSize;
        panel.object[3][8].MapY = 3 * panel.tileSize;

        panel.object[3][9] = new K_Oven(panel);
        panel.object[3][9].MapX = 5*panel.tileSize;
        panel.object[3][9].MapY = 4 * panel.tileSize;

        panel.object[3][10] = new K_Sink(panel);
        panel.object[3][10].MapX = 8*panel.tileSize;
        panel.object[3][10].MapY = 4 * panel.tileSize-4;

        panel.object[3][11] = new K_Shelf(panel);
        panel.object[3][11].MapX = 8 * panel.tileSize+32*4;
        panel.object[3][11].MapY = 4 * panel.tileSize;

        panel.object[3][12] = new K_Clock(panel);
        panel.object[3][12].MapX = 7 * panel.tileSize;
        panel.object[3][12].MapY = 2 * panel.tileSize-20;

        panel.object[3][13] = new K_Chair_Front(panel);
        panel.object[3][13].MapX = 6 * panel.tileSize;
        panel.object[3][13].MapY = 8 * panel.tileSize+20;

        panel.object[3][14] = new K_Chair_Front(panel);
        panel.object[3][14].MapX = 9 * panel.tileSize-15;
        panel.object[3][14].MapY = 8 * panel.tileSize+20;

        panel.object[3][15] = new K_Chair_Right(panel);
        panel.object[3][15].MapX = 11 * panel.tileSize-4;
        panel.object[3][15].MapY = 10 * panel.tileSize;

        panel.object[3][16] = new K_Table(panel);
        panel.object[3][16].MapX = 5 * panel.tileSize;
        panel.object[3][16].MapY = 10 * panel.tileSize;

        panel.object[3][17] = new K_Chair_Back(panel);
        panel.object[3][17].MapX = 6 * panel.tileSize;
        panel.object[3][17].MapY = 11 * panel.tileSize+20;

        panel.object[3][18] = new K_Chair_Back(panel);
        panel.object[3][18].MapX = 9 * panel.tileSize-15;
        panel.object[3][18].MapY = 11 * panel.tileSize+20;

        panel.object[3][19] = new K_FlowerPot(panel);
        panel.object[3][19].MapX = 8 * panel.tileSize;
        panel.object[3][19].MapY = 10 * panel.tileSize;

        panel.object[3][20] = new K_Flower(panel);
        panel.object[3][20].MapX = 14 * panel.tileSize;
        panel.object[3][20].MapY = 13 * panel.tileSize;

        panel.object[3][21] = new K_HangingUtilitys(panel);
        panel.object[3][21].MapX = 11 * panel.tileSize+15;
        panel.object[3][21].MapY = 2 * panel.tileSize;

        panel.object[3][22] = new K_EmptyPot(panel);
        panel.object[3][22].MapX = 12 * panel.tileSize;
        panel.object[3][22].MapY = 4 * panel.tileSize;
    }
}
