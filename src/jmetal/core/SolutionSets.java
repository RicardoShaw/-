/**    
  
* @Title: SolutionSets.java  
  
* @Package jmetal.core  
  
* @Description: TODO(��һ�仰�������ļ���ʲô)  
  
* @author Ricardo Shaw  

* @Email  ricardo_shaw@outlook.com    
  
* @date 2016��9��14�� ����4:48:25  
  
* @version V1.0    
  
*/ 
package jmetal.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import jmetal.util.Configuration;

/**  
  
 * @ClassName: SolutionSets  
 * @Description: TODO(������һ�仰��������������)    
 *  
  
 */
public class SolutionSets implements Serializable {
	
	// @maxfronts_ ��SolutionSets�����Ĳ��� 
	private int maxfronts_ = 0;
	
	
//	//@capacity_ ��SolutionSets�������������solution
//	private int capacity_ = 0; 

	//@size_ ��SolutionSets����solution�ĸ���
	private int size_ = 0;
	
	protected final List<SolutionSet> solutionsetsList_;
	
	//Ĭ�Ϲ�����
	public SolutionSets(){
		solutionsetsList_ = new ArrayList<SolutionSet>();
		this.maxfronts_ = 200;
	}
	
	//�Զ��幹����
	public SolutionSets(int maxfronts_){
		this.maxfronts_ = maxfronts_;
		solutionsetsList_ = new ArrayList<SolutionSet>();
	}
	
	public boolean add(SolutionSet solutionset){
		if(solutionsetsList_.size() == maxfronts_){
			Configuration.logger_.severe("The curfronts_ is equal to manfronts_");
			Configuration.logger_.severe("Maxfronts is :" + maxfronts_);
			Configuration.logger_.severe("\t Curfronts is :" + this.solutionsetsList_.size());
			return false;
		}
		this.size_ += solutionset.size();
		solutionsetsList_.add(solutionset);
		return true;
	}
	
	public boolean add(int index,SolutionSet solutionset){
		solutionsetsList_.add(index, solutionset);
		this.size_ += solutionset.size();
		return true;
	}
	
	public SolutionSet get(int i){
		if(i >= solutionsetsList_.size()){
			throw new IndexOutOfBoundsException("Index out of Bound "+ i);
		}
		return solutionsetsList_.get(i);
	}

	public int getMaxfronts_() {
		return maxfronts_;
	}

	
//	//������Ҫ����д
//	public void sort(Comparator comparator){
//		if(comparator == null){
//			Configuration.logger_.severe("No criterium for comparing exist");
//			return;
//		}
//		Collections.sort(solutionsetsList_, comparator);
//	}
//	
//	int indexBest(Comparator comparator){
//		if((solutionsetsList_ == null) || (this.solutionsetsList_.isEmpty())){
//			return -1;
//		}
//		int index = 0;
//	}
	
	public int size(){
		return solutionsetsList_.size();
	}
	
	public int getSize(){
		return size_;
	}
	

	public void setSize_(int size_) {
		this.size_ = size_;
	}

	public void clear(){
		solutionsetsList_.clear();
	}
	
	
	public void remove(int i){
		if(i > solutionsetsList_.size()-1){
			Configuration.logger_.severe("Front id :" +this.size());
		}
		size_ -= solutionsetsList_.get(i).size();
		solutionsetsList_.remove(i);
	}
	
	
	public void remove(int i, int j){
		if(i>solutionsetsList_.size()-1)
			Configuration.logger_.severe("Fronts id����" + this.size());
		solutionsetsList_.get(i).remove(j);
		--size_;
	}
	
	
	
	public Iterator<SolutionSet> iterator(){
		return solutionsetsList_.iterator();
	}
	
	public SolutionSets union(SolutionSets solutionSets){
		int newSize  = this.size() + solutionSets.size();
		if ( newSize < maxfronts_)
			newSize = maxfronts_;
		SolutionSets union = new SolutionSets(newSize);
		for(int i = 0 ; i < this.size() ; i++){
			union.add(this.get(i));
		}
		
		for(int i = this.size() ; i < (this.size()+solutionSets.size()); i++ ){
			union.add(solutionSets.get(i-this.size()));
		}
		return  union;
	}
	
	public void replace(int position, SolutionSet solutionSet){
		if(position > this.solutionsetsList_.size()){
			solutionsetsList_.add(solutionSet);
		}
		solutionsetsList_.remove(position);
		solutionsetsList_.add(position,solutionSet);
	}
}
