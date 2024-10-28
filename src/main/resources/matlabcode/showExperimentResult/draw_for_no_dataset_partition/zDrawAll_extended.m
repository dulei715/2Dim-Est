function zDrawAll_extended()
inputBasicPath = 'E:\dataset\2.dataset_for_spatial_estimation\result_extended';
%inputBasicPath = 'E:\1.学习\4.数据集\2.dataset_for_spatial_estimation\result_extended';
%outputBasicPath = 'E:\1.学习\4.论文\程鹏\讨论补充\github上传\TwoDimensionalLDPEstimation\tex\figures\result_extended';
outputBasicPath = 'E:\2.github\TwoDimensionalLDPEstimation\tex\figures\result_extended';
%outputBasicPath = 'D:\workspace\5.github\TwoDimensionalLDPEstimation\tex\figures\result_extended';
%outputBasicPath = 'E:\result2';
%yLabelName_left = 'W_2^2';
yLabelName_left = 'W_2';
yLabelName_right = 'W_1^1';
figure_MarkerSize = 15;
figure_FontSize = 30;
figure_FontSize_X = 30;
figure_FontSize_Y = 30;
%figure_FontSize_Y_B = 22;
%% altering sizeD (gridG)
xLabelName = 'd';
% Crime
inputFileName = [inputBasicPath, '\crime', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_crime_extended'];
extended_drawWDWithGridGChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% NYC
inputFileName = [inputBasicPath, '\nyc', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_nyc_extended'];
extended_drawWDWithGridGChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-normal
inputFileName = [inputBasicPath, '\normal', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_normal_extended'];
extended_drawWDWithGridGChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-Zipf
inputFileName = [inputBasicPath, '\zipf', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_zipf_extended'];
extended_drawWDWithGridGChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-nomal-multiple-centers
inputFileName = [inputBasicPath, '\normal_multi_centers', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_normal_multiple_centers_extended'];
extended_drawWDWithGridGChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

%% altering privacy budget (epsilon)
xLabelName = '\epsilon';
% Crime
inputFileName = [inputBasicPath, '\crime', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_crime_extended'];
extended_drawWDWithPBChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName_left, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% NYC
inputFileName = [inputBasicPath, '\nyc', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_nyc_extended'];
extended_drawWDWithPBChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName_left, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-normal
inputFileName = [inputBasicPath, '\normal', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_normal_extended'];
extended_drawWDWithPBChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-Zipf
inputFileName = [inputBasicPath, '\zipf', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_zipf_extended'];
extended_drawWDWithPBChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-nomal-multiple-centers
inputFileName = [inputBasicPath, '\normal_multi_centers', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_normal_multiple_centers_extended'];
extended_drawWDWithPBChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);
