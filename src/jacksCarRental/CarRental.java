package jacksCarRental; 
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
 *    CarRental.java
 *    Copyright (C) 2004 Francesco De Comite
 *
 */
import environment.*; 
import statistics.Poisson; 
import qlearning.ValueIteration; 

import java.util.Random; 

/** Here are defined the settings of the problem : probabilities, rewards...

The code for Value Iteration is copied/adapted from <ahref="http://www.cs.ualberta.ca/~sutton/book/code/jacks.lisp">Sutton & Barto code</a>
 */
public class CarRental extends AbstractEnvironmentSingle implements ValueIteration{
    private Random generateur=new Random(); 
    /** Maximum number of cars to move from one location to another */
    private int maxMove=5; 
    /** Cars requested at location 1 */
    private Poisson request1=new Poisson(3.0,20); 
    /** Cars requests at location 2 */
    private Poisson request2=new Poisson(4.0,20);
    /** Cars returned at location 1 */
    private Poisson return1=new Poisson(3.0,20); 
    /** Cars returned at location 2 */
    private Poisson return2=new Poisson(2.0,20); 


    private int lambdareq1=3; 
    private int lambdareq2=4; 
    private int lambdadrop1=3; 
    private int lambdadrop2=2; 

    private int req1; 
    private int req2;

    private int ret1=0; 
    private int ret2=0; 


    public ActionList getActionList(IState s){
	EtatJack ej=(EtatJack)s; 
	int nbPos1=ej.getPos1(); 
	int nbPos2=ej.getPos2(); 
	ActionList loa=new ActionList(ej); 
	for(int i=0;i<5;i++){
	    if(i<=nbPos1) loa.add(new ActionJack(i)); 
	    if(i<=nbPos2) loa.add(new ActionJack(-i)); 
	}
	return loa; 
    }// getListeAction 

    /** Next state is probabilistic */
    public IState successorState(IState s,IAction a){
	EtatJack ej=(EtatJack)s; 
	ActionJack aj=(ActionJack)a; 
	int net1=ej.getPos1()-aj.getNumber(); 
	if(net1>20) net1=20; 
	if(net1<0) net1=0; 
	int net2=ej.getPos2()+aj.getNumber(); 
	if(net2>20) net2=20; 
	if(net2<0) net2=0; 
	
	req1=request1.get();
	if(req1>net1) req1=net1; 
	req2=request2.get(); 
	if(req2>net2) req2=net2; 

	ret1=return1.get(); 
	ret2=return2.get(); 
	net1=net1-req1+ret1; 
	if(net1>20) net1=20; 
	net2=net2-req2+ret2; 
	if(net2>20) net2=20; 
	return new EtatJack(this,net1,net2); 
    }// etatSuivant

      
	public IState defaultInitialState(){
	    return new EtatJack(this,20,20);
	} 

     
    public double getReward(IState s1,IState s2,IAction a){
	ActionJack aj=(ActionJack)a;
	EtatJack ej=(EtatJack)s1; 
	double reward=0; 
	reward=10*(req1+req2); 
	reward-=2*Math.abs(aj.getNumber()); 
	return reward; 
    }//getReward
		       

	
	public boolean isFinal(IState s){
	    EtatJack ej=(EtatJack)s; 
	    return ((ej.getPos1()==0)&&(ej.getPos2()==0)); 
	}



    /**   No winner nor looser in this problem */
    public int whoWins(IState s){return 0; }


    // All the following code is adapted/copied from Sutton & Barto 

    double vStar[][]=new double[21][21]; 
    double P1[][]=new double[26][21]; 
    double P2[][]=new double[26][21]; 
    double R1[]=new double[26]; 
    double R2[]=new double[26]; 

    private int fact(int n){
	int s=1; 
	for(int i=1;i<=n;i++) s*=i; 
	return s; 
    }

    private double poisson(int n, double lambda){
	return Math.exp(-lambda)*Math.pow(lambda,(double)n)/(fact(n)+0.0);
    }
	


    

    private void fill(double P[][], double R[],double lreq,double ldrop){
	for(int requests=0;requests<10;requests++){
	    double rp=poisson(requests,lreq); 
	    if(rp<1e-10) break; 
	    for(int n=0;n<26;n++)
		R[n]+=10*rp*Math.min(requests,n); 
	    for(int drop=0;drop<10;drop++){
		double dp=poisson(drop,ldrop); 
		if(dp<1e-10) break; 
		for(int n=0;n<26;n++){
		    int sr=Math.min(requests,n);
		    int new_n=Math.max(0,Math.min(20,drop+n-sr)); 
		    P[n][new_n]+=rp*dp;
		}
	    }
	}
    }//fill

    private void fillAll(){
	fill(P1,R1,lambdareq1,lambdadrop1); 
	fill(P2,R2,lambdareq2,lambdadrop2); 
    }

    private double backupAction(int n1,int n2,int a,double gamma){
	if (a<-n2) a=-n2; 
	if (a>n1) a=n1; 
	if (a<-5) a=-5; 
	if(a>5) a=5; 

	double sum=-2*Math.abs(a); 
	int mn1=n1-a; 
	int mn2=n2+a; 
	for(int new_n1=0; new_n1<21;new_n1++)
	    	for(int new_n2=0; new_n2<21;new_n2++)
		    sum+=P1[mn1][new_n1]*P2[mn2][new_n2]*(R1[mn1]+R2[mn2]+gamma*vStar[new_n1][new_n2]); 
	return sum;
    }

    public void computeVStar(double gamma){
	fillAll(); 
	double delta; 
	int tour=0; 
	System.err.println("Computing vStar...."); 
	do{
	    
	    delta=0.0; 
	    for(int n1=0;n1<21;n1++){
		for(int n2=0;n2<21;n2++){
		    double v=vStar[n1][n2]; 
		    double max=0; 
		    double sum;
		    for(int a=-5;a<6;a++){
			sum=backupAction(n1,n2,a,gamma); 
			if(sum-max>1e-6) max=sum; 
		    }
		
		vStar[n1][n2]=max; 
		delta=Math.max(delta,Math.abs(v-vStar[n1][n2])); 
		}
	    }
	}while(delta>1e-15);
    }// computeVStar

    public void displayVStar(){
	for(int n1=0;n1<21;n1++){
	    for(int n2=0;n2<21;n2++)
		System.out.println(n1+" "+n2+" "+vStar[n1][n2]); 
	    System.out.println(); 
	}
    }

    public double getVStar(IState e){
	EtatJack ej=(EtatJack)e; 
	return vStar[ej.getPos1()][ej.getPos2()]; 
    }

    public void extractPolicy(double gamma){
	for(int n1=0;n1<21;n1++){
	    for(int n2=0;n2<21;n2++){
		double max=-1; 
		int indice=-6; 
		for(int a=-5;a<=5;a++){
		    int av=a; 
		    if (av<-n2) av=-n2; 
		    if (av>n1) av=n1; 
		    if (av<-5) av=-5; 
		    if(av>5) av=5; 
		    double sum=backupAction(n1,n2,av,gamma); 
		    if(sum-max>1e-6){
			max=sum; 
			indice=av; 
		    }
		}
		System.out.println(n1+" "+n2+" "+indice); 
	    }
	    System.out.println(); 
	}

    }// extractPolicy

    
	    
    }
