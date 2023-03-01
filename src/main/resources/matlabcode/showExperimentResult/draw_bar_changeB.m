function draw_bar_changeB()
fig = figure;
hold on;

figure_MarkerSize = 15;
legend_FontSize = 20;

x_DAM_Crime = [1:0.1:10]; 
x_DAM_NYC = [1:0.1:10];
x_DAM_Normal = [1:0.1:10];
x_DAM_Zipf = [1:0.1:10];
x_DAM_MCNormal = [1:0.1:10];

y_DAM_Crime = x_DAM_Crime * 2;
y_DAM_NYC = x_DAM_NYC * 2;
y_DAM_Normal = x_DAM_Normal * 2;
y_DAM_Zipf = x_DAM_Zipf * 2;
y_DAM_MCNormal = x_DAM_MCNormal * 2;

a = plot(x_DAM_Crime,y_DAM_Crime, 'rs-','LineWidth',2,'MarkerSize',figure_MarkerSize);
b = plot(x_DAM_NYC, y_DAM_NYC, 'rx-','LineWidth',2,'MarkerSize',figure_MarkerSize);
c = plot(x_DAM_Normal, y_DAM_Normal, 'g+:','LineWidth',2,'MarkerSize',figure_MarkerSize);
d = plot(x_DAM_Zipf, y_DAM_Zipf, 'go:', 'LineWidth', 2,'MarkerSize',figure_MarkerSize);
e = plot(x_DAM_Zipf, y_DAM_Zipf, 'g*:', 'LineWidth', 2,'MarkerSize',figure_MarkerSize);

a.Visible='off';
b.Visible='off';
c.Visible='off';
d.Visible='off';
e.Visible='off';

locationType = 'northoutside';
orientationType = 'horizontal';
textColor = 'black';
%h = legend('RAM-Crime', 'DAM-Crime','RAM-NYC','DAM-NYC','Location',locationType,'Orientation',orientationType, 'TextColor', textColor);
h = legend('DAM-Crime','DAM-NYC','DAM-Normal','DAM-Zipf','DAM-MC-Normal','Location',locationType,'Orientation',orientationType, 'TextColor', textColor);
set(h,'FontName','Times New Roman','FontSize',legend_FontSize,'FontWeight','normal');
axis off;