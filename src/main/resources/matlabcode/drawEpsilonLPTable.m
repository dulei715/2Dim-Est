function drawEpsilonLPTable(sizeDIndex)
basicPath = 'E:\1.ѧϰ\4.���ݼ�\2.dataset_for_spatial_estimation\budgetLPTable\';
damTablePath = strcat(basicPath, 'damBudgetLPTable.txt');
subGeoITablePath = strcat(basicPath, 'geoIBudgetLPTable.txt');
damUpperPath = strcat(basicPath, 'bound\damBudgetLPTableUpperBound.txt');
damLowerPath = strcat(basicPath, 'bound\damBudgetLPTableLowerBound.txt');
geoIUpperPath = strcat(basicPath, 'bound\geoIBudgetLPTableUpperBound.txt');
geoILowerPath = strcat(basicPath, 'bound\geoIBudgetLPTableLowerBound.txt');

yDAM = readBudgetLPWithFixedSizeD(damTablePath, sizeDIndex);
yGeoI = readBudgetLPWithFixedSizeD(subGeoITablePath, sizeDIndex);

yDAMUpper = readBudgetLPWithFixedSizeD(damUpperPath, sizeDIndex);
yDAMLower = readBudgetLPWithFixedSizeD(damLowerPath, sizeDIndex);
yGeoIUpper = readBudgetLPWithFixedSizeD(geoIUpperPath, sizeDIndex);
yGeoILower = readBudgetLPWithFixedSizeD(geoILowerPath, sizeDIndex);

fig = figure;
hold on;
%plot(yDAM(1,:),yDAM(2,:));
plot(yGeoI(1,:),yGeoI(2,:));
%plot(yDAMUpper(1,:), yDAMUpper(2,:));
plot(yDAMLower(1,:), yDAMLower(2,:));
plot(yGeoIUpper(1,:), yGeoIUpper(2,:));
plot(yGeoILower(1,:), yGeoILower(2,:));
