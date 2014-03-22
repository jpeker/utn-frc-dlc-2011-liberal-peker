rem ## Copyright 2003, Laurent GIRARD
rem ## All Rights Reserved.  Use is subject to license terms.
rem ## 
rem ## See the file "COPYING" for information on usage and
rem ## redistribution of this file, and for a DISCLAIMER OF ALL 
rem ## WARRANTIES.
rem ##
rem ##
rem ##	The JAHP main make file
rem ##
rem ##	Makefile	-	The make file.
rem ##
rem ##
rem ## Update by Maxime MORGE July 21th, 2003 
@echo off 
cls

SET JDK_PATH=D:\morge\prog
SET JAHP_PATH=D:\morge\JAHP2.1
SET JAMA=%JAHP_PATH%\utils
SET JAHP_SOURCE=%JAHP_PATH%\src
SET DESTINATION_PATH=%JAHP_PATH%\classes
SET JAHP_CLASSPATH=-classpath %JAMA%
SET JAHP_JAR_FILE=%JAHP_PATH%\jahp.2.1.jar 
SET DESTINATION_DOC_PATH=%JAHP_PATH%\\doc

rem all you need is ok
rem ############################
rem # Program names and options
rem ############################
SET JAVA=%JDK_PATH%\bin\java
SET JAR=%JDK_PATH%\bin\jar -cvf
SET JAVAC=%JDK_PATH%\bin\javac -nowarn -deprecation -d %DESTINATION_PATH%
SET JAVADOC=%JDK_PATH%\bin\javadoc -private -author -version -sourcepath %JAHP_SOURCE% -classpath %JAHP_CLASSPATH% -d %DESTINATION_DOC_PATH%
SET RMIC=%JDK_PATH%\bin\rmic -classpath %DESTINATION_PATH% -d %DESTINATION_PATH% -keepgeneratedSET JAR=%JDK_PATH%\bin\jar cvf

rem ############################
rem # FEATURES
rem ############################
rem comment verifier que le nb de param est strictement egal à 1?
rem IF %1=="" goto usage
IF %1==install goto install
IF %1==archive goto archive
IF %1==run goto run
IF %1==javadoc goto javadoc
IF %1==clean goto clean
IF %1==cleanall goto cleanall

:usage
echo usage:
echo do param
echo params:  install, archive, run, javadoc, clean, cleanall

goto fin


:install
echo ****************
echo * JCL  install *
echo ****************
SET CMD=%JAVAC% %JAHP_CLASSPATH% %JAHP_SOURCE%\adt\*.java %JAHP_SOURCE%\gui\*.java
goto exec

:archive
echo ****************
echo * JCL  archive *
echo ****************
SET CMD=%JAR% %JAHP_JAR_FILE% %JAHP_SOURCE%\adt\*.java %JAHP_SOURCE%\gui\*.java
goto exec

:run
rem Beware, the separator is no more a semi colon!!!
SET CMD=%JAVA% -classpath %JAMA%;%DESTINATION_PATH% gui.JAHP
goto exec

:javadoc 
echo ********************************
echo * Making JavaDoc Documentation *
echo ********************************
SET CMD=%JDK_PATH%\bin\javadoc -private -author -version -sourcepath %JAHP_SOURCE%\adt\*.java %JAHP_SOURCE%\gui\*.java -d  %DESTINATION_DOC_PATH%
goto exec

:exec
echo %CMD%
%CMD%
goto fin

:clean
echo **************************************
echo * Cleaning the destination directory *
echo **************************************
del /q %DESTINATION_PATH%\*.*
goto fin

:cleanall
echo ********************************
echo * Cleaning all the directories *
echo ********************************
cd %JAHP%del /q %DESTINATION_DOC_PATH%\*.*
goto clean

:fin
echo Program terminated correctly, have a good day@pause

@pause