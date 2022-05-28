clear
clc
syms b e
f1=-16*exp(2*e)*b^4+4*exp(e)*(1-3*exp(e))*b^3-2*exp(e)*(3+exp(e))*b^2+2*(1-3*exp(e))*b+(1-exp(e))+(8*b^3+18*b^2+8*b+1)*exp(e)*log((4*b+1)*exp(e));
[p1,q1]= solve(f1,b,e)