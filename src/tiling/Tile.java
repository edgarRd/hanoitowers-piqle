package tiling; 
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
 *    Tile.java
 *    Copyright (C) 2005 Francesco De Comite
 *
 */


import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Iterator;
/** A tile just has to store : 
<ul>
<li> The tiling it belongs too</li>
<li> Its coefficient (theta) </li>
</ul>
*/


public class Tile implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static int nbTiles=0; 
    //private static double maxTheta=0.0;  
    private static int nbNorm=0;
    private static double borne=100.0;

    protected Tiling pavage; 
    protected double theta; 
    
    
    public Tile(Tiling p){
	//	System.err.println("#New tile "+nbTiles); 
	nbTiles++; 
	this.pavage=p; 
	//this.theta=generateur.nextDouble(); 
	this.theta=0.0;
 
	this.pavage.getAlreadySeen().add(this); 
	if(nbTiles%1000==0)System.out.println("--->"+nbTiles); 
    }

    public void setTheta(double x){ 

	this.theta=x;
	if(this.theta>borne) normalize(); 
 	else{
 	    if(this.theta<-borne)normalize();
 	}
    }
    private void setThetaInternal(double x){
	this.theta=x;
    }

    public double getTheta(){return this.theta;}

    public String toString(){
	return "Pavage "+this.pavage+" theta "+this.theta; 
    }
   
    private void normalize(){
	System.err.println("Normalisation "+nbNorm);  
	Iterator c=this.pavage.getAlreadySeen().iterator(); 
	while(c.hasNext()){
	    Tile current=(Tile)(c.next()); 
	    double u=current.getTheta(); 
	    current.setThetaInternal(u/2.0);
	}
	nbNorm++;
       
    }
    @SuppressWarnings("unused")
	private void makeHistogram(int step){
	System.err.println("Histogramme "+step); 
	double tab[]=new double[20001]; 
	Iterator c=this.pavage.getAlreadySeen().iterator(); 
	while(c.hasNext()){
	    Tile current=(Tile)(c.next()); 
	    double u=current.getTheta(); 
	    tab[(int)((u/borne)*10000+10000)]++;
	}
	try{
	 PrintStream tot=new PrintStream(new FileOutputStream("/tmp/histo"+step)); 
	 for(int i=0;i<20001;i++)
	     tot.println(((i/10000.0)-1)+" "+tab[i]); 
	 tot.close(); 
	}
	catch(Exception e){ System.err.println(e.getMessage()); System.exit(0); }

    }//makeHistogram
}
