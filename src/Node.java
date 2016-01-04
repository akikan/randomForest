package randomForest;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Node {
	ArrayList<ArrayList<Double>> data;
	boolean leaf = false;//葉ノードならTRUE
	double brunchCondition = 0;//分岐の条件
	String result=null;
	Node nodeL = null;
	Node nodeR = null;
	giniSet set = new giniSet();
	int id;

	Node(ArrayList<ArrayList<Double>> data, ArrayList<String> labels){
		this.data = new ArrayList<ArrayList<Double>>(data);
		HashSet<String> check = new HashSet<String>(labels);
		//与えられたトレーニングデータがすべて同じラベルなら葉ノード判定のleafをtrueに
		//全部trueならfalseに
		if(check.size()==1){
			leaf = true;
			result = labels.get(0);
		}
		//もし葉ノードじゃなければ
		if(!leaf){
			set = calcGini(labels);
			brunchCondition = set.separate;//決定木の条件式

			ArrayList<ArrayList<Double>> dataL = new ArrayList<ArrayList<Double>>();
			ArrayList<ArrayList<Double>> dataR = new ArrayList<ArrayList<Double>>();
			ArrayList<String> labelsL = new ArrayList<String>();
			ArrayList<String> labelsR = new ArrayList<String>();
			if(set.L.size() > 0){
				for(int i=0, in=set.L.size(); i<in; i++){
					dataL.add(data.get(set.L.get(i)));
					labelsL.add(labels.get(set.L.get(i)));
				}
				nodeL = new Node(dataL, labelsL);
			}
			if(set.R.size() > 0){
				for(int i=0,in=set.R.size(); i<in; i++){
					dataR.add(data.get(set.R.get(i)));
					labelsR.add(labels.get(set.R.get(i)));
				}
				nodeR = new Node(dataR, labelsR);
			}

		}

	}

	//ジニ係数を用いてどの属性、どのデータで分ければいいかを計算
	private giniSet calcGini(ArrayList<String> labels){
		giniSet min = new giniSet();//返却用class宣言
		HashSet<String> temp = new HashSet<String>(labels);//labelsの重複なしの数を取得
		ArrayList<String> labelList = new ArrayList<String>(temp);
		HashMap<String, Integer> labR = new HashMap<String, Integer>();
		HashMap<String, Integer> labL = new HashMap<String, Integer>();
		for(int i=0,in=labelList.size(); i<in; i++){
			labR.put(labelList.get(i),0);
			labL.put(labelList.get(i),0);
		}

		double minGini=1,  allGini=0;
		float data_size = (float)data.size();
		//全走査して最小のジニ係数を求める
		for(int i=0,in=data.size(); i<in; i++){
			for(int j=0,jn=data.get(i).size(); j<jn; j++){
				double countR = 0.0, countL=0.0;
				double tempRGini=1, tempLGini=1;
				double nowData = data.get(i).get(j);
				//各属性でtrueとfalseが何個あるかを調べるfor文
				for(int k=0,kn=data.size(); k<kn; k++){
					if( nowData < data.get(k).get(j)){
						countL++;
						int num = labL.get(labels.get(k));
						num++;
						labL.put( labels.get(k) , num );
					}else{
						countR++;
						int num = labR.get(labels.get(k));
						num++;
						labR.put( labels.get(k) , num );
					}
				}

				//ジニ値計算
				for(int l=0,ln=labelList.size(); l<ln; l++){
					if(countR != 0){
						tempRGini -= Math.pow( labR.get(labels.get(l))/countR,2);
					}
					if(countL != 0){
						tempLGini -= Math.pow( labL.get(labels.get(l))/countL,2);
					}
				}

				//全体のジニ値
				allGini = ( tempLGini * countL/data_size ) + ( tempRGini * countR/data_size);
				if(allGini < minGini){
					min.att = j;
					min.separate=nowData;
					minGini = allGini;
				}
				//初期化
				for(int m=0,mn=labelList.size(); m<mn; m++){
					labR.put(labelList.get(m),0);
					labL.put(labelList.get(m),0);
				}


			}
		}
		//System.out.println(min.separate);
		//RとLに入るインデックスリストを作成
		for(int i=0,in=data.size(); i<in; i++){
			if( min.separate < data.get(i).get(min.att)){
				min.L.add(i);
			}
			else{
				min.R.add(i);
			}
		}
		return min;
	}

	String predict(ArrayList<Double> preData){
		String pre;
		if(leaf){
			pre =  result;
		}
		else{
			if(brunchCondition < preData.get(set.att)){
				pre = nodeL.predict(preData);
			}
			else{
				pre = nodeR.predict(preData);
			}
		}
		return pre;
	}

	void output_if(int rank, PrintWriter pw){
		for(int i=0; i<rank; i++)
			pw.print("    ");
		if(!leaf){
			pw.println("if("+brunchCondition+" < input["+set.att+"]){");
			pw.flush();
		}else{
			pw.println(result +"++;");
			pw.flush();
		}

		if(set.L.size() > 0){
			int num = rank+1;
			nodeL.output_if(num, pw);
		}
		if(set.R.size() > 0){
			for(int i=0; i<rank; i++){
				pw.print("    ");
			}
			pw.println("}else{");//pw.println("}else{");
			pw.flush();
			int num = rank+1;
			nodeR.output_if(num, pw);
		}

		if(!leaf){
			for(int i=0; i<rank; i++){
				pw.print("    ");
			}
			pw.println("}");
			pw.flush();
		}
	}

	void output(PrintWriter pw){
		if(!leaf){
			if(set.L.size() > 0 && set.R.size() > 0){
				pw.print(id+","+brunchCondition+","+set.att+","+nodeL.id+","+nodeR.id+","+0+"|");
				pw.flush();
			}
			else if(set.L.size() > 0){
				pw.print(id+","+brunchCondition+","+set.att+","+nodeL.id+","+0+","+0+"|");
				pw.flush();
			}
			else{
				pw.print(id+","+brunchCondition+","+set.att+","+0+","+nodeR.id+","+0+"|");
				pw.flush();
			}
		}else{
			pw.print(id+","+brunchCondition+","+set.att+","+0+","+0+","+result+"|");
			pw.flush();
		}

		if(set.L.size() > 0){
			nodeL.output(pw);
		}
		if(set.R.size() > 0){
			nodeR.output(pw);
		}
	}

	int setId(int rank){
		id = rank;

		if(nodeL != null){
			rank = nodeL.setId(++rank);
		}
		if(nodeR != null){
			rank = nodeR.setId(++rank);
		}/*
		if(nodeL == null && nodeR == null){
			rank++;
		}//*/

		return rank;
	}
}
