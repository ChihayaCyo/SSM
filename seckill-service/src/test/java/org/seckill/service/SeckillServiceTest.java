package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zh on 2/14/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list = {}",list);
    }

    @Test
    public void getById() throws Exception {
        Seckill seckill = seckillService.getById(1000L);
        logger.info("seckill = {}",seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        Exposer exposer = seckillService.exportSeckillUrl(1000L);
        logger.info("exposer = {}",exposer);
//        exposed=true,
//        md5='aa976e30099e889adddb51fab98e621f',
//        seckillId=1000,
    }

    @Test
    public void executeSeckill() throws Exception {
        String md5 = "aa976e30099e889adddb51fab98e621f";
        try {
            SeckillExecution execution = seckillService.executeSeckill(1000L,12345678901L,md5);
            logger.info("execution = {}",execution);
        } catch (RepeatKillException e1) {
            logger.error(e1.getMessage());
        } catch (SeckillCloseException e2) {
            logger.error(e2.getMessage());
        }
    }

    //测试代码完整逻辑, 可重复性执行(会回滚,同时通过代码测试)
    @Test
    public void testSeckillLogic() throws Exception{
        long seckillId = 1002;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
            logger.info("exposer = {}",exposer);
            String md5 = exposer.getMd5();
            try {
                SeckillExecution execution = seckillService.executeSeckill(seckillId,12345678901L,md5);
                logger.info("execution = {}",execution);
            } catch (RepeatKillException e1) {
                logger.error(e1.getMessage());
            } catch (SeckillCloseException e2) {
                logger.error(e2.getMessage());
            }
        }else {
            logger.info("exposer = {}",exposer);
        }
    }

}