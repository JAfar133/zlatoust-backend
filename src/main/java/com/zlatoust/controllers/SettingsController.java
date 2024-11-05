package com.zlatoust.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zlatoust.configuration.ApiConstants;
import com.zlatoust.services.SettingService;
import com.zlatoust.services.dto.SiteSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.API_PATH + "/settings")
public class SettingsController {

    private final SettingService settingService;

    @GetMapping("/get-site-settings")
    public ResponseEntity<SiteSettings> getSettings() {
        return ResponseEntity.ok(settingService.getSiteSettings());
    }

    @PostMapping("/update-site-settings")
    public ResponseEntity<SiteSettings> updateSettings(@RequestBody SiteSettings settings) throws JsonProcessingException {
        return ResponseEntity.ok(settingService.updateSiteSettings(settings));
    }
}
