package at.ac.fhcampuswien.Object;

import at.ac.fhcampuswien.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class BR_ToyHorse extends GameObject{
    GamePanel panel;
    public BR_ToyHorse(GamePanel panel){

        name = "ToyHorse";
        ObjectWidth = 48*2+20;      //Size in Pixels
        ObjectHeight = 2*48+10;     //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/BR_ToyHorse.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
