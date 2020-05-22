#include <stdio.h>

/*#define START 2969
  #define END 2970*/

#define START 1001
#define END 10000

static int is_prime(unsigned int n) {
  int i;
  //printf("checking %d\n", n);
  for(int i = 2; i*i <= n; i++) {
	if(n % i == 0) {return 0;}
  }
  return 1;
}

static int * split(unsigned int n, int *digits, int ndigits) {
  // 1486 = {6, 8, 4, 1}
  for(int i = 0; i < ndigits; i++) {
	digits[i] = n % 10;
	n /= 10;
  }

  return digits;
}

static int join(int *digits, int ndigits) {
    // {6, 8, 4, 1} = 1486
  int n = 0;

  for(int i = ndigits - 1; i >= 0; i--) {
	n = n*10 + digits[i];
  }

  return n;
}

static void permute4(unsigned int n, int *out) {
  int digits[4];
  int choices[4];
  int k = 0;

  split(n, digits, 4);

  // should be recursive ...
  for(int f=0; f<4; f++) {
	choices[0] = digits[f];

	for(int s=0; s<4; s++) {

	  if(s == f) continue;
	  choices[1] = digits[s];

	  for(int t=0; t<4; t++) {
		if(t == s || t == f) continue;

		choices[2] = digits[t];

		for(int l=0; l < 4; l++) {

		  if(l == f || l == s || l == t) continue;
		  choices[3] = digits[l];
		  out[k++] = join(choices, 4);
		}
	  }
	}
  }
}

static int check_diff(int *a, int n) {
  int diff;
  for(int i = 1; i < n; i++) {
	diff = a[i] - a[0];
	//printf("diff=%d\n", diff);
	for(int j = i+1; j < n; j++) {
	  if(a[j] - a[i] == diff) {
		//printf("*diff = %d\n", diff);
		return diff;
	  }
	  if(a[j] - a[i] > diff) break;
	}
  }

  return 0;
}

static void sort(int *a, int n) {
  for(int i = 0; i < n; i++) {
	for(int j = i; j > 0 && a[j-1] > a[j]; j--) {
	  int tmp = a[j-1];
	  a[j-1] = a[j];
	  a[j] = tmp;
	}
  }
}

long four_digit_primes() {
  int out[24];

  for(int j = START; j < END; j+=2) {
	if(is_prime(j) && j != 1487) {
	  permute4(j, out);

	  int primes=1;
	  for(int k = 1; k < 24; k++) {
		if(is_prime(out[k])) {
		  //printf("prime: %d\n", out[k]);
		  out[primes++] = out[k];
		}
	  }

	  /* for(int k = 0; k < primes; k++) { */
	  /* 	printf("%d\n", out[k]); */
	  /* } */

	  sort(out, primes);
	  /* printf("first: %d\n", out[0]); */

	  int s = 0;
	  while(s < primes && out[s] < j) s++;

	  /* printf("real first: %d\n", out[s]); */
	  /* for(int k = s; k < primes; k++) { */
	  /* 	printf("%d\n", out[k]); */
	  /* } */

	  int diff = check_diff(out+s, primes-s+1);
	  if(diff > 0) {
		long sum = out[s]*10000;
		sum = sum + out[s]+diff;
		sum *= 10000;
		sum += out[s]+2*diff;

		return sum;
	  }
	}
  }

  return -1;
}

#ifdef DEBUG
int main(void) {
  printf("%lu\n", four_digit_primes());
}
#endif
