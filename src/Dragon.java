import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;


public class Dragon extends Shape {
	private BufferedImage[] img = new BufferedImage[2];
	private int mode = 0; 
	private int counter = -1;
	
	
	public Dragon(int x, int y){
		this.setType("Dragon");
		this.setX(x);
		this.setY(y);
		//Load pics
		String pic1 = getClass().getResource("Images/gDragon.png").getFile();
		String pic2 = getClass().getResource("Images/sDragon.png").getFile();
		try {
		    img[0] = ImageIO.read(new File(pic1));
		    img[1] = ImageIO.read(new File(pic2));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		this.setWidth(img[0].getWidth());
		this.setHeight(img[0].getHeight());
	}
	@Override
	public void draw(Graphics g, DrawPanel dp) {
		//CountDown/Reset Super Mode
		if(mode>0){
			counter --;
		}
		if(counter==0){
			this.setWidth(img[0].getWidth());
			this.setHeight(img[0].getHeight());
			mode = 0;
			counter =-1;
		}
		move(dp);
		//Draw
		g.drawImage(img[mode], getX(), getY(),getWidth(),getHeight(), dp);
	}
	@Override
	public void collideWith(Shape s) {
		//Action to perform on collision
		switch(s.getType()){
		case "PowerUp" :
			superMode();
		case "Ghost" :
			this.setCdx(this.getDx());
			this.setCdy(this.getDy());
			break;
		case "Dragon" :	
		case "Bubble" :
		case "Goblin" :
			this.setCdx(s.getDx());
			this.setCdy(s.getDy());
			break;
		}
	}
	
	private void superMode(){
		//Grow
		this.setWidth(img[0].getWidth()*4/3);
		this.setHeight(img[0].getHeight()*4/3);
		mode = 1;
		counter += 500;//add time to super mode
	}
	@Override
	public void move(DrawPanel dp) {
		setX(getX() + getDx());
        setY(getY() + getDy());
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
