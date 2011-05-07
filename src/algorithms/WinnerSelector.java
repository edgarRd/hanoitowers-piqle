package algorithms; 

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
 *    alo0ng with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    WinnerSelector.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */



import java.util.Random;

import dataset.Dataset;
import environment.ActionList;
import environment.IAction;
import environment.IState;
import environment.ITwoPlayerState;



/** A player which plays to win : 
<ul>
<li> If there is an action that leads to victory : Play it !</li>
<li> Otherwise, play at random (among the legal moves) </li>
</ul>

There is no learning for this algorithm, it is just intended to be a more serious opponent than the random player...

Only for two players game !
    @author Francesco De Comite (decomite at lifl.fr)
    @version $Revision: 1.0 $ 


*/

public class WinnerSelector implements ISelector{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IAction getChoice(ActionList l){
	if(l.size()==0) return null;
	IState s=l.getState(); 
	for(int i=0;i<l.size();i++){
	    IAction a=l.get(i); 
	    ITwoPlayerState current=(ITwoPlayerState) s.copy(); 
	    current=(ITwoPlayerState) current.modify(a); 
	    if((current.getWinner()==1)&&!current.getTurn()) return a; 
	    if((current.getWinner()==-1)&&current.getTurn()) return a; 
	}
	    Random generator=new Random(); 
	     
	    return l.get(generator.nextInt(l.size()));  
    }// getChoix

    /** Learning is meaningless for this algorithm */
    public void learn(IState s1,IState s2, IAction a,double reward){}; 

    /** Nothing to reset at the beginning of an episode */
    public void newEpisode(){};   

    /** No collected data... */
    public Dataset extractDataset(){return null;}

  
    
}
