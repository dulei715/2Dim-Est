function y = diffGB(n)
syms b
e=0.5
g=-16*exp(2*e)*b^4+4*exp(e)*(1-3*exp(e))*b^3-2*exp(e)*(3+exp(e))*b^2+2*(1-3*exp(e))*b+(1-exp(e))+(8*b^3+18*b^2+8*b+1)*exp(e)*log((4*b+1)*exp(e));
%diff(g(b,e),b,n)
solve(g,b)