function y = diffEntropy1(n)
%b = [1:0.01:10];
%e = 0;
syms b e
f(b,e) = log((2*b^2+4*b+1)/(1+2*b^2*exp(e)))+(2*b^2*exp(e))/(1+2*b^2*exp(e))*log(exp(e))-log(4*b+1)/(2*b^2*exp(e));
diff(f(b,e),b,n)