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
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    Stockage.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import dataset.Dataset;
import dataset.Sample;
import environment.IAction;
import environment.IState;
import qlearning.NullValueChooser;
import qlearning.IDefaultValueChooser;




/** Memorizing  Q(s,a) in HashMap. The key is the pair (state, value). */

public class RewardMemorizer implements IRewardStore{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private HashMap<ActionStatePair, Double> map = new HashMap<ActionStatePair, Double>();
    @SuppressWarnings("unused")
	private Random generateur=new Random(); 
    /** Number of items stored */
    private int numberOfItems=0; 
    private int histogram[]=new int[1000]; 
    private static int numer=0;  
    @SuppressWarnings("unused")
	private int order; 
    private IDefaultValueChooser valueChooser; 

    public RewardMemorizer(){
    	this.valueChooser=new NullValueChooser();
	// Debug : to verify that several RewardMemorizers are created
	this.order=numer; 
	numer++;
    }
    public RewardMemorizer(IDefaultValueChooser vc){
    		this.valueChooser=vc; 
    		this.order=numer; 
    		numer++;	
    }
  
    /** Read Q(s,a) */
    public double get(IState s,IAction a){
	if((a==null)||(s==null)) return 0; 
	//DEBUG
	//System.out.println(s.getClass()+" "+a.getClass());
	ActionStatePair us=new ActionStatePair(a,s); 
	Double db=(Double)(map.get(us));
	if (db==null){
	    numberOfItems++; 
	    /*
	    if(numberOfItems%1000==0){
	    		System.out.println("\t\t\t"+order+"--->"+numberOfItems);
	    }
	    */
	    double u=this.valueChooser.getValue(); 
	     map.put(new ActionStatePair(a,s),new Double(u)); 
	    return u;
	}
	return db.doubleValue(); 
    }

     
    /** Store Q(s,a) : change its value if already there.
     */
    public void put(IState s,IAction a,IState sp,double qsa){
	if(sp!=null){
	    if(map.put(new ActionStatePair(a,s),new Double(qsa))==null)
		{ 
		    numberOfItems++;
		}
	    
	    }
	    else {
	    this.map.put(new ActionStatePair(a,s), new Double(qsa));	    
	    }
    }

   

    /** To monitor the evolution of Q(s,a) values.
     */
    public void makeHistogram(){ 
	Set keys=map.keySet(); 
	Iterator enu=keys.iterator(); 
	double min=10000.0; 
	double max=-10000.0; 
	double sum=0.0;
	int number=0; 
	while(enu.hasNext()){
	    ActionStatePair courante=(ActionStatePair)enu.next(); 
	   //Double db=(Double)this.get(courante.getState(),courante.getAction());
	    Double db=new Double (this.get(courante.getState(),courante.getAction()));
	    double d=db.doubleValue(); 
	    if(d>max) max=d; 
	    if(d<min) min=d; 
	    //sum+=db; 
	    sum += d;
	    number++; 
	}
	double mean=sum/number; 
	double ecartType=0.0; 
       enu=keys.iterator(); 
	while(enu.hasNext()){ 
	    ActionStatePair courante=(ActionStatePair)enu.next(); 
	    //Double db=(Double)this.get(courante.getState(),courante.getAction());
	    Double db=new Double(this.get(courante.getState(),courante.getAction()));
	    double d=db.doubleValue(); 
	    ecartType+=(d-mean)*(d-mean); 
 	    histogram[(int)Math.floor(999*(d-min)/(max-min))]++; 
	}
	
    }// makeHistogram

   public void displayHistogram(){
	for(int i=0;i<1000;i++)
	    System.out.println(i+" "+histogram[i]); 
    }
	  

    
	@SuppressWarnings("unchecked")
	public String toString(){
	HashMap<IState,IAction> prov=new HashMap<IState,IAction>(); 
	HashMap local=new HashMap(map); 
	Set keys=local.keySet(); 
	String s=keys.size()+" state/action pairs \nListing of ALL  Q(s,a)\n";  
	Iterator enu=keys.iterator(); 
	ActionStatePair courante=null; 
	while(enu.hasNext()){
	    courante=(ActionStatePair)enu.next(); 
	    s+=courante.getState()+" "+courante.getAction()+" "+this.get(courante.getState(),courante.getAction())+"\n";
	    IAction bestAct=(IAction)(prov.get(courante.getState())); 
	    if(bestAct==null) prov.put(courante.getState(),courante.getAction()); 
	    else{
		if(this.get(courante.getState(),courante.getAction())>
		   this.get(courante.getState(),bestAct))
		    prov.put(courante.getState(),courante.getAction());
	    }
	
	    
	}
	Set bk=prov.keySet(); 
	Iterator ebis=bk.iterator();
	s+="Best values Q(s,a) for given s and a\n"; 
	while(ebis.hasNext()){
	    IState s1=(IState)ebis.next(); 
	    IAction best=(IAction)(prov.get(s1)); 
	    s+=s1+"---->"+best+" : "+this.get(s1,best)+"\n"; 
	}
	 return s;
    } // toString


    /** Extracts dataset for use with local NN */ 
    public Dataset extractDataset(){
	Dataset forNN=new Dataset(); 
	Set keys=this.map.keySet(); 
	Iterator enu=keys.iterator(); 
	while(enu.hasNext()){
	    ActionStatePair courante=(ActionStatePair)enu.next(); 
	    IState etat=courante.getState(); 
	    IAction act=courante.getAction();
	    int prosize=act.nnCodingSize(); 
	    double u[]=new double[etat.nnCodingSize()+prosize]; 
	    System.arraycopy(etat.nnCoding(),0,u,0,etat.nnCodingSize()); 
	    System.arraycopy(act.nnCoding(),0,u,etat.nnCodingSize(),prosize); 
	    double v[]=new double[1]; 
	    v[0]=(1.0+this.get(etat,act))/2.0;
	    forNN.add(new Sample(u,v)); 
	}
	return forNN;    
    }// extractDataset

    
}

