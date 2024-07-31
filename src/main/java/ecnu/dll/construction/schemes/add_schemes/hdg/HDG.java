package ecnu.dll.construction.schemes.add_schemes.hdg;

import ecnu.dll.construction.structs.AttributeIndexPair;

import java.util.HashMap;
import java.util.List;

public class HDG<T> {

    protected Double epsilon;
    protected List<T> dataDomainList;
    protected Double gridCellLength;
    protected Integer userSize;


    protected Double alpha1, alpha2;
    protected Double g1, g2;
    // 代表一维属性组合
    protected HashMap<Integer, Integer> oneDimAttributeGroupMap;
    // 代表二维属性组合
    protected HashMap<AttributeIndexPair, Integer> twoDimAttributeGroupMap;

    protected void initialize() {
        Double[] alphaParameterArray = HDGUtils.getAlphaParameterArray();
        this.alpha1 = alphaParameterArray[0];
        this.alpha2 = alphaParameterArray[1];
        Double[] optimalGOneAndGTwo = HDGUtils.getOptimalGOneAndGTwo(this.userSize, this.epsilon, oneDimAttributeGroupMap.size(), this.alpha1, this.alpha2);
        this.g1 = optimalGOneAndGTwo[0];
        this.g2 = optimalGOneAndGTwo[1];
    }

    protected void constructGrid() {
        int dataDomainListSize = dataDomainList.size();
        this.oneDimAttributeGroupMap = new HashMap<>();
        this.twoDimAttributeGroupMap = new HashMap<>();
        for (int i = 0; i < dataDomainListSize; i++) {
            this.oneDimAttributeGroupMap.put(i, 0);
            for (int j = i + 1; j < dataDomainListSize; j++) {
                this.twoDimAttributeGroupMap.put(new AttributeIndexPair(i, j), 0);
            }
        }
    }
}
