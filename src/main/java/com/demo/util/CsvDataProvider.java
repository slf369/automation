package com.demo.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shi.lingfeng on 2018/3/5.
 */
public class CsvDataProvider {
    public static Object[][] getTestData(String fileName) throws  Exception {
        List<Object[]> records = new ArrayList<>();
        String record;
        BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        file.readLine();
        while ((record = file.readLine()) != null) {
            String fields[] = record.split(",");
            records.add(fields);
            fields.clone();
         }
            Object[][] results=new Object[records.size()][];
            for(int i=0;i<records.size();i++){
                results[i]=records.get(i);
            }
        return results;
    }


}
