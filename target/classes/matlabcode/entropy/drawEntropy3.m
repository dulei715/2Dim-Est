function y = drawEntropy3()
t = [1:0.01:10];
e = 0.5;
y = (((exp(e).*(exp(t) - 1).^2 + 8).*((8.*exp(t) + 2.*exp(t).*(exp(t) - 1))./(exp(e).*(exp(t) - 1).^2 + 8) - (2.*exp(e).*exp(t).*(8.*exp(t) + (exp(t) - 1).^2).*(exp(t) - 1))./(exp(e).*(exp(t) - 1).^2 + 8).^2))./(8.*exp(t) + (exp(t) - 1).^2) - 8./(exp(e).*(exp(t) - 1).^2 + 8) + (2.*exp(e).*exp(t).*(exp(t) - 1).*(8.*e + 8.*t))./(exp(e).*(exp(t) - 1).^2 + 8).^2)./log(2);
plot(t,y);