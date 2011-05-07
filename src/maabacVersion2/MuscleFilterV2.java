
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

/*    MuscleFilterV2.java
 *    22 nov. 06
 * 	  
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

package maabacVersion2;

import maabacVersion2.ContractionV2;
import maabacVersion2.MaabacStateV2;
import environment.Filter;
import environment.IEnvironment;
import environment.IState;

/**
 * @author Francesco De Comite
 *
 * 22 nov. 06
 */
public class MuscleFilterV2 extends Filter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int index; 

    public MuscleFilterV2(int i){
	super(); 
	this.index=i;
    }

    public IState filterState(IState s,IEnvironment c){
	MaabacStateV2 ms=(MaabacStateV2)s;
	ContractionV2 cc=new ContractionV2(c,ms.getValue(this.index),
                                       ms.getMaxValue(this.index),
                                       ms.getDist(),
                                       ms.getDir(),
				       ms.getDdist(),
				       ms.getDdir()); 
	return cc; 
    }

}
