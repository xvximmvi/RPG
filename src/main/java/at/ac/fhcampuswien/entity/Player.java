package at.ac.fhcampuswien.entity;

import at.ac.fhcampuswien.object.*;
import at.ac.fhcampuswien.main.Handler;
import at.ac.fhcampuswien.main.GamePanel;
import at.ac.fhcampuswien.main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

// CLASS CONTENT
/*
    PLAYER CONSTRUCTOR
        - Center of Screen
        - Call Methods

    DEFAULT POSITION & DIRECTION
        - Position beginning
        - Spawn-point and spawn-position

    PLAYER IMAGE
        - set direction to each Sprite Image

    PLAYER IMAGE SETUP
        - scale Image for improved rendering performance

    UPDATE PLAYER POSITION
        - Player direction
        - check collision
        - if Collision is false, Player can move
        - change Sprite image (animation)

    OBJECT INTERACTION
        - check index
        - set interaction with objects

    DRAW PLAYER
        - draw current sprite image
        - stop screen an edge of map
 */

public class Player extends Entity{
    GamePanel panel;
    Handler handler;
    BufferedImage bufferedImage;

    public final int ScreenX, ScreenY;  //Coordinate of Screen
    public boolean BedroomKey = false;
    public boolean KitchenKey = false;
    public boolean OutsideKey = false;
    int InteractionCounter=0;           //InteractionCounter: slower interaction with objects
    int ObjectCounter = 0;

    public int FullCompletion = 54;
    Boolean[] complete = new Boolean[FullCompletion+1];

    // OBJECT STATE
    public boolean BR_Light_State = false;

    // PLAYER CONSTRUCTOR
    public Player(GamePanel panel, Handler handler){
        this.panel = panel;
        this.handler = handler;

        ScreenX = panel.ScreenWidth/2;      //Center of screen
        ScreenY = panel.ScreenHeight/2;     //Center of screen

        Area = new Rectangle();     //create area of collision within the player character
        //4 parameter (x, y, width, height) of player character: which part of the character has collision detection
        Area.x = 18;
        Area.y = 36;
        Area.width = 38;    //48 pixels for 1 tile: is to precisely -> difficult to go through
        Area.height = 38;

        //Default collision for Player Reset
        AreaDefaultX = Area.x;
        AreaDefaultY = Area.y;


        setDefaultValues();         //call setDefaultValues()
        playerImage();              //call playerImage()
        setDialogue();
    }

    // DEFAULT POSITION & DIRECTION
    public void setDefaultValues(){
        MapX = panel.tileSize*12-(panel.tileSize/2);    //position vertical Center on Map
        MapY = panel.tileSize*9-(panel.tileSize/2);     //position horizontal Center on Map
        Speed = 4;      //same as in Panel class
        direction = "DOWN";

        BedroomKey = false;
        KitchenKey = false;
        OutsideKey = false;

        for(int c=0; c <= FullCompletion; c++)
            complete[c]=false;


        panel.ui.TutorialOn = true;     //Start game with Tutorial
    }

    // PLAYER IMAGES
    public void playerImage(){
        UP1 = setup("MC_B1");
        UP2 = setup("MC_B2");
        UP3 = setup("MC_B3");

        DOWN1 = setup("MC_F1");
        DOWN2 = setup("MC_F2");
        DOWN3 = setup("MC_F3");

        LEFT1 = setup("MC_L1");
        LEFT2 = setup("MC_L2");
        LEFT3 = setup("MC_L3");

        RIGHT1 = setup("MC_R1");
        RIGHT2 = setup("MC_R2");
        RIGHT3 = setup("MC_R3");
    }

    // PLAYER IMAGE SETUP
    public BufferedImage setup(String imageName){
        //scale Image so that Graphics2D can skip that part and draw faster
        Utility utility = new Utility();
        BufferedImage scaledImage = null;

        try{
            scaledImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/MC/" + imageName + ".png")));
            scaledImage = utility.scaleImage(scaledImage, panel.tileSize+20, panel.tileSize+20);

        } catch(IOException e){
            e.printStackTrace();
        }
        return scaledImage;
    }

    // UPDATE PLAYER POSITION
    public void update() {
        //update current position of player

        if(handler.Reset){
            panel.currentMap = 0;
            setDefaultValues();
            panel.ui.foundKey = false;
            panel.ui.foundTool = false;
            panel.ui.usedTool = false;

            //KITCHEN
            panel.ui.collectEmptyPot = false;
            panel.ui.collectSoupPot = false;
            panel.ui.collectSoupCan = false;
            panel.ui.collectEmptyPlate = false;
            panel.ui.collectSoupPlate = false;
            panel.ui.usedEmptyPot = false;
            panel.ui.usedSoupPot = false;
            panel.ui.usedSoupCan = false;
            panel.ui.usedEmptyPlate = false;
            panel.ui.usedSoupPlate = false;

            // INTERACTIVE OBJECTS
            for(int i=23; i<30; i++) {
                panel.object[3][i] = null;
            }
            panel.object[1][13] = null;
            panel.object[2][14] = null;
            panel.object[2][15] = null;
            panel.object[2][16] = null;

            panel.ui.playTime = panel.ui.DefaultPlayTime;
            handler.Reset =false;
        }

        // PLAYER DIRECTION
        if(handler.UP || handler.DOWN || handler.LEFT || handler.RIGHT){    //move if any key is pressed
            if(handler.UP)      direction = "UP";   //if W (UP) is pressed (=true) -> change direction of position
            if(handler.DOWN)    direction = "DOWN";
            if(handler.LEFT)    direction = "LEFT";
            if(handler.RIGHT)   direction = "RIGHT";

            // CHECK TILE COLLISION
            collisionOn = false;    //Default as false
            //player Class is a subclass of the Entity class
            panel.collisionDetection.DetectTile(this);  //CollisionDetection receives Player class as Entity


            // CHECK OBJECT COLLISION
            int objectIndex = panel.collisionDetection.DetectObject(this, true);
            Interaction(objectIndex);     //interaction with object

            // if Collision is false, Player can move
            if(!collisionOn){
                switch (direction) {
                    case "UP" -> MapY = MapY - Speed;    //if W (going UP) is pressed -> Y-Coordinate changes (-Speed)
                    case "DOWN" -> MapY = MapY + Speed;
                    case "LEFT" -> MapX = MapX - Speed;
                    case "RIGHT" -> MapX = MapX + Speed;
                }
            }
            spriteCounter++;    //continue counting
            if(spriteCounter>12){            //How fast to change (only change Sprite when number 12 has reached)
                if (spriteNum ==2)          spriteNum = 1;
                else if (spriteNum == 1)    spriteNum = 3;
                else if (spriteNum == 3)    spriteNum = 1;
                spriteCounter = 0;          //Reset spriteCounter
            }
        } else spriteNum = 2;               //if He stops moving -> go to basic position (Standing Position 2)
    }

    // OBJECT INTERACTION
    public void Interaction(int index) {

        OBJECT_Key key = new OBJECT_Key(panel); //OBJECT_Key Class
        bufferedImage = key.image;              //image of Key Class

        // Index as confirmation of collision
        if(index != 999) {   //if index isn't 999, then we haven't touched an object
            //panel.object[index] = null;     //if object is touched, then delete it -> "Pick it up"

            String ObjectName = panel.object[panel.currentMap][index].name;   //Which object is touched

                InteractionCounter++;       //Key-press takes to long -> interact more than once at a time
                if (InteractionCounter > 9) {  //count to 9 than do next interaction

                    // INTERACTION WITH BEDROOM OBJECTS
                    if (panel.currentMap == 0) {
                        // OBJECT INTERACTION
                        switch (ObjectName) {   //Identify Object
                            case "Nightstand":  //If Nightstand is "opened" than:
                                if (handler.INTERACT) {            //if no Key in possession:
                                    if(!BedroomKey) {
                                        panel.playSoundEffect(2);               //Play Key Sound
                                        panel.GameState = panel.dialogueState;
                                        panel.ui.currentDialogue = dialogues[2];
                                        panel.ui.foundKey = true;
                                        BedroomKey = true;
                                        if(!complete[0]){       // only add one time to Completion
                                            complete[0] = true; //set true, so it doesn't add again on second use
                                            panel.ui.CompletionOfGame++;    //add to Completion
                                        }
                                    } else {    //if Key is already in Possession
                                        panel.playSoundEffect(5);               //play Notification Sound
                                        //Turn Light on and off
                                        BR_Light_State = !BR_Light_State;   //change Light State
                                        if (BR_Light_State) {             //if turning on -> instantiate new Object (Light_ON)
                                            panel.object[0][13] = new BR_Light_ON(panel);
                                            panel.object[0][13].MapX = 11 * panel.tileSize - 2;
                                            panel.object[0][13].MapY = 4 * panel.tileSize - 23;
                                            if(!complete[1]){
                                                complete[1] = true;
                                                panel.ui.CompletionOfGame++;
                                            }
                                        } else {
                                            panel.object[0][13] = null;    //if turning off-> then delete Object (null)
                                            if (!complete[2]) {
                                                complete[2] = true;
                                                panel.ui.CompletionOfGame++;
                                            }
                                        }
                                    }
                                }
                                break;

                            case "Bed":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[1];
                                    if(!complete[3]){
                                        complete[3] = true;
                                        panel.ui.CompletionOfGame++;
                                    }
                                }
                                break;

                            case "Bookshelf":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[3];
                                    if(!complete[4]){
                                        complete[4] = true;
                                        panel.ui.CompletionOfGame++;
                                    }
                                }
                                break;

                            case "ToyHorse":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[4];
                                    if(!complete[5]){
                                        complete[5] = true;
                                        panel.ui.CompletionOfGame++;
                                    }
                                }
                                break;

                            case "BottomDoor":
                                if(handler.INTERACT) {
                                    if (BedroomKey) {    //if Key in possession ...
                                        panel.playSoundEffect(4);
                                        switchMap(1, 12, 7);
                                        if (!KitchenKey && !OutsideKey)
                                            panel.ui.foundKey = false;
                                        if(!complete[6]){
                                            complete[6] = true;
                                            panel.ui.CompletionOfGame++;
                                        }
                                    } else {  //if no Key in possession
                                        panel.playSoundEffect(3);
                                        panel.GameState = panel.dialogueState;
                                        panel.ui.currentDialogue = dialogues[0];
                                        if(!complete[7]){
                                            complete[7] = true;
                                            panel.ui.CompletionOfGame++;
                                        }
                                    }
                                }
                                break;
                        }
                        InteractionCounter = 0;   //reset InteractionCounter
                    }

                    // INTERACTION WITH CORRIDOR OBJECTS
                    else if(panel.currentMap == 1){
                        switch (ObjectName) {
                            case "BottomDoor":
                                if (OutsideKey && handler.INTERACT) {
                                    panel.playSoundEffect(0);
                                    panel.GameState = panel.GameWonState;
                                    if(!complete[8]){
                                        complete[8] = true;
                                        panel.ui.CompletionOfGame++;
                                    }
                                } else if (!OutsideKey && handler.INTERACT) {  //if no Key in possession
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[5];
                                    if(!complete[9]){
                                        complete[9] = true;
                                        panel.ui.CompletionOfGame++;
                                    }
                                }
                                break;
                            case "Bedroom_Door":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(4);
                                    switchMap(0, 8, 14);
                                    if(!complete[10]){
                                        complete[10] = true;
                                        panel.ui.CompletionOfGame++;
                                    }
                                }
                                break;
                            case "Bathroom_Door":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(4);
                                    switchMap(2, 8, 14);
                                    if(!complete[11]){
                                        complete[11] = true;
                                        panel.ui.CompletionOfGame++;
                                    }
                                }
                                break;
                            case "Fire":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[6];
                                    if(!complete[12]){
                                        complete[12] = true;
                                        panel.ui.CompletionOfGame++;
                                    }
                                }
                                break;
                            case "Lamp":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if(ObjectCounter>8) {           //Ugliest fucking solution to ever exist
                                        panel.playSoundEffect(3);
                                        panel.object[1][13] = new C_Lamp_ON(panel);
                                        panel.object[1][13].MapX = 2 * panel.tileSize + 20;
                                        panel.object[1][13].MapY = 3 * panel.tileSize + 30;
                                        panel.object[1][12] = null;
                                        if(!complete[13]){
                                            complete[13] = true;
                                            panel.ui.CompletionOfGame++;
                                        }
                                        ObjectCounter = 0;
                                    }
                                }
                                break;
                            case "LampOn":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if(ObjectCounter>8) {           //Ugliest fucking solution to ever exist
                                        panel.playSoundEffect(3);
                                        panel.object[1][12] = new C_Lamp(panel);
                                        panel.object[1][12].MapX = 2 * panel.tileSize + 20;
                                        panel.object[1][12].MapY = 3 * panel.tileSize + 30;
                                        panel.object[1][13] = null;
                                        if(!complete[14]){
                                            complete[14] = true;
                                            panel.ui.CompletionOfGame++;
                                        }
                                        ObjectCounter = 0;
                                    }
                                }
                                break;
                            case "Chouch":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[7];
                                    if(!complete[15]){
                                        complete[15] = true;
                                        panel.ui.CompletionOfGame++;
                                    }
                                }
                                break;
                            case "Clothes":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[8];
                                    if(!complete[16]){
                                        complete[16] = true;
                                        panel.ui.CompletionOfGame++;
                                    }
                                }
                                break;
                            case "Clock":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[9];
                                    if(!complete[17]){
                                        complete[17] = true;
                                        panel.ui.CompletionOfGame++;
                                    }
                                }
                                break;
                            case "ShelfFish":
                                if(handler.INTERACT) {
                                    if (!panel.ui.foundTool) {
                                        panel.playSoundEffect(2);
                                        panel.GameState = panel.dialogueState;
                                        panel.ui.currentDialogue = dialogues[10];
                                        panel.ui.foundTool = true;
                                        if (!complete[18]) {
                                            complete[18] = true;
                                            panel.ui.CompletionOfGame++;
                                        }
                                    } else {
                                        panel.playSoundEffect(3);
                                        panel.GameState = panel.dialogueState;
                                        panel.ui.currentDialogue = dialogues[11];
                                        if (!complete[19]) {
                                            complete[19] = true;
                                            panel.ui.CompletionOfGame++;
                                        }
                                    }
                                }
                                break;

                            case "SideDoor1":
                                if(handler.INTERACT) {
                                    if (KitchenKey) {    //if Key in possession ...
                                        panel.playSoundEffect(4);
                                        switchMap(3, 1, 11);
                                        if(!complete[20]){
                                            complete[20] = true;
                                            panel.ui.CompletionOfGame++;
                                        }
                                        if (!OutsideKey)
                                            panel.ui.foundKey = false;
                                    } else {  //if Key not in possession
                                        panel.playSoundEffect(3);
                                        panel.GameState = panel.dialogueState;
                                        panel.ui.currentDialogue = dialogues[12];
                                        if(!complete[21]){
                                            complete[21] = true;
                                            panel.ui.CompletionOfGame++;
                                        }
                                    }
                                }
                                break;
                        }
                    }

                    // INTERACTION WITH BATHROOM OBJECTS
                    else if(panel.currentMap == 2){
                        switch (ObjectName) {
                            case "BottomDoor":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(4);
                                    switchMap(1, 5, 7);
                                    if(!complete[22]){
                                        complete[22] = true;
                                        panel.ui.CompletionOfGame++;
                                    }
                                }
                                break;

                            // NORMAL OBJECTS
                            case "BigBox":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[14];
                                    //panel.object[2][17] = null;   //Optional: Make Box disappear
                                    if(!complete[23]){
                                        complete[23] = true;
                                        panel.ui.CompletionOfGame++;
                                    }
                                }
                                break;

                            case "SmallBox":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[15];
                                    //panel.object[2][18] = null;   //Optional: Make Box disappear
                                    if(!complete[24]){
                                        complete[24] = true;
                                        panel.ui.CompletionOfGame++;
                                    }
                                }
                                break;

                            case "Duck":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[16];
                                    if(!complete[25]){
                                        complete[25] = true;
                                        panel.ui.CompletionOfGame++;
                                    }
                                }
                                break;

                            // INTERACTIVE OBJECTS
                            case "Sink":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if(ObjectCounter>9) {           //Ugliest fucking solution to ever exist
                                        panel.playSoundEffect(5);
                                        panel.object[2][14] = new B_Sink_Interact(panel);
                                        panel.object[2][14].MapX = 3 * panel.tileSize - 3;
                                        panel.object[2][14].MapY = 3 * panel.tileSize + 20;
                                        panel.object[2][10] = null;
                                        if(!complete[26]){
                                            complete[26] = true;
                                            panel.ui.CompletionOfGame++;
                                        }
                                        ObjectCounter = 0;
                                    }
                                }
                                break;

                            case "Sink_INTERACT":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if(ObjectCounter>9) {           //Ugliest fucking solution to ever exist
                                        panel.playSoundEffect(5);
                                        panel.object[2][10] = new B_Sink(panel);
                                        panel.object[2][10].MapX = 3 * panel.tileSize - 3;
                                        panel.object[2][10].MapY = 3 * panel.tileSize + 20;
                                        panel.object[2][14] = null;
                                        if(!complete[27]){
                                            complete[27] = true;
                                            panel.ui.CompletionOfGame++;
                                        }
                                        ObjectCounter = 0;
                                    }
                                }
                                break;

                            case "Toilet":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if(ObjectCounter>9) {           //Ugliest fucking solution to ever exist
                                        panel.playSoundEffect(5);
                                        panel.object[2][15] = new B_Toilet_Interact(panel);
                                        panel.object[2][15].MapX = 13 * panel.tileSize;
                                        panel.object[2][15].MapY = 12 * panel.tileSize + 20;
                                        panel.object[2][12] = null;
                                        if(!complete[28]){
                                            complete[28] = true;
                                            panel.ui.CompletionOfGame++;
                                        }
                                        ObjectCounter = 0;
                                    }
                                }
                                break;

                            case "Toilet_INTERACT":
                                if (handler.INTERACT) {
                                    ObjectCounter++;            //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if(ObjectCounter>9) {       //Ugliest fucking solution to ever exist
                                        panel.playSoundEffect(5);
                                        panel.object[2][12] = new B_Toilet(panel);
                                        panel.object[2][12].MapX = 13 * panel.tileSize;
                                        panel.object[2][12].MapY = 12 * panel.tileSize + 20;
                                        panel.object[2][15] = null;
                                        if(!complete[29]){
                                            complete[29] = true;
                                            panel.ui.CompletionOfGame++;
                                        }
                                        ObjectCounter=0;
                                    }
                                }
                                break;

                            case "Bathtub_INTERACT":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if (ObjectCounter > 9) {        //Ugliest fucking solution to ever exist
                                        if (panel.ui.foundTool) {
                                            panel.playSoundEffect(5);
                                            panel.object[2][16] = new B_Bathtub(panel);
                                            panel.object[2][16].MapX = 11 * panel.tileSize;
                                            panel.object[2][16].MapY = 6 * panel.tileSize - 24;
                                            panel.object[2][7] = null;
                                            if (!complete[30]) {
                                                complete[30] = true;
                                                panel.ui.CompletionOfGame++;
                                            }
                                            panel.ui.usedTool = true;
                                            ObjectCounter = 0;

                                            if (!KitchenKey) {
                                                panel.playSoundEffect(2);
                                                panel.GameState = panel.dialogueState;
                                                panel.ui.currentDialogue = dialogues[13];
                                                panel.ui.foundKey = true;
                                                KitchenKey = true;
                                                if (!complete[31]) {
                                                    complete[31] = true;
                                                    panel.ui.CompletionOfGame++;
                                                }
                                            }
                                        }
                                    } else {
                                        panel.playSoundEffect(3);
                                        panel.GameState = panel.dialogueState;
                                        panel.ui.currentDialogue = dialogues[17];
                                        if(!complete[32]){
                                            complete[32] = true;
                                            panel.ui.CompletionOfGame++;
                                        }
                                    }
                                }
                                break;

                            case "Bathtub":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                    //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if (ObjectCounter > 9) {           //Ugliest fucking solution to ever exist
                                            panel.playSoundEffect(3);
                                            panel.object[2][7] = new B_Bathtub_Interact(panel);
                                            panel.object[2][7].MapX = 11 * panel.tileSize;
                                            panel.object[2][7].MapY = 6 * panel.tileSize - 24;
                                            panel.object[2][16] = null;
                                            if(!complete[33]){
                                                complete[33] = true;
                                                panel.ui.CompletionOfGame++;
                                            }
                                            ObjectCounter = 0;
                                    }
                                }
                                break;
                        }
                    }

                    // INTERACTION WITH KITCHEN OBJECTS
                    else if(panel.currentMap == 3){
                        switch (ObjectName) {
                            case "SideDoor2":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(4);
                                    switchMap(1, 15, 11);
                                    if(!complete[34]){
                                        complete[34] = true;
                                        panel.ui.CompletionOfGame++;
                                    }
                                }
                                break;

                            case "Fridge":
                            if (handler.INTERACT) {
                                ObjectCounter++;                    //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                if (ObjectCounter > 8) {           //Ugliest fucking solution to ever exist
                                    panel.playSoundEffect(5);
                                    panel.object[3][23] = new K_Fridge_Interact(panel);
                                    panel.object[3][23].MapX = panel.tileSize-4;
                                    panel.object[3][23].MapY = 3 * panel.tileSize;
                                    panel.object[3][8] = null;
                                    if(MapY>(6* panel.tileSize-(panel.tileSize / 2)) && MapX<((panel.tileSize+26*4)-(panel.tileSize / 2)))
                                        MapY = panel.tileSize *8 - (panel.tileSize / 2);
                                    if(!complete[35]){
                                        complete[35] = true;
                                        panel.ui.CompletionOfGame++;
                                    }
                                    ObjectCounter = 0;
                                }
                            }
                            break;

                            case "Fridge_INTERACT":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                    //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if (ObjectCounter > 8) {           //Ugliest fucking solution to ever exist
                                            if(panel.ui.usedEmptyPot && !panel.ui.collectSoupCan) {
                                                panel.playSoundEffect(3);
                                                panel.GameState = panel.dialogueState;
                                                panel.ui.currentDialogue = dialogues[18];
                                                if(!complete[36]){
                                                    complete[36] = true;
                                                    panel.ui.CompletionOfGame++;
                                                }
                                                panel.playSoundEffect(2);
                                                panel.ui.collectSoupCan = true;
                                            }
                                            else {
                                                if(!panel.ui.collectSoupCan) {
                                                    panel.playSoundEffect(3);
                                                    panel.GameState = panel.dialogueState;
                                                    panel.ui.currentDialogue = dialogues[19];
                                                    if(!complete[37]){
                                                        complete[37] = true;
                                                        panel.ui.CompletionOfGame++;
                                                    }
                                                }
                                                panel.playSoundEffect(5);
                                                panel.object[3][8] = new K_Fridge(panel);
                                                panel.object[3][8].MapX = panel.tileSize;
                                                panel.object[3][8].MapY = 3 * panel.tileSize;
                                                panel.object[3][23] = null;
                                                if(!complete[38]){
                                                    complete[38] = true;
                                                    panel.ui.CompletionOfGame++;
                                                }
                                            }
                                        ObjectCounter = 0;
                                    }
                                }
                                break;

                            case "Oven":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                    //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if (ObjectCounter > 8) {           //Ugliest fucking solution to ever exist
                                        panel.playSoundEffect(5);
                                        panel.object[3][24] = new K_Oven_Interact(panel);
                                        panel.object[3][24].MapX = 5*panel.tileSize;
                                        panel.object[3][24].MapY = 4 * panel.tileSize;
                                        panel.object[3][9] = null;
                                        if(!complete[39]){
                                            complete[39] = true;
                                            panel.ui.CompletionOfGame++;
                                        }
                                        ObjectCounter = 0;
                                    }
                                }
                                break;

                            case "Oven_INTERACT":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                    //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if (ObjectCounter > 8) {           //Ugliest fucking solution to ever exist
                                        if(!panel.ui.collectEmptyPot || panel.ui.collectSoupPlate ) {   //Nothing collected jet or Oven is finished (Soup is made)
                                            panel.playSoundEffect(5);
                                            panel.object[3][9] = new K_Oven(panel);
                                            panel.object[3][9].MapX = 5 * panel.tileSize;
                                            panel.object[3][9].MapY = 4 * panel.tileSize;
                                            panel.object[3][24] = null;
                                            if(!complete[40]){
                                                complete[40] = true;
                                                panel.ui.CompletionOfGame++;
                                            }
                                        } else {
                                            if(!panel.ui.collectSoupCan) {                  //Empty Pot but no soup
                                                panel.playSoundEffect(5);
                                                panel.object[3][27] = new K_EmptyPot(panel);
                                                panel.object[3][27].MapX = 5 * panel.tileSize + 8;
                                                panel.object[3][27].MapY = 4 * panel.tileSize + 4;
                                                if(!complete[41]){
                                                    complete[41] = true;
                                                    panel.ui.CompletionOfGame++;
                                                }
                                                panel.ui.usedEmptyPot = true;
                                            } else {
                                                if (!panel.ui.collectEmptyPlate) {  //Put Soup in Pot
                                                    if (!panel.ui.usedSoupCan) {
                                                        panel.playSoundEffect(5);
                                                        panel.object[3][28] = new K_SoupPot(panel);
                                                        panel.object[3][28].MapX = 5 * panel.tileSize + 8;
                                                        panel.object[3][28].MapY = 4 * panel.tileSize + 4;
                                                        panel.object[3][27] = null;
                                                        if (!complete[42]) {
                                                            complete[42] = true;
                                                            panel.ui.CompletionOfGame++;
                                                        }
                                                        panel.ui.usedSoupCan = true;
                                                    } else {
                                                        panel.playSoundEffect(3);
                                                        panel.GameState = panel.dialogueState;
                                                        panel.ui.currentDialogue = dialogues[20];
                                                        if (!complete[43]) {
                                                            complete[43] = true;
                                                            panel.ui.CompletionOfGame++;
                                                        }
                                                    }
                                                } else {      //Put Soup in Plate
                                                    panel.playSoundEffect(5);
                                                    panel.object[3][27] = new K_EmptyPot(panel);
                                                    panel.object[3][27].MapX = 5 * panel.tileSize + 8;
                                                    panel.object[3][27].MapY = 4 * panel.tileSize + 4;
                                                    panel.object[3][28] = null;
                                                    if (!complete[44]) {
                                                        complete[44] = true;
                                                        panel.ui.CompletionOfGame++;
                                                    }
                                                    panel.ui.usedEmptyPlate = true;
                                                    panel.playSoundEffect(2);
                                                    panel.ui.collectSoupPlate = true;
                                                }
                                            }
                                        }
                                        ObjectCounter = 0;
                                    }
                                }
                                break;

                            case "Sink":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                    //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if (ObjectCounter > 8) {           //Ugliest fucking solution to ever exist
                                        panel.playSoundEffect(5);
                                        panel.object[3][25] = new K_Sink_Interact(panel);
                                        panel.object[3][25].MapX = 8*panel.tileSize;
                                        panel.object[3][25].MapY = 4 * panel.tileSize-4;
                                        panel.object[3][10] = null;
                                        if(!complete[45]){
                                            complete[45] = true;
                                            panel.ui.CompletionOfGame++;
                                        }
                                        ObjectCounter = 0;
                                    }
                                }
                                break;

                            case "Sink_INTERACT":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                    //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if (ObjectCounter > 8) {           //Ugliest fucking solution to ever exist
                                        panel.playSoundEffect(5);
                                        panel.object[3][10] = new K_Sink(panel);
                                        panel.object[3][10].MapX = 8*panel.tileSize;
                                        panel.object[3][10].MapY = 4 * panel.tileSize-4;
                                        panel.object[3][25] = null;
                                        if(!complete[46]){
                                            complete[46] = true;
                                            panel.ui.CompletionOfGame++;
                                        }
                                        ObjectCounter = 0;
                                    }
                                }
                                break;

                            case "Shelf":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                    //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if (ObjectCounter > 8) {           //Ugliest fucking solution to ever exist
                                        if(panel.ui.collectEmptyPot) {
                                            panel.playSoundEffect(5);
                                            panel.object[3][26] = new K_Shelf_Interact(panel);
                                            panel.object[3][26].MapX = 8 * panel.tileSize + 32 * 4;
                                            panel.object[3][26].MapY = 4 * panel.tileSize;
                                            panel.object[3][11] = null;
                                            if(!complete[47]){
                                                complete[47] = true;
                                                panel.ui.CompletionOfGame++;
                                            }
                                            if (!panel.ui.usedSoupCan) {
                                                panel.playSoundEffect(3);
                                                panel.GameState = panel.dialogueState;
                                                panel.ui.currentDialogue = dialogues[21];
                                                if(!complete[48]){
                                                    complete[48] = true;
                                                    panel.ui.CompletionOfGame++;
                                                }
                                            }
                                        } else {
                                            panel.playSoundEffect(2);
                                            panel.object[3][22] = null;
                                            if(!complete[49]){
                                                complete[49] = true;
                                                panel.ui.CompletionOfGame++;
                                            }
                                            panel.ui.collectEmptyPot = true;
                                        }
                                        ObjectCounter = 0;
                                    }
                                }
                                break;

                            case "Shelf_INTERACT":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                    //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if (ObjectCounter > 8) {           //Ugliest fucking solution to ever exist
                                         if(panel.ui.usedSoupCan && !panel.ui.collectEmptyPlate) {
                                             if(!complete[50]){
                                                 complete[50] = true;
                                                 panel.ui.CompletionOfGame++;
                                             }
                                             panel.playSoundEffect(2);
                                             panel.ui.collectEmptyPlate = true;
                                        } else {
                                             panel.playSoundEffect(5);
                                             panel.object[3][11] = new K_Shelf(panel);
                                             panel.object[3][11].MapX = 8 * panel.tileSize + 32 * 4;
                                             panel.object[3][11].MapY = 4 * panel.tileSize;
                                             panel.object[3][26] = null;
                                             if(!complete[51]){
                                                 complete[51] = true;
                                                 panel.ui.CompletionOfGame++;
                                             }
                                         }
                                        ObjectCounter = 0;
                                    }
                                }
                                break;

                            case "Table":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                    //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if (ObjectCounter > 9) {           //Ugliest fucking solution to ever exist
                                        if(!panel.ui.collectSoupPlate){
                                            panel.playSoundEffect(3);
                                            panel.GameState = panel.dialogueState;
                                            panel.ui.currentDialogue = dialogues[22];
                                            if(!complete[52]){
                                                complete[52] = true;
                                                panel.ui.CompletionOfGame++;
                                            }
                                        } else {
                                            if (!panel.ui.usedSoupPlate) {
                                                panel.playSoundEffect(5);
                                                panel.object[3][29] = new K_SoupPlate(panel);
                                                panel.object[3][29].MapX = 6 * panel.tileSize - 25;
                                                panel.object[3][29].MapY = 11 * panel.tileSize + 24;
                                                if (!complete[53]) {
                                                    complete[53] = true;
                                                    panel.ui.CompletionOfGame++;
                                                }
                                                panel.ui.usedSoupPlate = true;
                                            } else {
                                                panel.playSoundEffect(5);
                                                panel.object[3][29] = new K_EmptyPlate(panel);
                                                panel.object[3][29].MapX = 6 * panel.tileSize - 25;
                                                panel.object[3][29].MapY = 11 * panel.tileSize + 24;
                                                if (!complete[54]) {
                                                    complete[54] = true;
                                                    panel.ui.CompletionOfGame++;
                                                }
                                                panel.playSoundEffect(3);
                                                panel.GameState = panel.dialogueState;
                                                panel.ui.currentDialogue = dialogues[23];
                                                panel.playSoundEffect(2);
                                                OutsideKey = true;
                                                panel.ui.foundKey = true;
                                            }
                                        }
                                        ObjectCounter = 0;
                                    }
                                }
                                break;
                        }
                    }
            }
        }
    }

    // LIST OF ALL DIALOGUES
    public void setDialogue() {
        int i=0;
        // BEDROOM
        dialogues[i] = "The door is locked...\nWhat should I do?";  i++;
        dialogues[i] = "I don't think I can fall asleep...";    i++;
        dialogues[i] = "huh...\nseems like there is a key in the drawer.";  i++;
        dialogues[i] = "My favorite book is about a goldfish."; i++;
        dialogues[i] = "I don't want to play right now. \nMaybe later Horsy.";  i++;

        // CORRIDOR
        dialogues[i] = "If I want more food for Nemo\nI need to find the house key.";  i++;
        dialogues[i] = "Maybe Santa would bring me presents\nif it weren't for CollisionDetection-Class";   i++;
        dialogues[i] = "Since the creators of \"Way Out\" are to\nlazy to animate me sitting\nI'll just stand"; i++;
        dialogues[i] = "That's dad's Hat.\nI'm not allowed to take it."; i++;
        dialogues[i] = "It's my favorite hiding spot!\nNo one ever finds me hehe";    i++;
        dialogues[i] = "oh! I found a screwdriver.\nMaybe I can use it for something..."; i++;
        dialogues[i] = "Hello Nemo.";   i++;
        dialogues[i] = "Why is the kitchen locked?\nI'm so hungry...";    i++;

        // BATHROOM
        dialogues[i] = "I found another key.";   i++;
        dialogues[i] = "A big box filled with towels.\nAnd ducks.";    i++;
        dialogues[i] = "A tiny box filled with tiny towels.\nAnd tiny Ducks.";  i++;
        dialogues[i] = "Hi Donald Duck."; i++;
        dialogues[i] = "I see something shiny!!\nI need to drain the water but it's clogged.\nLet's look for something in the corridor."; i++;  //17

        // KITCHEN
        dialogues[i] = "Tomato Soup. YUCK."; i++;  //18
        dialogues[i] = "I will cook some soup!\nJust like mom sometimes does."; i++;
        dialogues[i] = "I need a plate."; i++;
        dialogues[i] = "Here are all our plates."; i++;
        dialogues[i] = "I like to stand while I eat..."; i++;
        dialogues[i] = "Ouch!!\nI bit on something hard..."; //23

    }

    // DRAW PLAYER
    public void draw(Graphics2D graphics2D) {
        //draw object with current information

        /*graphics2D.setColor(Color.white);     <- for testing purposes
        graphics2D.fillRect(x, y, panel.tileSize, panel.tileSize);  / Rectangle (position x, position y, width, height)*/


        // DRAW SPRITE IMAGE
        BufferedImage image = null;

        switch (direction) {         //each possible direction
            case "UP" -> {
                if (spriteNum == 1) image = UP1;
                if (spriteNum == 2) image = UP2;
                if (spriteNum == 3) image = UP3;
            }
            case "DOWN" -> {
                if (spriteNum == 1) image = DOWN1;
                if (spriteNum == 2) image = DOWN2;
                if (spriteNum == 3) image = DOWN3;
            }
            case "LEFT" -> {
                if (spriteNum == 1) image = LEFT1;
                if (spriteNum == 2) image = LEFT2;
                if (spriteNum == 3) image = LEFT3;
            }
            case "RIGHT" -> {
                if (spriteNum == 1) image = RIGHT1;
                if (spriteNum == 2) image = RIGHT2;
                if (spriteNum == 3) image = RIGHT3;
            }
        }

        // STOP SCREEN AT EDGE OF MAP
        int x = ScreenX;
        int y = ScreenY;

        //Left Side / Top Side of Map
        if(ScreenX > MapX)  //If Screen at the edge of Map -> Player no more in center of Map
            x = MapX;       //Player Position is new Map Position instead of Screen Position
        if(ScreenY > MapY)
            y = MapY;


        //Right Side / Bottom Side of Map (same as in Manager Class)
        int RightOffset = panel.ScreenWidth - ScreenX;
        if(RightOffset > panel.MapWidth - MapX)
            x = panel.ScreenWidth - (panel.MapWidth - MapX);

        int BottomOffset = panel.ScreenHeight - ScreenY;
        if(BottomOffset > panel.MapHeight - MapY)
            y = panel.ScreenHeight - (panel.MapHeight - MapY);

        //ScreenX and ScreenY don't change
        graphics2D.drawImage(image, x, y, null);    //null: image observer  +8 to make character bigger
    }

    // SWITCH ROOMS/MAPS
    public void switchMap(int Map, int x, int y) {
        panel.TransitionMap = Map;
        panel.TransitionX = x;
        panel.TransitionY = y;
        panel.playSoundEffect(3);
        panel.GameState = panel.transitionState;
    }
}
