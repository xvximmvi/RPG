package at.ac.fhcampuswien.entity;

import java.awt.image.BufferedImage;

//This stores variables that will be used in players, monsters and NPCs
public class Entity {
    public int playerX, playerY;
    public int Speed;

    //Image with an accessible buffer of image data (to store our image files)
    public BufferedImage UP1, UP2, UP3, DOWN1, DOWN2, DOWN3, LEFT1, LEFT2, LEFT3, RIGHT1, RIGHT2, RIGHT3;
    public String direction;        //direction of character moving

    public int spriteCounter = 0;   //count up to change position
    public int spriteNum = 1;       //number of sprite
}
