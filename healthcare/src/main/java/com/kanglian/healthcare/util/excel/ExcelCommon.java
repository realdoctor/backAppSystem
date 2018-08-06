package com.kanglian.healthcare.util.excel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Excel公共封装类<br>
 * 说明：该类为Excel读、写的公共类包括:<br>
 * 1).获取单元格的值;<br>
 * 2).判断Sheet、Row、Cell是否存在;<br>
 * 3).加控制台遍历List<List<Object>>集合对象;<br>
 * 
 * @author liuxl
 */
public class ExcelCommon {

    /**
     * 将读取到的Cell单元格为日期类型时,通过表示5位数的Double类型,转换成Java的Date
     * 
     * @param cellDateValue 单元格日期类型
     * @return 返回Java Date类型
     */
    public static Date getCellToDate(Object cellDateValue) {
        if (cellDateValue == null || cellDateValue.equals("")) {
            return null;
        }
        double parseDouble = Double.parseDouble(cellDateValue.toString());
        Date javaDate = HSSFDateUtil.getJavaDate(parseDouble);
        return javaDate;
    }

    /**
     * 获取Cell的内容
     * 
     * @param cell
     * @return 单元格的内容
     */
    protected static Object getCellValue(Cell cell) {
        Object value = null;
        if (cell == null) {
            return "";
        }
        DecimalFormat df = new DecimalFormat("0"); // 格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00"); // 格式化数字
        int cellType = cell.getCellType();
        switch (cellType) {
            case Cell.CELL_TYPE_BOOLEAN:// Boolean
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:// 公式
                value = cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_NUMERIC:// 数字
                // 如果为时间格式的内容
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    value = sdf2.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                    break;
                } else {
                    String c = cell.getCellStyle().getDataFormatString();
                    if ("General".equals(c)) {
                        value = df.format(cell.getNumericCellValue());
                    } else if ("m/d/yy".equals(c)) {
                        value = sdf.format(cell.getDateCellValue());
                    } else if ("@".equals(c)) {
                        value = cell.getNumericCellValue();
                    } else if ("0.00".equals(c)) {
                        value = df2.format(cell.getNumericCellValue());
                    } else {
                        value = cell.getNumericCellValue();
                    }
                }
                
                break;
            case Cell.CELL_TYPE_STRING:// 字符串
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:// 空值
                value = "";
                break;
            case Cell.CELL_TYPE_ERROR: // 故障
                value = "";
                break;
            default:
                value = cell.getRichStringCellValue();
        }
        return value;
    }

    /**
     * 验证Sheet表对象是否存在,返回Boolean结果
     * 
     * @param sheet 验证的Sheet表对象
     * @return 是否存在Sheet表的Boolean值
     */
    protected static boolean isExistSheet(Sheet sheet) {
        return isExist(sheet);
    }

    /**
     * 验证一行对象是否存在,返回Boolean结果
     * 
     * @param row 验证的行对象
     * @return 是否存在行的Boolean值
     */
    protected static boolean isExistRow(Row row) {
        return isExist(row);
    }

    /**
     * 验证一单元格是否存在 ,返回Boolean结果
     * 
     * @param cell 验证的单元格对象
     * @return 是否存在列的Boolean值
     */
    protected static boolean isExistCell(Cell cell) {
        return isExist(cell);
    }

    /**
     * 对象是否为空对象
     * 
     * @param object 验证的对象
     * @return 对象是否为空
     */
    protected static boolean isExist(Object object) {
        if (object == null) {
            return false;
        }
        return true;
    }

    /**
     * 控制台遍历出List<List<Object>>对象
     * 
     * @param datas
     */
    public static void printLog(List<List<Object>> datas) {
        for (int i = 0; i < datas.size(); i++) {
            List<Object> rowData = datas.get(i);
            StringBuffer sbf = new StringBuffer("第【" + i + "】行：");
            for (Object object : rowData) {
                sbf.append("\t" + String.valueOf(object));
            }
            System.out.println(sbf.toString());
        }
    }

    public static void printLog2(List<Map<String, Object>> datas) {
        for (int i = 0; i < datas.size(); i++) {
            Map<String, Object> rowData = datas.get(i);
            StringBuffer sbf = new StringBuffer("第【" + i + "】行：");
            for (Map.Entry<String, Object> object : rowData.entrySet()) {
                sbf.append("\t" + String.valueOf(object.getValue()));
            }
            System.out.println(sbf.toString());
        }
    }
}
