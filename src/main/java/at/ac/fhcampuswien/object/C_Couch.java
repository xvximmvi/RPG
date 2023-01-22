 package at.ac.fhcampuswien.object;

import at.ac.fhcampuswien.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class C_Couch extends GameObject{
    GamePanel panel;
    public C_Couch(GamePanel panel){

        name = "Couch";
        ObjectWidth = 48*2+35;      //Size in Pixels
        ObjectHeight = 48*2+35;     //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/C_Couch.png")));
            utility.scaleImage(image, ObjectWidth, ObjectHeight);
        } catch(IOException e){
            e.printStackTrace();
        }
        collision = true;

    }
}