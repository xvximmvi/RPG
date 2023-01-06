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
    URL soundURL[] = new URL[30];

    // SOUND FILES
    public Sound(){
        soundURL[0] = getClass().getResource("/Sound/MainMelody_v1.wav");
        soundURL[1] = getClass().getResource("/Sound/MainMelody_v2.wav");
        soundURL[2] = getClass().getResource("/Sound/GameWon.wav");
        soundURL[3] = getClass().getResource("/Sound/GameOver.wav");
        soundURL[4] = getClass().getResource("/Sound/Key.wav");
        soundURL[5] = getClass().getResource("/Sound/Notification.wav");
    }

    // OPEN FILE
    public void setFile(int i) {
        try{
            //Java format to open sound file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream((soundURL[i]));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        }catch (Exception e){}
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
