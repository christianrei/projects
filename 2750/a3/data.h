#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct node {
    char * name;
    char * val;
    struct node * next;
}node;

void addToBack(node * head, char * info);
void addToIndex(node * head, int index, char * info);
void printList(node * head);
int findListName(node * head, char * info);
void destroy(node * head);