package com.zlatoust.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlatoust.mapper.SettingsValuesMapper;
import com.zlatoust.models.SettingValue;
import com.zlatoust.services.dto.SiteSettings;
import com.zlatoust.utils.SettingKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SettingService {

    private final SettingsValuesMapper settingsValuesMapper;
    private final ObjectMapper objectMapper;

    public boolean getIsCommentAnnouncementEnabled() {
        SettingValue settingRow = settingsValuesMapper.getSettingValueByKey(SettingKey.IS_COMMENT_ANNOUNCEMENTS_ENABLED.name());

        if (settingRow == null) {
            return Boolean.parseBoolean(SettingKey.IS_COMMENT_ANNOUNCEMENTS_ENABLED.getDefaultValue());
        }

        return Boolean.parseBoolean(settingRow.getValue());
    }

    public SiteSettings getSiteSettings() {
        SettingValue settingRow = settingsValuesMapper.getSettingValueByKey(SettingKey.SITE_SETTINGS.name());

        if (settingRow == null) {
            return SiteSettings.builder()
                    .maxNewsShowed(3)
                    .maxSermonsShowed(3)
                    .maxAnnouncementShowed(3)
                    .announcementCommentsEnabled(true)
                    .sermonsCommentsEnabled(true)
                    .newsCommentsEnabled(true)
                    .build();
        }

        try {
            return objectMapper.readValue(settingRow.getValue(), SiteSettings.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка при десериализации настроек сайта", e);
        }
    }

    @Transactional
    public SiteSettings updateSiteSettings(SiteSettings siteSettings) throws JsonProcessingException {
        String jsonValue = objectMapper.writeValueAsString(siteSettings);
        settingsValuesMapper.updateSettingValueByKey(jsonValue, SettingKey.SITE_SETTINGS.name());
        return siteSettings;
    }
}
