function result = toDoubleArray(strArray)
sizeValue = size(strArray);
result = zeros(sizeValue);
for i=1:max(sizeValue(1),sizeValue(2))
    result(i) = str2double(strArray(i));
end