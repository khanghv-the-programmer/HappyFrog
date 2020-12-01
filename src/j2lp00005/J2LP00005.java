/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j2lp00005;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import component.AFrameOnImage;
import component.Animation;
import component.GameScreen;
import component.SoundPlayer;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author USER
 */
public class J2LP00005 extends GameScreen {

    /**
     * @param args the command line arguments
     */
    private BufferedImage frogs;
    private Animation frog_animation;
    public static float gravity = 0.2f;
    private Frog frog;
    private Ground ground;
    private ChimneyGroup chimneyGrp;
    private BufferedImage tutor;
    private BufferedImage no;
    private BufferedImage bronze;
    private BufferedImage silver;
    private BufferedImage gold;
    

    public static void main(String[] args) {
        new J2LP00005();

    }

    private int point = 0;

    public J2LP00005() {
        super(1280, 720);
        this.setResizable(true);
        try {
            frogs = ImageIO.read(new File("Frog.png"));
            tutor = ImageIO.read(new File("Tutorial.png"));
            no = ImageIO.read(new File("GameOverNo.png"));
            bronze = ImageIO.read(new File("Bronze.png"));
            silver = ImageIO.read(new File("Silver.png"));
            gold = ImageIO.read(new File("Gold.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        AFrameOnImage f;
        frog_animation = new Animation(70); //milisec
        f = new AFrameOnImage(0, 0, 124, 78);
        frog_animation.AddFrame(f);
        f = new AFrameOnImage(124, 0, 124, 78);
        frog_animation.AddFrame(f);
        f = new AFrameOnImage(124 * 2, 0, 124, 78);
        frog_animation.AddFrame(f);
        f = new AFrameOnImage(124, 0, 124, 78);
        frog_animation.AddFrame(f);
        frog = new Frog(350, 350, 70, 70 * 78 / 124);
        ground = new Ground();
        chimneyGrp = new ChimneyGroup();
        frog.setIsLive(true);
        BeginGame();

    }

    public void resetGame() {
        frog.setPos(350, 350);
        frog.setFallSpeed(0);
        frog.setIsLive(true);
        point = 0;
        chimneyGrp.resetChimneys();

    }
    private final static int BEGIN_SCREEN = 0;
    private final static int GAME_PLAY_SCREEN = 1;
    private final static int GAME_OVER_SCREEN = 2;
    private final static int GAME_PAUSE_SCREEN = 3;

    private int currentScreen = BEGIN_SCREEN;

    @Override
    public void GAME_UPDATE(long deltaTime) {
        if (currentScreen == BEGIN_SCREEN) {
            resetGame();
        } else if (currentScreen == GAME_PLAY_SCREEN) {

            if (frog.isIsFlying()) {
                frog_animation.Update_Me(deltaTime);
            }

            frog.update(deltaTime);
            ground.Update();
            chimneyGrp.Update();
            for (int i = 0; i < ChimneyGroup.SIZE; i++) {
                if (frog.getRect().intersects(chimneyGrp.getChimney(i).getRect()) || frog.getRect().getY() == 0) {
                    if (frog.isIsLive()) {
                        frog.collapseSound.play();
                    }
                    frog.setIsLive(false);
                }
            }
            for (int i = 0; i < chimneyGrp.SIZE; i++) {
                if (frog.getPosX() > chimneyGrp.getChimney(i).getPosX() && !chimneyGrp.getChimney(i).isIsBehindBird() && i % 2 == 0) {
                    point += 1;
                    frog.pointSound.play();
                    chimneyGrp.getChimney(i).setIsBehindBird(true);
                }
            }
            if (frog.getPosY() + frog.getH() > ground.getYGrount()) {
                frog.collapseSound.play();
                currentScreen = GAME_OVER_SCREEN;
            }

        }

    }

    @Override
    public void GAME_PAINT(Graphics2D g2) {
        g2.setColor(Color.decode("#b8daef"));
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.fillRect(0, 0, MASTER_WIDTH, MASTER_HEIGHT);
        chimneyGrp.Paint(g2);
        ground.Paint(g2);

        if (frog.isIsFlying()) {
            frog_animation.PaintAnims((int) frog.getPosX(), (int) frog.getPosY(), frogs, g2, 0, -1);
        } else {
            frog_animation.PaintAnims((int) frog.getPosX(), (int) frog.getPosY(), frogs, g2, 0, 0);
        }

        if (currentScreen == BEGIN_SCREEN) {
            g2.setColor(Color.red);
            g2.drawImage(tutor, 600, 300, null);

        }
        if (currentScreen == GAME_OVER_SCREEN) {
            g2.setColor(Color.RED);
            g2.drawString("PRESS ANY KEY TO CONTINUE!", 200, 200);
            if (point < 10) {
                g2.drawImage(no, 600, 300, null);
            } else if (point >= 10 && point < 20) {
                g2.drawImage(bronze, 200, 300, null);
            } else if (point >= 20 && point < 30) {
                g2.drawImage(silver, 200, 300, null);
            } else {
                g2.drawImage(gold, 200, 300, null);
            }
        }
        if (currentScreen == GAME_PAUSE_SCREEN) {
            g2.setColor(Color.red);
            g2.drawString("PAUSED! PRESS P TO CONTINUE", 200, 200);
        }
        g2.setColor(Color.RED);
        String total = "Score: " + Integer.toString(point) + "                                         Press P to Pause the game";

        g2.drawString(total, 20, 50);
        g2.drawString("By KhangHV Cute", 650, 710);

    }

    @Override
    public void KEY_ACTION(KeyEvent e, int Event) {
        if (Event == KEY_PRESSED) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (currentScreen == BEGIN_SCREEN) {
                    currentScreen = GAME_PLAY_SCREEN;
                } else if (currentScreen == GAME_PLAY_SCREEN) {
                    if (frog.isIsLive()) {
                        frog.fly();
                    }
                } else if (currentScreen == GAME_OVER_SCREEN) {
                    currentScreen = BEGIN_SCREEN;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_P) {
                if (currentScreen == GAME_PLAY_SCREEN) {
                    currentScreen = GAME_PAUSE_SCREEN;
                } else {
                    currentScreen = GAME_PLAY_SCREEN;
                }
            }
        }

    }

}
