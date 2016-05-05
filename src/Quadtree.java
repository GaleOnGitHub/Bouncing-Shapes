import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

//Following Class Modified from
// http://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374

public class Quadtree {
	private final int MAX_OBJECTS = 10; //number of objects before node is split
	private final int MAX_LEVELS = 5; //number of times a node can split
	
	private int level; //current node level
	private List<Shape> objects; // objects in node
	private Rectangle bounds; 
	private Quadtree[] nodes; //sub
	
	public void setBounds(Rectangle pBounds){
		this.bounds = pBounds;
	}
	
	//CONSTRUCTOR
	public Quadtree(int pLevel, Rectangle pBounds){
		level = pLevel;
		objects = new ArrayList<Shape>();
		bounds = pBounds;
		nodes = new Quadtree[4];
	}
	
	//CLEAR QUADTREE
	public void clear() {
		objects.clear();
		
		for(int i=0; i<nodes.length; i++){
			if(nodes[i] != null){
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}//end clear
	
	//DRAW QUADTREE
	public void draw(Graphics g, DrawPanel dp) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.draw(bounds);
		
		for(int i=0; i<nodes.length; i++){
			if(nodes[i] != null){
				nodes[i].draw(g,dp);
			}
		}
	}//end clear
	
	
	//SPLIT NODE INTO SUBNODES
	private void split(){
		int subWidth = (int)(bounds.getWidth()/2);
		int subHeight = (int)(bounds.getHeight()/2);
		int x = (int)bounds.getX();
		int y = (int)bounds.getY();
		
		nodes[0] = new Quadtree(level+1, new Rectangle(x + subWidth, y, subWidth, subHeight));
		nodes[1] = new Quadtree(level+1, new Rectangle(x, y, subWidth, subHeight));
		nodes[2] = new Quadtree(level+1, new Rectangle(x, y + subHeight, subWidth, subHeight));
		nodes[3] = new Quadtree(level+1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
	}
	
	/*
	 * Determine which node the object belongs to. -1 means
	 * object cannot completely fit within a child node and is part
	 * of the parent node
	 */
	 private int getIndex(Shape pRect) {
	   int index = -1;
	   double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
	   double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);
	 
	   // Object can completely fit within the top quadrants
	   boolean topQuadrant = (pRect.getY() < horizontalMidpoint && pRect.getY() + pRect.getHeight() < horizontalMidpoint);
	   // Object can completely fit within the bottom quadrants
	   boolean bottomQuadrant = (pRect.getY() > horizontalMidpoint);
	 
	   // Object can completely fit within the left quadrants
	   if (pRect.getX() < verticalMidpoint && pRect.getX() + pRect.getWidth() < verticalMidpoint) {
	      if (topQuadrant) {
	        index = 1;
	      }
	      else if (bottomQuadrant) {
	        index = 2;
	      }
	    }
	    // Object can completely fit within the right quadrants
	    else if (pRect.getX() > verticalMidpoint) {
	     if (topQuadrant) {
	       index = 0;
	     }
	     else if (bottomQuadrant) {
	       index = 3;
	     }
	   }
	 
	   return index;
	 }
	 
	 /*
	  * Insert the object into the quadtree. If the node
	  * exceeds the capacity, it will split and add all
	  * objects to their corresponding nodes.
	  */
	  public void insert(Shape pRect) {
	    if (nodes[0] != null) {
	      int index = getIndex(pRect);
	  
	      if (index != -1) {
	        nodes[index].insert(pRect);
	  
	        return;
	      }
	    }
	  
	    objects.add(pRect);
	    //Check if split need & re-allocate objects
	    if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
	       if (nodes[0] == null) { 
	          split(); 
	       }
	  
	      int i = 0;
	      while (i < objects.size()) {
	        int index = getIndex(objects.get(i));
	        if (index != -1) {
	          nodes[index].insert(objects.remove(i));
	        }
	        else {
	          i++;
	        }
	      }
	    }
	  }//end insert
	  
	  /*
	   * Return all objects that could collide with the given object
	   */
	   public List<Shape> retrieve(List<Shape> returnObjects, Shape pRect) {
	     int index = getIndex(pRect);
	     if (index != -1 && nodes[0] != null) {
	       nodes[index].retrieve(returnObjects, pRect);
	     }
	   
	     returnObjects.addAll(objects);
	   
	     return returnObjects;
	   }
 
	  
	
}
