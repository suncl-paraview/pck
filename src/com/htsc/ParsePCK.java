package com.htsc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParsePCK {

	/**
	     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
	 * @return 
	     */
	    public static List<ArrayList<String>> readFileByChars(String fileName) {
	    	
	    	List<ArrayList<String>> allList= new ArrayList<ArrayList<String>>();
	    	List<String> procedureList= new ArrayList<String>();
	    	List<String> tableList= new ArrayList<String>();
	    	List<String> fieldList= new ArrayList<String>();
	    	List<String> etableList= new ArrayList<String>();
	    	List<String> efieldList= new ArrayList<String>();
	    	List<String> packageList= new ArrayList<String>();
	    	List<String> whereList= new ArrayList<String>();
	    	
	    	packageList.add(fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length()-4));
	        File file = new File(fileName);
	        Reader reader = null;
	        try {
	            //System.out.println("以字符为单位读取文件内容，一次读多个字节：");
	            // 一次读多个字符
	            char[] tempchars = new char[30000];
	            int charread = 0;
	            reader = new InputStreamReader(new FileInputStream(fileName));
	            // 读入多个字符到字符数组中，charread为一次读取字符数
	            while ((charread = reader.read(tempchars)) != -1) {
	                // 同样屏蔽掉\r不显示
	            	String nairong = String.valueOf(tempchars).toLowerCase();
	            	

	            	nairong = nairong.toLowerCase().replaceAll("\\/\\*[\\s\\S]*?\\*\\/|--[\\s\\S]*?\n", " ");
	            	String[] last=nairong.split(" package");
	            	nairong=last[2];
	            	String[] pname=last[2].split("procedure");
	            	for (int i = 1; i < pname.length; i++) {
	            		procedureList.add(pname[i].split("\\(")[0]);
					}
	            	System.out.println(nairong);
	            	System.err.println(procedureList.get(1));
	            	
	            	
	            	for (int i = 1; i < pname.length; i++) {
	            		String str=pname[i];
	            		str = str.replaceAll("\\r|\\n|\\t", " ");
		            	str = str.replaceAll("\\--\\S+", " ");
		            	//str = str.replaceAll("\\--.* ", " ");
		            	str = str.replaceAll("\\s{1,}", " ");
		            	if (str.toLowerCase().contains("Insert Into".toLowerCase())){
		            		String insert=str.substring(str.indexOf("Insert Into".toLowerCase()));
		            		insert=insert.split(";")[0];
			            	System.out.println(insert);
			               /* String pattern = "into (\\w+)[\\n ]*\\(([\\w ,]+)\\)[\\n ]*select ([\\w ,.]+) from ([\\s\\S]+)";
			                Pattern r = Pattern.compile(pattern);
			                Matcher m = r.matcher(insert);
			                if (m.find()) {
			                   System.out.println("Found value1: " + m.group(1) );
			                   tableList.add(m.group(1));
			                   System.out.println("Found value2: " + m.group(2) );
			                   String field[]=m.group(2).split(",");
			                   for (int j = 0; j < field.length; j++) {
			                	   fieldList.add(field[j].trim());
			                   }
			                   System.out.println("Found value3: " + m.group(3) );
			                   String efield[]=m.group(3).split(",");
			                   for (int j = 0; j < field.length; j++) {
			                	   efieldList.add(efield[j].trim());
			                   }
			                   System.out.println("Found value4: " + m.group(4) );
			                   
			                   whereList.add(m.group(4));
			                   
			                   
			                   
			                } else {
			                   System.out.println("NO MATCH");
			                }*/
			            }
					}
	            }//
	            allList.add((ArrayList<String>) tableList);
	            allList.add((ArrayList<String>) fieldList);
	            allList.add((ArrayList<String>) etableList);
	            allList.add((ArrayList<String>) efieldList);
	            allList.add((ArrayList<String>) whereList);
	            allList.add((ArrayList<String>) procedureList);
	            allList.add((ArrayList<String>) packageList);
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        } 
	       return allList; 
	    }

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static void readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 随机读取文件内容
	 */
	public static void readFileByRandomAccess(String fileName) {
		RandomAccessFile randomFile = null;
		try {
			System.out.println("随机读取一段文件内容：");
			// 打开一个随机访问文件流，按只读方式
			randomFile = new RandomAccessFile(fileName, "r");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 读文件的起始位置
			int beginIndex = (fileLength > 4) ? 4 : 0;
			// 将读文件的开始位置移到beginIndex位置。
			randomFile.seek(beginIndex);
			byte[] bytes = new byte[10];
			int byteread = 0;
			// 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
			// 将一次读取的字节数赋给byteread
			while ((byteread = randomFile.read(bytes)) != -1) {
				System.out.write(bytes, 0, byteread);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 显示输入流中还剩的字节数
	 */
	private static void showAvailableBytes(InputStream in) {
		try {
			System.out.println("当前字节输入流中的字节数为:" + in.available());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		/*
		 * String fileName = "C:/temp/newTemp.txt";
		 * ReadFromFile.readFileByBytes(fileName);
		 * ReadFromFile.readFileByChars(fileName);
		 * ReadFromFile.readFileByLines(fileName);
		 * ReadFromFile.readFileByRandomAccess(fileName);
		 */
		// readFileByLines("F:\\scl\\ETL_MS_CUST_PKG.pck");
		readFileByChars("F:\\scl\\ETL_MS_CUST_PKG.pck");
		/*
		 * String fileName="F:\\scl\\etl_mf_inve_pkg.pck";
		 * System.out.println(fileName);
		 * System.out.println(fileName.substring(fileName.lastIndexOf("\\")+1,
		 * fileName.length()-4));
		 */

	}

}
