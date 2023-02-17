function draw_bar_changeB_2()
fig = figure;
hold on;

figure_MarkerSize = 15;
legend_FontSize = 20;

x_RAM_one = [1:0.1:10]; 
x_DAM_one = [1:0.1:10];
x_RAM_two = [1:0.1:10];
x_DAM_two = [1:0.1:10];
x_RAM_three = [1:0.1:10];
x_DAM_three = [1:0.1:10];
y_RAM_one = x_RAM_one * 2;
y_DAM_one = x_DAM_one * 2;
y_RAM_two = x_RAM_two * 2;
y_DAM_two = x_DAM_two * 2;
y_RAM_three = x_RAM_three * 2;
y_DAM_three = x_DAM_three * 2;
a = plot(x_RAM_one,y_RAM_one, 'ro-','LineWidth',2,'MarkerSize',figure_MarkerSize);
b = plot(x_DAM_one, y_DAM_one, 'g*-','LineWidth',2,'MarkerSize',figure_MarkerSize);

c = plot(x_RAM_two, y_RAM_two, 'rs:','LineWidth',2,'MarkerSize',figure_MarkerSize);
d = plot(x_DAM_two, y_DAM_two, 'gx:', 'LineWidth', 2,'MarkerSize',figure_MarkerSize);

e = plot(x_RAM_three, y_RAM_three, 'r+:','LineWidth',2,'MarkerSize',figure_MarkerSize);
f = plot(x_DAM_three, y_DAM_three, 'g+:', 'LineWidth', 2,'MarkerSize',figure_MarkerSize);


a.Visible='off';
b.Visible='off';
c.Visible='off';
d.Visible='off';
e.Visible='off';
f.Visible='off';

locationType = 'northoutside';
orientationType = 'horizontal';
textColor = 'black';
h = legend('RAM-Normal', 'DAM-Normal','RAM-Zipf','DAM-Zipf','RAM-MC-Normal','DAM-MC-Normal','Location',locationType,'Orientation',orientationType, 'TextColor', textColor);
set(h,'FontName','Times New Roman','FontSize',legend_FontSize,'FontWeight','normal');
axis off;