/**
 *
 */
package randomForest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class randomForest {

	String predict(ArrayList<ArrayList<Double>> preData){
		 HashSet<String> temp = new HashSet<String>(trainLabels);//labelsの重複なしの数を取得
		 ArrayList<String> labelList = new ArrayList<String>(temp);
		 HashMap<String, Integer> votes = new HashMap<String, Integer>();
		 for(int i=0, in=labelList.size(); i<in; i++){
			 votes.put(labelList.get(i),0);
		 }

		 int num;
		for(int j=0; j<treeNum; j++){
			String vote = trees[j].predict(preGet);
		 	num = votes.get(vote);
		 	num++;
		 	votes.put(vote, num);
		 }
		 int max=0;
		 String result="";
		 int sum;
		 for(int j=0,jn=labelList.size(); j<jn; j++){
			 sum = votes.get(labelList.get(j));
			 if(max < sum){
				 max = sum;
				 result = labelList.get(j);
			 }
		 }
		 return result;
	}

	void output(String filename){
		File file;
		PrintWriter pw;
		try{
			file = new File(filename);
			pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		}catch(Exception e){
			return ;
		}
		for(int i=0; i<treeNum; i++){
			trees[i].output(pw);
			pw.println();
		}
	}

	void learn(){
		trees = new DecisionTree[treeNum];
		for(int i=0; i<treeNum; i++){
			trees[i] = new DecisionTree(trainData, trainLabels, selectLabelNumber);
		}
	}

	int treeNum = 1;
	ArrayList<ArrayList<Double>> preData = new ArrayList<ArrayList<Double>>();
	ArrayList<String> preLabels = new ArrayList<String>();
	ArrayList<ArrayList<Double>> trainData;
	ArrayList<String> trainLabels;
	DecisionTree[] trees;
	int selectLabelNumber;

	randomForest(int treeNum, ArrayList<ArrayList<Double>> trainData, ArrayList<String> trainLabels){
		this.treeNum = treeNum;
		this.trainData = trainData;
		this.trainLabels = trainLabels;
		selectLabelNumber = Math.sqrt(trainLabels.size());
	}
}
