function [x,fval] = linprog_test_2()
c = [-2;-3;5];
A = [-2,5,-1;1,3,1];
b = [-10;12];
aeq = [1,1,1];
beq = 7;
[x,y] =linprog(c,A,b,aeq,beq,zeros(3,1));
fval = -y;