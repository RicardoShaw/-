/**
 * 
 */
package jmetal.metaheuristics.mofa;

import java.util.Comparator;

import com.Ricardo.core.CoO;

import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.core.SolutionSets;
import jmetal.core.Variable;
import jmetal.operators.mutation.Mutation;
import jmetal.util.Distance;
import jmetal.util.JMException;
import jmetal.util.NonDominatedSolutionList;
import jmetal.util.PseudoRandom;
import jmetal.util.archive.CrowdingArchive;
import jmetal.util.comparators.CrowdingDistanceComparator;
import jmetal.util.comparators.DominanceComparator;
import jmetal.util.comparators.EpsilonDominanceComparator;

/**
 * @author X1A0CH1
 * 这个是没有加入全局指导的MOFA算法
 */
public class OPMOFA extends Algorithm {
	/**
	 * @param problem
	 */

	/*
	 * 萤火虫群体的个数
	 */
	private int fireflysSize_;
	/*
	 * 外部档案集的大小
	 */
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
	 * 非线性变异的概率
	 */
	private double perturbatiojn_;
	/*
	 * 萤火虫种群集
	 */
	private SolutionSet fireflys_;
	/*
	 * 萤火虫历史最优解的Solution数组
	 */
	private Solution[] best_;
	/*
	 * 萤火虫拥挤距离的档案集合
	 */
	private CrowdingArchive leaders_;
	/*
	 * 非支配解集
	 */
	private NonDominatedSolutionList eArchive_;
	/*
	 * 储存萤火虫个体的移动
	 */
	private double[][] move_;
	/*
	 * 支配对比器
	 */
	private Comparator dominance_;
	/*
	 * 拥挤距离对比器
	 */
	private Comparator crowdingDistanceComparator_;
	/*
	 * 存储一个Distance对象 
	 */
	private Distance distance_;
	/*
	 * 线性变异操作器
	 */
	private Operator uniformMutation_;
	/*
	 * 非线性变异操作器
	 */
	private Operator nonUniformMutation_;
	
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
	public OPMOFA(Problem problem) {
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
//		alpha_                       = ((Double)getInputParameter("alpha")).doubleValue();
		beta_                        = ((Double)getInputParameter("beta")).doubleValue();
		gammar_                      = ((Double)getInputParameter("gammar")).doubleValue();
		
		
		fireflys_               	 = new SolutionSet(fireflysSize_);
		best_                		 = new Solution[fireflysSize_];
		leaders_              		 = new CrowdingArchive(archiveSize_,problem_.getNumberOfObjectives());
		eArchive_              		 = new NonDominatedSolutionList(new EpsilonDominanceComparator(eta_));
		
		uniformMutation_       		 = (Mutation)operators_.get("uniformMutation") ;
		nonUniformMutation_          = (Mutation)operators_.get("nonUniformMutation") ;
		
		coo                          =new CoO(problem_,fireflysSize_);
		
		
		// Create the dominator for equadless and dominance
		dominance_                   = new DominanceComparator();    
		crowdingDistanceComparator_  = new CrowdingDistanceComparator();
		distance_                    = new Distance();
		
		move_                        = new double[fireflysSize_][problem_.getNumberOfVariables()];
	}//initParams
	
	/*
	 * 更新每个萤火虫的移动位置
	 */
	private void computeMove(int fireflyIndex) throws JMException{
		double r1,W,C1;
	    double diff1,diff2;    //Auxiliar var
	    double distance1 = 0.0;
//	    double distance2 = 0.0;
	    //-> Calculate the euclidean distance
	    // for   
		Variable[] bestGlobal;
		
		Variable[] firefly    = fireflys_.get(fireflyIndex).getDecisionVariables();
//		Variable[] bestFirefly = best_[i].getDecisionVariables();
			
		//从fireflyi与fireflyj中选择一个全局最优的萤火虫
		Solution one , two ;
		int pos1 = PseudoRandom.randInt(0, leaders_.size()-1);
		int pos2 = PseudoRandom.randInt(0,leaders_.size()-1);
		one = leaders_.get(pos1);
		two = leaders_.get(pos2);
		
		if(crowdingDistanceComparator_.compare(one, two)<1)
			bestGlobal = one.getDecisionVariables();
		else
			bestGlobal = two.getDecisionVariables();
			//
	    r1 = PseudoRandom.randDouble();
//		    r2 = PseudoRandom.randDouble();
		C1 = PseudoRandom.randDouble(1.5,2.0);
//		    C2 = PseudoRandom.randDouble(1.5,2.0);
		W  = PseudoRandom.randDouble(0.1,0.5); 
		    
		    
		for(int var = 0 ;var<firefly.length;var++){
		    diff1 = bestGlobal[var].getValue()-firefly[var].getValue();
		    distance1+=Math.pow(diff1, 2);
//		    diff2 = bestFirefly[var].getValue()-firefly[var].getValue();
//		    distance2+=Math.pow(diff2, 2);
		}

		for(int var=0; var<firefly.length;var++){
		    	move_[fireflyIndex][var] =  W  * move_[fireflyIndex][var] +
		                   			r1*C1*beta_*Math.exp(-gammar_*distance1)*
		                   				(bestGlobal[var].getValue() - firefly[var].getValue())
		                   				;
		    }
			
		
	}//computeMove()
	/*
	 * computeMove()定义了萤火虫个体向群体最优解以及萤火虫个体的历史最优解学习
	 * 下面的computeMove(int indexI,int indexJ)是萤火虫个体向传入的
	 */
	private void computeMove(int i,int j) throws JMException{
		double diff;
	    double distance = 0.0;
	    double diff1;
	    double distance1 = 0.0;
	    Variable[] bestGlobal;
		Variable[] fireflyI = fireflys_.get(i).getDecisionVariables();
		Variable[] fireflyJ = fireflys_.get(j).getDecisionVariables();
		
		Solution one , two ;
		int pos1 = PseudoRandom.randInt(0, leaders_.size()-1);
		int pos2 = PseudoRandom.randInt(0,leaders_.size()-1);
		one = leaders_.get(pos1);
		two = leaders_.get(pos2);
		
		if(crowdingDistanceComparator_.compare(one, two)<1)
			bestGlobal = one.getDecisionVariables();
		else
			bestGlobal = two.getDecisionVariables();
		
		 double r1 = PseudoRandom.randDouble();
		 double C1 = PseudoRandom.randDouble(1.0,1.5);
		double r=PseudoRandom.randDouble();
		 

		for(int var = 0 ;var < fireflyJ.length;var++){
		    	diff = fireflyI[var].getValue()-fireflyJ[var].getValue();
		    	distance+=Math.pow(diff, 2);
			    diff1 = bestGlobal[var].getValue()-fireflyJ[var].getValue();
			    distance1+=Math.pow(diff1, 2);
		}

		for(int var=0; var<fireflyI.length;var++){
		    move_[i][var] =   move_[i][var] +
		    				r*beta_*Math.exp(-gammar_*distance)*
		                   (fireflyI[var].getValue() - 
		                              fireflyJ[var].getValue())
		                              +
		                              (1-r)*beta_*Math.exp(-gammar_*distance1)*
		                   				(bestGlobal[var].getValue() - fireflyJ[var].getValue())
		                   				 ;
		}
	}
	/*
	 * 计算当前萤火虫个体的位置
	 */
	private void computeNewPositions() throws JMException{
		for(int i = 0; i < fireflysSize_; i++ ){
			Variable[] firefly=fireflys_.get(i).getDecisionVariables();
			for(int var = 0; var < firefly.length; var++){
				firefly[var].setValue(firefly[var].getValue()+move_[i][var]);
				if(firefly[var].getValue() < problem_.getLowerLimit(var)){
					firefly[var].setValue(problem_.getLowerLimit(var));
					move_[i][var] = move_[i][var] * -1.0;
				}
				if(firefly[var].getValue() > problem_.getUpperLimit(var)){
					firefly[var].setValue(problem_.getUpperLimit(var));
					move_[i][var] = move_[i][var] * -1.0;
				}
				
			}
		}
	}
	/*
	 * 分成了三组，第一组就非线性变异，第二组是线性变异，第三组不进行变异
	 * mofaMutation（）就是进行变异操作
	 */
	private void mofaMutation(int actualIteration , int totalIterations) throws JMException{
		nonUniformMutation_.setParameter("currentIteration",actualIteration);
	    //*/

	    for (int i = 0; i < fireflys_.size();i++)            
	      if (i % 3 == 0) { //particles_ mutated with a non-uniform mutation
	        nonUniformMutation_.execute(fireflys_.get(i));                                
	      } else if (i % 3 == 1) { //particles_ mutated with a uniform mutation operator
	        uniformMutation_.execute(fireflys_.get(i));                
	      } else //particles_ without mutation
	          ;      
	}


	
	/* (non-Javadoc)
	 * @see jmetal.core.Algorithm#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SolutionSet execute() throws JMException, ClassNotFoundException {
		//初始化所有的参数
		initParams();
		coo.OrthogonalArray();
		orthogonal=coo.newArray();
		
		//第一步初始化萤火虫群体并计算萤火虫个体的目标值
		for(int i = 0; i < fireflysSize_ ; i++){
			Solution firefly=new Solution(problem_);	
			for(int n=0;n<problem_.getNumberOfVariables();n++){
				firefly.getDecisionVariables()[n].setValue(orthogonal[n][i]);
			}
			problem_.evaluate(firefly);
			problem_.evaluateConstraints(firefly);
			fireflys_.add(firefly);
		}
		
		//初始化萤火虫个体的移动
		for( int i = 0 ; i < fireflysSize_;i++){
			for(int j = 0 ; j < problem_.getNumberOfVariables(); j++){
				move_[i][j] = 0.0;
			}
		}
		
		for(int i = 0;i < fireflys_.size();i++){
			Solution firefly=new Solution(fireflys_.get(i));
			if(leaders_.add(firefly)){
				eArchive_.add(new Solution(firefly));
			}
		}
		
//		for(int i = 0 ;i < fireflys_.size() ; i++){
//			Solution firefly = new Solution(fireflys_.get(i));
//			best_[i]=firefly;
//		}
		
		distance_.crowdingDistanceAssignment(leaders_, 
				problem_.getNumberOfObjectives());
		
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

			
			mofaMutation(iteration_,maxIterations_);
			
			
			for(int i = 0 ; i < fireflys_.size() ; i++){
				Solution firefly=fireflys_.get(i);
				problem_.evaluate(firefly);
				problem_.evaluateConstraints(firefly);
			}
			
			for(int i = 0 ; i < fireflys_.size(); i++){
				Solution firefly=new Solution(fireflys_.get(i));
				if(leaders_.add(firefly)){
					eArchive_.add(firefly);
				}
			}

			distance_.crowdingDistanceAssignment(leaders_, 
					problem_.getNumberOfObjectives());
			iteration_++;
		}
		return leaders_;
	}


}
