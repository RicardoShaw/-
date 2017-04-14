/**
 * 
 */
package jmetal.problems;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.encodings.solutionType.ArrayRealSolutionType;
import jmetal.encodings.solutionType.BinaryRealSolutionType;
import jmetal.encodings.solutionType.RealSolutionType;
import jmetal.util.JMException;

/**
 * @author X1A0CH1
 *
 */
public class Deb2 extends Problem {

	/* (non-Javadoc)
	 * @see jmetal.core.Problem#evaluate(jmetal.core.Solution)
	 */
	public Deb2(String solutionType) {
	    numberOfVariables_   = 2 ;
	    numberOfObjectives_  = 2 ;
	    numberOfConstraints_ = 0;
	    problemName_         = "Deb2";
	        
	    upperLimit_ = new double[numberOfVariables_];
	    lowerLimit_ = new double[numberOfVariables_];
	    for (int var = 0; var < numberOfVariables_; var++){
	      lowerLimit_[var] =  0.1;
	      upperLimit_[var] =  1.0;
	    } // for
	        
	    if (solutionType.compareTo("BinaryReal") == 0)
	    	solutionType_ = new BinaryRealSolutionType(this) ;
	    else if (solutionType.compareTo("Real") == 0) 
	    	solutionType_ = new RealSolutionType(this) ;
	    else if (solutionType.compareTo("ArrayReal") == 0)
	    	solutionType_ = new ArrayRealSolutionType(this) ;
	    else {
	    	System.out.println("Error: solution type " + solutionType + " invalid") ;
	    	System.exit(-1) ;
	    } 
	  } //Viennet2
	      
	    
	  /**
	   * Evaluates a solution
	   * @param solution The solution to evaluate
	   * @throws JMException 
	   */
	                    
	@Override
	public void evaluate(Solution solution) throws JMException {
		// TODO Auto-generated method stub
	    double [] x = new double[numberOfVariables_];
	    double [] f = new double[numberOfObjectives_];
	    double g 	= 0.0;
	    
	    
	    for (int i = 0; i < numberOfVariables_; i++)
	      x[i] = solution.getDecisionVariables()[i].getValue();
	        
	    // First function
	    f[0] = x[0] ;
	    
	    g = 2.0 - Math.exp(-((x[1]-0.2)/0.004)*((x[1]-0.2)/0.004))-
	    		0.8 * Math.exp(-((x[1]-0.6)/0.4)*((x[1]-0.6)/0.4)) ;

	    // Second function
	    f[1] =	g / f[0];

	    // Third function
      
	    for (int i = 0; i < numberOfObjectives_; i++)
	      solution.setObjective(i,f[i]);        
	}

}
