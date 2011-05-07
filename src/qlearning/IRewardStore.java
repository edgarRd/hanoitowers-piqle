package qlearning; 
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
 *    Rangement.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import java.io.Serializable;

import dataset.Dataset;
import environment.IAction;
import environment.IState;

/** Framework defining  methods to store, retrieve, enumerate state/action values.

 @author Francesco De Comite 
 @version $Revision: 1.0 $ 


*/

public interface IRewardStore extends Serializable{
    
    /** @return The state/action value */
    public double get(IState s,IAction a); 
    
    /** Store a state/action value. We sometimes need the new state (sp)... */
    public void put(IState s,IAction a,IState sp,double qsa); 

  
  
    /** Extract all the (state,action,value) vectors in a format suitable for use with Neural Networks.
@see neuralnetwork.NeuralNetwork
@see dataset.Dataset
@see dataset.Sample


  */
    public Dataset extractDataset(); 



}
