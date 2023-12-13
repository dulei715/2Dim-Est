function y = getBChangeRealDataSetResult(filenameOne)


sizeBIndexLine = 5;

wassersteinDistanceIndexLine_2 = 9;

matrixOne = importdata(filenameOne, ',',1);

x_DAM_one = matrixOne.data(6:10,sizeBIndexLine);


y_A_DAM = matrixOne.data(6:10,wassersteinDistanceIndexLine_2);

y_B_DAM = matrixOne.data(16:20,wassersteinDistanceIndexLine_2);

y_C_DAM = matrixOne.data(26:30,wassersteinDistanceIndexLine_2);

y_DAM_one = (y_A_DAM + y_B_DAM + y_C_DAM)/3;

y = [x_DAM_one, y_DAM_one];

