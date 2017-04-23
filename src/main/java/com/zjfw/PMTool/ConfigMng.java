package com.zjfw.PMTool;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class ConfigMng {

	public static void main(String[] args) {
        Properties prop = new Properties();     
        try{
            //读取属性文件a.properties
            InputStream in = new BufferedInputStream (new FileInputStream("Config.properties"));
            prop.load(in);     ///加载属性列表
            Iterator<String> it=prop.stringPropertyNames().iterator();
            while(it.hasNext()){
                String key=it.next();
                System.out.println(key+":"+prop.getProperty(key));
            }
            in.close();
            
            ///保存属性到b.properties文件
            FileOutputStream oFile = new FileOutputStream("b.properties", true);//true表示追加打开
            prop.setProperty("phone", "10086");
            prop.store(oFile, "The New properties file");
            oFile.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
	}
	
	public static String srcXlsxDir;
	public static String outJson;
	public static String outCSharp;
	public static String outJava;
	
	public static void init(){
		Properties prop = new Properties();
        //读取属性文件
        InputStream in;
		try {
			in = new BufferedInputStream (new FileInputStream("Config.properties"));
			prop.load(in);     ///加载属性列表
			srcXlsxDir = prop.getProperty("srcXlsxDir");
			outJson = prop.getProperty("outJson");
			outCSharp = prop.getProperty("outCSharp");
			outJava = prop.getProperty("outJava");
	        in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
