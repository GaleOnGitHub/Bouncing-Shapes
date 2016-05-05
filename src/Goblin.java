import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.Random;



public class Goblin extends Shape {
	private Color[] colors = {Color.RED,new Color(180, 100, 0)};
	private int hp = 1;
	
	public Goblin(int x, int y){
		this.setX(x);
		this.setY(y);
		this.setWidth(24);
		this.setHeight(42);
		this.setType("Goblin");
	}
	@Override
	public void draw(Graphics g, DrawPanel dp) {
		Graphics2D g2d = (Graphics2D) g;
		//Save state of g2d
		AffineTransform keep = g2d.getTransform();	

		//Shape points
		int xPoints[] = {0,6,6,18,18,24,24, 0};
		int yPoints[] = {0,0,6, 6, 0, 0,42,42};
		GeneralPath monster = new GeneralPath();
		monster.moveTo( xPoints[0], yPoints[0] );
	    //Create the shape
		for (int count = 1; count < xPoints.length; count++)
			monster.lineTo(xPoints[count], yPoints[count]);
		monster.closePath();
		
		//Move
		move(dp);
		//Set Position
		g2d.translate(getX(),getY());
		g2d.setColor(colors[hp]); //paint
        g2d.fill(monster); //Draw
		//Restore g2d
		g2d.setTransform(keep);
		//Draw goblins's face
		g2d.setColor(Color.BLACK);
		g2d.fillRect(getX()+4, getY()+12, 16, 10);
		g2d.setColor(Color.YELLOW);
		g2d.fillRect(getX()+6, getY()+14, 4, 4);
		g2d.fillRect(getX()+14, getY()+14, 4, 4);	
	}
	
	@Override
	public void collideWith(Shape s) {
		//Action to perform on collision
		switch(s.getType()){
			case "PowerUp" :
			case "Ghost" :
				this.setCdx(this.getDx());
				this.setCdy(this.getDy());
				break;
			case "Dragon" :
				hp -= 1;
				if(hp<0)
					this.setVisible(false);
			case "Bubble" :
			case "Goblin" :
				this.setCdx(s.getDx());
				this.setCdy(s.getDy());
				break;
		}
	}
	@Override
	public void move(DrawPanel dp) {
		if(hp==0){//move fast if hp = 0
			setX(getX() + 2*getDx());
			setY(getY() + 2*getDy());
		}else{	
			setX(getX() + getDx());
			setY(getY() + getDy());
		}	
		//Wall Collision		
        if(getX()+getWidth()>dp.getWidth()){
        	setX(dp.getWidth()-1-this.getWidth());
        	setDx(getDx() * -1);
        }
		if(getX()<0){
			setX(1);
			setDx(getDx() * -1);
		}
		if(getY()<0){
			setY(1);
			setDy(getDy() * -1);
		}
		if(getY()+getHeight()>dp.getHeight()){
        	setY(dp.getHeight()-1-this.getHeight());
        	setDy(getDy() * -1);
        }
	}
}
