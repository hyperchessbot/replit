echo "compiling"
scalac -classpath . -d . day3.scala
scalac -classpath . -d . main.scala
echo "running"
scala -classpath . Main
