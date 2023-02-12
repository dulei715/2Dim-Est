function y = drawWDWithGridGChangeRealDataSetExperiment(filename, xLabelName, yLabelName,outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y)

%data = readmatrix(path,lineStart-1,colStart-1 ,[lineStart-1,lineEnd-1,colStart-1,colEnd-1]);
%data(lineStart:end,colStart:end)
%readmatrix(path)
sizeDIndexLine = 4;
wassersteinDistanceIndexLine = 8;

matrix = importdata(filename, ',',1);
x = matrix.data(1:5,sizeDIndexLine);


y_A_RAM = matrix.data(1:5,wassersteinDistanceIndexLine);
y_A_DAM = matrix.data(6:10,wassersteinDistanceIndexLine);
y_A_MDSW = matrix.data(11:15,wassersteinDistanceIndexLine);
y_A_HUE = matrix.data(16:20,wassersteinDistanceIndexLine);
y_A_DAMShrink = matrix.data(21:25,wassersteinDistanceIndexLine);
y_A_SubGeoI = matrix.data(26:30,wassersteinDistanceIndexLine);

y_B_RAM = matrix.data(31:35,wassersteinDistanceIndexLine);
y_B_DAM = matrix.data(36:40,wassersteinDistanceIndexLine);
y_B_MDSW = matrix.data(41:45,wassersteinDistanceIndexLine);
y_B_HUE = matrix.data(46:50,wassersteinDistanceIndexLine);
y_B_DAMShrink = matrix.data(51:55,wassersteinDistanceIndexLine);
y_B_SubGeoI = matrix.data(56:60,wassersteinDistanceIndexLine);


y_C_RAM = matrix.data(61:65,wassersteinDistanceIndexLine);
y_C_DAM = matrix.data(66:70,wassersteinDistanceIndexLine);
y_C_MDSW = matrix.data(71:75,wassersteinDistanceIndexLine);
y_C_HUE = matrix.data(76:80,wassersteinDistanceIndexLine);
y_C_DAMShrink = matrix.data(81:85,wassersteinDistanceIndexLine);
y_C_SubGeoI = matrix.data(86:90,wassersteinDistanceIndexLine);


y_RAM = (y_A_RAM + y_B_RAM + y_C_RAM)/3;
y_DAM = (y_A_DAM + y_B_DAM + y_C_DAM)/3;
y_DAMShrink = (y_A_DAMShrink + y_B_DAMShrink + y_C_DAMShrink);
y_SubGeoI = (y_A_SubGeoI + y_B_SubGeoI + y_C_SubGeoI)/3;
y_MDSW = (y_A_MDSW + y_B_MDSW + y_C_MDSW)/3;
y_HUE = (y_A_HUE + y_B_HUE + y_C_HUE)/3;


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
fig = figure;
hold on;


plot(x, y_SubGeoI, 'ks-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x, y_MDSW, 'mx-', 'LineWidth', 2, 'MarkerSize',figure_MarkerSize);
plot(x, y_HUE, 'b+-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x, y_RAM, 'ro-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x, y_DAM, 'g*-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x, y_DAMShrink, 'cd:','LineWidth',2, 'MarkerSize',figure_MarkerSize);
%plot(x_C_DAM, y_C_DAM, 'gd:', 'LineWidth', 2);

xlim([roundn(x(1),-1) x(length(x))]);
set(gca,'XTick',roundn(x,-1));

%figure_FontSize = 18;
set(get(gca,'XLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');

set(gca,'FontName','Times New Roman' ,'FontSize',figure_FontSize);
%set(findobj('FontSize',10),'FontSize',figure_FontSize);

xlabel(xLabelName);
ylabel(yLabelName);
set(get(gca,'XLabel'),'FontSize',figure_FontSize_X,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize_Y,'FontName','Times New Roman');

h = legend('SubGeoI', 'MDSW','HUEM','RAM','DAM','DAMShrink','Location','Best');
set(h,'FontName','Times New Roman','FontSize',14,'FontWeight','normal');
legend('off');
saveas(fig,outputFileName,'fig');
export_fig(fig , '-pdf' , '-r256' , '-transparent' , outputFileName);


