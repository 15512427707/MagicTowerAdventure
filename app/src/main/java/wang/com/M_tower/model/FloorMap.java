package wang.com.M_tower.model;

import wang.com.M_tower.GamePlayConstants;

public class FloorMap {
    int level;
    int map[];

    public FloorMap(int level, int[] map) {
        this.level = level;
        this.map = map;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int[] getMap() {
        return map;
    }

    public void setMap(int[] map) {
        this.map = map;
    }

    public void setValue(int i,int j,int value)
    {
        this.map[i * GamePlayConstants.MAP_WIDTH + j] = value;
    }

}
