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

public class JavaConvert extends AbstractConvert {

	@Override
	public String getExtStr() {
		return ".java";
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
		excel2Java(srcFileStr, fName, toFileStr);
	}
	
	public void excel2Java(String srcFileStr, String fName, String toFileStr){
        OutputStream outFile;
		try {
			fName = fName.substring(0, fName.length() - 5);
			String cName = fName.substring(0, 1).toUpperCase().concat(fName).substring(1);
			
//			System.err.println(getHeader(fName.concat(".json"), cName));
//			System.err.println(getBody(srcFileStr, fName.concat(".xlsx")));
//			System.err.println(getFooter(cName));
			
			StringBuilder sb = new StringBuilder(getHeader(fName.concat(".json"), cName))
					.append(getBody(srcFileStr, fName.concat(".xlsx")))
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

	public StringBuilder getHeader(String srcFileName, String cName){
		return new StringBuilder("package com.xxqz.game.template2;\n\n") 
				.append("import java.util.ArrayList;\n")
				.append("import java.util.Collections;\n")
				.append("import java.util.HashMap;\n")
				.append("import java.util.Iterator;\n")
				.append("import java.util.List;\n")
				.append("import java.util.Map;\n")
				.append("import java.util.Set;\n\n")
				.append("import com.zjfw.framwork.base.annotation.JsonTemplate;\n")
				.append("import com.zjfw.framwork.base.domain.BaseTemplate;\n")
				.append("import com.zjfw.framwork.base.exception.TemplateDataException;\n\n")
				.append("@JsonTemplate(template = { \"jsonTpl/").append(srcFileName).append("\" })\n")
				.append("public class ").append(cName).append("Template extends BaseTemplate {\n\n");
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
			StringBuffer sb = new StringBuffer(getDef4Fields(typeRow, titleRow))
					.append(getExtendsMtd4Class())
					.append(getGetSetMtd4Fields(typeRow, titleRow));
			srcWB.close();
			srcFile.close();
			return sb;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static StringBuilder getDef4Fields(XSSFRow typeRow, XSSFRow titleRow) throws Exception{
		StringBuilder sb = new StringBuilder();
		String title = "";
		String type = "";
		for (int i = 0; i < typeRow.getLastCellNum(); i++) {
			title = titleRow.getCell(i).getStringCellValue();
			type = ExcelUtil.parseType4Java(typeRow.getCell(i).getStringCellValue());
			sb.append("\tprivate ").append(type).append(" ").append(title).append(";\n\n");
		}
		return sb;
	}
	
	public StringBuilder getExtendsMtd4Class(){
		StringBuilder sb = new StringBuilder("\t@Override\n")
		.append("\tpublic void init() throws TemplateDataException {\n")
		.append("\t}\n\n")
		.append("\t@Override\n")
		.append("\tpublic boolean validate() throws TemplateDataException {\n")
		.append("\t\treturn true;\n")
		.append("\t}\n\n");
		return sb;
	}
	
	public static StringBuilder getGetSetMtd4Fields(XSSFRow typeRow, XSSFRow titleRow) throws Exception{
		StringBuilder sb = new StringBuilder();
		String title = "";
		String type = "";
		String firstUpcaseKey = "";
		for (int i = 0; i < typeRow.getLastCellNum(); i++) {
			title = titleRow.getCell(i).getStringCellValue();
			type = ExcelUtil.parseType4Java(typeRow.getCell(i).getStringCellValue());
			firstUpcaseKey = ExcelUtil.OoOoFormat(title);
			sb.append("\tpublic ").append(type).append(" get").append(firstUpcaseKey).append("() {;\n")
			.append("\t\tresturn ").append(title).append(";\n")
			.append("\t}\n\n\tpublic void set").append(firstUpcaseKey).append("(").append(type).append(" ").append(title).append(") {\n")
			.append("\t\tthis.").append(title).append(" = ").append(title).append(";\n\t}\n\n");
		}
		return sb;
	}
	
	public StringBuilder getFooter(String cName){
		return new StringBuilder("}");
	}
}
