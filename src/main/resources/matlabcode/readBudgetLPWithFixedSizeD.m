function y = readBudgetLPWithFixedSizeD(dataPath, sizeDIndex)
fid = fopen(dataPath, 'r');
rowStrings = fgetl(fid);
colStrings = fgetl(fid);
strArray = strsplit(colStrings);
x = toDoubleArray(strArray);
i = 1;
while(i<sizeDIndex)
    fgetl(fid);
    i = i + 1;
end
newStr = fgetl(fid);
newStrArray = strsplit(newStr);
y1 = toDoubleArray(newStrArray);
y = [x;y1];
fclose(fid);