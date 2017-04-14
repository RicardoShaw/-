/**    
  
* @Title: HypE.java  
  
* @Package jmetal.metaheuristics.HypE  
  
* @Description: TODO(用一句话描述该文件做什么)  
  
* @author Ricardo Shaw  

* @Email  ricardo_shaw@outlook.com    
  
* @date 2016年9月7日 下午7:11:36  
  
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
 * @Description: HypE算法(这里用一句话描述这个类的作用)  
 * @author Ricardo Shaw  
 * @email  ricardo_shaw@outlook.com
 * @date 2016年9月7日 下午7:11:36  
  
 *  
    
  
 */
public class HypE extends Algorithm {
	//种群的个体数量
	private int populationSize_;
	
	//最大进化次数
	private int maxEvaluations_;
	
	//天选人数=2
	private int numOfSelected_;
	
	//初始种群集合
	private SolutionSet population_;
	
	//最后输出的档案集
	private SolutionSet archive_;
	
	/**  
	  
	* @Title :  HypE构造器
	  
	* @Description :  无
	  
	* @param problem  
	  
	*/ 
	public HypE(Problem problem) {
		super(problem);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public void initParams(){
		
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
	  
	* @Description: 基于hypeIndicator  移除前端中的部分个体(这里用一句话描述这个方法的作用)  
	  
	* @param @param solutionsets  		多层Pareto前端
	* @param @param populationSize_		种群个数大小
	* @param @param numOfSelected_      天选个数
	* @param        fronts_             多层Pareto前端的最后一层    
	* @return void    返回类型  
	  
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
	  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	  
	* @param @param solutionSet
	* @param @param d_value    设定文件  
	  
	* @return void    返回类型  
	  
	* @throws  
	  
	*/  
	private void hypeIndicator(SolutionSet solutionSet, int d_value) {
		// TODO Auto-generated method stub
		
	}




	
	
	
	
	
	
}
