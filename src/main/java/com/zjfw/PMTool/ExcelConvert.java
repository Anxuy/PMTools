package com.zjfw.PMTool;

import com.zjfw.PMTool.convert.CSharpConvert;
import com.zjfw.PMTool.convert.JavaConvert;
import com.zjfw.PMTool.convert.JsonConvert;

public class ExcelConvert {

	
	public static void main(String[] args) {
		System.err.println("------------   start   ------------");
		ConfigMng.init();
		System.out.println("----   out Json:" + ConfigMng.outJson);
		new JsonConvert().convert(ConfigMng.srcXlsxDir, ConfigMng.outJson);
		System.out.println("----   out Json:" + ConfigMng.outJson + "   end ----");
		
		System.out.println("----   out CSharp:" + ConfigMng.outCSharp);
		new CSharpConvert().convert(ConfigMng.srcXlsxDir, ConfigMng.outCSharp, false);
		System.out.println("----   out CSharp:" + ConfigMng.outCSharp + "   end ----");
		
		System.out.println("----   out Java:" + ConfigMng.outJava);
		new JavaConvert().convert(ConfigMng.srcXlsxDir, ConfigMng.outJava, false);
		System.out.println("----   out Java:" + ConfigMng.outJava + "   end ----");
		
		System.err.println();
		System.err.println("------------   end   ------------");
		
	}
}
