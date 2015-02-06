#include "ParameterManager.h"         /* data structures from the .h must be included */

ParameterManager * PM_create(int size);
int PM_destroy(ParameterManager *p);
int PM_parseFrom(ParameterManager *p, FILE *fp, char comment);
int PM_manage(ParameterManager *p, char *pname, param_t ptype, int required);
int PM_hasValue(ParameterManager *p, char *pname);
union param_value PM_getValue(ParameterManager *p, char *pname);

/* Malloc the necessary space for the Parameter Manager. More space is malloced as needed */
ParameterManager * PM_create(int size) {
    ParameterManager * newPM;
    int i;
    if(size <= 0) {
        return NULL;
    }
    newPM = malloc(sizeof(ParameterManager));
    newPM->parameters = size;
    newPM->hash = malloc(sizeof(Parameter*)*size);
    /*initialize*/
    if(newPM != NULL) {
        for(i = 0; i < size; i++) {
            newPM->hash[i] = malloc(sizeof(Parameter));
            if(newPM->hash[i] == NULL) {
                return NULL;
            }
            newPM->hash[i]->title = malloc(sizeof(char)*30);
            newPM->hash[i]->initialized = false;
            /* malloc for list */
            if(newPM->hash[i]->title == NULL) {
                return NULL;
            }
            newPM->hash[i]->title = "";
            newPM->hash[i]->required = 0;
            newPM->hash[i]->next = NULL;
        }
        return newPM;
    }
    else {
        return NULL;
    }
}

/* Frees everything that has been malloced */
int PM_destroy(ParameterManager *p) {
    int i;
    Parameter * curr,* temp;
    for(i = 0; i < p->parameters; i++) {
        temp = p->hash[i];
        if(strcmp(p->hash[i]->title, "") != 0) {
            while(temp->next != NULL) {
                if(temp->next == NULL) {
                    curr = temp;
                    free(temp);
                    printf("freed\n");
                    temp = curr;
                }
            }
            printf("past while\n");
            printf("%s\n", temp[i].title);   /* now prints null? */
            printf("freed title\n");
        }
    }
    /*free(p->hash);
    free(p);*/
    /*if(p != NULL) {
        fprintf(stderr, "Error\n");
        return 0;  /* can put error messages fprintf /
    }
    else {
        return 1;
    }*/
    return 1;
}

/* Recieves the values that have been managed and puts them in the correct locations in the hash table */
int PM_parseFrom(ParameterManager *p, FILE *fp, char comment) {
    char theChar;
    char title[30] = "";
    char value[100] = "";
    char * element;
    int state;
    int isFloat;
    int key;
    int type;
    int hasValue;
    int i;
    int error;
    error = 1;
    Parameter * temp;
    isFloat = 0;
    state = 1;
    if(fp == NULL) {
        fprintf(stderr, "Error\n");
        return 0;
    }
    while((theChar = fgetc(fp)) != EOF) {
        if(state == 1) {
            if(theChar == comment) {
                while((theChar = fgetc(fp)) != '\n' && theChar != EOF);
            }
            else if(theChar != ' ' && theChar != '\n' && theChar != ';' && theChar != EOF) {
                while(theChar != ' ' && theChar != comment && theChar != '\n') {
                    if(theChar == '=') {
                        if(strcmp(title, "") == 0) {
                            fprintf(stderr, "Parse error. = without title.\n");
                            return 0;
                        }
                        break; /*escape this while loop to go to state 2 then eventually 3 */
                    }
                    title[strlen(title)] = theChar;
                    title[strlen(title)+1] = '\0';
                    theChar = fgetc(fp);
                }
                state = 2;
            }
        }
        if(state == 2) {
            if(theChar == comment) {
                while((theChar = fgetc(fp)) != '\n');
            }
            else if(theChar == '=') {
                state = 3;
                theChar = fgetc(fp);
            }
            else if(theChar == ' ' || theChar == '\n') {
                state = 2;
            }
            else {
                fprintf(stderr, "Invalid name.\n");
                return 0;
            }
        }
        if(state == 3) {
            if(theChar == comment) {
                while((theChar = fgetc(fp)) != '\n' && theChar != EOF);
            }
            else if(theChar == ' ' || theChar == '\n') {
                state = 3;
            }else if(theChar == ';') {
                state = 4;
            }
            else {
                if(theChar == 't' || theChar == 'T') {  /* store value like boolean? */
                    theChar = fgetc(fp);
                    if(theChar != 'r' && theChar != 'R') {
                        fprintf(stderr, "PARSE ERROR.\n");
                        return 0;
                    }
                    else {
                        theChar = fgetc(fp);
                        if(theChar != 'u' && theChar != 'U') {
                            fprintf(stderr, "PARSE ERROR.\n");
                            return 0;
                        }
                        else {
                            theChar = fgetc(fp);
                            if(theChar != 'e' && theChar != 'E') {
                                fprintf(stderr, "PARSE ERROR.\n");
                                return 0;
                            }
                            else {
                                theChar = fgetc(fp);
                                type = 2;
                                strcat(value, "true");
                            }
                        }
                    }
                }
                else if(theChar == 'f' || theChar == 'F') { /*same */
                    theChar = fgetc(fp);
                    if(theChar != 'a' && theChar != 'A') {  /* store value like boolean? */
                        fprintf(stderr, "PARSE ERROR.\n");
                        return 0;
                    }
                    else {
                        theChar = fgetc(fp);
                        if(theChar != 'L' && theChar != 'l') {
                            fprintf(stderr, "PARSE ERROR.\n");
                            return 0;
                        }
                        else {
                            theChar = fgetc(fp);
                            if(theChar != 'S' && theChar != 's') {
                                fprintf(stderr, "PARSE ERROR.\n");
                                return 0;
                            }
                            else {
                                theChar = fgetc(fp);
                                if(theChar != 'e' && theChar != 'E') {
                                    fprintf(stderr, "PARSE ERROR.\n");
                                    return 0;
                                }
                                else {
                                    type = 2;
                                    strcat(value, "false");
                                }
                            }
                        }
                    }
                }
                else if(theChar == '"') {
                    theChar = fgetc(fp);
                    while(theChar != '"') {
                        value[strlen(value)] = theChar;
                        value[strlen(value)+1] = '\0';
                        theChar = fgetc(fp);
                    }
                    type = 3;
                }
                else if(theChar == '0' || theChar == '1' || theChar == '2' || theChar == '3' || theChar == '4' || theChar == '5' || theChar == '6' || theChar == '7' || theChar == '8' || theChar == '9') {
                    value[strlen(value)] = theChar;
                    value[strlen(value)+1] = '\0';
                    type = 0;
                    theChar = fgetc(fp);
                    if(theChar == '.') {
                        if(isFloat == 1) {
                            fprintf(stderr, "Error with decimal input.\n");
                            return 0;
                        }
                        isFloat = 1;
                        type = 1;
                        value[strlen(value)] = theChar;
                        value[strlen(value)+1] = '\0';
                    }
                    while(theChar == '0' || theChar == '1' || theChar == '2' || theChar == '3' || theChar == '4' || theChar == '5' || theChar == '6' || theChar == '7' || theChar == '8' || theChar == '9') {
                        value[strlen(value)] = theChar;
                        value[strlen(value)+1] = '\0';
                        type = 0;
                        if(theChar == '.') {
                            if(isFloat == 1) {
                                fprintf(stderr, "Error with decimal input.\n");
                                return 0;
                            }
                            isFloat = 1;
                            type = 1;
                            value[strlen(value)] = theChar;
                            value[strlen(value)+1] = '\0';
                        }
                        theChar = fgetc(fp);

                    }
                    if (theChar == ';') {
                        state = 4;
                    }else if(theChar == comment) {
                        while((theChar = fgetc(fp)) != '\n' && theChar != EOF);
                    }
                    if(isFloat == 1) {
                        type = 1;
                    }
                }
                else if(theChar == '{') {
                    while(theChar != '}') {
                        if(theChar == comment) {
                            while((theChar = fgetc(fp)) != '\n' && theChar != EOF);
                        }
                        else if(theChar != '\n' && theChar != EOF) {
                            value[strlen(value)] = theChar;
                            value[strlen(value)+1] = '\0';
                            theChar = fgetc(fp);
                        }
                        else {
                            theChar = fgetc(fp);
                        }
                    }
                    value[strlen(value)] = theChar;
                    value[strlen(value)+1] = '\0';
                    type = 4;
                }
                else {
                    fprintf(stderr, "PARSE ERROR---.\n");
                    return 0;
                }
            }
        }
        if(state == 4) {
            if(theChar == comment) {
                while((theChar = fgetc(fp)) != '\n' && theChar != EOF);
            }
            else {
                key = getIndex(p->parameters, title);
                temp = p->hash[key];
                if(strcmp(temp->title, title) == 0) {
                    if(temp->type != type) {
                        fprintf(stderr, "PARSE ERROR.\n");
                        return 0;
                    }
                    else {
                        if(type == INT_TYPE) {
                            temp->content.int_val = atoi(value);
                        }
                        else if(type == REAL_TYPE) {
                            temp->content.real_val = atof(value);
                        }
                        else if(type == BOOLEAN_TYPE) {
                            if(strcmp(value, "true") == 0 || strcmp(value, "TRUE") == 0) {
                                temp->content.bool_val = true;
                            }
                            else if(strcmp(value, "false") == 0 || strcmp(value, "FALSE") == 0 ) {
                                temp->content.bool_val = false;
                            }
                        }
                        else if(type == STRING_TYPE) {
                            temp->content.str_val = malloc(sizeof(char)*30);
                            memcpy(temp->content.str_val, value, strlen(value));
                            temp->content.str_val[strlen(value)] = '\0';
                        }
                        else if(type == LIST_TYPE) {
                            temp->content.list_val = NULL;
                            temp->content.list_val = malloc(sizeof(ParameterList));
                            temp->content.list_val->info = malloc(sizeof(char)*30);
                            temp->content.list_val->size = 0;
                            memmove(value, value+1, strlen(value));
                            value[strlen(value)-1] = '\0';
                            element = strtok(value, "\"");
                            ParameterList *tempList = malloc(sizeof(ParameterList));
                            tempList = temp->content.list_val;
                            while(element) {
                                if(strcmp(element, ",") != 0) {
                                    temp->content.list_val->size++;
                                    memcpy(tempList->info, element, strlen(element));
                                    tempList->info[strlen(element)] = '\0';
                                    tempList->next = NULL;
                                    tempList->next = malloc(sizeof(ParameterList));
                                    tempList = tempList->next;
                                    tempList->info = malloc(sizeof(char)*30);
                                }
                                element = strtok(NULL, "\"");
                            }
                            tempList->next = NULL;
                        }
                        temp->initialized = true;
                    }
                }
                else if(temp->next != NULL) {
                    while(temp != NULL) {
                        if(strcmp(temp->title, title) == 0) {
                            if(temp->type != type) {
                                fprintf(stderr, "PARSE ERROR.\n");
                                return 0;
                            }
                            else {
                                if(type == INT_TYPE) {
                                    temp->content.int_val = atoi(value);
                                }
                                else if(type == REAL_TYPE) {
                                    temp->content.real_val = atof(value);
                                }
                                else if(type == BOOLEAN_TYPE) {
                                    if(strcmp(value, "true") == 0 || strcmp(value, "TRUE") == 0) {
                                        temp->content.bool_val = true;
                                    }
                                    else if(strcmp(value, "false") == 0 || strcmp(value, "FALSE") == 0 ) {
                                        temp->content.bool_val = false;
                                    }
                                }
                                else if(type == STRING_TYPE) {
                                    temp->content.str_val = malloc(sizeof(char)*30);
                                    memcpy(temp->content.str_val, value, strlen(value));
                                    temp->content.str_val[strlen(value)] = '\0';
                                }
                                else if(type == LIST_TYPE) {
                                    temp->content.list_val = NULL;
                                    temp->content.list_val = malloc(sizeof(ParameterList));
                                    temp->content.list_val->info = malloc(sizeof(char)*30);
                                    temp->content.list_val->size = 0;
                                    memmove(value, value+1, strlen(value));
                                    value[strlen(value)-1] = '\0';
                                    element = strtok(value, "\"");
                                    ParameterList *tempList = malloc(sizeof(ParameterList));
                                    tempList = temp->content.list_val;
                                    while(element) {
                                        if(strcmp(element, ",") != 0) {
                                            temp->content.list_val->size++;
                                            memcpy(tempList->info, element, strlen(element));
                                            tempList->info[strlen(element)] = '\0';
                                            tempList->next = NULL;
                                            tempList->next = malloc(sizeof(ParameterList));
                                            tempList = tempList->next;
                                            tempList->info = malloc(sizeof(char)*30);
                                        }
                                        element = strtok(NULL, "\"");
                                    }
                                    tempList->next = NULL;
                                }
                                temp->initialized = true;
                            }
                        }
                        else {
                            if(temp == NULL) {
                                fprintf(stderr, "PARSE ERROR.\n");
                                return 0;
                            }
                        }
                        temp = temp->next;
                    }
                }
                else {
                    fprintf(stderr, "PARSE ERROR.\n");
                    return 0;
                }
            }
            memset(title,0,strlen(title));
            memset(value,0,strlen(value));
            element = "";
            isFloat = 0;
            state = 1;
        }
    }
    for(i = 0; i < p->parameters; i++) {   /* NEED TO ALSO GO THROUGH COLLISIONS */
        temp = p->hash[i];
        if(strcmp(temp->title, title) == 0) {
            if(temp->required == 1) {
                hasValue = PM_hasValue(p, temp->title);
                if(hasValue == 0) {
                    fprintf(stderr, "PARSE ERROR. Required value not found.\n");
                    error = 0;
                }
            }
        }
        else if(temp->next != NULL) {
            while(temp->next != NULL) {
                temp = temp->next;
                if(temp->required == 1) {
                    hasValue = PM_hasValue(p, temp->title);
                    if(hasValue == 0) {
                        printf("%s\n", temp->title);
                        fprintf(stderr, "PARSE ERROR. Required value not found.\n");
                        error = 0;
                    }
                }
            }
        }
    }
    return error; /*return 1 on success*/
}

/* Add names and descriptive parameter values to table before the union values can be added */
int PM_manage(ParameterManager *p, char *pname, param_t ptype, int required) {
    int i;
    int key;
    int nameUsed;
    Parameter * collision;
    Parameter * temp;
    key = getIndex(p->parameters, pname);
    i = 0;
    nameUsed = 1;
    temp = p->hash[key];
    while(strcmp(temp->title, "") != 0 && (i < p->parameters)) {
        if(temp->title == pname) {
            fprintf(stderr, "Error\n");
            return 0;
        }
        nameUsed = 0;
        if(temp->next == NULL) {
            break;
        }
        temp = temp->next; /* go through list at same key */
        i++;
    }
    if(nameUsed == 0) {
        collision = malloc(sizeof(Parameter));
        collision->title = malloc(sizeof(char)*30);
        if(collision != NULL && collision->title != NULL) {
            collision->title = pname;
            collision->type = ptype;
            collision->required = required;
            collision->next = NULL;
            temp->next = collision;
            return 1;
        }
        else {
            fprintf(stderr, "Error\n");
            return 0;
        }
    }
    p->hash[key]->title = pname;
    p->hash[key]->type = ptype;
    p->hash[key]->required = required;
    p->hash[key]->next = NULL;
    return 1;
}

/* Has value function that returns a 1 if a value has been given to this key and a 0 if not */
int PM_hasValue(ParameterManager *p, char *pname) {
    int key;
    Parameter * temp;
    key = getIndex(p->parameters, pname);
    temp = p->hash[key];
    if(temp->title == pname) {
        if(temp->initialized == true) {
            return 1;
        }
        else {
            return 0;
        }
    }
    else if(temp->next == NULL) {
        return 0;
    }
    else {
        while(temp->next != NULL) {
            if(temp->title == pname) {
                if(temp->initialized == true) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
            else {
                temp = temp->next;
            }
        }
    }
    return 0;
}

/* Recieves the value from the union in the table */
union param_value PM_getValue(ParameterManager *p, char *pname) {
    int valueFound;
    int key;
    union param_value value;
    Parameter * temp;
    valueFound = PM_hasValue(p, pname);
    if(valueFound == 0) {
        return value;
    }
    key = getIndex(p->parameters, pname);
    temp = p->hash[key];
    if(temp->type == LIST_TYPE) {
        temp->content.list_val->iterate = 0;
    }
    if(temp->title == pname) {
        value = temp->content;
        return value;
    }
    else if(temp->next == NULL) {
        return value;
    }
    else {
        while(temp->next != NULL){
            temp = temp->next;
            if(temp->title == pname) {
                value = temp->content;
                return value;
            }
        }
    }
    return value;
}

/* Getting an index using the name and size of the table to make a key and return it */
int getIndex(int size, char * title) {
    int index;
    int num;
    for(int i = 0; i < strlen(title); i++) {
        if (title[i] >= 'A' && title[i] <= 'Z')
            num = title[i] - 'A';
        else if (title[i] >= 'a' && title[i] <= 'z')
            num = title[i] - 'a';
        index = num + '0';
    }
    index = index % size;
    return index;
}

/* Iterate through the last and return each element of the list one by one whenever called */
char * PL_next(ParameterList *l) {
    int i;
    int loop;
    ParameterList * temp;
    temp = l;
    loop = l->iterate;
    for(i = 0; i < loop; i++) {
        temp = temp->next;
    }
    l->iterate++;
    if(loop >= l->size) {
        return NULL;
    }
    return temp->info;
}

