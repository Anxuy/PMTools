package com.zjfw.PMTool.convert;

import java.io.File;
import java.io.IOException;

public abstract class AbstractConvert {
	
	public abstract String getExtStr();
	
	public void convert(String srcDir, String toDir){
		recursiveInterceptFileInDir(srcDir, toDir, true);
	}
	
	public void convert(String srcDir, String toDir, boolean isDeepDir){
		recursiveInterceptFileInDir(srcDir, toDir, isDeepDir);
	}
	
	public void recursiveInterceptFileInDir(String srcDir, String toDir){
		recursiveInterceptFileInDir(srcDir, toDir, true);
	}
	
	public void recursiveInterceptFileInDir(String srcDir, String toDir, boolean isDeepDir){
		File dir = new File(srcDir);
		if(dir.isDirectory()){
			for (String fname : dir.list()) {
				File f = new File(srcDir + fname);
				if(f.isDirectory()){
					doWithFolder(srcDir, fname, toDir);
					if(isDeepDir){
						recursiveInterceptFileInDir(srcDir + fname +"\\", toDir + fname +"\\");
					}else{
						recursiveInterceptFileInDir(srcDir + fname +"\\", toDir, false);
					}
				}else{
					doWithFile(srcDir, fname, toDir);
				}
			}
		}
	}
	
	public void doWithFolder(String srcDir, String fName, String toDir){
		System.err.println(srcDir + fName +"\\");
		System.err.println(toDir + fName + "\\");
		new File(toDir + fName + "\\").mkdirs();
	}
	
	public void doWithFile(String srcFileStr, String fName, String toFileStr){
		System.err.println("  " + srcFileStr + fName);
		String toFile = toFileStr + fName.substring(0, fName.lastIndexOf(".")) + getExtStr();
		System.err.println("  ->" + toFile);
//		try {
//			new File(toFile).createNewFile();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
