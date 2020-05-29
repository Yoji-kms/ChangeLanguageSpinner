package com.yoji.changelanguagespinner;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({Language.RUSSIAN, Language.ENGLISH, Language.SPANISH, Language.GERMAN, Language.FRENCH})
public @interface Language {
    int RUSSIAN = 0;
    int ENGLISH = 1;
    int SPANISH = 2;
    int GERMAN = 3;
    int FRENCH = 4;
}
