function staticZipf(path, stepA, stepB)
points = textread(path);
[xLen yLen] = size(points);
vitalPointArr = round(stepA/stepB):round(stepA/stepB):xLen;
x = zeros(length(vitalPointArr)-1,1);
y = zeros(length(vitalPointArr)-1,1);
lastIndex = 1;
for i=1:length(vitalPointArr)
    vitalIndex = vitalPointArr(i);
    x(i) = i;
    sum = 0;
    for j=lastIndex:(vitalIndex-1)
        sum = sum + points(j,2);
    end
    y(i) = sum;
    lastIndex = vitalIndex;
end
plot(x,y);

