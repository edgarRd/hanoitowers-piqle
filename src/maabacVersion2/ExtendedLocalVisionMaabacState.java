
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

/*    ExtendedLocalVisionMaabacState.java
 *    23 nov. 06
 * 	  
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

package maabacVersion2;

import java.util.ArrayList;


import environment.ExtendedStateWithActions;
import environment.IEnvironment;
import environment.IState;


/**
 * @author Francesco De Comite
 *
 * 23 nov. 06
 */
public class ExtendedLocalVisionMaabacState extends ExtendedStateWithActions<ContractionV2,MuscleNullableAction>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * @param ct
	 */
	public ExtendedLocalVisionMaabacState(IEnvironment ct) {
		super(ct);
	}

	/**
	 * @param ct
	 * @param s
	 * @param actions
	 */
	
	public ExtendedLocalVisionMaabacState(IEnvironment ct, IState s,
			ArrayList<MuscleNullableAction> actions) {
		super(ct,s,actions);
	}
	
	protected ExtendedStateWithActions buildState(IEnvironment universe){
		ExtendedLocalVisionMaabacState nouveau=new ExtendedLocalVisionMaabacState(universe);
		return nouveau; 
	}
	
	

}
