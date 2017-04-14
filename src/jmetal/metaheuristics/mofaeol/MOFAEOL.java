/**    
  
* @Title: MOFAEOL.java  
  
* @Package jmetal.metaheuristics.mofaeol  
  
* @Description: TODO(用一句话描述该文件做什么)  
  
* @author Ricardo Shaw  

* @Email  ricardo_shaw@outlook.com    
  
* @date 2016年9月23日 下午6:45:37  
  
* @version V1.0    
  
*/ 
package jmetal.metaheuristics.mofaeol;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import jmetal.core.Algorithm;
import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.core.SolutionSets;
import jmetal.core.Variable;
import jmetal.util.Distance;
import jmetal.util.JMException;
import jmetal.util.NonDominatedSolutionList;
import jmetal.util.archive.CrowdingArchive;
import jmetal.util.comparators.DistanceToPopulationComparator;
import jmetal.util.comparators.DominanceComparator;
import jmetal.util.comparators.FitnessComparator;

/**  
  
 * @ClassName: MOFAEOL  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author Ricardo Shaw  
 * @email  ricardo_shaw@outlook.com
 * @date 2016年9月23日 下午6:45:37  
  
 *  
    
  
 */
public class MOFAEOL extends Algorithm {
	
	//种群规模
	private int populationSize_;
	
	//档案规模
	private int archiveSize_;
	
	//最大迭代次数
	private int maxIterations_;
	
	//当前迭代次数
	private int iterations_;

	//最大评估次数
	private int maxEvaluations_;
	
	//当前评估次数
	private int evaluations_;
	
	//当前种群
	private SolutionSet populations_;
	
	//外部档案
	private SolutionSet eArchive_;
	
	//拥挤距离档案
	private SolutionSet crowdingArchives_;
	
	//用于测试igd值
	private SolutionSets eArchives_;
	
	//初始半径
	private double r0_ ;
	
	//末代半径
	private double rend_ ;
	
	//爆炸半径指数
	private int alpha_ ;
	
	//一般化系数
	private double beta_ ;

	//适应度值Comparator
	private Comparator fitComparator_;
	
	//支配Comparator
	private Comparator domComparator_;
	
	//拥挤距离对比器
	private Comparator crowdingDistanceComparator_;
	
	//存储一个Distance对象
	private Distance distance_;
	
	/**  
	  
	* @Title :  构造函数
	  
	* @Description :  
	  
	* @param problem  
	  
	*/ 
	public MOFAEOL(Problem problem) {
		super(problem);
		// TODO Auto-generated constructor stub
	}
	
	
	
	/**
	 * 初始化所有参数
	 */
	public void initParams(){
		populationSize_      			= 100 ;
		
		archiveSize_           	 	 	= 100 ;
		
		maxIterations_          		= 100 ;
		
		iterations_ 					= 0 ;
		
		maxEvaluations_					= 50000 ;
		
		evaluations_					= 0 ;
		
		fitComparator_					= new FitnessComparator();
		
		domComparator_					= new DominanceComparator();
		
		populations_ 					= new SolutionSet(populationSize_);

		eArchive_ 						= new NonDominatedSolutionList(archiveSize_,domComparator_);
		
		crowdingArchives_				= new CrowdingArchive(archiveSize_,problem_.getNumberOfObjectives());
		
		distance_                   	= new Distance();
		
		r0_ 							= 0.5;
		
		rend_ 							= 0.000001;
		
		alpha_                      	= 5 ;
		
		beta_                        	= 1.0;
	}
	
	
	
	
	
	
	/* (非 Javadoc)  
	  
	 * <p>Title: execute</p>  
	  
	 * <p>Description: </p>  
	  
	 * @return
	 * @throws JMException
	 * @throws ClassNotFoundException  
	  
	 * @see jmetal.core.Algorithm#execute()  
	  
	 */
	@Override
	public SolutionSet execute() throws JMException, ClassNotFoundException {
		// TODO Auto-generated method stub
		//初始化参数
		initParams();
		
		//创建初始种群和计算Objective的值
		for(int i = 0 ; i < populationSize_ ; i++){
			Solution aspark = new Solution(problem_);
			problem_.evaluate(aspark);
			problem_.evaluateConstraints(aspark);
			populations_.add(aspark);
			if(eArchive_.add(aspark))
				crowdingArchives_.add(aspark);
		}
		
//		//CrowdingArchives的更新维护
//		distance_.crowdingDistanceAssignment(crowdingArchives_,problem_.getNumberOfObjectives());     
		
		
		//大循环开始
		while( evaluations_ < maxEvaluations_){
			SolutionSet unionset = new SolutionSet();
			
			//返回产生的火花种群
			SolutionSet sparkset = sparkProduct(populations_, evaluations_);
			
			//返回产生的精英反向学习的集合
			SolutionSet OBLset   = OBL(eArchive_,beta_);
			
			//将populations_、sparkset和OBLset合并在一起
			int capacity = populations_.size() + sparkset.size() + OBLset.size();
			unionset.setCapacity(capacity);
			
			System.out.println("capacity:"+capacity);
			unionset.setCapacity(capacity);
			for(int i = 0 ; i < populations_.size();i++)
				unionset.add(populations_.get(i));
			for(int i = 0 ; i < sparkset.size() ; i++)
				unionset.add(sparkset.get(i));
			for(int i = 0 ; i < OBLset.size() ; i++)
				unionset.add(OBLset.get(i));
			
			
			//更新unionset的信息
			for(int i = 0 ; i < unionset.size() ; i++){
				Solution aspark = unionset.get(i);
				problem_.evaluate(aspark);
				problem_.evaluateConstraints(aspark);
			}
			System.out.println("unionset.size() = " + unionset.size());
			//将优秀个体加入到非支配解集以及拥挤距离档案中
			for(int i = 0 ; i < unionset.size() ; i++){
				Solution aspark = new Solution(unionset.get(i));
				
				if(crowdingArchives_.add(aspark))
					eArchive_.add(aspark);
				
				evaluations_++ ;
				
				if(evaluations_ == maxEvaluations_)
					break;
				
				System.out.println("evaluations_:"+evaluations_);
			}
			
			//更新populations_种群，个体从原种群和产生的火花集合里面选
			populations_ = selectSpark(populations_,sparkset);
//			iterations_ ++;
//			System.out.println("iterations_:"+iterations_);
			distance_.crowdingDistanceAssignment(crowdingArchives_,problem_.getNumberOfObjectives());
		}
		return crowdingArchives_;
	}


	
	
	
	/**
	 * private function
	 * 烟花爆炸操作
	 */
	
	//半径公式
	public double radius(int evaluations_){
		double r = Math.pow((maxEvaluations_ - evaluations_) / maxEvaluations_, alpha_) * (r0_ - rend_) + rend_;
		return r;
	}
	
	/**
	 * 
	  
	* @Title: sparkProduct  
	  
	* @Description: 烟花爆炸(这里用一句话描述这个方法的作用)  
	  
	* @param @param populations
	* @param @param iterations
	* @param @return
	* @param @throws JMException    设定文件  
	  
	* @return SolutionSet    返回类型  
	  
	* @throws
	 */
	public SolutionSet sparkProduct(SolutionSet populations,int iterations) throws JMException{
		Random random = new Random();
		SolutionSet solutionset = new SolutionSet();
		solutionset.setCapacity(10000);
		for(int i = 0 ; i < populations.size() ; i++){
			for(int j = 0 ; j < (problem_.getNumberOfVariables()/3);j++){
				int index = random.nextInt(problem_.getNumberOfVariables());
				Solution solution1 = new Solution(populations.get(i));
				Solution solution2 = new Solution(populations.get(i));
				for(int k =1 ; k < 5 ; k++){
					Variable[] getVariable_ = solution1.getDecisionVariables();
					double value1 = getVariable_[index].getValue() + (k/4) * radius(iterations) ;
					double value2 = getVariable_[index].getValue() - (k/4) * radius(iterations) ;
					if( value1 >= 0  && value1 <= 1){
						getVariable_[index].setValue(value1);
						solution2.setDecisionVariables(getVariable_);
						solutionset.add(new Solution(solution2));
					}

					if(value2 >= 0 && value2 <= 1){
						getVariable_[index].setValue(value2);
						solution2.setDecisionVariables(getVariable_);
						solutionset.add(new Solution(solution2));
					}

				}
			}
		}
		return solutionset;
	}
	
	/**
	 * 精英反向学习
	 * @throws JMException 
	 */
	public SolutionSet OBL(SolutionSet solutionset , double beta) throws JMException{
		for(int i = 0 ; i < solutionset.size() ; i++){
			for(int j = 0 ; j < problem_.getNumberOfVariables(); j++){
				Variable[] variable_ = solutionset.get(i).getDecisionVariables();
				double value = beta * (problem_.getUpperLimit(j) + problem_.getLowerLimit(j))
								- variable_[j].getValue();
				variable_[j].setValue(value);
			}
		}
		SolutionSet newSolutionset = new SolutionSet(solutionset);
		return newSolutionset;
	}
	
	/**
	 * 选择炸点
	 * @throws JMException 
	 */
	public SolutionSet selectSpark(SolutionSet solutionset,SolutionSet solutionset1) throws JMException{
		SolutionSet newsolutionset = new SolutionSet(solutionset.size() + solutionset1.size());

		for(int i = 0 ; i < solutionset.size() ; i++)
			newsolutionset.add(solutionset.get(i));
		for(int i = 0 ; i< solutionset1.size() ; i++)
			newsolutionset.add(solutionset1.get(i));

		double sum = 0 ;
		for(int i = 0 ; i < newsolutionset.size() ; i++){
			double sum_i  = 0;
			for(int j = 0 ; j < newsolutionset.size() ;  j++){
				sum_i += distance_.distanceBetweenSolutions(newsolutionset.get(i), newsolutionset.get(j));
			}
			newsolutionset.get(i).setDistanceToSolutionSet(sum_i);
		}
		newsolutionset.sort(new DistanceToPopulationComparator());
		SolutionSet returnsolutionset = new SolutionSet(populationSize_);
		
		for(int i = 0 ; i < populationSize_ ; i++)
			returnsolutionset.add(new Solution(newsolutionset.get(i)));
		
		return returnsolutionset;
	}
//	/* (非 Javadoc)  
//	  
//	 * <p>Title: executes</p>  
//	  
//	 * <p>Description: </p>  
//	  
//	 * @return
//	 * @throws JMException
//	 * @throws ClassNotFoundException  
//	  
//	 * @see jmetal.core.Algorithm#executes()  
//	  
//	 */
//	@Override
//	public SolutionSets executes() throws JMException, ClassNotFoundException {
//		// TODO Auto-generated method stub
//		//初始化参数
//		initParams();
//		
//		//创建初始种群和计算Objective的值
//		for(int i = 0 ; i < populationSize_ ; i++){
//			Solution aspark = new Solution(problem_);
//			problem_.evaluate(aspark);
//			problem_.evaluateConstraints(aspark);
//			populations_.add(aspark);
//		}
//		
////		//CrowdingArchives的更新维护
////		distance_.crowdingDistanceAssignment(crowdingArchives_,problem_.getNumberOfObjectives());     
//		
//		
//		//大循环开始
//		while(iterations_ < maxIterations_){
//			
//	    	if(iterations_ % 10 == 0 && iterations_!=0 || iterations_ == (maxIterations_-1) ){
//	    		eArchives_.add(new SolutionSet(crowdingArchives_));
//	    	}
//
//			SolutionSet unionset = new SolutionSet();
//			
//			//返回产生的火花种群
//			SolutionSet sparkset = sparkProduct(populations_, iterations_);
//			
//			//返回产生的精英反向学习的集合
//			SolutionSet OBLset   = OBL(eArchive_,beta_);
//			
//			//将populations_、sparkset和OBLset合并在一起
//			int capacity = populations_.size() + sparkset.size() + OBLset.size();
//			System.out.println("capacity:"+capacity);
//			unionset.setCapacity(capacity);
//			for(int i = 0 ; i < populations_.size();i++)
//				unionset.add(populations_.get(i));
//			for(int i = 0 ; i < sparkset.size() ; i++)
//				unionset.add(sparkset.get(i));
//			for(int i = 0 ; i < OBLset.size() ; i++)
//				unionset.add(OBLset.get(i));
//			
//			//更新unionset的信息
//			for(int i = 0 ; i < unionset.size() ; i++){
//				Solution aspark = unionset.get(i);
//				problem_.evaluate(aspark);
//				problem_.evaluateConstraints(aspark);
//			}
//			
//			//将优秀个体加入到非支配解集以及拥挤距离档案中
//			for(int i = 0 ; i < unionset.size() ; i++){
//				Solution aspark = new Solution(unionset.get(i));
//				if(eArchive_.add(aspark))
//					crowdingArchives_.add(aspark);
//			}
//			
//			//更新populations_种群，个体从原种群和产生的火花集合里面选
//			populations_ = selectSpark(populations_,sparkset);
//			iterations_ ++;
//			distance_.crowdingDistanceAssignment(crowdingArchives_,problem_.getNumberOfObjectives());
//		}
//		return eArchives_;
//	}

}
