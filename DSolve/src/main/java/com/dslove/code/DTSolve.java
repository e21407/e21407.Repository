package com.dslove.code;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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

/**
 * 
 * @author Liubaichuan
 * @since 2017-09-03
 *
 */
public class DTSolve {
	final static String filePath = "resources/data/testGroup";
	static ArrayList<Disease> diseaseList;
	static ArrayList<PropertyName> propertyNameList;
	static ArrayList<PropertyValue> propertyValueList;
	static SqlSession session;
	static DTSolveMapper mapper;

	public static void main(String[] args) {
		diseaseList = new ArrayList<Disease>();
		propertyNameList = new ArrayList<PropertyName>();
		propertyValueList = new ArrayList<PropertyValue>();
		File filefolder = new File(filePath);
		File[] files = filefolder.listFiles();
		Document doc = null;

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
				doc = Jsoup.parse(file, "UTF-8", ""); // 读取文档，加载jsoup模型
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 抽取疾病名字和概要
			Disease dis = new Disease();
			String diseaseName = doc.select("h1").text();
			dis.setName(diseaseName);
			Elements sumEles = doc.getElementsByAttributeValue("class", "lemma-summary");
			Elements ele = sumEles.attr("class", "para");
			String summary = ele.first().text();
			dis.setSummary(summary);
			mapper.addDisease(dis);
			diseaseList.add(dis);

			// 抽取基本信息
			Elements basicInfoEles = doc.getElementsByAttributeValue("class", "basic-info cmn-clearfix");
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
							basicInfoEleValue.setDisease_id(dis.getId());
							basicInfoEleValue.setProperty_id(basicInfoEleName.getId());
							mapper.addPropertyValue(basicInfoEleValue);
						}
						session.commit();
					}
				}
			}
			//抽取主要内容
			Elements mianContent = doc.getElementsByAttributeValue("class", "main-content");
			if(mianContent.size() > 0) {
				Elements  anchorList = mianContent.first().getElementsByAttributeValue("class", "anchor-list");
				for (Element anchorItem : anchorList) {
					//提取属性名
					anchorItem = anchorItem.nextElementSibling();
					anchorItem.select("span").remove();
					String itemName = anchorItem.text();
					PropertyName item = new PropertyName();
					item.setProperty_name(itemName);
					
					anchorItem = anchorItem.nextElementSibling();
					if(anchorItem.is("table")) {
						
					}
					
				}
			}
			
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

}
