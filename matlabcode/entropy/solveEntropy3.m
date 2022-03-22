function y = solveEntropy3()
syms t e
y = (((exp(e)*(exp(t) - 1)^2 + 8)*((8*exp(t) + 2*exp(t)*(exp(t) - 1))/(exp(e)*(exp(t) - 1)^2 + 8) - (2*exp(e)*exp(t)*(8*exp(t) + (exp(t) - 1)^2)*(exp(t) - 1))/(exp(e)*(exp(t) - 1)^2 + 8)^2))/(8*exp(t) + (exp(t) - 1)^2) - 8/(exp(e)*(exp(t) - 1)^2 + 8) + (2*exp(e)*exp(t)*(exp(t) - 1)*(8*e + 8*t))/(exp(e)*(exp(t) - 1)^2 + 8)^2)/log(2);
solve(y,t)