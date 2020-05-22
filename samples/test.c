static int (*y)(int) = (void *) 10;

void cal(unsigned int *a, unsigned int *b, unsigned int *c)
{
  y(*a);
}

