package at.ac.fhcampuswien.Object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJECT_Window extends GameObject{
    public OBJECT_Window(){
        name = "Bed";
        ObjectWidth = 48*2;      //Size in Pixels
        ObjectHeight = 48*2+10;     //Size in Pixels
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/BR_Window.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
