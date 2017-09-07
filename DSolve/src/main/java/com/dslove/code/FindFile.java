package com.dslove.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 寻找所有和肿瘤癌症相关的页面
 * 
 * @author Liubaichuan
 * @since 2017-09-02
 *
 */
public class FindFile {

	static String filePath1 = "resources/data/疾病分类";
	static String filePath2 = "resources/data/科学百科疾病症状分类";
	static String filePath3 = "resources/data/医学分类";
	static String prePath = "resources/data/new癌症肿瘤词条/";

	public static void main(String[] args) throws IOException {
		int num = 0;
		ArrayList<String> diseaseName = new ArrayList<String>();
		File filefolder1 = new File(filePath1);
		File filefolder2 = new File(filePath2);
		File filefolder3 = new File(filePath3);

		File[] files = filefolder1.listFiles();
		for (File file : files) {
			String[] strs = file.getName().split("\\.");
			if (strs[1].contains("癌") || strs[1].contains("瘤"))
				if (!diseaseName.contains(strs[1])) {
					String toFileName = prePath + ++num + "." + strs[1] + ".html";
					File toFile = new File(toFileName);
					copyFile(file, toFile);
					diseaseName.add(strs[1]);
				}
		}
		files = filefolder2.listFiles();
		for (File file : files) {
			String[] strs = file.getName().split("\\.");
			if (strs[1].contains("癌") || strs[1].contains("瘤"))
				if (!diseaseName.contains(strs[1])) {
					String toFileName = prePath + ++num + "." + strs[1] + ".html";
					File toFile = new File(toFileName);
					copyFile(file, toFile);
					diseaseName.add(strs[1]);
				}
		}
		files = filefolder3.listFiles();
		for (File file : files) {
			String[] strs = file.getName().split("\\.");
			if (strs[1].contains("癌") || strs[1].contains("瘤"))
				if (!diseaseName.contains(strs[1])) {
					String toFileName = prePath + ++num + "." + strs[1] + ".html";
					File toFile = new File(toFileName);
					copyFile(file, toFile);
					diseaseName.add(strs[1]);
				}
		}
		System.out.println("done!");
	}

	static public void copyFile(File fromFile, File toFile) throws IOException {
		FileInputStream ins = new FileInputStream(fromFile);
		FileOutputStream out = new FileOutputStream(toFile);
		byte[] b = new byte[1024];
		int n = 0;
		while ((n = ins.read(b)) != -1) {
			out.write(b, 0, b.length);
		}

		ins.close();
		out.close();
	}

}
