package at.ac.fhcampuswien.object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class OBJECT_SmallPicture2 extends GameObject{

    public OBJECT_SmallPicture2(){

        name = "S_Picture2";
        ObjectWidth = 48+20;      //Size in Pixels
        ObjectHeight = 48+30;     //Size in Pixels

        Area = new Rectangle(0, 0, 0, 0);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/OBJECT_SmallPicture2.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = false;

    }
}
