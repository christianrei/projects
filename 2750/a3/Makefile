CC=gcc
CFLAGS=-Wall
PATH = /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX10.9.sdk/System/Library/Frameworks/JavaVM.framework/Versions/A/Headers
INCD = /usr/bin/java/include -I/usr/bin/java/include/linux

all: libJNIpm.dylib

yadc: data.c data.h lex.yy.c y.tab.c y.tab.h
	gcc -g data.c lex.yy.c y.tab.c -o yadc -ll

lex.yy.c: lex.l y.tab.c 
	lex lex.l

y.tab.c: parser.y
	yacc -d -y parser.y

y.tab.h: parser.y
	yacc -d -y parser.y

JNILIB.class: JNILIB.java
	javac JNILIB.java

JNILIB.h: JNILIB.class
	javah JNILIB

Dialogc.class: Dialogc.java
	javac Dialogc.java

parseConfig.class: parseConfig.java
	javac parseConfig.java

JNILIB.o: JNILIB.h
	$(CC) $(CFLAGS) -I$(PATH) -fPIC -c JNILIB.c

ParameterManager.o: ParameterManager.c ParameterManager.h
	$(CC) $(CFLAGS) -fPIC -c ParameterManager.c

libJNIpm.dylib: JNILIB.o ParameterManager.o Dialogc.class parseConfig.class yadc
	$(CC) -shared -I$(INCD) -o libJNIpm.dylib JNILIB.o ParameterManager.o

clean:
	rm -f parseConfig.class Dialogc.class JNILIB.o ParameterManager.o libJNIpm.dylib lex.yy.c y.tab.c y.tab.h yadc