package com.codetotop.model;

import com.codetotop.gui.MyFrame;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Dung96 on 2/28/2017.
 */
public class Bird {
    private int x;
    private int y;
    private int point = 0;
    private int index = 0;
    private int timeChangeImage = 0;
    private Image img;
    private Image arrImg[];
    private Clip clip;


    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
        arrImg = new Image[]{
                new ImageIcon(getClass().getResource("/com/codetotop/image/bird01.png")).getImage(),
                new ImageIcon(getClass().getResource("/com/codetotop/image/bird02.png")).getImage(),
                new ImageIcon(getClass().getResource("/com/codetotop/image/bird03.png")).getImage()
        };
        img = getImage();
    }

    public int getX() {
        return x;
    }

    public int getPoint() {
        return point;
    }

    public Image getImage() {
        index++;
        if (index >= arrImg.length) {
            index = 0;
        }
        return arrImg[index];
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(img, x, y, null);
    }

    public boolean move(ArrayList<Map> arrMap, int action, boolean statusMap) {
        int speed = 2;
        if (timeChangeImage % 6 == 0) {
            img = getImage();
            timeChangeImage = 0;
        }
        timeChangeImage++;
        if (action == 0) {
            y -= speed;
            if (y < 0)
                y = y + speed;

        } else if (action == 1) {
            y += speed;
            if (y >= MyFrame.H_FRAME)
                y = y - speed;
        } else if (action == -1) {
            x -= 0;
            y += 0;
        }
        if (checkPoints(arrMap) == true) {
            clip = AudioManager.getClip("point.wav");
            clip.start();
        }

        boolean check = checkMap(arrMap, statusMap);
        if (check == false) {
            x -= speed;
            y += speed;
            return false;
        }
        return true;
    }


    public boolean checkMap(ArrayList<Map> arrMap, boolean statusMap) {
        for (Map map : arrMap) {
            if (statusMap == true)
                map.move();
            Rectangle rect = getRect().intersection(map.getRect());
            if (rect.isEmpty() == false)
                return false;
        }
        return true;
    }

    private Rectangle getRect() {
        int w = img.getWidth(null) ;
        int h = img.getHeight(null);
        Rectangle rectangle = new Rectangle(x, y, w, h);
        return rectangle;
    }

    public boolean checkPoints(ArrayList<Map> arrMap) {
        for (Map map : arrMap) {
            if (map.getBit() == 1) {
                if (x == map.getX()) {
                    point++;
                    return true;
                }
            }

        }
        return false;


    }

}
