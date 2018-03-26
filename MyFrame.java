package com.codetotop.gui;

import javax.swing.*;

/**
 * Created by Dung96 on 2/28/2017.
 */
public class MyFrame extends JFrame {
    public static int W_FRAME =1100;
    public static int H_FRAME =740;
    private MyPanel panel = new MyPanel();
    public MyFrame(){
        setTitle("Flappy Bird");
        setIconImage(new ImageIcon(getClass().getResource("/com/codetotop/image/bird01.png")).getImage());
        setSize(W_FRAME,H_FRAME);//phải ở trước setLocation
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panel);
        setVisible(true);
    }


}
