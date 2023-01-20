package at.ac.fhcampuswien.object;

import at.ac.fhcampuswien.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class K_Wall extends GameObject{
    GamePanel panel;
    public K_Wall(GamePanel panel){

        name = "KITCHEN_WALL";
        ObjectWidth = panel.tileSize*16-8*3;       //Size in Pixels
        ObjectHeight = panel.tileSize*6-4*3;     //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/FWO/K_Wall.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
