#include<string>
#include<vector>
#include<sstream>
#include<fstream>
#include<iostream>

using namespace std;
vector<string> split(const string &str, char sep)
{
    vector<string> v;
    stringstream ss(str);
    string buffer;
    while( getline(ss, buffer, sep) ) {
        v.push_back(buffer);
    }
    return v;
}

double stod(const string &s){
	stringstream sss;
	double d;
	sss << s;
	sss >> d;
	return d;
}

string decisionTree(double* dou){
	string str;
	string ans;
	int ak=0, ko=0, ki=0, ha=0, to=0;
	ifstream text("output.txt");
	if(text.fail()){
		return 0;
	}
	
	while(getline(text,str)){
		vector<string> ifs = split(str, '|');
		int count=1;
		int i;
		while(1){
			double temp[5]={0,0,0,0,0};
			int index;
			vector<string> node = split(ifs.at(count-1), ',');
			if(node.at(5)=="0"){
				for(i=0; i<5; i++){
					temp[i] = stod(node.at(i));
				}
				if(temp[1] < dou[(int)temp[2]])	count = temp[3];
				else						count = temp[4];
			}else{
				if(node.at(5) == "ak")
					ak++;
				else if(node.at(5)=="ko")
					ko++;
				else if(node.at(5)=="ki")
					ki++;
				else if(node.at(5)=="ha")
					ha++;
				else if(node.at(5)=="to")
					to++;
				break;
			}
		}

	}
	if(ak >= ko && ak >= ki && ak >= ha && ak >= to)
		ans = "akarennga";
	else if(ko >= ak && ko >= ki && ko >= ha && ko >= to)
		ans = "koukaidou";
	else if(ki >= ko && ki >= ak && ki >= ha && ki >= to)
		ans = "kitajima";
	else if(ha >= ko && ha >= ki && ha >= ak && ha >= to)
		ans = "hakodateyama";
	else if(to >= ko && to >= ki && to >= ha && to >= ak)
		ans = "torapisutinu";
	return ans;
}

int main(int argc, char* argv[]){
	ifstream csv(argv[1]);
	if(!csv)
		return 0;
	string str;
	string ans;
	double i[100];//属性の数
	getline(csv, str)
	vector<string> data_set = split(str,',');
	for(int j=0, jn=data_set.size(); j<jn-1; j++){
		i[j] = stod(data_set.at(j));
	}
		
	ans = decisionTree(i);
	cout << ans << endl;
	
	return 0;
}