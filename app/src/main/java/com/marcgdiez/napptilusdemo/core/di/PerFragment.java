package com.marcgdiez.napptilusdemo.core.di;

import java.lang.annotation.Retention;
import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Scope @Retention(RUNTIME) public @interface PerFragment {
}
