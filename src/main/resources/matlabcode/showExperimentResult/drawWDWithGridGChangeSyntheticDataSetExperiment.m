function y = drawWDWithGridGChangeSyntheticDataSetExperiment(filename, xLabelName, yLabelName,outputFileName)

%data = readmatrix(path,lineStart-1,colStart-1 ,[lineStart-1,lineEnd-1,colStart-1,colEnd-1]);
%data(lineStart:end,colStart:end)
%readmatrix(path)
sizeDIndexLine = 4;
wassersteinDistanceIndexLine = 8;

matrix = importdata(filename, ',',1);
x = matrix.data(1:5,sizeDIndexLine);


y_A_RAM = matrix.data(1:5,wassersteinDistanceIndexLine);
y_A_DAM = matrix.data(6:10,wassersteinDistanceIndexLine);
y_A_SubGeoI = matrix.data(11:15,wassersteinDistanceIndexLine);
y_A_MDSW = matrix.data(16:20,wassersteinDistanceIndexLine);
y_A_HUE = matrix.data(21:25,wassersteinDistanceIndexLine);



y_RAM = matrix.data(1:5,wassersteinDistanceIndexLine);
y_DAM = matrix.data(6:10,wassersteinDistanceIndexLine);
y_SubGeoI = matrix.data(11:15,wassersteinDistanceIndexLine);
y_MDSW = matrix.data(16:20,wassersteinDistanceIndexLine);
y_HUE = matrix.data(21:25,wassersteinDistanceIndexLine);


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
fig = figure;
hold on;


plot(x, y_SubGeoI, 'ks-','LineWidth',2);
plot(x, y_MDSW, 'mx-', 'LineWidth', 2);
plot(x, y_HUE, 'b+-','LineWidth',2);
plot(x,y_RAM, 'ro-','LineWidth',2);
plot(x, y_DAM, 'g*-','LineWidth',2);
%plot(x_C_DAM, y_C_DAM, 'gd:', 'LineWidth', 2);

figure_FontSize = 18;
set(get(gca,'XLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize,'FontName','Times New Roman');

set(gca,'FontName','Times New Roman' ,'FontSize',figure_FontSize);
set(findobj('FontSize',10),'FontSize',figure_FontSize);

xlabel(xLabelName);
ylabel(yLabelName);
h = legend('SubGeoI', 'MDSW','HUEM','RAM','DAM','Location','Best');
set(h,'FontName','Times New Roman','FontSize',14,'FontWeight','normal');

saveas(fig,outputFileName,'fig');
export_fig(fig , '-pdf' , '-r256' , '-transparent' , outputFileName);


