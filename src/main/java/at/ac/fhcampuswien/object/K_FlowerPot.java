package at.ac.fhcampuswien.object;

import at.ac.fhcampuswien.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class K_FlowerPot extends GameObject {
    GamePanel panel;

    public K_FlowerPot(GamePanel panel){

        name = "FlowerPot";
        ObjectWidth = 8*4;       //Size in Pixels
        ObjectHeight = 11*4;     //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/K_FlowerPot.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = false;
    }
}
