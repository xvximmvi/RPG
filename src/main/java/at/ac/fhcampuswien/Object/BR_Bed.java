package at.ac.fhcampuswien.Object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class BR_Bed extends GameObject {
    public BR_Bed(){
        name = "Bed";
        ObjectWidth = 48*2+25;      //Size in Pixels
        ObjectHeight = 48*3+35;     //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/BR_Bed.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
