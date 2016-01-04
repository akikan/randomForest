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
	DecisionTree(ArrayList<ArrayList<Double>> data, ArrayList<String> labels, Double selectLabel){
		Random rand = new Random();
		double in = (double)selectLabel;
		for(int i=0; i<in; i++){
			int ran=0;
			ran = rand.nextInt( labels.size());
			list.add(ran);
			this.labels.add(labels.get(ran));
			this.data.add(data.get(ran));
		}

		node = new Node(this.data, this.labels);
		node.setId(1);
	}

	String predict(ArrayList<Double> preData){
		return node.predict(preData);
	}

	void output(PrintWriter pw){
		node.output(pw);
	}

	void output_if(PrintWriter pw){
		node.output_if(0,pw);
	}
}
