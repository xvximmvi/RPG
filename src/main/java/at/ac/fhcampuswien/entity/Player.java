package at.ac.fhcampuswien.entity;

import at.ac.fhcampuswien.main.Handler;
import at.ac.fhcampuswien.main.MyPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    MyPanel panel;
    Handler handler;

    public final int ScreenX, ScreenY;

    public Player(MyPanel panel, Handler handler){
        this.panel = panel;
        this.handler = handler;

        ScreenX = panel.ScreenWidth/2;      //always in center
        ScreenY = panel.ScreenHeight/2;

        Area = new Rectangle();     //create area of collision within the player character
        //4 parameter (x, y, width, height) of player character: which part of the character has collision detection
        Area.x = 18;
        Area.y = 36;
        AreaDefaultX = Area.x;
        AreaDefaultY = Area.y;
        Area.width = 38;    //48 pixels for 1 tile: is to precisely -> difficult to go through
        Area.height = 38;


        setDefaultValues();         //call setDefaultValues()
        playerImage();              //call playerImage()
    }

    public void setDefaultValues(){
        MapX = panel.tileSize*12-(panel.tileSize/2);    //position vertical Center on Map
        MapY = panel.tileSize*9-(panel.tileSize/2);     //position horizontal Center on Map
        Speed = 4;      //same as in Panel class
        direction = "DOWN";
    }

    public void playerImage(){
        try{    //Gives every direction corresponding image
            UP1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/MC/MC_B1.png")));
            UP2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/MC/MC_B2.png")));
            UP3 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/MC/MC_B3.png")));

            DOWN1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/MC/MC_F1.png")));
            DOWN2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/MC/MC_F2.png")));
            DOWN3 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/MC/MC_F3.png")));

            LEFT1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/MC/MC_L1.png")));
            LEFT2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/MC/MC_L2.png")));
            LEFT3 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/MC/MC_L3.png")));

            RIGHT1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/MC/MC_R1.png")));
            RIGHT2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/MC/MC_R2.png")));
            RIGHT3 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/MC/MC_R3.png")));


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update() {  //update current position of object
        if(handler.UP || handler.DOWN || handler.LEFT || handler.RIGHT){    //move if any key is pressed
            if(handler.UP){   //if W (UP) is pressed (=true)
                direction = "UP";   //change direction of position
                //MapY = MapY-Speed;  //-> change current position
            }
            if(handler.DOWN){   //when going down/up-left/right (diagonal), always up/down first then left/right so character looks to the side when going diagonal
                direction = "DOWN";
                //MapY = MapY+Speed;
            }
            if(handler.LEFT){
                direction = "LEFT";
                //MapX = MapX-Speed;
            }
            if(handler.RIGHT){
                direction = "RIGHT";
                //MapX = MapX+Speed;
            }

            //CHECK TILE COLLISION
            collisionOn = false;
            //player Class is a subclass of the Entity class
            panel.collisionDetection.DetectTile(this);  //CollisionDetection receives Player class as Entity

            //CHECK OBJECT COLLISION
            int objectIndex = panel.collisionDetection.DetectObject(this, true);

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(!collisionOn){
                switch (direction) {
                    case "UP" -> MapY = MapY - Speed;
                    case "DOWN" -> MapY = MapY + Speed;
                    case "LEFT" -> MapX = MapX - Speed;
                    case "RIGHT" -> MapX = MapX + Speed;
                }
            }
            spriteCounter++;
            if(spriteCounter>12){            //How fast to change
                if (spriteNum ==2)          spriteNum = 1;
                else if (spriteNum == 1)    spriteNum = 3;
                else if (spriteNum == 3)    spriteNum = 1;
                spriteCounter = 0;
            }
        } else spriteNum = 2;       //if he stops moving go to basic position (Position 2)
    }

    public void draw(Graphics2D graphics2D) {   //draw object with current information
        /*graphics2D.setColor(Color.white);     <- for testing purposes
        graphics2D.fillRect(x, y, panel.tileSize, panel.tileSize);  / Rectangle (position x, position y, width, height)*/

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

        //STOP SCREEN AT EDGE OF MAP
        int x = ScreenX;
        int y = ScreenY;

        //left side of Map
        if(ScreenX > MapX)
            x = MapX;
        if(ScreenY > MapY)
            y = MapY;

        //same as in Manager Class  (Right side of Map)
        int RightOffset = panel.ScreenWidth - ScreenX;
        if(RightOffset > panel.MapWidth - MapX)
            x = panel.ScreenWidth - (panel.MapWidth - MapX);

        int BottomOffset = panel.ScreenHeight - ScreenY;
        if(BottomOffset > panel.MapHeight - MapY)
            y = panel.ScreenHeight - (panel.MapHeight - MapY);

        graphics2D.drawImage(image, x, y, panel.tileSize+20, panel.tileSize+20, null);    //null: image observer  +8 to make character bigger
    }                   //ScreenX and ScreenY don't change
}
