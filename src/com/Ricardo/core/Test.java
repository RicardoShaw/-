/**    
  
* @Title: Test.java  
  
* @Package com.x1a0ch1.test  
  
* @Description: TODO(��һ�仰�������ļ���ʲô)  
  
* @author Ricardo Shaw  

* @Email  ricardo_shaw@outlook.com    
  
* @date 2016��9��6�� ����8:23:23  
  
* @version V1.0    
  
*/ 
package com.Ricardo.core;

import jmetal.core.SolutionSet;
import jmetal.util.archive.CrowdingArchive;

/**  
  
 * @ClassName: Test  
 * @Description: TODO(������һ�仰��������������)  
 * @author Ricardo Shaw  
 * @email  ricardo_shaw@outlook.com
 * @date 2016��9��6�� ����8:23:23  
  
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
