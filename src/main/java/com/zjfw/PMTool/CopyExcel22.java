package com.zjfw.PMTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CopyExcel22 {

	private static String tplExcelFilePath = "F:\\tmp\\out\\tpl.xlsx";
	
	private static String tempExcelFilePath = "F:\\tmp\\out\\temp.xlsx";
//	private static String tplExcelFilePath = "F:\\svn_xxqz3\\server\\tmp\\LibConvertC#JsonTools\\tpl.xlsx";
	
	private static String srcDir = "F:\\tmp\\src\\";
//	private static String srcDir = "F:\\svn_xxqz3\\design\\gamedata\\";
	
	public static void main(String[] args) {
		System.err.println("------------   start   ------------");
		excute();
//		doWithFile(srcDir+"weapon\\Weapon.xlsx");
		System.err.println();
		System.err.println("------------   end   ------------");
	}
	
	
	public static void copyFile(){
		File tplFile = new File(tplExcelFilePath);
		
	}
	
	public static void excute(){
		recursiveInterceptFileInDir(srcDir);
	}
	
	public static void recursiveInterceptFileInDir(String dirPath){
		File dir = new File(dirPath);
		if(dir.isDirectory()){
			for (String fname : dir.list()) {
				File f = new File(dirPath + fname);
				if(f.isDirectory()){
					String srcdirStr = dirPath + fname +"\\";
					System.err.println(srcdirStr);
					String outDir = srcdirStr.replace("src", "out");
					new File(outDir).mkdirs();
					recursiveInterceptFileInDir(srcdirStr);
				}else{
					String srcFileStr = dirPath + fname;
					String outFileStr = srcFileStr.replace("src", "out");
					try {
						new File(srcFileStr).createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
					doWithFile(srcFileStr, outFileStr);
				}
			}
		}
	}
	
	public static void doWithFile(String srcFileStr, String outFileStr){
		System.err.println("------" + srcFileStr);
		copyTplFile(outFileStr);
		copyData2NewFile(srcFileStr, outFileStr);
		System.err.println("------" + srcFileStr + "----   end ----");
//		try{
//	        InputStream sourceBytes = new FileInputStream(fPath);
//	        XSSFWorkbook wb = new XSSFWorkbook(sourceBytes);
//	        XSSFSheet st = wb.getSheetAt(0);
//	        for (int i = 0; i <= st.getLastRowNum(); i++) {
//				XSSFRow r = st.getRow(i);
//				if(r != null){
//					for (int j = 0; j < r.getLastCellNum(); j++) {
//						XSSFCell cell = r.getCell(j);
//						if(cell != null){
//							String v = parseCellValue(cell);
//							if(v != null && v != ""){
//								System.err.print( v + " ");
//							}
//						}
//					}
//					System.err.println();
//				}
//			}
//			int rowCnt = st.getLastRowNum();
//			int colCnt = st.getRow(0).getLastCellNum();
//			System.err.println("  rowCnt " + rowCnt + "   colCnt " + colCnt);
//			wb.close();
//			sourceBytes.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	private static void copyData2NewFile(String srcFileStr, String outFileStr) {
		copyTplFile(tempExcelFilePath);
		
        InputStream srcFile;
        XSSFWorkbook srcWB;
        OutputStream outFile;
		try {
			srcFile = new FileInputStream(srcFileStr);
			srcWB = new XSSFWorkbook(srcFile);
			XSSFSheet srcST = srcWB.getSheetAt(0);
//			XSSFRow commentRow = srcST.getRow(1);
			XSSFWorkbook tmpWB = new XSSFWorkbook(tempExcelFilePath);
			XSSFSheet tmpSt = tmpWB.getSheetAt(0);
//			XSSFRow tmpCmtRow = tmpSt.getRow(1);
			
			// 注释
			copyRow(srcST.getRow(1), tmpSt.getRow(1));
			// id
			copyRow(srcST.getRow(2), tmpSt.getRow(2));
			// type
			copyTypeRow(srcST.getRow(0), tmpSt.getRow(3));
			XSSFRow srcRow = null;
			XSSFRow toRow = null;
			for (int i = 3; i <= srcST.getLastRowNum(); i++) {
				srcRow = srcST.getRow(i);
				toRow = tmpSt.getRow(i+1);
				if(srcRow != null){
					if(toRow == null){
						toRow = tmpSt.createRow(i+1);
					}
					copyRow(srcRow, toRow);
				}
				System.err.println(srcFileStr + "  " + i +" / " + srcST.getLastRowNum());
			}
			
			outFile = new FileOutputStream(outFileStr);
			tmpWB.write(outFile);
			outFile.close();
			tmpWB.close();
			srcWB.close();
			srcFile.close();
			new File(tempExcelFilePath).delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void copyTypeRow(XSSFRow srcRow, XSSFRow toRow){
		for (int i = 0; i < srcRow.getLastCellNum(); i++) {
			XSSFCell cell = srcRow.getCell(i);
			if(cell != null){
				String v = parseCellValue(cell);
				if(v != null){
					v = v.equals("str")? "string":v;
					toRow.createCell(i).setCellValue(v);
				}
			}
		}
	}
	
	public static void copyRow(XSSFRow srcRow, XSSFRow toRow){
		for (int i = 0; i < srcRow.getLastCellNum(); i++) {
			XSSFCell cell = srcRow.getCell(i);
			if(cell != null){
				String v = parseCellValue(cell);
				if(v != null){
//					System.err.print( v + " ");
					toRow.createCell(i).setCellValue(v);
				}
			}
		}
	}
	
	public static void copyTplFile(String tofilePath){
		try {
			FileInputStream fi = new FileInputStream(new File(tplExcelFilePath));
			FileOutputStream fo = new FileOutputStream(new File(tofilePath));
			byte[] tmp = new byte[fi.available()];
			fi.read(tmp);
			fo.write(tmp);
			fo.flush();
			fo.close();
			fi.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String parseCellValue(XSSFCell cell){
		switch (cell.getCellTypeEnum()) {
		case NUMERIC:
			return cell.getNumericCellValue() + "";
		case STRING:
			return cell.getStringCellValue();
		case FORMULA:
			return cell.getCellFormula();
		case _NONE:
		case BLANK:
			return "";
		case BOOLEAN:
			return cell.getBooleanCellValue()?"1":"0";
		default:
			return null;
		}
	}
	
}
