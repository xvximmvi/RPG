package at.ac.fhcampuswien.object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class C_Lamp_ON extends GameObject{

    public C_Lamp_ON(){

        name = "LampOn";
        ObjectWidth = 48+20;      //Size in Pixels
        ObjectHeight = 48*4;     //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/C_Lamp_ON.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = true;

    }
}
