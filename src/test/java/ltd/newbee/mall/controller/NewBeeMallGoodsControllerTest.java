package ltd.newbee.mall.controller;

import jakarta.servlet.http.HttpServletRequest;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.admin.NewBeeMallGoodsController;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewBeeMallGoodsControllerTest {

    private NewBeeMallGoodsService newBeeMallGoodsService;
    private NewBeeMallCategoryService newBeeMallCategoryService;
    private HttpServletRequest request;
    private NewBeeMallGoodsController newBeeMallGoodsController;

    @BeforeEach
    void setUp() {
        newBeeMallGoodsService = EasyMock.mock(NewBeeMallGoodsService.class);
        newBeeMallCategoryService = EasyMock.mock(NewBeeMallCategoryService.class);
        request = EasyMock.mock(HttpServletRequest.class);
        newBeeMallGoodsController = new NewBeeMallGoodsController();
        newBeeMallGoodsController.newBeeMallGoodsService = newBeeMallGoodsService;
        newBeeMallGoodsController.newBeeMallCategoryService = newBeeMallCategoryService;
    }

    @Test
    void testGoodsPage() {
        request.setAttribute("path", "newbee_mall_goods");
        EasyMock.expectLastCall().once();
        EasyMock.replay(request);

        String viewName = newBeeMallGoodsController.goodsPage(request);

        assertEquals("admin/newbee_mall_goods", viewName);
        EasyMock.verify(request);
    }

    @Test
    void testEdit() {
        List<GoodsCategory> firstLevelCategories = new ArrayList<>();
        GoodsCategory firstLevelCategory = new GoodsCategory();
        firstLevelCategory.setCategoryId(1L);
        firstLevelCategories.add(firstLevelCategory);

        List<GoodsCategory> secondLevelCategories = new ArrayList<>();
        GoodsCategory secondLevelCategory = new GoodsCategory();
        secondLevelCategory.setCategoryId(2L);
        secondLevelCategories.add(secondLevelCategory);

        List<GoodsCategory> thirdLevelCategories = new ArrayList<>();
        GoodsCategory thirdLevelCategory = new GoodsCategory();
        thirdLevelCategory.setCategoryId(3L);
        thirdLevelCategories.add(thirdLevelCategory);

        EasyMock.expect(newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), 1))
                .andReturn(firstLevelCategories);
        EasyMock.expect(newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(1L), 2))
                .andReturn(secondLevelCategories);
        EasyMock.expect(newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(2L), 3))
                .andReturn(thirdLevelCategories);
        EasyMock.replay(newBeeMallCategoryService);

        String viewName = newBeeMallGoodsController.edit(request);

        assertEquals("admin/newbee_mall_goods_edit", viewName);
        EasyMock.verify(newBeeMallCategoryService);
    }

    @Test
    void testList() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", "1");
        params.put("limit", "10");

        PageQueryUtil pageUtil = new PageQueryUtil(params);

        EasyMock.expect(newBeeMallGoodsService.getNewBeeMallGoodsPage(pageUtil)).andReturn(null);
        EasyMock.replay(newBeeMallGoodsService);

        Result result = newBeeMallGoodsController.list(params);

        assertEquals(ResultGenerator.genSuccessResult(null).getResultCode(), result.getResultCode());
        EasyMock.verify(newBeeMallGoodsService);
    }

    @ParameterizedTest
    @MethodSource("provideTestSaveParameters")
    void testSave(NewBeeMallGoods newBeeMallGoods, String expectedResult) {
        EasyMock.expect(newBeeMallGoodsService.saveNewBeeMallGoods(EasyMock.eq(newBeeMallGoods))).andReturn(expectedResult);
        EasyMock.replay(newBeeMallGoodsService);

        Result result = newBeeMallGoodsController.save(newBeeMallGoods);

        if (expectedResult.equals(ServiceResultEnum.SUCCESS.getResult())) {
            assertEquals(ResultGenerator.genSuccessResult().getResultCode(), result.getResultCode());
        } else {
            assertEquals(ResultGenerator.genFailResult(expectedResult).getResultCode(), result.getResultCode());
        }

        EasyMock.verify(newBeeMallGoodsService);
    }

    private static Stream<Arguments> provideTestSaveParameters() {
        NewBeeMallGoods validGoods = new NewBeeMallGoods();
        validGoods.setGoodsId(1L);
        validGoods.setGoodsName("Test Goods");
        validGoods.setGoodsIntro("Intro");
        validGoods.setGoodsCategoryId(1L);
        validGoods.setGoodsCoverImg("img.png");
        validGoods.setGoodsCarousel("carousel.png");
        validGoods.setOriginalPrice(100);
        validGoods.setSellingPrice(80);
        validGoods.setStockNum(10);
        validGoods.setTag("Tag");
        validGoods.setGoodsSellStatus((byte) 0);
        validGoods.setCreateUser(1);
        validGoods.setCreateTime(new Date());
        validGoods.setUpdateUser(1);
        validGoods.setUpdateTime(new Date());
        validGoods.setGoodsDetailContent("Details");

        return Stream.of(
                Arguments.of(validGoods, ServiceResultEnum.SUCCESS.getResult()),
                Arguments.of(validGoods, "Error: Goods Update Failed")
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestUpdateParameters")
    void testUpdate(NewBeeMallGoods newBeeMallGoods, String expectedResult) {
        EasyMock.expect(newBeeMallGoodsService.updateNewBeeMallGoods(EasyMock.eq(newBeeMallGoods))).andReturn(expectedResult);
        EasyMock.replay(newBeeMallGoodsService);

        Result result = newBeeMallGoodsController.update(newBeeMallGoods);

        if (expectedResult.equals(ServiceResultEnum.SUCCESS.getResult())) {
            assertEquals(ResultGenerator.genSuccessResult().getResultCode(), result.getResultCode());
        } else {
            assertEquals(ResultGenerator.genFailResult(expectedResult).getResultCode(), result.getResultCode());
        }

        EasyMock.verify(newBeeMallGoodsService);
    }

    private static Stream<Arguments> provideTestUpdateParameters() {
        NewBeeMallGoods validGoods = new NewBeeMallGoods();
        validGoods.setGoodsId(1L);
        validGoods.setGoodsName("Updated Goods");
        validGoods.setGoodsIntro("Updated Intro");
        validGoods.setGoodsCategoryId(2L);
        validGoods.setGoodsCoverImg("updated_img.png");
        validGoods.setGoodsCarousel("updated_carousel.png");
        validGoods.setOriginalPrice(120);
        validGoods.setSellingPrice(90);
        validGoods.setStockNum(15);
        validGoods.setTag("Updated Tag");
        validGoods.setGoodsSellStatus((byte) 1);
        validGoods.setCreateUser(1);
        validGoods.setCreateTime(new Date());
        validGoods.setUpdateUser(2);
        validGoods.setUpdateTime(new Date());
        validGoods.setGoodsDetailContent("Updated Details");

        return Stream.of(
                Arguments.of(validGoods, ServiceResultEnum.SUCCESS.getResult()),
                Arguments.of(validGoods, "Error: Goods Update Failed")
        );
    }

    @Test
    void testInfo() {
        Long goodsId = 1L;
        NewBeeMallGoods newBeeMallGoods = new NewBeeMallGoods();
        newBeeMallGoods.setGoodsId(goodsId);

        EasyMock.expect(newBeeMallGoodsService.getNewBeeMallGoodsById(goodsId)).andReturn(newBeeMallGoods);
        EasyMock.replay(newBeeMallGoodsService);

        Result result = newBeeMallGoodsController.info(goodsId);

        assertEquals(ResultGenerator.genSuccessResult(newBeeMallGoods).getResultCode(), result.getResultCode());
        EasyMock.verify(newBeeMallGoodsService);
    }

    @Test
    void testDelete() {
        Long[] ids = {1L};
        int sellStatus = 0;

        EasyMock.expect(newBeeMallGoodsService.batchUpdateSellStatus(ids, sellStatus)).andReturn(true);
        EasyMock.replay(newBeeMallGoodsService);

        Result result = newBeeMallGoodsController.delete(ids, sellStatus);

        assertEquals(ResultGenerator.genSuccessResult().getResultCode(), result.getResultCode());
        EasyMock.verify(newBeeMallGoodsService);
    }
}
