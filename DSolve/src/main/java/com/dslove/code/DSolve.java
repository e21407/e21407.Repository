package com.dslove.code;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dslove.mapper.DiseasePropertyNameValueMapper;
import com.dslove.pojo.old.DiseaseName;
import com.dslove.pojo.old.DiseaseProperty;
import com.dslove.pojo.old.PropertyName;
import com.dslove.pojo.old.PropertyNameValue;
import com.dslove.pojo.old.PropertyValue;

public class DSolve {
	final static String filePath = "resources/data/癌症肿瘤词条";
	static int disNum = 0;
	static Map<String, Map<String, List<String>>> resultList = new HashMap<String, Map<String, List<String>>>();
	
	public static void main(String[] args) throws IOException {
		File filefolder = new File(filePath);
		File[] files = filefolder.listFiles();
		Document doc = null;

		for (File file : files) {
			try {
				doc = Jsoup.parse(file, "UTF-8", "");
			} catch (IOException e) {
				e.printStackTrace();
			}

			String diseaseName = doc.select("h1").text();
			// System.out.println(++disNum + " " + diseaseName);
			Map<String, List<String>> propertyList = null;
			Elements elements = doc.getElementsByAttributeValue("class", "basic-info cmn-clearfix");
			if (elements != null && elements.size() > 0) {
				Elements eleDL = elements.select("dl");
				propertyList = new HashMap<String, List<String>>();
				for (Element element : eleDL) {
					Elements eleDt = element.select("dt");
					Elements eleDd = element.select("dd");

					for (int i = 0; i < eleDt.size(); i++) {
						String property_name = new String(eleDt.get(i).text().getBytes(),"UTF-8").replaceAll("又    称", "又称").replaceAll("别    称", "别称");
						/*if(property_name.contains("别") || property_name.contains("　"))
							property_name = property_name.replaceAll(" ", "");*/
						String property_value = eleDd.get(i).text();
						ArrayList<String> valueList = new ArrayList<String>();

						if (property_value.replaceAll(",", "，").contains("，")) {
							String[] values = property_value.split("，");
							for (int j = 0; j < values.length; j++)
								valueList.add(values[j]);
						} else if (property_value.contains("、")) {
							String[] values = property_value.split("、");
							for (int j = 0; j < values.length; j++)
								valueList.add(values[j]);
						} else if (property_value.replaceAll(";", "；").contains(";")) {
							String[] values = property_value.split("；");
							for (int j = 0; j < values.length; j++)
								valueList.add(values[j]);
						} else {
							valueList.add(property_value);
						}

						propertyList.put(property_name, valueList);

						// System.out.println("属性名称：" + property_name + " 属性值：" + property_value);
					}
				}
				resultList.put(diseaseName, propertyList);
			}

		}
		tranverseMap(resultList);
		System.out.println();
		System.out.println(resultList.size());

	}

	static public void tranverseMap(Map<String, Map<String, List<String>>> map) throws IOException {
		Reader reader = Resources.getResourceAsReader("mybatis-conf.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader); 
		SqlSession session = sqlSessionFactory.openSession();
		DiseasePropertyNameValueMapper mapper = session.getMapper(DiseasePropertyNameValueMapper.class);
		
		for (Map.Entry<String, Map<String, List<String>>> entry : map.entrySet()) {
			String diseaseName = entry.getKey();
			System.out.println("疾病名称：" + diseaseName);
			
			DiseaseName dname = new DiseaseName();
			dname.setDiseaseName(diseaseName);
			mapper.addDiseaseName(dname);	//新增疾病
			
			Map<String, List<String>> properties = entry.getValue();
			for (Map.Entry<String, List<String>> property : properties.entrySet()) {
				String propertyName = property.getKey();
				ArrayList<String> values = (ArrayList<String>) property.getValue();
				System.out.print(propertyName + "：");
				for (String string : values) {
					List<PropertyName> pnames = mapper.getPropertyNameByName(propertyName);
					PropertyValue pvalue = new PropertyValue();
					PropertyNameValue pnamevalue = new PropertyNameValue();
					DiseaseProperty dproperty = new DiseaseProperty();
					
					if(pnames != null && pnames.size() > 0) {
						PropertyName pname = pnames.get(0);
						pvalue.setPropertyValue(string);
						pvalue.setDiseaseId(dname.getId());
						mapper.addPropertyValue(pvalue);
						pnamevalue.setNameId(pname.getId());
						pnamevalue.setValueId(pvalue.getId());
						mapper.addPropertyNameValue(pnamevalue);
						dproperty.setDiseaseId(dname.getId());
						dproperty.setPropertyId(pname.getId());
						mapper.addDiseaseProperty(dproperty);
					}else {
						PropertyName pname = new PropertyName();
						pname.setPropertyName(propertyName);
						mapper.addPropertyName(pname);
						pvalue.setPropertyValue(string);
						pvalue.setDiseaseId(dname.getId());
						mapper.addPropertyValue(pvalue);
						pnamevalue.setNameId(pname.getId());
						pnamevalue.setValueId(pvalue.getId());
						mapper.addPropertyNameValue(pnamevalue);
						dproperty.setDiseaseId(dname.getId());
						dproperty.setPropertyId(pname.getId());
						mapper.addDiseaseProperty(dproperty);
					}
					
					System.out.print(string + "\\");
				}
				System.out.println("");
			}
			System.out.println("");
		}
		session.commit();
	}

}
