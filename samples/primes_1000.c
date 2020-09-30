int calc_primes() {
	unsigned int aN = 1000;
	int curr_prime = 2;
	int pf = 1;
	for(int i = 3; pf <= aN; i++) {
		int is_prime = 1;
		for(int j = 2; j < i/2; j++) {
			if(i % j == 0) {
				is_prime = 0;
				break;
			}
		}
		if(is_prime) {
			curr_prime = i;
			pf++;
		}
  	}
	return curr_prime;
}
