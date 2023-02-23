function y = drawWDWithContributeKChangeRealDataSetExperiment(filename, xLabelName, yLabelName_left, yLabelName_right,outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y)

%data = readmatrix(path,lineStart-1,colStart-1 ,[lineStart-1,lineEnd-1,colStart-1,colEnd-1]);
%data(lineStart:end,colStart:end)
%readmatrix(path)
contributionKIndexLine = 7;
wassersteinDistanceIndexLine_1 = 8;
wassersteinDistanceIndexLine_2 = 9;

matrix = importdata(filename, ',',1);
x = matrix.data(1:5,contributionKIndexLine);


y_A_RAM = matrix.data(1:5,wassersteinDistanceIndexLine_1);
y_A_DAM = matrix.data(6:10,wassersteinDistanceIndexLine_2);
y_A_HUE = matrix.data(11:15,wassersteinDistanceIndexLine_2);

y_B_RAM = matrix.data(16:20,wassersteinDistanceIndexLine_1);
y_B_DAM = matrix.data(21:25,wassersteinDistanceIndexLine_2);
y_B_HUE = matrix.data(26:30,wassersteinDistanceIndexLine_2);


y_C_RAM = matrix.data(31:35,wassersteinDistanceIndexLine_1);
y_C_DAM = matrix.data(36:40,wassersteinDistanceIndexLine_2);
y_C_HUE = matrix.data(41:45,wassersteinDistanceIndexLine_2);


y_RAM = (y_A_RAM + y_B_RAM + y_C_RAM)/3;
y_DAM = (y_A_DAM + y_B_DAM + y_C_DAM)/3;
y_HUE = (y_A_HUE + y_B_HUE + y_C_HUE)/3;


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
fig = figure;
hold on;

xlabel(xLabelName);
%yyaxis left
plot(x, y_HUE, 'b+-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x, y_DAM, 'g*-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
ylabel(yLabelName_left);

%yyaxis right
%plot(x,y_RAM, 'ro:','LineWidth',2, 'MarkerSize',figure_MarkerSize);
%ylabel(yLabelName_right);

xlim([roundn(x(1),-1) x(length(x))]);
set(gca,'XTick',roundn(x,-1));

%figure_FontSize = 18;
set(get(gca,'XLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');

set(gca,'FontName','Times New Roman' ,'FontSize',figure_FontSize);
%set(findobj('FontSize',10),'FontSize',figure_FontSize);

set(get(gca,'XLabel'),'FontSize',figure_FontSize_X,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize_Y,'FontName','Times New Roman');

h = legend('HUEM','RAM','DAM','Location','Best');
set(h,'FontName','Times New Roman','FontSize',14,'FontWeight','normal');
legend('off');
saveas(fig,outputFileName,'fig');
export_fig(fig , '-pdf' , '-r256' , '-transparent' , outputFileName);


