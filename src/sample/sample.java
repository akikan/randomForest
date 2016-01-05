package sample;

import java.util.ArrayList;
import randomForest.RandomForest;

public class sample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
		ArrayList<String> label = new ArrayList<String>();
		label.add("プロ");
		label.add("プロ");
		label.add("趣味");
		label.add("プロ");
		label.add("プロ");
		label.add("趣味");
		label.add("趣味");
		label.add("プロ");

		ArrayList<Double> one = new ArrayList<Double>();
		one.add(5.0);
		one.add(1500.0);
		ArrayList<Double> two = new ArrayList<Double>();
		two.add(10.0);
		two.add(2500.0);
		ArrayList<Double> three = new ArrayList<Double>();
		three.add(20.0);
		three.add(2000.0);
		ArrayList<Double> four = new ArrayList<Double>();
		four.add(20.0);
		four.add(4000.0);
		ArrayList<Double> five = new ArrayList<Double>();
		five.add(25.0);
		five.add(3000.0);
		ArrayList<Double> six = new ArrayList<Double>();
		six.add(30.0);
		six.add(500.0);
		ArrayList<Double> seven = new ArrayList<Double>();
		seven.add(35.0);
		seven.add(2500.0);
		ArrayList<Double> eight = new ArrayList<Double>();
		eight.add(40.0);
		eight.add(3500.0);

		data.add(one);
		data.add(two);
		data.add(three);
		data.add(four);
		data.add(five);
		data.add(six);
		data.add(seven);
		data.add(eight);

		RandomForest RF = new RandomForest(data,label);
		RF.treeNum=10;//決定木の本数を任意で選択、デフォルトは10
		//RF.selectLabelNum = Math.sqrt(label.size());//デフォルトは√(ラベルの数)
		RF.learn();
		try{
			RF.output("sample.txt",true);
		}catch(Exception e){
			System.out.println("error");//*/
		}
	}

}
