/**    
  
* @Title: Test.java  
  
* @Package com.x1a0ch1.test  
  
* @Description: TODO(用一句话描述该文件做什么)  
  
* @author Ricardo Shaw  

* @Email  ricardo_shaw@outlook.com    
  
* @date 2016年9月6日 下午8:23:23  
  
* @version V1.0    
  
*/ 
package com.Ricardo.core;

import jmetal.core.SolutionSet;
import jmetal.util.archive.CrowdingArchive;

/**  
  
 * @ClassName: Test  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author Ricardo Shaw  
 * @email  ricardo_shaw@outlook.com
 * @date 2016年9月6日 下午8:23:23  
  
 *  
    
  
 */
public class Test {
	static int x = 10 ; 
	static {x += 5;}
	public static void main(String[] args){
		System.out.print("x= "+ x);
	}
	static {x /= 3;};
	
}
