package com.zjfw.PMTool;

import org.apache.poi.xssf.usermodel.XSSFCell;

public class ExcelUtil {

	public static String parseCellValue(XSSFCell cell){
		if(cell == null) return "";
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
			return "";
		}
	}
	

	public static String parseType(String str) {
		if(str.equalsIgnoreCase("str") || str.equalsIgnoreCase("string")){
			return "string";
		}else if(str.equalsIgnoreCase("bool") || str.equalsIgnoreCase("boolean")){
			return "boolean";
		}else if(str.equalsIgnoreCase("short")){
			return "short";
		}else if(str.equalsIgnoreCase("int")){
			return "int";
		}else if(str.equalsIgnoreCase("long")){
			return "long";
		}else if(str.equalsIgnoreCase("float")){
			return "float";
		}else if(str.equalsIgnoreCase("double")){
			return "double";
		}else if(str.equalsIgnoreCase("Vector2")){
			return "Vector2";
		}else if(str.equalsIgnoreCase("Vector3")){
			return "Vector3";
		}else if(str.equalsIgnoreCase("List<int>")){
			return "List<int>";
		}else if(str.equalsIgnoreCase("List<Vector2>")){
			return "List<Vector2>";
		}else if(str.equalsIgnoreCase("List<Vector3>")){
			return "List<Vector3>";
		}
		return null;
	}
	
	public static String parseType4Java(String str) {
		if(str.equalsIgnoreCase("str") || str.equalsIgnoreCase("string")){
			return "String";
		}else if(str.equalsIgnoreCase("bool") || str.equalsIgnoreCase("boolean")){
			return "boolean";
		}else if(str.equalsIgnoreCase("short")){
			return "short";
		}else if(str.equalsIgnoreCase("int")){
			return "int";
		}else if(str.equalsIgnoreCase("long")){
			return "long";
		}else if(str.equalsIgnoreCase("float")){
			return "float";
		}else if(str.equalsIgnoreCase("double")){
			return "double";
		}else if(str.equalsIgnoreCase("Vector2")){
			return "Vector2";
		}else if(str.equalsIgnoreCase("Vector3")){
			return "Vector3";
		}else if(str.equalsIgnoreCase("List<int>")){
			return "List<Integer>";
		}else if(str.equalsIgnoreCase("List<Vector2>")){
			return "List<Vector2>";
		}else if(str.equalsIgnoreCase("List<Vector3>")){
			return "List<Vector3>";
		}
		return null;
	}
	
	/**
	 * 是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (s == null) {
			return true;
		}
		s = s.trim();
		if(s.isEmpty() || s.equals("null"))
			return true;
		return false;
	}
	
	public static String OoOoFormat(String str){
		if(isEmpty(str)){
			return null;
		}
		String s = "";
		for (String ss : str.split("_")) {
			char[] name = ss.toCharArray();
			name[0] = (char) (name[0] < 'Z' ? name[0] : name[0] - 32);
			s += String.valueOf(name);
		}
		return s;
	}
	
	/**
	 * 首字母大写
	 * 
	 * @param value
	 * @return
	 */
	public static String firstLetterToUpper(String value) {
		if (isEmpty(value)) {
			return null;
		}
		char[] name = value.toCharArray();
		name[0] = (char) (name[0] < 'Z' ? name[0] : name[0] - 32);
		return String.valueOf(name);
	}
}
