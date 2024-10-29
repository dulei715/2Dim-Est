function y = trajectory_drawWDWithGridGChangeExperiment(filename, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y)

sizeDIndexLine = 4;
wassersteinDistanceIndexLine_2 = 9;

matrix = importdata(filename, ',',1);
x = matrix.data(1:5,sizeDIndexLine);


y_DAM = matrix.data(1:5,wassersteinDistanceIndexLine_2);
y_PivotTrace = matrix.data(6:10,wassersteinDistanceIndexLine_2);
y_LDPTrace = matrix.data(11:15,wassersteinDistanceIndexLine_2);


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
fig = figure;
hold on;

xlabel(xLabelName);

%yyaxis left
plot(x, y_LDPTrace, 'ks-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x, y_PivotTrace, 'b+-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x, y_DAM, 'ro-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
ylabel(yLabelName_left);


xlim([roundn(x(1),-1) x(length(x))]);
set(gca,'XTick',roundn(x,-1));

%figure_FontSize = 18;
set(get(gca,'XLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');

set(gca,'FontName','Times New Roman' ,'FontSize',figure_FontSize);
%set(findobj('FontSize',10),'FontSize',figure_FontSize);



set(get(gca,'XLabel'),'FontSize',figure_FontSize_X,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize_Y,'FontName','Times New Roman');

h = legend('SubGeoI_2', 'DAM','Location','Best');
set(h,'FontName','Times New Roman','FontSize',14,'FontWeight','normal');
legend('off');
saveas(fig,outputFileName,'fig');
export_fig(fig , '-pdf' , '-r256' , '-transparent' , outputFileName);


