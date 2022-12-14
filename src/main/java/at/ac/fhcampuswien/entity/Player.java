package at.ac.fhcampuswien.entity;

import at.ac.fhcampuswien.main.MyPanel;
import at.ac.fhcampuswien.main.Handler;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    MyPanel panel;
    Handler handler;

    public Player(MyPanel panel, Handler handler){
        this.panel = panel;
        this.handler = handler;

        setDefaultValues();     //call setDefaultValues()
        playerImage();          //call playerImage()
    }

    public void setDefaultValues(){
        playerX = 100;        //position vertical
        playerY = 100;        //position horizontal
        Speed = 4;      //same as in Panel class
        direction = "DOWN";
    }

    public void playerImage(){
        try{    //Gives every direction corresponding image
            UP1 = ImageIO.read(getClass().getResource("/Xeno/Xeno_Back_1.png"));
            UP2 = ImageIO.read(getClass().getResource("/Xeno/Xeno_Back_2.png"));
            UP3 = ImageIO.read(getClass().getResource("/Xeno/Xeno_Back_3.png"));

            DOWN1 = ImageIO.read(getClass().getResource("/Xeno/Xeno_Front_1.png"));
            DOWN2 = ImageIO.read(getClass().getResource("/Xeno/Xeno_Front_2.png"));
            DOWN3 = ImageIO.read(getClass().getResource("/Xeno/Xeno_Front_3.png"));

            LEFT1 = ImageIO.read(getClass().getResource("/Xeno/Xeno_Left_1.png"));
            LEFT2 = ImageIO.read(getClass().getResource("/Xeno/Xeno_Left_2.png"));
            LEFT3 = ImageIO.read(getClass().getResource("/Xeno/Xeno_Left_3.png"));

            RIGHT1 = ImageIO.read(getClass().getResource("/Xeno/Xeno_Right_1.png"));
            RIGHT2 = ImageIO.read(getClass().getResource("/Xeno/Xeno_Right_2.png"));
            RIGHT3 = ImageIO.read(getClass().getResource("/Xeno/Xeno_Right_3.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update() {  //update current position of object
        if(handler.UP || handler.DOWN || handler.LEFT || handler.RIGHT){    //don't move if no key is pressed
            if(handler.UP){   //if W (UP) is pressed (=true)
                direction = "UP";   //change direction of position
                playerY = playerY-Speed;  //-> change current position
            }
            if(handler.DOWN){   //when going down/up-left/right, always up/down first then left so character looks to the side when going diagonal
                direction = "DOWN";
                playerY = playerY+Speed;
            }
            if(handler.LEFT){
                direction = "LEFT";
                playerX = playerX-Speed;
            }
            if(handler.RIGHT){
                direction = "RIGHT";
                playerX = playerX+Speed;
            }
            spriteCounter++;
            if(spriteCounter>12){            //How fast to change
                if (spriteNum == 1){
                    spriteNum = 2;
                } else if (spriteNum ==2){
                    spriteNum = 3;
                } else if (spriteNum == 3){
                    spriteNum = 1;
                }
                spriteCounter = 0;

            }
        } else spriteNum = 2;       //if he stops moving go to basic position (Position 2)
    }

    public void draw(Graphics2D graphics2D) {   //draw object with current information
        //graphics2D.setColor(Color.white);     //<- for testing purposes
        //graphics2D.fillRect(x, y, panel.tileSize, panel.tileSize);  //Rectangle (position x, position y, width, height)

        BufferedImage image = null;

        switch(direction) {         //each possible direction
            case "UP":
                if(spriteNum == 1){
                    image = UP1;}
                if(spriteNum == 2){
                    image = UP2;}
                if(spriteNum == 3){
                    image = UP3;}
                break;
            case "DOWN":
                if(spriteNum == 1){
                    image = DOWN1;}
                if(spriteNum == 2){
                    image = DOWN2;}
                if(spriteNum == 3){
                    image = DOWN3;}
                break;
            case "LEFT":
                if(spriteNum == 1){
                    image = LEFT1;}
                if(spriteNum == 2){
                    image = LEFT2;}
                if(spriteNum == 3){
                    image = LEFT3;}
                break;
            case "RIGHT":
                if(spriteNum == 1){
                    image = RIGHT1;}
                if(spriteNum == 2){
                    image = RIGHT2;}
                if(spriteNum == 3){
                    image = RIGHT3;}
                break;
        }
        graphics2D.drawImage(image, playerX, playerY, panel.tileSize, panel.tileSize, null);    //null: image observer
    }
}
