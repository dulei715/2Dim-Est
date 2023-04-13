function zDrawAll_extended_only_for_KL()
inputBasicPath = 'E:\1.学习\4.数据集\2.dataset_for_spatial_estimation\result_extended_KL';
outputBasicPath = 'E:\1.学习\4.论文\程鹏\讨论补充\github上传\TwoDimensionalLDPEstimation\tex\figures\result_extended_KL';
%outputBasicPath = 'E:\result2';
yLabelName_left = 'KL';
%yLabelName_right = 'W_1^1';
figure_MarkerSize = 15;
figure_FontSize = 30;
figure_FontSize_X = 30;
figure_FontSize_Y = 30;
%figure_FontSize_Y_B = 22;
%% altering sizeD (gridG)
xLabelName = 'd';
% Crime
inputFileName = [inputBasicPath, '\crime', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_crime_extended_KL'];
extended_drawKLWithGridGChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName_left, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% NYC
inputFileName = [inputBasicPath, '\nyc', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_nyc_extended_KL'];
extended_drawKLWithGridGChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName_left, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-normal
inputFileName = [inputBasicPath, '\normal', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_normal_extended_KL'];
extended_drawKLWithGridGChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-Zipf
inputFileName = [inputBasicPath, '\zipf', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_zipf_extended_KL'];
extended_drawKLWithGridGChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-nomal-multiple-centers
inputFileName = [inputBasicPath, '\normal_multi_centers', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_normal_multiple_centers_extended_KL'];
extended_drawKLWithGridGChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

%% altering privacy budget (epsilon)
xLabelName = '\epsilon';
% Crime
inputFileName = [inputBasicPath, '\crime', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_crime_extended_KL'];
extended_drawKLWithPBChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName_left, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% NYC
inputFileName = [inputBasicPath, '\nyc', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_nyc_extended_KL'];
extended_drawKLWithPBChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName_left, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-normal
inputFileName = [inputBasicPath, '\normal', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_normal_extended_KL'];
extended_drawKLWithPBChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-Zipf
inputFileName = [inputBasicPath, '\zipf', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_zipf_extended_KL'];
extended_drawKLWithPBChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-nomal-multiple-centers
inputFileName = [inputBasicPath, '\normal_multi_centers', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_normal_multiple_centers_extended_KL'];
extended_drawKLWithPBChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);
