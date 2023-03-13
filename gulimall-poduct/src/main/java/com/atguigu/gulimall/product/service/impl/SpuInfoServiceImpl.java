package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.to.BoundsTo;
import com.atguigu.common.to.SkuReductionTo;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.product.entity.*;
import com.atguigu.gulimall.product.feign.CouponFeignService;
import com.atguigu.gulimall.product.service.*;
import com.atguigu.gulimall.product.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;


@Service("spuInfoService")
@Slf4j
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    SpuInfoDescService spuInfoDescService;

    @Autowired
    SpuImagesService spuImagesService;

    @Autowired
    ProductAttrValueService productAttrValueService;

    @Autowired
    SkuInfoService saveSkuInfo;

    @Autowired
    SkuImagesService skuImagesService;

    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    CouponFeignService couponFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * @throws
     * @title saveSpuInfo
     * @description 保存商品信息
     * @author yubo
     * @updateTime 2023-03-12 23:04
     * @Return void
     * @Param [spuVo]
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSpuInfo(SpuSaveVo spuVo) {
        log.info("保存商品信息{}", spuVo);
        //保存spu基本信息  pms_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuVo, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.SpuInfoSave(spuInfoEntity);

        //保存spu图片信息  pms_spu_images
        Long id = spuInfoEntity.getId();
        List<String> images = spuVo.getImages();
        spuImagesService.saveImages(id, images);

        //保存spu详细信息  pms_spu_info_desc
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(String.join(",", spuVo.getImages()));
        spuInfoDescService.saveDesc(spuInfoDescEntity);
        log.info("保存spu图片信息、spu基本信息、spu详细信息成功");
        //保存BaseAttrs信息 pms_product_attr_value
        List<BaseAttrs> baseAttrs = spuVo.getBaseAttrs();
        if (baseAttrs.size() > 0) {
            productAttrValueService.saveBaseAttrs(baseAttrs, id);
        }
        //保存spu积分信息
        BoundsTo boundsTo = new BoundsTo();
        Bounds bounds = spuVo.getBounds();
        BeanUtils.copyProperties(bounds, boundsTo);
        boundsTo.setSpuId(spuInfoEntity.getId());
        R r1 = couponFeignService.saveBoundsInfo(boundsTo);
        if (r1.getCode() != 0) {
            log.error("远程保存spu积分信息失败");
        }
        //保存当前spu对应的sku信息
        // Skus 中包含信息
        List<Skus> skus = spuVo.getSkus();
        //判断 skus 集合是否为空
        if (skus.size() > 0 || skus != null) {
            skus.stream().forEach(item -> {
                String defaultImg = "";
                //获取默认图片赋值
                for (Images image : item.getImages()
                ) {
                    if (image.getDefaultImg() == 1) {
                        defaultImg = image.getImgUrl();
                    }
                }
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(item, skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);
                saveSkuInfo.saveSkuInfo(skuInfoEntity);

                //获取sku编号
                Long skuId = skuInfoEntity.getSkuId();
                List<SkuImagesEntity> skuImagesEntities = item.getImages().stream().map(image -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setImgUrl(image.getImgUrl());
                    skuImagesEntity.setDefaultImg(image.getDefaultImg());
                    return skuImagesEntity;
                }).collect(Collectors.toList());
                //保存sku图片信息
                skuImagesService.saveSkuImages(skuImagesEntities);

                //sku的销售属性：pms_sku_sale_attr_value
                List<Attr> attr = item.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attr.stream().map(a -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(a, skuSaleAttrValueEntity);
                    skuSaleAttrValueEntity.setSkuId(skuId);
                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());

                //sku销售属性保存
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);


                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item, skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(BigDecimal.ZERO) == 1) {
                    R r = couponFeignService.saveSkuReduction(skuReductionTo);
                    if (r.getCode() != 0) {
                        log.error("远程保存sku积分信息失败");
                    }
                }
            });
        }
    }

    /**
     * @throws
     * @title SpuInfoSave
     * @description 保存spu基本信息  pms_spu_info
     * @author yubo
     * @updateTime 2023-03-12 23:33
     * @Return void
     * @Param [spuInfoEntity]
     */
    private void SpuInfoSave(SpuInfoEntity spuInfoEntity) {
        this.save(spuInfoEntity);
    }

}