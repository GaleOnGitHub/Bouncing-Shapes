import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class PowerUp extends Shape{
	private Color[] colors = {new Color(255,215,0), new Color(250,250,210)};
	private int glowCounter = 0;
	public PowerUp(int x, int y){
		this.setX(x);
		this.setY(y);
		this.setWidth(16);
		this.setHeight(16);
		this.setType("PowerUp");
		this.setDx(0);//No Movement
		this.setDy(0);
	}
	@Override
	public void draw(Graphics g, DrawPanel dp) {
        Graphics2D g2d = (Graphics2D) g;
        //Move
        move(dp);
        //Alternate gradient
        if(glowCounter<10){
        	g2d.setPaint(new GradientPaint(getX(),getY(),colors[0],getX()+getWidth()/2,getY()+getHeight()/2,colors[1],true));
        	glowCounter ++;
        }else{
        	g2d.setPaint(new GradientPaint(getX(),getY(),colors[1],getX()+getWidth()/2,getY()+getHeight()/2,colors[0],true));
        	glowCounter ++;
        	if(glowCounter>20){
        		glowCounter = 0;}
        }
        //Draw
		g2d.fillRect(getX(), getY(), getWidth(), getHeight());
	}
	
	@Override
	public void collideWith(Shape s) {
		//Action to perform on collision
		switch(s.getType()){
		case "Dragon" :	
			this.setVisible(false);
			break;
		case "PowerUp" : 
		case "Ghost" :
		case "Bubble" :
		case "Goblin" :
			break;
		}
	}
	
	@Override
	public void move(DrawPanel dp) {
        if(getX()+getWidth()>dp.getWidth()){
        	setX(dp.getWidth()-1-this.getWidth());
        }
		if(getX()<0){
			setX(1);
		}
		if(getY()<0){
			setY(1);
		}
		if(getY()+getHeight()>dp.getHeight()){
        	setY(dp.getHeight()-1-this.getHeight());
        }
	}
}
