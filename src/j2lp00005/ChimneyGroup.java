/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j2lp00005;

import component.QueueList;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author USER
 */
public class ChimneyGroup {

    private QueueList<Chimney> chimneys;

    private BufferedImage chimneyImg, chimneyImgHigh;
    public static int SIZE = 6;
    private int topChimneyY = -250;
    private int bottomChimneyY = 300;
    public int getRandomHeight()
    {
        Random random = new Random();
        int a = 0;
        a = random.nextInt(200);
        return a;
    }
    public Chimney getChimney(int i)
    {
        return chimneys.get(i);
    }
    public ChimneyGroup() {
        try {
            chimneyImg = ImageIO.read(new File("chimney.png"));
            chimneyImgHigh = ImageIO.read(new File("chimney_.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        chimneys = new QueueList<>();
        Chimney chimney;
        for (int i = 0; i < SIZE/2; i++) {
            int height = getRandomHeight();
            chimney = new Chimney(1320 + i * 450, bottomChimneyY + height , 74, 400);
            chimneys.push(chimney);
            chimney = new Chimney(1320 + i * 450, topChimneyY + height, 74, 400);
            chimneys.push(chimney);

        }
    }
    
    public void resetChimneys()
    {
        chimneys = new QueueList<>();
        Chimney chimney;
        for (int i = 0; i < SIZE/2; i++) {
            int height = getRandomHeight();
            chimney = new Chimney(1320 + i * 450, bottomChimneyY + height , 74, 400);
            chimneys.push(chimney);
            chimney = new Chimney(1320 + i * 450, topChimneyY + height, 74, 400);
            chimneys.push(chimney);

        }
    }
    
    

    public void Update() {
        for (int i = 0; i < SIZE; i++) {
            chimneys.get(i).update();
        }
        if (chimneys.get(0).getPosX() < -74) {
            int height = getRandomHeight();
            Chimney chim;
            chim = chimneys.pop();
            chim.setPosX(chimneys.get(4).getPosX() + 450);
            chim.setPosY(bottomChimneyY + height);
            chim.setIsBehindBird(false);
            chimneys.push(chim);
            chim = chimneys.pop();
            chim.setPosX(chimneys.get(4).getPosX());
            chim.setPosY(topChimneyY + height);
            chim.setIsBehindBird(false);
            chimneys.push(chim);
        }
    }

    public void Paint(Graphics2D g2) {
        for (int i = 0; i < SIZE; i++) {
            if (i % 2 == 0) {
                g2.drawImage(chimneyImg, (int) chimneys.get(i).getPosX(), (int) chimneys.get(i).getPosY(), null);

            } else {
                g2.drawImage(chimneyImgHigh, (int) chimneys.get(i).getPosX(), (int) chimneys.get(i).getPosY(), null);
            }
        }
    }
    
    void pause()
    {
        for (int i = 0; i < SIZE; i++) {
            chimneys.get(i).pause();
        }
    }

}
