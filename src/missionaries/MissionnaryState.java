package missionaries; 
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
 *    MissionnaryState.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import environment.IState; 
import environment.AbstractState; 
import environment.IEnvironment; 



/** 
A state is defined by : 
<ul>
<li> <code>Mg</code>, the number of missionaries on the left bank.</li>
<li> <code>Cg</code>, the number of cannibals on the left bank.</li>
<li> The bank where the boat is (initially : left)</li>
</ul>

Initial numbers of people, and boat capacity, are part of <code>Contraintes</code>

@author Francesco De Comite 
 @version $Revision: 1.0 $ 
*/
public class MissionnaryState extends AbstractState{
    
    /** Number of missionaries on the left bank. */
    private int MaLeft; 

    /** Number of cannibals on the left bank.*/
    private int CaLeft; 

    /** The bank where the boat is moored. */
    private boolean isAtLeft=true; 


      public MissionnaryState(IEnvironment ct){
	  super(ct); 
	  Congo mbt=(Congo)ct;  
	  this.MaLeft=mbt.getSizeOfPopulation(); 
	  this.CaLeft=mbt.getSizeOfPopulation(); 
    } 


    
    public IState copy(){
	MissionnaryState neuf=new MissionnaryState(this.myEnvironment); 
	neuf.MaLeft=this.MaLeft; 
	neuf.CaLeft=this.CaLeft; 
	neuf.isAtLeft=this.isAtLeft; 
	return neuf; 
    }

    public int getMissionaries(){return MaLeft; }
    public int getCannibals(){return CaLeft;}
    public boolean getBank(){return isAtLeft;}

    public void setMa(int a){MaLeft=a; }
    public void setCa(int a){CaLeft=a;}
    public void setBank(boolean b){this.isAtLeft=b;}

    public String toString(){
	String s="M : "+MaLeft+" C : "+CaLeft; 
	if(isAtLeft) s+=" left"; 
	else s+=" right"; 
	return s; 
    }

    public int hashCode(){
	int sum=MaLeft+7*CaLeft; 
	if(isAtLeft) return 2*sum; 
	else return 2*sum+1; 
    }

     public boolean equals(Object o){
	if(!(o instanceof MissionnaryState)) return false; 
	MissionnaryState el=(MissionnaryState)o; 
	if(isAtLeft!=el.isAtLeft) return false; 
	return((el.MaLeft==this.MaLeft)&&(el.CaLeft==this.CaLeft)); 
    }

   
    public boolean getTurn(){return false;}

     /** One among k coding, k<=10, of <code>Mg</code> and <code>Cg</code>, plus a boolean for the bank.*/
    public double[] nnCoding(){
	double code[]=new double[22]; 
	code[this.MaLeft%10]=1.0; 
	code[10+this.CaLeft%10]=1.0; 
	if(isAtLeft) 
	    code[20]=1.0; 
	else code[21]=1.0; 
	return code;
    }

    public int nnCodingSize(){return 22;}


   

}
    
