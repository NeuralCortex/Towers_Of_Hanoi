package com.fx.toh.pojo;

import java.util.List;

/**
 *
 * @author pscha
 */
public class StepPOJO {

    private int step;
    private int disk;
    private int from;
    private int to;
    private List<List<DiskPOJO>> stateList;

    public StepPOJO() {

    }

    public StepPOJO(int disk, int from, int to) {
        this.disk = disk;
        this.from = from;
        this.to = to;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getDisk() {
        return disk;
    }

    public void setDisk(int disk) {
        this.disk = disk;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public List<List<DiskPOJO>> getStateList() {
        return stateList;
    }

    public void setStateList(List<List<DiskPOJO>> stateList) {
        this.stateList = stateList;
    }

    public String getTowerStack(int tower) {
        String erg = "";
        try {
            if (stateList != null) {
                List<DiskPOJO> list = stateList.get(tower);
                for (int i = 0; i < list.size(); i++) {
                    DiskPOJO diskPOJO = list.get(i);
                    erg += diskPOJO.getDesc() + ",";
                }
            }
            erg = erg.substring(0, erg.lastIndexOf(","));
        } catch (IndexOutOfBoundsException ex) {
            erg = "";
        }
        return erg;
    }

    @Override
    public String toString() {
        return "StepPOJO{" + "step=" + step + ", disk=" + disk + ", from=" + from + ", to=" + to + ", stateList=" + stateList + '}';
    }
}
