package at.ac.fhcampuswien.Object;

import at.ac.fhcampuswien.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class BR_Carpet extends GameObject{
    GamePanel panel;
    public BR_Carpet(GamePanel panel){

        name = "Carpet";
        ObjectWidth = 48*7;      //Size in Pixels
        ObjectHeight = 48*4;     //Size in Pixels

        Area = new Rectangle(0, 0,0, 0);    //If no Collision needed -> Collision Area = 0

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/BR_Carpet.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
