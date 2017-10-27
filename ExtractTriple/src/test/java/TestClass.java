import java.util.List;

import org.junit.Test;

import com.lbc.mapper.ExtractMapper;
import com.lbc.util.DBConnector;

public class TestClass {

	@Test
	public void testStringTrim() {
		String str = new String("   12 3  ");
//		str = str.substring(5);
		System.out.println(str + ".");
		System.out.println(str.trim() + ".");
		str = str.trim();
		System.out.println(str + ".");
	}
	
	@Test
	public void testSQL() {
		DBConnector connector = DBConnector.getDBConnectorInstance();
		ExtractMapper mapper = connector.getMapper(ExtractMapper.class);
		List<String> catalogNameList = mapper.getCatalogNameByAffiliation("临床表现");
		for (String string : catalogNameList) {
			System.out.println(string);
		}
		System.out.println(catalogNameList.contains("临床表现"));
	}

}
