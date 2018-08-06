package com.kanglian.healthcare.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;

public class POIUtil extends ExcelCommon {
    private final static Logger logger     = LoggerFactory.getLogger(POIUtil.class);
    private final static String excel2003L = ".xls";                                // 2003-
    private final static String excel2007U = ".xlsx";                               // 2007+

    /**
     * 获取IO流中的数据，组装成List<List<Object>>对象
     * 
     * @param in,fileName
     * @return
     * @throws IOException
     */
    public static List<List<Object>> getListByExcel(InputStream in, String fileName)
            throws Exception {
        // 创建Excel工作薄
        Workbook work = getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }

        List<List<Object>> resultList = null;
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        resultList = new ArrayList<List<Object>>();
        // 遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            // 遍历当前sheet中的所有行
            for (int j = sheet.getFirstRowNum(); j < sheet.getLastRowNum() + 1; j++) { // 这里的加一是因为下面的循环跳过取第一行表头的数据内容了
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }
                // 遍历所有的列
                List<Object> li = new ArrayList<Object>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(getCellValue(cell));
                }
                resultList.add(li);
            }
        }
        work.close();
        return resultList;
    }

    /**
     * 将流中的Excel数据转成List<Map>
     * 
     * @param in 输入流
     * @param fileName 文件名
     * @param mapping 字段名称映射
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> getListByExcel(InputStream in, String fileName,
            Map<String, String> mapping) throws Exception {
        // 根据文件名来创建Excel工作薄
        Workbook work = getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        // 返回数据
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        // 遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null)
                continue;

            // 取第一行标题
            row = sheet.getRow(0);
            String title[] = null;
            if (row != null) {
                title = new String[row.getLastCellNum()];
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    title[y] = (String) getCellValue(cell);
                }
            } else
                continue;
            logger.info(JSON.toJSONString(title));

            // 遍历当前sheet中的所有行
            for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                row = sheet.getRow(j);
                Map<String, Object> m = new HashMap<String, Object>();
                // 遍历所有的列
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    String key = title[y];
                    // logger.info(JSON.toJSONString(key));
                    if (mapping != null) {
                        m.put(mapping.get(key), getCellValue(cell));
                    } else {
                        m.put(key, getCellValue(cell));
                    }
                }
                resultList.add(m);
            }

        }
        work.close();
        return resultList;
    }

    /**
     * 根据文件后缀，自适应上传文件的版本
     * 
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr); // 2003-
        } else if (excel2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr); // 2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    public static Workbook getWorkBook(MultipartFile file) {
        // 获得文件名
        String fileName = file.getOriginalFilename();
        // 创建Workbook工作薄对象
        Workbook workbook = null;
        try {
            // 获取excel文件的io流
            InputStream is = file.getInputStream();
            if (fileName.endsWith(excel2003L)) {
                // 2003-
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(excel2007U)) {
                // 2007+
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return workbook;
    }

    /**
     * 获取一行的内容,Map存储,存储方式由参数定义
     * 
     * @param row 行对象
     * @param isValueKey 是否以单元格内容作为Key?key为单元格内容, value为下标索引：key为下标索引, value为单元格内容
     * @return 一行的内容,Map存储
     */
    public static Map<String, Object> getRowDataToMap(Row row, boolean isValueKey) {
        Map<String, Object> headDatas = new HashMap<String, Object>();
        short countCellNum = row.getLastCellNum();
        // 在外面判断isValueKey是为了提高效率,放在循环体中降低效率
        if (isValueKey) {
            for (int j = 0; j < countCellNum; j++) {
                Cell cell = row.getCell(j);
                if (isExistCell(cell)) {
                    // Key=单元格内容, Value=下标索引
                    headDatas.put(getCellValue(cell) + "", j);
                }
            }
        } else {
            for (int j = 0; j < countCellNum; j++) {
                Cell cell = row.getCell(j);
                if (isExistCell(cell)) {
                    // Key=下标索引, Value=单元格内容
                    headDatas.put(j + "", getCellValue(cell));
                }
            }
        }
        return headDatas;
    }

    /**
     * 获取一行的内容,List存储
     * 
     * @param row 行对象
     * @return 一行的内容
     */
    public static List<Object> getRowDataToList(Row row) {
        List<Object> rowData = new ArrayList<Object>();
        if (isExistRow(row)) {
            short countCellNum = row.getLastCellNum();
            for (int i = 0; i < countCellNum; i++) {
                Cell cell = row.getCell(i);
                if (isExistCell(cell)) {
                    rowData.add(getCellValue(cell));
                }
            }
        }
        return rowData;
    }

    /**
     * 获取sheet批量行内容，以List存储
     * 
     * @param sheet Sheet表对象
     * @param startRowIndex 开始行下标索引号
     * @param endRowIndex 结束行下标索引号
     * @return 批量内容行内容
     */
    public static List<List<Object>> getBatchRowDataToList(Sheet sheet, int startRowIndex,
            int endRowIndex) {
        List<List<Object>> batchDatas = new ArrayList<List<Object>>();
        if (startRowIndex > endRowIndex) {
            throw new RuntimeException("错误提示：开始行不能大于结束行!");
        }
        // 获取sheet总行数
        int lastRowNum = sheet.getLastRowNum();
        if (startRowIndex > lastRowNum || endRowIndex > lastRowNum) {
            throw new RuntimeException("错误提示：开始行或结束行不能超过sheet最大行数!");
        }
        for (int i = startRowIndex; i <= endRowIndex; i++) {
            Row row = sheet.getRow(i);
            batchDatas.add(getRowDataToList(row));// 此处不需要验证row是否为空,在底层getRowData已验证
        }
        return batchDatas;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
//        String[] attributes = new String[] {"id", "省份", "城市", "医院名", "医院编号", "医院等级", "经度", "纬度", "科室名", "科室编号",
//                "姓名", "医生编号", "手机号", "医生执照", "擅长", "医生简介", "职称"};
        try {
            File file = new File("/home/dataimport/doctor.xlsx");
            FileInputStream fis = new FileInputStream(file);
            printLog2(getListByExcel(fis, "doctor.xlsx", null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
