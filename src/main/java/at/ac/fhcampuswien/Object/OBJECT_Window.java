package at.ac.fhcampuswien.Object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class OBJECT_Window extends GameObject{
    public OBJECT_Window(){

        name = "Bed";
        ObjectWidth = 48*2;         //Size in Pixels
        ObjectHeight = 48*2+10;     //Size in Pixels

        Area = new Rectangle(0, 0, 0, 0);   //If no Collision needed -> Collision Area = 0

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/BR_Window.png")));
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
