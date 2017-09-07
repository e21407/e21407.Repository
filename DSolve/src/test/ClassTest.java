import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.print.attribute.HashAttributeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.dslove.pojo.PropertyName;

import util.Ordinal;

public class ClassTest {

	@Test
	public void testOrdinal() {
		String str1 = "（一）国际卫生组织（WHO）诊断MM标准（2001年）";
		String str2 = "（3）M-成分：血清IgG>3.5g/dL或IgA>2.0g/dL，尿本周蛋白>1g/24h。";
		System.out.println(Ordinal.isStartOrdinal(str1));
		System.out.println(Ordinal.isStartOrdinal(str2));
	}

	@Test
	public void testContent() {
		File file = new File("resources/data/癌症肿瘤词条/11.骨髓瘤.html");
		Document doc = null;
		try {
			doc = Jsoup.parse(file, "UTF-8", "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements elements = doc.getElementsByAttributeValue("class", "lemma-summary");
		Elements ele = elements.attr("class", "para");
		String str = ele.first().text();
		System.out.println(str);
	}

	@Test
	public void testContent2() {
		File file = new File("resources/data/癌症肿瘤词条/11.骨髓瘤.html");
		Document doc = null;
		try {
			doc = Jsoup.parse(file, "UTF-8", "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements elements = doc.getElementsByAttributeValue("class", "title-text");
		Element ele = elements.first();
		ele.select("span").remove();
		String str = elements.first().text();
		System.out.println(str);
	}

	@Test
	public void extractTable() {
		File file = new File("resources/data/癌症肿瘤词条/11.骨髓瘤.html");
		Document doc = null;

		PropertyName tempTabParent = new PropertyName();
		tempTabParent.setProperty_name("临时表格属性名");

		try {
			doc = Jsoup.parse(file, "UTF-8", "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements tableEles = doc.getElementsByAttributeValue("class", "main-content").select("table");
		for (Element tabEle : tableEles) {
			Element tabParent = tabEle.previousElementSibling();
			String parentName = tabParent.text().trim();
			PropertyName table = new PropertyName();
		}
	}

	@Test
	public void extractTitle() {
		final String filePath = "resources/data/new癌症肿瘤词条";
		File filefolder = new File(filePath);
		File[] files = filefolder.listFiles();
		Document doc = null;
		Set<String> contentList = new HashSet<String>();

		for (File file : files) {
			try {
				doc = Jsoup.parse(file, "UTF-8", ""); // 读取文档，加载jsoup模型
			} catch (IOException e) {
				e.printStackTrace();
			}

			Elements mainContent = doc.getElementsByAttributeValue("class", "main-content");
			// Elements divs = mainContent.select("div");
			Elements divs = mainContent.first().getElementsByAttributeValue("class", "para");

			for (Element element : divs) {

				String absContent = element.select("b").text();
				if (!"".equals(absContent.trim()) && absContent.length() <= 30)
					contentList.add(absContent);
				Elements childEle = element.children();

				for (Element chi : childEle) {
					String content = chi.text();
					if (!"".equals(content.trim()))
						if (Ordinal.isStartOrdinal(content) != 0 && content.length() <= 30)
							if (!contentList.contains(content))
								contentList.add(content);
				}

			}
		}

		List<String> resultList = new ArrayList<String>();
		for (String string : contentList) {
			if ((string.contains("、") || string.contains("和") || string.contains("与") || string.contains("或")
					|| string.contains("等") || string.contains("，"))) {
				resultList.add(string);
			}
		}

		// 写入文件
		String outputFilePath = "resources/outputFile/title2.txt";
		File outputFile = new File(outputFilePath);
		FileWriter writer = null;
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			writer = new FileWriter(outputFile, true);
			for (String string : resultList) {
				writer.write(string + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	@Test
	public void catalogExtract() {
		final String filePath = "resources/data/new癌症肿瘤词条";
		File filefolder = new File(filePath);
		File[] files = filefolder.listFiles();
		Document doc = null;
		Set<String> catalogList = new HashSet<String>();

		for (File file : files) {
			try {
				doc = Jsoup.parse(file, "UTF-8", ""); // 读取文档，加载jsoup模型
			} catch (IOException e) {
				e.printStackTrace();
			}
			Element catalogDiv = doc.getElementsByAttributeValue("class", "catalog-scroller").first();
			Elements catalogs = catalogDiv.getElementsByAttributeValue("nslog-type", "10002802");
			for (Element element : catalogs) {
				String catalogStr = element.text();
				if(catalogStr != null && !"".equals(catalogStr.trim()))
					catalogList.add(catalogStr);
			}
		}
		for (String string : catalogList) {
			System.out.println(string);
		}
	}
}
