import com.ethanzeigler.jgamegui.audio.AudioClip;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class TowerDefense extends JFrame implements Runnable, KeyListener, MouseListener {

    ////////////////X is shifted to the right by 4
    ////////////////Y is shifted down by 50
    ArrayList<Tower> towers = new ArrayList<Tower>();
    ArrayList<Enemies> enemies = new ArrayList<Enemies>();
    ArrayList<Enemies> currentEnemies = new ArrayList<Enemies>();
    ArrayList<text> words = new ArrayList<text>();
    ArrayList<Bullets> bullets = new ArrayList<Bullets>();
    PointerInfo info = MouseInfo.getPointerInfo();
    AudioClip sounds;
    Point point = info.getLocation();
    int mouseX = (int) point.getX();
    int mouseY = (int) point.getY();
    Scanner inputStream = null;
    PrintWriter outputStream = null;
    Image test, tile, offscreen;
    Graphics buffer;
    Thread animate = null;
    boolean shadow = false, sougata = false, zeel = false, rob = false, herrick = false, evan = false, mike = false, tyler = false, logAdd = false;
    boolean sougataC = false, zeelC = false, robC = false, herrickC = false, evanC = false, mikeC = false, tylerC = false, select = false, showHealth = false;
    boolean herrickU = false, evanU = false, mikeU = false, tylerU = false, interestAdd = false, sold = false, gameOver = false;
    int logs = 0, coins = 40, score = 40, lives = 20, level = 0, interest = 10, currentLevel = -1, textIndex = 0, moreMoney = 0, circleBlink = 0, highScore = 40;

    public static void main(String[] args) {
        TowerDefense game = new TowerDefense();
        new Thread(() -> {
            game.init();
            game.start();
            game.setVisible(true);
            game.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }).start();
    }

    public void start() {
        if (animate == null) {
            animate = new Thread(this);
        }
        animate.start();
    }


    public void run() {
        while (Thread.currentThread() == animate) {
            repaint();
            try {
                Thread.sleep(1);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        repaint();
    }

    public void init() {
        addKeyListener(this);
        addMouseListener(this);
        setBackground(Color.blue);
        resize(650, 450);
        enemies.add(new Keyboard(1, 1, "none"));//1
        enemies.get(0).setSize(0);
        enemies.add(new Keyboard(10, 1, "none"));//1
        enemies.get(1).setSize(20);
        enemies.add(new Monitor(42, 1, "none"));//1
        enemies.get(2).setSize(20);
        enemies.add(new Keyboard(65, 1, "none"));//1
        enemies.get(3).setSize(20);
        enemies.add(new Mouse(75, 1, "none"));//1
        enemies.get(4).setSize(20);
        enemies.add(new Floppy(101, 1, "none"));//1
        enemies.get(5).setSize(20);
        enemies.add(new Mouse(87, 1, "fast"));//1, fast
        enemies.get(6).setSize(20);
        enemies.add(new Floppy(136, 1, "none"));//1
        enemies.get(7).setSize(20);
        enemies.add(new Keyboard(101, 1, "none"));//1, log after
        enemies.get(8).setSize(20);
        enemies.add(new Floppy(158, 1, "air"));//1, air
        enemies.get(9).setSize(20);
        enemies.add(new Keyboard(189, 1, "none"));//1
        enemies.get(10).setSize(20);
        enemies.add(new Monitor(212, 2, "immune"));//2, immune
        enemies.get(11).setSize(20);
        enemies.add(new Ulmer(2000, 45, "boss"));//45, boss
        enemies.get(12).setSize(1);
        enemies.add(new Floppy(246, 2, "none"));//2
        enemies.get(13).setSize(20);
        enemies.add(new Keyboard(331, 2, "none"));//2, log after
        enemies.get(14).setSize(20);
        enemies.add(new Floppy(384, 2, "none"));//2
        enemies.get(15).setSize(20);
        enemies.add(new Mouse(445, 2, "none"));//2
        enemies.get(16).setSize(20);
        enemies.add(new Floppy(580, 2, "air"));//2, air
        enemies.get(17).setSize(20);
        enemies.add(new Keyboard(695, 2, "none"));//2
        enemies.get(18).setSize(20);
        enemies.add(new Mouse(599, 2, "fast"));//2, fast
        enemies.get(19).setSize(20);
        enemies.add(new Floppy(806, 3, "none"));//3
        enemies.get(20).setSize(20);
        enemies.add(new Monitor(1125, 3, "immune"));//2, immune, wood after
        enemies.get(21).setSize(20);
        enemies.add(new Ulmer(14000, 55, "boss"));//55, boss
        enemies.get(22).setSize(1);
        enemies.add(new Mouse(1075, 3, "none"));//3
        enemies.get(23).setSize(20);
        enemies.add(new Floppy(1265, 4, "none"));//4
        enemies.get(24).setSize(20);
        enemies.add(new Keyboard(1468, 4, "none"));//4
        enemies.get(25).setSize(20);
        enemies.add(new Mouse(1256, 4, "fast"));//4, fast
        enemies.get(26).setSize(20);
        enemies.add(new Floppy(1615, 4, "air"));//4, air
        enemies.get(27).setSize(20);
        enemies.add(new Keyboard(1935, 4, "none"));//4, wood after
        enemies.get(28).setSize(20);
        enemies.add(new Floppy(2165, 5, "none"));//5
        enemies.get(29).setSize(20);
        enemies.add(new Keyboard(2005, 5, "none"));//5
        enemies.get(30).setSize(20);
        enemies.add(new Floppy(2655, 5, "none"));//5
        enemies.get(31).setSize(20);
        enemies.add(new Monitor(2500, 2, "immune/fast"));//2, immune, fast
        enemies.get(32).setSize(20);
        enemies.add(new Ulmer(35000, 100, "boss"));//100, boss
        enemies.get(33).setSize(1);
        enemies.add(new Floppy(5000, 5, "none"));//5
        enemies.get(34).setSize(20);
        enemies.add(new Keyboard(7000, 10, "none"));//10, 40 people, wood after
        enemies.get(35).setSize(40);
        enemies.add(new Floppy(10000, 15, "none"));//15
        enemies.get(36).setSize(40);
        enemies.add(new Keyboard(15000, 20, "none"));//20
        enemies.get(37).setSize(40);
        enemies.add(new Mouse(20000, 25, "none"));//25
        enemies.get(38).setSize(40);
        enemies.add(new Floppy(25001, 30, "none"));//30
        enemies.get(39).setSize(40);
        words.add(new text("Welcome to the greatest game in the world."));
        for (int i = 1; i < 40; i++) {
            String add = "";
            Enemies test = enemies.get(i);
            add += "Level " + i + " ";
            if (test instanceof Keyboard) {
                add += "Keyboard";
            } else if (test instanceof Floppy) {
                add += "Floppy";
            } else if (test instanceof Mouse) {
                add += "Mouse";
            } else if (test instanceof Monitor) {
                add += "Monitor";
            } else if (test instanceof Ulmer) {
                add += "Ulmer";
            }
            if (test.getType().equals("air"))
                add += " [AIR]";
            else if (test.getType().equals("fast"))
                add += " [FAST]";
            else if (test.getType().equals("boss"))
                add += " [BOSS]";
            else if (test.getType().equals("immune"))
                add += " [IMMUNE]";
            if (test.getType().equals("immune/fast"))
                add += " [IMMUNE][FAST]";
            add += ": ";
            add += test.getHP() + "hp, ";
            add += test.getEarn() + "gold each";
            words.add(new text(add));
        }
        highScore = getHighScore();
        currentLevel = enemies.get(level).getSize();
        offscreen = new BufferedImage(650, 450, BufferedImage.TYPE_INT_ARGB);
        System.out.println(offscreen);
        buffer = offscreen.getGraphics();
        repaint();
    }

    public void reset() {
        enemies.add(new Keyboard(1, 1, "none"));//1
        enemies.get(0).setSize(0);
        enemies.add(new Keyboard(10, 1, "none"));//1
        enemies.get(1).setSize(20);
        enemies.add(new Monitor(42, 1, "none"));//1
        enemies.get(2).setSize(20);
        enemies.add(new Keyboard(65, 1, "none"));//1
        enemies.get(3).setSize(20);
        enemies.add(new Mouse(75, 1, "none"));//1
        enemies.get(4).setSize(20);
        enemies.add(new Floppy(101, 1, "none"));//1
        enemies.get(5).setSize(20);
        enemies.add(new Mouse(87, 1, "none"));//1, fast
        enemies.get(6).setSize(20);
        enemies.add(new Floppy(136, 1, "none"));//1
        enemies.get(7).setSize(20);
        enemies.add(new Keyboard(101, 1, "none"));//1, log after
        enemies.get(8).setSize(20);
        enemies.add(new Floppy(158, 1, "air"));//1, air
        enemies.get(9).setSize(20);
        enemies.add(new Keyboard(189, 1, "none"));//1
        enemies.get(10).setSize(20);
        enemies.add(new Monitor(212, 2, "immune"));//2, immune
        enemies.get(11).setSize(20);
        enemies.add(new Ulmer(2000, 45, "boss"));//45, boss
        enemies.get(12).setSize(1);
        enemies.add(new Floppy(246, 2, "none"));//2
        enemies.get(13).setSize(20);
        enemies.add(new Keyboard(331, 2, "none"));//2, log after
        enemies.get(14).setSize(20);
        enemies.add(new Floppy(384, 2, "none"));//2
        enemies.get(15).setSize(20);
        enemies.add(new Mouse(445, 2, "none"));//2
        enemies.get(16).setSize(20);
        enemies.add(new Floppy(580, 2, "air"));//2, air
        enemies.get(17).setSize(20);
        enemies.add(new Keyboard(695, 2, "none"));//2
        enemies.get(18).setSize(20);
        enemies.add(new Mouse(599, 2, "fast"));//2, fast
        enemies.get(19).setSize(20);
        enemies.add(new Floppy(806, 3, "none"));//3
        enemies.get(20).setSize(20);
        enemies.add(new Monitor(1125, 3, "immune"));//2, immune, wood after
        enemies.get(21).setSize(20);
        enemies.add(new Ulmer(14000, 55, "boss"));//55, boss
        enemies.get(22).setSize(1);
        enemies.add(new Mouse(1075, 3, "none"));//3
        enemies.get(23).setSize(20);
        enemies.add(new Floppy(1265, 4, "none"));//4
        enemies.get(24).setSize(20);
        enemies.add(new Keyboard(1468, 4, "none"));//4
        enemies.get(25).setSize(20);
        enemies.add(new Mouse(1256, 4, "fast"));//4, fast
        enemies.get(26).setSize(20);
        enemies.add(new Floppy(1615, 4, "air"));//4, air
        enemies.get(27).setSize(20);
        enemies.add(new Keyboard(1935, 4, "none"));//4, wood after
        enemies.get(28).setSize(20);
        enemies.add(new Floppy(2165, 5, "none"));//5
        enemies.get(29).setSize(20);
        enemies.add(new Keyboard(2005, 5, "none"));//5
        enemies.get(30).setSize(20);
        enemies.add(new Floppy(2655, 5, "none"));//5
        enemies.get(31).setSize(20);
        enemies.add(new Monitor(2500, 2, "immune/fast"));//2, immune, fast
        enemies.get(32).setSize(20);
        enemies.add(new Ulmer(35000, 100, "boss"));//100, boss
        enemies.get(33).setSize(1);
        enemies.add(new Floppy(5000, 5, "none"));//5
        enemies.get(34).setSize(20);
        enemies.add(new Keyboard(7000, 10, "none"));//10, 40 people, wood after
        enemies.get(35).setSize(40);
        enemies.add(new Floppy(10000, 15, "none"));//15
        enemies.get(36).setSize(40);
        enemies.add(new Keyboard(15000, 20, "none"));//20
        enemies.get(37).setSize(40);
        enemies.add(new Mouse(20000, 25, "none"));//25
        enemies.get(38).setSize(40);
        enemies.add(new Floppy(25001, 30, "none"));//30
        enemies.get(39).setSize(40);

        highScore = getHighScore();
        currentLevel = enemies.get(level).getSize();
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        if (!gameOver) {
            sold = false;
            if (herrickU && evanU && mikeU)
                tylerU = true;
            info = MouseInfo.getPointerInfo();
            Point windowPoint = this.getLocationOnScreen();
            point = info.getLocation();
            mouseX = (int) ((int) point.getX() - windowPoint.getX());
            mouseY = (int) ((int) point.getY() - windowPoint.getY());
            buffer.clearRect(0, 0, 650, 450);
            tile = getImage("background.GIF");
            buffer.drawImage(tile, 0, 0, this);
            buffer.setFont(new Font("Serif", Font.PLAIN, 12));
            buffer.drawString("By: Tyler Jung", 575, 25);
            drawTowers();
            tile = getImage("circleCover.GIF");
            buffer.drawImage(tile, 349, 1, this);
            tile = getImage("circleCover2.GIF");
            buffer.drawImage(tile, 0, 0, this);
            tile = getImage("circleCover3.GIF");
            buffer.drawImage(tile, 0, 384, this);
            drawScore();

            checkLogs();
            checkCoins();

            if (!select)
                showInfo();
            else
                showUpgrade();

            if (shadow && (mouseX >= 19 && mouseX <= 340) && (mouseY >= 40 && mouseY <= 374)) {
                if (onMap()) {
                    tile = getImage("shadow.GIF");
                } else {
                    tile = getImage("redShadow.GIF");
                }
                drawRange();
            }
            for (int i = 0; i < 4; i++) {
                buffer.drawString(words.get(textIndex).toString(), 12, 407);
                if (textIndex - 1 >= 0)
                    buffer.drawString(words.get(textIndex - 1).toString(), 12, 419);
                if (textIndex - 2 >= 0)
                    buffer.drawString(words.get(textIndex - 2).toString(), 12, 431);
                if (textIndex - 3 >= 0)
                    buffer.drawString(words.get(textIndex - 3).toString(), 12, 443);
            }
            if (currentLevel > 0) {
                tile = getImage("nextLevelDark.GIF");
                buffer.drawImage(tile, 288, 395, this);
            }

            if (currentLevel <= 0 && level > 0) {
                if (interestAdd == false) {
                    double increase;
                    String add = "";
                    increase = (double) coins * (double) interest / 100;
                    if (increase - (int) increase >= .5)
                        increase++;
                    else
                        increase = (int) increase;
                    moreMoney = (int) (coins + increase);
                    add += "Interest: " + coins + " + " + interest + "% interest = " + moreMoney;
                    coins = moreMoney;
                    if (level <= 7)
                        words.add(level * 2, new text(add));
                    else if (level > 7 && level <= 14)
                        words.add((level * 2) + 1, new text(add));
                    else if (level > 14 && level <= 21)
                        words.add((level * 2) + 2, new text(add));
                    else if (level > 21 && level <= 28)
                        words.add((level * 2) + 3, new text(add));
                    else if (level > 28 && level <= 35)
                        words.add((level * 2) + 4, new text(add));
                    else if (level > 35)
                        words.add((level * 2) + 5, new text(add));

                    if (level % 7 == 0) {
                        textIndex++;
                        words.add(textIndex, new text("You earned 1 log."));

                    }
                    textIndex++;
                    interestAdd = true;
                }
                tile = getImage("nextLevel.GIF");
                buffer.drawImage(tile, 288, 395, this);
            }
            move();
            shooting();

            for (int i = 0; i < bullets.size(); i++) {
                Bullets bullet = null;
                Enemies target = null;
                bullet = bullets.get(i);
                int xMove = 0;
                int yMove = 0;
                target = bullet.getTarget();
                double xChange = target.getX() - bullet.getX();
                double yChange = bullet.getY() - target.getY();
                if (xChange == 0 && yChange < 0) {
                    xMove = 0;
                    yMove = 5;
                } else if (xChange == 0 && yChange > 0) {
                    xMove = 0;
                    yMove = -5;
                } else if (yChange == 0 && xChange > 0) {
                    xMove = 5;
                    yMove = 0;
                } else if (yChange == 0 && xChange < 0) {
                    xMove = -5;
                    yMove = 0;
                } else {
                    double tot = Math.sqrt((xChange * xChange) + (yChange * yChange));
                    xMove = (int) (5 * (xChange / tot));
                    yMove = -(int) (5 * (yChange / tot));
                }
                if (bullet.getTarget().getX() == -100 && bullet.getTarget().getY() == -100) {
                    bullets.remove(bullet);
                } else if ((bullet.getX() > target.getX() - 5 && bullet.getX() < target.getX() + 35) && (bullet.getY() > target.getY() - 5 && bullet.getY() < target.getY() + 25)) {
                    if (bullet.getTower() instanceof Herrick && !target.getType().equals("immune")) {
                        target.slow();
                    }
                    target.hit(bullet.getDamage());
                    int currentIndex = currentEnemies.indexOf(bullet.getTarget());
                    if (currentIndex + 1 < currentEnemies.size()) {
                        currentEnemies.get(currentIndex + 1).hit(bullet.getTower().getSplash1());
                        if (bullet.getTower().getSplash1() != 0 && bullet.getTower() instanceof Herrick && !currentEnemies.get(currentIndex + 1).equals("immune")) {
                            currentEnemies.get(currentIndex + 1).slow();
                        }
                        if (currentEnemies.get(currentIndex + 1).status()) {
                            currentEnemies.get(currentIndex + 1).setX(-100);
                            currentEnemies.get(currentIndex + 1).setY(-100);
                            if (!currentEnemies.get(currentIndex + 1).checkAdd()) {
                                coins += currentEnemies.get(currentIndex + 1).getEarn();
                                currentLevel--;
                                if (currentLevel == 0)
                                    logAdd = true;
                                score += currentEnemies.get(currentIndex + 1).getEarn();
                                currentEnemies.get(currentIndex + 1).scoreAdded();

                            }
                            for (int o = 0; o < bullets.size(); o++) {
                                Bullets temp = bullets.get(o);
                                if (temp.getTarget() == currentEnemies.get(currentIndex + 1)) {
                                    bullets.remove(temp);
                                }
                            }
                        }

                    }
                    if (currentIndex + 2 < currentEnemies.size()) {
                        currentEnemies.get(currentIndex + 2).hit(bullet.getTower().getSplash2());
                        if (bullet.getTower().getSplash2() != 0 && bullet.getTower() instanceof Herrick && !currentEnemies.get(currentIndex + 2).equals("immune")) {
                            currentEnemies.get(currentIndex + 2).slow();
                        }
                        if (currentEnemies.get(currentIndex + 2).status()) {

                            currentEnemies.get(currentIndex + 2).setX(-100);
                            currentEnemies.get(currentIndex + 2).setY(-100);

                            if (!currentEnemies.get(currentIndex + 2).checkAdd()) {
                                coins += currentEnemies.get(currentIndex + 2).getEarn();
                                currentLevel--;
                                if (currentLevel == 0)
                                    logAdd = true;
                                if (currentLevel == 0)
                                    logAdd = true;
                                score += currentEnemies.get(currentIndex + 2).getEarn();
                                currentEnemies.get(currentIndex + 2).scoreAdded();

                            }
                            for (int o = 0; o < bullets.size(); o++) {
                                Bullets temp = bullets.get(o);
                                if (temp.getTarget() == currentEnemies.get(currentIndex + 2)) {
                                    bullets.remove(temp);
                                }
                            }
                        }
                    }
                    if (currentIndex + 3 < currentEnemies.size()) {
                        currentEnemies.get(currentIndex + 3).hit(bullet.getTower().getSplash3());
                        if (bullet.getTower().getSplash3() != 0 && bullet.getTower() instanceof Herrick && !currentEnemies.get(currentIndex + 3).equals("immune")) {
                            currentEnemies.get(currentIndex + 3).slow();
                        }
                        if (currentEnemies.get(currentIndex + 3).status()) {
                            currentEnemies.get(currentIndex + 3).setX(-100);
                            currentEnemies.get(currentIndex + 3).setY(-100);

                            if (!currentEnemies.get(currentIndex + 3).checkAdd()) {
                                coins += currentEnemies.get(currentIndex + 3).getEarn();
                                currentLevel--;
                                if (currentLevel == 0)
                                    logAdd = true;
                                score += currentEnemies.get(currentIndex + 3).getEarn();
                                currentEnemies.get(currentIndex + 3).scoreAdded();
                            }
                            for (int o = 0; o < bullets.size(); o++) {
                                Bullets temp = bullets.get(o);
                                if (temp.getTarget() == currentEnemies.get(currentIndex + 3)) {
                                    bullets.remove(temp);
                                }
                            }
                        }

                    }
                    if (currentIndex - 1 > 0) {
                        currentEnemies.get(currentIndex - 1).hit(bullet.getTower().getSplash1());
                        if (bullet.getTower().getSplash1() != 0 && bullet.getTower() instanceof Herrick && !currentEnemies.get(currentIndex - 1).equals("immune")) {
                            currentEnemies.get(currentIndex - 1).slow();
                        }
                        if (currentEnemies.get(currentIndex - 1).status()) {
                            currentEnemies.get(currentIndex - 1).setX(-100);
                            currentEnemies.get(currentIndex - 1).setY(-100);

                            if (!currentEnemies.get(currentIndex - 1).checkAdd()) {
                                coins += currentEnemies.get(currentIndex - 1).getEarn();
                                currentLevel--;
                                if (currentLevel == 0)
                                    logAdd = true;
                                score += currentEnemies.get(currentIndex - 1).getEarn();
                                currentEnemies.get(currentIndex - 1).scoreAdded();

                            }
                            for (int o = 0; o < bullets.size(); o++) {
                                Bullets temp = bullets.get(o);
                                if (temp.getTarget() == currentEnemies.get(currentIndex - 1)) {
                                    bullets.remove(temp);
                                }
                            }
                        }

                    }
                    if (currentIndex - 2 >= 0) {
                        currentEnemies.get(currentIndex - 2).hit(bullet.getTower().getSplash2());
                        if (bullet.getTower().getSplash2() != 0 && bullet.getTower() instanceof Herrick && !currentEnemies.get(currentIndex - 2).equals("immune")) {
                            currentEnemies.get(currentIndex - 2).slow();
                        }
                        if (currentEnemies.get(currentIndex - 2).status()) {
                            currentEnemies.get(currentIndex - 2).setX(-100);
                            currentEnemies.get(currentIndex - 2).setY(-100);

                            if (!currentEnemies.get(currentIndex - 2).checkAdd()) {
                                coins += currentEnemies.get(currentIndex - 2).getEarn();
                                currentLevel--;
                                if (currentLevel == 0)
                                    logAdd = true;
                                score += currentEnemies.get(currentIndex - 2).getEarn();
                                currentEnemies.get(currentIndex - 2).scoreAdded();

                            }
                            for (int o = 0; o < bullets.size(); o++) {
                                Bullets temp = bullets.get(o);
                                if (temp.getTarget() == currentEnemies.get(currentIndex - 2)) {
                                    bullets.remove(temp);
                                }
                            }
                        }

                    }
                    if (currentIndex - 3 >= 0) {
                        currentEnemies.get(currentIndex - 3).hit(bullet.getTower().getSplash3());
                        if (bullet.getTower().getSplash3() != 0 && bullet.getTower() instanceof Herrick && !currentEnemies.get(currentIndex - 3).equals("immune")) {
                            currentEnemies.get(currentIndex - 3).slow();
                        }
                        if (currentEnemies.get(currentIndex - 3).status()) {
                            currentEnemies.get(currentIndex - 3).setX(-100);
                            currentEnemies.get(currentIndex - 3).setY(-100);

                            if (!currentEnemies.get(currentIndex - 3).checkAdd()) {
                                coins += currentEnemies.get(currentIndex - 3).getEarn();
                                currentLevel--;
                                if (currentLevel == 0)
                                    logAdd = true;
                                score += currentEnemies.get(currentIndex - 3).getEarn();
                                currentEnemies.get(currentIndex - 3).scoreAdded();

                            }
                            for (int o = 0; o < bullets.size(); o++) {
                                Bullets temp = bullets.get(o);
                                if (temp.getTarget() == currentEnemies.get(currentIndex - 3)) {
                                    bullets.remove(temp);
                                }
                            }
                        }
                    }
                    if (target.status()) {
                        target.setX(-100);
                        target.setY(-100);
                        coins += target.getEarn();
                        currentLevel--;
                        if (currentLevel == 0)
                            logAdd = true;
                        currentEnemies.remove(bullet.getTarget());
                        score += target.getEarn();
                        for (int o = 0; o < bullets.size(); o++) {
                            Bullets temp = bullets.get(o);
                            if (temp.getTarget() == target) {
                                bullets.remove(temp);
                            }
                        }
                    }
                    bullets.remove(bullet);
                }
                bullet.setX(bullet.getX() + xMove);
                bullet.setY(bullet.getY() + yMove);
                if (bullet.getTower() instanceof Sougata || bullet.getTower() instanceof Zeel || bullet.getTower() instanceof Rob || bullet.getTower() instanceof Tyler)
                    tile = getImage("arrow.GIF");
                else if (bullet.getTower() instanceof Herrick)
                    tile = getImage("water.GIF");
                else if (bullet.getTower() instanceof Evan)
                    tile = getImage("rock.GIF");
                else if (bullet.getTower() instanceof Mike)
                    tile = getImage("fire.GIF");
                buffer.drawImage(tile, bullet.getX(), bullet.getY(), this);
            }
            if (currentLevel == 0 && level % 7 == 0 && level > 0 && logAdd == true) {
                logs++;
                logAdd = false;
            }
        } else {
            tile = getImage("gameOver.GIF");
            buffer.drawImage(tile, 0, 0, this);
            buffer.setColor(Color.WHITE);
            buffer.setFont(new Font("Serif", Font.PLAIN, 48));
            buffer.drawString("Your Score: " + score, 80, 170);
            buffer.drawString("High Score: " + highScore, 80, 220);
            buffer.setFont(new Font("Serif", Font.PLAIN, 12));
            buffer.setColor(Color.BLACK);
        }
        g.drawImage(offscreen, 0, 20, this);
    }

    public void shooting() {

        for (int i = 0; i < towers.size(); i++) {
            Tower test = null;
            Bullets newB = null;
            Enemies test2 = null;
            test = towers.get(i);
            for (int o = 0; o < currentEnemies.size(); o++) {
                test2 = currentEnemies.get(o);
                int xDis = test.getX() - test2.getX();
                int yDis = test.getY() - test2.getY();
                int totDis = (int) Math.sqrt((xDis * xDis) + (yDis * yDis));
                if ((!test2.getType().equals("air") && !(test instanceof Rob)) || (test2.getType().equals("air") && !(test instanceof Zeel || test instanceof Evan))) {
                    if (totDis <= test.getRange() && test.canShoot()) {
                        if (test instanceof Sougata || test instanceof Evan || test instanceof Mike) {
                            sounds = new AudioClip("Arrow Swoosh-6753-Free-Loops.com.wav");
                            sounds.playOnce();
                        } else if (test instanceof Zeel || test instanceof Rob || test instanceof Tyler) {
                            sounds = new AudioClip("Arcade Explo A.wav");
                            sounds.playOnce();
                        } else if (test instanceof Herrick) {
                            ///////////////////////////////////////////////////
                        }

                        test.cantShoot();
                        newB = new Bullets();
                        newB.setTower(test);
                        newB.setX(test.getX());
                        newB.setY(test.getY());
                        newB.setTarget(test2);
                        newB.setDamage(test.getDamage());
                        bullets.add(newB);
                        o = currentEnemies.size();
                    }
                }
            }
        }
    }

    public void move() {
        for (int i = 0; i < currentEnemies.size(); i++) {

            Enemies test = currentEnemies.get(i);
            test.slowCheck();
            if (!test.speed()) {
                int move = 1;
                if (test.getType().equals("fast") || test.getType().equals("immune/fast"))
                    move = 2;
                checkTurns();
                boolean[] moves = test.getMoves();
                if (moves[0] == false && moves[1] == false && moves[2] == false && moves[3] == false && moves[4] == false && moves[5] == false && moves[6] == false && moves[7] == false && moves[8] == false && moves[9] == false && moves[10] == false && moves[11] == false)
                    test.setY(test.getY() + move);
                else if (moves[0])
                    test.setX(test.getX() - move);
                else if (moves[1])
                    test.setY(test.getY() + move);
                else if (moves[2])
                    test.setX(test.getX() + move);
                else if (moves[3])
                    test.setY(test.getY() - move);
                else if (moves[4])
                    test.setX(test.getX() + move);
                else if (moves[5])
                    test.setY(test.getY() + move);
                else if (moves[6])
                    test.setX(test.getX() - move);
                else if (moves[7])
                    test.setY(test.getY() + move);
                else if (moves[8])
                    test.setX(test.getX() + move);
                else if (moves[9])
                    test.setY(test.getY() - move);
                else if (moves[10])
                    test.setX(test.getX() - move);
                else if (moves[11]) {
                    test.setY(test.getY() - move);
                    if (test.getY() < 5) {
                        test.setX(105);
                        test.setY(32);
                        test.reset();
                        lives--;
                        coins -= test.getEarn();
                        if (lives == 0) {
                            gameOver = true;
                            setHighScore();
                        } else if (level == 39 && currentLevel == 0) {
                            gameOver = true;
                            setHighScore();
                        }
                    }

                }
            }
            if (test instanceof Keyboard) {
                tile = getImage("keyboard.GIF");
            } else if (test instanceof Monitor) {
                tile = getImage("monitor.GIF");
            } else if (test instanceof Mouse) {
                tile = getImage("mouse.GIF");
            } else if (test instanceof Floppy) {
                tile = getImage("floppy.GIF");
            } else if (test instanceof Ulmer) {
                tile = getImage("ulmer.GIF");
            }

            buffer.drawImage(tile, test.getX(), test.getY(), this);
            if (showHealth) {
                buffer.drawString("" + test.getHP(), test.getX() + 5, test.getY());
            }
        }
        tile = getImage("topHider.GIF");
        buffer.drawImage(tile, 98, 0, this);
    }

    public void setHighScore() {
        if (score > highScore) {
            try {
                outputStream = new PrintWriter("highScore.txt");
                outputStream.print(score);
                outputStream.close();
            } catch (FileNotFoundException e) {
                System.exit(0);
            }
        }
    }

    public void drawRange() {
        buffer.drawImage(tile, mouseX - 10, mouseY - 10, this);
        int range = 0;
        if (sougata)
            range = 100;
        else if (zeel)
            range = 70;
        else if (rob)
            range = 120;
        else if (herrick)
            range = 75;
        else if (evan)
            range = 100;
        else if (mike)
            range = 100;
        else if (tyler)
            range = 170;
        int circleX, circleY;
        circleX = mouseX - range;
        circleY = mouseY - range;
        buffer.setColor(Color.WHITE);
        buffer.drawOval(circleX, circleY, range * 2, range * 2);
        buffer.setColor(Color.BLACK);
        tile = getImage("circleCover2.GIF");
        buffer.drawImage(tile, 0, 0, this);
        tile = getImage("circleCover3.GIF");
        buffer.drawImage(tile, 0, 384, this);
    }

    public void showUpgrade() {
        int dam = 0, range = 0, lev = 0;
        String rate = "";
        String draw = "";
        tile = getImage("upgradeHide.GIF");
        buffer.drawImage(tile, 380, 158, this);
        tile = getImage("upgradeHide2.GIF");
        buffer.drawImage(tile, 370, 190, this);
        for (int i = 0; i < towers.size(); i++) {
            Tower test = towers.get(i);
            if (test.selected) {
                dam = test.getDamage();
                rate = test.getRate();
                range = test.getRange();
                lev = test.getLevel();
                if (test instanceof Sougata)
                    draw = "sStats";
                else if (test instanceof Zeel)
                    draw = "zStats";
                else if (test instanceof Rob)
                    draw = "rStats";
                else if (test instanceof Herrick)
                    draw = "hStats";
                else if (test instanceof Evan)
                    draw = "eStats";
                else if (test instanceof Mike)
                    draw = "mStats";
                else if (test instanceof Tyler)
                    draw = "tStats";
                draw += lev + ".GIF";
                tile = getImage(draw);
                break;
            }
        }
        buffer.drawImage(tile, 381, 170, this);
        buffer.drawString("" + dam, 423, 243);
        buffer.drawString("" + range, 423, 262);
        buffer.drawString(rate, 423, 282);

    }

    public void checkTurns() {
        for (int i = 0; i < currentEnemies.size(); i++) {
            Enemies test = currentEnemies.get(i);
            boolean[] moves = test.getMoves();
            int x = test.getX();
            int y = test.getY();
            if (x == 105 && (y == 45 || y == 46)) {
                for (int o = 0; o < 12; o++)
                    moves[o] = false;
                moves[0] = true;
            } else if ((x == 23 || x == 24) && (y == 45 || y == 46)) {
                for (int o = 0; o < 12; o++)
                    moves[o] = false;
                moves[1] = true;
            } else if ((x == 23 || x == 24) && (y == 195 || y == 196)) {
                for (int o = 0; o < 12; o++)
                    moves[o] = false;
                moves[2] = true;
            } else if ((x == 114 || x == 115) && (y == 195 || y == 196)) {
                for (int o = 0; o < 12; o++)
                    moves[o] = false;
                moves[3] = true;
            } else if ((x == 114 || x == 115) && (y == 125 || y == 126)) {
                for (int o = 0; o < 12; o++)
                    moves[o] = false;
                moves[4] = true;
            } else if ((x == 240 || x == 241) && (y == 125 || y == 126)) {
                for (int o = 0; o < 12; o++)
                    moves[o] = false;
                moves[5] = true;
            } else if ((x == 240 || x == 241) && (y == 285 || y == 286)) {
                for (int o = 0; o < 12; o++)
                    moves[o] = false;
                moves[6] = true;
            } else if ((x == 23 || x == 24) && (y == 285 || y == 286)) {
                for (int o = 0; o < 12; o++)
                    moves[o] = false;
                moves[7] = true;
            } else if ((x == 23 || x == 24) && (y == 345 || y == 346)) {
                for (int o = 0; o < 12; o++)
                    moves[o] = false;
                moves[8] = true;
            } else if ((x == 310 || x == 311) && (y == 345 || y == 346)) {
                for (int o = 0; o < 12; o++)
                    moves[o] = false;
                moves[9] = true;
            } else if ((x == 310 || x == 311) && (y == 45 || y == 46)) {
                for (int o = 0; o < 12; o++)
                    moves[o] = false;
                moves[10] = true;
            } else if ((x == 180 || x == 181) && (y == 45 || y == 46)) {
                for (int o = 0; o < 12; o++)
                    moves[o] = false;
                moves[11] = true;
            }
            test.setMoves(moves);
        }
    }

    public boolean onMap() {
        if ((((mouseX - 10) > 130 && (mouseX + 10) < 177) && (((mouseY - 10) > 30) && (mouseY) < 85)) || (((mouseX - 10) > 50 && (mouseX + 10) < 307) && (((mouseY - 10) > 72) && (mouseY + 10) < 122)) || (((mouseX - 10) > 50 && (mouseX + 10) < 110) && (((mouseY - 10) > 100) && (mouseY + 10) < 192)) || (((mouseX - 10) > 267 && (mouseX + 10) < 307) && (((mouseY - 10) > 100) && (mouseY + 10) < 342)) || (((mouseX - 10) > 140 && (mouseX + 10) < 237) && (((mouseY - 10) > 152) && (mouseY + 10) < 282)) || (((mouseX - 10) > 9 && (mouseX + 10) < 237) && (((mouseY - 10) > 222) && (mouseY + 10) < 282)) || (((mouseX - 10) > 50 && (mouseX + 10) < 307) && (((mouseY - 10) > 312) && (mouseY + 10) < 342)))
            return true;
        else
            return false;
    }

    public void showInfo() {
        if ((mouseX >= 381 && mouseX <= 410) && (mouseY >= 231 && mouseY <= 260)) {
            tile = getImage("sInfo.GIF");
            buffer.drawImage(tile, 380, 84, this);
            if (!sougataC) {
                tile = getImage("noGold1.GIF");
                buffer.drawImage(tile, 380, 158, this);
            }
        } else if ((mouseX >= 419 && mouseX <= 448) && (mouseY >= 231 && mouseY <= 260)) {
            tile = getImage("zInfo.GIF");
            buffer.drawImage(tile, 380, 84, this);
            if (!zeelC) {
                tile = getImage("noGold2.GIF");
                buffer.drawImage(tile, 380, 158, this);
            }
        } else if ((mouseX >= 457 && mouseX <= 486) && (mouseY >= 231 && mouseY <= 260)) {
            tile = getImage("rInfo.GIF");
            buffer.drawImage(tile, 380, 84, this);
            if (!robC) {
                tile = getImage("noGold3.GIF");
                buffer.drawImage(tile, 380, 158, this);
            }
        } else if ((mouseX >= 381 && mouseX <= 410) && (mouseY >= 287 && mouseY <= 316)) {
            tile = getImage("hInfo.GIF");
            buffer.drawImage(tile, 380, 84, this);
            if (!herrickU) {
                tile = getImage("noUpgrade1.GIF");
                buffer.drawImage(tile, 380, 158, this);
            } else if (!herrickC) {
                tile = getImage("noGold4.GIF");
                buffer.drawImage(tile, 380, 158, this);
            }
        } else if ((mouseX >= 419 && mouseX <= 448) && (mouseY >= 287 && mouseY <= 316)) {
            tile = getImage("eInfo.GIF");
            buffer.drawImage(tile, 380, 84, this);
            if (!evanU) {
                tile = getImage("noUpgrade2.GIF");
                buffer.drawImage(tile, 380, 158, this);
            } else if (!evanC) {
                tile = getImage("noGold5.GIF");
                buffer.drawImage(tile, 380, 158, this);
            }
        } else if ((mouseX >= 457 && mouseX <= 486) && (mouseY >= 287 && mouseY <= 316)) {
            tile = getImage("mInfo.GIF");
            buffer.drawImage(tile, 380, 84, this);
            if (!mikeU) {
                tile = getImage("noUpgrade3.GIF");
                buffer.drawImage(tile, 380, 158, this);
            } else if (!mikeC) {
                tile = getImage("noGold6.GIF");
                buffer.drawImage(tile, 380, 158, this);
            }
        } else if ((mouseX >= 381 && mouseX <= 410) && (mouseY >= 341 && mouseY <= 370)) {
            tile = getImage("tInfo.GIF");
            buffer.drawImage(tile, 380, 84, this);
            if (!tylerU) {
                tile = getImage("noUpgrade4.GIF");
                buffer.drawImage(tile, 380, 158, this);
            } else if (!tylerC) {
                tile = getImage("noGold7.GIF");
                buffer.drawImage(tile, 380, 158, this);
            }
        } else if ((mouseX >= 381 && mouseX <= 400) && (mouseY >= 396 && mouseY <= 415)) {
            tile = getImage("hUpgradeInfo.GIF");
            buffer.drawImage(tile, 380, 84, this);
            if (logs < 1) {
                tile = getImage("noLog.GIF");
                buffer.drawImage(tile, 380, 158, this);
            }
        } else if ((mouseX >= 409 && mouseX <= 428) && (mouseY >= 396 && mouseY <= 415)) {
            tile = getImage("eUpgradeInfo.GIF");
            buffer.drawImage(tile, 380, 84, this);
            if (logs < 1) {
                tile = getImage("noLog.GIF");
                buffer.drawImage(tile, 380, 158, this);
            }
        } else if ((mouseX >= 437 && mouseX <= 456) && (mouseY >= 396 && mouseY <= 415)) {
            tile = getImage("mUpgradeInfo.GIF");
            buffer.drawImage(tile, 380, 84, this);
            if (logs < 1) {
                tile = getImage("noLog.GIF");
                buffer.drawImage(tile, 380, 158, this);
            }
        } else if ((mouseX >= 465 && mouseX <= 484) && (mouseY >= 396 && mouseY <= 415)) {

            if (interest == 10)
                tile = getImage("interestRate1.GIF");
            else if (interest == 15)
                tile = getImage("interestRate2.GIF");
            else
                tile = getImage("interestRate3.GIF");
            buffer.drawImage(tile, 380, 84, this);
            if (logs < 1) {
                tile = getImage("noLog.GIF");
                buffer.drawImage(tile, 380, 158, this);
            }
        } else if ((mouseX >= 493 && mouseX <= 512) && (mouseY >= 396 && mouseY <= 415)) {
            tile = getImage("buyPerson.GIF");
            buffer.drawImage(tile, 380, 84, this);
            if (coins < 40) {
                tile = getImage("noGold8.GIF");
                buffer.drawImage(tile, 380, 158, this);
            }
        }

    }

    public void drawScore() {
        buffer.drawString("" + coins, 390, 30);
        buffer.drawString("" + score, 520, 30);
        buffer.drawString("" + logs, 390, 49);
        buffer.drawString("" + level, 443, 49);
        buffer.drawString("" + lives, 498, 49);
    }

    public void checkLogs() {
        tile = getImage("x.GIF");
        if (interest == 20) {
            buffer.drawImage(tile, 465, 396, this);
        }
        if (logs == 0) {
            buffer.drawImage(tile, 381, 396, this);
            buffer.drawImage(tile, 409, 396, this);
            buffer.drawImage(tile, 437, 396, this);
            buffer.drawImage(tile, 465, 396, this);
        } else {
            if (herrickU)
                buffer.drawImage(tile, 381, 396, this);
            if (evanU)
                buffer.drawImage(tile, 409, 396, this);
            if (mikeU)
                buffer.drawImage(tile, 437, 396, this);
        }
        if (coins < 40) {
            buffer.drawImage(tile, 493, 396, this);
        }
    }

    public void checkCoins() {
        tile = getImage("xBig.GIF");
        if (coins < 7) {
            buffer.drawImage(tile, 381, 231, this);
            sougataC = false;
        } else
            sougataC = true;
        if (coins < 9) {
            buffer.drawImage(tile, 419, 231, this);
            zeelC = false;
        } else
            zeelC = true;
        if (coins < 12) {
            buffer.drawImage(tile, 457, 231, this);
            robC = false;
        } else
            robC = true;
        if (herrickU == false) {
            buffer.drawImage(tile, 381, 287, this);
            herrickC = false;
        } else {
            if (coins >= 50) {
                herrickC = true;
            } else {
                buffer.drawImage(tile, 381, 287, this);
                herrickC = false;
            }
        }
        if (evanU == false) {
            buffer.drawImage(tile, 419, 287, this);
            evanC = false;
        } else {
            if (coins >= 50) {
                evanC = true;
            } else {
                buffer.drawImage(tile, 419, 287, this);
                evanC = false;
            }
        }
        if (mikeU == false) {
            buffer.drawImage(tile, 457, 287, this);
            mikeC = false;
        } else {
            if (coins >= 50) {
                mikeC = true;
            } else {
                buffer.drawImage(tile, 457, 287, this);
                mikeC = false;
            }
        }
        if (tylerU == false) {
            buffer.drawImage(tile, 381, 341, this);
            tylerC = false;
        } else {
            if (coins >= 200) {
                tylerC = true;
            } else {
                buffer.drawImage(tile, 381, 341, this);
                tylerC = false;
            }
        }
        if (coins < 40) {
            tile = getImage("x.GIF");
            buffer.drawImage(tile, 493, 396, this);
        }
    }

    public void drawTowers() {
        for (int a = 0; a <= towers.size() - 1; a++) {
            Tower test = towers.get(a);
            test.delay();
            if (test instanceof Sougata) {
                tile = getImage("sTower.GIF");
            } else if (test instanceof Zeel) {
                tile = getImage("zTower.GIF");
            } else if (test instanceof Rob) {
                tile = getImage("rTower.GIF");
            } else if (test instanceof Herrick) {
                tile = getImage("hTower.GIF");
            } else if (test instanceof Evan) {
                tile = getImage("eTower.GIF");
            } else if (test instanceof Mike) {
                tile = getImage("mTower.GIF");
            } else if (test instanceof Tyler) {
                tile = getImage("tTower.GIF");
            }
            buffer.drawImage(tile, test.getX(), test.getY(), this);
            if (test.selected) {
                int circleX, circleY;
                circleX = test.getX() - test.getRange() + 10;
                circleY = test.getY() - test.getRange() + 10;
                tile = getImage("select.GIF");
                buffer.drawImage(tile, test.getX(), test.getY(), this);
                if (circleBlink >= 25) {
                    buffer.setColor(Color.WHITE);
                    buffer.drawOval(circleX, circleY, test.getRange() * 2, test.getRange() * 2);
                    buffer.setColor(Color.BLACK);
                } else {
                    buffer.setColor(Color.RED);
                    buffer.drawOval(circleX, circleY, test.getRange() * 2, test.getRange() * 2);
                    buffer.setColor(Color.BLACK);
                }
                circleBlink++;
                if (circleBlink == 50) {
                    circleBlink = 0;
                }
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (!gameOver) {
            if ((x >= 9 && x <= 348) && (y >= 33 && y <= 382)) {
                for (int i = 0; i < towers.size(); i++) {
                    Tower test = towers.get(i);
                    test.unselect();
                    select = false;
                }
                for (int i = 0; i < towers.size(); i++) {
                    Tower test = towers.get(i);
                    if ((x >= test.getX() && x <= test.getX() + 20) && (y >= test.getY() && y <= test.getY() + 20)) {
                        test.select();
                        select = true;
                        sougata = false;
                        zeel = false;
                        rob = false;
                        herrick = false;
                        evan = false;
                        mike = false;
                        tyler = false;
                        break;
                    }
                }
            }
            if (x >= 390 && y >= 369 && x <= 580 && y <= 389) {
                for (int i = 0; i < towers.size(); i++) {
                    Tower test = towers.get(i);
                    if (test.selected) {
                        if (coins >= test.getUCost()) {
                            if ((test instanceof Sougata || test instanceof Zeel || test instanceof Rob) && test.level < 3) {
                                coins -= test.getUCost();
                                test.levelUp();
                            } else if ((test instanceof Herrick || test instanceof Evan || test instanceof Mike) && test.level < 4) {
                                coins -= test.getUCost();
                                test.levelUp();
                            } else if (test instanceof Tyler && test.level < 2) {
                                coins -= test.getUCost();
                                test.levelUp();
                            }

                        }
                        break;
                    }
                }
            } else if (x >= 390 && y >= 399 && x <= 580 && y <= 419) {
                for (int i = 0; i < towers.size(); i++) {
                    Tower test = towers.get(i);
                    if (test.selected) {
                        coins += test.getSell();
                        select = false;
                        sold = true;
                        towers.remove(test);
                        break;
                    }
                }
            }
            if (!select) {
                if ((x >= 381 && x <= 410) && (y >= 231 && y <= 260) && sougataC) {
                    sougata = true;
                    shadow = true;
                    zeel = false;
                    rob = false;
                    herrick = false;
                    evan = false;
                    mike = false;
                    tyler = false;
                } else if ((x >= 419 && x <= 448) && (y >= 231 && y <= 260) && zeelC) {
                    zeel = true;
                    shadow = true;
                    sougata = false;
                    rob = false;
                    herrick = false;
                    evan = false;
                    mike = false;
                    tyler = false;
                } else if ((x >= 457 && x <= 486) && (y >= 231 && y <= 260) && robC) {
                    rob = true;
                    shadow = true;
                    sougata = false;
                    zeel = false;
                    herrick = false;
                    evan = false;
                    mike = false;
                    tyler = false;
                } else if ((x >= 381 && x <= 410) && (y >= 287 && y <= 316) && herrickC) {
                    herrick = true;
                    shadow = true;
                    sougata = false;
                    zeel = false;
                    rob = false;
                    evan = false;
                    mike = false;
                    tyler = false;
                } else if ((x >= 419 && x <= 448) && (y >= 287 && y <= 316) && evanC) {
                    evan = true;
                    shadow = true;
                    sougata = false;
                    zeel = false;
                    rob = false;
                    herrick = false;
                    mike = false;
                    tyler = false;
                } else if ((x >= 457 && x <= 486) && (y >= 287 && y <= 316) && mikeC) {
                    mike = true;
                    shadow = true;
                    sougata = false;
                    zeel = false;
                    rob = false;
                    herrick = false;
                    evan = false;
                    tyler = false;
                } else if ((x >= 381 && x <= 410) && (y >= 341 && y <= 370) && tylerC) {
                    tyler = true;
                    shadow = true;
                    sougata = false;
                    zeel = false;
                    rob = false;
                    herrick = false;
                    evan = false;
                    mike = false;
                }
            }
            if (shadow && (mouseX >= 19 && mouseX <= 340) && (mouseY >= 40 && mouseY <= 374)) {
                if (onMap()) {
                    shadow = false;
                    if (sougata) {
                        towers.add(new Sougata(x - 10, y - 10));
                        coins -= 7;
                    } else if (zeel) {
                        towers.add(new Zeel(x - 10, y - 10));
                        coins -= 9;
                    } else if (rob) {
                        towers.add(new Rob(x - 10, y - 10));
                        coins -= 12;
                    } else if (herrick) {
                        towers.add(new Herrick(x - 10, y - 10));
                        coins -= 50;
                    } else if (evan) {
                        towers.add(new Evan(x - 10, y - 10));
                        coins -= 50;
                    } else if (mike) {
                        towers.add(new Mike(x - 10, y - 10));
                        coins -= 50;
                    } else if (tyler) {
                        towers.add(new Tyler(x - 10, y - 10));
                        coins -= 200;
                    }
                    sougata = false;
                    zeel = false;
                    rob = false;
                    herrick = false;
                    evan = false;
                    mike = false;
                    tyler = false;
                }
            }
            if (!sold && !herrickU && logs > 0 && (mouseX >= 381 && mouseX <= 400) && (mouseY >= 396 && mouseY <= 415)) {
                logs--;
                herrickU = true;
            }
            if (!sold && !mikeU && logs > 0 && (mouseX >= 437 && mouseX <= 456) && (mouseY >= 396 && mouseY <= 415)) {
                logs--;
                mikeU = true;
            }
            if (!sold && !evanU && logs > 0 && (mouseX >= 409 && mouseX <= 428) && (mouseY >= 396 && mouseY <= 415)) {
                logs--;
                evanU = true;
            }
            if (!sold && interest < 20 && logs > 0 && (mouseX >= 465 && mouseX <= 484) && (mouseY >= 396 && mouseY <= 415)) {
                logs--;
                interest += 5;

            }
            if (!sold && coins >= 40 && (mouseX >= 493 && mouseX <= 512) && (mouseY >= 396 && mouseY <= 415)) {
                coins -= 40;
                lives++;

            }
            if (x > 288 && x < 348 && y > 395 && y < 445 && currentLevel == 0) {
                level++;
                score += 10;
                logAdd = false;
                currentLevel = enemies.get(level).getSize();
                Enemies save = enemies.get(level);
                ;
                for (int i = 0; i < currentLevel; i++) {
                    Enemies test = null;
                    if (save instanceof Keyboard)
                        test = new Keyboard(save.getHP(), save.getEarn(), save.getType());
                    else if (save instanceof Monitor)
                        test = new Monitor(save.getHP(), save.getEarn(), save.getType());
                    else if (save instanceof Mouse)
                        test = new Mouse(save.getHP(), save.getEarn(), save.getType());
                    else if (save instanceof Floppy)
                        test = new Floppy(save.getHP(), save.getEarn(), save.getType());
                    else if (save instanceof Ulmer)
                        test = new Ulmer(save.getHP(), save.getEarn(), save.getType());
                    test.setX(105);
                    test.setY(32 - (30 * i));
                    currentEnemies.add(test);
                }
                interestAdd = false;
                textIndex++;
            }
        } else {
            if (x >= 0 && x <= 325 && y >= 349 && y <= 449) {
                shadow = false;
                sougata = false;
                zeel = false;
                rob = false;
                herrick = false;
                evan = false;
                mike = false;
                tyler = false;
                logAdd = false;
                sougataC = false;
                zeelC = false;
                robC = false;
                herrickC = false;
                evanC = false;
                mikeC = false;
                tylerC = false;
                select = false;
                showHealth = false;
                herrickU = false;
                evanU = false;
                mikeU = false;
                tylerU = false;
                interestAdd = false;
                sold = false;
                gameOver = false;
                logs = 0;
                coins = 40;
                score = 40;
                lives = 20;
                level = 0;
                interest = 10;
                currentLevel = -1;
                textIndex = 0;
                moreMoney = 0;
                circleBlink = 0;
                highScore = 40;
//				for(int i = 0; i < towers.size(); i++)
//				{
//					towers.remove(i);
//				}
                towers = null;
                towers = new ArrayList<Tower>();
                currentEnemies = null;
                currentEnemies = new ArrayList<Enemies>();
                enemies = null;
                enemies = new ArrayList<Enemies>();
                reset();
                for (int i = 0; i < words.size(); i++) {
                    if (words.get(i).toString().charAt(0) == 'I' || words.get(i).toString().charAt(0) == 'Y')
                        words.remove(i);
                }


                gameOver = false;
            } else if (x > 325 && x <= 649 && y >= 349 && y <= 449)
                System.exit(0);
        }
    }

    public int getHighScore() {
        int high = 40;
        try {
            inputStream = new Scanner(new File("highScore.txt"));
            if (inputStream.hasNextInt())
                high = inputStream.nextInt();
            inputStream.close();
        } catch (FileNotFoundException e) {
            high = 40;
            try {
                outputStream = new PrintWriter("highScore.txt");
                outputStream.println(40);
                outputStream.close();
            } catch (FileNotFoundException e1) {
                System.exit(0);
            }
        }

        return high;
    }

    public void keyPressed(KeyEvent arg0) {
        int code = arg0.getKeyCode();
        String lastKey = KeyEvent.getKeyText(code);
        if (lastKey.equalsIgnoreCase("space") && showHealth == true) {
            showHealth = false;
        } else if (lastKey.equalsIgnoreCase("space") && showHealth == false) {
            showHealth = true;
        }
    }

    public void keyTyped(KeyEvent arg0) {

    }

    public void keyReleased(KeyEvent arg0) {

    }


    public void mouseEntered(MouseEvent arg0) {

    }

    public void mouseExited(MouseEvent arg0) {

    }

    public void mousePressed(MouseEvent arg0) {

    }

    public void mouseReleased(MouseEvent arg0) {

    }

    public Image getImage(String loc) {
        return new ImageIcon(this.getClass().getResource("/" + loc)).getImage();
    }

}
