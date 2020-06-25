package com.hright.test;

import org.junit.Rule;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;

public abstract class BaseTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog().mute();

    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog().mute();

}
