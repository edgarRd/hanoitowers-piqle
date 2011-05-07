package memory; 
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
 *    MemoryZP.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */
import environment.IAction; 
import environment.IState; 
import environment.ITwoPlayerState;

/** 
The version studied by  <a href="http://www.cs.tau.ac.il/%7Ezwick/papers/memory-game.ps.gz">Zwick and Patterson</a>,where they try to maximize the number of cards. 

Compared to <code>MemoryBoard</code>, only the <code>getReward</code> method changes.


@see MemoryBoard

 @author Francesco De Comite 
 @version $Revision: 1.0 $ 

 */

public class MemoryBoardZP extends MemoryBoard{

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public MemoryBoardZP(){}; 

    public  MemoryBoardZP(int i,int j){super(i,j);}


    public double getReward(IState s1,IState s2,IAction a){
    	ITwoPlayerState ss2=(ITwoPlayerState)s2;
	if(ss2.getTurn())
	    return -nbCartes/4+((MemoryState)s2).firstFound(); 
	else 
	    return -nbCartes/4+((MemoryState)s2).secondFound(); 
    }
}
