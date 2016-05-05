import java.awt.Color;
import java.awt.Graphics;


import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;

public class DrawPanel extends JPanel {
	//Quadtree
	private Quadtree quad;
	private List<Shape> shapeList = new ArrayList<Shape>();
	private int mX;
	private int mY;
	private final int ANIMATION_DELAY = 15;
	Timer animationTimer = new Timer(ANIMATION_DELAY, new TimerHandler());
	private Random rn = new Random();
	/**
	 * Create the panel.
	 */
	public DrawPanel() {
		//Add Quadtree
		quad = new Quadtree(0, new Rectangle(0,0,400,200));
		//Initial Shapes
		shapeList.add((Shape) new Dragon(2,2));
		shapeList.add((Shape) new Goblin(100,2));
		shapeList.add((Shape) new Bubble(2,100));
		shapeList.add((Shape) new Ghost(150,100));
		this.setBackground(Color.BLACK);
		animationTimer.start(); //start timer
		//Actions
		this.addMouseListener(new MouseListener(){
					@Override
					public void mouseClicked(MouseEvent e) {}
					@Override
					public void mouseEntered(MouseEvent e) {}
					@Override
					public void mouseExited(MouseEvent e) {}
					@Override
					public void mousePressed(MouseEvent e) {}
					@Override
					public void mouseReleased(MouseEvent e) {
						mX = e.getX();
						mY = e.getY();
						//draw random shape on mouse unclick
						int newShape = 1+rn.nextInt(200);
						if(newShape<=150)
							shapeList.add((Shape)new Bubble(mX,mY));
						else if(newShape>150 && newShape<=197)
							shapeList.add((Shape)new Goblin(mX,mY));
						else if(newShape>197 && newShape<=199)
							shapeList.add((Shape)new Ghost(mX,mY));
						else
							shapeList.add((Shape)new Dragon(mX,mY));	
					}
				});
		
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		quad.setBounds(this.getBounds());
		quad.draw(g, this);
		if((1+rn.nextInt(1000))==1000)
			shapeList.add(0,(Shape) new PowerUp(rn.nextInt(this.getWidth()),rn.nextInt(this.getHeight())));	//draw powerUps on the bottom so things pass over them
		//Draw Shapes
		for(int i=0;i<shapeList.size();i++){
			//Delete if not visible
			if(!shapeList.get(i).isVisible())
				shapeList.remove(i);
			else
			//Draw
				shapeList.get(i).draw(g,this);
		}
		
	}//end paint
	private class TimerHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent actionEvenet) {
			//Clear tree
			quad.clear();
			//Add all shapes to tree
			for (int i = 0; i < shapeList.size(); i++) {
				quad.insert(shapeList.get(i));
				
				shapeList.get(i).collidedWith.clear();
			}
			//Find possible collisions
			List<Shape> returnObjects = new ArrayList<Shape>();
			for (int i = 0; i < shapeList.size(); i++) {
			  returnObjects.clear();
			  quad.retrieve(returnObjects, shapeList.get(i));
			 
			  for (int x = 0; x < returnObjects.size(); x++) {
			    Shape r = returnObjects.get(x);
			    Shape s = shapeList.get(i);
				  if(s!=r){
			    	if(s.detectCollision(r)){
					  	s.collideWith(r);
					  	r.collideWith(s);
					  	s.setDx(s.getCdx());
					  	s.setDy(s.getCdy());
					  	r.setDx(r.getCdx());
					  	r.setDy(r.getCdy());
			    	}
			    }
			  }
			}
			repaint();// call paintComponent	
		}
	}//end timerHandler
}
