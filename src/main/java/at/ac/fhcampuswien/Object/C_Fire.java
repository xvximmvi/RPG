package at.ac.fhcampuswien.Object;

import at.ac.fhcampuswien.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class C_Fire extends GameObject{
    GamePanel panel;
    public C_Fire(GamePanel panel){

        name = "Fire";
        ObjectWidth = 48*3;      //Size in Pixels
        ObjectHeight = 48*7+30;     //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/C_Fire.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = true;

    }
}
