/**
 *
 */
package randomForest;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;



public class DecisionTree {
	ArrayList<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
	ArrayList<String> labels = new ArrayList<String>();
	ArrayList<Integer> list = new ArrayList<Integer>();
	String predict=null;
	Node node=null;
	DecisionTree(ArrayList<ArrayList<Double>> data, ArrayList<String> labels, int selectLabel){
		Random rand = new Random();
	//	double in = Math.sqrt(labels.size());
		double in = (double)selectLabel;
		for(int i=0; i<in; i++){
			int ran=0;
//			do{
				ran = rand.nextInt( labels.size());
//			}while(list.indexOf(ran)!=-1);
			list.add(ran);
			this.labels.add(labels.get(ran));
			this.data.add(data.get(ran));
		}

		node = new Node(this.data, this.labels);
	}

	String predict(ArrayList<Double> preData){
		return node.predict(preData);
	}
	void output(PrintWriter pw){
		node.setId(1);
		node.output(pw);
	}
}
