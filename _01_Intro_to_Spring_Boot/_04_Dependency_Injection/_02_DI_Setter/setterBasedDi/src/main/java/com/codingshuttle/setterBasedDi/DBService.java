package com.codingshuttle.setterBasedDi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    @Autowired
    private DB db;

    public void setDb(DB db) {
        this.db = db;
    }

    public String getDataFromDB() {
        return db.getData();
    }
}

// We can use final keyword with constructor based dependency
