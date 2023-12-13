function y = getBChangeSyntheticDataSetResult(filenameOne)

sizeBIndexLine = 5;

wassersteinDistanceIndexLine_2 = 9;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
matrixOne = importdata(filenameOne, ',',1);
x_DAM_one = matrixOne.data(6:10,sizeBIndexLine);

y_DAM_one = matrixOne.data(6:10,wassersteinDistanceIndexLine_2);

y = [x_DAM_one, y_DAM_one];




