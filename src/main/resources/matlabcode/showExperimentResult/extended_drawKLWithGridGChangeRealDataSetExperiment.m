function y = extended_drawKLWithGridGChangeRealDataSetExperiment(filename, xLabelName, yLabelName_left, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y)

sizeDIndexLine = 4;
kl_divergence = 8;

matrix = importdata(filename, ',',1);
x = matrix.data(1:5,sizeDIndexLine);


y_A_DAM = matrix.data(1:5,kl_divergence);
y_A_SubGeoI_2 = matrix.data(6:10,kl_divergence);


y_B_DAM = matrix.data(11:15,kl_divergence);
y_B_SubGeoI_2 = matrix.data(16:20,kl_divergence);

y_C_DAM = matrix.data(21:25,kl_divergence);
y_C_SubGeoI_2 = matrix.data(26:30,kl_divergence);


y_DAM = (y_A_DAM + y_B_DAM + y_C_DAM)/3;
y_SubGeoI_2 = (y_A_SubGeoI_2 + y_B_SubGeoI_2 + y_C_SubGeoI_2)/3;


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
fig = figure;
hold on;

xlabel(xLabelName);

%yyaxis left
plot(x, y_SubGeoI_2, 'ks-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
plot(x, y_DAM, 'ro-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
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

h = legend('SubGeoI_2', 'DAM','Location','Best');
set(h,'FontName','Times New Roman','FontSize',14,'FontWeight','normal');
legend('off');
saveas(fig,outputFileName,'fig');
export_fig(fig , '-pdf' , '-r256' , '-transparent' , outputFileName);


