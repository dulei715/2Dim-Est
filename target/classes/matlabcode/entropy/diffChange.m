function y = diffChange(n)
syms b e
h(b,e) = (16*exp(2*e)*b^4+4*(3*exp(e)-1)*exp(e)*b^3+2*exp(e)*(exp(e)+3)*b^2+2*(3*exp(e)-1)*b+exp(e)-1)/(8*b^3+18*b^2+8*b+1)/exp(e)-log((4*b+1)*exp(e));
diff(h(b,e),b,n)