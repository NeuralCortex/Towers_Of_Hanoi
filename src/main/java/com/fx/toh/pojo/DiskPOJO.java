package com.fx.toh.pojo;

/**
 *
 * @author pscha
 */
public class DiskPOJO {

    private int disk;
    private int desc;

    public DiskPOJO(int disk, int desc) {
        this.disk = disk;
        this.desc = desc;
    }

    public int getDisk() {
        return disk;
    }

    public void setDisk(int disk) {
        this.disk = disk;
    }

    public int getDesc() {
        return desc;
    }

    public void setDesc(int desc) {
        this.desc = desc;
    }
}
