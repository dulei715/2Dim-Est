function test2()
% 示例数据
x = 1:5;               % x轴的数据
y = [5, 3, 8, 2, 7];   % y轴的数据

% 绘制条形图，但不显示数据
b = bar(x, y, 'Visible', 'off');

% 添加图例
legend(b, '示例数据');

% 添加标题（可选）
title('仅显示图例');
