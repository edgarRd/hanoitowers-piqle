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

import environment.*;
import java.util.Random; 

/** A pinball is a circular arena into one can put several bumpers 

@author Francesco De Comite
@version $Revision: 1.0 $ 
*/

public class Pinball extends Circuit{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double outerRadius; 
    private Random generateur=new Random(); 
    
    public double bigRadius(){return this.outerRadius;}

    public Pinball(double outer){
	super(); 
	this.outside=new CenteredCircle(outer); 
	this.inside=new SetOfObstacles(); 
	this.outerRadius=outer;
    }

    public void addBumper(double r,double x,double y){
	((SetOfObstacles)this.inside).addElement(new Bumper(r,x,y)); 
    }

    public IState defaultInitialState(){
	MobileState ev=null; 
	do{
	    double l=generateur.nextDouble()*this.outerRadius; 
	    double theta=2*Math.PI*generateur.nextDouble(); 
	    double locAngle=360*generateur.nextDouble(); 
	    int locSpeed=generateur.nextInt(MobileState.maxSpeed); 
	    ev=new MobileState(l*Math.cos(theta),l*Math.sin(theta),locAngle,locSpeed,this); 
	}
	while(this.inside.inside(ev)); 
	return ev;				   
    }
    /** Compute the view of a state (how far are we from the walls ?) */
    protected double getViewInOneDir(MobileState ev,double smallDev){
	double distSoFar=50000.0; // a huge number
	
	double radAngle=smallDev+ev.getAngle();
	if(radAngle>360) radAngle-=360; 
	if(radAngle<0) radAngle+=360; 
	double notRadAngle=radAngle; // Keeping a degree version of the angle...
	// Compute distance to external wall
	boolean done=false; 
	// Eliminate vertical cases
	if(Math.abs(radAngle-90)<0.001){
	    double d1=Math.sqrt((outerRadius*outerRadius)-(ev.getX()*ev.getX()))+ev.getY(); 	
	    if(d1<distSoFar) distSoFar=d1;
	    done=true; 
	}
	if(Math.abs(radAngle-270)<0.001){
	    double d1=Math.sqrt((outerRadius*outerRadius)-(ev.getX()*ev.getX()))+ev.getY(); 	
	     if(d1<distSoFar) distSoFar=d1;
	     done=true; 
	}
	if(!done){ // Normal angle...
	    double u = Math.atan(ev.getY()/ev.getX());  
	    if(ev.getX()<0) u+=Math.PI; 
	    if(u<0) u+=2*Math.PI; 
	    radAngle=(Math.PI*radAngle)/180.0;
	    double tan=Math.tan(radAngle); 
	    tan=tan*tan; 
	    double radius=this.outerRadius; 
	    double racines[]=solve(ev.getX(),ev.getY(),radAngle,radius); 
	    // Compute the distance between the car and the wall
	    double d1=(ev.getX()-racines[0])*(ev.getX()-racines[0])*(1+tan); 
	    double d2=(ev.getX()-racines[1])*(ev.getX()-racines[1])*(1+tan); 
	    d1=Math.sqrt(d1);
	    d2=Math.sqrt(d2); 
	    double cosAngle=Math.cos(radAngle); 
	    if(cosAngle*(ev.getX()-racines[0])<0){ if(d1<distSoFar) distSoFar=d1; }
	    else { if(d2<distSoFar) distSoFar=d2;}
	}// distance to external circle
	double d1=((SetOfObstacles)this.inside).distance(ev,notRadAngle); 
	if(d1<distSoFar) distSoFar=d1;
	return distSoFar; 
	
    }// getViewInOneDir
     /** Compute both radices of 2nd order polynome */
    private double[] solve(double x,double y,double theta,double r){
	double radix[]=new double[2]; 

	double t=Math.tan(theta); 
	double delta=((1+(t*t))*r*r)-(((t*x)-y)*((t*x)-y)); 
	radix[0]=(-1*t*(y-t*x)-Math.sqrt(delta))/(1+t*t); 
	radix[1]=(-1*t*(y-t*x)+Math.sqrt(delta))/(1+t*t); 
	return radix; 
    }

   

    }//Pinball