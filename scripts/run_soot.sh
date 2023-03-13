#!/bin/bash

DIR=$( cd "$(dirname "$0")" >/dev/null 2>&1 || exit ; pwd -P )
ROOT_DIR="$DIR"/..
cd "$ROOT_DIR" || exit

if [ ! -d "$ROOT_DIR"/target/classes ]; then
    mkdir -p "$ROOT_DIR"/target/classes
fi

rm -r "$ROOT_DIR"/target/classes

find "$ROOT_DIR"/src/main/java -name "*.java" -print0 | xargs -0 javac -classpath .:"$ROOT_DIR"/lib/* -d "$ROOT_DIR"/target/classes
java -classpath .:"$ROOT_DIR"/lib/*:"$ROOT_DIR"/target/classes comp5111.assignment.Assignment1 2 RegressionTest0

