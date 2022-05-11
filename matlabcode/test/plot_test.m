function y = plot_test(b)
x = [0.01:0.01:1];
y = -2*b.*exp(2.*x)+2*b.*exp(x)-x.*exp(x)+(6./power(x,2).*exp(x)-6./power(x,2)-6./x-3).*(2*b.*exp(x)+1);
%y = 6.*exp(3.*x)-3.*exp(2.*x)-6.*x.*exp(2.*x)-12.*x.*x.*exp(2.*x)-4.*exp(x)-1;
plot(x,y);