CC = gcc
CFLAGS = -Wall

all: ParameterManager

ParameterManager: ParameterManager.o
	ar rcs libpm.a ParameterManager.o

ParameterManager.o: ParameterManager.c ParameterManager.h
	$(CC) $(CFLAGS) -c ParameterManager.c
clean:
	rm -f *o libpm.a