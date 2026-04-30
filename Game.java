import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.event.*; 

public class Game extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private BufferedImage back; 
    private int key, count, score, lives; 
    private char screen;
    private Hook hook;
    private ArrayList<Fish> fish;
    private ImageIcon background;
    private ImageIcon WinGood;
    private ImageIcon Slose;

    public Game() {
        new Thread(this).start();    
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        key = -1; 
        screen = 'G'; 
        hook = new Hook();
        fish = new ArrayList<Fish>();
        count = 0;
        score = 0;
        lives = 3;

        background = new ImageIcon("Backgroundy2k.png");
        WinGood = new ImageIcon("WinGood.png"); 
        Slose = new ImageIcon("Slose.png"); // change image file
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(5);
                repaint();
            }
        } catch (Exception e) {}
    }

    public void screen(Graphics g2d) {
        switch (screen) {

        case 'G':
            count++;

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
            g2d.drawString("Score: " + score, 50, 50);
            g2d.drawString("Lives: " + lives, 50, 90);

            getFish();
            drawHook(g2d);

            hook.move();

            for (Fish f : fish) {
                f.move();
            }

            drawFish(g2d);
            checkCollisions();
            removeOOBFish();
            break;

        case 'W':
            g2d.drawImage(WinGood.getImage(), 0, 0, getWidth(), getHeight(), this);
        
            break;

        case 'L':
            g2d.drawImage(Slose.getImage(), 0, 0, getWidth(), getHeight(), this);
            g2d.setColor(new Color(255, 204, 229));
            g2d.fillRoundRect(550, 400, 250, 60, 30, 30);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            g2d.drawString("Press to play again", 565, 440);
            break;
        }
    }

    public void paint(Graphics g) {
        Graphics2D twoDgraph = (Graphics2D) g; 
        if (back == null)
            back = (BufferedImage)(createImage(getWidth(), getHeight())); 

        Graphics g2d = back.createGraphics();
        g2d.clearRect(0, 0, getSize().width, getSize().height);

        g2d.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
        screen(g2d);

        twoDgraph.drawImage(back, null, 0, 0);
    }

    public void drawHook(Graphics g2D) {
        g2D.drawImage(
            hook.getPic().getImage(),
            hook.getX(),
            hook.getY(),
            hook.getWidth(),
            hook.getHeight(),
            this
        );
    }

    public void getFish() {
        if (count % 200 == 0) {
            fish.add(new Fish());
        }
    }

    public void drawFish(Graphics g) {
        for (Fish f : fish) {
            g.drawImage(f.getFish1().getImage(), f.getX(), f.getY(), f.getWidth(), f.getHeight(), this);
        }
		
    }

    public void removeOOBFish() {
        for (int i = 0; i < fish.size(); i++) {
            if (fish.get(i).getX() > 2000) {
                fish.remove(i);
                i--;
            }
        }
    }

    public void checkCollisions() {
        for (int i = 0; i < fish.size(); i++) {
            if (fish.get(i).collidesWith(hook)) {
                score++;
                fish.remove(i);
                hook.reset();
                i--;
            }
        }
        checkGameStatus();
    }

    private void checkGameStatus() {
        if (lives <= 0) {
            screen = 'L';
        } else if (score >= 5) {
            screen = 'W';
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            hook.drop();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseMoved(MouseEvent m) {
        hook.setX(m.getX() - hook.getWidth() / 2);
    }

    @Override
    public void mouseClicked(MouseEvent m) {
        if (screen == 'W' || screen == 'L') {
            score = 0;
            lives = 3;
            fish.clear();
            hook.reset();
            screen = 'G';
        }
    }

    public void keyTyped(KeyEvent e) {}
    public void mouseDragged(MouseEvent m) {}
    public void mousePressed(MouseEvent m) {}
    public void mouseReleased(MouseEvent m) {}
    public void mouseEntered(MouseEvent m) {}
    public void mouseExited(MouseEvent m) {}
}
