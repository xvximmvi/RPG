package at.ac.fhcampuswien.object;

import at.ac.fhcampuswien.main.GamePanel;
import at.ac.fhcampuswien.main.Utility;
import at.ac.fhcampuswien.object.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class B_Toilet extends GameObject {
    GamePanel panel;

    public B_Toilet(GamePanel panel){

        name = "Toilet";
        ObjectWidth = 19*4+30;       //Size in Pixels
        ObjectHeight = 29*4+30;     //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/B_Toilet.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
