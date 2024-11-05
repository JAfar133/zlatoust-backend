package com.zlatoust.mapper;

import com.zlatoust.models.SettingValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SettingsValuesMapper {

    @Select("""
        SELECT sv.key, sv.value FROM settings_values sv WHERE sv.key = #{key}
    """)
    SettingValue getSettingValueByKey(String key);

    @Update("""
        UPDATE settings_values SET value = #{value} WHERE key = #{key}
    """)
    void updateSettingValueByKey(@Param("value") String value, @Param("key") String key);

}
