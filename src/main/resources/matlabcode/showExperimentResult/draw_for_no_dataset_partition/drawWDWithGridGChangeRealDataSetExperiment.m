function y = drawWDWithGridGChangeRealDataSetExperiment(filename, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y)

%data = readmatrix(path,lineStart-1,colStart-1 ,[lineStart-1,lineEnd-1,colStart-1,colEnd-1]);
%data(lineStart:end,colStart:end)
%readmatrix(path)
sizeDIndexLine = 4;
wassersteinDistanceIndexLine_2 = 9;

matrix = importdata(filename, ',',1);
x = matrix.data(11:15,sizeDIndexLine);


y_A_DAM = matrix.data(1:5,wassersteinDistanceIndexLine_2);
y_A_MDSW = matrix.data(6:10,wassersteinDistanceIndexLine_2);
y_A_HUE = matrix.data(11:15,wassersteinDistanceIndexLine_2);
y_A_DAMShrink = matrix.data(16:20,wassersteinDistanceIndexLine_2);
y_A_SubGeoI_2 = matrix.data(21:25,wassersteinDistanceIndexLine_2);



y_DAM = y_A_DAM;
y_DAMShrink = y_A_DAMShrink ;
y_SubGeoI_2 = y_A_SubGeoI_2 ;
y_MDSW = y_A_MDSW;
y_HUE = y_A_HUE ;


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
fig = figure;
hold on;

xlabel(xLabelName);

%yyaxis left
plot(x, y_SubGeoI_2, 'ks-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x, y_MDSW, 'mx-', 'LineWidth', 2, 'MarkerSize',figure_MarkerSize);
plot(x, y_HUE, 'b+-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x, y_DAM, 'ro-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x, y_DAMShrink, 'cd-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
ylabel(yLabelName_left);

%yyaxis right
%plot(x, y_SubGeoI_1, 'yx:','LineWidth',2, 'MarkerSize',figure_MarkerSize);
%plot(x, y_RAM, 'ro:','LineWidth',2, 'MarkerSize',figure_MarkerSize);
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

%h = legend('SubGeoI_2', 'MDSW','HUEM','DAM','DAMShrink','SubGeoI_1','RAM','Location','Best');
h = legend('SubGeoI_2', 'MDSW','HUEM','DAM','DAMShrink','Location','Best');
set(h,'FontName','Times New Roman','FontSize',14,'FontWeight','normal');
legend('off');
saveas(fig,outputFileName,'fig');
export_fig(fig , '-pdf' , '-r256' , '-transparent' , outputFileName);


