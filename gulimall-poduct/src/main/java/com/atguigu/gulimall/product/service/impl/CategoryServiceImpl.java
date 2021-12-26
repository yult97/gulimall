package com.atguigu.gulimall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //查出所有分类
        List<CategoryEntity> categoryEntityList = baseMapper.selectList(null);
        //组装成树形结构
        //找到所有一级分类
        //根据stream 数据流,在查出所有菜单集合中，过滤出一级菜单数据，放入至集合当中
        List<CategoryEntity> levelMeuns = categoryEntityList.stream().filter((categoryEntity) ->
                        categoryEntity.getParentCid() == 0
                ).map((menu) -> {
                    menu.setChildren(getChildrens(menu,categoryEntityList));
                    return menu;
                }).sorted((menu1, menu2) ->
                     (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort())
                ).collect(Collectors.toList());
        return levelMeuns;
    }

    /**
     * 查询所有子菜单名称
     * @param root
     * @param categoryEntityList
     * @return
     */
    private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> categoryEntityList) {
        List<CategoryEntity> children = categoryEntityList.stream().filter(categoryEntity -> {
                    return categoryEntity.getParentCid() == root.getCatId();
                }).map(categoryEntity -> {
                    //递归查找子菜单信息
                    categoryEntity.setChildren(getChildrens(categoryEntity, categoryEntityList));
                    return categoryEntity;
                }).sorted((menu1, menu2) ->
                    //菜单排序
                     (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort())
                ).collect(Collectors.toList());
        return children;
    }

}