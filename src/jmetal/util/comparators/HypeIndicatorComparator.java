/**    
  
* @Title: HypeIndicatorComparator.java  
  
* @Package jmetal.util.comparators  
  
* @Description: TODO(用一句话描述该文件做什么)  
  
* @author Ricardo Shaw  

* @Email  ricardo_shaw@outlook.com    
  
* @date 2016年9月22日 下午2:24:21  
  
* @version V1.0    
  
*/ 
package jmetal.util.comparators;

import java.util.Comparator;

import jmetal.core.Solution;

/**  
  
 * @ClassName: HypeIndicatorComparator  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author Ricardo Shaw  
 * @email  ricardo_shaw@outlook.com
 * @date 2016年9月22日 下午2:24:21  
  
 *  
    
  
 */
public class HypeIndicatorComparator implements Comparator {

	/* (非 Javadoc)  
	  
	 * <p>Title: compare</p>  
	  
	 * <p>Description: </p>  
	  
	 * @param o1
	 * @param o2
	 * @return  
	  
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)  
	  
	 */
	public int compare(Object o1, Object o2) {
		 if (o1==null)
		      return 1;
		    else if (o2 == null)
		      return -1;
		    
		    double value1 = ((Solution)o1).getHypeIndicatorValue_();
		    double value2 = ((Solution)o2).getHypeIndicatorValue_();
		    if (value1 < value2) {
		      return -1;
		    } else if (value1 > value2) {
		      return 1;
		    }
		    
		    return 0;
	}

}
