function y = drawWDWithSizeBChangeSyntheticDataSetExperiment(filenameOne,filenameTwo, filenameThree, xLabelName, yLabelName_left, yLabelName_right,outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y)

%data = readmatrix(path,lineStart-1,colStart-1 ,[lineStart-1,lineEnd-1,colStart-1,colEnd-1]);
%data(lineStart:end,colStart:end)
%readmatrix(path)
sizeBIndexLine = 5;
wassersteinDistanceIndexLine_1 = 8;
wassersteinDistanceIndexLine_2 = 9;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
matrixOne = importdata(filenameOne, ',',1);
x_RAM_one = matrixOne.data(1:5,sizeBIndexLine);
x_DAM_one = matrixOne.data(6:10,sizeBIndexLine);


y_RAM_one = matrixOne.data(1:5,wassersteinDistanceIndexLine_1);
y_DAM_one = matrixOne.data(6:10,wassersteinDistanceIndexLine_2);

%%%%%%%%%%%%%%%%%%%%%%%%%%
matrixTwo = importdata(filenameTwo, ',',1);
x_RAM_two = matrixTwo.data(1:5,sizeBIndexLine);
x_DAM_two = matrixTwo.data(6:10,sizeBIndexLine);


y_RAM_two = matrixTwo.data(1:5,wassersteinDistanceIndexLine_1);
y_DAM_two = matrixTwo.data(6:10,wassersteinDistanceIndexLine_2);

%%%%%%%%%%%%%%%%%%%%%%%%%%
matrixThree = importdata(filenameThree, ',',1);
x_RAM_three = matrixThree.data(1:5,sizeBIndexLine);
x_DAM_three = matrixThree.data(6:10,sizeBIndexLine);


y_RAM_three = matrixThree.data(1:5,wassersteinDistanceIndexLine_1);
y_DAM_three = matrixThree.data(6:10,wassersteinDistanceIndexLine_2);




%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
fig = figure;
hold on;

xlabel(xLabelName);

%yyaxis left
plot(x_DAM_one, y_DAM_one, 'g*-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x_DAM_two, y_DAM_two, 'gx-', 'LineWidth', 2, 'MarkerSize',figure_MarkerSize);
plot(x_DAM_three, y_DAM_three, 'gd-', 'LineWidth', 2, 'MarkerSize',figure_MarkerSize);
ylabel(yLabelName_left);

%yyaxis right
%plot(x_RAM_one,y_RAM_one, 'ro:','LineWidth',2, 'MarkerSize',figure_MarkerSize);
%plot(x_RAM_two, y_RAM_two, 'rs:','LineWidth',2, 'MarkerSize',figure_MarkerSize);
%plot(x_RAM_three, y_RAM_three, 'r+:','LineWidth',2, 'MarkerSize',figure_MarkerSize);
%ylabel(yLabelName_right);

x = sort(unique([roundn(x_RAM_one,-1), roundn(x_DAM_one,-1), roundn(x_RAM_two,-1), roundn(x_DAM_two,-1)]));
if length(x) > 1
   xlim([roundn(x(1),-1) x(length(x))]); 
end
set(gca,'XTick',roundn(x,-1));

%plot(x_C_RAM, y_C_RAM, 'r+:','LineWidth',2);
%plot(x_C_DAM, y_C_DAM, 'gd:', 'LineWidth', 2);

%figure_FontSize = 18;
set(get(gca,'XLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');

set(gca,'FontName','Times New Roman' ,'FontSize',figure_FontSize);
%set(findobj('FontSize',10),'FontSize',figure_FontSize);

set(get(gca,'XLabel'),'FontSize',figure_FontSize_X,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize_Y,'FontName','Times New Roman');

h = legend('DAM-Normal','DAM-Zipf','DAM-MC-Normal','RAM-Normal', 'RAM-Zipf','RAM-MC-Normal','Location','Best');
set(h,'FontName','Times New Roman','FontSize',14,'FontWeight','normal');
legend('off');
saveas(fig,outputFileName,'fig');
export_fig(fig , '-pdf' , '-r256' , '-transparent' , outputFileName);


