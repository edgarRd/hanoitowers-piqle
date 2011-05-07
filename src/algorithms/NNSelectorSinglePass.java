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
 *    NNSelectorSinglePass.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */



import qlearning.RewardMemorizerNN;
import qlearning.RewardMemorizerNNSinglePass;
import qlearning.IDefaultValueChooser;

/** The underlying Neural Network does not memorize the past experiences, 
 *  and only learns from the last experience.

@author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 




*/

public class NNSelectorSinglePass extends NNSelector{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NNSelectorSinglePass(){
	memory=new RewardMemorizerNNSinglePass(); 
    }
	
	public NNSelectorSinglePass(IDefaultValueChooser dvc){
		super(dvc);
		memory=new RewardMemorizerNNSinglePass(dvc); 
    	((RewardMemorizerNNSinglePass)memory).setRescale(); 
	}
	
	public NNSelectorSinglePass(int descLayers[]){
		memory=new RewardMemorizerNNSinglePass(); 
		((RewardMemorizerNNSinglePass)memory).setNN(descLayers);
	    }
	    
}
