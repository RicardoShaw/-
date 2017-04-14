/**    
  
* @Title: HypE.java  
  
* @Package jmetal.metaheuristics.HypE  
  
* @Description: TODO(��һ�仰�������ļ���ʲô)  
  
* @author Ricardo Shaw  

* @Email  ricardo_shaw@outlook.com    
  
* @date 2016��9��7�� ����7:11:36  
  
* @version V1.0    
  
*/ 
package jmetal.metaheuristics.HypE;

import jmetal.core.Algorithm;
import jmetal.core.Problem;
import jmetal.core.SolutionSet;
import jmetal.core.SolutionSets;
import jmetal.util.JMException;

/**  
  
 * @ClassName: HypE  
 * @Description: HypE�㷨(������һ�仰��������������)  
 * @author Ricardo Shaw  
 * @email  ricardo_shaw@outlook.com
 * @date 2016��9��7�� ����7:11:36  
  
 *  
    
  
 */
public class HypE extends Algorithm {
	//��Ⱥ�ĸ�������
	private int populationSize_;
	
	//����������
	private int maxEvaluations_;
	
	//��ѡ����=2
	private int numOfSelected_;
	
	//��ʼ��Ⱥ����
	private SolutionSet population_;
	
	//�������ĵ�����
	private SolutionSet archive_;
	
	/**  
	  
	* @Title :  HypE������
	  
	* @Description :  ��
	  
	* @param problem  
	  
	*/ 
	public HypE(Problem problem) {
		super(problem);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public void initParams(){
		
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
		return null;
	}
	
	//private function
	public void generateFrontPartition(){
		
	}
	
	
	
	
	public void environmentalSelection(SolutionSets solutionsets){
		int fronts = solutionsets.size() - 1;
		for(int i  = fronts ; i >= 0 ; i-- ){
			if(solutionsets.getSize() - solutionsets.get(i).size() >= populationSize_)
				solutionsets.remove(i);
			else
				break;
		}
		if(solutionsets.getSize() > populationSize_){
			hypeReduction(solutionsets,fronts,populationSize_,numOfSelected_);
		}
	}



	/**
	 
	  
	* @Title: hypeReduction  
	  
	* @Description: ����hypeIndicator  �Ƴ�ǰ���еĲ��ָ���(������һ�仰�����������������)  
	  
	* @param @param solutionsets  		���Paretoǰ��
	* @param @param populationSize_		��Ⱥ������С
	* @param @param numOfSelected_      ��ѡ����
	* @param        fronts_             ���Paretoǰ�˵����һ��    
	* @return void    ��������  
	  
	* @throws  
	  
	*/  
	private void hypeReduction(SolutionSets solutionsets, int fronts ,int populationSize_,
			int numOfSelected_) {
		// TODO Auto-generated method stub
		while(solutionsets.getSize() > populationSize_){
			int D_value =solutionsets.getSize() - populationSize_;
			hypeIndicator(solutionsets.get(fronts),D_value);
		}
		
		
	}



	/**  
	  
	* @Title: hypeIndicator  
	  
	* @Description: TODO(������һ�仰�����������������)  
	  
	* @param @param solutionSet
	* @param @param d_value    �趨�ļ�  
	  
	* @return void    ��������  
	  
	* @throws  
	  
	*/  
	private void hypeIndicator(SolutionSet solutionSet, int d_value) {
		// TODO Auto-generated method stub
		
	}




	
	
	
	
	
	
}
