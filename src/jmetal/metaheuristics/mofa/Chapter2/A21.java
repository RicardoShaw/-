package jmetal.metaheuristics.mofa.Chapter2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.Ricardo.core.CoO;

import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.core.Variable;
import jmetal.operators.mutation.Mutation;
import jmetal.util.Distance;
import jmetal.util.JMException;
import jmetal.util.NonDominatedSolutionList;
import jmetal.util.PseudoRandom;
import jmetal.util.archive.AdaptiveGridArchive;
import jmetal.util.archive.CrowdingArchive;
import jmetal.util.comparators.CrowdingDistanceComparator;
import jmetal.util.comparators.DominanceComparator;
import jmetal.util.comparators.EpsilonDominanceComparator;

/**
 * @author Ricardo Shaw
 * @descript 1.�����ʼ�� 2.levy flight3.����Ӧ����   The idea  from Yang
 * @version �㷨A21
 *
 */
public class A21 extends Algorithm {

	//ө������
	private int fireflysSize_;
	
	//�ⲿ��������С
	private int archiveSize_;
	/*
	 * ����������
	 */
	private int maxIterations_;
	/*
	 * ��ǰ�ĵ�������
	 */
	private int iteration_;
	
	/*
	 * ө�����Ⱥ��
	 */
	private SolutionSet fireflys_;

	private AdaptiveGridArchive archives_;

	/*
	 * ֧��Ա���
	 */
	private Comparator dominance_;


	//�洢һ��Distance���� 
	private Distance distance_;

	
	
	//�������鹹����
	private CoO coo;
	
	//��������
	private double[][] orthogonal;
	
	
	/*
	 * alpha_
	 * beta_
	 * gammar_
	 * 
	 */
	private double alpha_;
	private double beta_;
	private double gammar_;
	private double eta_=0.0075;
	

	
	
	/**
	 * @param problem
	 */
	public A21(Problem problem) {
		super(problem);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * ��ʼ�����еĲ���
	 */
	public void initParams(){
		fireflysSize_            	 = ((Integer)getInputParameter("fireflysSize")).intValue();
		
		archiveSize_           	 	 = ((Integer)getInputParameter("archiveSize")).intValue();
		
		maxIterations_          	 = ((Integer)getInputParameter("maxIterations")).intValue();
		
		alpha_                       = ((Double)getInputParameter("alpha")).doubleValue();
		
		beta_                        = ((Double)getInputParameter("beta")).doubleValue();
		
		gammar_                      = ((Double)getInputParameter("gammar")).doubleValue();
			
		fireflys_               	 = new SolutionSet(fireflysSize_);
	
		coo                          =new CoO(problem_,fireflysSize_);
			
		dominance_                   = new DominanceComparator();
		

		
		distance_                    = new Distance();
		
	}//initParams
	
	//����ÿ��ө�����ƶ�λ��
	 
	private void computeMove(int fireflyIndex) throws JMException{

			
		
	}//computeMove()
	
	/*
	 * computeMove()������ө��������Ⱥ�����Ž��Լ�ө���������ʷ���Ž�ѧϰ
	 * �����computeMove(int indexI,int indexJ)��ө�����������
	 */
	private void computeMove(int i,int j) throws JMException{


		
	}
	/*
	 * ���㵱ǰө�������λ��
	 */
	private void computeNewPositions() throws JMException{

	}


	
	/* (non-Javadoc)
	 * @see jmetal.core.Algorithm#execute()
	 */
	@Override
	public SolutionSet execute() throws JMException, ClassNotFoundException {
		//��ʼ�����еĲ���
		initParams();

		
		//��һ����ʼ��ө���Ⱥ�岢����ө�������Ŀ��ֵ
		for(int i = 0; i < fireflysSize_ ; i++){
			Solution firefly=new Solution(problem_);	
			problem_.evaluate(firefly);
			problem_.evaluateConstraints(firefly);
			fireflys_.add(firefly);
		}
		
		
		for(int i = 0;i < fireflys_.size();i++){
			Solution firefly=new Solution(fireflys_.get(i));

		}
		
		

		
		while(iteration_ < maxIterations_){
			if(problem_.getName().equals("ZDT3")){
			for(int i = 0 ;i < fireflysSize_ ; i++){
				for(int j = 0; j < fireflysSize_ ; j++){
					
						if(dominance_.compare(fireflys_.get(i), fireflys_.get(j))==1){
							computeMove(j,i);
						}
						if(dominance_.compare(fireflys_.get(i), fireflys_.get(j))==-1){
							computeMove(i,j);
						}
						else{
							computeMove(i);
							computeMove(j);
						}
					
				}
			}
			}else{
				for(int i = 0 ;i < fireflysSize_ ; i++){
					for(int j = 0; j < fireflysSize_ ; j++){
						if(i!=j){
							if(dominance_.compare(fireflys_.get(i), fireflys_.get(j))==1){
								computeMove(j,i);
							}
							if(dominance_.compare(fireflys_.get(i), fireflys_.get(j))==-1){
								computeMove(i,j);
							}
							else{
								computeMove(i);
								computeMove(j);
							}
						}
					}
			}
			}
			computeNewPositions();
			
			
			for(int i = 0 ; i < fireflys_.size() ; i++){
				Solution firefly=fireflys_.get(i);
				problem_.evaluate(firefly);
				problem_.evaluateConstraints(firefly);
			}
			
			for(int i = 0 ; i < fireflys_.size(); i++){
				Solution firefly=new Solution(fireflys_.get(i));

			}

			iteration_++;

		}
		return archives_;
	}
	
	

}
