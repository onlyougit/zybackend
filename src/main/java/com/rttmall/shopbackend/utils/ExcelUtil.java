package com.rttmall.shopbackend.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
public class ExcelUtil {
    /**
     * @param arrayList 把要导出的数据封装到一个个对象中，把对象存储到ArrayList中
     * @param path 文件的路径
     * @param list 第一行为数据的名称
     */
    public static void out(ArrayList<?> arrayList,String path,ArrayList<String> list) throws
            IllegalArgumentException, IllegalAccessException, IOException, WriteException{
        //打开文件
        WritableWorkbook book =  Workbook.createWorkbook(new File(path));
        //创建选项卡
        WritableSheet sheet = book.createSheet("sheet1", 0);
        //excel中第一行为数据名称
        for(int i=0;i<list.size();i++){
            Label label = new Label(i,0,list.get(i));
            sheet.addCell(label);
        }
        //j为list的元素个数，数据从第2行还是，第一行为数据的名称
        for(int j = 1;j<=arrayList.size();j++){
            //根据实例对象获取该对象的Class对象，下表从0开始j-1
            Class<? extends Object> clazz = arrayList.get(j-1).getClass();
            //获取所有属性值，公有属性和私有属性
            Field[] fields = clazz.getDeclaredFields();
            int i=0;
            for(Field ff : fields){
                //私有属性不能被访问和修改，要进行权限的修改
                ff.setAccessible(true);
                /**
                 * ff.getType()获取属性类型
                 * ff.getName()获取属性名
                 * ff.get()获取属性值
                 * System.out.println(ff.getType()+" "+ff.getName()+" "+ff.get(list.get(j)));
                 */

                //Label(0(代表列),1(行), "hello") 代表第二行第一个的值为hello
                Label label = new Label(i++,j, String.valueOf(ff.get(arrayList.get(j-1))));
                sheet.addCell(label);
            }
        }
        book.write();
        book.close();
    }

    /**
     * @param clazz Class的对象
     * @param path 文件路径
     * @return ArrayList
     */
    public static ArrayList<?> in(Class<?> clazz,String path) throws BiffException, IOException, NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
        ArrayList<Object> list = new ArrayList<Object>();
        Workbook book =  Workbook.getWorkbook(new File(path));
        //获取第一个选项卡
        Sheet sheet = book.getSheet(0);
        //根据Class的对象获取所有属性
        Field[] fields = clazz.getDeclaredFields();
        //获取行数，每一行为每一个对象的数据
        int row = sheet.getRows();
        for(int j=1;j<row;j++){
            int i = 0;
            //实例化一个对象
            Object  obj = clazz.newInstance();
            for(Field ff : fields){
                //ff.getName()获取属性名，把属性名的首字母大写。例如属性名为name,那么它set的方法名为setName
                String methodName = "set"+ff.getName().substring(0, 1).toUpperCase()+ff.getName().substring(1);
                //j为行，i为列，获取文件中的值,类型为String
                String value;
                Cell cell = sheet.getCell(i++,j);
                value = cell.getContents();
                //根据实例化的对象的Class对象获取实例对象的方法 ,getMethod的参数为要获取的方法的方法名，和参数的类型也即是要赋值的属性的类型
                Method method = obj.getClass().getMethod(methodName,ff.getType());
                //在excel中获取的值都是String,所以要根据属性的类型进行值的转换
                if(ff.getType().toString().equals("class java.lang.String")){
                    //调用实例对象obj的set方法进行对属性的数值
                    method.invoke(obj, value);
                }else if(ff.getType().toString().equals("int")||ff.getType().toString().equals("Integer")){
                    method.invoke(obj, Integer.valueOf(value));
                }
            }
            list.add(obj);//把值赋值到对象中，加入到ArrayList中
        }
        book.close();
        return list;
    }
}
