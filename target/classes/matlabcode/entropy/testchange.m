function y=testchange()
b = [0:0.01:10];
e = 0.5;
y = log(4.*b+1)./(1+2*b.^2*exp(e))./log(2);
plot(b,y);
