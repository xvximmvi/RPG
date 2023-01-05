package at.ac.fhcampuswien.Object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class BR_Bookshelf extends GameObject{
    public BR_Bookshelf(){
        name = "Bookshelf";
        ObjectWidth = 48*5+20;      //Size in Pixels
        ObjectHeight = 48*3+20;     //Size in Pixels

        Area = new Rectangle(0, 0, ObjectWidth, ObjectHeight);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/BR_Bookshelf.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;

    }
}
