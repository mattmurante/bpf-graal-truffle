void calc_primes(unsigned int *a, unsigned int aN, unsigned int n)
{
  int pf = 0;

  if(pf < aN)
	a[pf++] = 2;

  for(int i = 3; i < n && pf < aN; i++) {
	int is_prime = 1;

	for(int j = 0; j < pf; j++) {

	  if(i % a[j] == 0) {
		is_prime = 0;
		break;
	  }

	  /* if(a[j] * a[j] > i) */
	  /* 	break; */
	}

	if(is_prime) {
	  a[pf++] = i;
	}
  }
}

