echo "cleaning"
rm *.class
echo "compiling"
scalac -deprecation -classpath . -d . main2.scala
echo "running"
scala -classpath . Main
