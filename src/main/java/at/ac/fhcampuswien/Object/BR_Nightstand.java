package at.ac.fhcampuswien.Object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class BR_Nightstand extends GameObject{
    public BR_Nightstand(){
        name = "Nightstand";
        ObjectWidth = 48*2+20;      //Size in Pixels
        ObjectHeight = 48*2+20;     //Size in Pixels
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/BR_Nightstand.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;

    }
}
