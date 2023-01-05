package at.ac.fhcampuswien.Object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class BR_ToyHorse extends GameObject{
    public BR_ToyHorse(){
        name = "ToyHorse";
        ObjectWidth = 48*2+20;      //Size in Pixels
        ObjectHeight = 2*48+10;     //Size in Pixels
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/BR_ToyHorse.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
