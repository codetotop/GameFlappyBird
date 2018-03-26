package com.codetotop.model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Created by Dung96 on 2/28/2017.
 */
public class AudioManager {
    public static Clip getClip(String name) {
        File file = new File("src/com/codetotop/sound/" + name);
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
