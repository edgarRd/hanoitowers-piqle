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
 *    Brazzaville.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import environment.ActionList; 
import environment.IState; 
import environment.IAction; 

/** 
    Filling of the boat follows the game rules, but we do not verify whether someone can be eated on either bank. <br>

Negative reward will be assign in such cases.

Congo only generates non-eating moves. 
    

@see Congo 




 @author Francesco De Comite 
 @version $Revision: 1.0 $ 

 */

public class Brazzaville extends Congo{

    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/** A state is final either because everybody is safe on the right bank, 
     * or because someone eated someone else. */
	
  public boolean isFinal(IState e){
      boolean b=false; 
      MissionnaryState em=(MissionnaryState)e; 
      int m=em.getMissionaries(); 
      int c=em.getCannibals(); 
      if(!em.getBank()&&(m==0)&&(c==0))  b=true; 
      if((c>m)&&(m!=0)) b=true; 
      if((population-c>population-m)&&(population-m!=0)) b=true; 
      return b; 
    }


    /** @return 1 if everybody is safe.
	@return -1 is someone is eaten
	@return 0 otherwise
    */
    public double getReward(IState s1,IState s2,IAction a){
	if(!isFinal(s2)) return -1;  
	// Otherwise, win or lost ?
	MissionnaryState em=(MissionnaryState)s2;
	int m=em.getMissionaries(); 
	int c=em.getCannibals(); 
	if((m==0)&&(c==0)) return 1; 
	return -1000; 
    }

    /** We can loose */
    public int whoWins(IState s){
	if(!isFinal(s)) return 0; 
	// Otherwise, win or lost ?
	MissionnaryState em=(MissionnaryState)s;
	int m=em.getMissionaries(); 
	int c=em.getCannibals(); 
	if((m==0)&&(c==0)) return -1; 
	return 1; 


    }

   
    public ActionList getActionList(IState s){
	MissionnaryState el=(MissionnaryState) s;
	ActionList loa=new ActionList(el); 
	int nm,nc; 
	int MaLeft=el.getMissionaries(); 
	int CaLeft=el.getCannibals(); 
	if(el.getBank()){
	    nm=MaLeft; 
	    nc=CaLeft; 
	}
	else {
	    nm=population-MaLeft; 
	    nc=population-CaLeft; 
	}
	for(int i=0;i<=Math.min(nm,boatSize);i++){
	    // i missionaries into the boat
	    for(int j=0;j<=Math.min(nc,boatSize-i);j++)// add j cannibals
		if((i+j!=0) &&  // no empty boat
		   ((i>=j)||(i==0)))// boat ok
		{			
		   loa.add(new MissionaryAction(i,j,boatSize));
		}
	}
	return loa; 
    }
}
