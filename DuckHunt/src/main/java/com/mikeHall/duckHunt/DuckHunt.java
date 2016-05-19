package com.mikeHall.duckHunt;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Random;

/*Creator(s): Mike Hall, Kyle Burt
 *Duck & Dog Image Provider(s): John Maresca
 *Game Play Complete
 */

public class DuckHunt extends JApplet implements Runnable, MouseListener, MouseMotionListener, KeyListener {
    //<editor-fold desc="Description">
    //Add multiple duck colors and score appear next to duck
    //Organize Variables
    //If done everything above make the screen bigger

    //Game Methods
    Random generator = new Random();
    //Game Threads
    Thread duckThread, dogThread, shotThread;
    Thread animator;
    //Game Audio
    AudioClip intro, duckQuack, gunShot, bomb, dogBark, laugh, wingFlaps;
    //Background Variables
    Dimension offDimension;
    Image offImage;
    Graphics offGraphics;
    Image background, menu, pauseScreen, FlyAway, gameOver, Good;
    boolean menuOn = true, dogOn = false;
    //Foreground Variables
    Image hitBox, scoreBoard, foreground, SHOTflash;
    boolean[] duckH;
    //Duck Variables
    Image duck, duckShot, duckFall, darkDuck, redDuck, whiteDuck;
    Image[] shotBoxFA, shotBox, duckKilled, dogMiss;
    //Dog Variables
    Image dog, dogHit;
    //Game Variables
    int mouseX, mouseY, width, height;
    int dogX = 2, dogY = 293;
    int ducksKilled = 0, shotBoxFlash = 0, pauseCounter = 0, gamePhase = generator.nextInt(2), previousGamePhase = gamePhase, duckPauseFrames = 0, dogPauseFrames = 0, flyAwayFrames = 0;
    int dogPauseCount = 0, shotCount = 0, SBCount = 0, duckCount = 0, dogCount = 0, dogLaughCount = 0;
    int duckX = generator.nextInt(280) + 40, duckY = 265;
    int points = 0, highscore = 0, duckNumber = 0, topscore = 51000, herrickScore = 27500, robScore = 26500;
    char keyPressed = '-';
    boolean threadSuspended, suspended = false;
    boolean xborder = true, yborder = false, xborder2 = true, yborder2 = false;
    boolean duckHit = false, duckGame2 = false, duckHit2 = false;
    boolean dogBorder = false, dogLaugh = false;
    int previousGamePhase2 = 0, duckPauseFrames2 = 0, duckCount2 = 0, duckX2 = generator.nextInt(280) + 40, duckY2 = 265, gamePhase2 = generator.nextInt(2);
    int rounds = 1, darkCount = 0;
    int dogPause = 0, dogPhase = 0;
    Image[] dogWalk, dogSniff, dogJump;
    int sleep = 140, timer = 0, menuCount, duckSpeed = 7, FAtime = 500, gP2counter = 0, flapCounter = 0;
    URL url;
    //</editor-fold>

    public void init() {
        resize(511, 448);
        width = getSize().width;
        height = getSize().height;
        mouseX = width / 2;
        mouseY = height / 2;

        //Opening Menu
        menu = getImage("menu.JPG");

        //Screen Layout
        foreground = getImage("foreground.gif");
        background = getImage("background.JPG");
        pauseScreen = getImage("GamePaused.GIF");
        FlyAway = getImage("FlyAway.gif");
        scoreBoard = getImage("scoreboard.jpg");
        hitBox = getImage("hitBox.gif");
        gameOver = getImage("GameOver.gif");
        Good = getImage("Good.gif");

        //Mouse Cursor
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        //ShotBox Layout
        shotBox = new Image[4];
        shotBoxFA = new Image[4];
        shotBox[0] = getImage("shotBox.gif");
        shotBoxFA[0] = getImage("shotBoxFA.gif");
        shotBox[1] = getImage("shotBox1.gif");
        shotBoxFA[1] = getImage("shotBoxFA1.gif");
        shotBox[2] = getImage("shotBox2.gif");
        shotBoxFA[2] = getImage("shotBoxFA2.gif");
        shotBox[3] = getImage("shotBox3.gif");
        shotBoxFA[3] = getImage("shotBoxFA3.gif");
        SHOTflash = getImage("shotFlash.gif");

        //Duck Images
        duckKilled = new Image[2];
        duck = getImage("duck.gif");
        duckKilled[0] = getImage("duckShot.gif");
        duckKilled[1] = getImage("duckFall.gif");
        redDuck = getImage("redDuck.gif");
        darkDuck = getImage("darkDuck.gif");
        whiteDuck = getImage("whiteDuck.gif");
        //DuckBoolean
        duckH = new boolean[11];
        duckH[0] = false;
        duckH[1] = false;
        duckH[2] = false;
        duckH[3] = false;
        duckH[4] = false;
        duckH[5] = false;
        duckH[6] = false;
        duckH[7] = false;
        duckH[8] = false;
        duckH[9] = false;
        duckH[10] = false;

        //Dog Images
        dogMiss = new Image[2];
        dog = getImage("dog.gif");
        dogHit = getImage("Dog_Hit.gif");
        dogMiss[0] = getImage("Dog_Miss_1.gif");
        dogMiss[1] = getImage("Dog_Miss_2.gif");

        //Audio Clips
        intro = getAudioClip(this.getClass().getResource("/introMusic.wav"));
        duckQuack = getAudioClip(this.getClass().getResource("/duckquack.wav"));
        gunShot = getAudioClip(this.getClass().getResource("/44magnum.wav"));
        bomb = getAudioClip(this.getClass().getResource("/bomb5.wav"));
        dogBark = getAudioClip(this.getClass().getResource("/dogbark.wav"));
        laugh = getAudioClip(this.getClass().getResource("/dogbark.wav"));
        wingFlaps = getAudioClip(this.getClass().getResource("/wing_flaps.wav"));

        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
    }

    public void destroy() {
        removeMouseListener(this);
        removeMouseMotionListener(this);
    }

    public void start() {
        System.out.println("start"); //debug
        animator = new Thread(this);
        animator.start();
        /*if (duckThread == null) {
            duckThread = new Thread(this);
            threadSuspended = false;
            duckThread.start();
        }
        if (dogThread == null) {
            dogThread = new Thread(this);
            threadSuspended = false;
            dogThread.start();
        }
        if (shotThread == null) {
            shotThread = new Thread(this);
            threadSuspended = false;
            shotThread.start();
        } else {
            if (threadSuspended) {
                threadSuspended = false;
                synchronized (this) {
                    notify();
                }
            }
        }*/
    }

    public void run() {
        boolean secondRan = true;
        boolean firstRan = true;
        System.out.println("Run called"); // debug
        System.out.println("Menu on: " + menuOn);
        System.out.println("DogOn: " + dogOn);

        while(menuOn) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while ((!menuOn) && (!dogOn)) {
            if(firstRan)
                System.out.println("first run method?"); // debug
            firstRan = false;
        }
        while ((menuOn == false) && (dogOn == true)) {
            if(secondRan)
                System.out.println("second run method?"); // debug
            secondRan = false;

            //dogThread = Thread.currentThread();
            dogMove();
        }
        while ((menuOn == false) && (dogOn == false)) {
            System.out.println("Something");
            duckThread = Thread.currentThread();
            flapCounter++;
            System.out.println("Engage game mode"); // debug
            if (duckGame2 == false) {
                duckMove();
            } else {
                duck2Move();
            }
        }
    }

    public void dogMove() {
        System.out.println("Dog move"); /// debug
        try {
            while (dogOn) {
                if (dogPhase == 0) {
                    if (dogX <= 70) {
                        dogX++;
                    } else {
                        dogPhase = 1;
                    }
                }
                if (dogPhase == 1) {
                    System.out.println("phase 1"); //debug
                    if (dogPause <= 19) {
                        dogPause++;
                    } else {
                        dogPause = 0;
                        dogPhase = 2;
                    }
                }
                if (dogPhase == 2) {
                    if (dogX <= 110) {
                        dogX++;
                    } else {
                        dogPhase = 3;
                    }
                }
                if (dogPhase == 3) {
                    if (dogPause <= 10) {
                        dogPause++;
                    } else {
                        if (dogPause <= 20) {
                            dogPause++;
                        }
                        if ((dogPause >= 20) && (dogPause <= 34)) {
                            if ((dogPause >= 25) && (dogPause <= 27)) {
                                dogBark.play();
                            }
                            if (dogPause >= 32) {
                                dogBark.play();
                            }
                            dogY -= 6;
                            dogPause++;
                        }
                        if ((dogPause >= 34) && (dogPause <= 55)) {
                            dogY += 6;
                            dogPause++;
                        }
                        if ((dogPause >= 55) && (dogPause <= 75)) {
                            dogPause++;
                        }
                        if (dogPause >= 75) {
                            dogPhase = 5;
                        }

                    }
                }
                if (dogPhase == 5) {
                    System.out.println("Dog end"); // debug
                    dogPhase = 6;
                    sleep = 15;
                    menuOn = false;
                    dogOn = false;
                    dogThread.stop();
                }
                Thread.sleep(sleep);
                System.out.println("Dog pause: " + dogPause); //debug
            }
        } catch (InterruptedException e) {
        }
    }

    public void duckMove()//Game For 1 Duck
    {

        try {
            if (gamePhase == 0)//Duck Moves Right
            {
                if (flapCounter % 4 == 0) {
                    wingFlaps.play();
                }
                if (flyAwayFrames <= FAtime)//Enables Fly Away
                {
                    flyAwayFrames++;
                    if (flyAwayFrames == (FAtime - 1)) {
                        dogX = 220;
                        dogY = 280;
                        gamePhase = 5;
                    }
                }
                if (keyPressed == 'p') {
                    suspended = true;
                    previousGamePhase = gamePhase;
                    gamePhase = 8;
                }
                if (gP2counter == 0) {
                    if (yborder == false) {
                        duckY -= duckSpeed;
                        if (duckY <= -2) {
                            yborder = true;
                        }
                    } else {
                        duckY += duckSpeed;
                        if (duckY >= 260) {
                            yborder = false;
                        }
                    }
                    if (xborder == false) {
                        duckX -= duckSpeed;
                        if (duckX <= -10) {
                            xborder = true;
                        }
                    } else {
                        duckX += duckSpeed;
                        if (duckX >= 460) {
                            xborder = false;
                        }
                    }
                } else {
                    if (yborder == false) {
                        duckY--;
                        if (duckY <= -2) {
                            yborder = true;
                        }
                    } else {
                        duckY++;
                        if (duckY >= 260) {
                            yborder = false;
                        }
                    }
                    if (xborder == false) {
                        duckX -= duckSpeed;
                        if (duckX <= -10) {
                            xborder = true;
                        }
                    } else {
                        duckX += duckSpeed;
                        if (duckX >= 460) {
                            xborder = false;
                        }
                    }
                }

            }
            if (gamePhase == 1)//Duck Moves Left
            {
                if (flapCounter % 4 == 0) {
                    wingFlaps.play();
                }
                if (flyAwayFrames <= FAtime)//Enables Fly Away
                {
                    flyAwayFrames++;
                    if (flyAwayFrames == (FAtime - 1)) {
                        dogX = 220;
                        dogY = 280;
                        gamePhase = 5;
                    }
                }
                if (keyPressed == 'p') {
                    suspended = true;
                    previousGamePhase = gamePhase;
                    gamePhase = 8;
                }
                if (gP2counter == 0) {
                    if (yborder == false) {
                        duckY -= duckSpeed;
                        if (duckY <= -2) {
                            yborder = true;
                        }
                    } else {
                        duckY += duckSpeed;
                        if (duckY >= 260) {
                            yborder = false;
                        }
                    }
                    if (xborder == true) {
                        duckX -= duckSpeed;
                        if (duckX <= -10) {
                            xborder = false;
                        }
                    } else {
                        duckX += duckSpeed;
                        if (duckX >= 460) {
                            xborder = true;
                        }
                    }
                } else {
                    if (yborder == false) {
                        duckY -= duckSpeed;
                        if (duckY <= -2) {
                            yborder = true;
                        }
                    } else {
                        duckY += duckSpeed;
                        if (duckY >= 260) {
                            yborder = false;
                        }
                    }
                    if (xborder == true) {
                        duckX--;
                        if (duckX <= -10) {
                            xborder = false;
                        }
                    } else {
                        duckX++;
                        if (duckX >= 460) {
                            xborder = true;
                        }
                    }
                }
            }
            if (gamePhase == 3)//Duck falls to Ground
            {
                if (duckCount > 0) {
                    duckY += 3;
                }
                if (duckY >= 260) {
                    dogX = 220;
                    dogY = 280;
                    dogPauseFrames = 0;
                    gamePhase = 4;
                }
            }
            if (gamePhase == 4)//Dog Holds up Duck
            {
                if (dogBorder == false) {
                    bomb.stop();
                    dogY -= 1.5;
                    if (dogY <= 214) {
                        dogBorder = true;
                    }
                } else {
                    dogPauseFrames++;
                    if (dogPauseFrames >= 30) {
                        dogY += 2;
                    }
                    if (dogY >= 290) {
                        duckHit = false;
                        dogBorder = false;
                        dogLaugh = false;
                        shotCount = 0;
                        SBCount = 0;
                        duckY = 265;
                        duckPauseFrames = 0;
                        dogPauseFrames = 0;
                        dogPauseCount = 0;
                        duckCount = 0;
                        dogLaughCount = 0;
                        flyAwayFrames = 0;
                        shotBoxFlash = 0;
                        duckX = generator.nextInt(280) + 40;//=190 origional
                        gamePhase = generator.nextInt(2);
                        if (gP2counter == 0) {
                            gP2counter++;
                        } else {
                            gP2counter--;
                        }
                        duckNumber++;
                    }
                }
            }
            if (gamePhase == 5)//Duck Fly Away & Dog Laugh
            {
                if (flapCounter % 4 == 0) {
                    wingFlaps.play();
                }
                duckY -= 3;
                if (dogLaugh == false) {
                    dogY -= 1.4;
                    if (dogY <= 214) {
                        dogLaugh = true;
                    }
                } else {
                    if (duckY <= -400) {
                        dogY += 2;
                    }
                    if (dogY >= 290) {
                        duckHit = false;
                        dogBorder = false;
                        dogLaugh = false;
                        shotCount = 0;
                        SBCount = 0;
                        duckY = 265;
                        duckPauseFrames = 0;
                        dogPauseFrames = 0;
                        dogPauseCount = 0;
                        duckCount = 0;
                        dogLaughCount = 0;
                        flyAwayFrames = 0;
                        shotBoxFlash = 0;
                        duckX = generator.nextInt(280) + 40;//=190 origional
                        gamePhase = generator.nextInt(2);
                        if (gP2counter == 0) {
                            gP2counter++;
                        } else {
                            gP2counter--;
                        }
                        duckNumber++;
                    }
                }
            }
            if (gamePhase == 8) {
                duckThread.yield();
            }
            Thread.sleep(35);
        } catch (InterruptedException e) {
        }
    }

    public void duck2Move()//Game For 2 Duck
    {
        try {
            if ((gamePhase < 3) || (gamePhase2 < 3)) {
                if (flyAwayFrames <= FAtime)//Enables Fly Away
                {
                    flyAwayFrames++;
                    if (flyAwayFrames == (FAtime - 1)) {
                        dogX = 220;
                        dogY = 280;
                        gamePhase = 5;
                        gamePhase2 = 5;
                    }
                }
                if (keyPressed == 'p') {
                    suspended = true;
                    gamePhase = 8;
                    gamePhase2 = 8;
                }
            }

            if (gamePhase == 0)//Duck Moves Right
            {
                if (flapCounter % 4 == 0) {
                    wingFlaps.play();
                }
                if (gP2counter == 0) {
                    if (yborder == false) {
                        duckY -= duckSpeed;
                        if (duckY <= -2) {
                            yborder = true;
                        }
                    } else {
                        duckY += duckSpeed;
                        if (duckY >= 260) {
                            yborder = false;
                        }
                    }
                    if (xborder == false) {
                        duckX -= duckSpeed;
                        if (duckX <= -10) {
                            xborder = true;
                        }
                    } else {
                        duckX += duckSpeed;
                        if (duckX >= 460) {
                            xborder = false;
                        }
                    }
                } else {
                    if (yborder == false) {
                        duckY -= duckSpeed;
                        if (duckY <= -2) {
                            yborder = true;
                        }
                    } else {
                        duckY += duckSpeed;
                        if (duckY >= 260) {
                            yborder = false;
                        }
                    }
                    if (xborder == true) {
                        duckX--;
                        if (duckX <= -10) {
                            xborder = false;
                        }
                    } else {
                        duckX++;
                        if (duckX >= 460) {
                            xborder = true;
                        }
                    }
                }

            }
            if (gamePhase2 == 1)//Duck Moves Right
            {
                if (flapCounter % 4 == 0) {
                    wingFlaps.play();
                }
                if (yborder2 == false) {
                    duckY2 -= duckSpeed;
                    if (duckY2 <= -2) {
                        yborder2 = true;
                    }
                } else {
                    duckY2 += duckSpeed;
                    if (duckY2 >= 260) {
                        yborder2 = false;
                    }
                }
                if (xborder2 == false) {
                    duckX2 -= duckSpeed;
                    if (duckX2 <= -10) {
                        xborder2 = true;
                    }
                } else {
                    duckX2 += duckSpeed;
                    if (duckX2 >= 460) {
                        xborder2 = false;
                    }
                }
            }
            if (gamePhase == 1)//Duck Moves Left
            {
                if (flapCounter % 4 == 0) {
                    wingFlaps.play();
                }
                if (gP2counter == 0) {
                    if (yborder == false) {
                        duckY -= duckSpeed;
                        if (duckY <= -2) {
                            yborder = true;
                        }
                    } else {
                        duckY += duckSpeed;
                        if (duckY >= 260) {
                            yborder = false;
                        }
                    }
                    if (xborder == true) {
                        duckX -= duckSpeed;
                        if (duckX <= -10) {
                            xborder = false;
                        }
                    } else {
                        duckX += duckSpeed;
                        if (duckX >= 460) {
                            xborder = true;
                        }
                    }
                } else {
                    if (yborder == false) {
                        duckY -= duckSpeed;
                        if (duckY <= -2) {
                            yborder = true;
                        }
                    } else {
                        duckY += duckSpeed;
                        if (duckY >= 260) {
                            yborder = false;
                        }
                    }
                    if (xborder == true) {
                        duckX--;
                        if (duckX <= -10) {
                            xborder = false;
                        }
                    } else {
                        duckX++;
                        if (duckX >= 460) {
                            xborder = true;
                        }
                    }
                }
            }
            if (gamePhase2 == 0)//Duck Moves Left
            {
                if (flapCounter % 4 == 0) {
                    wingFlaps.play();
                }
                if (yborder2 == false) {
                    duckY2 -= duckSpeed;
                    if (duckY2 <= -2) {
                        yborder2 = true;
                    }
                } else {
                    duckY2 += duckSpeed;
                    if (duckY2 >= 260) {
                        yborder2 = false;
                    }
                }
                if (xborder2 == true) {
                    duckX2 -= duckSpeed;
                    if (duckX2 <= -10) {
                        xborder2 = false;
                    }
                } else {
                    duckX2 += duckSpeed;
                    if (duckX2 >= 460) {
                        xborder2 = true;
                    }
                }
            }
            if ((gamePhase == 4) && (gamePhase2 == 4)) {
                if (dogBorder == false) {
                    bomb.stop();
                    dogY -= 1.5;
                    if (dogY <= 214) {
                        dogBorder = true;
                    }
                } else {
                    dogPauseFrames++;
                    if (dogPauseFrames >= 30) {
                        dogY += 2;
                    }
                    if (dogY >= 290) {
                        duckHit = false;
                        duckHit2 = false;
                        dogBorder = false;
                        dogLaugh = false;
                        shotCount = 0;
                        SBCount = 0;
                        duckY = 265;
                        duckY2 = 265;
                        duckPauseFrames = 0;
                        duckPauseFrames2 = 0;
                        dogPauseFrames = 0;
                        dogPauseCount = 0;
                        duckCount = 0;
                        duckCount2 = 0;
                        dogLaughCount = 0;
                        flyAwayFrames = 0;
                        shotBoxFlash = 0;
                        duckX = generator.nextInt(280) + 40;//=190 origional
                        duckX2 = generator.nextInt(280) + 40;//=190 origional
                        gamePhase = generator.nextInt(2);
                        if (gP2counter == 0) {
                            gP2counter++;
                        } else {
                            gP2counter--;
                        }
                        gamePhase2 = generator.nextInt(2);
                    }
                }
            }
            if ((gamePhase == 5) && (gamePhase2 == 5)) {
                if ((duckHit == false)) {
                    if (flapCounter % 4 == 0) {
                        wingFlaps.play();
                    }
                    duckY -= 5;
                }
                if (duckHit2 == false) {
                    if (flapCounter % 4 == 0) {
                        wingFlaps.play();
                    }
                    duckY2 -= 5;
                }
                if (dogLaugh == false) {
                    dogY -= 1.4;
                    if (dogY <= 214) {
                        dogLaugh = true;
                    }
                } else {
                    if ((duckHit == false) && (duckHit2 == false)) {
                        if ((duckY <= -400) && (duckY2 <= -400)) {
                            dogY += 2;
                        }
                    } else if (duckHit == false) {
                        if (duckY <= -400) {
                            dogY += 2;
                        }
                    } else if (duckHit2 == false) {
                        if (duckY2 <= -400) {
                            dogY += 2;
                        }
                    }
                    if (dogY >= 290) {
                        if ((duckHit == false) && (duckHit2 == false)) {
                            duckNumber += 2;
                        } else {
                            duckNumber++;
                        }
                        duckHit = false;
                        duckHit2 = false;
                        dogBorder = false;
                        dogLaugh = false;
                        shotCount = 0;
                        SBCount = 0;
                        duckY = 265;
                        duckY2 = 265;
                        duckPauseFrames = 0;
                        duckPauseFrames2 = 0;
                        dogPauseFrames = 0;
                        dogPauseCount = 0;
                        duckCount = 0;
                        duckCount2 = 0;
                        dogLaughCount = 0;
                        flyAwayFrames = 0;
                        shotBoxFlash = 0;
                        duckX = generator.nextInt(280) + 40;//=190 origional
                        duckX2 = generator.nextInt(280) + 40;//=190 origional
                        gamePhase = generator.nextInt(2);
                        if (gP2counter == 0) {
                            gP2counter++;
                        } else {
                            gP2counter--;
                        }
                        gamePhase2 = generator.nextInt(2);
                    }
                }
            }
            if ((gamePhase == 8) && (gamePhase2 == 8)) {
                duckThread.yield();
            }
            Thread.sleep(35);
        } catch (InterruptedException e) {
        }
    }


    public void mouseClicked(MouseEvent e) {
        if ((mouseX <= 236) && (mouseX >= 121) && (mouseY <= 280) && (mouseY >= 245) && (menuOn == true) && (dogOn == false)) {
            if (menuCount == 1) {
                System.exit(0);
            }
            menuOn = false;
            dogOn = true;
            intro.play();
            repaint();
        }
        if ((mouseX <= 236) && (mouseX >= 121) && (mouseY <= 305) && (mouseY >= 280) && (menuOn == true) && (dogOn == false)) {
            if (menuCount == 1) {
                System.exit(0);
            }
            menuOn = false;
            dogOn = true;
            intro.play();
            duckGame2 = true;
            repaint();
        }
    }

    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if ((mouseX >= 475) && (mouseX <= 490) && (mouseY >= 425) && (mouseY <= 435)) {
            menuOn = true;
            menuCount++;
        }
        if ((shotCount <= 2) && (menuOn == false) && (dogOn == false) && (keyPressed != 'p')) {
            if ((gamePhase != 5) && (gamePhase2 != 5)) {
                if ((gamePhase <= 1) || (gamePhase2 <= 1)) {
                    gunShot.play();
                    shotCount++;
                    SBCount++;
                }
            }
            if (gamePhase <= 1) {
                if ((mouseX > duckX) && (mouseX < (duckX + 70)) && (mouseY > duckY) && (mouseY < (duckY + 65)) && (menuOn == false) && (dogOn == false)) {
                    duckHit = true;
                    if (duckHit == true) {
                        duckH[duckNumber] = true;
                    }
                    if (duckGame2 == true) {
                        duckNumber++;
                    }
                    duckQuack.play();
                    bomb.play();
                    ducksKilled++;
                    gamePhase = 3;
                }
            }
            if (gamePhase2 <= 1) {
                if ((mouseX > duckX2) && (mouseX < (duckX2 + 70)) && (mouseY > duckY2) && (mouseY < (duckY2 + 65)) && (menuOn == false) && (dogOn == false)) {
                    duckHit2 = true;
                    if (duckHit2 == true) {
                        duckH[duckNumber] = true;
                    }
                    if (duckGame2 == true) {
                        duckNumber++;
                    }
                    duckQuack.play();
                    bomb.stop();
                    bomb.play();
                    ducksKilled++;
                    gamePhase2 = 3;
                }
            }
        }
    }

    public void paint(Graphics g) {
        update(g);
    }

    public void update(Graphics g) {
        Dimension d = size();

        // Create the offscreen graphics context
        if ((offGraphics == null)
                || (d.width != offDimension.width)
                || (d.height != offDimension.height)) {
            offDimension = d;
            offImage = createImage(d.width, d.height);
            offGraphics = offImage.getGraphics();
        }

        // Erase the previous image
        offGraphics.setColor(getBackground());
        offGraphics.fillRect(0, 0, d.width, d.height);
        offGraphics.setColor(Color.black);

        if ((menuOn == true) && (dogOn == false)) {
            System.out.println("Paint menu"); // debug
            paintMenu(offGraphics);
        }
        if ((menuOn == false) && (dogOn == true)) {
            dogIntro(offGraphics);
        }
        if ((menuOn == false) && (dogOn == false)) {
            System.out.println("dog an menu off paint?"); // debug
            if (gamePhase >= 0) {
                paintBackground(offGraphics);
                if (duckGame2 == true) {
                    if (duckHit == true) {
                        paintDuckDead(offGraphics);
                        if (gamePhase == 3) {
                            if (duckCount > 0) {
                                duckY += 5;
                            }
                        }
                    } else {
                        paintDuckAlive(offGraphics);
                    }

                    if (duckHit2 == true) {
                        paintDuck2Dead(offGraphics);
                        if (gamePhase2 == 3) {
                            if (duckCount2 > 0) {
                                duckY2 += 5;
                            }
                        }
                    } else {
                        paintDuck2Alive(offGraphics);
                    }
                    if ((gamePhase == 3) && (gamePhase2 == 3)) {
                        if ((duckY >= 280) && (duckY2 >= 280)) {
                            dogX = 220;
                            dogY = 280;
                            dogPauseFrames = 0;
                            gamePhase = 4;
                            gamePhase2 = 4;
                        }
                    }
                    if (gamePhase == 4) {
                        paintDogHit(offGraphics);
                    } else {
                        paintDogLaugh(offGraphics);
                    }
                    if (duckNumber >= 10) {
                        gamePhase = -1;
                        gamePhase2 = -1;
                    }
                } else {
                    if (duckHit == false) {

                        paintDuckAlive(offGraphics);
                        paintDogLaugh(offGraphics);
                    } else {
                        if (gamePhase == 3) {
                            paintDuckDead(offGraphics);
                            paintDogLaugh(offGraphics);
                        } else {
                            paintDogHit(offGraphics);
                        }
                    }
                    if (duckNumber >= 10) {
                        gamePhase = -1;
                    }
                }
                paintForeground(offGraphics);
            }
        }
        if (gamePhase < 0) {
            paintBackground(offGraphics);
            paintForeground(offGraphics);
        }
        g.drawImage(offImage, 0, 0, null);

    }

    public void paintBackground(Graphics g) {
        if (gamePhase == 5) {
            background = getImage("backgroundFlyAway.JPG");
        } else {
            if (keyPressed == 'p') {
                background = getImage("backgroundPause.JPG");
            } else {
                background = getImage("background.JPG");
            }
        }
        g.drawImage(background, 0, 0, this);
    }

    public void paintDuckAlive(Graphics g) {
        g.drawImage(duck, duckX, duckY, this);
        if (keyPressed == 'p') {
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.setColor(Color.white);
            g.drawString("Game Paused", 220, 160);
            g.drawString("Press SpaceBar To Continue", 170, 180);
        }
        if (gamePhase == 5) {
            g.drawImage(FlyAway, 220, 160, 120, 30, this);
        }
    }

    public void paintDuck2Alive(Graphics g) {
        g.drawImage(duck, duckX2, duckY2, this);
        if (keyPressed == 'p') {
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.setColor(Color.white);
            g.drawString("Game Paused", 220, 160);
            g.drawString("Press SpaceBar To Continue", 170, 180);
        }
        if (gamePhase == 5) {
            g.drawImage(FlyAway, 220, 160, 120, 30, this);
        }
    }

    public void paintDogLaugh(Graphics g) {
        if ((gamePhase == 5) && (duckY <= -40)) {
            g.drawImage(dogMiss[dogLaughCount], dogX, (dogY + 10), 70, 70, this);
            if (dogLaughCount == 0) {
                if (dogPauseCount <= 4) {
                    dogPauseCount++;
                    if (dogPauseCount >= 4) {
                        dogLaughCount++;
                        dogPauseCount = 0;
                    }
                }
            } else {
                if (dogPauseCount <= 4) {
                    dogPauseCount++;
                    if (dogPauseCount >= 4) {
                        dogLaughCount--;
                        dogPauseCount = 0;
                    }
                }
            }
        }
    }

    public void paintForeground(Graphics g) {
        darkCount++;
        g.drawImage(foreground, 0, 0, this);
        if (gamePhase == 5) {
            g.drawImage(shotBoxFA[SBCount], 20, 385, 80, 60, this);
        } else {
            g.drawImage(shotBox[SBCount], 20, 385, 80, 60, this);
        }
        if (shotCount > 2) {
            shotBoxFlash++;
            if (ducksKilled <= 0) {
                if (shotBoxFlash % 4 == 0) {
                    g.drawImage(SHOTflash, 30, 418, 65, 20, this);
                }
            } else {
                if (shotBoxFlash % 4 == 0) {
                    g.drawImage(SHOTflash, 30, 418, 65, 20, this);
                }
            }
        }
        g.drawImage(scoreBoard, 400, 370, 100, 40, this);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.yellow);
        g.drawImage(SHOTflash, 36, 359, 50, 20, this);
        g.drawString("R = " + rounds, 40, 375);
        g.setColor(Color.white);
        g.drawString("" + points, 420, 390);
        if (gamePhase == -1) {
            if (ducksKilled < 5) {
                g.drawImage(gameOver, 220, 160, 120, 30, this);
                if (timer <= 50) {
                    timer++;
                } else {
                    menuOn = true;
                    menuCount++;
                }
            } else {
                g.drawImage(Good, 220, 160, 120, 30, this);
                if (timer <= 30) {
                    timer++;
                } else {
                    rounds++;
                    dogX = 2;
                    dogY = 293;
                    ducksKilled = 0;
                    shotBoxFlash = 0;
                    pauseCounter = 0;
                    gamePhase = generator.nextInt(2);
                    previousGamePhase = gamePhase;
                    duckPauseFrames = 0;
                    dogPauseFrames = 0;
                    flyAwayFrames = 0;
                    dogPauseCount = 0;
                    shotCount = 0;
                    SBCount = 0;
                    duckCount = 0;
                    dogCount = 0;
                    dogLaughCount = 0;
                    duckX = generator.nextInt(280) + 40;
                    duckY = 265;
                    duckNumber = 0;
                    keyPressed = '-';
                    xborder = true;
                    yborder = false;
                    xborder2 = true;
                    yborder2 = false;
                    duckHit = false;
                    duckHit2 = false;
                    dogBorder = false;
                    dogLaugh = false;
                    previousGamePhase2 = 0;
                    duckPauseFrames2 = 0;
                    duckCount2 = 0;
                    duckX2 = generator.nextInt(280) + 40;
                    duckY2 = 265;
                    gamePhase2 = generator.nextInt(2);
                    darkCount = 0;
                    dogPause = 0;
                    dogPhase = 0;
                    sleep = 130;
                    timer = 0;
                    duckSpeed += 2;
                    FAtime -= 20;
                    duckH[0] = false;
                    duckH[1] = false;
                    duckH[2] = false;
                    duckH[3] = false;
                    duckH[4] = false;
                    duckH[5] = false;
                    duckH[6] = false;
                    duckH[7] = false;
                    duckH[8] = false;
                    duckH[9] = false;
                    duckH[10] = false;
                    dogThread.resume();
                }
            }
        }
        if (gamePhase == 5) {
            hitBox = getImage("hitBoxFA.gif");
        } else {
            hitBox = getImage("hitBox.gif");
        }
        g.drawImage(hitBox, 145, 390, 230, 45, this);
        if (duckH[0] == true) {
            g.drawImage(redDuck, 212, 398, 16, 18, this);
        }
        if (duckH[1] == true) {
            g.drawImage(redDuck, 227, 398, 16, 18, this);
        }
        if (duckH[2] == true) {
            g.drawImage(redDuck, 243, 398, 16, 18, this);
        }
        if (duckH[3] == true) {
            g.drawImage(redDuck, 259, 398, 16, 18, this);
        }
        if (duckH[4] == true) {
            g.drawImage(redDuck, 275, 398, 16, 18, this);
        }
        if (duckH[5] == true) {
            g.drawImage(redDuck, 291, 398, 16, 18, this);
        }
        if (duckH[6] == true) {
            g.drawImage(redDuck, 306, 398, 16, 18, this);
        }
        if (duckH[7] == true) {
            g.drawImage(redDuck, 322, 398, 16, 18, this);
        }
        if (duckH[8] == true) {
            g.drawImage(redDuck, 337, 398, 16, 18, this);
        }
        if (duckH[9] == true) {
            g.drawImage(redDuck, 353, 398, 16, 18, this);
        }
        if (duckGame2 == true) {
            if (duckNumber == 0) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 212, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 212, 398, 16, 18, this);
                }
            }
            if (duckNumber == 0) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 227, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 227, 398, 16, 18, this);
                }
            }
            if (duckNumber == 1) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 227, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 227, 398, 16, 18, this);
                }
            }
            if (duckNumber == 2) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 243, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 243, 398, 16, 18, this);
                }
            }
            if (duckNumber == 2) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 259, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 259, 398, 16, 18, this);
                }
            }
            if (duckNumber == 3) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 259, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 259, 398, 16, 18, this);
                }
            }
            if (duckNumber == 4) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 275, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 275, 398, 16, 18, this);
                }
            }
            if (duckNumber == 4) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 291, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 291, 398, 16, 18, this);
                }
            }
            if (duckNumber == 5) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 291, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 291, 398, 16, 18, this);
                }
            }
            if (duckNumber == 6) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 306, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 306, 398, 16, 18, this);
                }
            }
            if (duckNumber == 6) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 322, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 322, 398, 16, 18, this);
                }
            }
            if (duckNumber == 7) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 322, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 322, 398, 16, 18, this);
                }
            }
            if (duckNumber == 8) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 337, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 337, 398, 16, 18, this);
                }
            }
            if (duckNumber == 8) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 353, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 353, 398, 16, 18, this);
                }
            }
            if (duckNumber == 9) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 353, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 353, 398, 16, 18, this);
                }
            }
            if (duckNumber == 0) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 212, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 212, 398, 16, 18, this);
                }
            }
            if (duckNumber == 0) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 227, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 227, 398, 16, 18, this);
                }
            }
            if (duckNumber == 1) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 227, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 227, 398, 16, 18, this);
                }
            }
            if (duckNumber == 2) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 243, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 243, 398, 16, 18, this);
                }
            }
            if (duckNumber == 2) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 259, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 259, 398, 16, 18, this);
                }
            }
            if (duckNumber == 3) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 259, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 259, 398, 16, 18, this);
                }
            }
            if (duckNumber == 4) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 275, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 275, 398, 16, 18, this);
                }
            }
            if (duckNumber == 4) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 291, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 291, 398, 16, 18, this);
                }
            }
            if (duckNumber == 5) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 291, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 291, 398, 16, 18, this);
                }
            }
            if (duckNumber == 6) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 306, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 306, 398, 16, 18, this);
                }
            }
            if (duckNumber == 6) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 322, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 322, 398, 16, 18, this);
                }
            }
            if (duckNumber == 7) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 322, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 322, 398, 16, 18, this);
                }
            }
            if (duckNumber == 8) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 337, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 337, 398, 16, 18, this);
                }
            }
            if (duckNumber == 8) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 353, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 353, 398, 16, 18, this);
                }
            }
            if (duckNumber == 9) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 353, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 353, 398, 16, 18, this);
                }
            }
        } else {
            if (duckNumber == 0) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 212, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 212, 398, 16, 18, this);
                }
            }
            if (duckNumber == 1) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 227, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 227, 398, 16, 18, this);
                }
            }
            if (duckNumber == 2) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 243, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 243, 398, 16, 18, this);
                }
            }
            if (duckNumber == 3) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 259, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 259, 398, 16, 18, this);
                }
            }
            if (duckNumber == 4) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 275, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 275, 398, 16, 18, this);
                }
            }
            if (duckNumber == 5) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 291, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 291, 398, 16, 18, this);
                }
            }
            if (duckNumber == 6) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 306, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 306, 398, 16, 18, this);
                }
            }
            if (duckNumber == 7) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 322, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 322, 398, 16, 18, this);
                }
            }
            if (duckNumber == 8) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 337, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 337, 398, 16, 18, this);
                }
            }
            if (duckNumber == 9) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 353, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 353, 398, 16, 18, this);
                }
            }
            if (duckNumber == 0) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 212, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 212, 398, 16, 18, this);
                }
            }
            if (duckNumber == 1) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 227, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 227, 398, 16, 18, this);
                }
            }
            if (duckNumber == 2) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 243, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 243, 398, 16, 18, this);
                }
            }
            if (duckNumber == 3) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 259, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 259, 398, 16, 18, this);
                }
            }
            if (duckNumber == 4) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 275, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 275, 398, 16, 18, this);
                }
            }
            if (duckNumber == 5) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 291, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 291, 398, 16, 18, this);
                }
            }
            if (duckNumber == 6) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 306, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 306, 398, 16, 18, this);
                }
            }
            if (duckNumber == 7) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 322, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 322, 398, 16, 18, this);
                }
            }
            if (duckNumber == 8) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 337, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 337, 398, 16, 18, this);
                }
            }
            if (duckNumber == 9) {
                if (darkCount % 15 == 0) {
                    g.drawImage(darkDuck, 353, 398, 16, 18, this);
                } else {
                    g.drawImage(whiteDuck, 353, 398, 16, 18, this);
                }
            }
        }
    }

    public void paintDuckDead(Graphics g) {
        g.drawImage(duckKilled[duckCount], duckX, duckY, this);
        if (duckPauseFrames <= 7) {
            g.setFont(new Font("Arial", Font.PLAIN, 13));
            g.setColor(Color.white);
            g.drawString("500", (duckX + 15), (duckY - 10));
            duckPauseFrames++;
            if (duckPauseFrames == 7) {
                duckCount++;
                points += 500;
                highscore = points;
            }
        }
    }

    public void paintDuck2Dead(Graphics g) {
        g.drawImage(duckKilled[duckCount2], duckX2, duckY2, this);
        if (duckPauseFrames2 <= 7) {
            g.setFont(new Font("Arial", Font.PLAIN, 13));
            g.setColor(Color.white);
            g.drawString("500", (duckX2 + 15), (duckY2 - 10));
            duckPauseFrames2++;
            if (duckPauseFrames2 == 7) {
                duckCount2++;
                points += 500;
                highscore += points;
            }
        }
    }

    public void paintDogHit(Graphics g) {
        if (duckGame2 == true) {
            dogHit = getImage("Dog_Hit2.gif");
        }
        g.drawImage(dogHit, dogX, dogY, 120, 110, this);
    }

    public void paintMenu(Graphics g) {
        g.drawImage(menu, 0, 0, this);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.white);
        g.drawString("By: Mike Hall", 10, 269);
        g.drawString("& Kyle Burt", 10, 302);
        //g.setColor(Color.GREEN);
        g.drawString("Top Score     =    " + topscore, 180, 360);
        g.drawString("Your Score   =    " + highscore, 180, 385);
    }

    public void dogIntro(Graphics g) {
        System.out.println("dogPause: " + dogPause); //debug
        g.drawImage(background, 0, 0, this);
        if (dogPause >= 34) {
            g.drawImage(dog, dogX, dogY, this);
            g.drawImage(foreground, 0, 0, this);
        } else {
            g.drawImage(foreground, 0, 0, this);
            g.drawImage(dog, dogX, dogY, this);
        }
        g.drawImage(shotBox[SBCount], 20, 385, 80, 60, this);
        g.drawImage(scoreBoard, 400, 370, 100, 40, this);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.white);
        g.drawString("" + points, 420, 390);
        g.drawImage(hitBox, 145, 390, 230, 45, this);
    }

    public void keyPressed(KeyEvent k) {
        keyPressed = k.getKeyChar();
        if (suspended == true) {
            if (keyPressed == ' ') {
                duckThread.resume();
                if (duckNumber % 2 == 0) {
                    if (duckH[duckNumber] == true) {
                    } else {
                        gamePhase2 = previousGamePhase2;
                    }
                }
                gamePhase = previousGamePhase;
                suspended = false;
            }
        }
    }

    //Unused Implement Methods
    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void keyReleased(KeyEvent arg0) {
    }

    public void keyTyped(KeyEvent arg0) {
    }

    private Image getImage(String loc) {
        return new ImageIcon(this.getClass().getResource("/" + loc)).getImage();
    }

}
