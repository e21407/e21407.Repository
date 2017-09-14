package com.lbc.test;

import java.io.File;
import java.util.Random;

import org.junit.Test;
import org.ujmp.core.Matrix;
import org.ujmp.core.io.ImportMatrixMAT;

public class TestClass {

	@Test
	public void randomNum() {
		Random random = new Random();
		for (int i = 0; i < 10; i++)
			System.out.println(random.nextInt(10) + 1);
	}

	@Test
	public void countDensity() {
		int K = 26;
		File file = new File("resources/data/Rs.mat");
		Matrix[] T = new Matrix[K];
		for (int i = 1; i <= K; i++) {
			String matrixName = "rs" + i;
			T[i - 1] = ImportMatrixMAT.fromFile(file, matrixName);
		}

		int nonZeroNum = 0;
		for (Matrix matrix : T) {
			for (int i = 0; i < matrix.getRowCount(); i++)
				for (int j = 0; j < matrix.getColumnCount(); j++)
					if (matrix.getAsLong(i, j) != 0)
						nonZeroNum++;
		}
		System.out.println(nonZeroNum);
		//0.038369
	}

}
