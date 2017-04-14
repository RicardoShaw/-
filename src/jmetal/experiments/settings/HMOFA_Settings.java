/**
 * 
 */
package jmetal.experiments.settings;

import java.util.HashMap;
import java.util.Properties;

import jmetal.core.Algorithm;
import jmetal.experiments.Settings;
import jmetal.metaheuristics.mofa.HMOFA;
import jmetal.operators.mutation.Mutation;
import jmetal.operators.mutation.NonUniformMutation;
import jmetal.operators.mutation.UniformMutation;
import jmetal.problems.ProblemFactory;
import jmetal.util.JMException;

/**
 * @author X1A0CH1
 *
 */
public class HMOFA_Settings extends Settings {
	public int fireflysSize_;
	public int archiveSize_ ;
    public int maxIterations_;
    public int maxEvaluations_;
    
   
    public double perturbationIndex_ ;
    public double mutationProbability_ ;
    public double beta_;
    public double gammar_;
    public HMOFA_Settings(String problem) {
        super(problem) ;
        
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
            fireflysSize_      = 100 ;
            archiveSize_       = 100 ;
            maxIterations_	   = 250 ;
            maxEvaluations_	   = 25000 ;
        }else{
            fireflysSize_      = 200 ;
            archiveSize_       = 200 ;
            maxIterations_	   = 250 ;
            maxEvaluations_	   = 50000 ;
        }

        perturbationIndex_ = 0.5 ;
        beta_              = 1;
        gammar_            = 1;
        mutationProbability_ = 1.0/problem_.getNumberOfVariables() ;
      } // OMOPSO_Settings
	/* (non-Javadoc)
	 * @see jmetal.experiments.Settings#configure()
	 */
	@Override
	public Algorithm configure() throws JMException {
		Algorithm algorithm ;
	    Mutation  uniformMutation ;
	    Mutation nonUniformMutation ;

	    HashMap  parameters ; // Operator parameters
		
	    algorithm = new HMOFA(problem_);
	    algorithm.setInputParameter("fireflysSize",fireflysSize_);
	    algorithm.setInputParameter("archiveSize",archiveSize_);
	    algorithm.setInputParameter("maxIterations",maxIterations_);
	    algorithm.setInputParameter("maxEvaluations", maxEvaluations_);
	    algorithm.setInputParameter("beta", beta_);
	    algorithm.setInputParameter("gammar", gammar_);
	    
	    parameters = new HashMap<String, Object>() ;
	    parameters.put("probability", mutationProbability_) ;
	    parameters.put("perturbation", perturbationIndex_) ;
	    uniformMutation = new UniformMutation(parameters);
	    
	    parameters = new HashMap() ;
	    parameters.put("probability", mutationProbability_) ;
	    parameters.put("perturbation", perturbationIndex_) ;
	    parameters.put("maxIterations", maxIterations_) ;
	    nonUniformMutation = new NonUniformMutation(parameters);

	    // Add the operators to the algorithm
	    algorithm.addOperator("uniformMutation",uniformMutation);
	    algorithm.addOperator("nonUniformMutation",nonUniformMutation);
	    return algorithm;
	}
	 /**
	   * Configure dMOPSO with user-defined parameter experiments.settings
	   * @return A dMOPSO algorithm object
	   */
	  @Override
	  public Algorithm configure(Properties configuration) throws JMException {
	    Algorithm algorithm ;
	    Mutation  uniformMutation ;
	    Mutation nonUniformMutation ;

	    HashMap  parameters ; // Operator parameters

	    // Creating the algorithm.
	    algorithm = new HMOFA(problem_) ;

	    // Algorithm parameters
	    fireflysSize_ = Integer.parseInt(configuration.getProperty("fireflysSize",String.valueOf(fireflysSize_)));
	    maxIterations_  = Integer.parseInt(configuration.getProperty("maxIterations",String.valueOf(maxIterations_)));
	    archiveSize_ = Integer.parseInt(configuration.getProperty("archiveSize", String.valueOf(archiveSize_)));

	    algorithm.setInputParameter("swarmSize",fireflysSize_);
	    algorithm.setInputParameter("maxIterations",maxIterations_);
	    algorithm.setInputParameter("archiveSize",archiveSize_);
	    algorithm.setInputParameter("beta", beta_);
	    algorithm.setInputParameter("gammar", gammar_);

	    mutationProbability_ = Double.parseDouble(configuration.getProperty("mutationProbability",String.valueOf(mutationProbability_)));
	    perturbationIndex_ = Double.parseDouble(configuration.getProperty("perturbationIndex",String.valueOf(mutationProbability_)));
	    parameters = new HashMap() ;
	    parameters.put("probability", mutationProbability_) ;
	    parameters.put("perturbation", perturbationIndex_) ;
	    uniformMutation = new UniformMutation(parameters);

	    parameters = new HashMap() ;
	    parameters.put("probability", mutationProbability_) ;
	    parameters.put("perturbation", perturbationIndex_) ;
	    parameters.put("maxIterations", maxIterations_) ;
	    nonUniformMutation = new NonUniformMutation(parameters);

	    // Add the operators to the algorithm
	    algorithm.addOperator("uniformMutation",uniformMutation);
	    algorithm.addOperator("nonUniformMutation",nonUniformMutation);
	    return algorithm ;
	  }
	  
}
