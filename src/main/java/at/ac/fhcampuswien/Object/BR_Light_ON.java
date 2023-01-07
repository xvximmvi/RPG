package at.ac.fhcampuswien.Object;

import at.ac.fhcampuswien.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class BR_Light_ON extends GameObject{
    GamePanel panel;
    public BR_Light_ON(GamePanel panel){

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
