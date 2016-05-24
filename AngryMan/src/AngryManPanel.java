//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AngryManPanel extends JPanel implements Runnable, KeyListener {
    private static final long serialVersionUID = 1L;
    private static final int PWIDTH = 900;
    private static final int PHEIGHT = 700;
    private static final String IMS_INFO = "imsInfo.txt";
    private static final String SNDS_FILE = "clipsInfo.txt";
    private static final int NO_DELAYS_PER_YIELD = 16;
    private static final int MAX_FRAME_SKIPS = 5;
    private static final int BOOSTER_SPEED_1 = 10;
    private static final int BOOSTER_SPEED_2 = 15;
    private static final int BOOSTER_SPEED_SUPER = 40;
    private Graphics dbg;
    private Image dbImage = null;
    private BufferedImage figureImage = null;
    private BufferedImage bgImage = null;
    private Thread animator;
    private volatile boolean running = false;
    private volatile boolean isPaused = false;
    private long period;
    private AngryManFrame angryTop;
    private ImagesLoader imsLoader;
    private ClipsLoader clipsLoader;
    private ArrayList<Rectangle> obstacles;
    private ArrayList<Rectangle> spikes;
    private ArrayList<Rectangle> boosters;
    private ArrayList<Rectangle> traps;
    private ArrayList<Rectangle> triggers;
    private boolean isShotActive;
    private int figureX;
    private int figureY;
    private int bulletX;
    private int bulletY;
    private int bulletDirection;
    private int bulletSpeed;
    private int backgroundIndex;
    private int figureIndex;
    private int level;
    private boolean enter;
    private boolean enter2;
    private boolean go;
    private boolean triggerState1;
    private boolean triggerState2;
    private boolean triggerState3;
    private int triggerCounter1;
    private int triggerCounter2;
    private int triggerCounter3;
    private int count;

    public AngryManPanel(AngryManFrame af, long period) {
        this.angryTop = af;
        this.period = period;
        this.setDoubleBuffered(false);
        this.setBackground(Color.black);
        this.setPreferredSize(new Dimension(900, 700));
        this.setFocusable(true);
        this.requestFocus();
        this.imsLoader = new ImagesLoader("imsInfo.txt");
        this.bgImage = this.imsLoader.getImage("lighting");
        this.clipsLoader = new ClipsLoader("clipsInfo.txt");
        this.addKeyListener(this);
        this.obstacles = new ArrayList();
        this.spikes = new ArrayList();
        this.boosters = new ArrayList();
        this.traps = new ArrayList();
        this.triggers = new ArrayList();
        this.initializeGameVars();
        this.initializeGameComponents();
        JOptionPane.showMessageDialog(this, "Welcome to ANGRY MAN!\nCreated by Tom Matsliach and fixed by Andrew Lee.\nThe code was revised for a better structure and efficiency.\nAfter a long day at work grading papers, Mr. Ulmer walks home frustrated\nbecause his students performed poorly on his test.");
        JOptionPane.showMessageDialog(this, "But that was only the beginning of his frustrations,\nbecause that day Angry Man was BORN!");
        this.clipsLoader.play("storm", false);
        this.setVisible(true);
    }

    private void initializeGameVars() {
        this.figureX = -50;
        this.figureY = 440;
        this.bulletDirection = 0;
        this.backgroundIndex = 0;
        this.figureIndex = 0;
        this.level = 0;
        this.enter = true;
        this.enter2 = true;
        this.go = true;
        this.triggerState1 = true;
        this.triggerState2 = true;
        this.triggerState3 = true;
        this.triggerCounter1 = 0;
        this.triggerCounter2 = 0;
        this.triggerCounter3 = 0;
        this.count = 0;
    }

    private void initializeGameComponents() {
        this.obstacles.add(new Rectangle(150, 100, 720, 20));
        this.obstacles.add(new Rectangle(850, 110, 20, 530));
        this.obstacles.add(new Rectangle(150, 620, 700, 20));
        this.obstacles.add(new Rectangle(155, 210, 145, 10));
        this.obstacles.add(new Rectangle(300, 170, 200, 50));
        this.obstacles.add(new Rectangle(540, 170, 100, 50));
        this.obstacles.add(new Rectangle(680, 170, 100, 50));
        this.obstacles.add(new Rectangle(680, 175, 20, 400));
        this.obstacles.add(new Rectangle(150, 575, 550, 20));
        this.obstacles.add(new Rectangle(580, 175, 20, 350));
        this.obstacles.add(new Rectangle(150, 400, 450, 20));
        this.obstacles.add(new Rectangle(150, 260, 20, 280));
        this.spikes.add(new Rectangle(750, 420, 100, 30));
        this.spikes.add(new Rectangle(680, 520, 80, 50));
        this.spikes.add(new Rectangle(644, 320, 40, 20));
        this.spikes.add(new Rectangle(600, 480, 40, 20));
        this.spikes.add(new Rectangle(400, 510, 70, 70));
        this.spikes.add(new Rectangle(240, 420, 50, 80));
        this.spikes.add(new Rectangle(490, 220, 10, 140));
        this.spikes.add(new Rectangle(490, 280, 75, 50));
        this.spikes.add(new Rectangle(465, 260, 10, 140));
        this.spikes.add(new Rectangle(300, 235, 120, 140));
        this.spikes.add(new Rectangle(220, 235, 80, 20));
        this.spikes.add(new Rectangle(170, 300, 100, 50));
        this.boosters.add(new Rectangle(640, 180, 40, 40));
        this.boosters.add(new Rectangle(780, 180, 70, 40));
        this.boosters.add(new Rectangle(150, 220, 20, 40));
        this.boosters.add(new Rectangle(150, 540, 20, 40));
        this.boosters.add(new Rectangle(150, 590, 20, 40));
        this.traps.add(new Rectangle(150, 100, 20, 120));
        this.traps.add(new Rectangle(300, 220, 20, 15));
        this.triggers.add(new Rectangle(140, 220, 40, 40));
        this.triggers.add(new Rectangle(140, 540, 40, 40));
        this.triggers.add(new Rectangle(140, 590, 40, 40));
    }

    public void addNotify() {
        super.addNotify();
        this.startGame();
    }

    private void startGame() {
        if(this.animator == null || !this.running) {
            this.animator = new Thread(this);
            this.animator.start();
        }

    }

    public void resumeGame() {
        this.isPaused = false;
    }

    public void pauseGame() {
        this.isPaused = true;
    }

    public void stopGame() {
        this.clipsLoader.stop("song");
        this.clipsLoader.stop("victory");
        this.running = false;
    }

    public void run() {
        long overSleepTime = 0L;
        int noDelays = 0;
        long excess = 0L;
        long beforeTime = System.nanoTime();
        this.running = true;

        while(this.running) {
            this.update();
            this.render();
            this.paintScreen();
            long afterTime = System.nanoTime();
            long timeDiff = afterTime - beforeTime;
            long sleepTime = this.period - timeDiff - overSleepTime;
            if(sleepTime > 0L) {
                try {
                    Thread.sleep(sleepTime / 1000000L);
                } catch (InterruptedException var15) {
                    ;
                }

                overSleepTime = System.nanoTime() - afterTime - sleepTime;
            } else {
                excess -= sleepTime;
                overSleepTime = 0L;
                ++noDelays;
                if(noDelays >= 16) {
                    Thread.yield();
                    noDelays = 0;
                }
            }

            beforeTime = System.nanoTime();

            for(int skips = 0; excess > this.period && skips < 5; ++skips) {
                excess -= this.period;
                this.update();
            }
        }

        System.exit(0);
    }

    private void update() {
        if(!this.isPaused) {
            if(this.isShotActive) {
                if(this.bulletDirection == 0) {
                    this.bulletX += this.bulletSpeed;
                } else if(this.bulletDirection == 1) {
                    this.bulletX -= this.bulletSpeed;
                } else if(this.bulletDirection == 2) {
                    this.bulletY -= this.bulletSpeed;
                } else if(this.bulletDirection == 3) {
                    this.bulletY += this.bulletSpeed;
                }
            }

            if(this.level == 0) {
                if(this.figureX < 460) {
                    this.figureX += 3;
                } else if(this.figureX == 460 && this.enter) {
                    if(this.figureIndex == 0) {
                        this.clipsLoader.play("scream", false);
                    }

                    if(this.figureIndex != 3) {
                        ++this.figureIndex;
                    } else {
                        this.figureX += 10;
                        this.enter = false;
                    }

                    if(this.figureIndex != 1) {
                        try {
                            Thread.sleep(this.period / 20000L);
                        } catch (InterruptedException var2) {
                            ;
                        }
                    }
                } else if(this.figureX > 460) {
                    this.figureX += 3;
                    if(this.figureX > 950) {
                        this.level = 1;
                        this.enter = true;
                    }
                }
            }

            if(this.level == 1) {
                if(this.enter) {
                    this.backgroundIndex = 1;
                    this.figureIndex = 3;
                    this.figureY = 120;
                    this.figureX = -50;
                    this.enter = false;
                    this.clipsLoader.play("song", true);
                }

                if(this.enter2) {
                    if(this.figureX < 150) {
                        this.figureX += 3;
                    } else {
                        this.enter2 = false;
                        JOptionPane.showMessageDialog(this, "Use space and the arrows\nto direct the lighting ball to free Angry Man.");
                    }
                } else if(!this.triggerState1 && !this.triggerState2 && !this.triggerState3) {
                    if(this.figureX > 0) {
                        this.figureX -= 5;
                    } else {
                        this.figureY -= 5;
                    }

                    if(this.figureY < -300) {
                        ++this.level;
                    }
                } else {
                    if(((Rectangle)this.traps.get(0)).contains(this.bulletX, this.bulletY)) {
                        this.isShotActive = false;
                    }

                    if(this.triggerCounter1 > 0 && ((Rectangle)this.traps.get(1)).contains(this.bulletX, this.bulletY)) {
                        this.isShotActive = false;
                    }

                    if(this.triggerState1 && this.isShotActive && ((Rectangle)this.triggers.get(0)).contains(this.bulletX, this.bulletY)) {
                        this.isShotActive = false;
                        ++this.triggerCounter1;
                        if(this.triggerCounter1 == 3) {
                            this.clipsLoader.play("miss", false);
                            this.triggerState1 = false;
                        }
                    }

                    if(this.triggerState2 && this.isShotActive && ((Rectangle)this.triggers.get(1)).contains(this.bulletX, this.bulletY)) {
                        this.isShotActive = false;
                        ++this.triggerCounter2;
                        if(this.triggerCounter2 == 3) {
                            this.clipsLoader.play("miss", false);
                            this.triggerState2 = false;
                        }
                    }

                    if(this.triggerState3 && this.isShotActive && ((Rectangle)this.triggers.get(2)).contains(this.bulletX, this.bulletY)) {
                        this.isShotActive = false;
                        ++this.triggerCounter3;
                        if(this.triggerCounter3 == 3) {
                            this.clipsLoader.play("miss", false);
                            this.triggerState3 = false;
                        }
                    }

                    int index;
                    for(index = 0; index <= this.obstacles.size() - 1; ++index) {
                        if(((Rectangle)this.obstacles.get(index)).contains(this.bulletX, this.bulletY)) {
                            this.isShotActive = false;
                        }
                    }

                    for(index = 0; index <= this.spikes.size() - 1; ++index) {
                        if(((Rectangle)this.spikes.get(index)).contains(this.bulletX, this.bulletY)) {
                            this.isShotActive = false;
                        }
                    }

                    for(index = 0; index <= this.boosters.size() - 1; ++index) {
                        if(index == 0) {
                            if(((Rectangle)this.boosters.get(index)).contains(this.bulletX, this.bulletY)) {
                                this.bulletSpeed = 10;
                            }
                        } else if(index == 1) {
                            if(((Rectangle)this.boosters.get(index)).contains(this.bulletX, this.bulletY)) {
                                this.bulletSpeed = 15;
                            }
                        } else if(((Rectangle)this.boosters.get(index)).contains(this.bulletX, this.bulletY)) {
                            this.bulletSpeed = 40;
                        }
                    }
                }

                if(this.level == 2) {
                    this.clipsLoader.stop("song");
                    this.backgroundIndex = 2;
                    this.clipsLoader.play("victory", false);
                    ++this.level;
                    JOptionPane.showMessageDialog(this, "To be continued");
                }
            }
        }

    }

    private void render() {
        if(this.dbImage == null) {
            this.dbImage = this.createImage(900, 700);
            if(this.dbImage == null) {
                System.out.println("dbImage is null");
                return;
            }

            this.dbg = this.dbImage.getGraphics();
        }

        this.setBackgroundImage();
        if(this.bgImage == null) {
            this.dbg.setColor(Color.DARK_GRAY);
            this.dbg.fillRect(0, 0, 900, 700);
        } else {
            this.dbg.drawImage(this.bgImage, 0, 0, 900, 700, this);
        }

        this.setFigureImage();
        this.dbg.drawImage(this.figureImage, this.figureX, this.figureY, 100, 100, this);
        if(this.level == 1) {
            this.dbg.setColor(Color.DARK_GRAY);

            int index;
            for(index = 0; index <= this.obstacles.size() - 1; ++index) {
                this.dbg.fillRect(((Rectangle)this.obstacles.get(index)).x, ((Rectangle)this.obstacles.get(index)).y, ((Rectangle)this.obstacles.get(index)).width, ((Rectangle)this.obstacles.get(index)).height);
            }

            for(index = 0; index <= this.spikes.size() - 1; ++index) {
                this.dbg.fillRect(((Rectangle)this.spikes.get(index)).x, ((Rectangle)this.spikes.get(index)).y, ((Rectangle)this.spikes.get(index)).width, ((Rectangle)this.spikes.get(index)).height);
            }

            this.dbg.setColor(Color.green);

            for(index = 0; index <= this.boosters.size() - 1; ++index) {
                this.dbg.fillOval(((Rectangle)this.boosters.get(index)).x, ((Rectangle)this.boosters.get(index)).y, ((Rectangle)this.boosters.get(index)).width, ((Rectangle)this.boosters.get(index)).height);
            }

            this.dbg.setColor(Color.black);

            for(index = 0; index <= this.traps.size() - 1; ++index) {
                if(index == 0 && !this.enter2 && (this.triggerState1 || this.triggerState2 || this.triggerState3)) {
                    this.dbg.fillRect(((Rectangle)this.traps.get(index)).x, ((Rectangle)this.traps.get(index)).y, ((Rectangle)this.traps.get(index)).width, ((Rectangle)this.traps.get(index)).height);
                } else if(index == 1 && this.triggerCounter1 > 0) {
                    this.dbg.fillRect(((Rectangle)this.traps.get(index)).x, ((Rectangle)this.traps.get(index)).y, ((Rectangle)this.traps.get(index)).width, ((Rectangle)this.traps.get(index)).height);
                }
            }

            for(index = 0; index <= this.triggers.size() - 1; ++index) {
                byte triggerDeterminant = 0;
                switch(index) {
                    case 0:
                        triggerDeterminant = 1;
                        break;
                    case 1:
                        triggerDeterminant = 2;
                        break;
                    case 2:
                        triggerDeterminant = 3;
                }

                if(triggerDeterminant == 1) {
                    if(this.triggerCounter1 == 0) {
                        this.dbg.setColor(Color.red);
                    } else if(this.triggerCounter1 == 1) {
                        this.dbg.setColor(Color.yellow);
                    } else {
                        if(this.triggerCounter1 != 2) {
                            continue;
                        }

                        this.dbg.setColor(Color.pink);
                    }
                } else if(triggerDeterminant == 2) {
                    if(this.triggerCounter2 == 0) {
                        this.dbg.setColor(Color.red);
                    } else if(this.triggerCounter2 == 1) {
                        this.dbg.setColor(Color.yellow);
                    } else {
                        if(this.triggerCounter2 != 2) {
                            continue;
                        }

                        this.dbg.setColor(Color.pink);
                    }
                } else if(triggerDeterminant == 3) {
                    if(this.triggerCounter3 == 0) {
                        this.dbg.setColor(Color.red);
                    } else if(this.triggerCounter3 == 1) {
                        this.dbg.setColor(Color.yellow);
                    } else {
                        if(this.triggerCounter3 != 2) {
                            continue;
                        }

                        this.dbg.setColor(Color.pink);
                    }
                }

                this.dbg.fillOval(((Rectangle)this.triggers.get(index)).x, ((Rectangle)this.triggers.get(index)).y, ((Rectangle)this.triggers.get(index)).width, ((Rectangle)this.triggers.get(index)).height);
            }
        }

        if(this.isShotActive) {
            if(this.go) {
                this.dbg.setColor(Color.magenta);
                ++this.count;
                if(this.count == 5) {
                    this.count = 0;
                    this.go = false;
                }
            } else {
                this.dbg.setColor(Color.blue);
                ++this.count;
                if(this.count == 5) {
                    this.count = 0;
                    this.go = true;
                }
            }

            this.dbg.fillOval(this.bulletX, this.bulletY, 10, 10);
        }

    }

    private void setBackgroundImage() {
        if(this.backgroundIndex == 0) {
            this.bgImage = this.imsLoader.getImage("lighting");
        } else if(this.backgroundIndex == 1) {
            this.bgImage = this.imsLoader.getImage("level1");
        } else if(this.backgroundIndex == 2) {
            this.bgImage = this.imsLoader.getImage("winner");
        }

    }

    private void setFigureImage() {
        if(this.figureIndex == 0) {
            this.figureImage = this.imsLoader.getImage("angry");
        } else if(this.figureIndex == 1) {
            this.figureImage = this.imsLoader.getImage("angry2");
        } else if(this.figureIndex == 2) {
            this.figureImage = this.imsLoader.getImage("angry3");
        } else if(this.figureIndex == 3) {
            this.figureImage = this.imsLoader.getImage("angry4");
        }

    }

    private void paintScreen() {
        try {
            Graphics g = this.getGraphics();
            if(g != null && this.dbImage != null) {
                g.drawImage(this.dbImage, 0, 0, (ImageObserver)null);
            }

            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        } catch (Exception var3) {
            System.out.println("Graphics context error: " + var3);
        }

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(this.level == 1) {
            if(key == 32) {
                this.isShotActive = true;
                this.bulletX = this.figureX + 40;
                this.bulletY = this.figureY + 20;
                this.bulletDirection = 0;
                this.bulletSpeed = 5;
                this.clipsLoader.play("shot", false);
            }

            if(this.isShotActive) {
                if(key == 39) {
                    this.bulletDirection = 0;
                } else if(key == 37) {
                    this.bulletDirection = 1;
                } else if(key == 38) {
                    this.bulletDirection = 2;
                } else if(key == 40) {
                    this.bulletDirection = 3;
                }
            }
        }

    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}