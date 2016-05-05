import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.Ellipse2D;


public class Bubble extends Shape {
	private int level = 1;
	
	public Bubble(int x, int y){
		this.setType("Bubble");
		this.setX(x);
		this.setY(y);
		this.setWidth(72);
		this.setHeight(72);
	}
	@Override
	public void draw(Graphics g, DrawPanel dp) {
        Graphics2D g2d = (Graphics2D) g;
        move(dp);
        //Draw
        g2d.setColor(new Color(0, 176, 224));
        g2d.draw(new Ellipse2D.Double(getX(),getY(),getHeight(),getWidth()));
        g2d.setColor(Color.WHITE);
        g2d.fillOval(getX()+getWidth()/4, getY()+getHeight()/4, getWidth()/6, getHeight()/6);
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
		case "Goblin" :
			shrink();
		case "Bubble" :
			this.setCdx(s.getDx());
			this.setCdy(s.getDy());

			break;
		}
	}
	private void shrink(){
		if(level>=4)
			this.setVisible(false);
		level ++;
		this.setWidth(72/level);
		this.setHeight(72/level);
	}
	@Override
	public void move(DrawPanel dp) {
		setX(getX() + getDx());
        setY(getY() + getDy());
        //Wall Collision
		if(getX()+getWidth()>dp.getWidth()){
        	setX(dp.getWidth()-getWidth());
        	setDx(getDx() * -1);
        }
		if(getX()<0)
			setDx(getDx() * -1);
		if(getY()<0)
			setDy(getDy() * -1);
		if(getY()+getHeight()>dp.getHeight()){
        	setY(dp.getHeight()-getHeight());
        	setDy(getDy() * -1);
        }
		
	}
}
