package org.example;

import org.example.excel.ExcelValidation;
import org.example.excel.LangExcelData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {


        LangExcelData langExcelData = new LangExcelData();
        langExcelData.setLangName("test,test");
        langExcelData.setLangCode("test1");
        langExcelData.setAreaName("1111");

        Field[] fields = langExcelData.getClass().getDeclaredFields();
        for (Field field : fields) {

            ExcelValidation annotation = field.getAnnotation(ExcelValidation.class);
            System.out.println(fields);

        }


        List<LangExcelData> langExcelData1 = splitDynamicObject(langExcelData, LangExcelData.class);

        System.out.println(langExcelData1);
    }

    public static <T> List<T> splitDynamicObject(T inputObject, Class<T> clazz) throws Exception {
        List<T> resultList = new ArrayList<>();

        // 필드 정보를 가져옴
        Field[] fields = clazz.getDeclaredFields();
        List<String[]> splitValues = new ArrayList<>();

        // 각 필드를 접근해서 ,로 나누기
        for (Field field : fields) {
            field.setAccessible(true); // private 필드 접근 허용
            Object value = field.get(inputObject);
            if (value instanceof String) {
                splitValues.add(((String) value).split(","));
            }
        }

        // 모든 조합 생성
        generateCombinations(clazz, fields, splitValues, resultList, 0, new Object[fields.length]);

        return resultList;
    }


    private static <T> void generateCombinations(Class<T> clazz, Field[] fields, List<String[]> splitValues,
                                                 List<T> resultList, int index, Object[] currentValues) throws Exception {
        if (index == splitValues.size()) {
            // 새로운 객체 생성 및 필드 값 설정
            Constructor<T> constructor = clazz.getConstructor();
            T newInstance = constructor.newInstance();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                fields[i].set(newInstance, currentValues[i]);
            }
            resultList.add(newInstance);
            return;
        }

        // 현재 필드의 값들에 대해 재귀적으로 조합 생성
        for (String value : splitValues.get(index)) {
            currentValues[index] = value;
            generateCombinations(clazz, fields, splitValues, resultList, index + 1, currentValues);
        }
    }


}