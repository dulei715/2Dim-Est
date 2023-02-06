function draw_bar_changeOthers()
fig = figure;
hold on;
x = [1:0.1:10];  
y_SubGeoI = x;
y_MDSW = x;
y_HUE = x;
y_RAM = x;
y_DAM = x;

a = plot(x, y_SubGeoI, 'ks-','LineWidth',2);
b = plot(x, y_MDSW, 'mx-', 'LineWidth', 2);
c = plot(x, y_HUE, 'b+-','LineWidth',2);
d = plot(x,y_RAM, 'ro-','LineWidth',2);
e = plot(x, y_DAM, 'g*-','LineWidth',2);


a.Visible='off';
b.Visible='off';
c.Visible='off';
d.Visible='off';
e.Visible='off';

legend_FontSize = 20;
locationType = 'northoutside';
orientationType = 'horizontal';
textColor = 'black';
h = legend('SubGeoI', 'MDSW','HUEM','RAM','DAM','Location',locationType,'Orientation',orientationType, 'TextColor', textColor);
set(h,'FontName','Times New Roman','FontSize',legend_FontSize,'FontWeight','normal');
axis off;