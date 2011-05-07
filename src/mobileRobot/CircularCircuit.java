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
 *    CircularCircuit.java
 *    Copyright (C) 2005,USTL  Francesco De Comite
 *
 */

import environment.IState; 
import java.util.Random; 

/** This circuit is a portion of plane between two origin-centered circles : the agent (a racing car ...) has to keep in lane.

@author Francesco De Comite 
 @version $Revision: 1.0 $ 
*/

public class CircularCircuit extends Circuit{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Random generateur=new Random(); 
    private double innerRadius,outerRadius; 
    public CircularCircuit(double inner,double outer){
	super(); 
	this.inside=new CenteredCircle(inner); 
	this.outside=new CenteredCircle(outer);
	this.innerRadius=inner; 
	this.outerRadius=outer; 
    }

    /** Radius of the external circle */
    public double bigRadius(){return this.outerRadius;}
    /** Radius of the internal circle */
    public double smallRadius(){return this.innerRadius;}

    /** The departure line is somewhere between the two circles.*/
    public IState defaultInitialState(){
	//double i=((CenteredCircle)this.inside).getRadius();
	//double o=((CenteredCircle)this.outside).getRadius();
	//	return new EtatVoiture(this.innerRadius+generateur.nextDouble()*(this.outerRadius-this.innerRadius),0.0,90.0,3,this); 
	double l=this.innerRadius+generateur.nextDouble()*(this.outerRadius-this.innerRadius);
	double theta=2*Math.PI*generateur.nextDouble(); 
	double locAngle=360*generateur.nextDouble(); 
	int locSpeed=generateur.nextInt(MobileState.maxSpeed); 
	return new MobileState(l*Math.cos(theta),l*Math.sin(theta),locAngle,locSpeed,this); 
							   
    }


 /** Compute the view of a state (how far are we from the walls ?) */
    protected double getViewInOneDir(MobileState ev,double smallDev){
	double radius; 
	boolean in=false; 
	double radAngle=smallDev+ev.getAngle();
	if(radAngle>360) radAngle-=360; 
	if(radAngle<0) radAngle+=360; 
	//double notRadAngle=radAngle; 
	// eliminate the vertical case
	if(Math.abs(radAngle-90)<0.001){
	    if((ev.getY()<=0)&&(ev.getX()>=-innerRadius)&(ev.getX()<=innerRadius))
		return -(ev.getY()+Math.sqrt((innerRadius*innerRadius)-(ev.getX()*ev.getX()))); 
	    else
		return Math.sqrt((outerRadius*outerRadius)-(ev.getX()*ev.getX()))-ev.getY(); 
	}
	if(Math.abs(radAngle-270)<0.001){
	    if((ev.getY()>=0)&&(ev.getX()>=-innerRadius)&(ev.getX()<=innerRadius))
		return (ev.getY()-Math.sqrt((innerRadius*innerRadius)-(ev.getX()*ev.getX()))); 
	    else
		return Math.sqrt((outerRadius*outerRadius)-(ev.getX()*ev.getX()))+ev.getY(); 	
	}
	// Compute the 1st angle
	double u = Math.atan(ev.getY()/ev.getX());  
	if(ev.getX()<0) u+=Math.PI; 
	if(u<0) u+=2*Math.PI; 
	// Determining the angle under which the inner circle is seen
	double alpha1=Math.asin(this.innerRadius/Math.sqrt(ev.getX()*ev.getX()+ev.getY()*ev.getY())); 
	//double alpha1=Math.atan(this.innerRadius/Math.sqrt(ev.getX()*ev.getX()+ev.getY()*ev.getY())); 
	double limitInf=Math.PI+u-alpha1; 
	double limitSup=Math.PI+u+alpha1; 
	if(limitInf<0) limitInf+=2*Math.PI; 
	if(limitInf>2*Math.PI) limitInf-=2*Math.PI; 
	if(limitSup<0) limitSup+=2*Math.PI; 
	if(limitSup>2*Math.PI) limitSup-=2*Math.PI; 

	
	radAngle=(Math.PI*radAngle)/180.0;
	double tan=Math.tan(radAngle); 
	tan=tan*tan; 
	if((radAngle>limitInf)&&(radAngle<limitSup)){
	    radius=this.innerRadius; 
	    in=true; 
	}
	else
	    radius=this.outerRadius; 
	double racines[]=solve(ev.getX(),ev.getY(),radAngle,radius); 
	// Compute the distance between the car and the wall
	 double d1=(ev.getX()-racines[0])*(ev.getX()-racines[0])*(1+tan); 
	 double d2=(ev.getX()-racines[1])*(ev.getX()-racines[1])*(1+tan); 
	 d1=Math.sqrt(d1);
	 d2=Math.sqrt(d2); 
	if(in){ // Distance to internal circle
	    if(d1<d2) return d1; else return d2; 
	}//in=true
	// else compute the distance to external wall
	double cosAngle=Math.cos(radAngle); 

	if(cosAngle*(ev.getX()-racines[0])<0){ return d1; }
	else { return d2; }
    }
    /** Compute both radices of 2nd order polynome */
    private double[] solve(double x,double y,double theta,double r){
	double radix[]=new double[2]; 

	double t=Math.tan(theta); 
	double delta=((1+(t*t))*r*r)-(((t*x)-y)*((t*x)-y)); 
	radix[0]=(-1*t*(y-t*x)-Math.sqrt(delta))/(1+t*t); 
	radix[1]=(-1*t*(y-t*x)+Math.sqrt(delta))/(1+t*t); 
	return radix; 
    }
}

