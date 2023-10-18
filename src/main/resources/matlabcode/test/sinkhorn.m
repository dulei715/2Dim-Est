function [d,u_n,v,coupling,K] = sinkhorn(M,lambda,r,C,lowerBound)
I=(r>0);
r = r(I);
M = M(I,:);
K = exp(-lambda*M);
N=size(C);
u_n = ones(length(r),N(2))/length(r);
K2 = bsxfun(@rdivide, K, r);
u_o = ones(length(u_n),N(2))*10000;
%while abs(sum(u_n-u_o))> lowerBound
while abs(sum(u_n-u_o))> lowerBound
    u_o = u_n;
    u_n = 1./(K2*(C./(K'*u_o)));
end
v = C./(K'*u_n);
%d = sqrt(sum(u_n.*((K.*M)*v)));
d = sum(u_n.*((K.*M)*v));
coupling = diag(u_n')*K*diag(v);