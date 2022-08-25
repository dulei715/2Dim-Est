function b = disk_b_test()
epsilon = [0.1:0.1:2];
m1 = exp(epsilon)-1-epsilon;
m2 = 1-exp(epsilon)+epsilon.*exp(epsilon);
b=(2*m2+sqrt(4*m2.*m2+2*exp(epsilon).*m1.*m2))./(2*exp(epsilon).*m1);