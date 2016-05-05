import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;



public class Ghost extends Shape{
	private double alpha = 0.95;
	
	public Ghost(int x,int y){
		this.setX(x);
		this.setY(y);
		this.setWidth(32);
		this.setHeight(40);
		this.setType("Ghost");
	}
	
	@Override
	public void draw(Graphics g, DrawPanel dp) {
		Graphics2D g2d = (Graphics2D) g;
		//Save state of g2d
		AlphaComposite keep = (AlphaComposite) g2d.getComposite(); 
        AlphaComposite alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, (float)alpha);
		
		move(dp);
		g2d.setComposite(alcom);
		g2d.setColor(Color.WHITE);
		g2d.fillArc(getX(), getY(), getWidth(), getHeight(), 0, 180);
		g2d.fillRect(getX()+1, getY()+getHeight()/2, getWidth()-1, getHeight()/2);
		//Restore g2d
		g2d.setComposite(keep);
		g2d.setColor(Color.BLACK);
		g2d.fillOval(getX()+6, getY()+12, 6, 8);
		g2d.fillOval(getX()+20, getY()+12, 6, 8);
		g2d.fillOval(getX()+13, getY()+20, 6, 8);
	}
	private void ghostMode(boolean on){
		if(on)
			alpha = 0.35;
		else
			alpha = 0.95;
	}


	@Override
	public void collideWith(Shape s) {
		//Action to perform on collision
		switch(s.getType()){
		case "Ghost" :
		case "Dragon" :	
		case "Goblin" :
		case "Bubble" :
			ghostMode(true);//turn on transparency
		case "PowerUp" :
			this.setCdx(this.getDx());
			this.setCdy(this.getDy());
			break;
		}
	}

	@Override
	public void move(DrawPanel dp) {
		setX(getX() + getDx());
        setY(getY() + getDy());
        //Wall Collision - turn of transparency
        if(getX()+getWidth()>dp.getWidth()){
        	setX(dp.getWidth()-1-this.getWidth());
        	setDx(getDx() * -1);
        	ghostMode(false);
        }
		if(getX()<0){
			setX(1);
			setDx(getDx() * -1);
			ghostMode(false);
		}
		if(getY()<0){
			setY(1);
			setDy(getDy() * -1);
			ghostMode(false);
		}
		if(getY()+getHeight()>dp.getHeight()){
        	setY(dp.getHeight()-1-this.getHeight());
        	setDy(getDy() * -1);
        	ghostMode(false);
        }
		
	}

}
