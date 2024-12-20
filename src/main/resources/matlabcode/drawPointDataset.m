function y = drawPointDataset(path, factorK, constA, shrinkSize)
% taskPath = [fileParentPath, '\task_point.txt'];
% workerPath = [fileParentPath, '\worker_point.txt'];
% disp(taskPath);
% disp(workerPath);

points = textread(path);
%size = points(1);

points = points(2:shrinkSize:end,:).*factorK+constA;
%taskColor = 'black';
%workerColor = 'red';
%disp(tasks(:,1))
%disp(tasks(:,2))
%for i = [0:length(tasks)]
%scatter(points(:,1), points(:,2), 'filled');
%scatter(points(:,1), points(:,2));
plot(points(:,1),points(:,2),'.');
% set(gca,'FontSize',20);
figure_FontSize = 30;
set(get(gca,'XLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');
set(gca,'FontName','Times New Roman' ,'FontSize',figure_FontSize);
set(findobj('FontSize',10),'FontSize',figure_FontSize);
%xlabel('x (km)');
%ylabel('y (km)');
xlabel('x');
ylabel('y');

%for crime
%xlim([roundn(min(points(:,1)),-1) 42.05]);
%ylim([roundn(min(points(:,2)),-1) -87.5]);

%for nyc
%xlim([40.53 41]);
%ylim([-74.06 -73.7]);

%for multi_normal
xlim([-5 6.6]);
ylim([-5 6.6]);

%set(gca,'XTick',roundn(x,-1));
%axis equal;
%hold off;