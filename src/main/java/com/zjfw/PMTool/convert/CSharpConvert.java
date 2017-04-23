package com.zjfw.PMTool.convert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zjfw.PMTool.ExcelUtil;

public class CSharpConvert extends AbstractConvert{

	@Override
	public String getExtStr() {
		return ".cs";
	}
	
	@Override
	public void doWithFolder(String srcDir, String fName, String toDir) {
//		super.doWithFolder(srcDir, fName, toDir);
		File file = new File(toDir);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdir();
		}
	}
	
	@Override
	public void doWithFile(String srcFileStr, String fName, String toFileStr) {
		super.doWithFile(srcFileStr, fName, toFileStr);
		excel2CSharp(srcFileStr, fName, toFileStr);
	}
	
	public void excel2CSharp(String srcFileStr, String fName, String toFileStr){
        OutputStream outFile;
		try {
			String cName = fName.substring(0, fName.length() - 5);
			cName = cName.substring(0, 1).toUpperCase().concat(cName).substring(1);
//			System.err.println(getHeader(cName));
//			System.err.println(getBody(srcFileStr, fName));
//			System.err.println(getFooter(cName));
			
			StringBuilder sb = new StringBuilder(getHeader(cName))
					.append(getBody(srcFileStr, fName))
					.append(getFooter(cName));
			
			String toFile = toFileStr + cName + getExtStr();
			File file = new File(toFile);
			
			if(file.exists()){
				file.delete();
				file.createNewFile();
			}else{
				file.createNewFile();
			}
			outFile = new FileOutputStream(file);
			outFile.write(sb.toString().getBytes());
			outFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public StringBuilder getHeader(String cName){
		return new StringBuilder("using UnityEngine;\n")
				.append("using System;\n")
				.append("using System.Collections;\n")
				.append("using System.Collections.Generic;\n")
				.append("\n")
				.append("namespace DataDefine {\n")
				.append("\n")
				.append("\t[Serializable]\n")
				.append("\tpublic class ").append(cName).append(" {\n");
	}
	
	public StringBuffer getBody(String srcFileStr, String fName){
		InputStream srcFile;
        XSSFWorkbook srcWB;
		try {
			srcFile = new FileInputStream(srcFileStr + fName);
			srcWB = new XSSFWorkbook(srcFile);
			XSSFSheet srcSt = srcWB.getSheetAt(0);
			XSSFRow typeRow = srcSt.getRow(0);
//			XSSFRow cmtRow = srcSt.getRow(1);
			XSSFRow titleRow = srcSt.getRow(2);
			StringBuffer sb = new StringBuffer();
			String title = "";
			String type = "";
			for (int i = 0; i < typeRow.getLastCellNum(); i++) {
				title = titleRow.getCell(i).getStringCellValue();
				type = ExcelUtil.parseType(typeRow.getCell(i).getStringCellValue());
				sb.append("\t\tpublic ").append(type).append(" ").append(title).append(";\n");
			}
			srcWB.close();
			srcFile.close();
			return sb;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public StringBuilder getFooter(String cName){
		return new StringBuilder("\t}\n\n\tpublic class ").append(cName).append("List{\n")
				.append("\t\tpublic List<").append(cName).append("> ").append(cName.toLowerCase()).append("_list;\n\t};\n};");
	}
	
}
