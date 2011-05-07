package mobileRobot; 
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
 *    Circuit.java
 *    Copyright (C) 2005,USTL  Francesco De Comite
 *
 */




/** A graphical representation of the arena, enabling the visualization of the mobile trajectory */

import javax.swing.JPanel; 

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;



public class VisualizeArena extends JPanel{

/** Default colour for the plot background */
  //private Color backgroundColour = Color.blue;

 /** Default colour for the arena */
  //private Color arenaColour = Color.green;

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**Default colour for the car */
    //private Color carColour = Color.red;
    
    private int xPoints[]=new int[5001],
	        yPoints[]=new int[5001]; 
    private int indice=0; 

    private Polyline inside,outside;

    public VisualizeArena(Polyline inside,Polyline outside){
	super(); 
	//	this.setBackground(backgroundColour);
	this.inside=inside; 
	this.outside=outside; 
	
	
	int xwidth=(int)(outside.getXTrans()+outside.maxSize()); 
	int ywidth=(int)(outside.getYTrans()+outside.maxSize()); 
	this.setSize(new Dimension(xwidth,ywidth)); 
    }// constructor


    /**
   * Renders this component
   * @param gx the graphics context
   */

     public void paintComponent(Graphics gx) {
	super.paintComponent(gx);   
    }

    protected void addPoint(int x,int y){
	Graphics gx=this.getGraphics(); 
	xPoints[indice]=x;
	yPoints[indice]=y; 
	indice++; 
	int xDep=(int)this.outside.getXTrans(); 
	int yDep=(int)this.outside.getYTrans(); 
	
	this.outside.drawPolyline(this.getGraphics());  
	this.inside.drawPolyline(xDep,yDep,this.getGraphics()); 
	    //   this.getGraphics().setColor(Color.black); 
	for(int i=1;i<indice;i++)
	    gx.drawLine(xPoints[i-1],yPoints[i-1],xPoints[i],yPoints[i]); 
	if(indice%100==0){
	    
	    this.repaint();
	} 
    }
    protected void reinit(){
	xPoints=new int[5001];
	yPoints=new int[5001]; 
	indice=0;
	for(int i=0;i<65000;i++)
	    for(int j=0;j<500;j++)
		{@SuppressWarnings("unused")
		int zozo=(int)Math.sqrt((double)i); }
	this.getGraphics().clearRect(0,0,400,400); 
	
	int xDep=(int)this.outside.getXTrans(); 
	int yDep=(int)this.outside.getYTrans(); 
	this.outside.drawPolyline(this.getGraphics());  
	this.inside.drawPolyline(xDep,yDep,this.getGraphics()); 

    }

}
	
    

    



