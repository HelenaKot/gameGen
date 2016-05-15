package com.fancytank.gamegen.programming.data;

import com.fancytank.gamegen.programming.looks.ConnectionArea;
import com.fancytank.gamegen.programming.looks.InputType;

public class InputFragment {
    public InputType inputType;
    public String labelText;
    private ConnectionArea connectionArea;

    public InputFragment(InputType inputType, String labelText) {
        this.inputType = inputType;
        this.labelText = labelText;
    }

    public void setConnectionArea(ConnectionArea connectionArea) {
        this.connectionArea = connectionArea;
        connectionArea.setInputFragment(this);
    }


    //TODO programming

}
