package com.example.latteec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by liangbingtian on 2018/3/12.
 */

public enum EcIcons implements Icon {
    icon_scan('\ue657'),
    icon_ali_pay('\ue64e');

    private char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_','-');
    }

    @Override
    public char character() {
        return character;
    }
}
