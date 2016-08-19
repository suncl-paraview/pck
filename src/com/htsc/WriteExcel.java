package com.htsc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class WriteExcel {

	/**
	 * 读取Excel测试，兼容 Excel 2003/2007/2010
	 */
	public static void readExcel() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// 同时支持Excel 2003、2007
			File excelFile = new File("F:\\test.xlsx"); // 创建文件对象
			FileInputStream is = new FileInputStream(excelFile); // 文件流
			Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel
															// 2003/2007/2010
															// 都是可以处理的
			int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
			System.out.println("sheetCount共有："+sheetCount);
			// 遍历每个Sheet
			for (int s = 0; s < sheetCount; s++) {
				org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(s);
				int rowCount = ((org.apache.poi.ss.usermodel.Sheet) sheet).getPhysicalNumberOfRows(); // 获取总行数
				//System.out.println("共有多少行："+rowCount);
				// 遍历每一行
				for (int r = 0; r < rowCount; r++) {
					Row row = ((org.apache.poi.ss.usermodel.Sheet) sheet).getRow(r);
					if (row==null) {
						break;
					}
					//row.getLastCellNum()
					int cellCount = row.getLastCellNum(); // 获取总列数
					//System.out.println("共有多少列："+cellCount);
					// 遍历每一列
					for (int c = 0; c < cellCount; c++) {
						Cell cell = row.getCell(c);
						if(cell==null){
							continue;
						}
						int cellType = cell.getCellType();
						//System.out.println(cellType);
						String cellValue = null;
						switch (cellType) {
										case Cell.CELL_TYPE_STRING: // 文本
											cellValue = cell.getStringCellValue();
											break;
										case Cell.CELL_TYPE_NUMERIC: // 数字、日期
											if (DateUtil.isCellDateFormatted(cell)) {
												cellValue = fmt.format(cell.getDateCellValue()); // 日期型
											} else {
												cellValue = String.valueOf(cell.getNumericCellValue()); // 数字
											}
											break;
										case Cell.CELL_TYPE_BOOLEAN: // 布尔型
											cellValue = String.valueOf(cell.getBooleanCellValue());
											break;
										case Cell.CELL_TYPE_BLANK: // 空白
											cellValue = "wu"/*cell.getStringCellValue()*/;
											break;
										case Cell.CELL_TYPE_ERROR: // 错误
											cellValue = "错误";
											break;
										case Cell.CELL_TYPE_FORMULA: // 公式
											cellValue = "错误";
											break;
										default:
											cellValue = "错误";
						}
						System.out.print(cellValue + "\t");
					}//列
					System.out.println();
				}//行
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	public static void testWriteExcel(List<ArrayList<String>> allList) {
		
		
    	List<String> tableList= new ArrayList<String>();
    	tableList=allList.get(0);
    	List<String> fieldList= new ArrayList<String>();
    	fieldList=allList.get(1);
    	
    	List<String> etableList= new ArrayList<String>();
    	etableList=allList.get(2);
    	List<String> efieldList= new ArrayList<String>();
    	efieldList=allList.get(3);
    	
    	List<String> whereList= new ArrayList<String>();
    	whereList=allList.get(4);
    	List<String> procedureList= new ArrayList<String>();
    	procedureList=allList.get(5);
    	List<String> packageList= new ArrayList<String>();
    	packageList=allList.get(6);
    	
    	
    	
    	
    	
		  String excelPath = "f:\\scl\\"+packageList.get(0)+".xlsx";  
		  
		  Workbook workbook = null;  
		  try {  
		    // XSSFWorkbook used for .xslx (>= 2007), HSSWorkbook for 03 .xsl  
		    workbook = new HSSFWorkbook();// XSSFWorkbook();//WorkbookFactory.create(inputStream);  
		  } catch (Exception e) {  
		    System.out.println("创建Excel失败: ");  
		    e.printStackTrace();  
		  }  
		  
		  for (int i = 0; i < procedureList.size(); i++) {
			  if (workbook != null) {  
				  org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet(procedureList.get(i));  
			    Row row0 = sheet.createRow(0); 
			    String[] name={"表名(table)","字段名(field)","受表影响的表(effected table)","受表影响的表的字段(effected field)","条件，计算方法(where)","过程名(procedure)","包名(package)"};
			    for (int i1 = 0; i1 < 7; i1++) {  
			      Cell cell = row0.createCell(i1, Cell.CELL_TYPE_STRING);  
			      cell.setCellValue(name[i1]);  
			      sheet.autoSizeColumn(i1);//自动调整宽度  
			    } 
			    
			    
			    
			    for (int rowNum = 1; rowNum < fieldList.size()+procedureList.size(); rowNum++) {  
			      Row row = sheet.createRow(rowNum);
			      Cell cell0 = row.createCell(0, Cell.CELL_TYPE_STRING);
				      if (tableList.size()>rowNum-1) {
					    	  cell0.setCellValue(tableList.get(rowNum-1));
						}else{
							cell0.setCellValue(" ");
						}
				      
			      
			      Cell cell1 = row.createCell(1, Cell.CELL_TYPE_STRING);
					      if (fieldList.size()>rowNum-1) {
					    	  cell1.setCellValue(fieldList.get(rowNum-1));
						}else{
							cell1.setCellValue(" ");
						}
			    	  
			
			      Cell cell2 = row.createCell(2, Cell.CELL_TYPE_STRING);
			      	cell2.setCellValue(" ");
			      
			      Cell cell3 = row.createCell(3, Cell.CELL_TYPE_STRING);
					      if (efieldList.size()>rowNum-1) {
					    	  cell3.setCellValue(efieldList.get(rowNum-1));
						}else{
							cell3.setCellValue(" ");
						}
			      
			      
			      	
			      Cell cell4 = row.createCell(4, Cell.CELL_TYPE_STRING);
			      
					      if (whereList.size()>rowNum-1) {
					    	  cell4.setCellValue(whereList.get(rowNum-1));
						}else{
							cell4.setCellValue(" ");
						}
			      
			      Cell cell5 = row.createCell(5, Cell.CELL_TYPE_STRING);
			      
					      if (rowNum==1) {
					    	  cell5.setCellValue(procedureList.get(i));
						}else{
							cell5.setCellValue(" ");
						}
					      
			      Cell cell6 = row.createCell(6, Cell.CELL_TYPE_STRING);
			      
			      

					      if (packageList.size()==rowNum) {
					    	  cell6.setCellValue(packageList.get(0));
						}else{
							cell6.setCellValue(" ");
						}
	      
			       
			      }  
			    }
			    
			    try {  
			      FileOutputStream outputStream = new FileOutputStream(excelPath);  
			      workbook.write(outputStream);  
			      outputStream.flush();  
			      outputStream.close();  
			    } catch (Exception e) {  
			      System.out .println("写入Excel失败: ");  
			      e.printStackTrace();  
			    }  
			  } 
		}
		  
		  
 
		 
	public static void main(String[] args) {
		
		List<ArrayList<String>> allList=ParsePCK.readFileByChars("F:\\scl\\ETL_MS_CUST_ZL_PKG.pck");
		testWriteExcel(allList);
		
		
		//readExcel();
	}
}
