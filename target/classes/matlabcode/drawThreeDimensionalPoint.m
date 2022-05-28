function y = drawThreeDimensionalPoint(path, factorKPoint, constAPoint, factorKCount, constACount)
% taskPath = [fileParentPath, '\task_point.txt'];
% workerPath = [fileParentPath, '\worker_point.txt'];
% disp(taskPath);
% disp(workerPath);

points = textread(path);
%size = points(1);

pointPoint = points(2:end,1:2).*factorKPoint+constAPoint;
pointCount = points(2:end,3).*factorKCount+constACount;

plot3(pointPoint(:,1),pointPoint(:,2),pointCount(:,1),'.');
% set(gca,'FontSize',20);
figure_FontSize = 18;
set(get(gca,'XLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');
set(gca,'FontName','Times New Roman' ,'FontSize',figure_FontSize);
set(findobj('FontSize',10),'FontSize',figure_FontSize);
xlabel('x (km)');
ylabel('y (km)');
%axis equal;
%hold off;