import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Shape {
	private int x; //Position
	private int y;
	private int dx=genRandomNum(-4,4); //Rate of movement
	private int dy=genRandomNum(-4,4);
	private int cdx=dx; //Change in dx due to collision
	private int cdy=dy;
	private int width; //Size
	private int height;
	private String type="none"; //Type identifier
	private boolean visible=true;
	protected List<Shape> collidedWith = new ArrayList<Shape>(); //record of collisions to avoid duplicates
	
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	//Getters & Setters
	public int getX() {return x;}
	public void setX(int x) {this.x = x;}
	public int getY() {return y;}
	public void setY(int y) {this.y = y;}
	public int getDx() {return dx;}
	public void setDx(int dx) {this.dx = dx;}
	public int getDy() {return dy;}
	public void setDy(int dy) {this.dy = dy;}
	public int getCdx() {return cdx;}
	public void setCdx(int cdx) {this.cdx = cdx;}
	public int getCdy() {return cdy;}
	public void setCdy(int cdy) {this.cdy = cdy;}
	public int getHeight() {return height;}
	public void setHeight(int height) {this.height = height;}
	public int getWidth() {return width;}
	public void setWidth(int width) {this.width = width;}
	public String getType() {return type;}
	public void setType(String type) {this.type = type;}
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,width,height);
	}
	
	public abstract void draw(Graphics g, DrawPanel dp);
	public abstract void collideWith(Shape s);
	public abstract void move(DrawPanel dp);
	
	public boolean detectCollision(Shape s){
		//if collided AND collision has not already been detected 
		if(this.getBounds().intersects(s.getBounds())&& !s.collidedWith.contains(this)){
			this.collidedWith.add(s);
			return true;
			}
		else
			return false;
	}
	public void resetCollisionVectors(){
		this.cdx=0;
		this.cdy=0;
	}
	private int genRandomNum(int min, int max){
		Random rn = new Random();
		return (min+rn.nextInt(max+1-min));
	}
}
