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
 *    MobileState.java
 *    Copyright (C) 2005,USTL  Francesco De Comite
 *
 */

import environment.*; 

/** A racing car is defined with : 
<ul>
<li> Its position on the circuit, aas its x,y coordinates. Not available for learning</li>
<li> Its speed (an integer)</li>
<li> Its angle with the X-axis (in degrees)  : not available for learning</li>
<li> The distance of the robot from the wall in as many directions as the number of pixels in its retina
(see Circuit.retinaSize) : this distance might be continuous or discretized</li>
</ul>

@author Francesco De Comite
 @version $Revision: 1.0 $ 
*/


public class MobileState extends AbstractState{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected double XCoordinate, YCoordinate, angle;
    protected int speed=0; 
    /** How far is the car from the walls ? (continuous values) */
    protected double distances[]; 

    /** How far are we from the walls ? (discretized values) */
    protected int discreteDistances[]; 
    private int[] primes={5,7,11,13,17,19,21,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,87,89,91}; 

    /** Maximum speed of the car */
    protected static int  maxSpeed=10; 
    

    /** 
	@param x first coordinate
	@param y second coordinate
	@param a angle with x axis (in degrees)
	@param s speed
	@param univers the circuit
    */
    public MobileState(double x, double y, double a,int s,IEnvironment univers){
	super(univers); 
	this.XCoordinate=x; 
	this.YCoordinate=y; 
	if(s>maxSpeed)this.speed=maxSpeed; 
	else this.speed=s; 
	this.angle=a; 
	this.distances=((Circuit)this.myEnvironment).getView(this); 
	double dmax=((Circuit)this.myEnvironment).maxSize(); 
	//	double R1=((CircularCircuit)this.myContraintes).smallRadius();
	//double dmax=2.0*Math.sqrt(R2*R2-R1*R1); 
	this.discreteDistances=new int[this.distances.length]; 
	for(int i=0; i<this.distances.length; i++){
	    if(this.distances[i]<dmax/10.0) {this.discreteDistances[i]=0; continue;} 
	    if(this.distances[i]<(2*dmax/3)) {this.discreteDistances[i]=1; continue;}
	    this.discreteDistances[i]=2; 
	}
    }

    public MobileState(MobileState ev,MobileAction av){
	super(ev.myEnvironment); 
	double angleInRad=(ev.angle*Math.PI)/180.0;
	double nx=ev.XCoordinate+(ev.speed*Math.cos(angleInRad)); 
	double ny=ev.YCoordinate+(ev.speed*Math.sin(angleInRad)); 
	this.XCoordinate=nx; 
	this.YCoordinate=ny; 
	this.angle=av.getAngle(); 
	this.speed=ev.getSpeed()+av.getSpeed(); 
	this.distances=((Circuit)this.myEnvironment).getView(this); 
	double dmax=((Circuit)this.myEnvironment).maxSize(); 
// 	double R2=((CircularCircuit)this.myContraintes).bigRadius(); 
// 	double R1=((CircularCircuit)this.myContraintes).smallRadius();
// 	double dmax=2.0*Math.sqrt(R2*R2-R1*R1); 
	this.discreteDistances=new int[this.distances.length]; 
	for(int i=0; i<this.distances.length; i++){
	    if(this.distances[i]<dmax/3.0) {this.discreteDistances[i]=0; continue;}
	    if(this.distances[i]<(2*dmax/3)) {this.discreteDistances[i]=1; continue;}
	    this.discreteDistances[i]=2; 
	}

    }//EtatVoiture

    protected double getDistance(int i){return distances[i];}

    protected double getX(){return this.XCoordinate; }
    protected double getY(){return this.YCoordinate;}
    protected double getAngle(){return this.angle; }
    protected int getSpeed(){return this.speed;}

    protected void setX(double x){this.XCoordinate=x;}
    protected void setY(double y){this.YCoordinate=y;}
    protected void setAngle(double a){
	this.angle=a; 
	while(angle>360) angle-=360; 
	while(angle<0) angle+=360; 
    }
    protected void setSpeed(int i){
    	if(i<0) return; 
    	if(i>maxSpeed) this.speed=maxSpeed; 
    	else
    	this.speed=i;}
    

    public IState copy(){
	return new MobileState(this.XCoordinate,this.YCoordinate,this.angle,this.speed,this.myEnvironment); 
    }

    public String toString(){
	//	return this.XCoordinate+","+this.YCoordinate+","+this.angle+","+this.speed+" "+this.hashCode(); 
	String s="Angle "+this.angle+" "; 
	for(int i=0;i<this.distances.length;i++) s+=+this.discreteDistances[i]+" "; 
	return s; 
    }

    public int hashCode(){
	int construct=this.speed;
	for(int i=0;i<this.discreteDistances.length;i++){
	    construct+=primes[i]*discreteDistances[i]; 
	}
	construct=construct%primes[this.distances.length]; 
	return construct; 
    }//hashCode
   
    public boolean equals(Object o){
	if(!(o instanceof MobileState)) return false; 
	MobileState ev=(MobileState)o; 
	if(this.speed!=ev.speed) return false; 
	for(int i=0;i<this.distances.length;i++)
	    if(this.discreteDistances[i]!=ev.discreteDistances[i]) return false; 
	//if(Math.abs(this.angle-ev.angle)>0) return false; 
	return true; 
    }//equals
    
    public int nnCodingSize(){return 2+this.discreteDistances.length;}

    /** Dummy coding .. TODO */
    public double[] nnCoding(){
	double code[]=new double[this.nnCodingSize()]; 

	return code; 
    }

   

    @SuppressWarnings("unused")
	private static String distValue(int i){
	if(i==0) return "Proche"; 
	if(i==1) return "Median"; 
	return "Lointain";
    }

  
    public String coordinates(){return XCoordinate+" "+YCoordinate;}

	/**
	 * @return the maxSpeed
	 */
	public static int getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * @param maxSpeed the maxSpeed to set
	 */
	public static void setMaxSpeed(int maxSpeed) {
		MobileState.maxSpeed = maxSpeed;
	}
	
}