package com.lbc.util;

import java.util.HashSet;
import java.util.Set;

import javax.security.auth.Subject;

import org.junit.Test;

/**
 * 序号工具类
 * 
 * @author Liubaichuan
 * @since 2017-09-01
 *
 */
public class Ordinal {
	/** 阿拉伯数字序号 */
	static Set<String> arabicNum;
	/** 阿拉伯数字带括号序号 */
	static Set<String> bracketsArabicNum;
	/** 阿拉伯数字带圆圈序号 */
	static Set<String> circleArabicNum;
	/** 小写字母序号 */
	static Set<String> lowercaseLetters;
	/** 大写字母序号 */
	static Set<String> uppercaseLetter;
	/** 小写字母带括号序号 */
	static Set<String> bracketsLowercaseLetters;
	/** 大写字母带括号序号 */
	static Set<String> bracketsUppercaseLetter;
	/** 中文数字序号 */
	static Set<String> chineseNum;
	/** 中文数字带括号序号 */
	static Set<String> bracketsChineseNum;
	/** 半括号数字序号 */
	static Set<String> halfbracketsNum;

	static {
		arabicNum = new HashSet<String>();
		arabicNum.add("1");
		arabicNum.add("2");
		arabicNum.add("3");
		arabicNum.add("4");
		arabicNum.add("5");
		arabicNum.add("6");
		arabicNum.add("7");
		arabicNum.add("8");
		arabicNum.add("9");
		arabicNum.add("10");
		arabicNum.add("11");
		arabicNum.add("12");
		arabicNum.add("13");
		arabicNum.add("14");
		arabicNum.add("15");

		bracketsArabicNum = new HashSet<String>();
		bracketsArabicNum.add("（1）");
		bracketsArabicNum.add("（2）");
		bracketsArabicNum.add("（3）");
		bracketsArabicNum.add("（4）");
		bracketsArabicNum.add("（5）");
		bracketsArabicNum.add("（6）");
		bracketsArabicNum.add("（7）");
		bracketsArabicNum.add("（8）");
		bracketsArabicNum.add("（9）");
		bracketsArabicNum.add("（10）");
		bracketsArabicNum.add("（11）");
		bracketsArabicNum.add("（12）");
		bracketsArabicNum.add("（13）");
		bracketsArabicNum.add("（14）");
		bracketsArabicNum.add("（15）");

		circleArabicNum = new HashSet<String>();
		circleArabicNum.add("①");
		circleArabicNum.add("②");
		circleArabicNum.add("③");
		circleArabicNum.add("④");
		circleArabicNum.add("⑤");
		circleArabicNum.add("⑥");
		circleArabicNum.add("⑦");
		circleArabicNum.add("⑧");
		circleArabicNum.add("⑨");
		circleArabicNum.add("⑩");
		circleArabicNum.add("⑪");
		circleArabicNum.add("⑫");
		circleArabicNum.add("⑬");
		circleArabicNum.add("⑭");
		circleArabicNum.add("⑮");

		lowercaseLetters = new HashSet<String>();
		lowercaseLetters.add("a");
		lowercaseLetters.add("b");
		lowercaseLetters.add("c");
		lowercaseLetters.add("d");
		lowercaseLetters.add("e");
		lowercaseLetters.add("f");
		lowercaseLetters.add("g");
		lowercaseLetters.add("h");
		lowercaseLetters.add("i");
		lowercaseLetters.add("j");
		lowercaseLetters.add("k");
		lowercaseLetters.add("l");
		lowercaseLetters.add("m");
		lowercaseLetters.add("n");
		lowercaseLetters.add("o");

		uppercaseLetter = new HashSet<String>();
		uppercaseLetter.add("A");
		uppercaseLetter.add("B");
		uppercaseLetter.add("C");
		uppercaseLetter.add("D");
		uppercaseLetter.add("E");
		uppercaseLetter.add("F");
		uppercaseLetter.add("G");
		uppercaseLetter.add("H");
		uppercaseLetter.add("I");
		uppercaseLetter.add("J");
		uppercaseLetter.add("K");
		uppercaseLetter.add("L");
		uppercaseLetter.add("M");
		uppercaseLetter.add("N");
		uppercaseLetter.add("O");

		bracketsLowercaseLetters = new HashSet<String>();
		bracketsLowercaseLetters.add("（a）");
		bracketsLowercaseLetters.add("（b）");
		bracketsLowercaseLetters.add("（c）");
		bracketsLowercaseLetters.add("（d）");
		bracketsLowercaseLetters.add("（e）");
		bracketsLowercaseLetters.add("（f）");
		bracketsLowercaseLetters.add("（g）");
		bracketsLowercaseLetters.add("（h）");
		bracketsLowercaseLetters.add("（i）");
		bracketsLowercaseLetters.add("（j）");
		bracketsLowercaseLetters.add("（k）");
		bracketsLowercaseLetters.add("（l）");
		bracketsLowercaseLetters.add("（m）");
		bracketsLowercaseLetters.add("（n）");
		bracketsLowercaseLetters.add("（o）");

		bracketsUppercaseLetter = new HashSet<String>();
		bracketsUppercaseLetter.add("（A）");
		bracketsUppercaseLetter.add("（B）");
		bracketsUppercaseLetter.add("（C）");
		bracketsUppercaseLetter.add("（D）");
		bracketsUppercaseLetter.add("（E）");
		bracketsUppercaseLetter.add("（F）");
		bracketsUppercaseLetter.add("（G）");
		bracketsUppercaseLetter.add("（H）");
		bracketsUppercaseLetter.add("（I）");
		bracketsUppercaseLetter.add("（J）");
		bracketsUppercaseLetter.add("（K）");
		bracketsUppercaseLetter.add("（L）");
		bracketsUppercaseLetter.add("（M）");
		bracketsUppercaseLetter.add("（N）");
		bracketsUppercaseLetter.add("（O）");

		chineseNum = new HashSet<String>();
		chineseNum.add("一");
		chineseNum.add("二");
		chineseNum.add("三");
		chineseNum.add("四");
		chineseNum.add("五");
		chineseNum.add("六");
		chineseNum.add("七");
		chineseNum.add("八");
		chineseNum.add("九");
		chineseNum.add("十");
		chineseNum.add("十一");
		chineseNum.add("十二");
		chineseNum.add("十三");
		chineseNum.add("十四");
		chineseNum.add("十五");

		bracketsChineseNum = new HashSet<String>();
		bracketsChineseNum.add("（一）");
		bracketsChineseNum.add("（二）");
		bracketsChineseNum.add("（三）");
		bracketsChineseNum.add("（四）");
		bracketsChineseNum.add("（五）");
		bracketsChineseNum.add("（六）");
		bracketsChineseNum.add("（七）");
		bracketsChineseNum.add("（八）");
		bracketsChineseNum.add("（九）");
		bracketsChineseNum.add("（十）");
		bracketsChineseNum.add("（十一）");
		bracketsChineseNum.add("（十二）");
		bracketsChineseNum.add("（十三）");
		bracketsChineseNum.add("（十四）");
		bracketsChineseNum.add("（十五）");

		halfbracketsNum = new HashSet<String>();
		halfbracketsNum.add("1）");
		halfbracketsNum.add("2）");
		halfbracketsNum.add("3）");
		halfbracketsNum.add("4）");
		halfbracketsNum.add("5）");
		halfbracketsNum.add("6）");
		halfbracketsNum.add("7）");
		halfbracketsNum.add("8）");
		halfbracketsNum.add("9）");
		halfbracketsNum.add("10）");
		halfbracketsNum.add("11）");
		halfbracketsNum.add("12）");
		halfbracketsNum.add("13）");
		halfbracketsNum.add("14）");
		halfbracketsNum.add("15）");
	}

	/**
	 * 判断字符串str是不是序号
	 * 
	 * @param str
	 * @return 为阿拉伯数字序号返回1，为带括号阿拉伯数字序号返回2 为带圆圈阿拉伯数字序号返回3，为小写字母序号返回4，
	 *         为大写字母序号返回5，为带括号小写字母序号返回6， 为带括号大写字母序号返回7，为中文数字序号返回8，
	 *         为带括号中文数字序号返回9，为半括号数字序号返回10，其他返回0
	 */
	static public int isOrdinal(String str) {
		if (arabicNum.contains(str))
			return 1;
		else if (bracketsArabicNum.contains(str))
			return 2;
		else if (circleArabicNum.contains(str))
			return 3;
		else if (lowercaseLetters.contains(str))
			return 4;
		else if (uppercaseLetter.contains(str))
			return 5;
		else if (bracketsLowercaseLetters.contains(str))
			return 6;
		else if (bracketsUppercaseLetter.contains(str))
			return 7;
		else if (chineseNum.contains(str))
			return 8;
		else if (bracketsChineseNum.contains(str))
			return 9;
		else if (halfbracketsNum.contains(str))
			return 10;
		else
			return 0;

	}

	/**
	 * 判断字符串是否以序号开头
	 * 
	 * @param str
	 * @return 开头为阿拉伯数字序号返回1，为带括号阿拉伯数字序号返回2 为带圆圈阿拉伯数字序号返回3，为小写字母序号返回4，
	 *         为大写字母序号返回5，为带括号小写字母序号返回6， 为带括号大写字母序号返回7，为中文数字序号返回8，
	 *         为带括号中文数字序号返回9，为半括号数字序号返回10，其他返回0
	 */
	static public int isStartOrdinal(String str) {
		for (String string : arabicNum) {
			if (str.startsWith(string))
				return 1;
		}
		for (String string : bracketsArabicNum) {
			if (str.startsWith(string))
				return 2;
		}
		for (String string : circleArabicNum) {
			if (str.startsWith(string))
				return 3;
		}
		for (String string : lowercaseLetters) {
			if (str.startsWith(string))
				return 4;
		}
		for (String string : uppercaseLetter) {
			if (str.startsWith(string))
				return 5;
		}
		for (String string : bracketsLowercaseLetters) {
			if (str.startsWith(string))
				return 6;
		}
		for (String string : bracketsUppercaseLetter) {
			if (str.startsWith(string))
				return 7;
		}
		for (String string : chineseNum) {
			if (str.startsWith(string))
				return 8;
		}
		for (String string : bracketsChineseNum) {
			if (str.startsWith(string))
				return 9;
		}
		for (String string : halfbracketsNum) {
			if (str.startsWith(string))
				return 10;
		}
		return 0;
	}

	/**
	 * 除去字符串开头的序号
	 * @param inputStr
	 * @return
	 */
	static public String removeOrdinal(String inputStr) {
		if(isStartOrdinal(inputStr) == 0)
			return inputStr;
		Set<String> ordinalSet = new HashSet<String>(arabicNum);
		ordinalSet.addAll(bracketsArabicNum);
		ordinalSet.addAll(bracketsChineseNum);
		ordinalSet.addAll(bracketsLowercaseLetters);
		ordinalSet.addAll(bracketsUppercaseLetter);
		ordinalSet.addAll(chineseNum);
		ordinalSet.addAll(circleArabicNum);
		ordinalSet.addAll(halfbracketsNum);
		ordinalSet.addAll(lowercaseLetters);
		ordinalSet.addAll(uppercaseLetter);
		for (String string : ordinalSet) {
			if(inputStr.startsWith(string)) {
				int ordinalLength = string.length();
				inputStr = inputStr.substring(ordinalLength);
				break;
			}
		}
		inputStr = inputStr.trim();
		if(inputStr.startsWith(".") || inputStr.startsWith("、"))
			inputStr = inputStr.substring(1);
		return inputStr.trim();
	}

	@Test
	public void testRemoveOrdinal() {
		String testStr = new String("（13）神经系统症状");
		System.out.println(removeOrdinal(testStr));
	}
}
