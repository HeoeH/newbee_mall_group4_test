package ltd.newbee.mall.service;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.entity.GoodsCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class NewBeeMallCategoryServiceTest {

    @Autowired
    private NewBeeMallCategoryService newBeeMallCategoryService;

    // 提供有效参数的方法
    public static Stream<GoodsCategory> validCategories() {
        return Stream.of(
                new GoodsCategory(112L, (byte) 1, 0L, "分类A", 1, (byte) 0, null, null, null, null),
                new GoodsCategory(113L, (byte) 2, 1L, "分类B", 1, (byte) 0, null, null, null, null)
        );
    }

    // 提供无效参数的方法
    public static Stream<GoodsCategory> invalidCategories() {
        return Stream.of(
                // 分类名称为空字符串
                new GoodsCategory(108L, (byte) 1, 0L, "", 1, (byte) 0, null, null, null, null),
                // 分类名称长度超过允许范围
                new GoodsCategory(109L, (byte) 1, 0L, "分类E1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234", 1, (byte) 0, null, null, null, null),
                // 分类排序值为null
                new GoodsCategory(110L, (byte) 1, 0L, "分类F", 1, (byte) 0, null, null, null, null),
                // 分类名称重复
                new GoodsCategory(111L, (byte) 1, 0L, "分类A", 1, (byte) 0, null, null, null, null)
        );
    }

    @BeforeEach
    public void setUp() throws Exception {
        // 如果需要，添加初始化代码
    }

    @AfterEach
    public void tearDown() throws Exception {
        // 如果需要，添加清理代码
    }

    @ParameterizedTest
    @MethodSource("validCategories")
    public void testAddValidCategory(GoodsCategory category) {
        String result = newBeeMallCategoryService.saveCategory(category);
        Assertions.assertEquals(ServiceResultEnum.SUCCESS.getResult(), result, "有效的分类应被成功添加");
    }

    @ParameterizedTest
    @MethodSource("invalidCategories")
    public void testAddInvalidCategory(GoodsCategory category) {
        String result = newBeeMallCategoryService.saveCategory(category);
        Assertions.assertNotEquals(ServiceResultEnum.SUCCESS.getResult(), result, "无效的分类不应被成功添加");
    }

    @Test
    public void updateGoodsCategory_valid() {
        // 首先创建一个GoodsCategory对象，确保其存在于数据库中
        GoodsCategory category = new GoodsCategory(100L, (byte) 3, 83L, "沙发发", 1, (byte) 0, new Date(), 1001, new Date(), 1001);
        // 更新分类
        category.setCategoryName("更新分类名A");
        category.setCategoryRank(0);
        String result = newBeeMallCategoryService.updateGoodsCategory(category);
        Assertions.assertEquals(ServiceResultEnum.SUCCESS.getResult(), result);

    }



    @Test
    public void updateGoodsCategory_invalid() {
        GoodsCategory category = new GoodsCategory(999L, (byte) 1, 0L, "更新分类A", 1, (byte) 0, null, null, null, null); // 不存在的categoryId
        String result = newBeeMallCategoryService.updateGoodsCategory(category);
        Assertions.assertEquals(ServiceResultEnum.DATA_NOT_EXIST.getResult(), result);
    }

    @Test
    public void getGoodsCategoryById_valid() {
        GoodsCategory category = newBeeMallCategoryService.getGoodsCategoryById(15L);
        Assertions.assertNotNull(category, "存在的ID应返回分类对象");
    }

    @Test
    public void getGoodsCategoryById_invalid() {
        GoodsCategory category = newBeeMallCategoryService.getGoodsCategoryById(999L); // 不存在的ID
        Assertions.assertNull(category, "不存在的ID应返回null");
    }

    @Test
    public void deleteBatch_valid() {
        Integer[] ids = {15, 16, 17};
        Boolean result = newBeeMallCategoryService.deleteBatch(ids);
        Assertions.assertTrue(result, "有效的ID数组应被成功删除");
    }

    @Test
    public void deleteBatch_invalid() {
        Integer[] ids = {};
        Boolean result = newBeeMallCategoryService.deleteBatch(ids);
        Assertions.assertFalse(result, "空的ID数组不应被删除");
    }
}
