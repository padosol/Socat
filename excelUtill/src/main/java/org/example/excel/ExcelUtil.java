package org.example.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ExcelUtil {

    public static <T> List<T> parseExcel(File file, Class<T> tClass) {
        Class<? super T> superclass = tClass.getSuperclass();
        Field[] fields = tClass.getDeclaredFields();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            Constructor<T> constructor = tClass.getConstructor();
            T data = constructor.newInstance();

            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue;

                int rowNum = row.getRowNum();
                Method setRowNum = superclass.getMethod("setRowNum", Integer.class);
                setRowNum.setAccessible(true);
                setRowNum.invoke(data, rowNum);

                for (Cell cell : row) {
                    int cellNum = cell.getColumnIndex();

                    if (cellNum >= fields.length)
                        continue;

                    // 해당 필드 가져옴 setAccessible 은 필드 수정 여부 확인
                    Field field = fields[cellNum];
                    field.setAccessible(true);

                    // 필드의 setter 를 이용해서 넣어줌
                    String cellData = switch (cell.getCellType()) {
                        case STRING -> cell.getStringCellValue().trim();
                        case NUMERIC -> String.valueOf((int) cell.getNumericCellValue()).trim();
                        default -> null;
                    };

                    rowValidation(field, cellData, rowNum);

                    field.set(data, cellData);
                }

            }

        } catch (IOException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static void rowValidation(Field field, String cellData, int rowNum) {
        ExcelValidation annotation = field.getAnnotation(ExcelValidation.class);

        // 공백인지 확인
        if (StringUtil.isBlank(cellData)) {
            throw new ExcelValidationException(
                    String.format("[%s 행][%s] 공백입니다.", rowNum, annotation.columName())
            );
        }

        // 검증 안해도됨
        if (annotation == null) return;

        // 검증
        if (annotation.isRegex()) {
            if (annotation.multi()) {
                // 멀티 데이터 일 경우, keyword 를 사용해서 데이터를 분리함
                String[] multiCellData = cellData.split(annotation.multiKey());

                for (String multiCellDatum : multiCellData) {
                    if (!multiCellDatum.trim().matches(annotation.pattern())) {
                        throw new ExcelValidationException(
                                String.format("[%s 행][%s] %s", rowNum, annotation.columName(), annotation.message())
                        );
                    }
                }

            } else {
                // 단일 데이터
                if (!cellData.trim().matches(annotation.pattern())) {
                    throw new ExcelValidationException(
                            String.format("[%s 행][%s] %s", rowNum, annotation.columName(), annotation.message())
                    );
                }
            }
        }

        // trim
        if (annotation.isTrim()) {
            if (annotation.multi()) {
                String[] multiData = cellData.split(annotation.multiKey());
                Object[] array = Arrays.stream(multiData).map(String::trim).toArray();
                cellData = Arrays.toString(array);
            } else {
                cellData = cellData.trim();
            }
        }
    }

}

