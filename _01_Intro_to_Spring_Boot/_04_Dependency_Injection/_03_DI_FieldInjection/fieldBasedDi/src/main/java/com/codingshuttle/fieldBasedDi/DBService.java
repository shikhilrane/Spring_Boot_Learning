package com.codingshuttle.fieldBasedDi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    @Autowired
    private DB db;

    public String getDataFromDB() {
        return db.getData();
    }
}

// We can use final keyword with constructor based dependency