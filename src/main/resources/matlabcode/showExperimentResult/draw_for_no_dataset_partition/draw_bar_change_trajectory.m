function draw_bar_change_trajectory()
fig = figure;
hold on;

figure_MarkerSize = 15;
legend_FontSize = 20;

x = [1:0.1:10];  
y_LDPTrace = x;
y_PivotTrace = x;
y_DAM = x;

a = plot(x, y_LDPTrace, 'ks-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
b = plot(x, y_PivotTrace, 'b+-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
c = plot(x, y_DAM, 'ro-','LineWidth',2, 'MarkerSize',figure_MarkerSize);
%ylabel(yLabelName_left);


a.Visible='off';
b.Visible='off';
c.Visible='off';

locationType = 'northoutside';
orientationType = 'horizontal';
textColor = 'black';
%h = legend('SubGeoI', 'MDSW','HUEM','RAM','DAM','DAM-NS','Location',locationType,'Orientation',orientationType, 'TextColor', textColor);
h = legend('LDPTrace', 'PivotTrace','DAM','Location',locationType,'Orientation',orientationType, 'TextColor', textColor);
set(h,'FontName','Times New Roman','FontSize',legend_FontSize,'FontWeight','normal');
axis off;