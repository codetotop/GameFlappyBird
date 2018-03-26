package com.codetotop.gui;

import com.codetotop.model.AudioManager;
import com.codetotop.model.GameManager;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.BitSet;

/**
 * Created by Dung96 on 2/28/2017.
 */
public class
MyPanel extends JPanel implements KeyListener {
    private JButton btnPause;
    private JLabel lbPlay, lbPause, lbPoint, lbhighscore,lbCreater;
    private JPanel panelGameOver;
    private JLabel lbGameOver, lbQuestion;
    private JButton btnYes, btnNo;
    private GameManager gameManager = new GameManager();
    private BitSet bitSet = new BitSet(256);//mang cac dieu khien ban phim
    private Clip clip;
    private int point = 0;
    private int statusPlay = 0;
    private boolean statusMap = false;
    private int a = -1;
    private int highScore;

    public MyPanel() {
        setLayout(null);
        gameManager.initGame();
        setFocusable(true);
        addKeyListener(this);
        readHighScore("src/map/highscore.txt");
        addComponent();
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void addComponent() {
        ImageIcon iconPause = new ImageIcon("src/com/codetotop/image/pause.png");
        ImageIcon iconSpeaker = new ImageIcon("src/com/codetotop/image/speaker.png");
        ImageIcon iconDeveloper = new ImageIcon("src/com/codetotop/image/idea.png");

        btnPause = new JButton(iconPause);
        btnPause.setBounds(0, 0, iconPause.getIconWidth(), iconPause.getIconHeight());
        btnPause.setBackground(Color.decode("#0096FF"));
        btnPause.addActionListener(action);

        lbPoint = new JLabel("0");
        lbPoint.setVerticalAlignment(SwingConstants.CENTER);
        lbPoint.setBounds(MyFrame.W_FRAME / 2 - 20, 0, 200, 40);
        lbPoint.setBackground(Color.decode("#0096FF"));
        lbPoint.setFont(new Font("Courier New", Font.BOLD, 40));
        lbPoint.setForeground(Color.RED);

        lbhighscore = new JLabel("High Score: ");
        lbPoint.setVerticalAlignment(SwingConstants.CENTER);
        lbhighscore.setBounds(MyFrame.W_FRAME / 2 - 156, 620, 360, 40);
        lbhighscore.setBackground(Color.decode("#0096FF"));
        lbhighscore.setFont(new Font("Courier New", Font.BOLD, 40));
        lbhighscore.setForeground(Color.RED);

        lbPlay = new JLabel("Press space to play ");
        lbPlay.setBounds(0, 300, 400, 40);
        lbPlay.setBackground(Color.decode("#0096FF"));
        lbPlay.setFont(new Font("Courier New", Font.BOLD, 26));
        lbPlay.setForeground(Color.RED);

        lbPause = new JLabel("Press 'P' to pause or continue");
        lbPause.setBounds(0, 680, 480, 40);
        lbPause.setBackground(Color.decode("#0096FF"));
        lbPause.setFont(new Font("Courier New", Font.BOLD, 26));
        lbPause.setForeground(Color.RED);

        lbCreater = new JLabel("Create by Nguyen Ba Dung");
        lbCreater.setBounds(708, 680, 400, 40);
        lbCreater.setBackground(Color.decode("#0096FF"));
        lbCreater.setFont(new Font("Courier New", Font.BOLD, 26));
        lbCreater.setForeground(Color.RED);

        panelGameOver = new JPanel();
        panelGameOver.setOpaque(false);
        panelGameOver.setLayout(null);
        panelGameOver.setBounds(MyFrame.W_FRAME / 2 - 200, MyFrame.H_FRAME / 2 - 150, 400, 180);

        lbGameOver = new JLabel("GAME OVER");
        lbGameOver.setForeground(Color.RED);
        lbGameOver.setFont(new Font("Courier New", Font.BOLD, 40));
        lbGameOver.setBounds(92, 10, 300, 60);

        lbQuestion = new JLabel("You want to play again?");
        lbQuestion.setForeground(Color.RED);
        lbQuestion.setFont(new Font("Courier New", Font.BOLD, 26));
        lbQuestion.setBounds(16, 76, 400, 40);

        btnYes = new JButton("Yes");
        btnYes.setForeground(Color.RED);
        btnYes.setOpaque(false);//lam trong suot button
        btnYes.setBackground(Color.decode("#0096FF"));
        btnYes.setFont(new Font("Courier New", Font.BOLD, 26));
        btnYes.setBounds(66, 136, 82, 36);

        btnNo = new JButton("No");
        btnNo.setForeground(Color.RED);
        btnNo.setOpaque(false);//lam trong suot button
        btnNo.setBackground(Color.decode("#0096FF"));
        btnNo.setFont(new Font("Courier New", Font.BOLD, 26));
        btnNo.setBounds(252, 136, 82, 36);


        add(lbPlay);
        add(btnPause);
        add(lbPoint);
        add(lbhighscore);
        add(lbPause);
        add(lbCreater);
        add(panelGameOver);
        panelGameOver.hide();

        panelGameOver.add(lbGameOver);
        panelGameOver.add(lbQuestion);
        panelGameOver.add(btnYes);
        panelGameOver.add(btnNo);
        btnYes.addActionListener(action);
        btnNo.addActionListener(action);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        gameManager.draw(g2d);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        bitSet.set(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            clip = AudioManager.getClip("flap.wav");
            clip.start();
            btnPause.setFocusable(false);
            lbPlay.hide();
            statusPlay = 1;
            btnPause.setIcon(new ImageIcon("src/com/codetotop/image/play.png"));
        }
        if (e.getKeyCode() == KeyEvent.VK_P) {
            if (statusPlay == 1) {
                btnPause.setIcon(new ImageIcon("src/com/codetotop/image/pause.png"));
                statusPlay = 0;
                statusMap = false;
                a = -1;
            } else if (statusPlay == 0) {
                btnPause.setIcon(new ImageIcon("src/com/codetotop/image/play.png"));
                statusPlay = 1;
                statusMap = true;
                a = 1;
            }
        }
        repaint();//ve lai giao dien khi nhan vat di chuyen
    }

    @Override
    public void keyReleased(KeyEvent e) {
        bitSet.clear();

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                point = gameManager.upPoint(a, statusMap);
                if (point > highScore) {
                    highScore = point;
                    writeHighScore("src/map/highscore.txt");
                }
                lbPoint.setText("" + point);
                lbhighscore.setText("High Score:" + highScore);
                if (bitSet.get(KeyEvent.VK_SPACE) == true) {
                    a = 0;
                    statusMap = true;
                    gameManager.birdMove(a, statusMap);
                } else {
                    if (a == -1) {
                        statusMap = false;
                        gameManager.birdMove(a, statusMap);
                    } else if (bitSet.isEmpty()) {
                        a = 1;
                        statusMap = true;
                        gameManager.birdMove(a, statusMap);
                    }
                }
                boolean check = gameManager.birdMove(a, statusMap);

                if (check == false) {
                    Clip clip = AudioManager.getClip("die.wav");
                    clip.start();
                    panelGameOver.show();
                    repaint();
                    return;
                }
                repaint();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    };

    private void writeHighScore(String str) {
        File file = new File(str);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(highScore);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readHighScore(String str) {
        File file = new File(str);
        try {
            FileInputStream inputStream = new FileInputStream(file);
            highScore = inputStream.read();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ActionListener action = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnPause) {
                if (statusPlay == 1) {
                    btnPause.setIcon(new ImageIcon("src/com/codetotop/image/pause.png"));
                    statusPlay = 0;
                    statusMap = false;
                    a = -1;
                } else if (statusPlay == 0) {
                    btnPause.setIcon(new ImageIcon("src/com/codetotop/image/play.png"));
                    statusPlay = 1;
                    statusMap = true;
                    a = 1;
                }

            } else if (e.getSource() == btnYes) {
                a = -1;
                clip.close();
                statusMap = false;
                lbPlay.show();
                bitSet.clear();
                gameManager.initGame();
                repaint();
                panelGameOver.hide();
                Thread thread = new Thread(runnable);
                thread.start();
            } else if (e.getSource() == btnNo) {
                System.exit(0);
            }

            btnPause.setFocusable(false);

        }
    };

}
