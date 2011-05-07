package mazes; 
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
 *    composantLabyrinthe.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */





import java.io.Serializable; 

/** A basic cell in a maze :
<ul> 
<li> Free cell </li>
<li> Walls</li>
<li> Treasure (or way out) </li>
</ul>
@author Francesco De Comite 
 @version $Revision: 1.0 $ 


*/

public class composantLabyrinthe implements Serializable{
    private int value; 

    /** THE free cell.*/
    private static composantLabyrinthe LIBRE=new composantLabyrinthe(0); 
    /** THE brick cell.*/
    private static composantLabyrinthe MUR=new composantLabyrinthe(1); 
    /** THE treasure.*/
    private static composantLabyrinthe BUT=new composantLabyrinthe(2);

    private composantLabyrinthe(int i){
	this.value=i;
    }

    /** @return the free cell.*/
    public composantLabyrinthe getFree(){return LIBRE;}
    /**@return the brick cell.*/
    public composantLabyrinthe getWall(){return MUR;}
    /** @return the trasure cell.*/
    public composantLabyrinthe getEnd(){return BUT;}

    public boolean isFree(){return this.equals(LIBRE); }
    public boolean isWall(){return this.equals(MUR); }
    public boolean isEnd(){return this.equals(BUT); }


    public String toString(){
	if(this.isFree()) return "-"; 
	if(this.isWall()) return "X";
	if(this.isEnd()) return "u"; 
	return "?????";  // never reached
    }

    /** The cell factory. */
    protected static composantLabyrinthe makeComponent(int i){
	switch(i){
	case 0 : return LIBRE; 
	case 1 : return MUR; 
	case 2 : return BUT; 
	default : return null; 
	}
    }
    protected int getValue(){return this.value;}
}
