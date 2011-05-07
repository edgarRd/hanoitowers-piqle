
/**
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

/*    TestMaabacV2.java
 *    13 dec. 06
 * 	  
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

/**
 * @author Francesco De Comite
 *
 * 13 dec. 06
 */
import maabacVersion2.ArmNV2;
import maabacVersion2.ElementaryMuscleEnvironmentV2;
import maabacVersion2.MaabacElementaryCooperativeAgent;
import maabacVersion2.MuscleFilterV2;
import qlearning.ConstantValueChooser;
import referees.OnePlayerReferee;
import agents.AbstractSwarm;
import agents.SwarmHashMap;
import algorithms.AbstractMemorySelector;
import algorithms.*;
import environment.IState;


public class TestMaabacV2 {
	 public static void main(String argv[]){
			// Number of arm segments */
			int nbseg=4; 
			/* Target's position  and radius */
			ArmNV2 bras=new ArmNV2(nbseg,0.5,3.5,0.2); 
			AbstractSwarm essaim=new SwarmHashMap(bras); 
			AbstractMemorySelector guru[]=new AbstractMemorySelector[2*nbseg]; 
			int contr[]=new int[2*nbseg];
			int maxi[]=new int[2*nbseg];
			MaabacElementaryCooperativeAgent agentsArray[]
			                =new MaabacElementaryCooperativeAgent[2*nbseg];
			for(int i=0;i<2*nbseg;i++) maxi[i]=50; 
			bras.setState(contr,maxi); 
			IState depart=bras.defaultInitialState();
			double epsilon=0.5; 
			for(int i=0;i<2*nbseg;i++){
			    guru[i]=new WatkinsSelector(0.8,new ConstantValueChooser(1.0)); 
			    //guru[i].setBoltzmann();
			    guru[i].setEpsilonGreedy(); 
			    //guru[i].setRouletteWheel(); 
			    guru[i].setEpsilon(epsilon);
		 	    guru[i].setGamma(1.0);  
			    guru[i].setAlpha(0.1);  
			    guru[i].setGeometricAlphaDecay(); 
			    guru[i].setDecay(1); 
			    guru[i].setTau(0.5); 
			    agentsArray[i]=new MaabacElementaryCooperativeAgent(new ElementaryMuscleEnvironmentV2(),
						  guru[i],
						  new MuscleFilterV2(i)); 
			    essaim.add(agentsArray[i]);
			} 
			
			// Set the neighbours
			for(int i=1;i<2*nbseg-1;i++){
				agentsArray[i].addNeighbour(agentsArray[i-1]);
				agentsArray[i].addNeighbour(agentsArray[i+1]);
			}
			agentsArray[0].addNeighbour(agentsArray[1]); 
			agentsArray[2*nbseg-1].addNeighbour(agentsArray[2*nbseg-2]);
			for(int i=0;i<2*nbseg;i++)
				agentsArray[i].buildInitialComposedState(depart);
			OnePlayerReferee arbitre=new OnePlayerReferee(essaim); 
			arbitre.setMaxIter(1000); 
			double total=0.0; 
			for(int i=1;i<100000;i++){
			    int u=arbitre.episode(depart); 
			    total+=u;
			    epsilon*=0.9999;
			    for(int k=0;k<2*nbseg;k++)
			    		guru[k].setEpsilon(epsilon);
			    //if(i%100==0) 
				System.out.println(i+" "+u+" "+(total/(i+0.0)));
			    if(i==5000) arbitre.setGraphical(); 
			    
			
			}

	 }	

}
