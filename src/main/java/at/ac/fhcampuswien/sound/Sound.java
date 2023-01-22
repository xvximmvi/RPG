package at.ac.fhcampuswien.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
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
    FloatControl floatControl;
    public int volumeScale = 3;
    float volume;


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
            floatControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);    //FloatControl accepts from -80f to 6f (-80 = no sound)
            setVolume();
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
