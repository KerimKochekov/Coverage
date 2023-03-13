#!/bin/bash

DIR=$( cd "$(dirname "$0")" >/dev/null 2>&1 || exit ; pwd -P )
ROOT_DIR="$DIR"/..
cd "$DIR"/.. || exit

if [ ! -d "$ROOT_DIR"/raw-classes ]; then
    mkdir -p "$ROOT_DIR"/raw-classes
fi

echo "compiling castle.comp5111.example.Subject ..."
javac -d "$ROOT_DIR"/raw-classes "$ROOT_DIR"/src/main/java/comp5111/assignment/cut/Subject.java

# test generation using randoop
# the randomseed can be configured in order to have different test suite
java -classpath .:"$ROOT_DIR"/raw-classes:"$ROOT_DIR"/lib/* randoop.main.Main \
  gentests --testclass comp5111.assignment.cut.Subject --output-limit 300 --randomseed 0 \
  --junit-output-dir "$DIR"/../src/test/randoop4 --junit-package-name comp5111.assignment.cut
