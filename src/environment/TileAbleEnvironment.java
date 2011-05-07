package environment;
/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 *    TileAbleEnvironment.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */


import tiling.ListOfTiles;


/** When the problem can be solved with tiling


@author Francesco De Comite
 @version $Revision: 1.0 $ 
*/

 public interface TileAbleEnvironment{
   
   public ListOfTiles getTiles(IState s,IAction a); 

}


