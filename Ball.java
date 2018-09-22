package blair_pong;
import java.awt.Point;
import java.awt.Rectangle;

public class Ball {
	private int x;
	private int y;
	private int width;
	private int height;
	private Point velocity;
	private Rectangle circle;
	
	public Ball(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		circle = new Rectangle(this.x, this.y, this.width, this.height);
		velocity = new Point(10, -5);
		/*System.out.println(getCircle().x + " " + getCircle().y);
		setVelocity(new Point(10, -5));*/
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getX(){
		return this.x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public void setheight(int height){
		this.height = height;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	public void setCircle(Rectangle circle){
		this.circle = circle;
	}
	
	public void setCircle(int x, int y, int width, int height){
		this.circle = new Rectangle(x, y, width, height);
	}
	
	public Rectangle getCircle(){
		return this.circle;
	}

	public Point getVelocity() {
		return this.velocity;
	}

	public void setVelocity(Point velocity) {
		this.velocity = velocity;
	}
	
}