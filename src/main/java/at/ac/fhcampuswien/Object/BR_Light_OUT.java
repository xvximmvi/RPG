package at.ac.fhcampuswien.Object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class BR_Light_OUT extends GameObject{
    public BR_Light_OUT(){
        name = "Light_OUT";
        ObjectWidth = 48+7;      //Size in Pixels
        ObjectHeight = 48+20;     //Size in Pixels
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/BR_Light_OUT.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
