package com.kildeen.ref;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class KeyDefaultResourceBundle extends ResourceBundle {

    public KeyDefaultResourceBundle() {
        setParent(getBundle("messages"));
    }

    @Override
    public Enumeration<String> getKeys() {
        return parent.getKeys();
    }

    @Override
    protected Object handleGetObject(String key) {
        try {
            return parent.getObject(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }

}
