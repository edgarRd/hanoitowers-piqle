package referees; 

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
 *    Partie2Joueurs.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */


import agents.TwoPlayerAgent;
import environment.IAction;
import environment.ITwoPlayerState;
import environment.IEnvironmentTwoPlayers;

/** A referee for a two-players game : 
    <ul>
    <li> Ask successively each player to play.</li>
    <li> Look if the game is over.</li>
    <li> Tells who is the winner. </li>
    </ul>

@author Francesco De Comite 
 @version $Revision: 1.0 $ 


 */

public class TwoPlayerReferee{
    
    /** One of the two players.  */
    private TwoPlayerAgent j1,j2; 


    /** Maximal length of an episode */
    private int maxIter=1000; 

    /** Reads the maximum length of an episode. */
    public int getMaxIter(){return this.maxIter;}

    /** Changes the maximal length of an episode.*/
    public void setMaxIter(int t){
	if(t>0) maxIter=t;}

    /** Player one's reward for this episode */
    private double totalRewardPlayer1; 

/** Player two's reward for this episode */
    private double totalRewardPlayer2; 
    

    /** Set the two players. */
    public TwoPlayerReferee(TwoPlayerAgent x,TwoPlayerAgent y){
	this.j1=x; 
	this.j2=y; 
    }
    
    /** Verbosity control. */
    private boolean verbosity=false; 
    
    /** Verbose */
    public void setVerbosity(){verbosity=true; }
    /** Mute*/
    public void unsetVerbosity(){verbosity=false;}
    
    /** Play one game.
	@return the winner : 
	<ul>
	<li> -1 : Player one</li>
	<li> 0 : Tie </li>
	<li> 1 : Player two </li>
	</ul>
    */
    public int episode(){
    IEnvironmentTwoPlayers ie=(IEnvironmentTwoPlayers)this.j1.getCurrentState().getEnvironment(); 
	ITwoPlayerState situation=ie.defaultInitialTwoPlayerState(); 
	j1.setInitialState(situation); 	
	this.j1.newEpisode(); 
	this.j2.newEpisode(); 
	this.totalRewardPlayer1=0.0; 
	this.totalRewardPlayer2=0.0; 
	IAction cj; 
	for(int i=0;i<maxIter;i++){
	    cj=j1.act();
	    totalRewardPlayer1+=this.j1.getLastReward(); 
	    if (verbosity) {
		    System.out.println("J1 plays "+cj); 	
	    }
	    situation=(ITwoPlayerState) j1.getCurrentState(); 
	    if (verbosity) System.out.println(situation); 
	    j2.getInformed(situation); 
	    if(situation.isFinal()){
		j1.getInformedFinal(situation); 
		totalRewardPlayer1+=this.j1.getLastReward(); 
		break; }
	    cj=j2.act(); 
	    totalRewardPlayer2+=this.j2.getLastReward(); 
	    if (verbosity) {
		    System.out.println("J2 plays "+cj); 
	    }
	    situation=(ITwoPlayerState) j2.getCurrentState();
	    if (verbosity) System.out.println(situation);
	    j1.getInformed(situation); 
	    if(situation.isFinal()) {
		j2.getInformedFinal(situation); 
		 totalRewardPlayer2+=this.j2.getLastReward(); 
		break; } 
	}// for i

	
	// who won ? 
	if(verbosity){
	if(situation.getWinner()==-1) System.out.println("J1 won\n"); 
	if(situation.getWinner()==1) System.out.println("J2 won\n"); 
	if(situation.getWinner()==0) System.out.println("Tie\n"); 
	}
	return situation.getWinner(); 
    }

    
    public double getRewardForEpisodePlayer1(){
	return totalRewardPlayer1;
    }

    public double getRewardForEpisodePlayer2(){
	return totalRewardPlayer2;
    }
    
    

}
	
