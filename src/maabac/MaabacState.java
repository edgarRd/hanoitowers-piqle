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
 *    MaabacState.java
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

/* A State gathering all the individual muscle contractions informations. */


import environment.AbstractState; 
import environment.IEnvironment; 
import environment.IState; 

import java.util.Random; 

public class MaabacState extends AbstractState{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Random gene=new Random();
    
    protected int taille=4; 
    protected int contractionValues[]; 
    protected int maxValues[]; 
    protected int discreteDistance=-1; 
    protected int discreteDirection=-1; 
    protected double distance=0.0;
    protected double direction=0.0; 

    public int getTaille(){return this.taille;}

    public MaabacState(IEnvironment ct){
	super(ct); 
	this.contractionValues=new int[this.taille]; 
	this.maxValues=new int[this.taille];
	for(int i=0;i<this.taille;i++) this.maxValues[i]=50;	
    }

    public MaabacState( IEnvironment ct,int t){
	super(ct); 
	this.taille=t; 
	this.contractionValues=new int[this.taille];
	this.maxValues=new int[this.taille]; 
	for(int i=0;i<this.taille;i++) this.maxValues[i]=50;
    }

    public int getValue(int i){
	return this.contractionValues[i]; 
    }

    public int getMaxValue(int i){
	return this.maxValues[i];
    }
    
    public void changeValue(int i,MuscleAction a){
	int max=this.maxValues[i]; 
	int modif=a.getValue(); 
	int nov=this.contractionValues[i]; 
	nov=nov+modif; 
	if(nov<0) nov=0; 
	if(nov>=max) nov=max-1; 
	this.contractionValues[i]=nov; 
    }

    protected void setDist(int d){this.discreteDistance=d;}
    protected void setDir(int d){this.discreteDirection=d;}
    protected void setDdist(double d){this.distance=d;}
    protected void setDdir(double d){this.direction=d;}

    public int getDist(){return this.discreteDistance;}
    public int getDir(){return this.discreteDirection;}
    public double getDdist(){return this.distance;}
    public double getDdir(){return this.direction;}

     /** Clone */
    public IState copy(){
	MaabacState neuf=new MaabacState(myEnvironment,this.taille); 
	for(int i=0;i<this.taille;i++){
	    neuf.contractionValues[i]=this.contractionValues[i]; 
	    neuf.maxValues[i]=this.maxValues[i];
	    neuf.discreteDistance=this.discreteDistance;
	    neuf.discreteDirection=this.discreteDirection;
	    neuf.distance=this.distance;
	    neuf.direction=this.direction; 
	}
	return neuf; 
    }
    
      public String toString(){
	String s=""; 
	for(int i=0;i<this.taille;i++)
	     s+=this.contractionValues[i]+" "; 
	s+=this.discreteDistance+" "+this.discreteDirection; 
	return s; 
    }
    

    public boolean equals(Object o){
	if(!(o instanceof MaabacState)) return false; 
	MaabacState ms=(MaabacState)o; 
	if(this.discreteDistance!=ms.discreteDistance) return false; 
	if(this.discreteDirection!=ms.discreteDirection) return false; 
	for(int i=0; i<taille;i++){
	    if(this.contractionValues[i]!=ms.contractionValues[i]) return false; 
	    if(this.maxValues[i]!=ms.maxValues[i]) return false; 
	}
	return true; 
    }

    public int hashCode(){
	int sum=0; 
	for(int i=0;i<this.taille;i++)
	    sum+=this.contractionValues[i]; 
	sum+=this.discreteDistance+this.discreteDirection; 
	return sum%23; 
    }

    public int nnCodingSize(){return this.taille+4;}
    
    public double[] nnCoding(){
	double code[]=new double[this.taille+2]; 
	for(int i=0;i<this.taille;i++){
	    int max=this.maxValues[i]; 
	    code[i]=this.contractionValues[i]/(max+0.0); 
	}
	code[this.taille]=(this.discreteDistance+0.0)/4.0; 
	code[this.taille+1]=(this.discreteDirection+0.0)/4.0; 
	code[this.taille+2]=this.distance;
	code[this.taille+3]=this.direction/(2*Math.PI); 
	return code;
    }

    /** b=true, random init
	b=false initialize to 0 */
    protected void init(int i,boolean b){
	int max=this.maxValues[i]; 
	if(b) this.contractionValues[i]=gene.nextInt(max); 
	else this.contractionValues[i]=0; 
    }

    protected void init(int i,int c,int max){
	this.maxValues[i]=max; 
	this.contractionValues[i]=c;
    }

    
}