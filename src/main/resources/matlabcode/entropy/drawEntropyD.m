function y = drawEntropyD()
b = [0.01:0.01:10];
d = 1;
e = 2;
%temp = 2.*b.^2.*exp(e);
% y = (log((2.*b.^2+4.*b+1)./(1+temp))+(temp)./(1+temp).*log(exp(e))-log(4.*b+1)./(temp))./log(2);

%tempA = 2*b+1;
%tempB = 2*b*exp(e)+1;
%tempC = 2*b*e*exp(e);
%tempD = 2*b*exp(e)+1;
%y = log(tempA./tempB)+tempC./tempD;

tempA = 2*b.^2+4*b*d+d*d;
tempB = 2*b.^2.*exp(e)+4*b*d+d*d;
y = (log(tempA./tempB)+e-((4*b*d+d*d)*e)./tempB)./log(2);
%y = (log(tempA./tempB)+e-((4*b+1)*e)./tempB);

plot(b,y);