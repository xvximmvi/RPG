package at.ac.fhcampuswien.Object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class OBJECT_BottomDoor extends GameObject{
    public OBJECT_BottomDoor(){

        name = "BottomDoor";
        ObjectWidth = 3*48+30;      //Size in Pixels
        ObjectHeight = 4;           //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/OBJECT_BottomDoor.png")));
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
