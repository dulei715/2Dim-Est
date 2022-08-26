function zDrawAll()
inputBasicPath = 'F:\dataset\test\result';
outputBasicPath = 'F:\github\TwoDimensionalLDPEstimation\tex\figures\result';
yLabelName = 'W_2^2';
%% altering sizeB
xLabelName = 'b';

% real data sets (Crime and NYC)
inputFileNameA = [inputBasicPath, '\crime', '\alteringB.csv'];
inputFileNameB = [inputBasicPath, '\nyc', '\alteringB.csv'];
outputFileName = [outputBasicPath, '\alter_b_real_datasets'];
drawWDWithSizeBChangeRealDataSetExperiment(inputFileNameA, inputFileNameB, xLabelName, yLabelName, outputFileName);

% sythetic data sets (2-normal and 2-Zipf)
inputFileNameA = [inputBasicPath, '\normal', '\alteringB.csv'];
inputFileNameB = [inputBasicPath, '\zipf', '\alteringB.csv'];
outputFileName = [outputBasicPath, '\alter_b_synthetic_datasets'];
drawWDWithSizeBChangeSyntheticDataSetExperiment(inputFileNameA, inputFileNameB, xLabelName, yLabelName, outputFileName);

%% altering sizeD (gridG)
xLabelName = 'd';
% Crime
inputFileName = [inputBasicPath, '\crime', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_crime'];
drawWDWithGridGChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName, outputFileName);

% NYC
inputFileName = [inputBasicPath, '\nyc', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_nyc'];
drawWDWithGridGChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName, outputFileName);

% 2-normal
inputFileName = [inputBasicPath, '\normal', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_normal'];
drawWDWithGridGChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName, outputFileName);

% 2-Zipf
inputFileName = [inputBasicPath, '\zipf', '\alteringG.csv'];
outputFileName = [outputBasicPath, '\alter_d_zipf'];
drawWDWithGridGChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName, outputFileName);


%% altering privacy budget
xLabelName = '\epsilon';
% Crime
inputFileName = [inputBasicPath, '\crime', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_crime'];
drawWDWithPrivacyBudgetChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName, outputFileName);

% NYC
inputFileName = [inputBasicPath, '\nyc', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_nyc'];
drawWDWithPrivacyBudgetChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName, outputFileName);

% 2-normal
inputFileName = [inputBasicPath, '\normal', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_normal'];
drawWDWithPrivacyBudgetChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName, outputFileName);

% 2-Zipf
inputFileName = [inputBasicPath, '\zipf', '\alteringBudget.csv'];
outputFileName = [outputBasicPath, '\alter_e_zipf'];
drawWDWithPrivacyBudgetChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName, outputFileName);



%% altering sizeK
xLabelName = 'k';

% Crime
inputFileName = [inputBasicPath, '\crime', '\alteringK.csv'];
outputFileName = [outputBasicPath, '\alter_k_crime'];
drawWDWithContributeKChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName, outputFileName);

% NYC
inputFileName = [inputBasicPath, '\nyc', '\alteringK.csv'];
outputFileName = [outputBasicPath, '\alter_k_nyc'];
drawWDWithContributeKChangeRealDataSetExperiment(inputFileName, xLabelName, yLabelName, outputFileName);

% 2-normal
inputFileName = [inputBasicPath, '\normal', '\alteringK.csv'];
outputFileName = [outputBasicPath, '\alter_k_normal'];
drawWDWithContributeKChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName, outputFileName);

% 2-Zipf
inputFileName = [inputBasicPath, '\zipf', '\alteringK.csv'];
outputFileName = [outputBasicPath, '\alter_k_zipf'];
drawWDWithContributeKChangeSyntheticDataSetExperiment(inputFileName, xLabelName, yLabelName, outputFileName);




