package at.ac.fhcampuswien.object;

import at.ac.fhcampuswien.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class B_Bathtub_Interact extends GameObject{
    GamePanel panel;
    public B_Bathtub_Interact(GamePanel panel){

        name = "Bathtub_INTERACT";
        ObjectWidth = 42*4+21;       //Size in Pixels
        ObjectHeight = 23*4+21;     //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/B_Bathtub_Interact.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
