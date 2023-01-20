package at.ac.fhcampuswien.object;

import at.ac.fhcampuswien.main.GamePanel;
import at.ac.fhcampuswien.main.Utility;
import at.ac.fhcampuswien.object.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class B_Sink extends GameObject {
    GamePanel panel;

    public B_Sink(GamePanel panel){

        name = "Sink";
        ObjectWidth = 19*6-10;       //Size in Pixels
        ObjectHeight = 29*6-10;     //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/B_Sink.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
