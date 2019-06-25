//package com.anmoyi.model;
//
//import com.anmoyi.model.dao.UseTimeMapper;
//import com.anmoyi.model.dao.UserMapper;
//import com.anmoyi.model.po.UseTime;
//import com.anmoyi.model.po.User;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Date;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@EnableAutoConfiguration
////生成dao对应的mapper
//@MapperScan("com.anmoyi.model.dao")
//public class ModelApplicationTests {
//
//    @Autowired
//    private UseTimeMapper useTimeMapper;
//
//
//    @Autowired
//    private UserMapper userMapper;
//
//
//	@Test
//	public void contextLoads() {
//        System.out.println("****************************");
//        System.out.println("test");
//
//        User user = userMapper.selectByPrimaryKey(6);
//
//
//        long currentTime = new Date().getTime();
//
//
//
//        List<UseTime>  list = useTimeMapper.getUseTimeList(9876,1,new Date());
//        System.out.println("useTime is :");
//        System.out.println(list);
//
//
//        long time = new Date().getTime();
//
//        System.out.println("总耗时 : ");
//        System.out.println(time - currentTime);
//
//	}
//
//}
