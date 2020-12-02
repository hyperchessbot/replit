echo "cleaning"
rm *.class
echo "compiling"
scalac -classpath . -d . day3.scala main.scala
echo "running"
scala -classpath . Main
