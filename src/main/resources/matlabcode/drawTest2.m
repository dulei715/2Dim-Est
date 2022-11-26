function drawTest2()

v=[0 0 0;0 50 0;50 0 0;0 0 50;50 0 50;0 50 50;];
f= [1 2 3 1;1 2 6 4;2 3 5 6;1 3 5 4;4 5 6 4];
patch('Faces',f,'Vertices',v,'FaceColor','r');
view(135,30)
alpha(0.5) 