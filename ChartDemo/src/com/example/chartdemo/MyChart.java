package com.example.chartdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

public class MyChart extends View {

	private int bgColor = Color.argb(Integer.parseInt("0", 16),
			Integer.parseInt("4d", 16), Integer.parseInt("af", 16),
			Integer.parseInt("ea", 16));// 整体的背景色

	private int singleColumnFillColor = Color.rgb(Integer.parseInt("e7", 16),
			Integer.parseInt("e7", 16), Integer.parseInt("e9", 16));// 单数列的背景色

	private int doubleColumnFillColor = Color.rgb(Integer.parseInt("4d", 16),
			Integer.parseInt("af", 16), Integer.parseInt("ea", 16));// 单数行的背景色

	private int fillDownColor = Color.rgb(Integer.parseInt("45", 16),
			Integer.parseInt("64", 16), Integer.parseInt("bf", 16));// 填充下面部分的背景色

	private int xyLineColor = Color.rgb(Integer.parseInt("94", 16),
			Integer.parseInt("94", 16), Integer.parseInt("94", 16));// 表格的线颜色

	private int chartLineColor = Color.GREEN;// 绘制趋势线的颜色

	private int shadowLineColor = Color.rgb(Integer.parseInt("1a", 16),
			Integer.parseInt("49", 16), Integer.parseInt("84", 16));// 趋势线阴影的颜色

	private String yUnit = "";// Y轴单位

	private boolean isDrawY = true;// 是否绘制Y轴

	private boolean isDrawX = true;// 是否绘制X轴

	private boolean isDrawInsideX = true;// 是否绘制内部的X轴

	private boolean isDrawInsedeY = true;// 是否绘制内部的Y轴

	private boolean isFillDown = false;// 是否填充点的下面部分

	private boolean isFillUp = false;// 是否填充点的上面部分（暂未实现）

	private boolean isAppendX = false;// X轴是否向左突出一点

	private boolean isDemo = true;// 是否demo测试数据

	private int ScreenX;// view的宽度

	private int ScreenY;// view的高度

	private int numberOfX = 6;// 默认X轴放6个值

	private int numberOfY = 5;// 默认Y轴放5个值（越多显示的值越精细）

	private int paddingTop = 30;// 默认上下左右的padding

	private int paddingLeft = 70;// 默认上下左右的padding

	private int paddingRight = 30;// 默认上下左右的padding

	private int paddingDown = 50;// 默认上下左右的padding

	private int appendXLength = 10;// 向左X轴突出的长度

	private float maxNumber = 0;// Y轴最大值

	private List<List<Float>> pointList;// 传入的数据

	private List<Integer> bitmapList = null;// 传入的颜色值

	private List<Integer> lineColorList;

	private List<String> titleXList;// 传入的X轴标题

	private List<String> titleYList;// 计算得出的Y轴标题

	public MyChart(Context context) {
		super(context);
		demo();

	}

	public MyChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		demo();
	}

	private void demo() {

		pointList = new ArrayList<List<Float>>();
		titleXList = new ArrayList<String>();

		// 线的颜色
		lineColorList = new ArrayList<Integer>();
		lineColorList.add(Color.WHITE);
		lineColorList.add(Color.GREEN);
		lineColorList.add(Color.YELLOW);

		for (int i = 0; i < 3; i++) {
			List<Float> pointInList = new ArrayList<Float>();
			for (int j = 0; j < 6; j++) {
				Random r = new Random();
				Float z = r.nextFloat() * 100;
				pointInList.add(z);
				titleXList.add("12." + (i + 1) + "1");
			}
			pointList.add(pointInList);
		}

	}

	/**
	 * 测量。
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measuredHeight = measureHeight(heightMeasureSpec);

		int measuredWidth = measureWidth(widthMeasureSpec);

		setMeasuredDimension(measuredWidth, measuredHeight);

		ScreenX = measuredWidth;

		ScreenY = measuredHeight;
	}

	/**
	 * 计算View的高度
	 * 
	 * @param measureSpec
	 * @return
	 */
	private int measureHeight(int measureSpec) {

		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		int result = 300;
		if (specMode == MeasureSpec.AT_MOST) {

			result = specSize;
		} else if (specMode == MeasureSpec.EXACTLY) {

			result = specSize;
		}

		return result;
	}

	/**
	 * 计算View的宽度。
	 * 
	 * @param measureSpec
	 * @return
	 */
	private int measureWidth(int measureSpec) {
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		int result = 450;
		if (specMode == MeasureSpec.AT_MOST) {
			result = specSize;
		}

		else if (specMode == MeasureSpec.EXACTLY) {

			result = specSize;
		}

		return result;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		maxNumber = 0;
		List<Point> listX = initNumberOfX();// 计算出X轴平均后的坐标
		List<Point> listY = initNumberOfY();// 计算出Y轴平均后的坐标
		canvas.drawColor(bgColor);// 背景色
		// fillColor(listX, canvas);// 根据需求，对每一个框做不同的填充颜色

		// 画背景表格
		Paint paint = new Paint();
		paint.setColor(xyLineColor);// //表格线颜色
		if (isDrawX) {
			int appendX = 0;
			if (isAppendX) {
				appendX = appendXLength;
			}
			canvas.drawLine(paddingLeft - appendX, paddingTop + listY.get(0).y,
					listY.get(0).x + paddingLeft, paddingTop + listY.get(0).y,
					paint);
		}
		if (isDrawY) {
			canvas.drawLine(listX.get(0).x, paddingTop, listX.get(0).x,
					listX.get(0).y + paddingTop, paint);
		}
		if (isDrawInsedeY) {// 绘制纵向的
			for (Point point : listX) {
				if (!isDrawX) {
					isDrawX = !isDrawX;
					continue;
				}
				canvas.drawLine(point.x, paddingTop, point.x, point.y
						+ paddingTop, paint);
			}
		}
		if (isDrawInsideX) {// 绘制横向的
			for (Point point : listY) {
				if (!isDrawY) {
					isDrawY = !isDrawY;
					continue;
				}
				int appendX = 0;
				if (isAppendX) {
					appendX = appendXLength;
				}
				canvas.drawLine(paddingLeft - appendX, paddingTop + point.y,
						point.x + paddingLeft, paddingTop + point.y, paint);
			}
		}

		setYTitle(listY, canvas);// 画折线图Y的单位，同时计算出最大的Y轴值
		List<List<Point>> positionList = countListPosition(listX);// 计算像素位置
		drawFill(canvas, positionList);// 填充折线和边框
		drawChart(canvas, positionList);// 画折线
		drawCicle(canvas, positionList);// 画点

		setXTitle(listX, canvas);// 画折线图X的单位

	}

	/**
	 * 填充折线和边框
	 * 
	 * @param canvas
	 * @param positionList
	 */
	private void drawFill(Canvas canvas, List<List<Point>> positionList) {
		if (!isFillDown) {
			return;
		}
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(fillDownColor);
		paint.setAlpha(76);
		for (int i = 0; i < positionList.size(); i++) {
			Path path = new Path();
			path.moveTo(paddingLeft, ScreenY - paddingDown);
			for (int j = 0; j < positionList.get(i).size(); j++) {
				path.lineTo(positionList.get(i).get(j).x, positionList.get(i)
						.get(j).y);
			}
			path.lineTo(ScreenX - paddingRight, ScreenY - paddingDown);
			path.close();
			canvas.drawPath(path, paint);
		}
	}

	/**
	 * 画点
	 * 
	 * @param canvas
	 * @param positionList
	 */
	private void drawCicle(Canvas canvas, List<List<Point>> positionList) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.GREEN);
		// Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.comm_chart_point);
		int resouceId = 0;
		for (int i = 0; i < positionList.size(); i++) {

			if (bitmapList != null && bitmapList.get(i) != null) {
				resouceId = bitmapList.get(i);
			} else {
				resouceId = R.drawable.comm_chart_point;
			}

			Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
					resouceId);

			for (int j = 0; j < positionList.get(i).size(); j++) {
//				canvas.drawBitmap(bitmap, positionList.get(i).get(j).x + 0.5f
//						- bitmap.getWidth() / 2, positionList.get(i).get(j).y
//						+ 0.5f - bitmap.getHeight() / 2, paint);
				 canvas.drawCircle(positionList.get(i).get(j).x,
				 positionList.get(i).get(j).y,
				 7, paint);

			}
		}
	}

	/**
	 * 画折线
	 * 
	 * @param canvas
	 * @param positionList
	 */
	private void drawChart(Canvas canvas, List<List<Point>> positionList) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(chartLineColor);
		paint.setStrokeWidth(3);// 默认线宽为3，到时候提升到全局变量，用于设置
		Paint shadowPaint = new Paint();
		shadowPaint.setAntiAlias(true);
		shadowPaint.setColor(shadowLineColor);
		shadowPaint.setStrokeWidth(1);// 默认线宽为3，到时候提升到全局变量，用于设置
		shadowPaint.setAlpha(178);
		for (int i = 0; i < positionList.size(); i++) {
			if (lineColorList != null && lineColorList.get(i) != null) {
				paint.setColor(lineColorList.get(i));
			}
			for (int j = 0; j < positionList.get(i).size() - 1; j++) {
				canvas.drawLine(positionList.get(i).get(j).x,
						positionList.get(i).get(j).y + 2, positionList.get(i)
								.get(j + 1).x,
						positionList.get(i).get(j + 1).y + 2, shadowPaint);
				canvas.drawLine(positionList.get(i).get(j).x,
						positionList.get(i).get(j).y,
						positionList.get(i).get(j + 1).x, positionList.get(i)
								.get(j + 1).y, paint);
			}
		}
	}

	/**
	 * 画折线图X的单位
	 * 
	 * @param listX
	 * @param canvas
	 */
	private void setXTitle(List<Point> listX, Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		if (titleXList == null) {
			titleXList = new ArrayList<String>();
			for (int i = 1; i <= numberOfX; i++) {
				titleXList.add("title" + i);
			}
		}
		for (int i = 0; i < numberOfX; i++) {
			canvas.save();
			canvas.rotate(30, listX.get(i).x, listX.get(i).y + paddingTop
					+ paddingDown / 2);
			canvas.drawText(titleXList.get(i), listX.get(i).x, listX.get(i).y
					+ paddingTop + paddingDown / 2, paint);
			canvas.restore();
		}
	}

	/**
	 * // 计算像素位置,根据X轴的点计算数据的位置。
	 * 
	 * @param listX
	 * @return
	 */
	private List<List<Point>> countListPosition(List<Point> listX) {
		List<List<Point>> positionList = new ArrayList<List<Point>>();
		if (pointList == null) {
			pointList = new ArrayList<List<Float>>();
			List<Float> pointInList = new ArrayList<Float>();
			for (int i = 0; i < numberOfX; i++) {
				pointInList.add(0f);
			}
			pointList.add(pointInList);
		}
		for (int i = 0; i < pointList.size(); i++) {
			List<Point> positionInList = new ArrayList<Point>();
			for (int j = 0; j < pointList.get(i).size(); j++) {
				Point point = new Point();
				Float z = pointList.get(i).get(j);
				point.x = listX.get(j).x;
				point.y = listX.get(j).y
						+ paddingTop
						- (int) ((listX.get(j).y) * (float) z / (float) maxNumber);
				positionInList.add(point);
			}
			positionList.add(positionInList);
		}
		return positionList;
	}

	/**
	 * 画折线图Y的单位
	 * 
	 * @param listY
	 *            Y轴的点集合
	 * @param canvas
	 */
	private void setYTitle(List<Point> listY, Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		if (pointList == null) {
			titleYList = new ArrayList<String>();
			for (int i = 1; i <= numberOfY; i++) {
				titleYList.add(String.valueOf(100 / i));
			}
		} else {
			for (int i = 0; i < pointList.size(); i++) {
				for (int j = 0; j < pointList.get(i).size(); j++) {

					if (pointList.get(i).get(j) > maxNumber) {
						maxNumber = pointList.get(i).get(j);
					}
				}
			}
			maxNumber = maxNumber + maxNumber / 3;
			titleYList = new ArrayList<String>();
			for (int i = 0; i < numberOfY; i++) {
				titleYList.add(String.valueOf((int) (0 + i
						* (maxNumber / (numberOfY - 1)))));
			}
		}
		for (int i = 0; i < numberOfY; i++) {
			int appendX = 0;
			if (isAppendX) {
				appendX = appendXLength;
			}
			if (i != 0) {
				canvas.drawText(titleYList.get(i), paddingLeft - appendX
						- paddingLeft / 3, paddingTop + listY.get(i).y, paint);
			} else {
				canvas.drawText(titleYList.get(i) + yUnit, paddingLeft
						- appendX - paddingLeft / 3, paddingTop
						+ listY.get(i).y, paint);
			}
		}
	}

	/**
	 * 计算出X轴平均后的坐标
	 * 
	 * @return
	 */
	private List<Point> initNumberOfX() {
		int num = (ScreenX - paddingLeft - paddingRight) / (numberOfX - 1);
		List<Point> list = new ArrayList<Point>();
		for (int i = 0; i < numberOfX; i++) {
			Point point = new Point();
			point.y = ScreenY - paddingDown - paddingTop;
			point.x = paddingLeft + num * i;
			list.add(point);
		}
		return list;
	}

	/**
	 * 计算出Y轴平均后的坐标
	 * 
	 * @return
	 */
	private List<Point> initNumberOfY() {
		int num = (ScreenY - paddingDown - paddingTop) / (numberOfY - 1);
		List<Point> list = new ArrayList<Point>();
		for (int i = 0; i < numberOfY; i++) {
			Point point = new Point();
			point.x = ScreenX - paddingLeft - paddingRight;
			point.y = ScreenY - paddingDown - paddingTop - num * i;
			list.add(point);
		}
		return list;
	}

	private void fillColor(List<Point> listX, Canvas canvas) {
		Paint paint = new Paint();
		paint.setStyle(Style.FILL);
		for (int i = 0; i < numberOfX - 1; i++) {
			if (i % 2 == 0) {
				paint.setColor(singleColumnFillColor);
				paint.setAlpha(102);
			} else {
				paint.setColor(doubleColumnFillColor);
				paint.setAlpha(255);
			}
			canvas.drawRect(listX.get(i).x, paddingTop, listX.get(i + 1).x,
					ScreenY - paddingDown, paint);
		}
	}

}
