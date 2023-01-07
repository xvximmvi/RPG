package at.ac.fhcampuswien.main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Utility {
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());     //instantiate BufferedImage
        Graphics2D graphics2D = scaledImage.createGraphics();       //create a Graphics2D, which can be used to draw into this BufferedImage
        graphics2D.drawImage(original, 0, 0, width, height, null);    //Draw tile80].image into the scaledImage (BufferedImage) that this Graphics2D is linked to

        graphics2D.dispose();

        return scaledImage;
    }
}
