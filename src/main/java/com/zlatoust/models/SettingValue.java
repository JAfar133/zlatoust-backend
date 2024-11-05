package com.zlatoust.models;

import com.zlatoust.utils.SettingKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingValue {

    String value;
    SettingKey key;
}
