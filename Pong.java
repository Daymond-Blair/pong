package blair_pong;
import java.applet.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Pong extends Applet implements Runnable, KeyListener, MouseListener {
	public boolean alive = false;
	public int mouseX;
	public int mouseY;
	public final int WIDTH = 800;
	public final int HEIGHT = 600;
	public Paddle paddle1;
	public Paddle paddle2;
	public Ball puck;
	public boolean p1Pressed = false;
	public boolean p2Pressed = false;
	
	public int score1 = 0;
	public int score2 = 0;
	
	private Image bImage;
	private Graphics b;
	
	public void init(){
		paddle1 = new Paddle(20, HEIGHT/2, 10, 100, 5);
		paddle2 = new Paddle(WIDTH - 20, HEIGHT/2, 10, 100, 5);
		puck = new Ball(WIDTH/2, HEIGHT/2, 10, 10); 
		setBackground(Color.black);
		addKeyListener(this);
		addMouseListener(this);
	}
	
	public void start(){
		Thread th = new Thread (this);
		th.start();
	}
	
	public void run(){
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		
		while(true){
			//System.out.println(p1Pressed);
			moveP1();
			moveP2();
			moveBall();
			checkCollision();
			repaint();
			try{
				Thread.sleep(30);
			} catch (InterruptedException ex){}
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}
	}
	
	private void moveP1() {
		
		if(paddle1.getUp() && p1Pressed){
			int newY = (paddle1.getPad().y >= paddle1.getSpeed()) ? paddle1.getPad().y - paddle1.getSpeed() : paddle1.getPad().y;
			paddle1.setPad(paddle1.getPad().x, newY, paddle1.getPad().width, paddle1.getPad().height);
		}
		if(!paddle1.getUp() && p1Pressed){
			int newY = (paddle1.getPad().y <= HEIGHT - paddle1.getPad().height) ? paddle1.getPad().y + paddle1.getSpeed() : paddle1.getPad().y;
			paddle1.setPad(paddle1.getPad().x, newY, paddle1.getPad().width, paddle1.getPad().height);
		}
	}

	private void moveP2() {
		
		if(paddle2.getUp() && p2Pressed){
			int newY = (paddle2.getPad().y >= paddle2.getSpeed()) ? paddle2.getPad().y - paddle2.getSpeed() : paddle2.getPad().y;
			paddle2.setPad(paddle2.getPad().x, newY, paddle2.getPad().width, paddle2.getPad().height);
		}
		if(!paddle2.getUp() && p2Pressed){
			int newY = (paddle2.getPad().y <= HEIGHT - paddle2.getPad().height) ? paddle2.getPad().y + paddle2.getSpeed() : paddle2.getPad().y;
			paddle2.setPad(paddle2.getPad().x, newY, paddle2.getPad().width, paddle2.getPad().height);

		}
	}

	private void moveBall() {
		puck.setCircle(puck.getCircle().x + puck.getVelocity().x, puck.getCircle().y + puck.getVelocity().y, puck.getCircle().width, puck.getCircle().height);
		if(puck.getCircle().y <= 0 || puck.getCircle().y >= HEIGHT)
			puck.setVelocity(new Point(puck.getVelocity().x, puck.getVelocity().y));
		if(puck.getCircle().x <= 0){
			score2 ++;
			reset();
		}
	}
	
	private void reset(){
		puck.setCircle(WIDTH/2, HEIGHT/2, puck.getCircle().width, puck.getCircle().height);
	}

	public void update(Graphics g){
		if (bImage == null){
			bImage = createImage(this.getSize().width, this.getSize().height);
			b = bImage.getGraphics();
		}
		
		b.setColor(getBackground());
		b.fillRect(0, 0, this.getSize().width, this.getSize().height);
		
		b.setColor(getForeground());
		paint(b);
		
		g.drawImage(bImage, 0, 0, this);
		
	}
	
	public void paint(Graphics g){
		//draw scene
		g.setColor(Color.white);
		g.fillRect(paddle1.getPad().x, paddle1.getPad().y, paddle1.getPad().width, paddle1.getPad().height);
		g.fillRect(paddle2.getPad().x, paddle2.getPad().y, paddle2.getPad().width, paddle2.getPad().height);
		g.fillOval(puck.getCircle().x, puck.getCircle().y, puck.getCircle().width, puck.getCircle().height);
		//System.out.println(puck.getCircle().x + " " + puck.getCircle().y);
		g.drawString(score1 + " | " + score2, WIDTH/2 - 20, 20);
	}
	
	public void checkCollision(){
		if(paddle1.getPad().intersects(puck.getCircle()) || (paddle1.getPad().intersects(puck.getCircle()))) {
				puck.setVelocity(new Point(puck.getVelocity().x, puck.getVelocity().y));
		}
		if(paddle1.getPad().intersects(puck.getCircle())){
				if(puck.getCircle().y < paddle1.getPad().y + paddle1.getPad().getHeight() / 3)
					puck.setVelocity(new Point(puck.getVelocity().x, -10));
				else if (puck.getCircle().y > paddle1.getPad().y + paddle1.getPad().getHeight() - paddle1.getPad().getHeight() / 3) puck.setVelocity(new Point(puck.getVelocity().x, -10));
					puck.setVelocity(new Point(puck.getVelocity().x, 10));
		}
					else if (puck.getVelocity().y < 0)
						puck.setVelocity(new Point(puck.getVelocity().x, -5));
	
		
		else if(paddle2.getPad().intersects(puck.getCircle())){
			if(puck.getCircle().y < paddle2.getPad().y + paddle2.getPad().getHeight() / 3)
				puck.setVelocity(new Point(puck.getVelocity().x, -10));
			else if (puck.getCircle().y > paddle2.getPad().y + paddle2.getPad().getHeight() - paddle2.getPad().getHeight() / 3) puck.setVelocity(new Point(puck.getVelocity().x, -10));
				puck.setVelocity(new Point(puck.getVelocity().x, 10));}
				//else if(puck.getVelocity().y);
			}
		
	
	public void keyTyped(KeyEvent e){}
	
	public void keyReleased(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_W:
			p1Pressed = false;
			break;
		case KeyEvent.VK_S:
			p1Pressed = false;
			break;
		case KeyEvent.VK_UP:
			p2Pressed = false;
			break;
		case KeyEvent.VK_DOWN:
			p2Pressed = false;
			break;
		}
	}
	
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_W:
			System.out.println("HERE");
			paddle1.setUp(true);
			p1Pressed = true;
			break;
		case KeyEvent.VK_S:
			paddle1.setUp(false);
			p1Pressed = true;
			break;
		case KeyEvent.VK_UP:
			paddle2.setUp(true);
			p2Pressed = true;
			break;
		case KeyEvent.VK_DOWN:
			paddle2.setUp(false);
			p2Pressed = true;
			break;
		case KeyEvent.VK_SPACE:
			if(score2 > score1)
				puck.setVelocity(new Point(-10, -5));
			else
				puck.setVelocity(new Point(10, -5));
		}
	}
	
	public void mouseEntered(MouseEvent e){}
	
	public void mouseExited(MouseEvent e){}
	
	public void mousePressed(MouseEvent e){}
	
	public void mouseReleased(MouseEvent e){}
	
	public void mouseClicked(MouseEvent e){}
	
	public void restartGame(){}


	
}
 