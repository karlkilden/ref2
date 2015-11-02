package com.kildeen.gv;

import liquibase.parser.core.xml.XMLChangeLogSAXParser;

public class MyChangeLogParser extends XMLChangeLogSAXParser {
    @Override
    public int getPriority() {
        return PRIORITY_DEFAULT+100;
    }
}
