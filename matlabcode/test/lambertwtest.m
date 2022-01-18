function y=lambertwtest()
x = [-1:0.01:10];
y = lambertw(x);
y2 = lambertw(-1,x);
plot(x,y,x,y2);
