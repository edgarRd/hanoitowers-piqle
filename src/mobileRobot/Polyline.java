package mobileRobot; 
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
 *    Polyline.java
 *    Copyright (C) 2005,USTL  Francesco De Comite
 *
 */

import java.io.Serializable; 
import java.awt.Graphics;
/** Definition of a closed line : we just need to know whether a car is inside or outside of the region. 

@author Francesco De Comite 
 @version $Revision: 1.0 $ 
*/

public interface Polyline extends Cloneable,Serializable{

    public boolean inside(MobileState ev); 
    public boolean outside(MobileState ev); 
    public double maxSize(); 

    /** How far must we go in X direction in order to see all the line ? */
    public double getXTrans(); 
    /** How far must we go in Y direction in order to see all the line ? */
    public double getYTrans(); 
    /** Draw an external wall (bounds are calculated) */
    public void drawPolyline(Graphics gx); 
    /** Draw an internal wall (must use xtrans and Ytrans )*/
    public void drawPolyline(double xDep,double Ydep,Graphics gx); 
}


