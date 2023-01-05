package at.ac.fhcampuswien.Object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJECT_BottomOutline extends GameObject{
    public OBJECT_BottomOutline(){
        name = "BottomOutline";
        ObjectWidth = 288;      //Size in Pixels
        ObjectHeight = 4;     //Size in Pixels
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/OBJECT_BottomOutline.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;

    }
}
