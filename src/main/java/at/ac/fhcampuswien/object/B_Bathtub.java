package at.ac.fhcampuswien.object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class B_Bathtub extends GameObject {

    public B_Bathtub(){

        name = "Bathtub";
        ObjectWidth = 42*4+21;       //Size in Pixels
        ObjectHeight = 23*4+21;     //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/B_Bathtub.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        utility.scaleImage(image, ObjectWidth, ObjectHeight);

        collision = true;
    }
}