package com.codingshuttle.constructorBasedDi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    @Autowired
    final private DB db;

    public DBService(DB db) {
        this.db = db;
    }

    public String getDataFromDB(){
        return db.getData();
    }
}

// We can use final keyword with constructor based dependency