package com.fancytank.gamegen.programming.looks.input;


import com.fancytank.gamegen.programming.data.InputFragment;

import static com.fancytank.gamegen.programming.looks.BlockAppearance.padding;

public class SocketInputAppearance extends BlockInputAppearance {

     SocketInputAppearance(InputFragment inputFragment) {
        super(inputFragment);
        setPreferredSize();
    }

    void setPreferredSize() {
        patchData.height = padding * 2;
    }

}
