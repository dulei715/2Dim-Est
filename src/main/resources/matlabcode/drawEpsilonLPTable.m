function drawEpsilonLPTable(damTablePath, subGeoITablePath, sizeDIndex)
y1 = readBudgetLPWithFixedSizeD(damTablePath, sizeDIndex);
y2 = readBudgetLPWithFixedSizeD(subGeoITablePath, sizeDIndex);
plot(y1(1,:),y1(2,:),y2(1,:),y2(2,:));