/**    
  
* @Title: JunitTest.java  
  
* @Package com.x1a0ch1.test  
  
* @Description: TODO(用一句话描述该文件做什么)  
  
* @author Ricardo Shaw  

* @Email  ricardo_shaw@outlook.com    
  
* @date 2016年9月12日 下午8:52:22  
  
* @version V1.0    
  
*/ 
package com.Ricardo.core;



import java.util.Iterator;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;

import org.junit.Test;

/**  
  
 * @ClassName: JunitTest  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author Ricardo Shaw  
 * @email  ricardo_shaw@outlook.com
 * @date 2016年9月12日 下午8:52:22  
  
 *  
    
  
 */
public class JunitTest {
	
	/**  
	  
	* @Title: test  
	  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	  
	* @param     设定文件  
	  
	* @return void    返回类型  
	  
	* @throws  
	  
	*/  
	@Test
	public void test() {

		Solution one = new Solution();
		Solution two = new Solution();
		Solution three = new Solution();
		Solution four = new Solution(one);
		
		SolutionSet solutionList_ = new SolutionSet();


		solutionList_.add(one);
		solutionList_.add(two);
		solutionList_.add(three);
		
		
		SolutionSet solutionList1_ = new SolutionSet(solutionList_);
		
		Solution oneplus = solutionList_.get(0);  //Solution oneplus 引用了solutionList_中的Solution one 的地址
		
		oneplus.setFitness(0.01);
		
		solutionList_.get(0).setFitness(0.02);
		
		System.out.println(oneplus.equals(one));
		System.out.println(four.equals(one));
		System.out.println(one.equals(solutionList_.get(0)));
		System.out.println(oneplus.equals(solutionList_.get(0)));
		System.out.println(oneplus.hashCode());
		System.out.println(one.hashCode());
		System.out.println(oneplus);
		System.out.println(solutionList_);
		
		
		for(int i = 0 ; i < 3 ; i++){
			System.out.println("ID+"+i+"是否是一个对象："+solutionList_.get(i).equals(solutionList1_.get(i)));
			
			
		}
	}


}
