function y = drawEntropy()
b = [0.5:0.01:10];
e = 0;
temp = 2.*b.^2.*exp(e);
y = (log((2.*b.^2+4.*b+1)./(1+temp))+(temp)./(1+temp).*log(exp(e))-log(4.*b+1)./(temp))./log(2);
plot(b,y);