package at.ac.fhcampuswien.Object;

import at.ac.fhcampuswien.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class BEDROOM_DOOR extends GameObject{
    GamePanel panel;
    public BEDROOM_DOOR(GamePanel panel){

        name = "Bedroom_Door";
        ObjectWidth = 48*2;      //Size in Pixels
        ObjectHeight = 48*4;     //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/OBJECT_DOOR.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = true;

    }
}