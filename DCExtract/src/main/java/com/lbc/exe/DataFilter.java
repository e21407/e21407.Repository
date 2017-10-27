package com.lbc.exe;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lbc.entity.Content;
import com.lbc.entity.Disease;
import com.lbc.entity.Title;
import com.lbc.mapper.IDECEMapper;
import com.lbc.util.DBConnector;

public class DataFilter {
	static DBConnector connector;
	static IDECEMapper mapper;

	public static void main(String[] args) {
		connector = DBConnector.getDBConnectorInstance();
		mapper = connector.getMapper(IDECEMapper.class);

		List<Disease> disList = mapper.getDiseaseByExample(new Disease());
		List<Title> titList = mapper.getTitleByExample(new Title());

		Scanner scanner = new Scanner(System.in);

		for (Disease disease : disList) {
			List<Integer> title_ids = mapper.getTitIdsOfContentByDisId(disease.getId());
			for (Integer tit_id : title_ids) {
				Content conExample = new Content();
				conExample.setDisease_id(disease.getId());
				conExample.setTitle_id(tit_id);
				conExample.setStatus(1);
				List<Content> conList = mapper.getContentByExample(conExample);
				if (conList.size() < 1)
					continue;
				Title title = null;
				for (Title tit : titList) {
					if (tit.getId().intValue() == tit_id.intValue()) {
						title = tit;
						break;
					}
				}
				System.out.println(
						"@" + disease.getName() + "---->" + title.getTitle_name() + " # " + title.getRemark1());
				for (Content content : conList) {
					System.out.println("[c_id:" + content.getId() + "]   " + content.getValue());
				}
				System.out.println("******************************");
				String input;
				do {
					System.out.print("【1】添加title标签  【2】停用content 【0】继续：");
					input = scanner.nextLine();
					if (input.equals("1"))
						addLabel(title);
					else if (input.equals("2"))
						disabledContent(conList);
					else if (input.equals("0")) {
						markRead(conList);
						break;
					}
				} while (true);
			}
		}
		System.out.println("done!");
	}

	public static void addLabel(Title title) {
		do {
			System.out.print("【1】检查   【2】症状   【3】治疗  【0】exit：");
			String input = new Scanner(System.in).nextLine();
			if (input.equals("1")) {
				title.setRemark1("检查");
				break;
			} else if (input.equals("2")) {
				title.setRemark1("症状");
				break;
			} else if (input.equals("3")) {
				title.setRemark1("治疗");
				break;
			} else if (input.equals("0"))
				break;
		} while (true);
		mapper.updateTitleByExample(title);
		connector.commit();
	}

	public static void disabledContent(List<Content> conList) {
		String input;
		Pattern pattern = Pattern.compile("[0-9]*");
		boolean c_flag;
		do {
			c_flag = false;
			System.out.print("请输入c_id(以空格隔开)：");
			input = new Scanner(System.in).nextLine();
			String ids[] = input.split(" ");

			for (String id : ids) {
				Matcher isNum = pattern.matcher(id);
				if (!isNum.matches()) {
					System.out.println("请输入数字");
					c_flag = true;
					break;
				}
				for (Content con : conList) {
					if (con.getId() == Integer.valueOf(id)) {
						con.setStatus(0);
						break;
					}
				}
			}
		} while (c_flag);

	}

	public static void markRead(List<Content> conList) {
		for (Content con : conList) {
			if (con.getStatus() != 0)
				con.setStatus(9);
			mapper.updateContentByExample(con);
		}
		connector.commit();
	}

}
