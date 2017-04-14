/**    
  
* @Title: MOFAEOL.java  
  
* @Package jmetal.experiments.settings  
  
* @Description: TODO(用一句话描述该文件做什么)  
  
* @author Ricardo Shaw  

* @Email  ricardo_shaw@outlook.com    
  
* @date 2016年9月23日 下午6:46:16  
  
* @version V1.0    
  
*/ 
package jmetal.experiments.settings;

import java.util.HashMap;

import jmetal.core.Algorithm;
import jmetal.experiments.Settings;
import jmetal.metaheuristics.mofaeol.MOFAEOL;
import jmetal.problems.ProblemFactory;
import jmetal.util.JMException;

/**  
  
 * @ClassName: MOFAEOL  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author Ricardo Shaw  
 * @email  ricardo_shaw@outlook.com
 * @date 2016年9月23日 下午6:46:16  
  
 *  
    
  
 */
public class MOFAEOL_Settings extends Settings {
	public int populationSize_;
	public int archiveSize_;
	public int maxIterations_;
	public int maxEvaluations_;
	public int alpha_;
	public double r0_;
	public double rend_;
	public double beta_;
	

	
	public MOFAEOL_Settings(String problem){
		super(problem);
	     Object [] problemParams = {"Real"};
	        try {
	    	    problem_ = (new ProblemFactory()).getProblem(problemName_, problemParams);
	        } catch (JMException e) {
	    	    e.printStackTrace();
	        }      
	        // Default experiments.settings
	        if(problem.equals("ZDT1")||problem.equals("ZDT2")
	        		||problem.equals("ZDT3")||problem.equals("ZDT4")
	        		||problem.equals("ZDT6")){
	            populationSize_    = 100 ;
	            archiveSize_       = 100 ;
	            maxIterations_     = 250 ;
	            maxEvaluations_	   = 25000 ;
	        }else{
	            populationSize_    = 200 ;
	            archiveSize_       = 200 ;
	            maxIterations_     = 250 ;
	            maxEvaluations_    = 50000 ;
	        }

	        r0_ 				= 0.5 ;
	        rend_				= 0.000001 ;
	        beta_               = 1.0;
	        alpha_              = 5;

	}//MOFAEOL_Settings
	/* (非 Javadoc)  
	  
	 * <p>Title: configure</p>  
	  
	 * <p>Description: </p>  
	  
	 * @return
	 * @throws JMException  
	  
	 * @see jmetal.experiments.Settings#configure()  
	  
	 */
	@Override
	public Algorithm configure() throws JMException {
		Algorithm algorithm;

		algorithm = new MOFAEOL(problem_);
		algorithm.setInputParameter("populationSize",populationSize_);
		algorithm.setInputParameter("archiveSize", archiveSize_);
		algorithm.setInputParameter("maxIterations", maxIterations_);
		algorithm.setInputParameter("maxEvaluations", maxEvaluations_);
		algorithm.setInputParameter("r0", r0_);
		algorithm.setInputParameter("rend", rend_);
		algorithm.setInputParameter("alpha", alpha_);
		algorithm.setInputParameter("beta", beta_);

		return algorithm;
	}

}
