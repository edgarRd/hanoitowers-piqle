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
 *    AbstractMember.java
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

import environment.AbstractEnvironmentSingle; 
import environment.IState; 
import environment.IAction; 
import environment.ActionList;

/** A (abstract) member is an assembly of muscles, with one free end (the member position), 
    which tries to reach a target */

abstract public class AbstractMember extends AbstractEnvironmentSingle{

    /** Number of Muscles */
    protected int taille; 

    /** Member position  */
    protected double x,y;
    
    /** Target coordinates */
    protected double xTarget; 
    protected double yTarget; 
    /** Target Radius */
    protected double rTarget=0.2; 

    public void setTarget(double x,double y,double r){
	this.xTarget=x;
	this.yTarget=y;
	this.rTarget=r; 
    }

     protected double beta1=1,
	beta2=3,
	beta3=6; 


    public double getXtarget(){return this.xTarget;}
    public double getYtarget(){return this.yTarget;}
    public double getRtarget(){return this.rTarget;}

    
    protected MaabacState defaultInitialState; 

    public int getTaille(){return this.taille;}

    public ActionList getActionList(IState s){
	System.out.println("Wrong fonction call, exit"); 
	System.exit(0); 
	return null;
    }

    public AbstractMember(){}

    public AbstractMember(double x,double y,double r){}

    public void setState(int c[],int m[]){
	this.taille=c.length;
	this.defaultInitialState=new MaabacState(this,taille); 
	for(int i=0;i<taille;i++)
	    this.defaultInitialState.init(i,c[i],m[i]); 
    }
    
    abstract public  IState successorState(IState s,IAction a); 

    protected double computeDdist(){
	double dist=((this.xTarget-this.x)*(this.xTarget-this.x))
	    +((this.yTarget-this.y)*(this.yTarget-this.y)); 
	dist=Math.sqrt(dist); 
	return dist; 
	
    }

    protected int computeDist(){
	double dist=((this.xTarget-this.x)*(this.xTarget-this.x))
	    +((this.yTarget-this.y)*(this.yTarget-this.y)); 
	dist=Math.sqrt(dist); 
	if(dist<beta1*rTarget) return 0; 
	if(dist<beta2*rTarget) return 1; 
	if(dist<beta3*rTarget) return 2; 
	return 3; 
    }// compute Dist

    protected double computeDdir(){
	double xx=this.x-this.xTarget; 
	double yy=this.y-this.yTarget;
	if(yy==0){
	    if(xx>0) return 0.0; 
	    else return Math.PI;
	}
	double angle=Math.atan(xx/yy); 
	if(xx*yy<0) angle+=Math.PI; 
	return angle;
    }

    protected int computeDir(){
	if(this.x<this.xTarget){
	    if(this.y<this.yTarget) return 2; 
	    else return 1;
	}
	else{
	    if(this.y<this.yTarget) return 3; 
	    else return 0;
	}
    }// computeDir

     public IState defaultInitialState(){
	return this.defaultInitialState;
    }

    public int whoWins(IState s){if(isFinal(s)) return -1; else return 0;}

    public boolean isFinal(IState s){
	double dist=((this.xTarget-this.x)*(this.xTarget-this.x))
	    +((this.yTarget-this.y)*(this.yTarget-this.y)); 
	return (Math.sqrt(dist)<this.rTarget); 
    }// isFinal

       public double getReward(IState s1,IState s2,IAction a){
	//	if(isFinal(s2)) System.out.println(s2); 
	if(isFinal(s2)) return 1; 
	//return -0.025; 
	return -1;
       }
    
}