package at.ac.fhcampuswien.Object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJECT_SideOutline extends GameObject{
    public OBJECT_SideOutline(){
        name = "SideOutline";
        ObjectWidth = 4;      //Size in Pixels
        ObjectHeight = 98;     //Size in Pixels
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/OBJECT_SideOutline.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
