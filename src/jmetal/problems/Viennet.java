/**
 * 
 */
package jmetal.problems;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.encodings.solutionType.BinaryRealSolutionType;
import jmetal.encodings.solutionType.RealSolutionType;
import jmetal.util.JMException;

/**
 * @author X1A0CH1
 *
 */
public class Viennet extends Problem {
	
	public Viennet(String solutionType){
		numberOfVariables_   = 2;
		numberOfObjectives_  = 3;
		numberOfConstraints_ = 0;
		problemName_         = "Viennet";
		
		upperLimit_ = new double[numberOfVariables_];
		lowerLimit_ = new double[numberOfVariables_];
		for(int var = 0 ; var < numberOfVariables_ ; var++){
			lowerLimit_[var] = -2.0;
			upperLimit_[var] =  2.0;
		}
	
		if (solutionType.compareTo("BinaryReal") == 0)
	    	solutionType_ = new BinaryRealSolutionType(this) ;
	    else if (solutionType.compareTo("Real") == 0)
	      solutionType_ = new RealSolutionType(this) ;
	    else {
	    	System.out.println("Error: solution type " + solutionType + " invalid") ;
	    	System.exit(-1) ;
	    }
	}//Viennet
	
	
	
	
	
	/* (non-Javadoc)
	 * @see jmetal.core.Problem#evaluate(jmetal.core.Solution)
	 */
	@Override
	public void evaluate(Solution solution) throws JMException {
		// TODO Auto-generated method stub
		double [] x = new double[numberOfVariables_];
		double [] f = new double[numberOfObjectives_];
		
		for(int i = 0 ; i < numberOfVariables_ ; i++)
			x[i] = solution.getDecisionVariables()[i].getValue();
		
		//First function
		f[0] = x[0] * x[0] + (x[1] - 1) * (x[1] - 1) ;

		//Second function
		f[1] = x[0] * x[0] + (x[1] + 1) * (x[1] + 1) + 1;
		
		//Third function
		f[2] = (x[0] - 1) * (x[0] - 1) + x[1] * x[1] + 2;
			
		for(int i = 0 ;i < numberOfObjectives_ ;i++)
			solution.setObjective(i, f[i]);
	}//evaluate
}//Viennet
