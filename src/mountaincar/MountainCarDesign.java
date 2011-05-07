package mountaincar; 
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
 *    MountainCar.java
 *    Copyright (C) 2005,USTL  Francesco De Comite
 *
 */

/** A graphical representation of the mountain car system */

import javax.swing.JPanel; 

import java.awt.Graphics;
import java.awt.Dimension;


public class MountainCarDesign extends JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Size of the inner window */
    private int xcord,ycord; 
    private int origineX=300/2; 
    private int origineY=220/2; 
    public MountainCarDesign(int lo,int la){
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
    
    
    public void plot(double x,double speed){
	
	Graphics gx=this.getGraphics(); 
	this.reinit(); 
	
	
		
	
	double lt        = -1.5;
	double rt        = 0.7;
	double L         = rt-lt;
	double stepsize  = 0.1;
	int numpoints = (int)(L/stepsize);
	int[] xPoints = new int[numpoints];
	int[] yPoints = new int[numpoints];
	double radiusx = 200/2;
	double radiusy = 200/2;
	double xx=lt;
	for(int j=0;j<numpoints;j++,xx+=stepsize)
	{
	  xPoints[j] = origineX+(int)(radiusx*xx);
	  yPoints[j] = origineY+(int)(-radiusy*Math.sin(3.0*xx));
	}
	
	gx.drawPolyline(xPoints, yPoints, numpoints) ;
	
	int xcar = origineX+(int)(radiusx*x);
	int ycar = origineY+(int)(-radiusy*Math.sin(3*x));
	
	gx.fillOval(xcar-15,ycar-30,30,30); 
	
	
	if (speed>0) gx.drawString(">>>", xcar+30-15, ycar-12);
	if (speed<0) gx.drawString("<<<", xcar-35, ycar-12);
	
	// temporization
	for(int i=0;i<99000;i++)
		{@SuppressWarnings("unused")
		int zozo=(int)Math.sqrt((double)i); }
	//this.repaint(); 
    }
	 protected void reinit(){
	     this.getGraphics().clearRect(0,0,xcord,ycord); 
	 }

}