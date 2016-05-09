set class=E:\NLP\trunk\mwdumper\target\mwdumper-1.25.jar;E:\NLP\mysql-connector-java-5.1.21.jar
set data="C:\Users\Kiki\Downloads\viwiki-20160305\viwiki-20160305-pages-articles.xml"
java -client -classpath %class% org.mediawiki.dumper.Dumper "--output=mysql://127.0.0.1/wikidb?user=root&password=manh980838" "--format=sql:1.5" "--filter=notalk" %data%
pause