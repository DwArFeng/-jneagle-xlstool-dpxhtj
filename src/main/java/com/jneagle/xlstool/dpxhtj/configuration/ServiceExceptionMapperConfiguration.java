package com.jneagle.xlstool.dpxhtj.configuration;

import com.dwarfeng.subgrade.impl.exception.MapServiceExceptionMapper;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.jneagle.xlstool.dpxhtj.exception.WrongPasswordException;
import com.jneagle.xlstool.dpxhtj.util.ServiceExceptionCodes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ServiceExceptionMapperConfiguration {

    @Bean
    public MapServiceExceptionMapper mapServiceExceptionMapper() {
        Map<Class<? extends Exception>, ServiceException.Code> destination = ServiceExceptionHelper.putDefaultDestination(null);
        destination.put(WrongPasswordException.class, ServiceExceptionCodes.WRONG_PASSWORD);
        return new MapServiceExceptionMapper(destination, com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes.UNDEFINE);
    }
}
