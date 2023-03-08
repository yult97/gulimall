package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.enums.PrizeAttrType;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.product.dao.AttrGroupDao;
import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.AttrRspVO;
import com.atguigu.gulimall.product.vo.AttrVO;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.atguigu.gulimall.product.dao.AttrDao;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 属性实现类
 *
 * @author yubo
 */
@Service("attrService")
@Slf4j
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {
    /**
     * 参数维护信息和属性关联实现类
     */
    @Autowired
    private AttrAttrgroupRelationServiceImpl attrAttrgroupRelationService;

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private AttrGroupDao attrGroupDao;


    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据分类维护编号查询规格参数信息
     *
     * @param params
     * @param catelogId
     * @param type
     * @return
     */
    @Override
    public PageUtils queryPageAttr(Map<String, Object> params, Long catelogId, String type) {
        log.info("==================商品属性查询信息{},类型设置{}================", catelogId, type);
        //获取参数值
        String key = (String) params.get("key");
        QueryWrapper<AttrEntity> attrEntityQueryWrapper = new QueryWrapper<AttrEntity>()
                .eq("attr_type", type.equalsIgnoreCase(PrizeAttrType.BaseType.getTypeName()) ? PrizeAttrType.BaseType.getCode() : PrizeAttrType.SaleType.getCode());
        if (catelogId != 0) {
            attrEntityQueryWrapper.eq("catelog_Id", catelogId);
        }
        if (!StringUtils.isEmpty(key)) {
            attrEntityQueryWrapper.and((attrWarp) -> {
                attrWarp.eq("attr_id", key).or().like("attr_name", key);
            });
        }

        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), attrEntityQueryWrapper);
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        log.info("分页查询数据信息{}", records);
        List<AttrRspVO> list = records.stream().map((attrEntiy) -> {
            AttrRspVO attrRspVO = new AttrRspVO();
            BeanUtils.copyProperties(attrEntiy, attrRspVO);
            //判断属性类型是否为基础属性类型，只有基础属性类型才会存储
            if (type.equalsIgnoreCase(PrizeAttrType.BaseType.getTypeName())) {
                AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntiy.getAttrId()));
                log.info("属性关联信息{}", attrAttrgroupRelationEntity);
                if (!StringUtils.isEmpty(attrAttrgroupRelationEntity)) {
                    //根据商品属性id查询属性信息,使用关联表中分组id进行查询
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
                    log.info("获取{}商品属性信息{}", attrEntiy.getAttrId(), attrGroupEntity);
                    if (!StringUtils.isEmpty(attrGroupEntity)) {
                        attrRspVO.setAttrGroupName(attrGroupEntity.getAttrGroupName());
                    }
                }

            }
            //根据分类id查询分类信息
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntiy.getCatelogId());
            log.info("获取{}分类信息{}", attrEntiy.getCatelogId(), categoryEntity);
            if (!StringUtils.isEmpty(categoryEntity)) {
                attrRspVO.setCatelogName(categoryEntity.getName());
            }
            return attrRspVO;
        }).collect(Collectors.toList());
        pageUtils.setList(list);
        return pageUtils;
    }

    /**
     * 规格参数信息保存及关联属性信息保存
     *
     * @param attr
     */
    @Override
    public void saveAttr(AttrVO attr) {
        AttrEntity attrEntity = new AttrEntity();
        //将属性信息值赋值给属性信息实体类
        BeanUtils.copyProperties(attr, attrEntity);
        this.save(attrEntity);
        //基础属性信息时,才会存储属性分组和属性关联表
        if (attr.getAttrType().equals(PrizeAttrType.BaseType.getTypeName())) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            //赋值属性id 为分组维护中编号
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
            //赋值属性分组id 为分组关联表分组id
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            attrAttrgroupRelationService.save(attrAttrgroupRelationEntity);
        }


    }

    /**
     * 根据规则参数id查询详情
     *
     * @param attrId
     * @return
     */
    @Override
    public AttrRspVO infoById(Long attrId) {
        AttrRspVO attrRspVO = new AttrRspVO();
        //根据属性id查询商品属性信息
        AttrEntity attrEntity = this.getById(attrId);
        //将商品属性信息赋值给定义 VO 字段值
        BeanUtils.copyProperties(attrEntity, attrRspVO);
        //根据属性id查询属性和属性分组关联关系信息
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
        log.info("===========获取关联关系信息{}========", attrAttrgroupRelationEntity);
        if (attrAttrgroupRelationEntity != null) {
            //根据属性分组id查询属性分组信息
            AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
            if (attrGroupEntity != null) {
                //赋值属性分组id
                attrRspVO.setAttrGroupId(attrAttrgroupRelationEntity.getAttrGroupId());
                //赋值属性分组名称
                attrRspVO.setAttrGroupName(attrGroupEntity.getAttrGroupName());
            }
        }
        //所属分类信息显示
        Long catelogId = attrEntity.getCatelogId();
        //查询分类维护路径信息 例如:手机->手机通讯->手机
        Long[] catelogPath = categoryService.findCatelogPath(catelogId);
        attrRspVO.setCatelogPath(catelogPath);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if (categoryEntity != null) {
            attrRspVO.setCatelogName(categoryEntity.getName());
        }
        return attrRspVO;
    }

    /**
     * 更新规格参数属性值
     *
     * @param attr
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAttr(AttrVO attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        //将基本属性值进行更新
        this.updateById(attrEntity);
        //基础属性信息时,才会存储属性分组和属性关联表
        if (attr.getAttrType().equals(PrizeAttrType.BaseType.getTypeName())) {
            //修改规则参数属性关联值
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            //赋值最新规格参数分组值
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            //赋值最新属性分组值
            attrAttrgroupRelationEntity.setAttrId(attr.getAttrId());
            //查询规则参数属性是否存在关联值
            int count = attrAttrgroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            if (count > 0) {
                attrAttrgroupRelationDao.update(attrAttrgroupRelationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            } else {
                attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
            }
        }

    }

    /**
     * 查询属性分组关联关系
     *
     * @param params
     * @param attrGroupId
     * @return
     */
    @Override
    public PageUtils queryGroupRelation(Map<String, Object> params, Long attrGroupId) {
        //获取属性分组信息
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
        log.info("查询属性分组信息为{}", attrGroupEntity);
        Long catelogId = attrGroupEntity.getCatelogId();
        log.info("获取属性分组中分类编码{}", catelogId);
        //根据分类id获取属性分组信息
        List<AttrGroupEntity> attrEntityList = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        log.info("查询属性信息为{}", attrEntityList);
        List<Long> list = attrEntityList.stream().map((attrGroup) -> {
            return attrGroup.getAttrGroupId();
        }).collect(Collectors.toList());
        List<AttrAttrgroupRelationEntity> attrgroupRelationEntityList = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", list));
        List<Long> attrIds = attrgroupRelationEntityList.stream().map((attrRel) -> {
            return attrRel.getAttrId();
        }).collect(Collectors.toList());
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId).eq("attr_type", PrizeAttrType.BaseType.getCode());
        if (attrIds.size() > 0) {
            queryWrapper.notIn("attr_id", attrIds);
        }
        //判断是否有参数进行模糊查询
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and((w) -> {
                w.eq("attr_id", key).or().like("attr_name", key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);
        return new PageUtils(page);
    }

}