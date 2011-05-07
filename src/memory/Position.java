package memory; 
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
 *    Position.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */



/** A auxiliary class to handle pairs of integers.*/

 class Position{
    private int x,y; 

    protected Position(int i,int j){
	this.x=i; 
	this.y=j;
    }

    protected int getX(){return x;}
    protected int getY(){return y; }

    protected void setX(int i){this.x=i;}
    protected void setY(int i){this.y=i;}

    public boolean equals(Object o){
	if(!(o instanceof Position)) return false; 
	Position a=(Position)o; 
	return (this.x==a.x)&&(this.y==a.y); 
    }

    public int hashCode(){
	return (7*x+y)%13; 
    }

    public String toString(){
	return "("+x+","+y+")"; 
    }
}
