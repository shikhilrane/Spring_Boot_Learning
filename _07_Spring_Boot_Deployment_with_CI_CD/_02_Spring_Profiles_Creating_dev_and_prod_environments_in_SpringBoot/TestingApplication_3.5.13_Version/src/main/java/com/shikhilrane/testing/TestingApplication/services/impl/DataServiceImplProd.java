package com.shikhilrane.testing.TestingApplication.services.impl;

import com.shikhilrane.testing.TestingApplication.services.DataService;

public class DataServiceImplProd implements DataService {
    @Override
    public String getData() {
        return "Prod Data";
    }
}
