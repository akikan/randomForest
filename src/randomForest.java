package randomForest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class randomForest {

	/*
	 * 関数名:predict
	 * 引数:ArrayList<Double> preData
	 *		予測したいデータセットを入力します
	 * 詳細：学習した決定木毎に引数を与えて予測します
	 *		最も予測された回数の多いラベルを出力します
	 */
	String predict(ArrayList<Double> preData){
		HashSet<String> temp = new HashSet<String>(trainLabels);//labelsの重複なしの数を取得
		ArrayList<String> labelList = new ArrayList<String>(temp);
		HashMap<String, Integer> votes = new HashMap<String, Integer>();
		for(int i=0, in=labelList.size(); i<in; i++){
			 votes.put(labelList.get(i),0);
		}
		//決定木毎に予測、各ラベルが何回出力されたかを計測
		int num;
		for(int j=0; j<treeNum; j++){
			String vote = trees[j].predict(preData);
		 	num = votes.get(vote);
		 	num++;
		 	votes.put(vote, num);
		}
		//最も予測された回数が多いラベルを出力
		int sum,max=0;
		String result="";
		for(int j=0,jn=labelList.size(); j<jn; j++){
			sum = votes.get(labelList.get(j));
			if(max < sum){
				max = sum;
				result = labelList.get(j);
			}
		}
		return result;
	}

	/*
	 * 関数名:output
	 *　引数:filename
	 *		ファイル名を引数として渡してください
	 *		flag
	 *		trueのときに詳細に示す形式で出力します
	 *		falseのときはノードのif文を出力します
	 * 詳細:各決定木のノードの状態を以下の形式で出力します
	 *		区切り文字に|を使用しています
	 *		ノードの番号,属性の番号,判別する値,trueの時に移動するノード番号,falseの時に移動するノード番号,どこにも遷移しない場合はラベル名(それ以外は0) |
	 */
	void output(String filename, Boolean flag){
		File file;
		PrintWriter pw;
		try{
			file = new File(filename);
			pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		}catch(Exception e){
			return ;
		}
		for(int i=0; i<treeNum; i++){
			if(flag){
				trees[i].output(pw);
			}
			else{
				trees[i].output_if(pw);
			}
			pw.println();
		}
	}


	/*
	 * 関数名;learn
	 * 詳細:学習します。treeNum,selectLabelNumを設定した後に使用して、その後にoutputやpredictといった関数を使用してください
	 */
	void learn(){
		trees = new DecisionTree[treeNum];
		for(int i=0; i<treeNum; i++){
			trees[i] = new DecisionTree(trainData, trainLabels, selectLabelNum);
		}
	}

	int treeNum = 10;
	ArrayList<ArrayList<Double>> preData = new ArrayList<ArrayList<Double>>();
	ArrayList<String> preLabels = new ArrayList<String>();
	ArrayList<ArrayList<Double>> trainData;
	ArrayList<String> trainLabels;
	DecisionTree[] trees;
	Double selectLabelNum;

	randomForest(ArrayList<ArrayList<Double>> trainData, ArrayList<String> trainLabels){
		this.trainData = trainData;
		this.trainLabels = trainLabels;
		selectLabelNum = Math.sqrt(trainLabels.size());
		System.out.println(selectLabelNum);//*/
	}
}
