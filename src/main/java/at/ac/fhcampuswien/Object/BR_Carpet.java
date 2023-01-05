package at.ac.fhcampuswien.Object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class BR_Carpet extends GameObject{
    public BR_Carpet(){
        name = "Carpet";
        ObjectWidth = 48*7;      //Size in Pixels
        ObjectHeight = 48*4;     //Size in Pixels
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/BR_Carpet.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
