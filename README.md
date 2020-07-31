# ir-lastAssignment2020
大学の授業（情報アクセスと知的情報）の最終課題
このプログラムは，指定した時期のアニメ作品一覧を取得し，その作品の評価情報を表示する。  
評価情報は，Annictの作品全体に対する4段階評価とTwitterのTweetから感情分析を行った結果に基づいて表示する。

## クラスについて  
**App.java**　  
|- 実行する操作を選択・実行します  

**LoadAnnictWorksApi.java**  
|- Annictから作品情報を取得します。  
|- 4半期ごとに指定して取得  

**LoadAnnictReviewApi.java**  
|- Annictからレビューを取得します。  
|- LoadAnnictWorksApiで取得したAnnictの作品IDを指定して取得

**twitterLoad/WordSearch.java**  
|- Annictから取得したハッシュタグをもとに，ツイートを取得します。  
|- TwitterAPIの制限により取得可能件数に制限があり，取得可能なものは過去7日間以内にツイートされたものです。  

**CalcAnnictReviewRating**  
|- Annictから取得した4段階評価の文字列を元に数値化し，平均値を計算します。

**sentimentAnalysis/TweetAnalyze.java**  
|- 取得したツイートから感情分析を行います。

**CalScore.java**  
|- Annictから取得したレビューの評価情報とツイートの感情分析によって得られた評価情報を用いてスコアを出します。

## ツイートを格納するデータベースについて
現状，データを挿入する機能のみが実装されているため，ツイート取得を実行する度に，続きのレコードからツイートが挿入されます。
ツイート取得をする処理を実行する場合は，tweet2テーブルのレコードを空にしてから実行します。  
ツイートの評価結果を挿入するanalyzeResultテーブルについても同様です。