package at.ac.fhcampuswien.object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class BR_Light_ON extends GameObject{

    public BR_Light_ON(){

        name = "Light_ON";
        ObjectWidth = 48+7;     //Size in Pixels
        ObjectHeight = 48+22;   //Size in Pixels

        Area = new Rectangle(0, 0, 0, 0);   //If no Collision needed -> Collision Area = 0

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/BR_Light_ON.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
