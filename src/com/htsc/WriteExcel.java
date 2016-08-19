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
	 * ��ȡExcel���ԣ����� Excel 2003/2007/2010
	 */
	public static void readExcel() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// ͬʱ֧��Excel 2003��2007
			File excelFile = new File("F:\\test.xlsx"); // �����ļ�����
			FileInputStream is = new FileInputStream(excelFile); // �ļ���
			Workbook workbook = WorkbookFactory.create(is); // ���ַ�ʽ Excel
															// 2003/2007/2010
															// ���ǿ��Դ����
			int sheetCount = workbook.getNumberOfSheets(); // Sheet������
			System.out.println("sheetCount���У�"+sheetCount);
			// ����ÿ��Sheet
			for (int s = 0; s < sheetCount; s++) {
				org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(s);
				int rowCount = ((org.apache.poi.ss.usermodel.Sheet) sheet).getPhysicalNumberOfRows(); // ��ȡ������
				//System.out.println("���ж����У�"+rowCount);
				// ����ÿһ��
				for (int r = 0; r < rowCount; r++) {
					Row row = ((org.apache.poi.ss.usermodel.Sheet) sheet).getRow(r);
					if (row==null) {
						break;
					}
					//row.getLastCellNum()
					int cellCount = row.getLastCellNum(); // ��ȡ������
					//System.out.println("���ж����У�"+cellCount);
					// ����ÿһ��
					for (int c = 0; c < cellCount; c++) {
						Cell cell = row.getCell(c);
						if(cell==null){
							continue;
						}
						int cellType = cell.getCellType();
						//System.out.println(cellType);
						String cellValue = null;
						switch (cellType) {
										case Cell.CELL_TYPE_STRING: // �ı�
											cellValue = cell.getStringCellValue();
											break;
										case Cell.CELL_TYPE_NUMERIC: // ���֡�����
											if (DateUtil.isCellDateFormatted(cell)) {
												cellValue = fmt.format(cell.getDateCellValue()); // ������
											} else {
												cellValue = String.valueOf(cell.getNumericCellValue()); // ����
											}
											break;
										case Cell.CELL_TYPE_BOOLEAN: // ������
											cellValue = String.valueOf(cell.getBooleanCellValue());
											break;
										case Cell.CELL_TYPE_BLANK: // �հ�
											cellValue = "wu"/*cell.getStringCellValue()*/;
											break;
										case Cell.CELL_TYPE_ERROR: // ����
											cellValue = "����";
											break;
										case Cell.CELL_TYPE_FORMULA: // ��ʽ
											cellValue = "����";
											break;
										default:
											cellValue = "����";
						}
						System.out.print(cellValue + "\t");
					}//��
					System.out.println();
				}//��
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
		    System.out.println("����Excelʧ��: ");  
		    e.printStackTrace();  
		  }  
		  
		  for (int i = 0; i < procedureList.size(); i++) {
			  if (workbook != null) {  
				  org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet(procedureList.get(i));  
			    Row row0 = sheet.createRow(0); 
			    String[] name={"����(table)","�ֶ���(field)","�ܱ�Ӱ��ı�(effected table)","�ܱ�Ӱ��ı���ֶ�(effected field)","���������㷽��(where)","������(procedure)","����(package)"};
			    for (int i1 = 0; i1 < 7; i1++) {  
			      Cell cell = row0.createCell(i1, Cell.CELL_TYPE_STRING);  
			      cell.setCellValue(name[i1]);  
			      sheet.autoSizeColumn(i1);//�Զ��������  
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
			      System.out .println("д��Excelʧ��: ");  
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
