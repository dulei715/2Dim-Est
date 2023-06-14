function y = drawPointDatasetWithRectangleFrame(path, factorK, constA)
% taskPath = [fileParentPath, '\task_point.txt'];
% workerPath = [fileParentPath, '\worker_point.txt'];
% disp(taskPath);
% disp(workerPath);

points = textread(path);
%size = points(1);

points = points(2:end,:).*factorK+constA;
%taskColor = 'black';
%workerColor = 'red';
%disp(tasks(:,1))
%disp(tasks(:,2))
%for i = [0:length(tasks)]
%scatter(points(:,1), points(:,2), 'filled');
%scatter(points(:,1), points(:,2));
fig = figure;
hold on;
plot(points(:,1),points(:,2),'.');
%rectangle('Position',[-1.5 -1.5 3 3],'EdgeColor','r','LineWidth',1.5);
rectangle('Position',[-1.5 -2 5 5],'EdgeColor','r','LineWidth',1.5);
% set(gca,'FontSize',20);
xlim([roundn(min(points(:,1)),-1) roundn(max(points(:,1)),-1)]);
ylim([roundn(min(points(:,2)),-1) roundn(max(points(:,2)),-1)]);
figure_FontSize = 40;
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