echo "cleaning"
rm *.class
echo "compiling"
scalac -deprecation -classpath . -d . main.scala
echo "running"
scala -classpath . Main
