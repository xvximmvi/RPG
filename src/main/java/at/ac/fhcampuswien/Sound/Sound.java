package at.ac.fhcampuswien.Sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

// CLASS CONTENT
/*
    SOUND FILES
    OPEN FILE
    SOUND PLAY / LOOP / STOP
 */

public class Sound {

    Clip clip;
    URL sound[] = new URL[10];

    // SOUND FILES
    public Sound(){
        sound[0] = getClass().getResource("/Sound/MainMelody_v1.wav");
        sound[1] = getClass().getResource("/Sound/MainMelody_v2.wav");
    }

    // OPEN FILE
    public void setFile(int i) {
        try{
            //Java format to open sound file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream((sound[i]));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // SOUND PLAY / LOOP / STOP
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
