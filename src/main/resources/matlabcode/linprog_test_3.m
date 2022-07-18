function [x,fval] = linprog_test_3()
c = [-1;-1;0;0];
aeq = [2,1,1,0;1,2,0,1];
beq = [12;9];
[x,y] =linprog(c,[],[],aeq,beq,zeros(4,1));
fval = y;