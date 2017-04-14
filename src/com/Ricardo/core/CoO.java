/**
 * 
 */
package com.Ricardo.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jmetal.core.Problem;


/**
 * @author X1A0CH1
 * 正交数组的初始化
 */
public class CoO {
	/*
	 * 定义传入的数据是N,M,以及Problem.getUpperLimit(),以及Problem.getLowerLimit()
	 * 定义NumOfVariables为N
	 * Q就是在给定范围内的均匀分布，也就是将范围多少等分,实验者默认为11个水平和7个水平的
	 * M就是正交分布的实验次数下面是M的定义
	 * M=Math.pow(Q,J)，然后J的定义为  N<=(Math.pow(Q,J)-1)/(Q-1)
	 */
	private int J;
	private int j;
	private int Q;
	private int M;
	private int N;
	private int fireflysSize_;
	private int numOfVariables;
	private Problem problem;
	private List<double[][]> orthogonal;
	double[][] a;
	public CoO(int numOfVariables){
		this.numOfVariables=numOfVariables;
		orthogonal=new ArrayList<double[][]>();
		orthogonal = InitialList(numOfVariables);
		this.J=(int) Math.ceil(Math.log10(N*(Q-1)+1)/Math.log10(Q));
		this.M=(int) Math.pow(Q, J);
	}
	public CoO(Problem problem,int fireflysSize_){
		this.fireflysSize_=fireflysSize_;
		this.problem=problem;
		this.numOfVariables=problem.getNumberOfVariables();
		orthogonal=new ArrayList<double[][]>();
		orthogonal = InitialList(numOfVariables);
		this.J=(int) Math.ceil(Math.log10(N*(Q-1)+1)/Math.log10(Q));
		this.M=(int) Math.pow(Q, J);
	}
	public List<double[][]> OrthogonalArray(){
		for(int k = 1 ; k <= J  ; k++){
			j=(int) ((Math.pow(Q, k-1)-1)/(Q-1)+1);
			for(int n=0;n<orthogonal.get(j-1).length;n++){
				for(int i = 0 ; i< M ; i++){
					orthogonal.get(j-1)[n][i]=Math.floor((i)/Math.pow(Q,J-k)) % Q;
				}
			}
		}

		for(int k = 2 ; k <= J ; k++){
			j=(int) ((Math.pow(Q, k-1)-1)/(Q-1)+1);
			for(int s = 1; s <= (j-1) ;s++){
				for(int t = 1; t <= (Q-1) ;t++){
					for(int n=0;n<orthogonal.get(j-1+(s-1)*(Q-1)+t).length;n++){
						for(int i = 0 ; i< M ; i++){
							orthogonal.get(j-1+(s-1)*(Q-1)+t)[n][i]=(orthogonal.get(s-1)[0][i]*t+orthogonal.get(j-1)[0][i])% Q;
						}
					}
//					for(int i=1;i<M+1;i++){
//						a[i][j+(s-1)*(Q-1)+t]=(a[i][1]*t+a[i][2]) % Q;
//					}
				}
			}
		}
		return orthogonal;
	}
	public List<double[][]> InitialList(int numOfVariables){
		int total=0;
		if(numOfVariables==30){
			this.Q=11;
			this.N=12;
			for(int i = 0 ; i < this.N;i++){
				int j=(int) Math.floor(Math.random()*(30-(11-i)-total))+1;
				total+=j;
				double[][] a=new double[j][121];
				orthogonal.add(a);
			}
		}else if(numOfVariables==12){
			this.Q=11;
			this.N=12;
			for(int i = 0 ; i < this.N;i++){
				double[][] a=new double[1][121];
				orthogonal.add(a);
			}
		}else if(numOfVariables==22){
			this.Q=11;
			this.N=12;
			for(int i = 0 ; i < this.N;i++){
				int j=(int) Math.floor(Math.random()*(22-(11-i)-total))+1;
				total+=j;
				double[][] a=new double[j][121];
				orthogonal.add(a);
			}
		}
		else if(numOfVariables==10||numOfVariables==11){
			this.Q=11;
			this.N=12;
			this.numOfVariables=12;
			for(int i = 0 ; i < this.N;i++){
				double[][] a=new double[1][121];
				orthogonal.add(a);
			}
		}else if(numOfVariables == 7){
			this.Q=7;
			this.N=8;
			this.numOfVariables=8;
			for(int i = 0 ; i < this.N ; i++){
				double[][] a = new double[1][49];
				orthogonal.add(a);
			}
		}
		else{
			this.Q=3;
			this.N=4;
		}
		return orthogonal;
	}

	
	public double[][] newArray(){

		double[][] newArray=new double[numOfVariables][M];
		Iterator<double[][]> iter=orthogonal.iterator();
		int k = 0 ;
		while(iter.hasNext()){
			double[][] a=iter.next();
			for(int n=0;n<a.length;n++){
				for(int m=0;m<a[n].length;m++){
					newArray[k][m]=problem.getLowerLimit(n)+
							(problem.getUpperLimit(n)-problem.getLowerLimit(n))
							/(Q-1)
							*a[n][m];
				}
				k++;
			}
		}
		if(M<100 && fireflysSize_==200){
			double[][] newArray1=new double[numOfVariables][M*5];
			for(int i = 0 ; i <numOfVariables;i++){
				for(int j = 0; j <(M*3);j++){
					newArray1[i][j]=newArray[i][(j%49)];
				}
			}
			return newArray1;
		}else if(M<200 && fireflysSize_==200){
			double[][] newArray1=new double[numOfVariables][M*2];
			for(int i = 0 ; i <numOfVariables;i++){
				for(int j = 0; j <(M*2);j++){
					newArray1[i][j]=newArray[i][(j%121)];
				}
			}
			return newArray1;
		}
		else
		{
			return newArray;
		}
	}
}
