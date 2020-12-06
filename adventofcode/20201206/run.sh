echo "cleaning"
rm *.class
echo "compiling"
scalac -classpath . -d . main.scala
echo "running"
scala -classpath . Main
