package at.ac.fhcampuswien.object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class K_HangingUtilitys extends GameObject {

    public K_HangingUtilitys(){

        name = "HangingUtilitys";
        ObjectWidth = 24*4;       //Size in Pixels
        ObjectHeight = 15*4;     //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/K_HangingUtilitys.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = false;
    }
}
