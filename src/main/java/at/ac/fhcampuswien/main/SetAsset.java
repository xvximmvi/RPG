package at.ac.fhcampuswien.main;

import at.ac.fhcampuswien.Object.*;

public class SetAsset {
    MyPanel panel;

    public SetAsset(MyPanel panel){
        this.panel = panel;
    }

    public void setObject(){    //instantiate default objects
        //Play around with variables till it fits
        panel.object[0] = new BR_Bed();  //ObjectBed is subclass of Object Class -> instantiate as one of this array
        panel.object[0].MapX = 13 * panel.tileSize;
        panel.object[0].MapY = 5 * panel.tileSize;

        panel.object[1] = new OBJECT_Window();
        panel.object[1].MapX = 7 * panel.tileSize+20;
        panel.object[1].MapY = 2 * panel.tileSize-20;

        panel.object[2] = new BR_Carpet();
        panel.object[2].MapX = panel.tileSize;
        panel.object[2].MapY = 10 * panel.tileSize+20;

        panel.object[3] = new BR_Ball();
        panel.object[3].MapX = 2 * panel.tileSize+10;
        panel.object[3].MapY = 12 * panel.tileSize;

        panel.object[4] = new BR_ToyHorse();
        panel.object[4].MapX = 4 * panel.tileSize;
        panel.object[4].MapY = 10 * panel.tileSize+20;

        panel.object[5] = new BR_Bookshelf();
        panel.object[5].MapX = panel.tileSize-15;
        panel.object[5].MapY = 4 * panel.tileSize+20;

        panel.object[6] = new BR_Nightstand();
        panel.object[6].MapX = 10 * panel.tileSize;
        panel.object[6].MapY = 5 * panel.tileSize;

        panel.object[7] = new BR_Light_OUT();
        panel.object[7].MapX = 11 * panel.tileSize-2;
        panel.object[7].MapY = 4 * panel.tileSize-20;

        panel.object[8] = new OBJECT_SideOutline();
        panel.object[8].MapX = 0;
        panel.object[8].MapY = 67;

        panel.object[9] = new OBJECT_SideOutline();
        panel.object[9].MapX = 16 * panel.tileSize -4;
        panel.object[9].MapY = 67;

        panel.object[10] = new OBJECT_BottomOutline();
        panel.object[10].MapX = 10 * panel.tileSize - 4;
        panel.object[10].MapY = 16 * panel.tileSize - 4;

        panel.object[11] = new OBJECT_BottomDoor();
        panel.object[11].MapX = 6 * panel.tileSize + 9;
        panel.object[11].MapY = 16 * panel.tileSize - 4;

    }
}
