package at.ac.fhcampuswien.entity;

import at.ac.fhcampuswien.main.MyPanel;

import java.awt.*;
import java.awt.image.BufferedImage;

//This stores variables that will be used in players, monsters and NPCs
public class Entity {
    public int MapX, MapY;
    public int Speed;

    //Image with an accessible buffer of image data (to store our image files)
    public BufferedImage UP1, UP2, UP3, DOWN1, DOWN2, DOWN3, LEFT1, LEFT2, LEFT3, RIGHT1, RIGHT2, RIGHT3;
    public String direction;        //direction of character moving

    public int spriteCounter = 0;   //count up to change position
    public int spriteNum = 1;       //number of sprite
    public Rectangle Area;          //Collision Area
    public int AreaDefaultX, AreaDefaultY;
    public boolean collisionOn = false;

    /*MyPanel panel;

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int ScreenX = MapX - panel.player.MapX + panel.player.ScreenX;
        int ScreenY = MapY - panel.player.MapY + panel.player.ScreenY;
        // STOP MOVING CAMERA
        if(panel.player.MapX < panel.player.ScreenX) {
            ScreenX = MapX;
        }
        if(panel.player.MapY < panel.player.ScreenY) {
            ScreenY = MapY;
        }
        int rightOffset = panel.ScreenWidth - panel.player.ScreenX;
        if(rightOffset > panel.MapWidth - panel.player.MapX) {
            ScreenX = panel.ScreenWidth - (panel.MapWidth - MapX);
        }
        int bottomOffset = panel.ScreenHeight - panel.player.ScreenY;
        if(bottomOffset > panel.MapHeight - panel.player.MapY) {
            ScreenY = panel.ScreenHeight - (panel.MapHeight - MapY);
        }

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

        if(MapX + panel.tileSize > panel.player.MapX - panel.player.ScreenX &&
                MapX - panel.tileSize < panel.player.MapX + panel.player.ScreenX &&
                MapY + panel.tileSize > panel.player.MapY - panel.player.ScreenY &&
                MapY - panel.tileSize < panel.player.MapY + panel.player.ScreenY) {
            g2.drawImage(image, ScreenX, ScreenY, panel.tileSize, panel.tileSize, null);
        }
        // If player is around the edge, draw everything
        else if(panel.player.MapX < panel.player.ScreenX ||
                panel.player.MapY < panel.player.ScreenY ||
                rightOffset > panel.MapWidth - panel.player.MapX ||
                bottomOffset > panel.MapHeight - panel.player.MapY) {
            g2.drawImage(image, ScreenX, ScreenY, panel.tileSize, panel.tileSize, null);
        }
    }*/
}
