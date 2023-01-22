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
    FloatControl floatControl;
    public int volumeScale = 3;
    float volume;

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
            floatControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);    //FloatControl accepts from -80f to 6f (-80 = no sound)
            setVolume();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setVolume(){
        switch(volumeScale){
            case 0: volume = -80f;  break;
            case 1: volume = -20f;  break;
            case 2: volume = -12f;  break;
            case 3: volume = -5f;   break;
            case 4: volume = 1f;    break;
            case 5: volume = 6f;    break;
        }
        floatControl.setValue(volume);
    }
}
