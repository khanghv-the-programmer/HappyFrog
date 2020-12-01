/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j2lp00005;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author USER
 */
public class Ground {

    private BufferedImage groundImg;
    private int x1, y1, x2, y2;

    public Ground() {
        try {
            groundImg = ImageIO.read(new File("Ground.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        x1 = 0;
        y1 = 650;
        x2 = x1 + 1500;
        y2 = 650;

    }

    public void Update() {
        x1 -= 4;
        x2 -= 4;
        if (x2 < 0) {
            x1 = x2 + 1500;
        }
        if (x1 < 0) {
            x2 = x1 + 1500;
        }
        
    }
    
    public void Pause()
    {
        x1 -= 0;
        x2 -= 0;
    }

    public void Paint(Graphics2D g2) {
        g2.drawImage(groundImg, x1, y1, null);
        g2.drawImage(groundImg, x2, y2, null);
    }

    public int getYGrount() {
        return y1;
    }
}
