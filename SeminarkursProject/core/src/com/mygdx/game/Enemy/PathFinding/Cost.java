package com.mygdx.game.Enemy.PathFinding;

import com.mygdx.game.Tools.IVector2;

/**
 * Created by Tobias on 17.12.2017.
 */

public class Cost {

    public float cost;
    public float moveCost;

    public IVector2 pos;
    public Cost parent;
    public boolean read = false;

    public Cost(float valueCost, IVector2 pos, Cost parent)
    {
        cost = valueCost;
        this.pos = pos;
        this.parent = parent;
    }
}
