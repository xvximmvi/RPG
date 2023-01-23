package at.ac.fhcampuswien.object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class OBJECT_SmallPicture1 extends GameObject{

    public OBJECT_SmallPicture1(){

        name = "S_Picture1";
        ObjectWidth = 48*2;      //Size in Pixels
        ObjectHeight = 48+20;     //Size in Pixels

        Area = new Rectangle(0, 0, 0, 0);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/OBJECT_SmallPicture1.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = false;

    }
}
