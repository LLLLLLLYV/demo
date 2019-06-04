package com.example.demoamqp.RedisController;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.demoamqp.DeadAmqpController.bean.User;
import com.example.demoamqp.DeadAmqpController.bean.VoteRecord;
import com.example.demoamqp.DeadAmqpController.utils.PoiUtil;
import com.example.demoamqp.DeadAmqpController.service.impl.VoteRecordServiceImpl;
import com.example.demoamqp.RedisController.config.RedisDate;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@RestController
public class DemoRedisController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private User user0;

    @Autowired
    private VoteRecordServiceImpl voteRecordService;

    @RequestMapping(value="getUserBean2",method = RequestMethod.GET)
    public User getUserBean(Boolean isClose){

        System.out.println(user0);
        System.out.println(user0);
        return user0;
    }


    @RequestMapping(value = "getRedisBeans",method = RequestMethod.GET)
    private void getRedisBeans(){
        AnnotationConfigApplicationContext applicationContext2 = new AnnotationConfigApplicationContext(RedisDate.class);
        String[] beanNames = applicationContext2.getBeanDefinitionNames();
        for(int i=0;i<beanNames.length;i++){
            System.out.println("bean名称为==="+beanNames[i]);
        }
    }


    @RequestMapping(value = "setRedit",method = RequestMethod.GET)
    public Long setRedit(String key){
        Long expire = redisTemplate.getExpire(key);
        return expire;
    }

    @RequestMapping(value = "setRedit2",method = RequestMethod.POST)
    public String setRedit2(String key,@RequestBody User user){

        Jedis jedis = jedisPool.getResource();
        String set = jedis.set(key.getBytes(), serialize(user));
        jedis.close();
        return set;
    }

    @RequestMapping(value = "setHset",method = RequestMethod.POST)
    public Long setHset(@RequestBody User user){
        Jedis jedis = jedisPool.getResource();

        jedis.hset("User"+user.getId(),"id",user.getId());
        jedis.hset("User"+user.getId(),"password",user.getPassword());
        jedis.hset("User"+user.getId(),"userName",user.getUserName());
        Long status = jedis.hset("User" + user.getId(), "status", user.getStatus() + "");
        jedis.close();
        return status;
    }

    @RequestMapping(value = "getHset",method = RequestMethod.POST)
    public Map setHset(@RequestParam("id") String id){
        Jedis jedis = jedisPool.getResource();
        Map<String, String> stringStringMap = jedis.hgetAll("User" + id);
        jedis.close();
        return stringStringMap;
    }

    @RequestMapping(value = "getStr",method = RequestMethod.POST)
    public String getStr(@RequestParam("key") String key){
        Jedis jedis = jedisPool.getResource();
        byte[] bytes = jedis.get(key.getBytes());
        User unserizlize = (User)unserizlize(bytes);
        jedis.close();
        return unserizlize.toString();
    }

    //序列化
    public static byte [] serialize(Object obj){
        ObjectOutputStream obi=null;
        ByteArrayOutputStream bai=null;
        try {
            bai=new ByteArrayOutputStream();
            obi=new ObjectOutputStream(bai);
            obi.writeObject(obj);
            byte[] byt=bai.toByteArray();
            return byt;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //反序列化
    public static Object unserizlize(byte[] byt){
        ObjectInputStream oii=null;
        ByteArrayInputStream bis=null;
        bis=new ByteArrayInputStream(byt);
        try {
            oii=new ObjectInputStream(bis);
            Object obj=oii.readObject();
            return obj;
        } catch (Exception e) {

            e.printStackTrace();
        }


        return null;
    }

    @RequestMapping(value="/getExport2",method = RequestMethod.GET)
    public String getExport2(HttpServletResponse response, String filaName) throws Exception{
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);

            // 设置EXCEL名称
            String fileName = new String(("SystemExcel").getBytes(), "UTF-8");

            // 设置SHEET名称
            Sheet sheet = new Sheet(1, 0);
            sheet.setSheetName("系统列表sheet1");

            // 设置标题
            Table table = new Table(1);
            List<List<String>> titles = new ArrayList<List<String>>();
            titles.add(Arrays.asList("id"));
            titles.add(Arrays.asList("userId"));
            titles.add(Arrays.asList("voteId"));
            titles.add(Arrays.asList("groupId"));
            titles.add(Arrays.asList("createTime"));
            table.setHead(titles);

            // 查询总数并 【封装相关变量 这块直接拷贝就行 不要改动】
            Integer totalRowCount = voteRecordService.queryVoteRecordCount();

            Integer pageSize = PoiUtil.PER_WRITE_ROW_COUNT;
            Integer writeCount = totalRowCount % pageSize == 0 ? (totalRowCount / pageSize) : (totalRowCount / pageSize + 1);

            // 写数据 这个i的最大值直接拷贝就行了 不要改
            for (int i = 0; i < writeCount; i++) {
                List<List<String>> dataList = new LinkedList<>();

                // 此处查询并封装数据即可 currentPage, pageSize这个变量封装好的 不要改动
                PageHelper.startPage(i + 1, pageSize);
                List<VoteRecord> sysSystemVOList = voteRecordService.queryVoteRecord();

                if (!CollectionUtils.isEmpty(sysSystemVOList)) {
                    sysSystemVOList.forEach(eachSysSystemVO -> {
                        dataList.add(Arrays.asList(
                                eachSysSystemVO.getId(),
                                eachSysSystemVO.getUserId(),
                                eachSysSystemVO.getVoteId(),
                                eachSysSystemVO.getGroupId(),
                                eachSysSystemVO.getCreateTime()
                        ));
                    });
                }
                writer.write0(dataList, sheet, table);
            }

            // 下载EXCEL
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            writer.finish();
            out.flush();

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return "导出系统列表EXCEL成功";
    }
}
