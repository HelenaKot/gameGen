package com.fancytank.gamegen.programming.data;

import com.fancytank.gamegen.programming.looks.ConnectionArea;
import com.fancytank.gamegen.programming.looks.input.InputType;

import java.io.Serializable;

public class InputFragment implements Serializable {
    public InputType inputType;
    public String labelText;
    transient private ConnectionArea connectionArea;

    public InputFragment(InputType inputType, String labelText) {
        this.inputType = inputType;
        this.labelText = labelText;
    }

    public void setConnectionArea(ConnectionArea connectionArea) {
        this.connectionArea = connectionArea;
        connectionArea.setInputFragment(this);
    }

    //TODO Connection

    //TODO programming

}
