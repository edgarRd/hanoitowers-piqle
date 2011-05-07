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
 *    SetOfObstacles.java
 *    Copyright (C) 2005,USTL  Francesco De Comite
 *
 */

import java.util.ArrayList; 
import java.util.Iterator; 
import java.awt.Graphics;
/** A set of internal obstacles (imagine bumpers into a pinball !).In this version, only Bumpers are allowed...

@author Francesco De Comite 
 @version $Revision: 1.0 $ 
*/



public class SetOfObstacles implements Polyline{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Keeping track of all obstacles */
    private ArrayList<Polyline> listOfObstacles=new ArrayList<Polyline>(); 
    /** Size of the bigger obstacle */
    private double maxTaille=0; 
    
    public boolean inside(MobileState ev){
	Iterator<Polyline> iter=this.listOfObstacles.iterator(); 
	while(iter.hasNext()){
	    Polyline pl=iter.next(); 
	    if(pl.inside(ev)) return true; 
	}
	return false; 
    }//inside
    
    public boolean outside(MobileState ev){
	Iterator<Polyline> iter=this.listOfObstacles.iterator(); 
	while(iter.hasNext()){
	    Polyline pl=iter.next(); 
	    if(!pl.outside(ev)) return false; 
	}
	return true; 
    }

    /** Add an obstacle to the set */
    protected void addElement(Polyline pl){
	if(! (pl instanceof Bumper)) {
	    System.err.println("Sorry, only bumpers.."); 
	    System.exit(-1); 
	}
	this.listOfObstacles.add(pl); 
	if(pl.maxSize()>this.maxTaille) maxTaille=pl.maxSize();
    }

    public double maxSize(){return maxTaille;}

    public String toString(){
	Iterator<Polyline> iter=this.listOfObstacles.iterator(); 
	String s=""; 
	while(iter.hasNext()){
	    Polyline pl=iter.next(); 
	    s+=pl+"\n"; 
	}
	return s; 
    }

    /** Find the distance between a car and a bumper, if at least one bumper is visible from the car, in direction angle */
    protected double distance(MobileState ev,double angle){
	double dist=50000.0; // a huge number again !
	double radAngle=(Math.PI*angle)/180.0;
	Iterator<Polyline> iter=this.listOfObstacles.iterator(); 
	while(iter.hasNext()){
	    Bumper bcurrent=(Bumper)iter.next(); 
	    // angle between the car and the center of the bumper
	    double x1=ev.getX(); 
	    double y1=ev.getY(); 
	    double x0=bcurrent.getX(); 
	    double y0=bcurrent.getY(); 
	    double theta; 
	    if(Math.abs(x1-x0)<1e-4){//verticals
		if(y1>y0) theta=3*Math.PI/2; 
		else theta=Math.PI/2; 
	    }
	    else {
		theta=Math.atan((y1-y0)/(x1-x0));
		if(x0<x1) theta+=Math.PI; 
		if(theta<0) theta+=Math.PI*2; 
	    }
	    double distToCenter=Math.sqrt((x1-x0)*(x1-x0)+(y1-y0)*(y1-y0)); 
	    double alpha1=Math.asin(bcurrent.getRadius()/distToCenter); 
	    double limitInf=Math.PI+theta-alpha1; 
	double limitSup=Math.PI+theta+alpha1; 
	if(limitInf<0) limitInf+=2*Math.PI; 
	if(limitInf>2*Math.PI) limitInf-=2*Math.PI; 
	if(limitSup<0) limitSup+=2*Math.PI; 
	if(limitSup>2*Math.PI) limitSup-=2*Math.PI; 
	if((radAngle<limitInf)||(radAngle>limitSup))continue; // this bumper is not visible; 
	// Bumper visible
	double gamma=Math.abs(theta-radAngle); 
	double terme1=bcurrent.getRadius()*bcurrent.getRadius(); 
	double terme2=distToCenter*Math.sin(gamma); 
	terme2=terme2*terme2; 
	double d1=distToCenter*Math.cos(gamma)-Math.sqrt(terme1-terme2); 
	if(d1<dist) dist=d1; 

	}// while
	return dist; 

    }// distance

     /** A set of obstacles will never be an outside wall */
    public double getXTrans(){return 0;}

   /** A set of obstacles will never be an outside wall */
    public double getYTrans(){return 0;}

    /** A set of obstacles will never be an outside wall */
    public void drawPolyline(Graphics gx){}
    
    /** Draw an internal wall  */
    public void drawPolyline(double xDep,double yDep,Graphics gx){
	Iterator<Polyline> iter=this.listOfObstacles.iterator(); 
	while(iter.hasNext()){
	    Polyline pl=iter.next(); 
	    pl.drawPolyline(xDep,yDep,gx); 
	}
    }//drawPolyline

   
}
	    
	    