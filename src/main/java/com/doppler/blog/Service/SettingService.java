package com.doppler.blog.Service;

import com.doppler.blog.mappers.SettingMapper;
import com.doppler.blog.models.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.doppler.blog.GlobalConstants.UPDATESETTINGS;

@Service
public class SettingService {
    Logger logger = LoggerFactory.getLogger(SettingService.class);
    @Resource
    private SettingMapper settingMapper;

    public Setting getSetting(){
        return settingMapper.getSettings();
    }


    public  Setting updateSetting(Setting setting) {
       logger.info(UPDATESETTINGS.value());
        settingMapper.updateSettings(setting);
        return setting;
    }


}
