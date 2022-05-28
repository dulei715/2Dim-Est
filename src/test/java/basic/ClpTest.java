package basic;


import com.quantego.clp.CLP;
import com.quantego.clp.CLPConstraint;
import com.quantego.clp.CLPExpression;
import com.quantego.clp.CLPVariable;
import org.junit.Test;
import tools.io.print.MyPrint;

import java.util.HashMap;
import java.util.Map;

public class ClpTest {
    @Test
    public void fun1() {
        CLP clp = new CLP();
        int count;

        CLPVariable v = clp.addVariable();
        count = clp.getNumVariables();
        System.out.println(count);
        CLPVariable v2 = clp.addVariable();
        System.out.println(v);
        System.out.println(v2);

        count = clp.getNumVariables();
        System.out.println(count);

        CLPExpression expression = clp.createExpression();

        double v2Solution = v2.getSolution();
        System.out.println(v2Solution);


//        CLPVariable var = new CLP


    }

    @Test
    public void fun2() {
        CLP clp = new CLP();
        System.out.println(clp.getNumVariables());
        CLPVariable x_1 = clp.addVariable();
        CLPVariable x_2 = clp.addVariable();
        CLPVariable x_3 = clp.addVariable();

        int size = 3;
        CLPVariable[] variableArr = new CLPVariable[size];
        for (int i = 0; i < variableArr.length; i++) {
            variableArr[i] = clp.addVariable();
            variableArr[i].bounds(0, Double.MAX_VALUE);
        }


        Map<CLPVariable, Double> constrainMapA = new HashMap<>();
        constrainMapA.put(x_1, 1.0);
        constrainMapA.put(x_2, -1.0);
        constrainMapA.put(x_3, 1.0);
        clp.addConstraint(constrainMapA, CLPConstraint.TYPE.LEQ, 20.0);

        Map<CLPVariable, Double> constrainMapB = new HashMap<>();
        constrainMapB.put(x_1, 3.0);
        constrainMapB.put(x_2, 2.0);
        constrainMapB.put(x_3, 4.0);
        clp.addConstraint(constrainMapB, CLPConstraint.TYPE.LEQ, 42.0);

        Map<CLPVariable, Double> constrainMapC = new HashMap<>();
        constrainMapC.put(x_1, 3.0);
        constrainMapC.put(x_2, 2.0);
        constrainMapC.put(x_3, 0.0);
        clp.addConstraint(constrainMapC, CLPConstraint.TYPE.LEQ, 30.0);


        Map<CLPVariable, Double> goalConstrainMap = new HashMap<>();
        goalConstrainMap.put(x_1, -5.0);
        goalConstrainMap.put(x_2, -4.0);
        goalConstrainMap.put(x_3, -6.0);
        clp.addObjective(goalConstrainMap, 0);

        System.out.println("x_1 = " + clp.getSolution(x_1));
        System.out.println("x_2 = " + clp.getSolution(x_2));
        System.out.println("x_3 = " + clp.getSolution(x_3));
        System.out.println("Result value = " + clp.getObjectiveValue());
//
        MyPrint.showSplitLine("+", 100);

        System.out.println(clp.getNumVariables());
        CLP.STATUS resultStatus = clp.minimize();
        System.out.println(resultStatus);
//        clp.minimization();


        System.out.println("Result value = " + clp.getObjectiveValue());

        System.out.println("x_1 = " + clp.getSolution(x_1));
        System.out.println("x_2 = " + clp.getSolution(x_2));
        System.out.println("x_3 = " + clp.getSolution(x_3));

        MyPrint.showSplitLine("*", 150);

        clp.reset();



    }

    @Test
    public void fun3() {
        CLP clp = new CLP();
        CLPVariable x_1 = clp.addVariable();
        CLPVariable x_2 = clp.addVariable();
        int size = 2;
        CLPVariable[] variableArr = new CLPVariable[size];
        for (int i = 0; i < variableArr.length; i++) {
            variableArr[i] = clp.addVariable();
            variableArr[i].bounds(0, Double.MAX_VALUE);
        }


        Map<CLPVariable, Double> constrainMapA = new HashMap<>();
        constrainMapA.put(x_1, 3.0);
        constrainMapA.put(x_2, 1.0);
        clp.addConstraint(constrainMapA, CLPConstraint.TYPE.GEQ, 30.0);

        Map<CLPVariable, Double> constrainMapB = new HashMap<>();
        constrainMapB.put(x_1, 1.0);
        constrainMapB.put(x_2, -1.0);
        clp.addConstraint(constrainMapB, CLPConstraint.TYPE.LEQ, 10.0);

        Map<CLPVariable, Double> constrainMapC = new HashMap<>();
        constrainMapC.put(x_2, 1.0);
        clp.addConstraint(constrainMapC, CLPConstraint.TYPE.GEQ, 0.0);


        Map<CLPVariable, Double> goalConstrainMap = new HashMap<>();
        goalConstrainMap.put(x_1, 2.0);
        goalConstrainMap.put(x_2, 3.0);
        clp.addObjective(goalConstrainMap, 0);

        System.out.println(clp.getNumVariables());
        CLP.STATUS minimize = clp.minimize();
        System.out.println(minimize);

        System.out.println("x_1 = " + clp.getSolution(x_1));
        System.out.println("x_2 = " + clp.getSolution(x_2));

        System.out.println("Result value = " + clp.getObjectiveValue());
    }

    @Test
    public void fun4 () {
        CLP clp = new CLP();
        CLPVariable x_1 = clp.addVariable();
        CLPVariable x_2 = clp.addVariable();
        CLPVariable x_3 = clp.addVariable();
        int size = 3;
        CLPVariable[] variableArr = new CLPVariable[size];
        for (int i = 0; i < variableArr.length; i++) {
            variableArr[i] = clp.addVariable();
            variableArr[i].bounds(0, Double.MAX_VALUE);
        }


        Map<CLPVariable, Double> constrainMapA = new HashMap<>();
        constrainMapA.put(x_1, -1.0);
        constrainMapA.put(x_2, 1.0);
        constrainMapA.put(x_3, 1.0);
        clp.addConstraint(constrainMapA, CLPConstraint.TYPE.LEQ, 20.0);

        Map<CLPVariable, Double> constrainMapB = new HashMap<>();
        constrainMapB.put(x_1, 1.0);
        constrainMapB.put(x_2, -3.0);
        constrainMapB.put(x_3, 1.0);
        clp.addConstraint(constrainMapB, CLPConstraint.TYPE.LEQ, 30.0);

        Map<CLPVariable, Double> constrainMapC = new HashMap<>();
        constrainMapC.put(x_1, 1.0);
        clp.addConstraint(constrainMapC, CLPConstraint.TYPE.LEQ, 40.0);


        Map<CLPVariable, Double> goalConstrainMap = new HashMap<>();
        goalConstrainMap.put(x_1, 1.0);
        goalConstrainMap.put(x_2, 2.0);
        goalConstrainMap.put(x_3, 3.0);
        clp.addObjective(goalConstrainMap, 0);

        System.out.println(clp.getNumVariables());
        CLP.STATUS maximize = clp.maximize();
        System.out.println(maximize);

        System.out.println("x_1 = " + clp.getSolution(x_1));
        System.out.println("x_2 = " + clp.getSolution(x_2));
        System.out.println("x_3 = " + clp.getSolution(x_3));

        System.out.println("Result value = " + clp.getObjectiveValue());
    }

}
