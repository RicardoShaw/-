/**
 * 
 */
package jmetal.metaheuristics.mofa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import jmetal.core.Algorithm;
import jmetal.core.Problem;
import jmetal.core.SolutionSet;
import jmetal.operators.mutation.Mutation;
import jmetal.operators.mutation.NonUniformMutation;
import jmetal.operators.mutation.UniformMutation;
import jmetal.problems.ProblemFactory;
import jmetal.problems.DTLZ.DTLZ1;
import jmetal.problems.DTLZ.DTLZ2;
import jmetal.problems.DTLZ.DTLZ4;
import jmetal.problems.DTLZ.DTLZ5;
import jmetal.problems.ZDT.ZDT1;
import jmetal.problems.ZDT.ZDT2;
import jmetal.qualityIndicator.QualityIndicator;
import jmetal.util.Configuration;
import jmetal.util.JMException;

/**
 * @author X1A0CH1
 *
 */
public class MOFA_HV {
	 public static Logger      logger_ ;      // Logger object
	  public static FileHandler fileHandler_ ; // FileHandler object

	  /**
	   * @param args Command line arguments. The first (optional) argument specifies 
	   *             the problem to solve.
	   * @throws JMException 
	   * @throws IOException 
	   * @throws SecurityException 
	   * Usage: three options
	   *      - jmetal.metaheuristics.mocell.MOCell_main
	   *      - jmetal.metaheuristics.mocell.MOCell_main problemName
	   *      - jmetal.metaheuristics.mocell.MOCell_main problemName ParetoFrontFile
	   */
	  public static void main(String [] args) throws JMException, IOException, ClassNotFoundException {
	    Problem   problem   ;         // The problem to solve
	    Algorithm algorithm ;         // The algorithm to use
	    Mutation  uniformMutation ;
	    Mutation nonUniformMutation ;
	    List<SolutionSet> populations=new ArrayList<SolutionSet>();
	    SolutionSet population = null;
	    
	    QualityIndicator indicators ; // Object to get quality indicators
	        
	    HashMap<String, Object>  parameters ; // Operator parameters
	    
	    // Logger object and file to store log messages
	    logger_      = Configuration.logger_ ;
	    fileHandler_ = new FileHandler("MOFA_main.log"); 
	    logger_.addHandler(fileHandler_) ;
	    
	    indicators = null ;
	    if (args.length == 1) {
	      Object [] params = {"Real"};
	      problem = (new ProblemFactory()).getProblem(args[0],params);
	    } // if
	    else if (args.length == 2) {
	      Object [] params = {"Real"};
	      problem = (new ProblemFactory()).getProblem(args[0],params);
	      indicators = new QualityIndicator(problem, args[1]) ;
	    } // if
	    else { // Default problem
//	      problem = new Kursawe("Real", 3); 
	      //problem = new Water("Real");
//	      problem = new ZDT1("Real");
	      //problem = new WFG1("Real");
	      //problem = new DTLZ1("Real");
//	      problem = new OKA2("Real") ;
	    problem=new DTLZ4("Real");
	    indicators = new QualityIndicator(problem, "E:\\paretoFronts\\DTLZ4.2D.pf") ;
	    }
	    
	    algorithm = new NewMOFA(problem) ;
	    
	    Integer maxIterations = 250 ;
	    Double perturbationIndex = 0.5 ;
	    Double alpha = 0.25;
	    Double beta = 1.0;
	    Double gammar = 1.0;
	    Double mutationProbability = 1.0/problem.getNumberOfVariables() ;
	    
	    // Algorithm parameters
	    algorithm.setInputParameter("fireflysSize",200);
	    algorithm.setInputParameter("archiveSize",200);
	    algorithm.setInputParameter("maxIterations",maxIterations);
	    algorithm.setInputParameter("alpha", alpha);
	    algorithm.setInputParameter("beta", beta);
	    algorithm.setInputParameter("gammar", gammar);
	    
	    parameters = new HashMap<String, Object>() ;
	    parameters.put("probability", mutationProbability) ;
	    parameters.put("perturbation", perturbationIndex) ;
	    uniformMutation = new UniformMutation(parameters);
	    
	    parameters = new HashMap<String, Object>() ;
	    parameters.put("probability", mutationProbability) ;
	    parameters.put("perturbation", perturbationIndex) ;
	    parameters.put("maxIterations", maxIterations) ;
	    nonUniformMutation = new NonUniformMutation(parameters);

	    // Add the operators to the algorithm
	    algorithm.addOperator("uniformMutation",uniformMutation);
	    algorithm.addOperator("nonUniformMutation",nonUniformMutation);

	    // Execute the Algorithm 
	    long initTime = System.currentTimeMillis();
//	    populations = algorithm.execute();
	    long estimatedTime = System.currentTimeMillis() - initTime;
	    
	    // Print the results

//	    Iterator<SolutionSet> iter = populations.iterator();
	    if (indicators != null) {
	      logger_.info("Quality indicators") ;
	      for(int n = 0 ; n < populations.size();n++){
	    	population = populations.get(n);
		    logger_.info("Variables values have been writen to file MOFA_VAR");
		    population.printVariablesToFile("MOFA_VAR"+n);    
		    logger_.info("Objectives values have been writen to file MOFA_FUN");
		    population.printObjectivesToFile("MOFA_FUN"+n);
		    logger_.info("GD         : "+n+"´Îµü´ú  " + indicators.getGD(population)) ;
	      }
//	      logger_.info("GD         : " + indicators.getGD(population)) ;
//	      logger_.info("IGD        : " + indicators.getIGD(population)) ;
//	      logger_.info("Spread     : " + indicators.getSpread(population)) ;
//	      logger_.info("Epsilon    : " + indicators.getEpsilon(population)) ;  
	    } // if
	  }//main
}
