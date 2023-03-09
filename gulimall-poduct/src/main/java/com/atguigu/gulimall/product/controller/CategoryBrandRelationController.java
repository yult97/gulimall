package com.atguigu.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.vo.CategoryBrandVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.product.entity.CategoryBrandRelationEntity;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;


/**
 * 品牌分类关联
 *
 * @author yubo
 * @email 1904282367@gmail.com
 * @date 2021-12-18 23:49:40
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 列表
     * 总结  @RequestParam 和 @PathVariable 区别
     * 以及 GetMapping 注解中拼接路径参数
     *
     * @author yubo
     */
    @GetMapping("/catelog/list")
    public R categorylist(@RequestParam("brandId") Long brandId) {
        List<CategoryBrandRelationEntity> categoryBrandRelationEntityList = categoryBrandRelationService.selectDetail(brandId);

        //List<CategoryBrandRelationEntity> brand_id = categoryBrandRelationService.list(new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));

        return R.ok().put("data", categoryBrandRelationEntityList);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.saveDetail(categoryBrandRelation);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * @return
     * @throws
     * @title
     * @description 根据分类id查询品牌信息
     * @author yubo
     * @updateTime 2023-03-09 22:12
     */
    @GetMapping("brands/list")
    public R list(@RequestParam("catId") long catId) {
        //查询商品分类品牌关联的实体类信息
        List<BrandEntity> brandEntityList = categoryBrandRelationService.selectBrandsList(catId);
        List<CategoryBrandVO> brandVOList = brandEntityList.stream().map(item -> {
            CategoryBrandVO categoryBrandVO = new CategoryBrandVO();
            categoryBrandVO.setBrandId(item.getBrandId());
            categoryBrandVO.setBrandName(item.getName());
            return categoryBrandVO;
        }).collect(Collectors.toList());

        return R.ok().put("data", brandVOList);
    }

}
