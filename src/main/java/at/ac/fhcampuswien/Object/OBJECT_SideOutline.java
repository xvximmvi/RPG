package at.ac.fhcampuswien.Object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class OBJECT_SideOutline extends GameObject{
    public OBJECT_SideOutline(){
        name = "SideOutline";
        ObjectWidth = 4;      //Size in Pixels
        ObjectHeight = 10*48;     //Size in Pixels

       Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/OBJECT_SideOutline.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
