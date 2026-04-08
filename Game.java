import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.event.*; 


public class Game  extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{

	
	private BufferedImage back; 
	private int key, count, score, lives; 
	private char screen;
	private Hook hook;
	private ArrayList<Fish>fish;
	private ImageIcon background;
	private ImageIcon BerryGood;
	private ImageIcon Slose;







	
	public Game() {
		new Thread(this).start();	
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		key =-1; 
		screen = 'G'; 
		hook = new Hook();
		fish = new ArrayList<Fish>();
		count = 0;
		score = 0;
		lives = 3;
		background = new ImageIcon("Polka.png");
		BerryGood = new ImageIcon("BerryGood.png");
		Slose = new ImageIcon("Slose.png");
		
		

		
	
	}

	
	
	public void run()
	   {
	   	try
	   	{
	   		while(true)
	   		{
	   		   Thread.currentThread().sleep(5);
	            repaint();
	         }
	      }
	   		catch(Exception e)
	      {
	      }
	  	}
	
	public void screen(Graphics g2d) {
    switch (screen) {
        case 'S':
            break;
        case 'G':
		count++;
		g2d.setColor(Color.BLACK);
g2d.setFont(new Font("Arial", Font.BOLD, 30));
g2d.drawString("Score: " + score, 50, 50);
g2d.drawString("Lives: " + lives, 50, 90);

		getFish(g2d);
		drawBasket(g2d);
		fish.move();
		if(!fish.isEmpty()){
			drawFish(g2d);
		}

checkCollisions();

	
	removeFruit();
            break;
        case 'W':
            // Win screen
			g2d.drawImage(BerryGood.getImage(), 0, 0, getWidth(), getHeight(), this);
Color pastelPink = new Color(255, 204, 229); // Soft pastel pink

			g2d.setColor(pastelPink); // button
g2d.fillRoundRect(550, 400, 250, 60, 30, 30); // x, y, width, height, arcWidth, arcHeight

g2d.setColor(Color.WHITE); 
g2d.setFont(new Font("Arial", Font.BOLD, 24));
g2d.drawString("Press to play again", 565, 440);
 
            break;
        case 'L':
            // Lose screen
g2d.drawImage(Slose.getImage(), 0, 0, getWidth(), getHeight(), this);
Color pasteelPink = new Color(255, 204, 229); // Soft pastel pink

g2d.setColor(pasteelPink); // button

g2d.fillRoundRect(550, 400, 250, 60, 30, 30); // x, y, width, height, arcWidth, arcHeight

g2d.setColor(Color.WHITE); 
g2d.setFont(new Font("Arial", Font.BOLD, 24));
g2d.drawString("Press to play again", 565, 440);

            break;
        default:
            break;
    }
}

		





	
	
	
	public void paint(Graphics g){
		
		Graphics2D twoDgraph = (Graphics2D) g; 
		if( back ==null)
			back=(BufferedImage)( (createImage(getWidth(), getHeight()))); 
		

		Graphics g2d = back.createGraphics();
	
		g2d.clearRect(0,0,getSize().width, getSize().height);
		
		g2d.setFont( new Font("Broadway", Font.BOLD, 50));
		g2d.drawImage(background.getImage(),0,0,getWidth(),getHeight(),this);
		screen(g2d);
	
		twoDgraph.drawImage(back, null, 0, 0);

	}

	public void drawBasket(Graphics g2D) {
    g2D.drawImage(
        hook.getPic().getImage(),hook.getX(),hook.getY(),hook.getWidth(),
		hook.getHeight(),this
    );
	for (Fish b : fish) {
    b.setDy(1);
}

}

public void getFish(Graphics g2d) {
    if (count % 200 == 0) {
        // Get the screen width
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

        // Define the padding
        int padding = 48;

        // Generate a random x-coordinate within the screen width, ensuring the apple is fully displayed
        int randX = (int) (Math.random() * (screenWidth - 40 - 2 * padding));
	}
       
public void removeOOBBlackberries(ArrayList<Fish> fish) {
    for(int i = 0; i < fish.size(); i++) {
        Fish fish = fish.get(i);
        if (fish.getY() > 1000) {
            fish.remove(i);
            i--;
        }
    }
}

public void checkCollisions() {
    checkFruitCollisions(fish, 1);
    checkGameStatus();
}

private void checkFruitCollisions(ArrayList<Fish> blackberries, int points) {
    for (int i = 0; i < fish.size(); i++) {
        Fish fish = fish.get(i);
        if (fish.collidesWith(hook)) {
            
    if (fish.getImageName().equals("Custard.png")) {
    lives -= 1;
} else {
    score += points;
}



            fish.remove(i);
            i--; // Adjust index after removal
        }
    }
}
public void removeFruit(){
	removeOOBBlackberries(fish);

}

private void checkGameStatus(){
	if (lives<=0){
		System.out.println("Game Over!");
		screen = 'L';
		// add additional game over logic here
	}
	else if (score>=15){
		System.out.println("You Win!");
		screen = 'W';
		//additional win logic here
	}
}

	//DO NOT DELETE
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}




//DO NOT DELETE
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		key= e.getKeyCode();
		System.out.println(key);
		if (key == 37){
			hook.setDx(-3);

		}
		if (key == 39){
			hook.setDx(3);
			
		}
		
	
	}


	//DO NOT DELETE
	@Override
	public void keyReleased(KeyEvent e) {
		
		if (key == 37){
			hook.setDx(0);
			
		}
		
		if (key == 39){
			hook.setDx(0);
			
		}
	}



	@Override
	public void mouseDragged(MouseEvent m) {
		// TODO Auto-generated method stub
	}



	@Override
	public void mouseMoved(MouseEvent m) {
		// TODO Auto-generated method stub
		 hook.setX(m.getX() - hook.getWidth() / 2); 

	}



	@Override
	public void mouseClicked(MouseEvent m) {
		// TODO Auto-generated method stub
		if (screen == 'W' || screen == 'L'){
			score=0;
			lives=3;
			screen = 'G';
		}
	}



	@Override
	public void mousePressed(MouseEvent m) {
		// TODO Auto-generated method stub
	}



	@Override
	public void mouseReleased(MouseEvent m) {
		// TODO Auto-generated method stub
	}



	@Override
	public void mouseEntered(MouseEvent m) {
		// TODO Auto-generated method stub
	}



	@Override
	public void mouseExited(MouseEvent m) {
		// TODO Auto-generated method stub
	}
	
	
	

	
}
