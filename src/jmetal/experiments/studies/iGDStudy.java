/**    
  
* @Title: iGDStudy.java  
  
* @Package jmetal.experiments.studies  
  
* @Description: TODO(用一句话描述该文件做什么)  
  
* @author Ricardo Shaw  

* @Email  ricardo_shaw@outlook.com    
  
* @date 2016年9月6日 下午8:29:13  
  
* @version V1.0    
  
*/ 
package jmetal.experiments.studies;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import jmetal.core.Algorithm;
import jmetal.experiments.Experiment;
import jmetal.experiments.Settings;
import jmetal.experiments.settings.HMOFA_Settings;
import jmetal.experiments.settings.MOEADACD_Settings;
import jmetal.experiments.settings.MOEAD_Settings;
import jmetal.experiments.settings.MOFAEOL_Settings;
import jmetal.experiments.settings.MOFA_Settings;
import jmetal.experiments.settings.NSGAII_Settings;
import jmetal.experiments.settings.OMOPSO_Settings;
import jmetal.util.JMException;

/**  
  
 * @ClassName: iGDStudy  
 * @Description: 这个函数主要用于对比算法的收敛性的实验 
 * @author Ricardo Shaw  
 * @email  ricardo_shaw@outlook.com
 * @date 2016年9月6日 下午8:29:13  
  
 *  
    
  
 */
public class iGDStudy extends Experiment {

	/* (非 Javadoc)  
	  
	 * Title: algorithmSettings
	 * Description:  
	 * @param problemName
	 * @param problemId
	 * @param algorithm
	 * @throws ClassNotFoundException  
	  
	 * @see jmetal.experiments.Experiment#algorithmSettings(java.lang.String, int, jmetal.core.Algorithm[])  
	  
	 */
	@Override
	public void algorithmSettings(String problemName, int problemId,
			Algorithm[] algorithm) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		 try {
		      int numberOfAlgorithms = algorithmNameList_.length;

		      HashMap[] parameters = new HashMap[numberOfAlgorithms];

		      for (int i = 0; i < numberOfAlgorithms; i++) {
		        parameters[i] = new HashMap();
		      } // for

		      if (!(paretoFrontFile_[problemIndex] == null) && !paretoFrontFile_[problemIndex].equals("")) {
		        for (int i = 0; i < numberOfAlgorithms; i++)
		          parameters[i].put("frontPath_", frontPath_[problemIndex]);
//		          parameters[i].put("paretoFrontFile_", paretoFrontFile_[problemIndex]);
		      } // if
		      algorithm[0] = new HMOFA_Settings(problemName).configure();
		      algorithm[1] = new NSGAII_Settings(problemName).configure();
		      algorithm[2] = new OMOPSO_Settings(problemName).configure();
		      algorithm[3] = new MOEAD_Settings(problemName).configure();
//		      algorithm[4] = new MOEADACD_Settings(problemName).configure();
//		      algorithm[5] = new MOFA_Settings(problemName).configure();
//		      algorithm[6] = new MOFAEOL_Settings(problemName).configure();

		    } catch (IllegalArgumentException ex) {
		      Logger.getLogger(StandardStudy.class.getName()).log(Level.SEVERE, null, ex);
		    } catch  (JMException ex) {
		      Logger.getLogger(StandardStudy.class.getName()).log(Level.SEVERE, null, ex);
		    }
	}
	
	public static void main(String[] args) throws JMException, IOException{
		iGDStudy exp  = new iGDStudy();
		
		/** 实验名为iGDStudy，主要研究算法的收敛性*/
		exp.experimentName_ = "iGDStudy" ;
		
		/** 测试算法集合*/
		exp.algorithmNameList_ = new String[] {
				"MOFAMS",
				"NSGAII",
				"OMOPSO"
				,"MOEAD"
//				"MOEADACD",
//				"MOFA",
//				"MOFAEOL"
				};
	    
		/** 测试函数集合*/
		exp.problemList_ = new String[]{"ZDT1", "ZDT2","ZDT3", "ZDT4","ZDT6",
//										"Kursawe","Deb2", 
										"DTLZ1","DTLZ2","DTLZ3","DTLZ4","DTLZ5",
										"DTLZ6","DTLZ7"
//										,"Viennet","Viennet2","Viennet3"
										};
		/** 真实Pareto前端文件*/
		exp.paretoFrontFile_ = new String[]{"ZDT1.pf", "ZDT2.pf","ZDT3.pf",
											"ZDT4.pf","ZDT6.pf",
											"DTLZ1.2D.pf","DTLZ2.2D.pf","DTLZ3.2D.pf",
											"DTLZ4.2D.pf","DTLZ5.2D.pf","DTLZ6.2D.pf",
                							"DTLZ7.2D.pf"};
		
		 int numberOfAlgorithms = exp.algorithmNameList_.length;

		 exp.experimentBaseDirectory_ = "/Users/antelverde/Softw/pruebas/jmetal/" +
				 						exp.experimentName_;
		 
		 exp.paretoFrontDirectory_ = "/Users/antelverde/Softw/pruebas/data/paretoFronts";
		
		 
		 exp.indicatorList_ = new String[]{"IGD"};


		 exp.algorithmSettings_ = new Settings[numberOfAlgorithms];

		 exp.independentRuns_ = 1;

		 exp.initExperiment();

		    // Run the experiments
		  int numberOfThreads = 4;
		  
		  exp.runExperiment(numberOfThreads) ;

		  exp.generateQualityIndicators() ;


		 
		 
	}

}
