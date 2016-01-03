/**
 * 
 */
package randomForest;

import java.util.ArrayList;

/**
 *　@author b1013169 赤坂尚衡
 *
 */
public class giniSet {
	int att;//属性の番号
	double separate;//決定木分岐の条件式
	ArrayList<Integer> L = new ArrayList<Integer>();//true判定されたほうのインデックスの集合
	ArrayList<Integer> R = new ArrayList<Integer>();//false判定されたほうのインデックスの集合
	
}
