function sample2DimNormalPoints()
mu = zeros(1,2);     % 均值
sigma = [1  , 0.9;
         0.9  , 1];  % 协方差
rng('default')  % For reproducibility
X = mvnrnd(mu,sigma,1000);
y = mvnpdf(X,mu,sigma);
figure
scatter3(X(:,1),X(:,2),y, '.')
xlabel('X1')
ylabel('X2')
zlabel('Probability Density')