function generateWriteTwoDimNormPointWithMultiCenter(pointSizeA, pointSizeB, pointSizeC, pathStr)
muA = zeros(1,2);     % 均值
muB = [1,2];
muC = [2,0];
sigmaA = [1  , 0.5;
         0.5  , 1];  % 协方差
sigmaB = [1  , 0;
         0  , 1];
sigmaC = [1  , -0.2;
         -0.2  , 1];
rng('default')  % For reproducibility
pointMatrixA = mvnrnd(muA,sigmaA,pointSizeA);
pointMatrixB = mvnrnd(muB,sigmaB,pointSizeB);
pointMatrixC = mvnrnd(muC,sigmaC,pointSizeC);
[xLenA yLenA] = size(pointMatrixA);
[xLenB yLenB] = size(pointMatrixB);
[xLenC yLenC] = size(pointMatrixC);
fid = fopen(pathStr, 'w');
fprintf(fid,'%d\n', xLenA+xLenB+xLenC);
for i=1:xLenA
    fprintf(fid,'%f %f\n', pointMatrixA(i,1), pointMatrixA(i,2));
end
for i=1:xLenB
    fprintf(fid,'%f %f\n', pointMatrixB(i,1), pointMatrixB(i,2));
end
for i=1:xLenC
    fprintf(fid,'%f %f\n', pointMatrixC(i,1), pointMatrixC(i,2));
end
fclose(fid);

