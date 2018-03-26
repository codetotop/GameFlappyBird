package com.codetotop.model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Dung96 on 2/28/2017.
 */
public class Map {
    private int x;
    private int y;
    private int bit;
    int a=0;
    ArrayList<Map> arrMap = new ArrayList<Map>();
    Random random = new Random();
    private Image arrImg[] = {
            new ImageIcon(getClass().getResource("/com/codetotop/image/OngTren1_old.png")).getImage(),
            new ImageIcon(getClass().getResource("/com/codetotop/image/OngDuoi1_old.png")).getImage(),
            new ImageIcon(getClass().getResource("/com/codetotop/image/Vien1.png")).getImage()
    };

    public Map(int x, int y, int bit) {
        this.x = x;
        this.y = y;
        this.bit = bit;
    }

    public int getBit() {
        return bit;
    }

    public int getX() {
        return x;
    }

    void draw(Graphics2D g2d) {
        if (bit > 0)
            g2d.drawImage(arrImg[bit - 1], x, y, null);
    }

    public void move() {
        int speed = 1;
        if (bit == 1 || bit == 2) {
            if (x < -64) {
                //x = 2200;
                x=2720;
            }
            x -= speed;
        }
        getRect();

    }

    //bao bọc các map thành các hình chữ nhật
    public Rectangle getRect() {
        if (bit == 0) {
            return new Rectangle();
        }
        /*int w = arrImg[bit - 1].getWidth(null) - 15;
        int h = arrImg[bit - 1].getHeight(null) - 20;*/
        int w = arrImg[bit - 1].getWidth(null) -5;
        int h = arrImg[bit - 1].getHeight(null) -8;
        Rectangle rect = new Rectangle(x+4, y, w, h);
        return rect;
    }


}
