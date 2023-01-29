package com.atguigu.gulimall.product;

import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallPoductApplicationTests {

    /**
     * 品牌实现类
     */
    @Autowired
    BrandService brandService;

    @Test
    public void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("手机");
        brandEntity.setLogo("小米");
        brandEntity.setDescript("小米手机4G");
        brandEntity.setShowStatus(1);
        brandEntity.setFirstLetter("小");
        brandEntity.setSort(1);
        boolean save = brandService.save(brandEntity);
        System.out.println("========商品插入成功=======" + save);

        //查询品牌信息数据
        List<BrandEntity> entityList = brandService.list();
        entityList.forEach((item) -> System.out.println(item));

        //根据编号查询品牌信息数据
        List<BrandEntity> brand_id = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_Id", 2));
        brand_id.forEach((item) -> System.out.println(item));


        //更新操作
        brandEntity.setBrandId(1l);
        brandEntity.setDescript("华为");
        brandService.updateById(brandEntity);
        System.out.println("...............更新成功");
    }

}
