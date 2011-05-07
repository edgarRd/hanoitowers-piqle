package mazes;

import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 


public class Bouton extends JButton{


    protected static int NW=0,
	NMY=1,
	NE=2,
	WMY=3,
	STILL=4,
	EMY=5,
	SW=6,
	SMY=7,
	SE=8,
	UNKNOWN=9,
	WALL=10,
	TRESOR=11, 
	ROBOT=12;
    
    
    private static String fichiers[]={
	//"/local/decomite/RECHERCHE/distribution/sources/mazes/images/arrowul.gif",
	"src/mazes/images/arrowul.gif",
	//"/local/decomite/RECHERCHE/distribution/src/mazes/images/arrowu.gif",
	"src/mazes/images/arrowu.gif",
	//"/local/decomite/RECHERCHE/distribution/src/mazes/images/arrowur.gif",
	"src/mazes/images/arrowur.gif",
	//"/local/decomite/RECHERCHE/distribution/src/mazes/images/arrowl.gif",
	"src/mazes/images/arrowl.gif",
	//"/local/decomite/RECHERCHE/distribution/src/mazes/images/still.gif",
	"src/mazes/images/still.gif",
	//"/local/decomite/RECHERCHE/distribution/src/mazes/images/arrowr.gif",
	"src/mazes/images/arrowr.gif",
	//"/local/decomite/RECHERCHE/distribution/src/mazes/images/arrowdl.gif",
	"src/mazes/images/arrowdl.gif",
	//"/local/decomite/RECHERCHE/distribution/src/mazes/images/arrowd.gif",
	"src/mazes/images/arrowd.gif",
	//"/local/decomite/RECHERCHE/distribution/src/mazes/images/arrowdr.gif",
	"src/mazes/images/arrowdr.gif",
	//"/local/decomite/RECHERCHE/distribution/src/mazes/images/grass.gif",
	"src/mazes/images/grass.gif",
	//"/local/decomite/RECHERCHE/distribution/src/mazes/images/wall.gif",
	"src/mazes/images/wall.gif",  
	//"/local/decomite/RECHERCHE/distribution/src/mazes/images/tresor.gif",
	"src/mazes/images/tresor.gif",
	//"/local/decomite/RECHERCHE/distribution/src/mazes/images/robot.gif"  
	"src/mazes/images/robot.gif"      
    };

    private static ImageIcon tableau[]=new ImageIcon[13]; 

    static{
	for(int i=0;i<13;i++)
	    tableau[i]=new ImageIcon(fichiers[i]); 
	System.out.println("init"); 
    }
    
    public Bouton(int i){
	super(tableau[i]);
	setEnabled(false); 
    }

    public void setIcon(int i){
	
	this.setDisabledIcon(tableau[i]); this.repaint(); 
    }


}
