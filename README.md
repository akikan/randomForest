# はじめに
従来のランダムフォレストでは学習した決定木などを出力しても別の言語で扱うのは困難だといえます。
殆どの場合はそれでも問題はありませんがどうしても別の言語で使わないといけない人のために作成しております。

# 目的
学習した識別機をたいていの言語で利用できるようにすること

# 使用方法
ランダムフォレスト自体の使い方はsample.javaに載せますので、ここではどうやって他の言語で使用するのかについて記述します。
<br>
まず始めにどのように学習した決定木が出力されているかを説明します。
<br>
例えば以下のような決定木があったとします(predictData配列は予測するのに使われるデータとします)
<br>if(100 < predictData[i]){//ノード番号1
<br>  if(200 < predictData[j]){//ノード番号2
<br>    label1//ノード番号3
<br>  }
<br>  else{
<br>    label2//ノード番号4
<br>  }
<br>}
<br>
このような決定木は
<br>
1,100.0,i,2,0,0|2,200.0,j,3,4,0|3,0.0,0,0,0,label1|4,0.0,0,0,0,label2|
<br>
のように出力されます