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
 * �����û�м���ȫ��ָ����MOFA�㷨
 */
public class OPMOFA extends Algorithm {
	/**
	 * @param problem
	 */

	/*
	 * ө���Ⱥ��ĸ���
	 */
	private int fireflysSize_;
	/*
	 * �ⲿ�������Ĵ�С
	 */
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
	 * �����Ա���ĸ���
	 */
	private double perturbatiojn_;
	/*
	 * ө�����Ⱥ��
	 */
	private SolutionSet fireflys_;
	/*
	 * ө�����ʷ���Ž��Solution����
	 */
	private Solution[] best_;
	/*
	 * ө���ӵ������ĵ�������
	 */
	private CrowdingArchive leaders_;
	/*
	 * ��֧��⼯
	 */
	private NonDominatedSolutionList eArchive_;
	/*
	 * ����ө��������ƶ�
	 */
	private double[][] move_;
	/*
	 * ֧��Ա���
	 */
	private Comparator dominance_;
	/*
	 * ӵ������Ա���
	 */
	private Comparator crowdingDistanceComparator_;
	/*
	 * �洢һ��Distance���� 
	 */
	private Distance distance_;
	/*
	 * ���Ա��������
	 */
	private Operator uniformMutation_;
	/*
	 * �����Ա��������
	 */
	private Operator nonUniformMutation_;
	
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
	public OPMOFA(Problem problem) {
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
	 * ����ÿ��ө�����ƶ�λ��
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
			
		//��fireflyi��fireflyj��ѡ��һ��ȫ�����ŵ�ө���
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
	 * computeMove()������ө��������Ⱥ�����Ž��Լ�ө���������ʷ���Ž�ѧϰ
	 * �����computeMove(int indexI,int indexJ)��ө�����������
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
	 * ���㵱ǰө�������λ��
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
	 * �ֳ������飬��һ��ͷ����Ա��죬�ڶ��������Ա��죬�����鲻���б���
	 * mofaMutation�������ǽ��б������
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
		//��ʼ�����еĲ���
		initParams();
		coo.OrthogonalArray();
		orthogonal=coo.newArray();
		
		//��һ����ʼ��ө���Ⱥ�岢����ө�������Ŀ��ֵ
		for(int i = 0; i < fireflysSize_ ; i++){
			Solution firefly=new Solution(problem_);	
			for(int n=0;n<problem_.getNumberOfVariables();n++){
				firefly.getDecisionVariables()[n].setValue(orthogonal[n][i]);
			}
			problem_.evaluate(firefly);
			problem_.evaluateConstraints(firefly);
			fireflys_.add(firefly);
		}
		
		//��ʼ��ө��������ƶ�
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
