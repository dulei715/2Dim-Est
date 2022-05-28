function y = drawEntropy2()
%b = [1:0.01:10];
%e = 0.5;
syms t e
f(t,e)=log(((exp(t)-1)^2+8*exp(t))/(8+(exp(t)-1)^2*exp(e)))+e-(8*e+8*t)/(8+(exp(t)-1)^2*exp(e));
y(t)=diff(f(t,e),t)
