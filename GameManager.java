package com.codetotop.model;

import com.codetotop.gui.MyFrame;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Dung96 on 2/28/2017.
 */
public class GameManager {
    private Image imageBackground = new ImageIcon(getClass().getResource("/com/codetotop/image/background_1_old.png")).getImage();
    private Random random = new Random();
    private ArrayList<Map> arrMap;
    private Clip clip;
    private Bird bird;

    public void initGame() {
        if (clip != null) {
            clip.close();
        }
        clip = AudioManager.getClip("background_music.wav");
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
        arrMap = new ArrayList<Map>();
        readMap("map1.txt");
        bird = new Bird(100, 300);

    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(imageBackground, 0, 0, MyFrame.W_FRAME, MyFrame.H_FRAME, null);
        for (Map map : arrMap) {
            map.draw(g2d);
        }
        bird.draw(g2d);

    }

    private void readMap(String map) {
        File file = new File("src/map/" + map);
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String strLine = bufferedReader.readLine();
            int a[] = new int[strLine.length() + 1];
            while (strLine != null) {
                int x = 0;
                int y = 0;
                int bit;
                for (int i = 1; i < strLine.length(); i++) {
                    char c = strLine.charAt(i);
                    bit = Integer.parseInt(c + "");
                    int yRandom = random.nextInt(250) - 360;
                    if (bit == 1) {
                        x = 62 * i;
                        y = yRandom;
                        a[i] = yRandom;
                    }
                    if (bit == 2) {
                        x = 62* (i + 1);
                        y = a[i + 1] + 540 + 120;
                    }
                    if (bit == 3) {
                        x = 0;
                        y = 620;
                    }
                    Map map1 = new Map(x, y, bit);
                    arrMap.add(map1);
                }
                strLine = bufferedReader.readLine();
            }
            reader.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean birdMove(int action, boolean statusMap) {
        boolean check = bird.move(arrMap, action, statusMap);
        return check;
    }

    public int upPoint(int action, boolean statusMap) {
        bird.move(arrMap, action, statusMap);
        return bird.getPoint();
    }
}
