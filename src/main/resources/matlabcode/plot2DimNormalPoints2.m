function y = plot2DimNormalPoints2()
mu = zeros(1,2);     % 均值
sigma = [1  , 0.9;
         0.9  , 1];  % 协方差
rng('default')  % For reproducibility
X = mvnrnd(mu,sigma,1000);
sigma2 = cov(X);
mu2 = mean(X);
y2 = sqrt(1/((2*pi)^length(mu2)*det(sigma2))) * exp(-1/2*(X-mu2)*inv(sigma2)*(X-mu2)');
y3 = zeros(1000, 1);
for i = 1: 1000
   y3(i) =  y2(i, i);
end
figure
scatter3(X(:,1),X(:,2),y3, '.')
xlabel('X1')
ylabel('X2')
zlabel('Probability Density')