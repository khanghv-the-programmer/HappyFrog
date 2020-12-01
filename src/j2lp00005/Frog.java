/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j2lp00005;

import component.Objects;
import component.SoundPlayer;
import java.awt.Rectangle;
import java.io.File;

/**
 *
 * @author USER
 */
public class Frog extends Objects {

    private float fallSpeed = 0;
    private boolean isFlying = false;
    private Rectangle rect;
    private boolean isLive = true;
    public SoundPlayer flapSound = new SoundPlayer(new File("fap.wav"));
    public SoundPlayer collapseSound = new SoundPlayer(new File("fall.wav"));
    public SoundPlayer pointSound = new SoundPlayer(new File("getpoint.wav"));
    public SoundPlayer op = new SoundPlayer(new File("op.mp3"));

    public Frog(int firstx, int firsty, int w, int h) {
        super(firstx, firsty, w, h);
        rect = new Rectangle(firstx, firsty, w, h);
    }

    public boolean isIsLive() {
        return isLive;
    }

    public void setIsLive(boolean isLive) {
        this.isLive = isLive;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void update(long deltatime) {
        fallSpeed += J2LP00005.gravity;
        this.setPosY(this.getPosY() + fallSpeed);
        this.rect.setLocation((int) this.getPosX(), (int) this.getPosY());
        if (fallSpeed < 0) //đang đi lên
        {
            isFlying = true;
        } else {
            isFlying = false;
        }
    }

    public void fly() {
        fallSpeed = -4;
        flapSound.play();
    }

    public boolean isIsFlying() {
        return isFlying;
    }

    public void setFallSpeed(float fallSpeed) {
        
        this.fallSpeed = fallSpeed;
    }

}
