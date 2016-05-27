package com.fancytank.gamegen.programming.data;

import com.fancytank.gamegen.programming.looks.ConnectionArea;
import com.fancytank.gamegen.programming.looks.input.InputType;

import java.io.Serializable;

public class InputFragment implements Serializable {
    public InputType inputType;
    public String labelText;
    public BlockData connectedTo;
    BlockData blockData;
    transient private ConnectionArea connectionArea;

    public InputFragment(InputType inputType, String labelText) {
        this.inputType = inputType;
        this.labelText = labelText;
    }

    public boolean hasConnectonArea() {
        return connectionArea != null;
    }

    public ConnectionArea getConnectionArea() {
        return connectionArea;
    }

    public void setConnectionArea(ConnectionArea connectionArea) {
        if (this.connectionArea != connectionArea) {
            this.connectionArea = connectionArea;
            connectionArea.setInputFragment(this);
        }
    }

    public String getDebugLog(String spacing) {
        String output = spacing + "> Input [" + labelText + "] type " + inputType + "\n";
        if (connectedTo != null)
            output += spacing + "> connected to: \n" + connectedTo.getDebugLog(spacing + "  ") + "\n";
        return output;
    }

    //TODO programming

}
