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
 * @descript 1.随机初始化 2.levy flight3.自适应网格   The idea  from Yang
 * @version 算法A21
 *
 */
public class A21 extends Algorithm {

	//萤火虫个数
	private int fireflysSize_;
	
	//外部档案集大小
	private int archiveSize_;
	/*
	 * 最大迭代次数
	 */
	private int maxIterations_;
	/*
	 * 当前的迭代次数
	 */
	private int iteration_;
	
	/*
	 * 萤火虫种群集
	 */
	private SolutionSet fireflys_;

	private AdaptiveGridArchive archives_;

	/*
	 * 支配对比器
	 */
	private Comparator dominance_;


	//存储一个Distance对象 
	private Distance distance_;

	
	
	//正交数组构造器
	private CoO coo;
	
	//正交数组
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
	 * 初始化所有的参数
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
	
	//更新每个萤火虫的移动位置
	 
	private void computeMove(int fireflyIndex) throws JMException{

			
		
	}//computeMove()
	
	/*
	 * computeMove()定义了萤火虫个体向群体最优解以及萤火虫个体的历史最优解学习
	 * 下面的computeMove(int indexI,int indexJ)是萤火虫个体向传入的
	 */
	private void computeMove(int i,int j) throws JMException{


		
	}
	/*
	 * 计算当前萤火虫个体的位置
	 */
	private void computeNewPositions() throws JMException{

	}


	
	/* (non-Javadoc)
	 * @see jmetal.core.Algorithm#execute()
	 */
	@Override
	public SolutionSet execute() throws JMException, ClassNotFoundException {
		//初始化所有的参数
		initParams();

		
		//第一步初始化萤火虫群体并计算萤火虫个体的目标值
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
