## Copyright 2002, Maxime MORGE 
## All Rights Reserved.  Use is subject to license terms.
## 
## See the file "COPYING" for information on usage and
## redistribution of this file, and for a DISCLAIMER OF ALL 
## WARRANTIES.
##
##
##	The JAHP main make file
##
##	Makefile	-	The make file.
##
##
## Update by Maxime MORGE February 14th, 2003 
#################
# Path variables
#################

JAHP_PATH = .

JDK_PATH = /usr/java/j2sdk1.4.2

JAHP_SOURCE = $(JAHP_PATH)/src

JAMA =  $(JAHP_PATH)/utils/

JAHP_CLASSPATH = -classpath $(JAHP_SOURCE):$(DESTINATION_PATH):$(JAMA)

DESTINATION_DOC_PATH = $(JAHP_PATH)/doc

DESTINATION_PATH = $(JAHP_PATH)/classes

JAHP_JAR_FILE = $(JAHP_PATH)/../jahp.jar #all you need is ok
############################
# Program names and options
############################

JAVAC   = $(JDK_PATH)/bin/javac -nowarn -deprecation -d $(DESTINATION_PATH)
JAVADOC = $(JDK_PATH)/bin/javadoc -private -author -version -sourcepath $(JAHP_SOURCE) -classpath $(JAHP_CLASSPATH) -d $(DESTINATION_DOC_PATH)
RMIC	= $(JDK_PATH)/bin/rmic -classpath $(DESTINATION_PATH) -d $(DESTINATION_PATH) -keepgenerated
JAR     = $(JDK_PATH)/bin/jar cvf

install:
	@echo "   ****************"
	@echo "   * JCL  install *"
	@echo "   ****************" 
	$(JAVAC) $(JAHP_CLASSPATH) $(JAHP_SOURCE)/adt/*.java  $(JAHP_SOURCE)/gui/*.java 

archive:
	@echo "   ****************"
	@echo "   * JCL  archive *"
	@echo "   ****************"
	$(JAR) $(JAHP_JAR_FILE) $(JAHP_SOURCE)/adt/*.java  $(JAHP_SOURCE)/gui/*.java 

javadoc: 
	@echo "********************************"
	@echo "* Making JavaDoc Documentation *"
	@echo "********************************"
	$(JDK_PATH)/bin/javadoc -private -author -version -sourcepath $(JAHP_SOURCE)/adt/*.java  $(JAHP_SOURCE)/gui/*.java   -d  $(DESTINATION_DOC_PATH)


clean:
	@echo "***********************************"
	@echo "* Cleaning the source directories *"
	@echo "***********************************"
	find $(JAHP_SOURCE)/adt   \( -name "*.bak" -o -name "*.BAK" -o -name "*~"  -o -name "core" -o -name "*.class" \) -exec rm {} \;
	find $(JAHP_SOURCE)/gui   \( -name "*.bak" -o -name "*.BAK" -o -name "*~"  -o -name "core" -o -name "*.class" \) -exec rm {} \;

cleanall:
	@echo "********************************"
	@echo "* Cleaning all the directories *"
	@echo "********************************"
	find $(JAHP_SOURCE)/ \( -name "*.bak" -o  -name "*.html" -name "*.class" -o -name "*.BAK" -o -name "*~"  -o -name "core" -o -name "*.o" -o -name "libdemas.a" \) -exec rm {} \;
	find $(DESTINATION_PATH)/ \( -name "*.bak" -o -name "*.html" -o -name "*.class" -o -name "*.BAK" -o -name "*~"  -o -name "core" -o -name "*.o" -o -name "libdemas.a" \) -exec rm {} \;
	find $(DESTINATION_DOC_PATH)/ \( -name "*.bak" -o -name "*.html" -o -name "*.class" -o -name "*.BAK" -o -name "*~"  -o -name "core" -o -name "*.o" -o -name "libdemas.a" \) -exec rm {} \;
