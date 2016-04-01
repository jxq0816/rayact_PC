package com.bra.modules.reserve.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author
 * @since 2013-12-27 16:37:12
 * @version 1.0
 */
public class ExcelInfo {
	public static final String HSSFFONT_BLACK = "黑体";
	/**
	 * 单元格宽度
	 */
	private int columnWidth;

	/**
	 * 单元格高度
	 */
	private short columnHeight;

	/**
	 * excel的名称
	 */
	private String excelName;

	/**
	 * 页签名称
	 */
	private String sheetName;

	/**
	 * 抬头名称
	 */
	private String headName;

	/**
	 * 标题名称数组
	 */
	private String[] titles;

	/**
	 * 内容集合
	 */
	private List<String[]> contentList;

	/**
	 * 当前会话的response
	 */
	private HttpServletResponse response;

	/**
	 * @param columnWidth
	 *            the columnWidth to set
	 */
	public void setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
	}

	/**
	 * @param columnHeight
	 *            the columnHeight to set
	 */
	public void setColumnHeight(short columnHeight) {
		this.columnHeight = columnHeight;
	}

	/**
	 * @return the excelName
	 */
	public String getExcelName() {
		return excelName;
	}

	/**
	 * @param excelName
	 *            the excelName to set
	 */
	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	/**
	 * @return the sheetName
	 */
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * @param sheetName
	 *            the sheetName to set
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	/**
	 * @return the headName
	 */
	public String getHeadName() {
		return headName;
	}

	/**
	 * @param headName
	 *            the headName to set
	 */
	public void setHeadName(String headName) {
		this.headName = headName;
	}

	/**
	 * @param titles
	 *            the titles to set
	 */
	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	/**
	 * @param contentList
	 *            the contentList to set
	 */
	public void setContentList(List<String[]> contentList) {
		this.contentList = contentList;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * 对Excel操作的对象
	 */
	private HSSFWorkbook hssfWorkbook;

	/**
	 * 私有构造器，保证不能创建空对象
	 */
	private ExcelInfo() {
	}

	/**
	 * @param response
	 * @param excelName
	 * @param titles
	 * @param contentList
	 */
	public ExcelInfo(HttpServletResponse response, String excelName,
			String[] titles, List<String[]> contentList) throws Exception {
		super();
		this.response = response;
		this.excelName = excelName;
		this.titles = titles;
		this.contentList = contentList;
		validationDown();
		reset(excelName);
	}

	/**
	 * @param inputStream
	 *            excel 的文件输入流
	 */
	public ExcelInfo(InputStream inputStream) throws Exception {
		validationUp(inputStream);
	}

	/**
	 * 校验是否上传有效的流
	 * 
	 * @param inputStream
	 * 
	 * @throws Exception
	 */
	private void validationUp(InputStream inputStream) throws Exception {
		if (inputStream == null) {
			throw new Exception("参数不允许为空！");
		}
		POIFSFileSystem poiFileSystem = null;
		try {
			poiFileSystem = new POIFSFileSystem(inputStream);
		} catch (Exception e) {
			throw new Exception(e + "参数不允许为空！");
		} finally {
			inputStream.close();
		}

		this.hssfWorkbook = new HSSFWorkbook(poiFileSystem);
	}

	/**
	 * 验证抬头是否是对应的excel模板
	 * 
	 * @param headName
	 *            抬头的名称
	 * @return
	 * @throws Exception
	 */
	public boolean checkHeadName(String headName) throws Exception {
		if (headName == null) {
			throw new Exception("参数不允许为空！");
		}
		HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
		return headName.equals(constrainHSSF(sheet.getRow(0).getCell(0))
				.toString());
	}

	/**
	 * 读一行信息放到数组中
	 * 
	 * @param row
	 * @return
	 */
	private String[] readRow(HSSFRow row) throws Exception {
		return this.readRow(row, 0);
	}

	/**
	 * 读一行信息放到特定长度的数组中
	 * 
	 * @param row
	 * @return
	 */
	private String[] readRow(HSSFRow row, int len) throws Exception {
		if (len < 0) {
			throw new Exception("len 的长度不能为负数。");
		} else if (len == 0) {
			len = row.getLastCellNum();
			if (len == 0)
				throw new Exception("标题行为空，请检查上传的excel模板是否正确！");
		}
		String cloumns[] = new String[len];
		for (int i = 0; i < len; i++) {
			cloumns[i] = constrainHSSF(row.getCell(i));
		}
		return cloumns;
	}

	/**
	 * 获取excel中的信息集合
	 * 
	 * @return the contentList
	 */
	public List<String[]> getContentList() throws Exception {
		if (hssfWorkbook != null) {
			HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
			int len = sheet.getLastRowNum();
			// 读取标题数组的长度
			int titleLen = getTitles().length;
			HSSFRow row;
			String cloumnsProduct[];
			this.contentList = new ArrayList<String[]>();
			for (int i = 2; i <= len; i++) {
				row = sheet.getRow(i);
				if (isValid(row, 5)) {
					cloumnsProduct = readRow(sheet.getRow(i), titleLen);
					contentList.add(cloumnsProduct);
				}
			}
		}
		return contentList;
	}

	/**
	 * 获取excel中的标题数组
	 * 
	 * @return the titles
	 */
	public String[] getTitles() throws Exception {
		if (hssfWorkbook != null) {
			HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
			this.titles = readRow(sheet.getRow(1));
		}
		return titles;
	}

	/**
	 * 初始化
	 *
	 */
	private void reset(String excelName) {
		this.columnWidth = 4000;
		this.columnHeight = (short) 400;
		this.sheetName = excelName;
		this.headName = excelName;
	}

	/**
	 * 验证是否创建有效对象
	 */
	private void validationDown() throws Exception {
		if (this.response == null || this.excelName == null
				|| "".equals(this.excelName) || this.titles == null
				|| this.contentList == null) {
			throw new Exception("参数不允许为空！");
		}
		if (titles.length < 1) {
			throw new Exception("标题内容不允许为空！");
		}
		// if (contentList.isEmpty()) {
		// throw new Exception("导出内容不允许为空！");
		// }
	}

	public void export() throws Exception {
		OutputStream out = null;
		try {
			// 获得标题数组
			String[] cloumns = titles;
			// 清空输出流
			response.reset();
			// 设置导出文件名称
			String filename = new String(excelName.getBytes("GBK"),
					"ISO-8859-1") + ".xls";
			response.setContentType("application/vnd.ms-excel; charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filename);
			HSSFWorkbook workBook = new HSSFWorkbook();
			HSSFSheet sheet = workBook.createSheet(sheetName);
			// 合并第一行
			rowhead(sheet, workBook, cloumns, headName);
			// 设置单元格宽度
			for (int i = 0; i < cloumns.length; i++) {
				sheet.setColumnWidth(i, columnWidth);
			}
			// 创建标题样式
			rowTitle(sheet, workBook, cloumns);
			// 创建单元格样式
			HSSFCellStyle style = style(workBook);
			HSSFRow valueRow;
			HSSFCell cell;
			String[] content;
			for (int i = 0; i < contentList.size(); i++) {
				content = contentList.get(i);
				valueRow = sheet.createRow(i + 2);
				valueRow.setHeight(columnHeight);
				for (int j = 0; j < content.length; j++) {
					cell = valueRow.createCell(j);
					cell.setCellStyle(style);
					// 填值
					cell.setCellValue(content[j]);
				}
			}
			out = response.getOutputStream();
			workBook.write(out);
		} finally {
			if (out != null) {
				out.flush();// 操作结束，关闭文件
				out.close();
			}
		}
	}

	/**
	 * 单元格样式
	 * 
	 * @param workBook
	 * @return
	 */
	private HSSFCellStyle style(HSSFWorkbook workBook) {
		HSSFCellStyle style = workBook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setWrapText(true);// 设置自动换行
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 设置单元格字体显示左对齐
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 设置单元格字体显示居中(上下方向)
		return style;
	}

	/**
	 * 标题样式
	 * 
	 * @param sheet
	 * @param workBook
	 * @param cloumns
	 */
	private void rowTitle(HSSFSheet sheet, HSSFWorkbook workBook,
			String[] cloumns) {
		HSSFCellStyle styleTitle = workBook.createCellStyle();
		styleTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFFont font1 = workBook.createFont();// 创建一个字体对象
		font1.setFontHeightInPoints((short) 10);// 设置字体的高度
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		styleTitle.setFont(font1);// 设置style1的字体
		styleTitle.setWrapText(true);// 设置自动换行
		styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置单元格字体显示居中（左右方向）
		styleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 设置单元格字体显示居中(上下方向)
		HSSFRow rowTitle = sheet.createRow(1);// 第二行 标题
		styleTitle.setFillForegroundColor((short) 3);// 设置背景色
		styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		rowTitle.setHeight((short) 400);
		for (int i = 0; i < cloumns.length; i++) {
			HSSFCell cell = rowTitle.createCell(i);
			cell.setCellStyle(styleTitle);
			cell.setCellValue(cloumns[i]);
		}
	}

	/**
	 * excel抬头样式
	 * 
	 * @param sheet
	 * @param workBook
	 * @param cloumns
	 */
	private void rowhead(HSSFSheet sheet, HSSFWorkbook workBook,
			String[] cloumns, String headName) {
		HSSFRow rowhead = sheet.createRow(0);// 第一行 表头
		rowhead.setHeight((short) 700);
		HSSFCellStyle regionStyle = workBook.createCellStyle();
		regionStyle.setFillForegroundColor((short) 15);// 设置背景色
		regionStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		regionStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		regionStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		regionStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		regionStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

		HSSFFont font = workBook.createFont();
		font.setFontName(HSSFFONT_BLACK);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
		font.setFontHeightInPoints((short) 25);// 字体大小

		regionStyle.setFont(font);// 选择需要用到的字体格式
		regionStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置单元格字体显示居中（左右方向）
		HSSFCell cellOne = rowhead.createCell(0);
		cellOne.setCellStyle(regionStyle);
		cellOne.setCellValue(headName);
		for (int i = 1; i < cloumns.length; i++) {
			HSSFCell cell = rowhead.createCell(i);
			cell.setCellStyle(regionStyle);
		}
		// 合并单元格
		CellRangeAddress region = new CellRangeAddress(0, 0, 0,
				cloumns.length - 1);
		sheet.addMergedRegion(region);
	}

	/**
	 * 判断当前行是否为空
	 * 
	 * @param row
	 * @param cellLength
	 * @return
	 */
	public Boolean isValid(HSSFRow row, int cellLength) {
		for (int i = 0; i < cellLength; i++) {
			HSSFCell cell = row.getCell(i);
			if (cell != null) {
				if (constrainHSSF(cell) != null
						&& !"".equals(constrainHSSF(cell))) {
					return true;
				}
			}
		}
		return false;
	}

	public String constrainHSSF(HSSFCell cell) {
		String cellValue = "";
		DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		if (cell == null) {
			return cellValue;
		}

		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			cellValue = cell.getStringCellValue().trim();
			if (cellValue.trim().equals("") || cellValue.trim().length() <= 0)
				cellValue = " ";
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				// 用于转化为日期格式
				Date d = cell.getDateCellValue();
				cellValue = formater.format(d);
				return cellValue;
			} else {
				cellValue = String.valueOf(Double.valueOf(cell
						.getNumericCellValue()));
			}
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			cellValue = " ";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			break;
		default:
			break;
		}
		return cellValue;
	}
}