package com.fancytank.gamegen.editor;

import android.graphics.Color;

import uz.shift.colorpicker.Palette;

public class MyColors {
    private static int[] palette;

    public static int[] getPalette() {
        if (palette == null) {
            palette = new int[Palette.DEFAULT.length + 2];
            palette[0] = Color.parseColor("#555555");
            palette[1] = Color.parseColor("#ffffff");
            System.arraycopy(Palette.DEFAULT, 0, palette, 2, Palette.DEFAULT.length);
        }
        return palette;
    }
}
