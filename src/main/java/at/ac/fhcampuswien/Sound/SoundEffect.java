package at.ac.fhcampuswien.Sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

// CLASS CONTENT
/*
    SOUND FILES
    OPEN FILE
    SOUND PLAY
 */

public class SoundEffect {

    Clip clip ;
    URL soundeffect[] = new URL[10];

    // SOUND FILES
    public SoundEffect(){
        soundeffect[0] = getClass().getResource("/Sound/GameWon.wav");
        soundeffect[1] = getClass().getResource("/Sound/GameOver.wav");
        soundeffect[2] = getClass().getResource("/Sound/Key.wav");
        soundeffect[3] = getClass().getResource("/Sound/Notification.wav");
    }

    // OPEN FILE
    public void play(int i) {
        try{
            //Java format to open sound file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream((soundeffect[i]));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
