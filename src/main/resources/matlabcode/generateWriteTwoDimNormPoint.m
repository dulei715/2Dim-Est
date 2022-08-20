function generateWriteTwoDimNormPoint(pointSize, pathStr)
mu = zeros(1,2);     % 均值
sigma = [1  , 0.5;
         0.5  , 1];  % 协方差
rng('default')  % For reproducibility
pointMatrix = mvnrnd(mu,sigma,pointSize);
[xLen yLen] = size(pointMatrix);
fid = fopen(pathStr, 'w');
fprintf(fid,'%d\n', xLen);
for i=1:xLen
    for j=1:2
        fprintf(fid,'%f %f\n', pointMatrix(i,1), pointMatrix(i,2));
    end
end
fclose(fid);

