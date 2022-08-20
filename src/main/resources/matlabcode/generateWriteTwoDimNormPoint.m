function generateWriteTwoDimNormPoint(pointSize, pathStr)
mu = zeros(1,2);     % ��ֵ
sigma = [1  , 0.5;
         0.5  , 1];  % Э����
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

