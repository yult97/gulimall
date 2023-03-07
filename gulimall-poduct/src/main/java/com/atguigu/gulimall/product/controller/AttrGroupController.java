package com.atguigu.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.AttrRelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.service.AttrGroupService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;


/**
 * 属性分组
 *
 * @author yubo
 * @email 1904282367@gmail.com
 * @date 2021-12-18 23:49:40
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 属性分组分页查询
     *
     * @param params
     * @param catelogId
     * @return
     * @author yubo
     */
    @RequestMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("catelogId") Long catelogId) {

        PageUtils page = attrGroupService.queryPage(params, catelogId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        //根据分类id查询属性id
        Long catelogId = attrGroup.getCatelogId();

        //查询分类id全路径信息
        Long[] catelogPath = categoryService.findCatelogPath(catelogId);

        //将查询完成路径放入至属性维护实体类中
        attrGroup.setCatelogPath(catelogPath);

        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

    /**
     * 属性分组分页查询
     *
     * @param params
     * @param attrGroupId
     * @return
     * @author yubo
     */
    @RequestMapping("{attrGroupId}/noattr/relation")
    public R groupRelation(@RequestParam Map<String, Object> params, @PathVariable("attrGroupId") Long attrGroupId) {

        PageUtils pageUtils = attrGroupService.queryGroupRelation(params, attrGroupId);

        return R.ok();
    }

    /**
     * 根据属性分组编号获取属性分组的关联的所有属性
     * @param attrgroupId
     * @return
     */
    @GetMapping("{attrgroupId}/attr/relation")
    public R attrGroupRelation(@PathVariable("attrgroupId") Long attrgroupId) {
        List<AttrEntity> attrGroupList=attrGroupService.attrGroupRelation(attrgroupId);
        return R.ok().put("data",attrGroupList);
    }

    @PostMapping("/attr/relation/delete")
    public R relationDelete(@RequestBody AttrRelationVO[] attrRelationVOS) {
        attrGroupService.relationDelete(attrRelationVOS);
        return R.ok();
    }

}
