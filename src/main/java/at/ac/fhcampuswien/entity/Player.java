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
import java.util.concurrent.TimeUnit;

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
                                    } else {    //if Key is already in Possession
                                        panel.playSoundEffect(3);               //play Notification Sound
                                        //Turn Light on and off
                                        BR_Light_State = !BR_Light_State;   //change Light State
                                        if (BR_Light_State) {             //if turning on -> instantiate new Object (Light_ON)
                                            panel.object[0][13] = new BR_Light_ON(panel);
                                            panel.object[0][13].MapX = 11 * panel.tileSize - 2;
                                            panel.object[0][13].MapY = 4 * panel.tileSize - 23;
                                        } else
                                            panel.object[0][13] = null;    //if turning off-> then delete Object (null)
                                    }
                                }
                                break;

                            case "Bed":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[1];
                                }
                                break;

                            case "Bookshelf":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[3];
                                }
                                break;

                            case "ToyHorse":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[4];
                                }
                                break;

                            case "BottomDoor":
                                if(handler.INTERACT) {
                                    if (BedroomKey) {    //if Key in possession ...
                                        switchMap(1, 12, 7);
                                        if (!KitchenKey && !OutsideKey)
                                            panel.ui.foundKey = false;
                                    } else {  //if no Key in possession
                                        panel.playSoundEffect(3);
                                        panel.GameState = panel.dialogueState;
                                        panel.ui.currentDialogue = dialogues[0];
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
                                } else if (!OutsideKey && handler.INTERACT) {  //if no Key in possession
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[5];
                                }
                                break;
                            case "Bedroom_Door":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    switchMap(0, 8, 14);
                                }
                                break;
                            case "Bathroom_Door":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    switchMap(2, 8, 14);
                                }
                                break;
                            case "Fire":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[6];
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
                                        ObjectCounter = 0;
                                    }
                                }
                                break;
                            case "Chouch":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[7];
                                }
                                break;
                            case "Clothes":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[8];
                                }
                                break;
                            case "Clock":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[9];
                                }
                                break;
                            case "ShelfFish":
                                if (!panel.ui.foundTool && handler.INTERACT) {
                                    panel.playSoundEffect(2);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[10];
                                    panel.ui.foundTool = true;
                                } else if (panel.ui.foundTool && handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[11];
                                }
                                break;

                            case "SideDoor1":
                                if(handler.INTERACT) {
                                    if (KitchenKey) {    //if Key in possession ...
                                        switchMap(3, 1, 11);
                                        if (!OutsideKey)
                                            panel.ui.foundKey = false;
                                    } else {  //if no Key in possession
                                        panel.playSoundEffect(3);
                                        panel.GameState = panel.dialogueState;
                                        panel.ui.currentDialogue = dialogues[12];
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
                                    panel.playSoundEffect(3);
                                    switchMap(1, 5, 7);
                                }
                                break;

                            // NORMAL OBJECTS
                            case "BigBox":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[14];
                                    //panel.object[2][17] = null;   //Optional: Make Box disappear
                                }
                                break;

                            case "SmallBox":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[15];
                                    //panel.object[2][18] = null;   //Optional: Make Box disappear
                                }
                                break;

                            case "Duck":
                                if (handler.INTERACT) {
                                    panel.playSoundEffect(3);
                                    panel.GameState = panel.dialogueState;
                                    panel.ui.currentDialogue = dialogues[16];
                                }
                                break;

                            // INTERACTIVE OBJECTS
                            case "Sink":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if(ObjectCounter>9) {           //Ugliest fucking solution to ever exist
                                        panel.playSoundEffect(3);
                                        panel.object[2][14] = new B_Sink_Interact(panel);
                                        panel.object[2][14].MapX = 3 * panel.tileSize - 3;
                                        panel.object[2][14].MapY = 3 * panel.tileSize + 20;
                                        panel.object[2][10] = null;
                                        ObjectCounter = 0;
                                    }
                                }
                                break;

                            case "Sink_INTERACT":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if(ObjectCounter>9) {           //Ugliest fucking solution to ever exist
                                        panel.playSoundEffect(3);
                                        panel.object[2][10] = new B_Sink(panel);
                                        panel.object[2][10].MapX = 3 * panel.tileSize - 3;
                                        panel.object[2][10].MapY = 3 * panel.tileSize + 20;
                                        panel.object[2][14] = null;
                                        ObjectCounter = 0;
                                    }
                                }
                                break;

                            case "Toilet":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if(ObjectCounter>9) {           //Ugliest fucking solution to ever exist
                                        panel.playSoundEffect(3);
                                        panel.object[2][15] = new B_Toilet_Interact(panel);
                                        panel.object[2][15].MapX = 13 * panel.tileSize;
                                        panel.object[2][15].MapY = 12 * panel.tileSize + 20;
                                        panel.object[2][12] = null;
                                        ObjectCounter = 0;
                                    }
                                }
                                break;

                            case "Toilet_INTERACT":
                                if (handler.INTERACT) {
                                    ObjectCounter++;            //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if(ObjectCounter>9) {       //Ugliest fucking solution to ever exist
                                        panel.playSoundEffect(3);
                                        panel.object[2][12] = new B_Toilet(panel);
                                        panel.object[2][12].MapX = 13 * panel.tileSize;
                                        panel.object[2][12].MapY = 12 * panel.tileSize + 20;
                                        panel.object[2][15] = null;
                                        ObjectCounter=0;
                                    }
                                }
                                break;

                            case "Bathtub_INTERACT":
                                if (handler.INTERACT) {
                                    if(panel.ui.foundTool) {
                                        ObjectCounter++;                //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                        if (ObjectCounter > 9) {           //Ugliest fucking solution to ever exist
                                            panel.playSoundEffect(3);
                                            panel.object[2][16] = new B_Bathtub(panel);
                                            panel.object[2][16].MapX = 11 * panel.tileSize;
                                            panel.object[2][16].MapY = 6 * panel.tileSize - 24;
                                            panel.object[2][7] = null;
                                            ObjectCounter = 0;
                                        }
                                    } else {
                                        panel.playSoundEffect(3);
                                        panel.GameState = panel.dialogueState;
                                        panel.ui.currentDialogue = dialogues[17];
                                    }
                                }
                                break;

                            case "Bathtub":
                                if (handler.INTERACT) {
                                    ObjectCounter++;                    //Problem: Object switches to fast. One KeyPress = many Interactions at once
                                    if (ObjectCounter > 9) {           //Ugliest fucking solution to ever exist
                                        if (!KitchenKey) {
                                            panel.playSoundEffect(3);
                                            panel.GameState = panel.dialogueState;
                                            panel.ui.currentDialogue = dialogues[13];
                                            panel.ui.foundKey = true;
                                            KitchenKey = true;
                                        } else {
                                            panel.playSoundEffect(3);
                                            panel.object[2][7] = new B_Bathtub_Interact(panel);
                                            panel.object[2][7].MapX = 11 * panel.tileSize;
                                            panel.object[2][7].MapY = 6 * panel.tileSize - 24;
                                            panel.object[2][16] = null;
                                            ObjectCounter = 0;
                                        }
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
                                        switchMap(1, 15, 11);
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
        dialogues[i] = "I don't want to play right now. \nMaybe later horsy.";  i++;

        // CORRIDOR
        dialogues[i] = "STILL MISSING DIALOGE\nFINAL DOOR = GOAL";  i++;
        dialogues[i] = "STILL MISSING DIALOGE\nSomething about Fire (Fireplace)";   i++;
        dialogues[i] = "STILL MISSING DIALOGE\nIDK what to say about a fucking Chouch (Couch)"; i++;
        dialogues[i] = "STILL MISSING DIALOGE\nMaybe something about the Hat or idk (Clothes)"; i++;
        dialogues[i] = "STILL MISSING DIALOGE\nClock.. (Clock)";    i++;
        dialogues[i] = "STILL MISSING DIALOGE\nFOUND TOOL TO SCREW BATHTUB (Shelf + Tool)"; i++;
        dialogues[i] = "STILL MISSING DIALOGE\nFishy. Goldfish. Nemo. idk. (Shelf/Fish)";   i++;
        dialogues[i] = "STILL MISSING DIALOGE\nKITCHEN DOOR LOCKED! (Kitchen Door)";    i++;

        // BATHROOM
        dialogues[i] = "STILL MISSING DIALOGE\nKEY FOR KITCHEN FOUND! (Bathtub)";   i++;
        dialogues[i] = "STILL MISSING DIALOGE\nBig empty box with stuff. idk. (BB)";    i++;
        dialogues[i] = "STILL MISSING DIALOGE\nSmall empty box with stuff. idk. (SB)";  i++;
        dialogues[i] = "STILL MISSING DIALOGE\nToy Duck. Bath Duck. something. (Duck)"; i++;
        dialogues[i] = "STILL MISSING DIALOGE\nCANT OPEN! NEED TOOL FROM CORRIDOR (Bathtub)"; i++;  //17

        // KITCHEN

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
