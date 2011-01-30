#!/bin/sh -e
#
# This batch file builds and preverifies the code for the demos.
# it then packages them in a JAR file appropriately.
#
DEMO=LoroDux
LIB_DIR=../../../lib
CLDCAPI=${LIB_DIR}/cldcapi11.jar
MIDPAPI=${LIB_DIR}/midpapi21.jar
JSR=${LIB_DIR}/jsr179.jar:$LIB_DIR/jsr082.jar

PREVERIFY=../../../bin/preverify

PATHSEP=":"

JAVAC=javac
JAR=jar

if [ -n "${JAVA_HOME}" ] ; then
  JAVAC=${JAVA_HOME}/bin/javac
  JAR=${JAVA_HOME}/bin/jar
fi

#
# Make possible to run this script from any directory'`
#
cd `dirname $0`

echo "Creating directories..."
mkdir -p ../tmpclasses
mkdir -p ../classes

echo "Compiling source files..."

${JAVAC} \
    -bootclasspath ${CLDCAPI}${PATHSEP}${MIDPAPI}${PATHSEP}${JSR} \
    -source 1.3 \
    -target 1.3 \
    -d ../tmpclasses \
    -classpath ../tmpclasses $LIBDIR \
    `find ../src -name '*'.java`

echo "Preverifying class files..."

${PREVERIFY} \
    -classpath ${CLDCAPI}${PATHSEP}${MIDPAPI}${PATHSEP}../tmpclasses \
    -d ../classes \
    ../tmpclasses

echo "Jaring preverified class files..."
${JAR} cmf MANIFEST.MF ${DEMO}.jar nodes -C ../classes  .

if [ -d ../res ] ; then
  ${JAR} uf ${DEMO}.jar -C ../res .
fi

echo
echo "Don't forget to update the JAR file size in the JAD file!!!"
echo
