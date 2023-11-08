function [xDistribution, yDistribution, cost] = readData(xPath, yPath)
xData = readtable(xPath);
xDistribution = xData.distributionValue;
xXIndex = xData.xIndex;
xYIndex = xData.yIndex;
yData = readtable(yPath);
yDistribution = yData.distributionValue;
yXIndex = yData.xIndex;
yYIndex = yData.yIndex;
itXSize = length(xDistribution);
itYSize = length(yDistribution);
cost = zeros(itXSize, itYSize);
for i = 1:itXSize
    for j = 1:itYSize
        cost(i,j) = power(xXIndex(i) - yXIndex(j), 2) + power(xYIndex(i) - yYIndex(j), 2);
    end
end
