package at.ac.fhcampuswien.sound;

import javax.sound.sampled.*;
import java.net.URL;

// CLASS CONTENT
/*
    SOUND FILES
    OPEN FILE
    SOUND PLAY
 */

public class SoundEffect {

    Clip clip ;
    URL[] soundeffect = new URL[10];

    // SOUND FILES
    public SoundEffect(){
        soundeffect[0] = getClass().getResource("/Sound/GameWon.wav");
        soundeffect[1] = getClass().getResource("/Sound/GameOver.wav");
        soundeffect[2] = getClass().getResource("/Sound/Collect.wav");
        soundeffect[3] = getClass().getResource("/Sound/Dialogue_v1_single.wav");
        soundeffect[4] = getClass().getResource("/Sound/Door.wav");
        soundeffect[5] = getClass().getResource("/Sound/Choose.wav");
        soundeffect[6] = getClass().getResource("/Sound/Switch.wav");
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
