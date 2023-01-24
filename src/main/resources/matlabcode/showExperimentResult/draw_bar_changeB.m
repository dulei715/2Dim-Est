function draw_bar_changeB()
fig = figure;
hold on;
x_RAM_one = [1:0.1:10]; 
x_DAM_one = [1:0.1:10];
x_RAM_two = [1:0.1:10];
x_DAM_two = [1:0.1:10];
y_RAM_one = x_RAM_one * 2;
y_DAM_one = x_DAM_one * 2;
y_RAM_two = x_RAM_two * 2;
y_DAM_two = x_DAM_two * 2;
a = plot(x_RAM_one,y_RAM_one, 'ro-','LineWidth',2);
b = plot(x_DAM_one, y_DAM_one, 'g*-','LineWidth',2);

c = plot(x_RAM_two, y_RAM_two, 'rs:','LineWidth',2);
d = plot(x_DAM_two, y_DAM_two, 'gx:', 'LineWidth', 2);

a.Visible='off';
b.Visible='off';
c.Visible='off';
d.Visible='off';

legend_FontSize = 20;
locationType = 'northoutside';
orientationType = 'horizontal';
textColor = 'black';
h = legend('RAM-Crime', 'DAM-Crime','RAM-NYC','DAM-NYC','Location',locationType,'Orientation',orientationType, 'TextColor', textColor);
set(h,'FontName','Times New Roman','FontSize',legend_FontSize,'FontWeight','normal');
axis off;