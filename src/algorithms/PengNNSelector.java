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
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    PengNNSelector.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */
import qlearning.IDefaultValueChooser;
import qlearning.RewardMemorizerNN;


/** <ul>
 <li> Algorithm : Peng & Williams</li>
<li> Q(s,a) values stored/learnt/computed in a Neural Network</li>
</ul>
@author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 


*/

public class PengNNSelector extends PengSelector{ 

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PengNNSelector(double l){
	super(l); 
	this.memory=new RewardMemorizerNN(); 
    }
	

    /**
	 * @param l
	 * @param dvc
	 */
	public PengNNSelector(double l, IDefaultValueChooser dvc) {
		super(l);
		this.memory=new RewardMemorizerNN(dvc);
	}


	/** Auxiliary/Debug : makes it possible to inspect the underlying Neural Network.*/
      public double getWeight(int i, int j,int k){
	 return ((RewardMemorizerNN)memory).getWeight(i,j,k); 
     }
     
    /** Auxiliary/Debug : makes it possible to inspect the underlying Neural Network.*/
     public int getSizeOfLayer(int i){
	 return ((RewardMemorizerNN)memory).getSizeOfLayer(i);
     }
}
