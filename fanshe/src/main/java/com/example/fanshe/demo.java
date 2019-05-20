package com.example.fanshe;

import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class demo {

    public static void main(String[] args) throws IllegalAccessException {


        Object obj="";
        Object obj2=null;
        boolean empty = ObjectUtils.isEmpty(obj);
        boolean empty2 = ObjectUtils.isEmpty(obj2);
        System.out.println(empty);
        System.out.println(empty2);

       /* EibCopyReg eibCopyReg = new EibCopyReg("haha1","haha2","haha3",444L,"haha5",666L);

        EibCopyReg eibCopyReg2 = new EibCopyReg("haha111","haha222","haha333",444444L,"haha555",666666L);
        List<EibCopyReg> list=new ArrayList<>();
        list.add(eibCopyReg);
        list.add(eibCopyReg2);*/

        //String exportDateByList = getExportDateByList(list);

        //System.out.println(exportDateByList);

        /*if(clazz.isAnnotationPresent(SopAnnotation.class))//扫描这个类是否使用了注解
        {
            SopAnnotation sa = (SopAnnotation)clazz.getAnnotation(SopAnnotation.class);//得到注解

            System.out.println(sa.color());//得到注解的值

        }*/
    }



    private <T> String getExportDateByList(List<T> list){
        //最终返回的字符串
        StringBuffer result=new StringBuffer();
        try {
            for (int i = 0; i <list.size() ; i++) {
                T eibCopyReg1 = list.get(i);
                //当前对象的类的Class对象
                Class<?> clazz = eibCopyReg1.getClass();
                //根据Class对象获取属性对象
                Field[] declaredFields = clazz.getDeclaredFields();
                //临时存储每个对象的属性值的字符串
                StringBuffer value=new StringBuffer();
                //循环当前对象的属性对象
                for (Field declaredField : declaredFields) {
                    SopAnnotation excelField = declaredField.getAnnotation(SopAnnotation.class);
                    if(declaredField.isAnnotationPresent(SopAnnotation.class)){
                        //获取导出表头并拼入字符串result中
                        if(0==i){
                            result.append(excelField.color()).append(";    ");
                        }
                        //获取private属性值
                        declaredField.setAccessible(true);
                        //获取导出的内容并拼入字符串result1中
                        Object obj = declaredField.get(eibCopyReg1);
                        value.append(obj).append(";    ");
                    }
                }
                //将上面拼接的临时属性值存到 最终的返回字符串中
                result.append("/r/n   ").append(value);
            }
        }catch (Exception e){

        }
        return result.toString();
    }
}
