package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * Created by zh on 2/13/2017.!
 */
public interface SuccessKilledDao {

    /**
     * 插入购买明细, 可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

    /**
     * 根据id查询SuccessKilled并携带秒杀产品实体对象
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSecKill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

}
