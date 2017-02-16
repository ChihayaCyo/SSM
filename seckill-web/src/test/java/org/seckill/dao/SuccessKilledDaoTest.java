package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by zh on 2/14/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        int insertCount=0;
        insertCount = successKilledDao.insertSuccessKilled(1001L, 18210033795L);
        System.out.println("insertCount = " + insertCount);
    }

    @Test
    public void queryByIdWithSecKill() throws Exception {
        SuccessKilled successKilled = successKilledDao.queryByIdWithSecKill(1001L,18210033795L);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }

}