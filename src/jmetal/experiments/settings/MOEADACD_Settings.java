/**    
  
* @Title: MOEADACD_Settings.java  
  
* @Package jmetal.experiments.settings  
  
* @Description: TODO(用一句话描述该文件做什么)  
  
* @author Ricardo Shaw  

* @Email  ricardo_shaw@outlook.com    
  
* @date 2016年9月7日 下午2:28:58  
  
* @version V1.0    
  
*/ 
package jmetal.experiments.settings;

import java.util.HashMap;

import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.experiments.Settings;
import jmetal.metaheuristics.moead.MOEAD;
import jmetal.metaheuristics.moead.MOEADACD;
import jmetal.operators.crossover.CrossoverFactory;
import jmetal.operators.mutation.MutationFactory;
import jmetal.problems.ProblemFactory;
import jmetal.util.JMException;

/**  
  
 * @ClassName: MOEADACD_Settings  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author Ricardo Shaw  
 * @email  ricardo_shaw@outlook.com
 * @date 2016年9月7日 下午2:28:58  
  
 *  
    
  
 */
public class MOEADACD_Settings extends Settings {
	
	  public double CR_ ;
	  public double F_  ;
	  public int populationSize_ ;
	  public int maxEvaluations_ ;
	 
	  public double mutationProbability_          ;
	  public double mutationDistributionIndex_ ;

	  public String dataDirectory_  ;

	  public int T_        ;
	  public double delta_ ;
	  public int nr_    ;
	  
	  public String functionType_;
	
	
	public MOEADACD_Settings(String problem){
		super(problem);
		Object[] problemParams = {"Real"};
		
		try {
			    problem_ = (new ProblemFactory()).getProblem(problemName_, problemParams);
		    } catch (JMException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
		    }      

		    // Default experiments.settings
		    CR_ = 1.0 ;
		    F_  = 0.5 ;
		    if(problem_.equals("ZDT1")){
		    	maxEvaluations_     = 25000 ;
		    	populationSize_ 	= 100	;
		    	functionType_       = "_TE" ;
		    }else{
		    	
		    	
		    	functionType_       = "_PBI";
		    	
		    }
		    
		    mutationProbability_ = 1.0/problem_.getNumberOfVariables() ;
		    mutationDistributionIndex_ = 20;
		    
		    T_					= 20;
		    delta_	= 0.9;
		    nr_		= 2;
	}
	/* (非 Javadoc)  
	  
	 * <p>Title: configure</p>  
	  
	 * <p>Description: </p>  
	  
	 * @return
	 * @throws JMException  
	  
	 * @see jmetal.experiments.Settings#configure()  
	  
	 */
	@Override
	public Algorithm configure() throws JMException {
		// TODO Auto-generated method stub
		Algorithm algorithm;
	    Operator crossover;
	    Operator mutation;

	    HashMap  parameters ; // Operator parameters

	    // Creating the problem
	    algorithm = new MOEADACD(problem_,functionType_);

	    // Algorithm parameters
	    algorithm.setInputParameter("populationSize", populationSize_);
	    algorithm.setInputParameter("maxEvaluations", maxEvaluations_);
	    algorithm.setInputParameter("dataDirectory", dataDirectory_) ;
	    algorithm.setInputParameter("T", T_) ;
	    algorithm.setInputParameter("delta", delta_) ;
	    algorithm.setInputParameter("nr", nr_) ;
	    
	    // Crossover operator 
	    parameters = new HashMap() ;
	    parameters.put("CR", CR_) ;
	    parameters.put("F", F_) ;
	    crossover = CrossoverFactory.getCrossoverOperator("DifferentialEvolutionCrossover", parameters);                   
	    
	    // Mutation operator
	    parameters = new HashMap() ;
	    parameters.put("probability", mutationProbability_) ;
	    parameters.put("distributionIndex", mutationDistributionIndex_) ;
	    mutation = MutationFactory.getMutationOperator("PolynomialMutation", parameters);         
	    
	    algorithm.addOperator("crossover", crossover);
	    algorithm.addOperator("mutation", mutation);

	    return algorithm;
		

	}

}
