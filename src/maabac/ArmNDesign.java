package maabac; 
/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation; either version 2.1 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    ArmNDesign.java
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

/** A graphical representation of a N-segments arm*/

import javax.swing.JPanel; 

import java.awt.Graphics;
import java.awt.Dimension;


public class ArmNDesign extends JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Size of the inner window */
    private int xcord,ycord; 
    private int origineX=200; 
    private int origineY=200; 
    private int barSize=50; 
    
    public ArmNDesign(int lo,int la){
	super(); 
	this.xcord=lo; 
	this.ycord=la;
	this.origineX=lo/2;
	this.origineY=la/2;
	this.setSize(new Dimension(lo,la)); 
    }
    
    /**
     * Renders this component
     * @param gx the graphics context
     */
    
    public void paintComponent(Graphics gx) {
	super.paintComponent(gx);   
    }
    
    // l1 and l2=50 pixels
    public void plot(double angles[],double xt,double yt,double rt){

	Graphics gx=this.getGraphics(); 
	this.reinit(); 
	int predX=origineX;
	int predY=origineY;
	int curX=origineX; 
	int curY=origineY;
	gx.fillArc(origineX-5,origineY-5,10,10,0,360); 
	for(int i=0;i<angles.length;i++){
	    curX+=(int)(barSize*Math.sin(angles[i])); 
	    curY+=(int)(barSize*Math.cos(angles[i])); 
	    gx.drawLine(predX,predY,curX,curY); 
	    gx.fillArc(curX-5,curY-5,10,10,0,360); 
	    predX=curX; 
	    predY=curY;
	}
	gx.drawArc(origineX+(int)(50*yt),origineY+(int)(50*xt),
		   (int)(50*rt),(int)(50*rt),0,360); 

	// temporization
	for(int i=0;i<9000;i++)
	    for(int j=0;j<1000;j++)
		{@SuppressWarnings("unused")
		int zozo=(int)Math.sqrt((double)i); }
	//this.repaint(); 
    }
	 protected void reinit(){
	      this.getGraphics().clearRect(0,0,2*xcord,2*ycord); 
	 }

}