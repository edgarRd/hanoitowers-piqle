
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

/*    MaabacElementaryCooperativeAgent.java
 *    24 nov. 06
 * 	  
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

package maabacVersion2;

import java.util.ArrayList;

import agents.ElementaryCooperativeAgent;
import algorithms.ISelector;
import environment.Filter;
import environment.IEnvironment;
import environment.IState;


/**
 * @author Francesco De Comite
 *
 * 24 nov. 06
 */
public class MaabacElementaryCooperativeAgent extends
		ElementaryCooperativeAgent<ExtendedLocalVisionMaabacState,
										MuscleNullableAction> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public MaabacElementaryCooperativeAgent(IEnvironment s, ISelector al,
			Filter f) {
		super(s, al, f);
		this.lastAction=MuscleNullableAction.getNullAction();
	}
	

	protected ExtendedLocalVisionMaabacState buildState(IEnvironment universe,
			IState st,
			ArrayList<MuscleNullableAction> old)

{
			return new ExtendedLocalVisionMaabacState(this.universe,st,old);

}
	public void newEpisode() {
		super.newEpisode(); 
		this.lastAction=MuscleNullableAction.getNullAction();
	}
}
