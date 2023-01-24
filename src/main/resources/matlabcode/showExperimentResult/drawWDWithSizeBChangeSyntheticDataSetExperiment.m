function y = drawWDWithSizeBChangeSyntheticDataSetExperiment(filenameOne,filenameTwo, xLabelName, yLabelName,outputFileName)

%data = readmatrix(path,lineStart-1,colStart-1 ,[lineStart-1,lineEnd-1,colStart-1,colEnd-1]);
%data(lineStart:end,colStart:end)
%readmatrix(path)
sizeBIndexLine = 5;
wassersteinDistanceIndexLine = 8;

matrixOne = importdata(filenameOne, ',',1);
x_RAM_one = matrixOne.data(1:5,sizeBIndexLine);
x_DAM_one = matrixOne.data(6:10,sizeBIndexLine);


y_RAM_one = matrixOne.data(1:5,wassersteinDistanceIndexLine);
y_DAM_one = matrixOne.data(6:10,wassersteinDistanceIndexLine);

%%%%%%%%%%%%%%%%%%%%%%%%%%
matrixTwo = importdata(filenameTwo, ',',1);
x_RAM_two = matrixTwo.data(1:5,sizeBIndexLine);
x_DAM_two = matrixTwo.data(6:10,sizeBIndexLine);


y_RAM_two = matrixTwo.data(1:5,wassersteinDistanceIndexLine);
y_DAM_two = matrixTwo.data(6:10,wassersteinDistanceIndexLine);



%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
fig = figure;
hold on;

plot(x_RAM_one,y_RAM_one, 'ro-','LineWidth',2);
plot(x_DAM_one, y_DAM_one, 'g*-','LineWidth',2);

plot(x_RAM_two, y_RAM_two, 'rs:','LineWidth',2);
plot(x_DAM_two, y_DAM_two, 'gx:', 'LineWidth', 2);

%plot(x_C_RAM, y_C_RAM, 'r+:','LineWidth',2);
%plot(x_C_DAM, y_C_DAM, 'gd:', 'LineWidth', 2);

figure_FontSize = 18;
set(get(gca,'XLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');

set(gca,'FontName','Times New Roman' ,'FontSize',figure_FontSize);
set(findobj('FontSize',10),'FontSize',figure_FontSize);

xlabel(xLabelName);
ylabel(yLabelName);
h = legend('RAM-Normal', 'DAM-Normal','RAM-Zipf','DAM-Zipf','Location','Best');
set(h,'FontName','Times New Roman','FontSize',14,'FontWeight','normal');
legend('off');
saveas(fig,outputFileName,'fig');
export_fig(fig , '-pdf' , '-r256' , '-transparent' , outputFileName);


