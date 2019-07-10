package com.dc.base.util;

import com.alibaba.fastjson.JSON;
import com.dc.base.contants.BaseContants;
import com.dc.base.exception.BusinessException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.jexl2.UnifiedJEXL;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 黄继升 16041321
 * @Description: 时间类操作工具类
 * @date Created in 2019/7/7 14:38
 * @Modified By:
 */
public class ExcelUtil {
    /**
     * @param list
     * @param fileName
     * @return
     * @throws Exception
     * @Description 导出excel文件, 返回导出的excel路径
     * @author 黄继升 16041321
     * @date 2015-8-21
     */
    public static String expTemplateExcel(List<?> list, String fileName) throws Exception {
        Map<String, Object> beanParams = new HashMap<String, Object>();
        beanParams.put("list", list);
        String webappRoot = System.getProperty(BaseContants.WEBAPP_ROOT);//获得前端目录
        String destPath = webappRoot + "files" + File.separator + "temp"
                + File.separator + fileName;// 导出的临时文件目录
        File destFile = new File(destPath);//导出的临时文件
        if (!destFile.getParentFile().exists()) {//导出的文件夹是否存在
            destFile.getParentFile().mkdirs();//创建导出文件的文件夹
        }
        String templatePath = webappRoot + "files" + File.separator + "xlsExportTemplate"
                + File.separator + fileName;// 导出的模板目录
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(templatePath, beanParams, destPath);// 生成excel文件
        return destPath;
    }

    /**
     * @return java.util.List<java.util.Map<ava.lang.String,java.lang.Object>>
     * @title:<h3> 解析上传的excel，并返回listMap <h3>
     * @author: 黄继升 16041321
     * @date: 2018-11-21 14:55
     * @params [arrFile]
     **/
    public static List<Map<String, Object>> getExcelData(MultipartFile[] arrFile) throws Exception {
        if (arrFile == null || arrFile.length == 0) {
            throw new BusinessException("请选择上传的excel文件");
        }
        MultipartFile file = arrFile[0];
        //验证文件格式
        if (!"application/vnd.ms-excel".equals(file.getContentType())) {
            throw new BusinessException("请选择正确的excel格式文件");
        }
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        Workbook book = new HSSFWorkbook(file.getInputStream());//获得excel文件对象
        Sheet sheet = book.getSheetAt(0);// 获得工作簿对象
        Row rowFileName = sheet.getRow(2);//获得第三行，字段行
        Map<Integer, String> mapField = new HashMap<Integer, String>();//存储字段序列
        try {
            for (int i = 0; i < rowFileName.getLastCellNum(); i++) {//遍历字段单元格
                if (rowFileName.getCell(i) != null) {//如果存在单元格
                    mapField.put(i, rowFileName.getCell(i).getStringCellValue());//获得单元格中的值
                }
            }
        }catch (Exception e){
            throw new BusinessException("excel头部解析异常");
        }

//循环数据,每行
        for (int i = 4; i <=sheet.getLastRowNum(); i++) {
            //循环数据，每列表
            Row row=sheet.getRow(i);//获得单元格行
            if(row!=null){//如果行不为空
                Map<String,Object>map=new HashMap<String, Object>();
                    for(int j=0;j<row.getLastCellNum();j++){//循环每行中的单元格
                        try {
                        if(row.getCell(j)!=null){//如果单元格不为空
                            map.put(mapField.get(j),getValue(row.getCell(j)));//设置字段的名，字段的值到map
                        }
                        }catch (Exception e){
                            throw new BusinessException("第"+(i+1)+"行,第"+(j+1)+"列,数据解析异常");
                        }
                    }
                    listMap.add(map);
            }
        }
        System.out.println("解析的excel数据："+JSON.toJSONString(listMap));
        return listMap;
    }


    /**
     * @param cell
     * @return
     * @Description 获取excel单元格内容, 将其转换为String类型
     * @author 黄继升 16041321
     * @date 2016-1-21
     */
    private static String getValue(Cell cell)throws Exception {
        String value = "";
        if (null == cell) {
            return value;
        }
        switch (cell.getCellType()) {
            // 数值型
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // 如果是date类型则 ，获取该cell的date值
                    Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    value = format.format(date);
                } else {// 纯数字
                    BigDecimal big = new BigDecimal(cell.getNumericCellValue());
                    value = big.toString();
                    if (null != value && !"".equals(value.trim())) {
                        String[] item = value.split("[.]");
                        if (1 < item.length && item[1].trim().length() > 3) {// 如果小数点后面大于三位，则保留三位小数
                            value = item[0] + "." + item[1].trim().substring(0, 3);
                        }
                    }
                }
                break;
            // 字符串类型
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue().toString();
                break;
            // 公式类型
            case Cell.CELL_TYPE_FORMULA:
                // 读公式计算值
                value = String.valueOf(cell.getNumericCellValue());
                if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
                    value = cell.getStringCellValue().toString();
                }
                break;
            // 布尔类型
            case Cell.CELL_TYPE_BOOLEAN:
                value = " " + cell.getBooleanCellValue();
                break;
            // 空值
            case Cell.CELL_TYPE_BLANK:
                value = "";
                // LogUtil.getLogger().error("excel出现空值");
                break;
            // 故障
            case Cell.CELL_TYPE_ERROR:
                value = "";
                // LogUtil.getLogger().error("excel出现故障");
                break;
            default:
                value = cell.getStringCellValue().toString();
        }
        if ("null".endsWith(value.trim())) {
            value = "";
        }
        return value;
    }
}
