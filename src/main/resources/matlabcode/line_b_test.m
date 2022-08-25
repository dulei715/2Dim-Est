function b = line_b_test()
epsilon = [0.1:0.1:1];
b=(epsilon.*exp(epsilon)-exp(epsilon)+1)./(2*exp(epsilon).*(exp(epsilon)-1-epsilon));