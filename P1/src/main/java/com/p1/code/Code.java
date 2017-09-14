package com.p1.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.p1.entity.RL;
import com.p1.entity.STY;
import com.p1.entity.Triple;
import com.p1.mapper.P1Mapper;

public class Code {
	Reader reader;
	SqlSessionFactory sqlSessionFactory;
	SqlSession session;
	P1Mapper mapper;

	public Code() throws IOException {
		reader = Resources.getResourceAsReader("mybatis-conf.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sqlSessionFactory.openSession();
		mapper = session.getMapper(P1Mapper.class);
	}

	public static void main(String[] args) throws IOException {
		Code code = new Code();
		// main.extractSRSTRE2();
		// main.extractSRSTR();
		code.createMatrix2();
	}

	// @Test
	public void extractSRDEF() throws IOException {
		final String filePath = "resources/2016AA/SRDEF";
		FileReader freader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(freader);

		String str = null;
		while ((str = br.readLine()) != null) {
			String[] strs = str.split("\\|");
			if (strs[0].equals("STY")) {
				STY sty = new STY();
				sty.setOld_id(strs[1]);
				sty.setName(strs[2]);
				sty.setRemark1(strs[3]);
				mapper.addSTY(sty);
			} else {
				RL rl = new RL();
				rl.setOld_id(strs[1]);
				rl.setName(strs[2]);
				rl.setRemark1(strs[3]);
				mapper.addRL(rl);
			}
		}
		session.commit();
		br.close();
		reader.close();
		System.out.println("done!");
	}

	// @Test
	public void extractSRSTRE2() throws IOException {
		int i = 0;
		final String filePath = "resources/2016AA/SRSTRE2";
		FileReader freader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(freader);

		List<STY> styList = mapper.getAllSTY();
		List<RL> rlList = mapper.getAllRL();

		String str = null;
		List<Triple> tripleList = new ArrayList<Triple>();
		while ((str = br.readLine()) != null) {
			String[] strs = str.split("\\|");
			Triple triple = new Triple();
			STY subject = null;
			STY object = null;
			for (STY sty : styList) {
				if (sty.getName().trim().equals(strs[0].trim()))
					subject = sty;
				if (sty.getName().trim().equals(strs[2].trim()))
					object = sty;
				if (subject != null && object != null)
					break;
			}
			RL relationship = null;
			for (RL rl : rlList) {
				if (rl.getName().trim().equals(strs[1].trim())) {
					relationship = rl;
					break;
				}
			}
			System.out.println(str);
			if (subject != null && object != null) {
				triple.setRelationship_id(relationship.getId());
				triple.setSubject_id(subject.getId());
				triple.setObject_id(object.getId());
				tripleList.add(triple);
				// mapper.addTriple(triple);
			}

		}
		System.out.println("finished extracting");
		for (Triple triple : tripleList) {
			System.out.println(++i);
			mapper.addTriple(triple);
		}
		session.commit();
		br.close();
		reader.close();
		System.out.println("done!");
	}

	public void extractSRSTR() throws IOException {
		int i = 0;
		final String filePath = "resources/2016AA/SRSTR";
		FileReader freader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(freader);

		List<STY> styList = mapper.getAllSTY();
		List<RL> rlList = mapper.getAllRL();
		List<Triple> listResult = mapper.getAllTriple();

		String str = null;
		List<Triple> tripleList = new ArrayList<Triple>();
		while ((str = br.readLine()) != null) {
			String[] strs = str.split("\\|");
			Triple triple = new Triple();
			STY subject = null;
			STY object = null;
			for (STY sty : styList) {
				if (sty.getName().trim().equals(strs[0].trim()))
					subject = sty;
				if (sty.getName().trim().equals(strs[2].trim()))
					object = sty;
				if (subject != null && object != null)
					break;
			}
			RL relationship = null;
			for (RL rl : rlList) {
				if (rl.getName().trim().equals(strs[1].trim())) {
					relationship = rl;
					break;
				}
			}
			System.out.println(str);
			if (subject != null && object != null) {
				triple.setRelationship_id(relationship.getId());
				triple.setSubject_id(subject.getId());
				triple.setObject_id(object.getId());
				for (Triple tri : listResult) {
					if (triple.getSubject_id() == tri.getSubject_id() && triple.getObject_id() == tri.getObject_id()
							&& triple.getRelationship_id() == tri.getRelationship_id()) {
						triple = null;
						break;
					}
				}
				if (triple != null)
					tripleList.add(triple);
			}

		}
		System.out.println("finished extracting");
		for (Triple triple : tripleList) {
			System.out.println(++i);
			mapper.addTriple(triple);
		}
		session.commit();
		br.close();
		reader.close();
		System.out.println("done!");
	}

	public void createMatrix() throws IOException {
		// List<STY> styList = mapper.getAllSTY();
		// List<RL> rlList = mapper.getAllRL();
		List<Triple> tripleList = mapper.getAllTriple();
		int[][][] Rs = new int[54][127][127];

		for (Triple tr : tripleList)
			Rs[tr.getRelationship_id() - 1][tr.getSubject_id() - 1][tr.getObject_id() - 1] = 1;

		FileWriter writer = null;
		// 写入文件
		String outputFilePath = "resources/outputFile/matrixAll.txt";
		File outputFile = new File(outputFilePath);
		writer = new FileWriter(outputFile, true);

		int k = 0;
		for (int[][] is : Rs) {
			System.out.println("第" + ++k
					+ "片：----------------------------------------------------------------------------------------------------");
			System.out.println(
					"-------------------------------------------------------------------------------------------------------------");

			writer.write("a(:,:," + k + ")=[\n");
			for (int i = 0; i < is.length; i++) {
				for (int j = 0; j < is[i].length; j++) {
					System.out.print(is[i][j]);
					writer.write(is[i][j] + "");
					if (j != is[i].length - 1) {
						System.out.print(",");
						writer.write(",");
					}
				}
				if (i != is.length - 1) {
					System.out.println(";");
					writer.write(";\n");
				} else {
					System.out.println();
					writer.write("]\n");
					// writer.close();
				}
			}
		}
		writer.close();
	}

	public void createMatrix2() throws IOException {
		// List<STY> styList = mapper.getAllSTY();
		// List<RL> rlList = mapper.getAllRL();
		List<Triple> tripleList = mapper.getAllTriple();
		int[][][] Rs = new int[54][127][127];

		for (Triple tr : tripleList)
			Rs[tr.getRelationship_id() - 1][tr.getSubject_id() - 1][tr.getObject_id() - 1] = 1;

		// 统计矩阵稀疏度
		int nonZeroNum = 0;
		for (int[][] is : Rs) {
			for (int[] is2 : is) {
				for (int i : is2) {
					if (i != 0)
						nonZeroNum++;
				}
			}
		}
		double density = nonZeroNum / 127 / 127 / 54;
		System.out.println(nonZeroNum); // 6131
		System.out.println(density); // 0.0070393

		// 改变稀疏度
		int tu = 0;
		Random random = new Random();
		for (int[][] is : Rs) {
			for (int i = 0; i < is.length; i++) {
				for (int j = 0; j < is[i].length; j++) {
					if (is[i][j] == 0) {
						if (random.nextInt(1000) <= 31) {
							is[i][j] = 1;
							tu++;
						}
					}
				}
			}
		}
		System.out.println(tu);

		// 统计矩阵稀疏度
		nonZeroNum = 0;
		for (int[][] is : Rs) {
			for (int[] is2 : is) {
				for (int i : is2) {
					if (i != 0)
						nonZeroNum++;
				}
			}
		}
		System.out.println(nonZeroNum);

		FileWriter writer = null; // 写入文件
		String outputFilePath = "resources/outputFile/matrixManmake.txt";
		File outputFile = new File(outputFilePath);
		writer = new FileWriter(outputFile, true);

		int k = 0;
		for (int[][] is : Rs) {
			System.out.println("第" + ++k
					+ "片：----------------------------------------------------------------------------------------------------");
			System.out.println(
					"-------------------------------------------------------------------------------------------------------------");

			writer.write("a(:,:," + k + ")=[\n");
			for (int i = 0; i < is.length; i++) {
				for (int j = 0; j < is[i].length; j++) {
					System.out.print(is[i][j]);
					writer.write(is[i][j] + "");
					if (j != is[i].length - 1) {
						System.out.print(",");
						writer.write(",");
					}
				}
				if (i != is.length - 1) {
					System.out.println(";");
					writer.write(";\n");
				} else {
					System.out.println();
					writer.write("]\n");
				}
			}
		}
		writer.close();

	}

}
