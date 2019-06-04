package com.example.demoamqp.DeadAmqpController.web;

import com.example.demoamqp.DeadAmqpController.annotation.ExcelValue;
import com.example.demoamqp.DeadAmqpController.bean.MerchantsCustomer;
import com.example.demoamqp.DeadAmqpController.bean.User;
import com.example.demoamqp.DeadAmqpController.bean.VoteRecord;
import com.example.demoamqp.DeadAmqpController.config.*;
import com.example.demoamqp.DeadAmqpController.service.impl.CacheServiceImpl;
import com.example.demoamqp.DeadAmqpController.service.impl.VoteRecordServiceImpl;
import com.example.demoamqp.DeadAmqpController.utils.PoiUtil;
import com.github.pagehelper.PageHelper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;

@RestController
@RefreshScope
public class AmqpController {

    private static final Logger logger = LoggerFactory.getLogger(AmqpController.class);
    @Autowired
    private CacheServiceImpl cacheService;

    @Autowired
    private VoteRecordServiceImpl voteRecordService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${server.port}")
    private String port;

    @Autowired(required = false)
    private User user0;


    @RequestMapping(value = "jisuan",method = RequestMethod.GET)
    public Long jisuan(Integer a){
        if(a<2){
            return 1L;
        }else{
            return jisuan(a-1)+jisuan2(a);
        }


    }

    public Long jisuan2(Integer b){
        if(b==1){
            return 1L;
        }else if(0==b){
            return 0L;
        }else{
            return jisuan2(b-1)*b;
        }

    }


    /**
     * 测试单例模式
     */
    @RequestMapping(value = "getSingle",method = RequestMethod.GET)
    public void getSingle(){
        Single instance = Single.getInstance();
        System.out.println(instance);
    }

    @RequestMapping(value = "setSingle",method = RequestMethod.GET)
    public void setSingle(){
        Single.stInstance();
    }

    @RequestMapping(value="getVoteRecordBysize",method = RequestMethod.GET)
    public List<VoteRecord> getVoteRecordBysize(HttpServletResponse response){

        List<VoteRecord> voteRecords = voteRecordService.queryVoteRecord();
        SetExcel(voteRecords,response,"测试表");
        return voteRecords;
    }


    @RequestMapping(value="getUserBean",method = RequestMethod.GET)
    public User getUserBean(Boolean isClose){

        System.out.println(user0);
        System.out.println(user0);
        return user0;
    }

    /**
     * 传入指定修改的字段
     * @param name
     * @param value
     * @param id
     * @return
     */
    @RequestMapping(value = "/updateByDate" ,method = RequestMethod.POST)
    public int updateByDate(String name, String value, String id){
        int i = cacheService.updateUserByDate(name, value, id);
        System.out.println(i);
        return i;
    }

    @RequestMapping(value = "/haha" ,method = RequestMethod.GET)
    public String haha(@Valid String id){
        User user = cacheService.queryUserById(id);
        System.out.println(user);
       // int a=1/0;

        return port;
    }

    @RequestMapping(value = "/sethaha" ,method = RequestMethod.POST)
    public String sethaha(@Valid User user){
        /*if(result.hasFieldErrors()){
            List<FieldError> errorList = result.getFieldErrors();
            //通过断言抛出参数不合法的异常
            errorList.stream().forEach(item -> Assert.isTrue(false,item.getDefaultMessage()));
        }*/

        cacheService.addUserByUser(user);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_D_TO_D,RabbitConfig.ROUTINGKEY_DD ,user
                ,new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //消息持久化
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                //消息过期时间
                message.getMessageProperties().setExpiration(1000*15+"");
                return message;
            }
        });
        System.out.println(user);
        return user.toString();
    }

    @RequestMapping("/sethaha1")
    public String sethaha1(){

        User user = new User();
        user.setId(UUID.randomUUID().toString().substring(0,5));
        user.setUserName("张三"+UUID.randomUUID().toString().substring(0,2));
        user.setPassword("123456");
        cacheService.addUserByUser(user);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_D_TO_D,RabbitConfig.ROUTINGKEY_DD ,user);
        System.out.println(user);
        return user.toString();
    }


    @RequestMapping("/zhifu")
    public void zhifu(String id){
        cacheService.updateUserById(id);
    }

    @RequestMapping("/exportEXCEL")
    public void exportEXCEL(HttpServletResponse response){

        MerchantsCustomer me=new MerchantsCustomer("11","阿里伯伯",11,"22","9999");
        MerchantsCustomer me2=new MerchantsCustomer("112","阿里伯伯2",112,"222","99992");
        List<MerchantsCustomer> list=new ArrayList<>();
        list.add(me);
        list.add(me2);
        SetExcel(list,response,"测试表");
    }
    //自动导出excel
    private <T> void SetExcel(List<T> list,HttpServletResponse response,String titleName) {
        try {
        if(null==list ||list.size()<1){
            return;
        }
        //存储对象的属性对应那一列
        Map<String,Integer> map =new HashMap<>();
            SXSSFWorkbook wb=new SXSSFWorkbook();

            //合并的单元格样式
            CellStyle boderStyle = wb.createCellStyle();
            // HSSFCellStyle boderStyle = wb.createCellStyle();


            Sheet sheet = wb.createSheet("哈哈");
        int titleIndex=-1;
        for (int i = 0; i <list.size() ; i++) {
            //创建所在行
            Row row = null;
            //获取LIST的当前对象
            T t = list.get(i);

            Class<?> clazz = t.getClass();
            //获取属性
            Field[] fields = clazz.getDeclaredFields();
            //第一次循环的时候创建表头2
            if(0==i){
                row = sheet.createRow(1);
                //循环获取的属性，判断是否需要导出
                for (int j = 0; j <fields.length ; j++) {
                    Field field = fields[j];
                    //如果设置了@ExcelValue注解则要导出
                    if(field.isAnnotationPresent(ExcelValue.class)){
                        titleIndex++;
                        //获取@ExcelValue的name值
                        String titlename = field.getAnnotation(ExcelValue.class).name();
                        row.createCell(titleIndex).setCellValue(titlename);
                        //记录该表头的位置
                        String name = field.getName();
                        map.put(name,titleIndex);

                    }
                }
            }
            row = sheet.createRow(i+2);
            //创建表格内容
            for (int k = 0; k <fields.length ; k++) {

                Field field = fields[k];
                //获取private属性值
                field.setAccessible(true);
                //获取导出的内容
                String name = field.getName();
                String value = field.get(t)+"";
                if(map.containsKey(name)){
                    row.createCell(map.get(name)).setCellValue(value);
                }
            }
            //创建表头1
            setTitet(titleName,wb,sheet,boderStyle,0,0,0,titleIndex);

        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=xx.xls");
        OutputStream ouputStream = response.getOutputStream();
        wb.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
        wb.close();
    } catch (Exception e) {

        e.printStackTrace();
    }

    }

    //将表格第一行设置为表头
    private void setTitet(String titleName,SXSSFWorkbook wb,Sheet sheet,CellStyle style,int firstRow, int lastRow, int firstCol, int lastCol) {
        // 合并单元格：参数：起始行, 终止行, 起始列, 终止列
        CellRangeAddress cra = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        Cell cell = sheet.createRow(firstRow).createCell(firstCol);
        cell.setCellValue(titleName);
        cell.setCellStyle(style);
        sheet.addMergedRegion(cra);

    }


    @RequestMapping(value="/getExport",method = RequestMethod.GET)
    public String getExport(HttpServletResponse response,String filaName) throws Exception {
        // 总记录数
        Integer totalRowCount = this.voteRecordService.queryVoteRecordCount();

        // 导出EXCEL文件名称
        // 标题
        String[] titles = {"id","userId","voteId","groupId","createTime"};

        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, filaName, titles, new WriteExcelDataDelegated() {
            @Override
            public void writeExcelData(SXSSFSheet eachSheet, Integer startRowCount, Integer endRowCount, Integer currentPage, Integer pageSize) throws Exception {

                PageHelper.startPage(currentPage, pageSize);
                List<VoteRecord> voteRecords = voteRecordService.queryVoteRecord();
                if (!CollectionUtils.isEmpty(voteRecords)) {

                    // --------------   这一块变量照着抄就行  强迫症 后期也封装起来     ----------------------
                    for (int i = startRowCount; i <= endRowCount; i++) {
                        SXSSFRow eachDataRow = eachSheet.createRow(i);
                        if ((i - startRowCount) < voteRecords.size()) {

                            VoteRecord eachUserVO = voteRecords.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------

                            eachDataRow.createCell(0).setCellValue(eachUserVO.getId() == null ? "" : eachUserVO.getId());
                            eachDataRow.createCell(1).setCellValue(eachUserVO.getUserId() == null ? "" : eachUserVO.getUserId());
                            eachDataRow.createCell(2).setCellValue(eachUserVO.getVoteId() == null ? "" : eachUserVO.getVoteId());
                            eachDataRow.createCell(3).setCellValue(eachUserVO.getGroupId() == null ? "" : eachUserVO.getGroupId());
                            eachDataRow.createCell(4).setCellValue(eachUserVO.getCreateTime() == null ? "" : eachUserVO.getCreateTime());
                        }
                    }
                }

            }
        });

        return "导出成功";
    }


}
