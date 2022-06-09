function [x,fval] = linprog_test()
c = [-10;-15;-12];
A = [5,3,1;-5,6,15;-2,-1,-1];
b = [9;15;-5];
lb = [0;0;0];
[x,fval] =linprog(c,A,b,[],[],lb);