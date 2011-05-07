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
 *    CenteredCircle.java
 *    Copyright (C) 2005,USTL  Francesco De Comite
 *
 */

import environment.*;
import java.awt.Graphics;
/** An elementary Polyline : an origin-centered circle 

@author Francesco De Comite 
 @version $Revision: 1.0 $ 
*/

public class CenteredCircle implements Polyline{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double radius; 
    private double squareRadius; 

    public CenteredCircle(double r){
	this.radius=r; 
	this.squareRadius=r*r; 
    }

    public boolean inside(MobileState ev){
	double x=ev.getX(); 
	double y=ev.getY(); 
	return ((x*x)+(y*y)<this.squareRadius); 
    }
    
    public boolean outside(MobileState ev) {
	double x=ev.getX(); 
	double y=ev.getY(); 
	return ((x*x)+(y*y)>=this.squareRadius); 
    }

    protected double getRadius(){return this.radius;}

    public double maxSize(){return 2*this.radius;}

    /** How far must we go in X direction in order to see all the line ? */
    public double getXTrans(){return this.radius; }

    /** How far must we go in Y direction in order to see all the line ? */
    public double getYTrans(){return this.radius;}

    /** Draw an external wall (bounds are calculated) */
    public void drawPolyline(Graphics gx){
	int width=(int)(this.getXTrans()+this.radius); 
	int height=(int)(this.getYTrans()+this.radius); 
	gx.drawArc(0,0,width,height,0,360); 
    }
    /** Draw an internal wall (must use xTrans and Ytrans) */
    public void drawPolyline(double xDep,double yDep,Graphics gx){
	int x=(int)(xDep-this.radius);
	int y=(int)(yDep-this.radius);
	int width=(int)(2*this.radius); 
	int height=(int)(2*this.radius); 
	gx.fillArc(x,y,width,height,0,360); 
    }
}