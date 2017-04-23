package com.zjfw.PMTool.convert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjfw.PMTool.ExcelUtil;

public class JsonConvert extends AbstractConvert {
	
	@Override
	public String getExtStr() {
		return ".json";
	}
	
	@Override
	public void doWithFile(String srcFileStr, String fName, String toFileStr) {
		super.doWithFile(srcFileStr, fName, toFileStr);
		excel2Json(srcFileStr, fName, toFileStr);
	}
	
	public void excel2Json(String srcFileStr, String fName, String toFileStr){
		InputStream srcFile;
        XSSFWorkbook srcWB;
        OutputStream outFile;
		try {
			srcFile = new FileInputStream(srcFileStr + fName);
			srcWB = new XSSFWorkbook(srcFile);
			XSSFSheet srcSt = srcWB.getSheetAt(0);
			XSSFRow titleRow = srcSt.getRow(2);
			List<String> titleLst = new ArrayList<String>();
			for (int i = 0; i < titleRow.getLastCellNum(); i++) {
				titleLst.add(titleRow.getCell(i).getStringCellValue());
			}
			
			XSSFRow dRow = null;
			JSONArray dataLst = new JSONArray();
			JSONObject d = null;
			for (int i = 3; i <= srcSt.getLastRowNum(); i++) {
				dRow = srcSt.getRow(i);
				if(dRow != null){
					d = new JSONObject();
					for (int j = 0; j < titleLst.size(); j++) {
						d.put(titleLst.get(j), ExcelUtil.parseCellValue(dRow.getCell(j)));
					}
					dataLst.add(d);
				}
//				System.out.println( "   " + fName + "  " + i + " / " + srcSt.getLastRowNum());
			}
			JSONObject root = new JSONObject();
			fName = fName.substring(0, fName.length() - 5);
			root.put(fName, dataLst);
			String toFile = toFileStr + fName + getExtStr();
			File file = new File(toFile);
			
			if(file.exists()){
				file.delete();
				file.createNewFile();
			}else{
				file.createNewFile();
			}
			outFile = new FileOutputStream(file);
			outFile.write(root.toJSONString().getBytes());
			outFile.close();
			srcWB.close();
			srcFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
