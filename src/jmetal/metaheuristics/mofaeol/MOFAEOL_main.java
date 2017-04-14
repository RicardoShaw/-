/**    
  
* @Title: MOFAEOL_main.java  
  
* @Package jmetal.metaheuristics.mofaeol  
  
* @Description: TODO(用一句话描述该文件做什么)  
  
* @author Ricardo Shaw  

* @Email  ricardo_shaw@outlook.com    
  
* @date 2016年9月26日 下午9:01:00  
  
* @version V1.0    
  
*/ 
package jmetal.metaheuristics.mofaeol;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import jmetal.core.Algorithm;
import jmetal.core.Problem;
import jmetal.core.SolutionSet;
import jmetal.problems.ProblemFactory;
import jmetal.problems.ZDT.ZDT1;
import jmetal.problems.ZDT.ZDT6;
import jmetal.qualityIndicator.QualityIndicator;
import jmetal.util.Configuration;
import jmetal.util.JMException;

/**  
  
 * @ClassName: MOFAEOL_main  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author Ricardo Shaw  
 * @email  ricardo_shaw@outlook.com
 * @date 2016年9月26日 下午9:01:00  
  
 *  
    
  
 */
public class MOFAEOL_main {
	public static Logger logger_;
	public static FileHandler fileHandler_;
	
	public static void main(String [] args) throws SecurityException, IOException, ClassNotFoundException, JMException{
		Problem problem;
		Algorithm algorithm;
		QualityIndicator indicators;
		SolutionSet population ;
		
		logger_ 		=  Configuration.logger_;
		fileHandler_ 	= new FileHandler("MOFAEOL_main.log");
		logger_.addHandler(fileHandler_);
		
		
		indicators = null;
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
//			problem = new Kursawe("Real", 3); 
//			problem = new Water("Real");
			problem = new ZDT6("Real");
//			problem = new WFG1("Real");
//			problem = new DTLZ7("Real");
//			problem = new OKA2("Real") ;
			indicators = new QualityIndicator(problem,"E:\\paretoFronts\\ZDT4.pf");
		}
		
		algorithm = new MOFAEOL(problem);
//		Integer populationSize_ = 100;
//		Integer archiveSize_    = 100;
//		Integer maxEvaluations_ = 25000;
//		Integer maxIterations_  = 5 ;
//        Double 	r0_ 		    = 0.5 ;
//        Double  rend_		    = 0.000001 ;
//        Double  beta_           = 1.0;
//        Integer alpha_          = 5;
		

//		algorithm.setInputParameter("populationSize",100);
//		algorithm.setInputParameter("archiveSize", 100);
//		algorithm.setInputParameter("maxIterations", 5);
//		algorithm.setInputParameter("maxEvaluations", 25000);
//		algorithm.setInputParameter("r0", r0_);
//		algorithm.setInputParameter("rend", rend_);
//		algorithm.setInputParameter("alpha", alpha_);
//		algorithm.setInputParameter("beta", beta_);
		
		
		population = algorithm.execute();
		
		
		population.printFeasibleFUN("E:\\feasibleFun.dat");
		population.printFeasibleVAR("E:\\feasibleVar.dat");
		
		
//	    if (indicators != null) {
//	        logger_.info("Quality indicators") ;
//	        logger_.info("Variables values have been writen to file MOFAEOL_VAR");
//	        logger_.info("Objectives values have been writen to file MOFAEOL_FUN");
//	        logger_.info("GD         : " + indicators.getGD(population)) ;
//	    }
//	        logger_.info("Hypervolume: " + indicators.getHypervolume(population)) ;
//	        logger_.info("GD         : " + indicators.getGD(population)) ;
//	        logger_.info("IGD        : " + indicators.getIGD(population)) ;
//	        logger_.info("Spread     : " + indicators.getSpread(population)) ;
//	        logger_.info("Epsilon    : " + indicators.getEpsilon(population)) ;  
	}
	
	
}
