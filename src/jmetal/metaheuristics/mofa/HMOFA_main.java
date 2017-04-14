/**
 * 
 */
package jmetal.metaheuristics.mofa;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import jmetal.core.Algorithm;
import jmetal.core.Problem;
import jmetal.core.SolutionSet;
import jmetal.operators.mutation.Mutation;
import jmetal.operators.mutation.NonUniformMutation;
import jmetal.operators.mutation.UniformMutation;
import jmetal.problems.Kursawe;
import jmetal.problems.ProblemFactory;
import jmetal.problems.DTLZ.DTLZ1;
import jmetal.problems.DTLZ.DTLZ2;
import jmetal.problems.DTLZ.DTLZ3;
import jmetal.problems.DTLZ.DTLZ4;
import jmetal.problems.DTLZ.DTLZ5;
import jmetal.problems.DTLZ.DTLZ6;
import jmetal.problems.DTLZ.DTLZ7;
import jmetal.problems.ZDT.ZDT1;
import jmetal.problems.ZDT.ZDT2;
import jmetal.problems.ZDT.ZDT3;
import jmetal.problems.ZDT.ZDT4;
import jmetal.problems.ZDT.ZDT5;
import jmetal.problems.ZDT.ZDT6;
import jmetal.qualityIndicator.QualityIndicator;
import jmetal.util.Configuration;
import jmetal.util.JMException;

/**
 * @author X1A0CH1
 *
 */
public class HMOFA_main {
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
	    
	    QualityIndicator indicators  ; // Object to get quality indicators
	        
	    HashMap<String, Object>  parameters ; // Operator parameters
	    
	    // Logger object and file to store log messages
	    logger_      = Configuration.logger_ ;
	    fileHandler_ = new FileHandler("newMOFA_main.log"); 
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
//	      problem = new ZDT7("Real");
	      //problem = new WFG1("Real");
	      problem = new DTLZ5("Real");
//	      problem = new DTLZ3("Real");
//	      problem = new OKA2("Real") ;
//	    	problem=new ZDT2("Real");
//	    	problem=new ZDT2("Real");
//	    	problem=new ZDT3("Real");
//	    	problem=new ZDT4("Real");
//	    	problem=new ZDT6("Real");
//	      indicators = new QualityIndicator(problem,"") ;
	    }
	    
	    algorithm = new HMOFA(problem) ;
	    
	    Integer maxIterations = 250 ;
	    Double perturbationIndex = 0.5;
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
	    SolutionSet population = algorithm.execute();
//	    List<SolutionSet> eArchives_ = algorithm.execute1();
	    long estimatedTime = System.currentTimeMillis() - initTime;
	    
	    // Print the results
	    logger_.info("Total execution time: "+estimatedTime + "ms");
	    logger_.info("Variables values have been writen to file newMOFA_VAR");
	    population.printVariablesToFile("newMOFA_VAR");    
	    logger_.info("Objectives values have been writen to file newMOFA_FUN");
	    population.printObjectivesToFile("newMOFA__ZDT1");
	  
	    if (indicators != null) {
	      logger_.info("Quality indicators") ;
	      logger_.info("Hypervolume: " + indicators.getHypervolume(population)) ;
	      logger_.info("GD         : " + indicators.getGD(population)) ;
	      logger_.info("IGD        : " + indicators.getIGD(population)) ;
	      logger_.info("Spread     : " + indicators.getSpread(population)) ;
	      logger_.info("Epsilon    : " + indicators.getEpsilon(population)) ;  
	    } // if
	  }//main
	} // mofa_main
