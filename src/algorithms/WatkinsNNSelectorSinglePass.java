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
 *    WatkinsNNSelectorSingleSinglejava
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import qlearning.RewardMemorizerNNSinglePass;
import qlearning.IDefaultValueChooser;



/** Watkin's algorithm to manage and compute Q(s,a), but Q(s,a) are learned thanks to a memoryless Neural Network. */


public class WatkinsNNSelectorSinglePass extends WatkinsSelector{

  

  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public WatkinsNNSelectorSinglePass(double l){
	super(l); 
	memory=new RewardMemorizerNNSinglePass(); 
    }
	
	public WatkinsNNSelectorSinglePass(double l,IDefaultValueChooser dvc){
		super(l); 
		memory=new RewardMemorizerNNSinglePass(dvc); 
	    }

    /** Auxiliary/Debug : makes it possible to inspect the underlying Neural Network.*/
      public double getWeight(int i, int j,int k){
	 return ((RewardMemorizerNNSinglePass)memory).getWeight(i,j,k); 
     }
     

    /** Auxiliary/Debug : makes it possible to inspect the underlying Neural Network.*/
     public int getSizeOfLayer(int i){
	 return ((RewardMemorizerNNSinglePass)memory).getSizeOfLayer(i);
     }


}
