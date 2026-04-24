package com.shikhilrane.testing.TestingApplication.services.impl;

import com.shikhilrane.testing.TestingApplication.services.DataService;

public class DataServiceImplDev implements DataService {
    @Override
    public String getData() {
        return "Dev Data";
    }
}
