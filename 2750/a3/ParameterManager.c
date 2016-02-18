#include "ParameterManager.h"

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
    for(i = 0; i < size; i++) {
        newPM->hash[i] = malloc(sizeof(Parameter));
        newPM->hash[i]->title = malloc(sizeof(char)*50);
        if(newPM->hash[i] == NULL) {
            return NULL;
        }
        if(newPM->hash[i]->title == NULL) {
            return NULL;
        }
        newPM->hash[i]->title = "";
        newPM->hash[i]->required = 0;
        newPM->hash[i]->next = NULL;
        newPM->hash[i]->initialized = 0;
        newPM->hash[i]->type = 0;
    }
    return newPM;
}

/* Frees everything that has been malloced */
int PM_destroy(ParameterManager *p) {
    int i;
    /*return 1;
    Parameter *temp, *curr; */
    if(p == NULL) {
        return 0;
    }
    for(i = 0; i < p->parameters; i++) {
        /*curr = p->hash[i];
        while(temp != NULL) {
            curr = temp;
            temp = temp->next;
            free(curr->title);
            free(curr);
        }*/
        if(p->hash[i]) {
            free(p->hash[i]);
        }
    }
    free(p->hash);
    free(p);
    p = NULL;
    if(p != NULL) {
        fprintf(stderr, "Error\n");
        return 0;
    }
    else {
        return 1;
    }
    return 1;
}

/* Recieves the values that have been managed and puts them in the correct locations in the hash table */
int PM_parseFrom(ParameterManager *p, FILE *fp, char comment) { /* no semicolon only error */
    char theChar;
    char title[50] = "fjnekfnjkennenjfkenjkrfjknejrnkfeerferferfe";
    char value[100] = "erferfemrnfjejnkrfjnejnrfjnerjnfejnkrfjnkenrjfjnerfjknenjrfnjernjfkfekjnrfnjkenrjfjnkef";
    char * element;
    int state;
    int isFloat;
    int key;
    int type;
    int hasValue;
    int i;
    int error;
    int blankCheck;
    blankCheck = 0;
    error = 1;
    Parameter * temp;
    isFloat = 0;
    state = 1;
    if(fp == NULL) {
        fprintf(stderr, "Error\n");
        return 0;
    }
    memset(title,0,strlen(title));
    memset(value,0,strlen(value));
    while((theChar = fgetc(fp)) != EOF) {
        //printf("%c\n", theChar);
        if(state == 1) {
            if(theChar == comment) {
                while((theChar = fgetc(fp)) != '\n' && theChar != EOF);
            }
            else if(theChar != ' ' && theChar != '\n' && theChar != ';' && theChar != EOF) {
                while(theChar != ' ' && theChar != comment && theChar != '\n' && theChar != EOF) {
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
            }
            else if(theChar == ';') {
                state = 4;
            }
            else {
                if(blankCheck == 1) {
                    error = 0;
                }
                if(theChar == 't' || theChar == 'T') {  /* store value like boolean? */
                    theChar = fgetc(fp);
                    if(theChar != 'r' && theChar != 'R') {
                        fprintf(stderr, "PARSE ERROR1.\n");
                        return 0;
                    }
                    else {
                        theChar = fgetc(fp);
                        if(theChar != 'u' && theChar != 'U') {
                            fprintf(stderr, "PARSE ERROR2.\n");
                            return 0;
                        }
                        else {
                            theChar = fgetc(fp);
                            if(theChar != 'e' && theChar != 'E') {
                                fprintf(stderr, "PARSE ERROR3.\n");
                                return 0;
                            }
                            else {
                                type = 2;
                                strcat(value, "true");
                            }
                        }
                    }
                }
                else if(theChar == 'f' || theChar == 'F') { /*same */
                    theChar = fgetc(fp);
                    if(theChar != 'a' && theChar != 'A') {  /* store value like boolean? */
                        fprintf(stderr, "PARSE ERROR4.\n");
                        return 0;
                    }
                    else {
                        theChar = fgetc(fp);
                        if(theChar != 'L' && theChar != 'l') {
                            fprintf(stderr, "PARSE ERROR5.\n");
                            return 0;
                        }
                        else {
                            theChar = fgetc(fp);
                            if(theChar != 'S' && theChar != 's') {
                                fprintf(stderr, "PARSE ERROR5.\n");
                                return 0;
                            }
                            else {
                                theChar = fgetc(fp);
                                if(theChar != 'e' && theChar != 'E') {
                                    fprintf(stderr, "PARSE ERROR6.\n");
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
                    while(theChar != '"' && theChar != EOF) {  /*TRTHRT*/
                        value[strlen(value)] = theChar;
                        value[strlen(value)+1] = '\0';
                        theChar = fgetc(fp);
                    }
                    type = 3;
                }
                else if(theChar == '0' || theChar == '1' || theChar == '2' || theChar == '3' || theChar == '4' || theChar == '5' || theChar == '6' || theChar == '7' || theChar == '8' || theChar == '9' || theChar == '-') {
                    value[strlen(value)] = theChar;
                    value[strlen(value)+1] = '\0';
                    type = 0;
                    blankCheck = 1;
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
                    if(theChar == ';') {
                        state = 4;
                    }
                    else if(theChar == comment) {
                        while((theChar = fgetc(fp)) != '\n' && theChar != EOF);
                    }
                    if(isFloat == 1) {
                        type = 1;
                        blankCheck = 0;
                    }
                }
                else if(theChar == '{') {
                    while(theChar != '}') {
                        if(theChar == comment) {
                            while((theChar = fgetc(fp)) != '\n' && theChar != EOF);
                        }
                        else if(theChar != '\n' && theChar != EOF && theChar != ' ') {
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
                    fprintf(stderr, "PARSE ERROR--.\n");
                    return 0;
                }
            }
        }
        if(state == 4) {
            blankCheck = 0;
            if(theChar == comment) {
                while((theChar = fgetc(fp)) != '\n' && theChar != EOF);
            }
            else {
                int managed = 0;
                for(i = 0; i < p->parameters; i++) {   /* NEED TO ALSO GO THROUGH COLLISIONS */
                    temp = p->hash[i];
                    if(strcmp(title, "fields") == 0 || strcmp(title, "buttons") == 0) {
                        managed = 1;
                        break;
                    }
                    if(strcmp(temp->title, title) == 0) {
                        managed = 1;
                        break;
                    }
                    else if(temp->next != NULL) {
                        while(temp->next != NULL) {
                            temp = temp->next;
                            if(strcmp(temp->title, title) == 0) {
                                managed = 1;
                                break;
                            }
                        }
                        if(managed == 1) {
                            break;
                        }
                    }
                    else {
                        managed = 0;
                    }
                }

                key = getIndex(p->parameters, title);
                
                temp = p->hash[key];
                if(temp->type == REAL_TYPE && type == INT_TYPE) {
                    type = REAL_TYPE;
                }
                /*if(temp->type != type) {
                    fprintf(stderr, "PARSE ERROR. Not correct type.\n");
                    return 0;
                }*/
                if(managed == 0) {
                    state = 1;
                }
                else if(strcmp(temp->title, title) == 0) {
                    if(temp->type != type) {
                        fprintf(stderr, "PARSE ERROR7.\n");
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
                            temp->content.list_val->iterate = 0;
                            if(strcmp(value, "{}") == 0) {   /* in case list is empty */
                                temp->content.list_val->info = NULL;
                            }
                            memmove(value, value+1, strlen(value));
                            value[strlen(value)-1] = '\0';
                            element = strtok(value, " \"");
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
                                element = strtok(NULL, " \"");
                            }
                            tempList->next = NULL;
                        }
                        temp->initialized = 1;
                    }
                }
                else if(temp->next != NULL) {
                    while(temp != NULL) {
                        if(strcmp(temp->title, title) == 0) {
                            if(temp->type != type) {
                                fprintf(stderr, "PARSE ERROR8.\n");
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
                                    temp->content.list_val->iterate = 0;
                                    if(strcmp(value, "{}") == 0) {   /* in case list is empty */
                                        temp->content.list_val->info = NULL;
                                    }
                                    temp->content.list_val->size = 0;
                                    memmove(value, value+1, strlen(value));
                                    value[strlen(value)-1] = '\0';
                                    element = strtok(value, " \"");
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
                                        element = strtok(NULL, " \"");
                                    }
                                    tempList->next = NULL;
                                }
                                temp->initialized = 1;
                            }
                        }
                        else {
                            if(temp == NULL) {
                                fprintf(stderr, "PARSE ERROR9.\n");
                                return 0;
                            }
                        }
                        temp = temp->next;
                    }
                }
                else {
                    fprintf(stderr, "PARSE ERROR10.\n");
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
        if(temp->required == 1) {
            hasValue = PM_hasValue(p, temp->title);
            if(hasValue == 0) {
                printf("ERROR: %s\n", temp->title);
                fprintf(stderr, "PARSE ERROR. Required value not foundxz.\n");
                error = 0;
            }
        }
        else if(temp->next != NULL) {
            while(temp->next != NULL) {
                temp = temp->next;
                if(temp->required == 1) {
                    hasValue = PM_hasValue(p, temp->title);
                    if(hasValue == 0) {
                        printf("ERROR: %s\n", temp->title);
                        fprintf(stderr, "PARSE ERROR. Required value not foundz.\n");
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
        nameUsed = 0;
        if(temp->next == NULL) {
            break;
        }
        temp = temp->next; /* go through list at same key */
        i++;
    }
    if(nameUsed == 0) {
        collision = malloc(sizeof(Parameter));
        collision->title = malloc(sizeof(char)*50);
        if(collision != NULL && collision->title != NULL) {
            memcpy(collision->title, pname, strlen(pname));
            collision->title[strlen(pname)] = '\0';
            //collision->title = pname;
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
    p->hash[key]->title = malloc(sizeof(char)*30);
    memcpy(p->hash[key]->title, pname, strlen(pname));
    p->hash[key]->title[strlen(pname)] = '\0';
    //p->hash[key]->title = pname;
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
    if(strcmp(temp->title, pname) == 0) {
        if(temp->initialized == 1) {
            return 1;
        }
        else {
            return 0;
        }
    }
    else {
        while(temp->next != NULL) {
            temp = temp->next;
            if(strcmp(temp->title, pname) == 0) {
                if(temp->initialized == 1) {
                    return 1;
                }
                else {
                    return 0;
                }
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
    if(strcmp(temp->title, pname) == 0) {
        value = temp->content;
        return value;
    }
    else {
        while(temp->next != NULL) {
            temp = temp->next;
            if(strcmp(temp->title, pname) == 0) {
                value = temp->content;
                return value;    /*wtf*/
            }
        }
    }
    return value;
}

/* Getting an index using the name and size of the table to make a key and return it */
int getIndex(int size, char * title) {
    int index = 0;
    int i;
    int num = 0;
    for(i = 0; i < strlen(title); i++) {
        if (title[i] >= 'A' && title[i] <= 'Z')
            num = title[i] - 'A';
        else if (title[i] >= 'a' && title[i] <= 'z')
            num = title[i] - 'a';
        index = num + '0';
    }
    index = index % size;
    index = (int)index;
    return index;
}

/* Iterate through the last and return each element of the list one by one whenever called */
char * PL_next(ParameterList *l) {
    int i;
    int loop;
    ParameterList * temp;
    temp = l;
    loop = l->iterate;
    l->iterate++;
    for(i = 0; i < loop; i++) {
        temp = temp->next;
    }
    if(loop >= l->size) {
        return NULL;
    }
    return temp->info;
}

