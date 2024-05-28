package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.common.NewBeeMallCategoryLevelEnum;
import ltd.newbee.mall.exception.NewBeeMallException;
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
public class NewBeeMallGoodsServiceTest {

    @Autowired
    private NewBeeMallGoodsService newBeeMallGoodsService;

    // 提供有效参数的方法
    public static Stream<NewBeeMallGoods> validGoods() {
        return Stream.of(
                new NewBeeMallGoods(null, "商品A", "简介A", 51L, "封面A", "轮播A", 100, 90, 10, "标签A", (byte) 0, 1, new Date(), 1, new Date(), "详情A"),
                new NewBeeMallGoods(null, "商品B", "简介B", 51L, "封面B", "轮播B", 200, 180, 20, "标签B", (byte) 0, 1, new Date(), 1, new Date(), "详情B")
        );
    }

    // 提供无效参数的方法
    public static Stream<NewBeeMallGoods> invalidGoods() {
        return Stream.of(
                // 商品名称为空字符串
                new NewBeeMallGoods(null, "", "简介A", 1L, "封面A", "轮播A", 100, 90, 10, "标签A", (byte) 0, 1, new Date(), 1, new Date(), "详情A"),
                // 分类ID为null
                new NewBeeMallGoods(null, "商品C", null, 1L, "封面C", "轮播C", 100, 90, 10, "标签C", (byte) 0, 1, new Date(), 1, new Date(), "详情C"),
                // 分类ID不是三级分类
                new NewBeeMallGoods(null, "商品D", "简介D", 1L, "封面D", "轮播D", 100, 90, 10, "标签D", (byte) 0, 1, new Date(), 1, new Date(), "详情D"),
                // 商品价格为负数
                new NewBeeMallGoods(null, "商品E", "简介E", 1L, "封面E", "轮播E", -100, 90, 10, "标签E", (byte) 0, 1, new Date(), 1, new Date(), "详情E"),
                // 库存数量为负数
                new NewBeeMallGoods(null, "商品F", "简介F", 1L, "封面F", "轮播F", 100, 90, -10, "标签F", (byte) 0, 1, new Date(), 1, new Date(), "详情F")
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
    @MethodSource("validGoods")
    public void testAddValidGoods(NewBeeMallGoods goods) {
        String result = newBeeMallGoodsService.saveNewBeeMallGoods(goods);
        Assertions.assertEquals(ServiceResultEnum.SUCCESS.getResult(), result, "有效的商品应被成功添加");
    }

    @ParameterizedTest
    @MethodSource("invalidGoods")
    public void testAddInvalidGoods(NewBeeMallGoods goods) {
        String result = newBeeMallGoodsService.saveNewBeeMallGoods(goods);
        Assertions.assertNotEquals(ServiceResultEnum.SUCCESS.getResult(), result, "无效的商品不应被成功添加");
    }

    @Test
    public void updateNewBeeMallGoods_valid() {
        NewBeeMallGoods goods = new NewBeeMallGoods(10181L, "更新商品A", "更新简介A", 51L, "更新封面A", "更新轮播A", 100, 90, 10, "更新标签A", (byte) 0, 1, new Date(), 1, new Date(), "更新详情A");
//        goods.setGoodsCategoryId(51L); // 确保分类ID为三级分类
        String result = newBeeMallGoodsService.updateNewBeeMallGoods(goods);
        Assertions.assertEquals(ServiceResultEnum.SUCCESS.getResult(), result);
    }

    @Test
    public void updateNewBeeMallGoods_invalid() {
        NewBeeMallGoods goods = new NewBeeMallGoods(999L, "更新商品A", "更新简介A", 51L, "更新封面A", "更新轮播A", 100, 90, 10, "更新标签A", (byte) 0, 1, new Date(), 1, new Date(), "更新详情A"); // 不存在的goodsId
        String result = newBeeMallGoodsService.updateNewBeeMallGoods(goods);
        Assertions.assertEquals(ServiceResultEnum.DATA_NOT_EXIST.getResult(), result);
    }

    @Test
    public void getNewBeeMallGoodsById_valid() {
        NewBeeMallGoods goods = newBeeMallGoodsService.getNewBeeMallGoodsById(10003L);
        Assertions.assertNotNull(goods, "存在的ID应返回商品对象");
    }

    @Test
    public void getNewBeeMallGoodsById_invalid() {
        NewBeeMallException exception = Assertions.assertThrows(NewBeeMallException.class, () -> {
            newBeeMallGoodsService.getNewBeeMallGoodsById(999L); // 不存在的ID
        });
        Assertions.assertEquals(ServiceResultEnum.GOODS_NOT_EXIST.getResult(), exception.getMessage(), "预期的异常消息不匹配");
    }


    @Test
    public void batchUpdateSellStatus_valid() {
        Long[] ids = {10003L, 10004L, 10005L};
        boolean result = newBeeMallGoodsService.batchUpdateSellStatus(ids, 1);
        Assertions.assertTrue(result, "有效的ID数组应被成功更新销售状态");
    }

    @Test
    public void batchUpdateSellStatus_invalid() {
        Long[] ids = {0L}; // 使用一个不存在的ID
        boolean result = newBeeMallGoodsService.batchUpdateSellStatus(ids, 0);
        Assertions.assertFalse(result, "无效的ID不应被更新销售状态");
    }

}
