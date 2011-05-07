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
 *    alo0ng with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    Gagnant.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import environment.IState;

/** Every problem where the set of states  can be enumerated might implement this interface.<br>

It provides methods to compute V*(state), print the values on the display, allow the programmer to access an individual V*() value.

*/

public interface ValueIteration{

    /** Computes V* for each state, stores the result in a n-dimensionnal array<br>

    n is the number of State dimensions. */
    public void computeVStar(double gamma); 

    /** Display all the values on the screen : it is up to the user to define the
	output format corresponding to its needs (gnuplot...)

	Warning : computeVStar must have been called before !
    */
    public void  displayVStar(); 

    /** Read V*(State) <br>
	Warning : computeVStar must have been called before !
    */
    
    public double getVStar(IState e); 

    /** Use V* to extract an optimal policy : you must define the output format.
   
    */
    public void extractPolicy(double gamma); 
}