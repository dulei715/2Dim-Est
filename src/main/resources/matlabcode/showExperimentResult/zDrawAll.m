function zDrawAll()
inputBasicPath = 'E:\1.学习\4.数据集\2.dataset_for_spatial_estimation\result';
outputBasicPath = 'E:\1.学习\4.论文\程鹏\讨论补充\github上传\TwoDimensionalLDPEstimation\tex\figures\result';
%outputBasicPath = 'E:\result2';
yLabelName_left = 'W_2^2';
yLabelName_right = 'W_1^1';
figure_MarkerSize = 15;
figure_FontSize = 30;
figure_FontSize_X = 30;
figure_FontSize_Y = 30;
%figure_FontSize_Y_B = 22;
%% altering sizeB
xLabelName = 'b';

% real data sets (Crime and NYC)
inputFileNameA = [inputBasicPath, '\crime', '\alteringB.csv'];
inputFileNameB = [inputBasicPath, '\nyc', '\alteringB.csv'];
outputFileName = [outputBasicPath, '\alter_b_real_datasets'];
drawWDWithSizeBChangeRealDataSetExperiment(inputFileNameA, inputFileNameB, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% sythetic data sets (2-normal, 2-Zipf and 2-normal-multiple-centers)
inputFileNameA = [inputBasicPath, '\normal', '\alteringB.csv'];
inputFileNameB = [inputBasicPath, '\zipf', '\alteringB.csv'];
inputFileNameC = [inputBasicPath, '\normal_multi_centers', '\alteringB.csv'];
outputFileName = [outputBasicPath, '\alter_b_synthetic_datasets'];
drawWDWithSizeBChangeSyntheticDataSetExperiment(inputFileNameA, inputFileNameB, inputFileNameC, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

%% altering sizeD (gridG)
xLabelName = 'd';
% Crime
inputFileName = [inputBasicPath, '\crime', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_crime'];
drawWDWithGridGChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% NYC
inputFileName = [inputBasicPath, '\nyc', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_nyc'];
drawWDWithGridGChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-normal
inputFileName = [inputBasicPath, '\normal', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_normal'];
drawWDWithGridGChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-Zipf
inputFileName = [inputBasicPath, '\zipf', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_zipf'];
drawWDWithGridGChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-nomal-multiple-centers
inputFileName = [inputBasicPath, '\normal_multi_centers', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_normal_multiple_centers'];
drawWDWithGridGChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

%% altering privacy budget
xLabelName = '\epsilon';
% Crime
inputFileName = [inputBasicPath, '\crime', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_crime'];
drawWDWithPrivacyBudgetChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% NYC
inputFileName = [inputBasicPath, '\nyc', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_nyc'];
drawWDWithPrivacyBudgetChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-normal
inputFileName = [inputBasicPath, '\normal', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_normal'];
drawWDWithPrivacyBudgetChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-Zipf
inputFileName = [inputBasicPath, '\zipf', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_zipf'];
drawWDWithPrivacyBudgetChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-nomal-multiple-centers
inputFileName = [inputBasicPath, '\normal_multi_centers', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_normal_multiple_centers'];
drawWDWithPrivacyBudgetChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);



%% altering sizeK
xLabelName = 'k';

% Crime
inputFileName = [inputBasicPath, '\crime', '\alteringK.csv'];
outputFileName = [outputBasicPath, '\alter_k_crime'];
drawWDWithContributeKChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% NYC
inputFileName = [inputBasicPath, '\nyc', '\alteringK.csv'];
outputFileName = [outputBasicPath, '\alter_k_nyc'];
drawWDWithContributeKChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-normal
inputFileName = [inputBasicPath, '\normal', '\alteringK.csv'];
outputFileName = [outputBasicPath, '\alter_k_normal'];
drawWDWithContributeKChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-Zipf
inputFileName = [inputBasicPath, '\zipf', '\alteringK.csv'];
outputFileName = [outputBasicPath, '\alter_k_zipf'];
drawWDWithContributeKChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);

% 2-nomal-multiple-centers
inputFileName = [inputBasicPath, '\normal_multi_centers', '\alteringK.csv'];
outputFileName = [outputBasicPath, '\alter_k_normal_multiple_centers'];
drawWDWithContributeKChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName_left, yLabelName_right, outputFileName, figure_MarkerSize, figure_FontSize, figure_FontSize_X, figure_FontSize_Y);




