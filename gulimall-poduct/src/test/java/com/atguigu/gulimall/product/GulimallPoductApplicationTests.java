package com.atguigu.gulimall.product;

import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallPoductApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    public void contextLoads() {
//          插入操作
//        BrandEntity brandEntity=new BrandEntity();
//        brandEntity.setName("华为");
//        brandService.save(brandEntity);
//        System.out.println("...............保存成功");
//          更新操作
//        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setBrandId(1l);
//        brandEntity.setDescript("华为");
//        brandService.updateById(brandEntity);
//        System.out.println("...............更新成功");

        List<BrandEntity> entityList = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1));
        entityList.forEach((item)-> System.out.println(item));
    }

}
