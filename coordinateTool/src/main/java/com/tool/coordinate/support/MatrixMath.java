package com.tool.coordinate.support;

import com.tool.coordinate.entity.Enum.MatrixColumn;

import java.util.*;

/**
 * @author yk
 * @version 1.0
 * @description:
 * @date 2021/9/20 19:06
 */
public class MatrixMath {

    /**
     * @desc   计算[2x2]矩阵的行列式
     * @author yk
     * @date 2021/4/15 18:33
     * @param arr -- 要计算的矩阵
     * @return
     */
    public long determinant2D(Long[][] arr)
    {
        int row = arr.length;
        if (row > 0)
        {
            int column = arr[0].length;
            if (row != column)
            {
                throw new RuntimeException("这不是一个正方形矩阵！");
            }
            return (long)((arr[1][1] * arr[0][0]) - (arr[0][1] * arr[1][0]));
        }
        else
        {
            throw new RuntimeException("这不是一个正方形矩阵！");
        }
    }

    /**
     * @desc  计算[3x3]矩阵的行列式
     * @author yk
     * @date 2021年4月15日 18点31分
     * @param arr       --  要计算的矩阵
     * @param rowNo     --  起始行
     * @param columnNo  --  起始列
     * @param count     --  行列式的值
     * @return
     */
    public long determinant3D(Long[][] arr, int rowNo, int columnNo, long count)
    {
        int row = arr.length;
        if (row > 0)
        {
            int column = arr[0].length;
            if (row != column)
            {
                throw new RuntimeException("这不是一个正方形矩阵！");
            }
            List<List<Long>> covalentFormulaMatrix = this.getCovalentFormulaMatrix(arr, rowNo, columnNo);
            if (covalentFormulaMatrix.size() == 2)
            {
                if ((rowNo + columnNo) % 2 == 0)
                {
                    count = (count + this.determinant2D(this.formatList2Array(covalentFormulaMatrix)) * arr[rowNo][columnNo]);
                }
                else
                {
                    count = (count + this.determinant2D(this.formatList2Array(covalentFormulaMatrix))) * arr[rowNo][columnNo] * -1;
                }
                columnNo++;
            }
            if (columnNo >= column)
            {
                return count;
            }
            else
            {
                return determinant3D(arr, rowNo, columnNo, count);
            }
        }
        else
        {
            throw new RuntimeException("这不是一个正方形矩阵！");
        }
    }

    /**
     * @desc   计算[4x4]矩阵的行列式
     * @author yk
     * @date 2021/4/16 16:26
     * @param arr
     * @param rowNo
     * @param columnNo
     * @param count
     * @return
     */
    public long determinant4D(Long[][] arr, int rowNo, int columnNo, long count)
    {
        int row = arr.length;
        if (row > 0)
        {
            int column = arr[0].length;
            if (row != column)
            {
                throw new RuntimeException("这不是一个正方形矩阵！");
            }
            List<List<Long>> covalentFormulaMatrix = this.getCovalentFormulaMatrix(arr, rowNo, columnNo);
            if (covalentFormulaMatrix.size() == 3)
            {
                if ((rowNo + columnNo) % 2 == 0)
                {
                    count = ((count + this.determinant3D(this.formatList2Array(covalentFormulaMatrix), 0, 0, 0)) * arr[rowNo][columnNo]);
                }
                else
                {
                    count = ((count + this.determinant3D(this.formatList2Array(covalentFormulaMatrix), 0, 0, 0)) * arr[rowNo][columnNo] * -1);
                }
                columnNo++;
            }
            if (columnNo >= column)
            {
                return count;
            }
            else
            {
                return determinant4D(arr, rowNo, columnNo, count);
            }
        }
        else
        {
            throw new RuntimeException("这不是一个正方形矩阵！");
        }
    }

    /**
     * @desc   求矩阵的余子式
     * @author yk
     * @date 2021/4/15 18:34
     * @param arr       --  要计算的矩阵
     * @param rowNo     --  起始行
     * @param columnNo  --  起始列
     * @return
     */
    public List<List<Long>> getCovalentFormulaMatrix(Long[][] arr, int rowNo, int columnNo)
    {
        List<List<Long>> r = new ArrayList<List<Long>>();
        for (int iCyc = 0, len = arr.length; iCyc < len; iCyc++)
        {
            if (rowNo != iCyc)
            {
                List<Long> bigDecimals = new ArrayList<Long>();
                for (int lCyc = 0, mlen = arr[0].length; lCyc < mlen; lCyc++)
                {
                    if (lCyc != columnNo)
                    {
                        bigDecimals.add(arr[iCyc][lCyc]);
                    }
                }
                r.add(bigDecimals);
            }
        }
        return r;
    }

    /**
     * @desc   求一个[3x3]矩阵的代数余子式
     * @author yk
     * @date 2021/4/16 18:03
     * @param arr       --  要计算的矩阵
     * @param rowNo     --  起始行
     * @param columnNo  --  起始列
     * @return
     */
    public long getAlgebraCovalentFormula3DMatrix(Long[][] arr, int rowNo, int columnNo)
    {
        List<List<Long>> covalentFormulaMatrix = this.getCovalentFormulaMatrix(arr, rowNo, columnNo);
        Long[][] bigDecimals = this.formatList2Array(covalentFormulaMatrix);
        long bigDecimal = this.determinant2D(bigDecimals);
        if ((rowNo + columnNo) % 2 == 0)
        {
            return bigDecimal;
        }
        else
        {
            return bigDecimal * -1;
        }
    }

    /**
     * 集合转数组
     * @param list
     * @return
     */
    public Long[][] formatList2Array(List<List<Long>> list)
    {
        Long[][] bigr = new Long[list.size()][];

        for (int iCyc = 0, len = list.size(); iCyc < len; iCyc++)
        {
            List<Long> pColumnArr = list.get(iCyc);
            Long[] bigDecimals = pColumnArr.toArray(new Long[]{});
            bigr[iCyc] = bigDecimals;
        }
        return bigr;
    }

    /**
     * @desc   矩阵相乘（多列矩阵x单列矩阵）
     * @author yk
     * @date 2021/6/6 22:16
     * @param matrix1
     * @param matrix2
     * @return
     */
    public int[][] multipMatrixSingleColumn(Integer[][] matrix1, Integer[][] matrix2)
    {
        if (matrix1[0].length != matrix2[0].length)
        {
            throw new RuntimeException("相乘的矩阵行数不相等!!!");
        }
        int[][] temp = new int[matrix2.length][matrix2[0].length];
        for (int iCyc = 0, len = matrix1.length; iCyc < len; iCyc++)
        {
            Integer[] pRowArr = getRowOrColumn(matrix1, iCyc, MatrixColumn.ROW);
            int cTemp = 0;
            for (int jCyc = 0, nLen = pRowArr.length; jCyc < nLen; jCyc++)
            {
                int row = pRowArr[jCyc];
                Integer[] pColumnArr = getRowOrColumn(matrix2, jCyc, MatrixColumn.COLUMN);
                int column = pColumnArr[0];
                cTemp = cTemp + (row * column);
            }
            temp[0][iCyc] = cTemp;
        }
        return temp;
    }

    /**
     * @desc   矩阵相乘
     * @author yk
     * @date 2021/4/15 18:35
     * @param matrix1
     * @param matrix2
     * @return
     */
    public int[][] multipMatrixSpecsEqual(Integer[][] matrix1, Integer[][] matrix2)
    {
        if (matrix1.length != matrix2.length)
        {
            throw new RuntimeException("相乘的矩阵行列数需要一致!!!");
        }
        int[][] temp = new int[matrix1.length][];
        for (int iCyc = 0, len = matrix1.length; iCyc < len; iCyc++)
        {
            Integer[] pRowArr = getRowOrColumn(matrix1, iCyc, MatrixColumn.ROW);
            for (int lCyc = 0, mLen = matrix2[0].length; lCyc < mLen; lCyc++)
            {
                Integer[] pColumnArr = getRowOrColumn(matrix2, lCyc, MatrixColumn.COLUMN);
                int cTemp = 0;
                for (int jCyc = 0, nLen = pRowArr.length; jCyc < nLen; jCyc++)
                {
                    int row = pRowArr[jCyc];
                    int column = pColumnArr[jCyc];
                    cTemp = cTemp + (row * column);
                }
                temp[iCyc][lCyc] = cTemp;
            }
        }
        return temp;
    }

    /**
     * @desc   打印矩阵
     * @author yk
     * @date 2021/9/20 19:37
     * @param arr
     * @return
     */
    public void printMatrix(int[][] arr)
    {
        for (int[] pRow : arr)
        {
            for (int pColumn : pRow)
            {
                System.out.println(pColumn + "\t\t");
            }
            System.out.println("\n");
        }
    }

    /**
     * @desc   获取一个矩阵的指定行或者列
     * @author yk
     * @date 2021/4/15 18:35
     * @param arr   --  矩阵
     * @param num   --  行号或者列号
     * @param pDir  --  行或者列
     * @return
     */
    private Integer[] getRowOrColumn(Integer[][] arr, int num, MatrixColumn pDir)
    {
        if (pDir.equals(MatrixColumn.COLUMN))
        {
            List<Integer> temp = new ArrayList<Integer>();
            for (int iCyc = 0, len = arr.length; iCyc < len; iCyc++)
            {
                temp.add(arr[iCyc][num]);
            }

            return temp.toArray(new Integer[temp.size()]);
        }
        else if (pDir.equals(MatrixColumn.ROW))
        {
            return arr[num];
        }
        else
        {
            return new Integer[] { };
        }
    }
}
