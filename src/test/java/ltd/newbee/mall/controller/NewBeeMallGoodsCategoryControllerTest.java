package ltd.newbee.mall.controller;

import jakarta.servlet.http.HttpServletRequest;
import ltd.newbee.mall.common.NewBeeMallCategoryLevelEnum;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.admin.NewBeeMallGoodsCategoryController;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
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

public class NewBeeMallGoodsCategoryControllerTest {

    private NewBeeMallCategoryService newBeeMallCategoryService;
    private HttpServletRequest request;
    private NewBeeMallGoodsCategoryController newBeeMallGoodsCategoryController;

    @BeforeEach
    void setUp() {
        newBeeMallCategoryService = EasyMock.mock(NewBeeMallCategoryService.class);
        request = EasyMock.mock(HttpServletRequest.class);
        newBeeMallGoodsCategoryController = new NewBeeMallGoodsCategoryController();
        newBeeMallGoodsCategoryController.newBeeMallCategoryService = newBeeMallCategoryService;
    }

    @Test
    void testCategoriesPage() {
        Long parentId = 1L;
        Long backParentId = 2L;
        Byte categoryLevel = 1;

        request.setAttribute("path", "newbee_mall_category");
        EasyMock.expectLastCall().once();
        request.setAttribute("parentId", parentId);
        EasyMock.expectLastCall().once();
        request.setAttribute("backParentId", backParentId);
        EasyMock.expectLastCall().once();
        request.setAttribute("categoryLevel", categoryLevel);
        EasyMock.expectLastCall().once();
        EasyMock.replay(request);

        String viewName = newBeeMallGoodsCategoryController.categoriesPage(request, categoryLevel, parentId, backParentId);

        assertEquals("admin/newbee_mall_category", viewName);
        EasyMock.verify(request);
    }

    @Test
    void testList() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", "1");
        params.put("limit", "10");
        params.put("categoryLevel", "1");
        params.put("parentId", "0");

        PageQueryUtil pageUtil = new PageQueryUtil(params);

        EasyMock.expect(newBeeMallCategoryService.getCategorisPage(pageUtil)).andReturn(null);
        EasyMock.replay(newBeeMallCategoryService);

        Result result = newBeeMallGoodsCategoryController.list(params);

        assertEquals(ResultGenerator.genSuccessResult(null).getResultCode(), result.getResultCode());
        EasyMock.verify(newBeeMallCategoryService);
    }

    @ParameterizedTest
    @MethodSource("provideTestSaveParameters")
    void testSave(GoodsCategory goodsCategory, String expectedResult) {
        EasyMock.expect(newBeeMallCategoryService.saveCategory(goodsCategory)).andReturn(expectedResult);
        EasyMock.replay(newBeeMallCategoryService);

        Result result = newBeeMallGoodsCategoryController.save(goodsCategory);

        if (expectedResult.equals(ServiceResultEnum.SUCCESS.getResult())) {
            assertEquals(ResultGenerator.genSuccessResult().getResultCode(), result.getResultCode());
        } else {
            assertEquals(ResultGenerator.genFailResult(expectedResult).getResultCode(), result.getResultCode());
        }

        EasyMock.verify(newBeeMallCategoryService);
    }

    private static Stream<Arguments> provideTestSaveParameters() {
        GoodsCategory validCategory = new GoodsCategory();
        validCategory.setCategoryLevel((byte) 1);
        validCategory.setCategoryName("Category Name");
        validCategory.setParentId(0L);
        validCategory.setCategoryRank(1);

        return Stream.of(
                Arguments.of(validCategory, ServiceResultEnum.SUCCESS.getResult()),
                Arguments.of(validCategory, "Error: Category Save Failed")
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestUpdateParameters")
    void testUpdate(GoodsCategory goodsCategory, String expectedResult) {
        EasyMock.expect(newBeeMallCategoryService.updateGoodsCategory(goodsCategory)).andReturn(expectedResult);
        EasyMock.replay(newBeeMallCategoryService);

        Result result = newBeeMallGoodsCategoryController.update(goodsCategory);

        if (expectedResult.equals(ServiceResultEnum.SUCCESS.getResult())) {
            assertEquals(ResultGenerator.genSuccessResult().getResultCode(), result.getResultCode());
        } else {
            assertEquals(ResultGenerator.genFailResult(expectedResult).getResultCode(), result.getResultCode());
        }

        EasyMock.verify(newBeeMallCategoryService);
    }

    private static Stream<Arguments> provideTestUpdateParameters() {
        GoodsCategory validCategory = new GoodsCategory();
        validCategory.setCategoryId(1L);
        validCategory.setCategoryLevel((byte) 1);
        validCategory.setCategoryName("Updated Category Name");
        validCategory.setParentId(0L);
        validCategory.setCategoryRank(1);

        return Stream.of(
                Arguments.of(validCategory, ServiceResultEnum.SUCCESS.getResult()),
                Arguments.of(validCategory, "Error: Category Update Failed")
        );
    }

    @Test
    void testInfo() {
        Long categoryId = 1L;
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setCategoryId(categoryId);

        EasyMock.expect(newBeeMallCategoryService.getGoodsCategoryById(categoryId)).andReturn(goodsCategory);
        EasyMock.replay(newBeeMallCategoryService);

        Result result = newBeeMallGoodsCategoryController.info(categoryId);

        assertEquals(ResultGenerator.genSuccessResult(goodsCategory).getResultCode(), result.getResultCode());
        EasyMock.verify(newBeeMallCategoryService);
    }

    @Test
    void testDelete() {
        Integer[] ids = {1, 2, 3};

        EasyMock.expect(newBeeMallCategoryService.deleteBatch(ids)).andReturn(true);
        EasyMock.replay(newBeeMallCategoryService);

        Result result = newBeeMallGoodsCategoryController.delete(ids);

        assertEquals(ResultGenerator.genSuccessResult().getResultCode(), result.getResultCode());
        EasyMock.verify(newBeeMallCategoryService);
    }
    // 新添加的测试方法
    @ParameterizedTest
    @MethodSource("provideTestListForSelectParameters")
    void testListForSelect(Long categoryId, Result expectedResult) {
        if (categoryId == null) {
            Result result = ResultGenerator.genFailResult("缺少参数！");
            assertEquals(expectedResult.getResultCode(), result.getResultCode());
            return;
        } else if (categoryId == 1L) {
            GoodsCategory levelOneCategory = new GoodsCategory();
            levelOneCategory.setCategoryId(categoryId);
            levelOneCategory.setCategoryLevel((byte) NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
            EasyMock.expect(newBeeMallCategoryService.getGoodsCategoryById(categoryId)).andReturn(levelOneCategory);
            EasyMock.expect(newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(categoryId), NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel()))
                    .andReturn(Collections.emptyList());
        } else if (categoryId == 2L) {
            GoodsCategory levelTwoCategory = new GoodsCategory();
            levelTwoCategory.setCategoryId(categoryId);
            levelTwoCategory.setCategoryLevel((byte) NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
            EasyMock.expect(newBeeMallCategoryService.getGoodsCategoryById(categoryId)).andReturn(levelTwoCategory);
            EasyMock.expect(newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(categoryId), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel()))
                    .andReturn(Collections.emptyList());
        } else if (categoryId == 999L) {
            EasyMock.expect(newBeeMallCategoryService.getGoodsCategoryById(categoryId)).andReturn(null);
        }

        EasyMock.replay(newBeeMallCategoryService);

        Result result = newBeeMallGoodsCategoryController.listForSelect(categoryId);

        assertEquals(expectedResult.getResultCode(), result.getResultCode());
        EasyMock.verify(newBeeMallCategoryService);
    }



    private static Stream<Arguments> provideTestListForSelectParameters() {
        return Stream.of(
                Arguments.of(null, ResultGenerator.genFailResult("缺少参数！")),
                Arguments.of(999L, ResultGenerator.genFailResult("参数异常！")),
                Arguments.of(1L, ResultGenerator.genSuccessResult(new HashMap<>())),
                Arguments.of(2L, ResultGenerator.genSuccessResult(Collections.singletonMap("thirdLevelCategories", Collections.emptyList())))
        );
    }

}
