package at.ac.fhcampuswien.Object;

import at.ac.fhcampuswien.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class OBJECT_Key extends GameObject{
    GamePanel panel;
    public OBJECT_Key(GamePanel panel){

        name = "Key";
        ObjectWidth = 24*2;      //Size in Pixels
        ObjectHeight = 13*2;     //Size in Pixels

        Area = new Rectangle(0, 0, 0, 0);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/OBJECT_Key.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }

    }
}
