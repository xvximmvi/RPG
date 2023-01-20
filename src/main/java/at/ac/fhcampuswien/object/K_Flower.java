package at.ac.fhcampuswien.object;

import at.ac.fhcampuswien.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class K_Flower extends GameObject {
    GamePanel panel;

    public K_Flower(GamePanel panel){

        name = "Flower";
        ObjectWidth = 10*5;       //Size in Pixels
        ObjectHeight = 23*5;     //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/K_Flower.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
