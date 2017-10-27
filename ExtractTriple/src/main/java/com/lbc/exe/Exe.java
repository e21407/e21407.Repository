package com.lbc.exe;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lbc.entity.CheckMethod;
import com.lbc.entity.Symptom;
import com.lbc.entity.Therapy;
import com.lbc.mapper.ExtractMapper;
import com.lbc.util.DBConnector;
import com.lbc.util.Ordinal;

public class Exe {

	static DBConnector connector;
	static ExtractMapper mapper;
	static List<String> catalogNameList;
	static final String filePath = "resources/data/new癌症肿瘤词条";

	public static void main(String[] args) {
		connector = DBConnector.getDBConnectorInstance();
		mapper = connector.getMapper(ExtractMapper.class);
		
//		抽取临床表现
//		catalogNameList = mapper.getCatalogNameByAffiliation("临床表现");
		catalogNameList = mapper.getCatalogNameByAffiliationTouch("症状");
		doExtractSymptom();
		
		
//		抽取检查手段
//		catalogNameList = mapper.getCatalogNameByAffiliation("检查");
//		catalogNameList.addAll(mapper.getCatalogNameByAffiliation("诊断"));
		catalogNameList = mapper.getCatalogNameByAffiliationTouch("检查");
		catalogNameList.addAll(mapper.getCatalogNameByAffiliationTouch("诊断"));
		catalogNameList.addAll(mapper.getCatalogNameByAffiliationTouch("鉴别诊断"));
		doExtractCheck();
		
		
//		抽取治疗手段
		catalogNameList = mapper.getCatalogNameByAffiliationTouch("治疗");
		doExtractTherapy();
	}

	static  void doExtractSymptom() {
		File filefolder = new File(filePath);
		File[] files = filefolder.listFiles();

		for (File file : files) {
			Document currentDoc = null;
			try {
				currentDoc = Jsoup.parse(file, "UTF-8", ""); // 读取文档，加载jsoup模型
			} catch (IOException e) {
				e.printStackTrace();
			}
			String diseaseName = currentDoc.select("h1").text(); // 获取当前疾病名字
			Elements mainContent = currentDoc.getElementsByAttributeValue("class", "main-content");
			if (mainContent.size() == 0)
				break;
			Elements anchorList = mainContent.first().getElementsByAttributeValue("class", "anchor-list");
			for (Element anchorItem : anchorList) { // 提取属性名
				// 当前anchorItem是锚标签，下一个元素是目录标签
				anchorItem = anchorItem.nextElementSibling();
				anchorItem.select("span").remove();
				if (!catalogNameList.contains(anchorItem.text()))
					continue;
				System.out.println(diseaseName + "\t\t" + anchorItem.text());
				while (!anchorItem.attr("id").equals("open-tag") && !anchorItem.attr("class").equals("anchor-list")
						&& !anchorItem.is("dl")) {
					anchorItem = anchorItem.nextElementSibling();
					if (anchorItem.select("b").size() > 0) { // 存在加粗文本，直接抽取
						if("".equals(Ordinal.removeOrdinal(anchorItem.select("b").text())))
							continue;
						Symptom sym = new Symptom();
						sym.setDisease_name(diseaseName);
						sym.setSymptom(Ordinal.removeOrdinal(anchorItem.select("b").text()));
						sym.setFull_text(anchorItem.text());
						sym.setStatus(1);
						sym.setRemark1("初次创建");
						sym.setRemark2("加粗字体直接抽取");
						mapper.addSymptom(sym);
					} else if (Ordinal.isStartOrdinal(anchorItem.text()) != 0) {
						if (Ordinal.removeOrdinal(anchorItem.text()).length() <= 20) {
							Symptom sym = new Symptom();
							sym.setDisease_name(diseaseName);
							sym.setSymptom(Ordinal.removeOrdinal(anchorItem.text()));
							sym.setFull_text(anchorItem.text());
							sym.setStatus(1);
							sym.setRemark1("初次创建");
							sym.setRemark2("序号开头长度少于20文本");
							mapper.addSymptom(sym);
						}
					} else {
						if("".equals(anchorItem.text().trim()))
							continue;
						Symptom sym = new Symptom();
						sym.setDisease_name(diseaseName);
						sym.setFull_text(anchorItem.text());
						sym.setStatus(1);
						sym.setRemark1("初次创建");
						sym.setRemark2("原文本");
						mapper.addSymptom(sym);
					}
					connector.commit();
				}
			}
		}
	}
	
	static  void doExtractCheck() {
		File filefolder = new File(filePath);
		File[] files = filefolder.listFiles();

		for (File file : files) {
			Document currentDoc = null;
			try {
				currentDoc = Jsoup.parse(file, "UTF-8", ""); // 读取文档，加载jsoup模型
			} catch (IOException e) {
				e.printStackTrace();
			}
			String diseaseName = currentDoc.select("h1").text(); // 获取当前疾病名字
			Elements mainContent = currentDoc.getElementsByAttributeValue("class", "main-content");
			if (mainContent.size() == 0)
				break;
			Elements anchorList = mainContent.first().getElementsByAttributeValue("class", "anchor-list");
			for (Element anchorItem : anchorList) { // 提取属性名
				// 当前anchorItem是锚标签，下一个元素是目录标签
				anchorItem = anchorItem.nextElementSibling();
				anchorItem.select("span").remove();
				if (!catalogNameList.contains(anchorItem.text()))
					continue;
				System.out.println(diseaseName + "\t\t" + anchorItem.text());
				while (!anchorItem.attr("id").equals("open-tag") && !anchorItem.attr("class").equals("anchor-list")
						&& !anchorItem.is("dl")) {
					anchorItem = anchorItem.nextElementSibling();
					if (anchorItem.select("b").size() > 0) { // 存在加粗文本，直接抽取
						if("".equals(Ordinal.removeOrdinal(anchorItem.select("b").text())))
							continue;
						CheckMethod che = new CheckMethod();
						che.setDisease_name(diseaseName);
						che.setCheck_method(Ordinal.removeOrdinal(anchorItem.select("b").text()));
						che.setFull_text(anchorItem.text());
						che.setStatus(1);
						che.setRemark1("初次创建");
						che.setRemark2("加粗字体直接抽取");
						mapper.addCheck(che);
					} else if (Ordinal.isStartOrdinal(anchorItem.text()) != 0) {
						if (Ordinal.removeOrdinal(anchorItem.text()).length() <= 20) {
							CheckMethod che = new CheckMethod();
							che.setDisease_name(diseaseName);
							che.setCheck_method(Ordinal.removeOrdinal(anchorItem.text()));
							che.setFull_text(anchorItem.text());
							che.setStatus(1);
							che.setRemark1("初次创建");
							che.setRemark2("序号开头长度少于20文本");
							mapper.addCheck(che);
						}
					} else {
						if("".equals(anchorItem.text().trim()))
							continue;
						CheckMethod che = new CheckMethod();
						che.setDisease_name(diseaseName);
						che.setFull_text(anchorItem.text());
						che.setStatus(1);
						che.setRemark1("初次创建");
						che.setRemark2("原文本");
						mapper.addCheck(che);
					}
					connector.commit();
				}
			}
		}
	}

	static void doExtractTherapy() {
		File filefolder = new File(filePath);
		File[] files = filefolder.listFiles();

		for (File file : files) {
			Document currentDoc = null;
			try {
				currentDoc = Jsoup.parse(file, "UTF-8", ""); // 读取文档，加载jsoup模型
			} catch (IOException e) {
				e.printStackTrace();
			}
			String diseaseName = currentDoc.select("h1").text(); // 获取当前疾病名字
			Elements mainContent = currentDoc.getElementsByAttributeValue("class", "main-content");
			if (mainContent.size() == 0)
				break;
			Elements anchorList = mainContent.first().getElementsByAttributeValue("class", "anchor-list");
			for (Element anchorItem : anchorList) { // 提取属性名
				// 当前anchorItem是锚标签，下一个元素是目录标签
				anchorItem = anchorItem.nextElementSibling();
				anchorItem.select("span").remove();
				if (!catalogNameList.contains(anchorItem.text()))
					continue;
				System.out.println(diseaseName + "\t\t" + anchorItem.text());
				while (!anchorItem.attr("id").equals("open-tag") && !anchorItem.attr("class").equals("anchor-list")
						&& !anchorItem.is("dl")) {
					anchorItem = anchorItem.nextElementSibling();
					if (anchorItem.select("b").size() > 0) { // 存在加粗文本，直接抽取
						if("".equals(Ordinal.removeOrdinal(anchorItem.select("b").text())))
							continue;
						Therapy the = new Therapy();
						the.setDisease_name(diseaseName);
						the.setTherapy(Ordinal.removeOrdinal(anchorItem.select("b").text()));
						the.setFull_text(anchorItem.text());
						the.setStatus(1);
						the.setRemark1("初次创建");
						the.setRemark2("加粗字体直接抽取");
						mapper.addTherapy(the);
					} else if (Ordinal.isStartOrdinal(anchorItem.text()) != 0) {
						if (Ordinal.removeOrdinal(anchorItem.text()).length() <= 20) {
							Therapy the = new Therapy();
							the.setDisease_name(diseaseName);
							the.setTherapy(Ordinal.removeOrdinal(anchorItem.text()));
							the.setFull_text(anchorItem.text());
							the.setStatus(1);
							the.setRemark1("初次创建");
							the.setRemark2("序号开头长度少于20文本");
							mapper.addTherapy(the);
						}
					} else {
						if("".equals(anchorItem.text().trim()))
							continue;
						Therapy the = new Therapy();
						the.setDisease_name(diseaseName);
						the.setFull_text(anchorItem.text());
						the.setStatus(1);
						the.setRemark1("初次创建");
						the.setRemark2("原文本");
						mapper.addTherapy(the);
					}
					connector.commit();
				}
			}
		}
	}
}
