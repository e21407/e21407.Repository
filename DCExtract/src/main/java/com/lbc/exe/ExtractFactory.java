package com.lbc.exe;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lbc.entity.Content;
import com.lbc.entity.Disease;
import com.lbc.entity.Title;
import com.lbc.mapper.DCEMapper;
import com.lbc.util.Ordinal;

public class ExtractFactory {
	Reader reader;
	SqlSessionFactory sqlSessionFactory;
	SqlSession session;
	DCEMapper mapper;

	Document currentDoc = null;
	Disease currentDis;

	public ExtractFactory() {
		try {
			reader = Resources.getResourceAsReader("mybatis-conf.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sqlSessionFactory.openSession();
		mapper = session.getMapper(DCEMapper.class);
	}

	public static void main(String[] args) {
		ExtractFactory ef = new ExtractFactory();
		ef.doExtract();
	}

	public void doExtract() {
		final String filePath = "resources/data/new癌症肿瘤词条 ";
		File filefolder = new File(filePath);
		File[] files = filefolder.listFiles();

		for (File file : files) {
			try {
				currentDoc = Jsoup.parse(file, "UTF-8", ""); // 读取文档，加载jsoup模型
			} catch (IOException e) {
				e.printStackTrace();
			}
			currentDis = new Disease();
			String diseaseName = currentDoc.select("h1").text();
			System.out.println("-----------------------" + diseaseName + "-----------------------");
			currentDis.setName(diseaseName);
			Elements sumEles = currentDoc.getElementsByAttributeValue("class", "lemma-summary");
			Elements ele = sumEles.attr("class", "para");
			String summary = ele.first().text();
			currentDis.setSummary(summary);
			mapper.addDisease(currentDis);

			this.extractBasicInfo();
			this.extractMainDiv();
			session.commit();

		}
		System.out.println("done!");
	}

	/**
	 * 抽取基本信息
	 */
	void extractBasicInfo() {
		Elements basicInfoEles = currentDoc.getElementsByAttributeValue("class", "basic-info cmn-clearfix");
		if (basicInfoEles.isEmpty())
			return;
		Elements dlEles = basicInfoEles.select("dl");
		for (Element element : dlEles) {
			Elements eleDt = element.select("dt");
			Elements eleDd = element.select("dd");
			
			for (int i = 0; i < eleDt.size(); i++) {
				Title title = new Title();
				String title_name = null;
				try {
					title_name = new String(eleDt.get(i).text().getBytes(), "UTF-8") // 除去空格
							.replaceAll("又    称", "又称").replaceAll("别    称", "别称");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				title.setTitle_name(title_name);
				System.out.println(">>>" + title_name);
				title = addTitleIfNotExist(title);
				String content = eleDd.get(i).text();
				Content con = new Content();
				con.setTitle_id(title.getId());
				con.setDisease_id(currentDis.getId());
				con.setValue(content);
				con.setStatus(1);
				mapper.addContent(con);
			}
		}
	}

	/**
	 * 抽取主要内容
	 */
	void extractMainDiv() {
		Elements mainContent = currentDoc.getElementsByAttributeValue("class", "main-content");
		if (mainContent.isEmpty())
			return;
		Elements anchorList = mainContent.first().getElementsByAttributeValue("class", "anchor-list");
		for (Element anchorItem : anchorList) {
			anchorItem = anchorItem.nextElementSibling();
			anchorItem.select("span").remove();
			String itemName = anchorItem.text();
			Title title = new Title();
			System.out.println(">>>" + itemName);
			title.setTitle_name(itemName);
			title = addTitleIfNotExist(title);
			while (true) {
				anchorItem = anchorItem.nextElementSibling();
				if (anchorItem.attr("class").equals("anchor-list"))
					break;
				if (anchorItem.attr("id").equals("open-tag") || anchorItem.attr("class").equals("anchor-list")
						|| anchorItem.is("dl"))
					break; // 遇上下一个小节、最后一个小节末尾的时候结束
				if (!anchorItem.is("div"))
					continue;
				String currText = "";
				if (!anchorItem.select("b").isEmpty()) {
					Elements bs = anchorItem.select("b");
					for (int i = 0; i < bs.size(); i++) {
						if (i != 0)
							currText += " | ";
						currText += bs.get(i).text();
					}
				} else if (Ordinal.isStartOrdinal(anchorItem.text()) != 0) {
					currText += anchorItem.text();
				} else if (anchorItem.text().length() <= 50) {
					currText += anchorItem.text();
				}
				if (currText.trim().length() <= 0)
					continue;
				Content contnent = new Content();
				contnent.setTitle_id(title.getId());
				contnent.setDisease_id(currentDis.getId());
				contnent.setValue(currText);
				contnent.setStatus(1);
				mapper.addContent(contnent);
			}

		}

	}

	Title addTitleIfNotExist(Title example) {
		List<Title> resultList = mapper.getTitleByName(example.getTitle_name());
		if (resultList != null && resultList.size() > 0)
			return resultList.get(0);
		mapper.addTitle(example);
		return example;
	}

}
