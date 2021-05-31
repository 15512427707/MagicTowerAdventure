package wang.com.M_tower.model;

import androidx.annotation.Nullable;

public class AStarNode {
    //行列
    private int i,j;
    //布尔型变量 是否能走
    private boolean walkable;
    //两个Cost,fCost = g + h;
    private int gCost = 0,hCost = 0;
    private AStarNode parent;

    public AStarNode() {
    }

    public AStarNode(int i, int j, boolean walkable) {
        this.i = i;
        this.j = j;
        this.walkable = walkable;
    }

    public AStarNode getParent() {
        return parent;
    }

    public void setParent(AStarNode parent) {
        this.parent = parent;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getgCost() {
        return gCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

    public int gethCost() {
        return hCost;
    }

    public void sethCost(int hCost) {
        this.hCost = hCost;
    }

    public int getfCost() {
        return gCost + hCost;
    }


    //判断是不是相同
    public boolean isTheSame(AStarNode other)
    {
        return i == other.getI() && j == other.getJ();
    }

    public void setPosition(int i,int j)
    {
        this.i = i;
        this.j = j;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof AStarNode)
            return ((AStarNode) obj).i == i && ((AStarNode) obj).j==j;
        return false;
    }

}
