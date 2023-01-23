package at.ac.fhcampuswien.object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class C_SideDoor1 extends GameObject{

    public C_SideDoor1(){

        name = "C_SideDoor1";
        ObjectWidth = 4*3;      //Size in Pixels
        ObjectHeight = 5*3;     //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/C_SideDoor1.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = true;

    }
}
