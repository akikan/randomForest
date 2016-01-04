# はじめに
従来のランダムフォレストでは学習した決定木などを出力しても別の言語で扱うのは困難だといえます。
殆どの場合はそれでも問題はありませんがどうしても別の言語で使わないといけない人のために作成しております。

# 目的
学習した識別機をたいていの言語で利用できるようにすること

# 使用方法
ランダムフォレスト自体の使い方はsample.javaに載せますので、ここではどうやって他の言語で使用するのかについて記述します。
<br>
まず始めにどのように学習した決定木が出力されているかを説明します。  
output("hogehoge.txt",true)で出力されたhogehoge.txtには  
ノード番号,属性の値,属性が入ってる配列の番号,trueのときに遷移するノード番号,falseのときに遷移するノード番号,ラベルの名前|
<br>
のような形式で値が入ります。  
上の形式で該当するものがない場合は0が入ります。
<br>
例えば以下のような決定木があったとします(predictData配列は予測するのに使われるデータとします)

    if(100 < predictData[i]){//ノード番号1
      if(200 < predictData[j]){//ノード番号2
        label1//ノード番号3
      }
      else{
        label2//ノード番号4
      }
    }

このような決定木は
<br>
1,100.0,i,2,0,0|2,200.0,j,3,4,0|3,0.0,0,0,0,label1|4,0.0,0,0,0,label2|
<br>
のように出力されます。  

これを使用するために  
1.hogehoge.txtを１行読み込み  
2.|で文字列を分割する(ノードで分割)  
3.必要な場所のノードを,で分割する(最初は1番目のノード)  
4.一番最後の要素が0でなければその値を返してbreak
5.3.で分割したものの最後の要素以外を数値へと変換して配列にいれる(tempという配列とする)  
6.if(temp[1] < predictData[temp[2]])のif文がtrueならtemp[3]番、falseならtemp[4]番のノードについて3.から始める  
  
これをhogehoge.txtの各行で行い最も多く返された文字列が予測されたデータとなります。  
c++での実装例をsampleフォルダに載せてありますので参考にしてください。
