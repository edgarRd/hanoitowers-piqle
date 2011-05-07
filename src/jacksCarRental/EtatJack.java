package jacksCarRental; 
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
 *    EtatJack.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import environment.*;

public class EtatJack extends AbstractState{

    /** Number of cars at each location*/
    private int pos1,pos2; 
    
    public EtatJack(IEnvironment ct,int p1,int p2){
	super(ct); 
	pos1=p1; 
	pos2=p2; 
    }

    public int getPos1(){return pos1;}
    public int getPos2(){return pos2;} 

    

    public IState copy(){
	EtatJack nouveau=new EtatJack(myEnvironment,pos1,pos2); 
	return nouveau; 
    }

    public String toString(){
	return pos1+" "+pos2; 
    }

  
    
    /** One among k, k=20 */
    public double[] nnCoding(){
	double code[]=new double[40];
	code[pos1]=1.0; 
	code[pos2+20]=1.0;
	return code; 
    }
    
      
    public int  nnCodingSize(){return 40;}

 
    public boolean equals(Object o){
	if(!(o instanceof EtatJack)) return false; 
	EtatJack ej=(EtatJack)o; 
	return ((ej.pos1==this.pos1)&&(ej.pos2==this.pos2)); 
    }

    public int hashCode(){
	return (pos1+pos2)%13; 
    }

     /** Issuing information in FOIL-readable format */
    public String foilInfo(){
	return pos1+","+pos2; 
    }


    
  
}

