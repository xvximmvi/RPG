package at.ac.fhcampuswien.sound;

import javax.sound.sampled.*;
import java.net.URL;

// CLASS CONTENT
/*
    SOUND FILES
    OPEN FILE
        - Open sound file
        - define Volume
    SOUND PLAY / LOOP / STOP
    SET VOLUME
        - choose from -80f (no Sound) to 6f
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
        soundeffect[3] = getClass().getResource("/Sound/Dialogue_v1_multi.wav");
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
        switch (volumeScale) {
            //play with variables till changes seem same
            case 0 -> volume = -80f;
            case 1 -> volume = -20f;
            case 2 -> volume = -12f;
            case 3 -> volume = -5f;
            case 4 -> volume = 1f;
            case 5 -> volume = 6f;
        }
        floatControl.setValue(volume);  //set new Volume
    }
}
