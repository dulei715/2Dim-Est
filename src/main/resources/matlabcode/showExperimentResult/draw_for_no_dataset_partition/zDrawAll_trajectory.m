function zDrawAll_trajectory()
inputBasicPath = 'E:\dataset\2.dataset_for_spatial_estimation\result_trajectory';
outputBasicPath = 'E:\2.github\TwoDimensionalLDPEstimation\tex\figures\result_trajectory';

yLabelName_left = 'W_2';
yLabelName_right = 'W_1^1';
figure_MarkerSize = 15;
figure_FontSize = 30;
figure_FontSize_X = 30;
figure_FontSize_Y = 30;
%figure_FontSize_Y_B = 22;
%% altering sizeD (gridG)
xLabelName = 'd';

% NYC
inputFileName = [inputBasicPath, '\nyc', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_nyc_trajectory'];
trajectory_drawWDWithGridGChangeExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

%% altering privacy budget (epsilon)
xLabelName = '\epsilon';

% NYC
inputFileName = [inputBasicPath, '\nyc', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_nyc_trajectory'];
trajectory_drawWDWithPBChangeExperiment(inputFileName, xLabelName, yLabelName_left, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);
