package at.ac.fhcampuswien.object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class OBJECT_Tool extends GameObject{

    public OBJECT_Tool(){

        name = "Tool";
        ObjectWidth = 7*2;      //Size in Pixels
        ObjectHeight = 28*2;     //Size in Pixels

        Area = new Rectangle(0, 0, 0, 0);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/OBJECT_Tool.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }

    }
}
