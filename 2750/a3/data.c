#include "data.h"

void addToBack(node * head, char * info) {
    node * temp;
    temp = head;
    while(temp->next != NULL) {
        temp = temp->next;
    }
    temp->next = malloc(sizeof(node));
    temp->next->name = malloc(sizeof(char)*strlen(info));
    temp->next->val = malloc(sizeof(char)*25);
    strcpy(temp->next->name, info);
    temp->next->next = NULL;
}

void addToIndex(node * head, int index, char * info) {
    node * temp;
    int i;
    temp = head;
    i = 1;
    while(i <= index) {
        temp = temp->next;
        i++;
    }
    temp->val = malloc(sizeof(char)*strlen(info));
    strcpy(temp->val, info);
}

void printList(node * head) {
    node * temp;
    temp = head;
    while(temp != NULL) {
        printf("ITEM: %s\n", temp->name);
        printf("VAL: %s\n", temp->val);
        temp = temp->next;
    }
}

int findListName(node * head, char * info) {
    node * temp;
    temp = head;
    int count;
    count = 0;
    count++;
    temp = temp->next;
    while(temp != NULL) {
        if(strcmp(temp->name,info) == 0) {
            return count;
        }
        count++;
        temp = temp->next;
    }
    return 0; /*if index returns 0 it didn't find it */
}

void destroy(node * head) {
    node * temp;
    temp = head;
    while(temp != NULL) {
        free(temp->name);
        free(temp->val);
        free(temp);
        temp = temp->next;
    }
}