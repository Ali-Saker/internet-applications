package com.internetapplications.helper;


import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Component
public class CsvHelper {

    public  <T> InputStream generateCsv(List<T> data, Class<T> projectionClass) throws IOException {
//        File file = Paths.get(System.getProperty("java.io.tmpdir"), (new Date()).getTime() + ".csv").toFile();
        File file = Paths.get("/Users/alisaker/Desktop", (new Date()).getTime() + ".csv").toFile();

        PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(projectionClass);
        String[] headers = new String[descriptors.length];

        for(int i = 0; i < descriptors.length; ++i) {
            headers[i] = descriptors[i].getName();
        }

        FileOutputStream os = new FileOutputStream(file);
        ICsvBeanWriter beanWriter = new CsvBeanWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8), CsvPreference.STANDARD_PREFERENCE);
        Throwable var8 = null;

        try {
            beanWriter.writeHeader(headers);
            for (Object c : data) {
                beanWriter.write(c, headers);
            }
        } catch (Throwable var18) {
            var8 = var18;
            throw var18;
        } finally {
            if (var8 != null) {
                try {
                    beanWriter.close();
                } catch (Throwable var17) {
                    var8.addSuppressed(var17);
                }
            } else {
                beanWriter.close();
            }
        }

        return new FileInputStream(file);
    }

}
