package acrobot; 
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
 *    AcrobotDesign.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */

/** A graphical representation of the acrobot system */

import javax.swing.JPanel; 

import java.awt.Graphics;
import java.awt.Dimension;


public class AcrobotDesign extends JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Size of the inner window */
    private int xcord,ycord; 
    private int origineX=100; 
    private int origineY=80; 
    private int barSize=50; 
    
    public AcrobotDesign(int lo,int la){
	super(); 
	this.xcord=lo; 
	this.ycord=la;
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
    public void plot(double t1,double t2){
	Graphics gx=this.getGraphics(); 
	this.reinit(); 
	int jointX1=origineX+(int)(barSize*Math.sin(t1)); 
	int jointY1=origineY+(int)(barSize*Math.cos(t1)); 
	gx.drawLine(origineX,origineY,jointX1,jointY1); 
	gx.fillArc(jointX1-5,jointY1-5,10,10,0,360); 
	gx.fillArc(origineX-5,origineY-5,10,10,0,360); 
	double l=barSize*barSize*(2+2*Math.cos(t2)); 
	l=Math.sqrt(l); 
	double lx=barSize*Math.sin(t2); 
	double ly=barSize*Math.cos(t2); 
	
	gx.drawLine(jointX1,jointY1,jointX1+(int)lx,jointY1+(int)ly); 
	gx.fillArc(jointX1+(int)lx-5,jointY1+(int)ly-5,10,10,0,360); 
	// temporization
	for(int i=0;i<9000;i++)
	    for(int j=0;j<1000;j++)
		{@SuppressWarnings("unused")
		int zozo=(int)Math.sqrt((double)i); }
	//this.repaint(); 
    }
	 protected void reinit(){
	     this.getGraphics().clearRect(0,0,xcord,ycord); 
	 }

}