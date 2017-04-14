/**    
  
* @Title: JunitTest.java  
  
* @Package com.x1a0ch1.test  
  
* @Description: TODO(��һ�仰�������ļ���ʲô)  
  
* @author Ricardo Shaw  

* @Email  ricardo_shaw@outlook.com    
  
* @date 2016��9��12�� ����8:52:22  
  
* @version V1.0    
  
*/ 
package com.Ricardo.core;



import java.util.Iterator;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;

import org.junit.Test;

/**  
  
 * @ClassName: JunitTest  
 * @Description: TODO(������һ�仰��������������)  
 * @author Ricardo Shaw  
 * @email  ricardo_shaw@outlook.com
 * @date 2016��9��12�� ����8:52:22  
  
 *  
    
  
 */
public class JunitTest {
	
	/**  
	  
	* @Title: test  
	  
	* @Description: TODO(������һ�仰�����������������)  
	  
	* @param     �趨�ļ�  
	  
	* @return void    ��������  
	  
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
		
		Solution oneplus = solutionList_.get(0);  //Solution oneplus ������solutionList_�е�Solution one �ĵ�ַ
		
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
			System.out.println("ID+"+i+"�Ƿ���һ������"+solutionList_.get(i).equals(solutionList1_.get(i)));
			
			
		}
	}


}
