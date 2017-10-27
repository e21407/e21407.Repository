package com.dslove.code;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.print.attribute.HashAttributeSet;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dslove.mapper.DTSolveMapper;
import com.dslove.pojo.Disease;
import com.dslove.pojo.PropertyName;
import com.dslove.pojo.PropertyValue;

import util.Ordinal;

/**
 * 
 * @author Liubaichuan
 * @since 2017-09-03
 *
 */
public class DTSolve {
	final static String filePath = "resources/data/testGroup";
	/*
	 * static ArrayList<Disease> diseaseList; static ArrayList<PropertyName>
	 * propertyNameList; static ArrayList<PropertyValue> propertyValueList;
	 */
	static SqlSession session;
	static DTSolveMapper mapper;
	static Document currentDoc = null;
	static Disease currentDis;

	public static void main(String[] args) {
		/*
		 * diseaseList = new ArrayList<Disease>(); propertyNameList = new
		 * ArrayList<PropertyName>(); propertyValueList = new
		 * ArrayList<PropertyValue>();
		 */
		File filefolder = new File(filePath);
		File[] files = filefolder.listFiles();

		// 初始化数据库连接
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader("mybatis-conf2.xml");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sqlSessionFactory.openSession();
		mapper = session.getMapper(DTSolveMapper.class);

		for (File file : files) {
			try {
				currentDoc = Jsoup.parse(file, "UTF-8", ""); // 读取文档，加载jsoup模型
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 抽取疾病名字和概要
			currentDis = new Disease();
			String diseaseName = currentDoc.select("h1").text();
			currentDis.setName(diseaseName);
			Elements sumEles = currentDoc.getElementsByAttributeValue("class", "lemma-summary");
			Elements ele = sumEles.attr("class", "para");
			String summary = ele.first().text();
			currentDis.setSummary(summary);
			mapper.addDisease(currentDis);

			System.out.println("doing " + diseaseName);
			// 抽取基本信息
			extractBasicInfo();
			// 抽取目录信息
			extractCatalog();

			// 抽取主要内容
			/*
			 * Elements mianContent = currentDoc.getElementsByAttributeValue("class",
			 * "main-content"); if (mianContent.size() > 0) { Elements anchorList =
			 * mianContent.first().getElementsByAttributeValue("class", "anchor-list"); for
			 * (Element anchorItem : anchorList) { // 提取属性名 anchorItem =
			 * anchorItem.nextElementSibling(); anchorItem.select("span").remove(); String
			 * itemName = anchorItem.text(); PropertyName item = new PropertyName();
			 * item.setProperty_name(itemName);
			 * 
			 * anchorItem = anchorItem.nextElementSibling(); if (anchorItem.is("table")) {
			 * 
			 * }
			 * 
			 * } }
			 */

		}
		System.out.println("done!");
	}

	/**
	 * 从数据库中查找是否存在example对象，若存在则返回查找结果，否则将example插入数据库并返回原对象
	 * 
	 * @param example
	 * @return
	 */
	static PropertyName addPropertyNameIfNotExist(PropertyName example) {
		List<PropertyName> resultList = mapper.getPropertyNameByExample(example);
		if (resultList != null && resultList.size() > 0)
			return resultList.get(0);
		mapper.addPropertyName(example);
		return example;
	}

	/**
	 * 抽取基本信息
	 */
	static void extractBasicInfo() {
		// 抽取基本信息
		Elements basicInfoEles = currentDoc.getElementsByAttributeValue("class", "basic-info cmn-clearfix");
		if (!basicInfoEles.isEmpty()) {
			PropertyName basicInfo = new PropertyName();
			basicInfo.setProperty_name("基本信息");
			basicInfo = addPropertyNameIfNotExist(basicInfo);
			// mapper.addPropertyName(basicInfo);
			Elements dlEles = basicInfoEles.select("dl");
			for (Element element : dlEles) {
				Elements eleDt = element.select("dt");
				Elements eleDd = element.select("dd");
				PropertyName basicInfoEleName = new PropertyName();
				for (int i = 0; i < eleDt.size(); i++) {
					basicInfoEleName.setParent_id(basicInfo.getId()); // 添加父节点id
					String property_name = null;
					try {
						property_name = new String(eleDt.get(i).text().getBytes(), "UTF-8") // 除去空格
								.replaceAll("又    称", "又称").replaceAll("别    称", "别称");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					basicInfoEleName.setProperty_name(property_name);
					basicInfoEleName = addPropertyNameIfNotExist(basicInfoEleName);
					String propertyValue = eleDd.get(i).text();
					// 拆分属性值
					ArrayList<String> valueList = new ArrayList<String>();
					if (propertyValue.replaceAll(",", "，").contains("，")) {
						String[] values = propertyValue.split("，");
						for (int j = 0; j < values.length; j++)
							valueList.add(values[j]);
					} else if (propertyValue.contains("、")) {
						String[] values = propertyValue.split("、");
						for (int j = 0; j < values.length; j++)
							valueList.add(values[j]);
					} else if (propertyValue.replaceAll(";", "；").contains(";")) {
						String[] values = propertyValue.split("；");
						for (int j = 0; j < values.length; j++)
							valueList.add(values[j]);
					} else {
						valueList.add(propertyValue);
					}
					for (String string : valueList) {
						PropertyValue basicInfoEleValue = new PropertyValue();
						basicInfoEleValue.setValue(string);
						basicInfoEleValue.setDisease_id(currentDis.getId());
						basicInfoEleValue.setProperty_id(basicInfoEleName.getId());
						mapper.addPropertyValue(basicInfoEleValue);
					}
					session.commit();
				}
			}
		}
	}

	/**
	 * 抽取目录内容
	 */
	static void extractCatalog() {
		Elements cats = currentDoc.getElementsByAttributeValue("class", "lemma-catalog");
		if (cats.size() > 0) {
			PropertyName catalog = new PropertyName();
			catalog.setProperty_name("目录");
			catalog = addPropertyNameIfNotExist(catalog);
			Elements level1 = cats.first().getElementsByAttributeValue("class", "level1");
			Elements level2 = cats.first().getElementsByAttributeValue("class", "level2");
			List<PropertyName> catalogL1 = new ArrayList<PropertyName>();
			for (Element element : level1) {
				PropertyName catItem = new PropertyName();
				catItem.setParent_id(catalog.getId());
				catItem.setProperty_name(element.select("span").first().text());
				catItem = addPropertyNameIfNotExist(catItem);
				catalogL1.add(catItem);
				PropertyValue value = new PropertyValue();
				value.setDisease_id(currentDis.getId());
				value.setProperty_id(catItem.getId());
				value.setValue(element.select("a").text());
				mapper.addPropertyValue(value);
			}
			for (Element element : level2) {
				PropertyName catItem = new PropertyName();
				// 寻找父目录位置
				String posStr = element.select("a").first().attr("href");
				if (posStr.contains("_"))
					posStr = posStr.split("_")[0].substring(1);
				int i = Integer.valueOf(posStr);
				catItem.setParent_id(catalogL1.get(i - 1).getId());
				catItem.setProperty_name(element.select("span").first().text());
				catItem = addPropertyNameIfNotExist(catItem);
				PropertyValue value = new PropertyValue();
				value.setDisease_id(currentDis.getId());
				value.setProperty_id(catItem.getId());
				value.setValue(element.select("a").text());
				mapper.addPropertyValue(value);
			}
		}
		session.commit();
	}

	/**
	 * 主要内容抽取
	 */
	static void contentExtract() {
		PropertyName currentParentNode = new PropertyName();
		Elements mainContent = currentDoc.getElementsByAttributeValue("class", "main-content");
		if (mainContent.size() > 0) {
			Elements anchorList = mainContent.first().getElementsByAttributeValue("class", "anchor-list");
			for (Element anchorItem : anchorList) { // 提取属性名
				anchorItem = anchorItem.nextElementSibling();
				anchorItem.select("span").remove();
				String itemName = anchorItem.text();
				PropertyName titleLevel1 = new PropertyName();
				titleLevel1.setProperty_name(itemName);

				titleLevel1 = addPropertyNameIfNotExist(titleLevel1);	//一级标题存入数据库
				currentParentNode = titleLevel1;
				while (!anchorItem.attr("class").equals("anchor-list")) {
					Stack<Integer> numStack = new Stack<Integer>();	//记录当前文本序号类型栈
					anchorItem = anchorItem.nextElementSibling();
					String currentContent = "";
					if (anchorItem.attr("id").equals("open-tag") || anchorItem.attr("class").equals("anchor-list")
							|| anchorItem.is("dl"))
						break; // 遇上下一个小节、最后一个小节末尾的时候结束
					if (anchorItem.is("div")) {
						String text = anchorItem.text();
						if (Ordinal.isStartOrdinal(text) == 0)
							currentContent += text + "\n";
						else {
							
						}
					}
				}

			}
		}

	}

	/**
	 * 判断当前元素是否标题
	 * 
	 * @return
	 */
	boolean isTitle(Element el) {
		if (el == null)
			return false;
		String text = el.text();
		if (el.attr("class").equals("para-title level-3")) // 三级标题
			return true;
		if (el.select("b").size() > 0) {
			if (el.select("b").text().equals(text) && text.length() <= 35)
				return true; // 仅包含<b>标签并且文本长度小于35位标题
			return false;
		}

		return false;
	}
}
