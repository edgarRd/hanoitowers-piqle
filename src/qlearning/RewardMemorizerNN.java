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
 *    RewardMemorizerNN.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */
import java.util.Random;

import neuralnetwork.NeuralNetwork;
import dataset.Dataset;
import dataset.Sample;
import environment.IAction;
import environment.IState;
import qlearning.IDefaultValueChooser;
import qlearning.NullValueChooser;




/** Memorizing Q(s,a) in a neural network. 

Each experience is transformed into a <code>Sample</code><br>
At some moments, the <code>Sample</code>s gathered into a <code>Dataset</code> are fed into a neural network, which is asked to learn Q(s,a) from this dataset.<br>
When asked to return a Q(s,a) value, this class uses the neural network to compute it.

@author Francesco De Comite 
 @version $Revision: 1.0 $ 

*/

public class RewardMemorizerNN implements IRewardStore{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The neural network plays the role of memory*/
    protected NeuralNetwork memory; 
    /** The current samples */
    protected Dataset myDataset=new Dataset(); 
    /** The maximum number of Samples contained into the dataset.*/
    protected int maxSize=5000; 
    /** Control the number of newcoming samples.*/ 
    protected int newOnes=0;
    /** Each time incoming samples reach limit, the neural network is asked to learn.*/
    protected int limit=50; 
    protected Random generateur=new Random(); 
    protected IDefaultValueChooser valueChooser;
    
    public RewardMemorizerNN(){
    		this.valueChooser=new NullValueChooser();
    		this.rescale=true; 
    }
    
    public RewardMemorizerNN(IDefaultValueChooser dvc){
    		this.valueChooser=dvc;
    		this.rescale=true; 
    }
    
    /** Maps R to [0,1] */
    protected double inverseLogistic(double x){
	double u=Math.exp(x); 
	return u/(1+u); 
    }

    /** Maps [0,1] to R */
    protected double logistic(double x){
	return Math.log(x/(1-x)); 
    }

    /** To rescale the values between 0 and 1 */
    protected boolean rescale=false; 

    public double getWeight(int i,int j,int k){
	return memory.getWeight(i,j,k);
    }
    
    public int getSizeOfLayer(int i){
	return memory.getSizeOfLayer(i);
    }

    public void setEpoch(int i){
	memory.setEpoch(i);
    }

    /** Enable rescaling */
    public void setRescale(){this.rescale=true;}
    
    /** Disable rescaling */
    public void unsetRescale(){this.rescale=false;}

    
 
    public double get(IState s,IAction a){
    	int prosize=a.nnCodingSize(); 
	double inputs[]=new double[s.nnCodingSize()+prosize];
	double resu[]=new double[1]; 
	System.arraycopy(s.nnCoding(),0,inputs,0,s.nnCodingSize()); 
	System.arraycopy(a.nnCoding(),0,inputs,s.nnCodingSize(),
					prosize);
	if(memory!=null)
	    {
		try{
		resu=memory.classify(inputs); 
		}
		catch(Exception e){System.err.println("xxx:"+e); System.exit(-1); }
		if(!rescale)
		    return resu[0];
		else
		    return logistic(resu[0]); 
	    }
		
	else return this.valueChooser.getValue();
 }



    public void setNN(int descLayers[]){
	memory=new NeuralNetwork(descLayers); 
	 memory.setEpoch(100); 
	 memory.initNetwork(); 
    }
     
    public void put(IState s,IAction a,IState sp,double qsa){
    	int prosize=a.nnCodingSize(); 
	if (memory==null) {// Network does not exist yet
	    // Build it
	    int archi[]=new int[3]; 
	    archi[0]=s.nnCodingSize()+prosize; 
	    //TODO RESTORE
	    archi[1]=7; //1+archi[0]/5; 
	    archi[2]=1; 
	    memory=new NeuralNetwork(archi); 
	    // TODO experiments for chess endgames : low down for other examples ? 
	    memory.setEpoch(100); 
	    memory.initNetwork(); 
	}
	double inputs[]=new double[s.nnCodingSize()+prosize];
	double outputs[]=new double[1]; 
	if (rescale)
	    outputs[0]=inverseLogistic(qsa); 
	else
	    outputs[0]=qsa; 
	System.arraycopy(s.nnCoding(),0,inputs,0,s.nnCodingSize()); 
	System.arraycopy(a.nnCoding(),0,inputs,
			s.nnCodingSize(),
			prosize); 
	myDataset.add(new Sample(inputs,outputs)); 
	newOnes++; 
	if(newOnes%limit==1) {
	    try{
		memory.learnFromDatasetNonStochastic(myDataset); 
	    }
	    catch(Exception e){System.err.println("YYY"+e); System.exit(-1);}
	}

	if (myDataset.numInstances()>maxSize){
	    int u=generateur.nextInt(maxSize); 
	    myDataset.remove(u); 
	}
	return;
	}
    


    /** Supposed to print Q(s,a) value.
     More difficult than when Q(s,a) are really stored : we can only sample the values...*/

    public String toString(){
	return "No interesting view on the Neural Network"; 
    }
		
    /** No dataset extraction at this time (september 2005,USTL ) */
    public Dataset extractDataset(){return null;}; 
}
