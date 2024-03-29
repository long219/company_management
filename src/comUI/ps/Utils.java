package comUI.ps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.beanutils.BeanUtils;

public class Utils {
	private static BufferedReader brInt;
	
	private static BufferedReader brString;
	
	public static int getInt() {
	   brInt = new BufferedReader(new InputStreamReader(System.in));
	   int input = 0;
	   while(true) {
		   try {
			   input = Integer.valueOf(brInt.readLine());
			   break;
			} catch (NumberFormatException e) {
				System.out.println("输入有误，请输入数字");
			} catch (IOException e) {
				closeStream();
				e.printStackTrace();
			}
	   }
	   
		return input;
	}
	
	public static  double getDouble(){
	   Scanner sc=new Scanner(System.in);
	   return sc.nextDouble();
	   
	}
	
	public static float getFloat(){
		Scanner sc=new Scanner(System.in);
		return sc.nextFloat();
	}
   public boolean getBoolean(){
	   Scanner sc=new Scanner(System.in);
	   return  sc.nextBoolean();
   }
   public byte getByte(){
	   Scanner sc=new Scanner(System.in);
	   return sc.nextByte();
   }
	public static String getString(){
		brString = new BufferedReader(new InputStreamReader(System.in));
		String string = null;
		try {
			string = brString.readLine();
		} catch (IOException e) {
			closeStream();
			e.printStackTrace();
		}
		return string;
	}
	
	
	
   public  int  getRandom(int i){
	   java.util.Random random=new java.util.Random();// 定义随机类
	   int result=random.nextInt(i);// 返i[0,10)集合中的整数，注意不包括10
	   return result+1;   
   }
   /**
    * 获取当前系统时间
    * @return
    */
   public  static  String getDate(){
	   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	   return df.format(new Date());
   }
  
   /**
    * 获取订单号
    * @return
    */
   public static   String getOrderId(){
	   SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	   return df.format(new Date())+"_A";
   }
   public static void main(String[] args) {
		  System.out.println(getTimeDifference("2017-07-12 10:10:10","2017-07-11 10:10:10"));
   }
   
   
   
   /**
	 * 获取两个日期的时间差  （endDate - currentDate）
	 * @param endDate  结束时间
	 * @param currentDate   当前时间    
	 * @return
	 */
	public  static  long  getTimeDifference(String endDate ,String currentDate){
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(endDate != null && currentDate != null) {
				Date end = df.parse(endDate);
			    Date current = df.parse(currentDate);
			    long diff = end.getTime() - current.getTime();
			    long hours = diff / (1000 * 60 * 60);
			    return  hours;
			}
		} catch (Exception e) {
			System.out.println("算时间差错误"+e);
		}
		return 0;
	}
	
	public static void closeStream() {
		try {
			if(brString != null) {
				brString.close();
			}
			if(brInt != null) {
				brInt.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将一个 ResultSet 转换成 java对象   （vo）
	 * @param <T>
	 */
	public static <T> List<T> sqlToJavaObjectVO(ResultSet rs, Class outputClass) {
		List<T> outputList = null;
		try {
			// make sure resultset is not null
			if (rs != null) {
				// check if outputClass has 'Entity' annotation
				if (outputClass.isAnnotationPresent(Entity.class)) {
					// get the resultset metadata
					ResultSetMetaData rsmd = rs.getMetaData();
					// get all the attributes of outputClass
					Field[] fields = outputClass.getDeclaredFields();
					while (rs.next()) {
						T bean = (T) outputClass.newInstance();
						for (int _iterator = 0; _iterator < rsmd.getColumnCount(); _iterator++) {
							// getting the SQL column name
							String columnName = rsmd.getColumnName(_iterator + 1);
							// reading the value of the SQL column
							Object columnValue = rs.getObject(_iterator + 1);
							// iterating over outputClass attributes to check if
							// any attribute has 'Column' annotation with
							// matching 'name' value
							for (Field field : fields) {
								if (field.isAnnotationPresent(Column.class)) {
									Column column = field.getAnnotation(Column.class);
									if (column.name().equalsIgnoreCase(columnName) && columnValue != null) {
										BeanUtils.setProperty(bean, field.getName(), columnValue);
										break;
									}
								}
							}
						}
						if (outputList == null) {
							outputList = new ArrayList<T>();
						}
						outputList.add(bean);
					}
				} else {
					// throw some error
				}
			} else {
				return null;
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return outputList;
	}
}
