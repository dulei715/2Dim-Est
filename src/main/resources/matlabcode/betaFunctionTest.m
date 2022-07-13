function z = betaFunctionTest()
x = 1:0.1:10;
y = 1:0.1:10;
[x,y] = meshgrid(x,y);
z = beta(x,y);
%plot2(x,y,z);
mesh(x,y,z);