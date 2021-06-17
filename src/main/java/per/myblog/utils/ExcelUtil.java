package per.myblog.utils;

import com.winsafe.framework.util.log.WfLogger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ExcelUtil {
	/*
	 * 
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file 读取数据的源Excel
	 * 
	 * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * 
	 * @return 读出的Excel中数据的内容
	 * 
	 * @throws FileNotFoundException
	 * 
	 * @throws IOException
	 */
	public static String[][] getData(File file, int ignoreRows)
			throws FileNotFoundException, IOException {
		return getData(new FileInputStream(file), ignoreRows);
	}
	
	public static String[][] getData(InputStream input, int ignoreRows)
			throws FileNotFoundException, IOException {
		List<String[]> result = new ArrayList<String[]>();
		DataFormatter formatter = new DataFormatter();
		int rowSize = 0;
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(input);
			// 打开HSSFWorkbook
			POIFSFileSystem fs = new POIFSFileSystem(in);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFCell cell = null;
			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
				HSSFSheet st = wb.getSheetAt(sheetIndex);
				// 第一行为标题，不取
				for (int rowIndex = 0; rowIndex <= st.getLastRowNum(); rowIndex++) {
					HSSFRow row = st.getRow(rowIndex);
					if (row == null) {
						continue;
					}
					int tempRowSize = row.getLastCellNum() + 1;
					if (tempRowSize > rowSize) {
						rowSize = tempRowSize;
					}
					
					if(rowIndex < ignoreRows){
						continue;
					}
					
					String[] values = new String[rowSize];
					Arrays.fill(values, "");
					boolean hasValue = false;
					for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
						String value = "";
						cell = row.getCell(columnIndex);
						if (cell != null) {
							// 注意：一定要设成这个，否则可能会出现乱码
							// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
							switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_STRING:
								value = cell.getStringCellValue();
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:
								if (HSSFDateUtil.isCellDateFormatted(cell)) {
									Date date = cell.getDateCellValue();
									if (date != null) {
										value = new SimpleDateFormat("yyyy-MM-dd")
										.format(date);
									} else {
										value = "";
									}
								} else {
									value = new DecimalFormat("0").format(cell
									.getNumericCellValue());
								}
								break;
							case HSSFCell.CELL_TYPE_FORMULA:
								value = formatter.formatCellValue(cell); 
								// 导入时如果为公式生成的数据则无值
								/*if (!cell.getStringCellValue().equals("")) {
									value = cell.getStringCellValue();
								} else {
									value = cell.getNumericCellValue() + "";
								}*/
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								break;
							case HSSFCell.CELL_TYPE_ERROR:
								value = "";
								break;
							case HSSFCell.CELL_TYPE_BOOLEAN:
								value = (cell.getBooleanCellValue() == true ? "Y"
								: "N");
								break;
							default:
								value = "";
							}
						}
						if (/*columnIndex == 0 &&*/ value.trim().equals("")) {
							values[columnIndex] = "";
						}else{						
							values[columnIndex] = rightTrim(value);
						}
						hasValue = true;
					}
					if (hasValue) {
						result.add(values);
					}
				}
			}
			String[][] returnArray = new String[result.size()][rowSize];
			for (int i = 0; i < returnArray.length; i++) {
				returnArray[i] = (String[]) result.get(i);
			}
			return returnArray;
		} catch (Exception e) {
			WfLogger.error("", e);
			throw e;
		} finally {
			if(in != null) {
				in.close();
			}
		}
		
	}
	/*
	 * 处理文件
	 */
	public static void SaveFileFromInputStream(InputStream stream,String path,String savefile) throws IOException
    {      
        FileOutputStream fs=new FileOutputStream( path + "/"+ savefile);
    //  System.out.println("------------"+path + "/"+ savefile);
        byte[] buffer =new byte[1024*1024];
        int bytesum = 0;
        int byteread = 0; 
        while ((byteread=stream.read(buffer))!=-1)
        {
           bytesum+=byteread;
           fs.write(buffer,0,byteread);
           fs.flush();
        } 
        fs.close();
        stream.close();      
    }
	/**
	 * 
	 * 去掉字符串右边的空格
	 * 
	 * @param str
	 *            要处理的字符串
	 * 
	 * @return 处理后的字符串
	 */
	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}
	
	public static void writeTxt(List<String> wrongtxt,String path) {
		BufferedWriter out = null;
		String destFile = path;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(destFile, true)));
			for(String str:wrongtxt){
				out.write(str);
				out.write("\r\n");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
