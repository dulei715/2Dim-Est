function y = solveEntropy4()
syms b e
y = -16*exp(2*e)*b^4+8*exp(e)*(2-exp(e))*b^3+16*exp(e)*b^2+4*b+4;
solve(y,b)