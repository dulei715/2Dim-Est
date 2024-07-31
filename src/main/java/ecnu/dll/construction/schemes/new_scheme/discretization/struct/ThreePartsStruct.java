package ecnu.dll.construction.schemes.new_scheme.discretization.struct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于记录高概率cells，混合概率cells 以及低概率cells
 * @param <T>
 */
public class ThreePartsStruct<T> {
    private Collection<T> highProbabilityCellCollection = null;
    private Map<T, Double> mixProbabilityCellCollection = null;
    private Collection<T> lowProbabilityCellCollection = null;

    public ThreePartsStruct() {
        this.highProbabilityCellCollection = new ArrayList<>();
        this.mixProbabilityCellCollection = new HashMap<>();
        this.lowProbabilityCellCollection = new ArrayList<>();
    }

    public ThreePartsStruct(Collection<T> highProbabilityCellCollection, Map<T, Double> mixProbabilityCellCollection, Collection<T> lowProbabilityCellCollection) {
        this.highProbabilityCellCollection = highProbabilityCellCollection;
        this.mixProbabilityCellCollection = mixProbabilityCellCollection;
        this.lowProbabilityCellCollection = lowProbabilityCellCollection;
    }

    public void addHighProbabilityElement(T element) {
        this.highProbabilityCellCollection.add(element);
    }

    public void addMixProbabilityElement(T element, Double shrinkElementCell) {
        this.mixProbabilityCellCollection.put(element, shrinkElementCell);
    }

    public void addLowProbabilityElement(T element) {
        this.lowProbabilityCellCollection.add(element);
    }


    public Collection<T> getHighProbabilityCellCollection() {
        return highProbabilityCellCollection;
    }

    public void setHighProbabilityCellCollection(Collection<T> highProbabilityCellCollection) {
        this.highProbabilityCellCollection = highProbabilityCellCollection;
    }

    public Map<T, Double> getMixProbabilityCellCollection() {
        return mixProbabilityCellCollection;
    }

    public void setMixProbabilityCellCollection(Map<T, Double> mixProbabilityCellCollection) {
        this.mixProbabilityCellCollection = mixProbabilityCellCollection;
    }

    public Collection<T> getLowProbabilityCellCollection() {
        return lowProbabilityCellCollection;
    }

    public void setLowProbabilityCellCollection(Collection<T> lowProbabilityCellCollection) {
        this.lowProbabilityCellCollection = lowProbabilityCellCollection;
    }

    @Override
    public String toString() {
        return "ThreePartsStruct{" +
                "highProbabilityCellCollection=" + highProbabilityCellCollection +
                ", mixProbabilityCellCollection=" + mixProbabilityCellCollection +
                ", lowProbabilityCellCollection=" + lowProbabilityCellCollection +
                '}';
    }
}
