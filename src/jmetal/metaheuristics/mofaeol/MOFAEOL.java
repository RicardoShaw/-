/**    
  
* @Title: MOFAEOL.java  
  
* @Package jmetal.metaheuristics.mofaeol  
  
* @Description: TODO(��һ�仰�������ļ���ʲô)  
  
* @author Ricardo Shaw  

* @Email  ricardo_shaw@outlook.com    
  
* @date 2016��9��23�� ����6:45:37  
  
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
 * @Description: TODO(������һ�仰��������������)  
 * @author Ricardo Shaw  
 * @email  ricardo_shaw@outlook.com
 * @date 2016��9��23�� ����6:45:37  
  
 *  
    
  
 */
public class MOFAEOL extends Algorithm {
	
	//��Ⱥ��ģ
	private int populationSize_;
	
	//������ģ
	private int archiveSize_;
	
	//����������
	private int maxIterations_;
	
	//��ǰ��������
	private int iterations_;

	//�����������
	private int maxEvaluations_;
	
	//��ǰ��������
	private int evaluations_;
	
	//��ǰ��Ⱥ
	private SolutionSet populations_;
	
	//�ⲿ����
	private SolutionSet eArchive_;
	
	//ӵ�����뵵��
	private SolutionSet crowdingArchives_;
	
	//���ڲ���igdֵ
	private SolutionSets eArchives_;
	
	//��ʼ�뾶
	private double r0_ ;
	
	//ĩ���뾶
	private double rend_ ;
	
	//��ը�뾶ָ��
	private int alpha_ ;
	
	//һ�㻯ϵ��
	private double beta_ ;

	//��Ӧ��ֵComparator
	private Comparator fitComparator_;
	
	//֧��Comparator
	private Comparator domComparator_;
	
	//ӵ������Ա���
	private Comparator crowdingDistanceComparator_;
	
	//�洢һ��Distance����
	private Distance distance_;
	
	/**  
	  
	* @Title :  ���캯��
	  
	* @Description :  
	  
	* @param problem  
	  
	*/ 
	public MOFAEOL(Problem problem) {
		super(problem);
		// TODO Auto-generated constructor stub
	}
	
	
	
	/**
	 * ��ʼ�����в���
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
	
	
	
	
	
	
	/* (�� Javadoc)  
	  
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
		//��ʼ������
		initParams();
		
		//������ʼ��Ⱥ�ͼ���Objective��ֵ
		for(int i = 0 ; i < populationSize_ ; i++){
			Solution aspark = new Solution(problem_);
			problem_.evaluate(aspark);
			problem_.evaluateConstraints(aspark);
			populations_.add(aspark);
			if(eArchive_.add(aspark))
				crowdingArchives_.add(aspark);
		}
		
//		//CrowdingArchives�ĸ���ά��
//		distance_.crowdingDistanceAssignment(crowdingArchives_,problem_.getNumberOfObjectives());     
		
		
		//��ѭ����ʼ
		while( evaluations_ < maxEvaluations_){
			SolutionSet unionset = new SolutionSet();
			
			//���ز����Ļ���Ⱥ
			SolutionSet sparkset = sparkProduct(populations_, evaluations_);
			
			//���ز����ľ�Ӣ����ѧϰ�ļ���
			SolutionSet OBLset   = OBL(eArchive_,beta_);
			
			//��populations_��sparkset��OBLset�ϲ���һ��
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
			
			
			//����unionset����Ϣ
			for(int i = 0 ; i < unionset.size() ; i++){
				Solution aspark = unionset.get(i);
				problem_.evaluate(aspark);
				problem_.evaluateConstraints(aspark);
			}
			System.out.println("unionset.size() = " + unionset.size());
			//�����������뵽��֧��⼯�Լ�ӵ�����뵵����
			for(int i = 0 ; i < unionset.size() ; i++){
				Solution aspark = new Solution(unionset.get(i));
				
				if(crowdingArchives_.add(aspark))
					eArchive_.add(aspark);
				
				evaluations_++ ;
				
				if(evaluations_ == maxEvaluations_)
					break;
				
				System.out.println("evaluations_:"+evaluations_);
			}
			
			//����populations_��Ⱥ�������ԭ��Ⱥ�Ͳ����Ļ𻨼�������ѡ
			populations_ = selectSpark(populations_,sparkset);
//			iterations_ ++;
//			System.out.println("iterations_:"+iterations_);
			distance_.crowdingDistanceAssignment(crowdingArchives_,problem_.getNumberOfObjectives());
		}
		return crowdingArchives_;
	}


	
	
	
	/**
	 * private function
	 * �̻���ը����
	 */
	
	//�뾶��ʽ
	public double radius(int evaluations_){
		double r = Math.pow((maxEvaluations_ - evaluations_) / maxEvaluations_, alpha_) * (r0_ - rend_) + rend_;
		return r;
	}
	
	/**
	 * 
	  
	* @Title: sparkProduct  
	  
	* @Description: �̻���ը(������һ�仰�����������������)  
	  
	* @param @param populations
	* @param @param iterations
	* @param @return
	* @param @throws JMException    �趨�ļ�  
	  
	* @return SolutionSet    ��������  
	  
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
	 * ��Ӣ����ѧϰ
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
	 * ѡ��ը��
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
//	/* (�� Javadoc)  
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
//		//��ʼ������
//		initParams();
//		
//		//������ʼ��Ⱥ�ͼ���Objective��ֵ
//		for(int i = 0 ; i < populationSize_ ; i++){
//			Solution aspark = new Solution(problem_);
//			problem_.evaluate(aspark);
//			problem_.evaluateConstraints(aspark);
//			populations_.add(aspark);
//		}
//		
////		//CrowdingArchives�ĸ���ά��
////		distance_.crowdingDistanceAssignment(crowdingArchives_,problem_.getNumberOfObjectives());     
//		
//		
//		//��ѭ����ʼ
//		while(iterations_ < maxIterations_){
//			
//	    	if(iterations_ % 10 == 0 && iterations_!=0 || iterations_ == (maxIterations_-1) ){
//	    		eArchives_.add(new SolutionSet(crowdingArchives_));
//	    	}
//
//			SolutionSet unionset = new SolutionSet();
//			
//			//���ز����Ļ���Ⱥ
//			SolutionSet sparkset = sparkProduct(populations_, iterations_);
//			
//			//���ز����ľ�Ӣ����ѧϰ�ļ���
//			SolutionSet OBLset   = OBL(eArchive_,beta_);
//			
//			//��populations_��sparkset��OBLset�ϲ���һ��
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
//			//����unionset����Ϣ
//			for(int i = 0 ; i < unionset.size() ; i++){
//				Solution aspark = unionset.get(i);
//				problem_.evaluate(aspark);
//				problem_.evaluateConstraints(aspark);
//			}
//			
//			//�����������뵽��֧��⼯�Լ�ӵ�����뵵����
//			for(int i = 0 ; i < unionset.size() ; i++){
//				Solution aspark = new Solution(unionset.get(i));
//				if(eArchive_.add(aspark))
//					crowdingArchives_.add(aspark);
//			}
//			
//			//����populations_��Ⱥ�������ԭ��Ⱥ�Ͳ����Ļ𻨼�������ѡ
//			populations_ = selectSpark(populations_,sparkset);
//			iterations_ ++;
//			distance_.crowdingDistanceAssignment(crowdingArchives_,problem_.getNumberOfObjectives());
//		}
//		return eArchives_;
//	}

}
