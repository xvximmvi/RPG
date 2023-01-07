package at.ac.fhcampuswien.entity;

import at.ac.fhcampuswien.Object.BR_Light_ON;
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

    public final int ScreenX, ScreenY;  //Coordinate of Screen
    public int Keys = 0;                //Number of collected/found Keys
    int InteractionCounter=0;           //InteractionCounter: slower interaction with objects

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
    }

    // DEFAULT POSITION & DIRECTION
    public void setDefaultValues(){
        MapX = panel.tileSize*12-(panel.tileSize/2);    //position vertical Center on Map
        MapY = panel.tileSize*9-(panel.tileSize/2);     //position horizontal Center on Map
        Speed = 4;      //same as in Panel class
        direction = "DOWN";

        panel.ui.TutorialOn = true;     //Start game with Tutorial
    }

    // PLAYER IMAGES
    public void playerImage(){
        UP1 = setup("MC_B1");
        UP2 = setup("MC_B2");
        UP3 = setup("MC_B3");

        DOWN1 = setup("MC_F1");
        DOWN2 = setup("MC_F2");
        DOWN3 = setup("MC_F2");

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

        // Index as confirmation of collision
        if(index != 999){   //if index isn't 999, then we haven't touched an object
            //panel.object[index] = null;     //if object is touched, then delete it -> "Pick it up"

            String ObjectName = panel.object[index].name;   //Which object is touched

            InteractionCounter++;       //Key-press takes to long -> interact more than once at a time
            if(InteractionCounter>9) {  //count to 9 than do next interaction

                // OBJECT INTERACTION
                switch (ObjectName) {   //Identify Object
                    case "Nightstand":  //If Nightstand is "opened" than:
                        if (Keys < 1 && handler.INTERACT) {            //if no Key in possession:
                            panel.playSoundEffect(4);               //Play Key Sound
                            panel.ui.ShowMessage("The Key!");     //Print Message on screen
                            Keys++;                                    //Increase numbers of Keys
                        } else if (Keys >= 1 && handler.INTERACT) {    //if Key is already in Possession
                            panel.playSoundEffect(5);               //play Notification Sound

                            //Turn Light on and off
                            BR_Light_State = !BR_Light_State;   //change Light State
                            if(BR_Light_State){             //if turning on -> instantiate new Object (Light_ON)
                                panel.object[13] = new BR_Light_ON(panel);
                                panel.object[13].MapX = 11 * panel.tileSize - 2;
                                panel.object[13].MapY = 4 * panel.tileSize - 23;
                            } else  panel.object[13] = null;    //if turning off-> then delete Object (null)
                        }
                        break;

                    case "Bed":
                        if (handler.INTERACT) {
                            panel.playSoundEffect(5);
                            panel.ui.ShowMessage("*Looks under Bed* \r\n Nothing");
                        }

                        break;

                    case "Bookshelf":
                        if (handler.INTERACT) {
                            panel.playSoundEffect(5);
                            panel.ui.ShowMessage("*Looks between Books* \r\n Nothing");
                        }
                        break;

                    case "ToyHorse":
                        if (handler.INTERACT) {
                            panel.playSoundEffect(5);
                            panel.ui.ShowMessage("*Looks under Toy Horse* \r\n Nothing.");
                        }
                        break;

                    case "BottomDoor":
                        if (Keys >= 1 && handler.INTERACT) {    //if Key in possession ...
                            panel.ui.GameWon = true;            //... then Door is unlocked (Game Won)
                            //panel.ui.ShowMessage("Open! Can go through.");

                        } else if (Keys < 1 && handler.INTERACT) {  //if no Key in possession
                            panel.playSoundEffect(5);             //notification
                            panel.ui.ShowMessage("It's locked.");
                        }
                        break;
                }
              InteractionCounter = 0;   //reset InteractionCounter
           }
        }
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
}
