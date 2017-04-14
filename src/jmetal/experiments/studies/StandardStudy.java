//  StandardStudy.java
//
//  Authors:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package jmetal.experiments.studies;

import jmetal.core.Algorithm;
import jmetal.experiments.Experiment;
import jmetal.experiments.Settings;
import jmetal.experiments.settings.*;
import jmetal.experiments.util.Friedman;
import jmetal.util.JMException;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class implementing a typical experimental study. Five algorithms are 
 * compared when solving the ZDT, DTLZ, and WFG benchmarks, and the hypervolume,
 * spread and additive epsilon indicators are used for performance assessment.
 */
public class StandardStudy extends Experiment {

  /**
   * Configures the algorithms in each independent run
   * @param problemName The problem to solve
   * @param problemIndex
   * @throws ClassNotFoundException 
   */
  public void algorithmSettings(String problemName, 
  		                          int problemIndex, 
  		                          Algorithm[] algorithm) throws ClassNotFoundException {
    try {
      int numberOfAlgorithms = algorithmNameList_.length;

      HashMap[] parameters = new HashMap[numberOfAlgorithms];

      for (int i = 0; i < numberOfAlgorithms; i++) {
        parameters[i] = new HashMap();
      } // for

      if (!paretoFrontFile_[problemIndex].equals("")) {
        for (int i = 0; i < numberOfAlgorithms; i++)
          parameters[i].put("paretoFrontFile_", paretoFrontFile_[problemIndex]);
        } // if
      	
      
      //对比算法的参数设置工厂
      	algorithm[0] = new OMOPSO_Settings(problemName).configure();


        
        

      } catch (IllegalArgumentException ex) {
      Logger.getLogger(StandardStudy.class.getName()).log(Level.SEVERE, null, ex);
    } catch  (JMException ex) {
      Logger.getLogger(StandardStudy.class.getName()).log(Level.SEVERE, null, ex);
    }
  } // algorithmSettings

  /**
   * Main method
   * @param args
   * @throws JMException
   * @throws IOException
   */
  public static void main(String[] args) throws JMException, IOException {
    StandardStudy exp = new StandardStudy();

    exp.experimentName_ = "StandardStudy";
    /*
     * 2016.09.06  对比算法还差如下几个算法
     * HypE算法（需自己编写）,MOFA算法（需自己编写），MOEADACD算法（需自己改写）
     */
    exp.algorithmNameList_ = new String[]{"MOFAMS","NSGAII","OMOPSO","MOEAD"
    										,"MOEADACD","HypE","MOFA"};
    /*
     * 2016.09.06  实验还差如下几个
     * Deb2（需要自己编写），Viennet（也需要自己编写）
     */
    exp.problemList_ = new String[]{"ZDT1", "ZDT2","ZDT3", "ZDT4","ZDT6",
    								"Kursawe","Deb2", 
                                    "DTLZ1","DTLZ2","DTLZ3","DTLZ4","DTLZ5",
                                    "DTLZ6","DTLZ7",
                                    "Viennet","Viennet2","Viennet3"};
    
    /*
     * 2016.09.06	paretoFrontFile差如下几个
     * KUR实验，Deb2实验，Viennet-Viennet3实验的
     */
    exp.paretoFrontFile_ = new String[]{"ZDT1.pf", "ZDT2.pf","ZDT3.pf",
                                    "ZDT4.pf","ZDT6.pf",
                                    "DTLZ1.2D.pf","DTLZ2.2D.pf","DTLZ3.2D.pf",
                                    "DTLZ4.2D.pf","DTLZ5.2D.pf","DTLZ6.2D.pf",
                                    "DTLZ7.2D.pf"};

    exp.indicatorList_ = new String[]{"GD"};

    int numberOfAlgorithms = exp.algorithmNameList_.length;

    exp.experimentBaseDirectory_ = "/Users/antelverde/Softw/pruebas/jmetal/" +
                                   exp.experimentName_;
    exp.paretoFrontDirectory_ = "/Users/antelverde/Softw/pruebas/data/paretoFronts";

    exp.algorithmSettings_ = new Settings[numberOfAlgorithms];

    exp.independentRuns_ = 10;

    exp.initExperiment();

    // Run the experiments
    int numberOfThreads = 4;
    exp.runExperiment(numberOfThreads) ;

    exp.generateQualityIndicators() ;

    // Generate latex tables
    exp.generateLatexTables() ;

    // Configure the R scripts to be generated
//    int rows  ;
//    int columns  ;
//    String prefix ;
//    String [] problems ;
//    boolean notch ;

//    // Configuring scripts for ZDT
//    rows = 3 ;
//    columns = 2 ;
//    prefix = new String("ZDT");
//    problems = new String[]{"ZDT1", "ZDT2","ZDT3", "ZDT4","ZDT6"} ;
//    
//    exp.generateRBoxplotScripts(rows, columns, problems, prefix, notch = false, exp) ;
//    exp.generateRWilcoxonScripts(problems, prefix, exp) ;
//
//    // Configure scripts for DTLZ
//    rows = 3 ;
//    columns = 3 ;
//    prefix = new String("DTLZ");
//    problems = new String[]{"DTLZ1","DTLZ2","DTLZ3","DTLZ4","DTLZ5",
//                                    "DTLZ6","DTLZ7"} ;
//
//    exp.generateRBoxplotScripts(rows, columns, problems, prefix, notch=false, exp) ;
//    exp.generateRWilcoxonScripts(problems, prefix, exp) ;

//     Configure scripts for WFG
//    rows = 3 ;
//    columns = 3 ;
//    prefix = new String("WFG");
//    problems = new String[]{"WFG1","WFG2","WFG3","WFG4","WFG5","WFG6",
//                            "WFG7","WFG8","WFG9"} ;
//
//    exp.generateRBoxplotScripts(rows, columns, problems, prefix, notch=false, exp) ;
//    exp.generateRWilcoxonScripts(problems, prefix, exp) ;

    // Applying Friedman test
//    Friedman test = new Friedman(exp);
//    test.executeTest("EPSILON");
//    test.executeTest("GD");
//    test.executeTest("SPREAD");
//    test.executeTest("IGD");
  } // main
} // StandardStudy


