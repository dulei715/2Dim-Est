function y = drawWDWithSizeBChangeRealDataSetExperiment(filenameOne,filenameTwo, xLabelName, yLabelName,outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y)

%data = readmatrix(path,lineStart-1,colStart-1 ,[lineStart-1,lineEnd-1,colStart-1,colEnd-1]);
%data(lineStart:end,colStart:end)
%readmatrix(path)
sizeBIndexLine = 5;
wassersteinDistanceIndexLine = 8;

matrixOne = importdata(filenameOne, ',',1);
x_RAM_one = matrixOne.data(1:5,sizeBIndexLine);
x_DAM_one = matrixOne.data(6:10,sizeBIndexLine);

y_A_RAM = matrixOne.data(1:5,wassersteinDistanceIndexLine);
y_A_DAM = matrixOne.data(6:10,wassersteinDistanceIndexLine);
y_B_RAM = matrixOne.data(11:15,wassersteinDistanceIndexLine);
y_B_DAM = matrixOne.data(16:20,wassersteinDistanceIndexLine);
y_C_RAM = matrixOne.data(21:25,wassersteinDistanceIndexLine);
y_C_DAM = matrixOne.data(26:30,wassersteinDistanceIndexLine);

y_RAM_one = (y_A_RAM + y_B_RAM + y_C_RAM)/3;
y_DAM_one = (y_A_DAM + y_B_DAM + y_C_DAM)/3;

%%%%%%%%%%%%%%%%%%%%%%%%%%
matrixTwo = importdata(filenameTwo, ',',1);
x_RAM_two = matrixTwo.data(1:5,sizeBIndexLine);
x_DAM_two = matrixTwo.data(6:10,sizeBIndexLine);

y_A_RAM = matrixTwo.data(1:5,wassersteinDistanceIndexLine);
y_A_DAM = matrixTwo.data(6:10,wassersteinDistanceIndexLine);
y_B_RAM = matrixTwo.data(11:15,wassersteinDistanceIndexLine);
y_B_DAM = matrixTwo.data(16:20,wassersteinDistanceIndexLine);
y_C_RAM = matrixTwo.data(21:25,wassersteinDistanceIndexLine);
y_C_DAM = matrixTwo.data(26:30,wassersteinDistanceIndexLine);

y_RAM_two = (y_A_RAM + y_B_RAM + y_C_RAM)/3;
y_DAM_two = (y_A_DAM + y_B_DAM + y_C_DAM)/3;



%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
fig = figure;
hold on;

plot(x_RAM_one,y_RAM_one, 'ro-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x_DAM_one, y_DAM_one, 'g*-','LineWidth',2, 'MarkerSize',figure_MarkerSize);

plot(x_RAM_two, y_RAM_two, 'rs:','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x_DAM_two, y_DAM_two, 'gx:', 'LineWidth', 2, 'MarkerSize',figure_MarkerSize);

%xlim([min([roundn(x_RAM_one(1),-1),roundn(x_DAM_one(1),-1),roundn(x_RAM_two(1),-1),roundn(x_DAM_two(1),-1)]) max([x_RAM_one(length(x_RAM_one)),x_DAM_one(length(x_DAM_one)),x_RAM_two(length(x_RAM_two)),x_DAM_two(length(x_DAM_two))])]);
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

xlabel(xLabelName);
ylabel(yLabelName);
set(get(gca,'XLabel'),'FontSize',figure_FontSize_X,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize_Y,'FontName','Times New Roman');

h = legend('RAM-Crime', 'DAM-Crime','RAM-NYC','DAM-NYC','Location','Best');
set(h,'FontName','Times New Roman','FontSize',14,'FontWeight','normal');
legend('off');
saveas(fig,outputFileName,'fig');
export_fig(fig , '-pdf' , '-r256' , '-transparent' , outputFileName);


