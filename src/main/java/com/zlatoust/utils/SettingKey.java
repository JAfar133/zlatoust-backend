package com.zlatoust.utils;

public enum SettingKey {

    IS_COMMENT_ANNOUNCEMENTS_ENABLED("true"),
    IS_COMMENT_NEWS_ENABLED("true"),
    IS_COMMENT_SERMONS_ENABLED("true"),
    MAIN_PAGE_MAX_ANNOUNCEMENTS("3"),
    MAIN_PAGE_MAX_NEWS("3"),
    MAIN_PAGE_MAX_SERMONS("3"),
    SITE_SETTINGS(null);

    private final String defaultValue;

    private SettingKey(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }
}
