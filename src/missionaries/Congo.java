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
 *    Congo.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import environment.AbstractEnvironmentSingle; 
import environment.ActionList; 
import environment.IState; 
import environment.IAction; 

/**Only legal moves are generated (nobody can be eaten).<br>

Brazzaville allows generation of <i>bad</i> moves.<br>



@see Brazzaville 

 @author Francesco De Comite
 @version $Revision: 1.0 $ 
 */


public class Congo extends AbstractEnvironmentSingle{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Number of people of the same kind. */
    protected int population=3; 
    /** Boat capacity.*/
    protected int boatSize=2; 

    public int getSizeOfPopulation(){return population;}
    

    protected MissionnaryState defaultInitialState; 

    public IState defaultInitialState(){return defaultInitialState;}

    public Congo(){
	defaultInitialState=new MissionnaryState(this); 
    }

    /** Customize the problem.*/
    public Congo(int pop,int size){
	this.population=pop; 
	this.boatSize=size; 
	defaultInitialState=new MissionnaryState(this); 
	
    }
    
    public boolean isFinal(IState e){
	MissionnaryState em=(MissionnaryState)e; 
	if(em.getBank()) return false; 
	return  ((em.getMissionaries()==0)&&(em.getCannibals()==0));
    }

    /** @return 1 if everybody is safe on the right bank.*/
    public double getReward(IState s1,IState s2,IAction a){
	if(isFinal(s2)) return 1; 
	else return 0; 
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
	    // put i missionaries into the boat
	    for(int j=0;j<=Math.min(nc,boatSize-i);j++)// add j cannibals
		if((i+j!=0) &&  // no empty boat allowed
		   ((i>=j)||(i==0)) &&// boat ok
		   ((nm-i==0)||(nm-i>=nc-j))&& // starting bank ok
		   ((population-nm+i==0)||(population-nm+i>=population-nc+j)))// other bank ok
		   loa.add(new MissionaryAction(i,j,boatSize)); 
	}
	return loa; 
    }

  
    public IState successorState(IState s,IAction a){
	MissionnaryState em=(MissionnaryState)s; 
	MissionaryAction am=(MissionaryAction)a; 
	MissionnaryState neuf=(MissionnaryState)em.copy(); 
	if(!em.getBank()){
	    neuf.setMa(em.getMissionaries()+am.getMissionaries()); 
	    neuf.setCa(em.getCannibals()+am.getCannibals()); 
	}
	else{
	    neuf.setMa(em.getMissionaries()-am.getMissionaries()); 
	    neuf.setCa(em.getCannibals()-am.getCannibals());
	}
	neuf.setBank(!em.getBank()); 
	return neuf; 
    }//etatSuivant

    public String toString(){
	return "Population : 2*"+population+" Boat "+boatSize; 
    }
    
    public int whoWins(IState s){
	if(isFinal(s)) return -1; 
	else return 0; 
    }// whoWins

   
    }
