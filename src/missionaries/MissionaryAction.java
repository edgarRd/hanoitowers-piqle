package missionaries; 

/*
 *    This program is free software; you cannibalsOnBoat redistribute it and/or modify
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
 *    MissionaryAction.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */


import environment.IAction; 


/** Action = number of missionaries and number of cannibals in the boat.

@author Francesco De Comite 
 @version $Revision: 1.0 $ 
 */
public class MissionaryAction implements IAction{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Number of missionaries in the boat.*/
    private int missionariesOnBoat; 
    /** Number of cannibals in the boat */
    private int cannibalsOnBoat; 
    static private int sizeOfBoat; 
    

    public MissionaryAction(int m,int c,int size){
	this.missionariesOnBoat=m; 
	this.cannibalsOnBoat=c; 
	MissionaryAction.sizeOfBoat=size; 
    }
    
    public int getMissionaries(){return missionariesOnBoat; }
    public int getCannibals(){return cannibalsOnBoat; }

    public String toString(){
	String s1,s2; 
	if(missionariesOnBoat<2) s1="missionary"; else s1="missionaries"; 
	if(cannibalsOnBoat<2) s2="cannibal"; else s2="cannibals"; 
	return "On board :  "+missionariesOnBoat+" "+s1+" and "+cannibalsOnBoat+" "+s2; 
    }
  
    public int hashCode(){
	return missionariesOnBoat+3*cannibalsOnBoat; 
    }

     public boolean equals(Object o){
	if(!(o instanceof MissionaryAction)) return false; 
	MissionaryAction el=(MissionaryAction)o; 
	return((el.missionariesOnBoat==this.missionariesOnBoat)&&(el.cannibalsOnBoat==this.cannibalsOnBoat)); 
    }

  
    public IAction copy(){
	return new MissionaryAction(missionariesOnBoat,cannibalsOnBoat,sizeOfBoat); 
    }

    /** One among k coding (k=2*sizeOfBoat+2) */
    public double[] nnCoding(){
	double code[]=new double[2*MissionaryAction.sizeOfBoat+2]; 
	code[this.missionariesOnBoat]=1.0; 
	code[MissionaryAction.sizeOfBoat +this.cannibalsOnBoat+1]=1.0; 
	return code;
    }

   public int nnCodingSize(){return 2*MissionaryAction.sizeOfBoat+2;}

    

	
}
