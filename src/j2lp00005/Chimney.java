/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j2lp00005;

import component.Objects;
import java.awt.Rectangle;

/**
 *
 * @author USER
 */
public class Chimney extends Objects {
    
    private Rectangle rect;
    
    private boolean isBehindBird = false;
    
    public Chimney(int x, int y, int width, int height) {
        super(x, y, width, height);
        rect = new Rectangle(x, y, width, height);
    }

    public Rectangle getRect() {
        return rect;
    }

    public boolean isIsBehindBird() {
        return isBehindBird;
    }

    public void setIsBehindBird(boolean isBehindBird) {
        this.isBehindBird = isBehindBird;
    }

    
    public void update() {
        setPosX(getPosX() - 4);
        rect.setLocation((int)this.getPosX(),(int) this.getPosY());
    }
    
    public void pause()
    {
        setPosX(getPosX());
    }

}
