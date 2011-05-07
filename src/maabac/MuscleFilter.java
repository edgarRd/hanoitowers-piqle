package maabac; 
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
 *    MuscleFilter.java
 *    Copyright (C) 2006 Francesco De Comite
 *
 */

import environment.Filter; 
import environment.IState; 
import environment.IEnvironment; 

/** Extract a Contraction from a MaabacState */

public class MuscleFilter extends Filter{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int index; 

    public MuscleFilter(int i){
	super(); 
	this.index=i;
    }

    public IState filterState(IState s,IEnvironment c){
	MaabacState ms=(MaabacState)s;
	Contraction cc=new Contraction(c,ms.getValue(this.index),
                                       ms.getMaxValue(this.index),
                                       ms.getDist(),
                                       ms.getDir(),
				       ms.getDdist(),
				       ms.getDdir()); 

	return cc; 
    }

}