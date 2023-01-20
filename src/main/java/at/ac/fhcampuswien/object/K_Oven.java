package at.ac.fhcampuswien.object;

import at.ac.fhcampuswien.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class K_Oven extends GameObject {
    GamePanel panel;

    public K_Oven(GamePanel panel){

        name = "Oven";
        ObjectWidth = 30*4;       //Size in Pixels
        ObjectHeight = 43*4;     //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/K_Oven.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}