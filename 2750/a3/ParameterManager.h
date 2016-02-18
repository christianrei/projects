#include <stdio.h>
#include <string.h>
#include <stdlib.h>

typedef enum param_t {
    INT_TYPE,
    REAL_TYPE,
    BOOLEAN_TYPE,
    STRING_TYPE,
    LIST_TYPE
}param_t;

typedef enum Boolean {
    false,
    true
}Boolean;

typedef struct ParameterList {   /* iterator */
    char * info;
    int size;
    int iterate;
    struct ParameterList * next;
}ParameterList;

union param_value
{
    int           int_val;
    double        real_val;
    Boolean       bool_val;
    char          *str_val;
    ParameterList *list_val;
};

typedef struct Parameter {
    union param_value content;
    char * title;
    int required;
    param_t type;
    int initialized;
    struct Parameter * next;
}Parameter;

typedef struct ParameterManager {
    int parameters;
    Parameter **hash;
}ParameterManager;

ParameterManager * PM_create(int size);
int PM_destroy(ParameterManager *p);
int PM_parseFrom(ParameterManager *p, FILE *fp, char comment);
int PM_manage(ParameterManager *p, char *pname, param_t ptype, int required);
int PM_hasValue(ParameterManager *p, char *pname);
union param_value PM_getValue(ParameterManager *p, char *pname);
char * PL_next(ParameterList *l);
int getIndex(int size, char * title);
