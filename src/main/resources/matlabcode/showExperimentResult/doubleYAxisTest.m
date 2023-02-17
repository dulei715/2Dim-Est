function doubleYAxisTest()
x = linspace(0,10);
y = sin(3*x);
y2 = cos(3*x);
y3 = cos(3*x+1);
yyaxis left
plot(x,y,x,y2,x,y3);
ylabel('w_1^1');
z = sin(3*x).*exp(0.5*x);
yyaxis right
plot(x,z);
ylim([-150 150]);
ylabel('w_2^2');