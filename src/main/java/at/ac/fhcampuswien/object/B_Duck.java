package at.ac.fhcampuswien.object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class B_Duck extends GameObject {

    public B_Duck(){

        name = "Duck";
        ObjectWidth = 11*3+10;       //Size in Pixels
        ObjectHeight = 9*3+10;     //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/B_Duck.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
