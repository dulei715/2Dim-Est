function drawRhombusExample(pointX, pointY, b, q, epsilon)
step = 0.05;
stepZ = 0.01;
x = [-b:step:1+b];
y = [-b:step:1+b];
zLow = [0:stepZ:q];
zHigh = [0:stepZ:q*exp(epsilon)];
zLowLen = length(zLow);
zHighLen = length(zHigh);
figure
hold on;
for i=x
    for j=y
        if i+j+b>=0 && i+j-b-2<=0 && i-j+1+b>=0 && i-j-1-b<=0
            if abs(i-pointX)+abs(j-pointY)>b
                scatter3(ones(1,zLowLen)*i, ones(1,zLowLen)*j ,zLow, '.');
            else
                scatter3(ones(1,zHighLen)*i, ones(1,zHighLen)*j ,zHigh, '.');
            end
        end
    end
end