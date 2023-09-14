function y = drawPointDatasetWithDifferentColors(path)
% taskPath = [fileParentPath, '\task_point.txt'];
% workerPath = [fileParentPath, '\worker_point.txt'];
% disp(taskPath);
% disp(workerPath);
fig = figure;
hold on;

points = textread(path);
sizeA = points(1);

pointA = points(2:sizeA+1,:);
%taskColor = 'black';
%workerColor = 'red';
%disp(tasks(:,1))
%disp(tasks(:,2))
%for i = [0:length(tasks)]
%scatter(points(:,1), points(:,2), 'filled');
%scatter(points(:,1), points(:,2));
plot(pointA(:,1),pointA(:,2),'o');

sizeB = points(sizeA+2);
pointB = points(sizeA+3:sizeA+sizeB+2,:);
plot(pointB(:,1),pointB(:,2),'*');

% set(gca,'FontSize',20);
figure_FontSize = 20;
set(get(gca,'XLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');
set(gca,'FontName','Times New Roman' ,'FontSize',figure_FontSize);
set(findobj('FontSize',10),'FontSize',figure_FontSize);
%xlabel('x (km)');
%ylabel('y (km)');
xlabel('x');
ylabel('y');
%axis equal;
%hold off;