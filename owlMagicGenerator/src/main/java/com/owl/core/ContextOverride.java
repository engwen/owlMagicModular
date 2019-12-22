package com.owl.core;

import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.ModelType;

import java.util.List;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/12/21.
 */
public class ContextOverride extends Context {
    //添加ServiceGeneratorConfiguration
    private ServiceGeneratorConfiguration serviceGeneratorConfiguration;

    public ContextOverride(ModelType defaultModelType) {
        super(defaultModelType);
    }

    public ServiceGeneratorConfiguration getServiceGeneratorConfiguration() {
        return serviceGeneratorConfiguration;
    }

    public void setServiceGeneratorConfiguration(ServiceGeneratorConfiguration serviceGeneratorConfiguration) {
        this.serviceGeneratorConfiguration = serviceGeneratorConfiguration;
    }

    @Override
    public void validate(List<String> errors) {
        if(serviceGeneratorConfiguration != null)
            serviceGeneratorConfiguration.validate(errors, this.getId());

        super.validate(errors);
    }
}