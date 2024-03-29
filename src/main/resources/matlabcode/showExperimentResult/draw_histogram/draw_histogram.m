function y = draw_histogram()
inputBasicPath = 'E:\1.ѧϰ\4.���ݼ�\2.dataset_for_spatial_estimation\result';
outputBasicPath = 'D:\workspace\5.github\TwoDimensionalLDPEstimation\tex\figures\result_histogram';

inputFileNameA = [inputBasicPath, '\crime', '\alteringB.csv'];
inputFileNameB = [inputBasicPath, '\nyc', '\alteringB.csv'];


crime_data = getBChangeRealDataSetResult(inputFileNameA);
synthetic_data = getBChangeRealDataSetResult(inputFileNameB);

inputFileNameD = [inputBasicPath, '\normal', '\alteringB.csv'];
inputFileNameE = [inputBasicPath, '\zipf', '\alteringB.csv'];
inputFileNameF = [inputBasicPath, '\normal_multi_centers', '\alteringB.csv'];



normal_data = getBChangeSyntheticDataSetResult(inputFileNameD);
zipf_data = getBChangeSyntheticDataSetResult(inputFileNameE);
normal_multi_data = getBChangeSyntheticDataSetResult(inputFileNameF);

x_data = crime_data(:,1);
y_data = [crime_data(:,2), synthetic_data(:,2), normal_data(:,2), zipf_data(:,2), normal_multi_data(:,2)];
fig = figure;
h = bar(x_data,y_data);
%h(1).EdgeColor='r';
%h(2).EdgeColor='b';
%h(3).EdgeColor='c';
%h(4).EdgeColor='k';
%h(5).EdgeColor='m';


yLabelName_left = 'W_2';
figure_MarkerSize = 15;
figure_FontSize_A = 13;
figure_FontSize_B = 10;
figure_FontSize_X = 30;
figure_FontSize_Y = 30;

xLabelName = 'b';
lgnd = legend('Crime','NYC','Normal', 'SZipf','MNormal','Orientation','horizontal','Location','northeast');

set(get(gca,'XLabel'),'FontSize',figure_FontSize_A,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize_A,'FontName','Times New Roman');

set(gca,'FontName','Times New Roman' ,'FontSize',figure_FontSize_A);

set(get(gca,'XLabel'),'FontSize',figure_FontSize_X,'FontName','Times New Roman');
set(get(gca,'YLabel'),'FontSize',figure_FontSize_Y,'FontName','Times New Roman');

set(lgnd,'EdgeColor','none');
set(lgnd, 'FontSize', figure_FontSize_B);

set(fig,'Position',[650 550 600 240]);

outputFileName = [outputBasicPath, '\alter_b_datasets'];
saveas(fig,outputFileName,'fig');
export_fig(fig , '-pdf' , '-r256' , '-transparent' , outputFileName);











