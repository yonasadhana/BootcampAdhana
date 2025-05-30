package com.delicious.pos.models;

import com.delicious.pos.enums.SideType;

public class Side extends RegularTopping {
    private final SideType sideType;

    public Side(SideType sideType) {
        super(sideType.getDisplayName());
        this.sideType = sideType;
    }

    public SideType getSideType() {
        return sideType;
    }
}