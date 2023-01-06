package at.ac.fhcampuswien.Object;

import at.ac.fhcampuswien.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

//CLASS CONTENT
/*
    VARIABLES
    DRAW OBJECTS
        - Recalculate Screen-Coordinates
        - Stop moving camera at edge of map
 */

public class GameObject {

    // VARIABLES
    public BufferedImage image;
    public String name;
    public int ObjectWidth;
    public int ObjectHeight;
    public boolean collision = false;
    public int MapX, MapY;
    public Rectangle Area;          //defined in each specific Object Class
    public int AreaDefaultX = 0;
    public int AreaDefaultY = 0;

    // DRAW OBJECT
    public void draw(Graphics2D g2, GamePanel panel) {

        int ScreenX = MapX - panel.player.MapX + panel.player.ScreenX;
        int ScreenY = MapY - panel.player.MapY + panel.player.ScreenY;

        // STOP MOVING CAMERA
        // Left Side
        if (panel.player.MapX < panel.player.ScreenX) ScreenX = MapX;
        if (panel.player.MapY < panel.player.ScreenY) ScreenY = MapY;

        // Right Side
        int RightOffset = panel.ScreenWidth - panel.player.ScreenX;
        if (RightOffset > panel.MapWidth - panel.player.MapX)   ScreenX = panel.ScreenWidth - (panel.MapWidth - MapX);

        int BottomOffset = panel.ScreenHeight - panel.player.ScreenY;
        if (BottomOffset > panel.MapHeight - panel.player.MapY) ScreenY = panel.ScreenHeight - (panel.MapHeight - MapY);

        if (MapX + panel.tileSize > panel.player.MapX - panel.player.ScreenX &&
                MapX - panel.tileSize < panel.player.MapX + panel.player.ScreenX &&
                MapY + panel.tileSize > panel.player.MapY - panel.player.ScreenY &&
                MapY - panel.tileSize < panel.player.MapY + panel.player.ScreenY)
            g2.drawImage(image, ScreenX, ScreenY, ObjectWidth, ObjectHeight, null);

        // If player is around the edge, draw everything
        else if (panel.player.MapX < panel.player.ScreenX ||
                panel.player.MapY < panel.player.ScreenY ||
                RightOffset > panel.MapWidth - panel.player.MapX ||
                BottomOffset > panel.MapHeight - panel.player.MapY)
            g2.drawImage(image, ScreenX, ScreenY, ObjectWidth, ObjectHeight, null);
    }
}
