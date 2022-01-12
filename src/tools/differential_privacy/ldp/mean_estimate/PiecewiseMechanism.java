package tools.differential_privacy.ldp.mean_estimate;

/**
 * 分段机制 (在ε较大时优于随机舍入)
 */
public class PiecewiseMechanism {
    private double epsilon;
    private double p;
    private double q;
    private double s;

    public PiecewiseMechanism(double epsilon) {
        this.epsilon = epsilon;
        this.s = (Math.exp(epsilon / 2) + 1) / (Math.exp(epsilon / 2) - 1);
    }

    public double getRandomValue(double v) {
        if (v < -1 || v > 1) {
            throw new RuntimeException("v is not between [-1, 1]");
        }
        double half = Math.exp(this.epsilon/2);
//        double half_m_one = half - 1;
//        double half_p_one = half + 1;

        double lv = (half * v - 1) / (half - 1);
        double rv = (half * v + 1) / (half - 1);
        // innerProbability代表将[0,1)分成[0,innerProbability)和[innnerprobability,1). 前者代表着高概率部分的积分值，后者代表低概率部分的积分值
        double innerProbability = half / (half + 1);
        double randomPosition = Math.random();
        if (randomPosition < innerProbability) {
            // 代表选中的是高概率部分
            return (Math.random() * ( rv - lv ) + lv);
        }
        double innerProbability2 = half * (v+1) / (2*half);
        randomPosition = Math.random();
        double commonFactor = half*(2*randomPosition-1);
        if (randomPosition < innerProbability2) {
//            return (Math.random() * (lv + this.s) - this.s);
            return ((commonFactor-1) / (half-1));
        }
//        return (Math.random() * (this.s - rv) + rv);
        return ((commonFactor+1) / (half-1));
    }
}
