package ltd.newbee.mall.controller;

import jakarta.servlet.http.HttpServletRequest;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.admin.NewBeeMallCarouselController;
import ltd.newbee.mall.entity.Carousel;
import ltd.newbee.mall.service.NewBeeMallCarouselService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewBeeMallCarouselControllerTest {

    private NewBeeMallCarouselService newBeeMallCarouselService;
    private HttpServletRequest request;
    private NewBeeMallCarouselController newBeeMallCarouselController;

    @BeforeEach
    void setUp() {
        newBeeMallCarouselService = EasyMock.mock(NewBeeMallCarouselService.class);
        request = EasyMock.mock(HttpServletRequest.class);
        newBeeMallCarouselController = new NewBeeMallCarouselController();
        newBeeMallCarouselController.newBeeMallCarouselService = newBeeMallCarouselService;
    }

    @Test
    void testCarouselPage() {
        request.setAttribute("path", "newbee_mall_carousel");
        EasyMock.expectLastCall().once();
        EasyMock.replay(request);

        String viewName = newBeeMallCarouselController.carouselPage(request);

        assertEquals("admin/newbee_mall_carousel", viewName);
        EasyMock.verify(request);
    }

    @Test
    void testList() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", "1");
        params.put("limit", "10");

        PageQueryUtil pageUtil = new PageQueryUtil(params);

        EasyMock.expect(newBeeMallCarouselService.getCarouselPage(pageUtil)).andReturn(null);
        EasyMock.replay(newBeeMallCarouselService);

        Result result = newBeeMallCarouselController.list(params);

        assertEquals(ResultGenerator.genSuccessResult(null).getResultCode(), result.getResultCode());
        EasyMock.verify(newBeeMallCarouselService);
    }

    @ParameterizedTest
    @MethodSource("provideTestSaveParameters")
    void testSave(String carouselUrl, Integer carouselRank, String expectedResult) {
        Carousel carousel = new Carousel();
        carousel.setCarouselUrl(carouselUrl);
        carousel.setCarouselRank(carouselRank);

        if ("success".equals(expectedResult)) {
            EasyMock.expect(newBeeMallCarouselService.saveCarousel(carousel)).andReturn(ServiceResultEnum.SUCCESS.getResult());
        }

        EasyMock.replay(newBeeMallCarouselService);

        Result result = newBeeMallCarouselController.save(carousel);

        if ("success".equals(expectedResult)) {
            assertEquals(ResultGenerator.genSuccessResult().getResultCode(), result.getResultCode());
        } else {
            assertEquals(ResultGenerator.genFailResult("参数异常！").getResultCode(), result.getResultCode());
        }

        EasyMock.verify(newBeeMallCarouselService);
    }

    private static Stream<Arguments> provideTestSaveParameters() {
        return Stream.of(
                Arguments.of(null, null, "参数异常！"),
                Arguments.of("http://example.com/carousel", 1, "success")
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestUpdateParameters")
    void testUpdate(Integer carouselId, String carouselUrl, Integer carouselRank, String expectedResult) {
        Carousel carousel = new Carousel();
        carousel.setCarouselId(carouselId);
        carousel.setCarouselUrl(carouselUrl);
        carousel.setCarouselRank(carouselRank);

        if ("success".equals(expectedResult)) {
            EasyMock.expect(newBeeMallCarouselService.updateCarousel(carousel)).andReturn(ServiceResultEnum.SUCCESS.getResult());
        }

        EasyMock.replay(newBeeMallCarouselService);

        Result result = newBeeMallCarouselController.update(carousel);

        if ("success".equals(expectedResult)) {
            assertEquals(ResultGenerator.genSuccessResult().getResultCode(), result.getResultCode());
        } else {
            assertEquals(ResultGenerator.genFailResult("参数异常！").getResultCode(), result.getResultCode());
        }

        EasyMock.verify(newBeeMallCarouselService);
    }

    private static Stream<Arguments> provideTestUpdateParameters() {
        return Stream.of(
                Arguments.of(null, "", null, "参数异常！"),
                Arguments.of(null, "", 1, "参数异常！"),
                Arguments.of(null, "url", null, "参数异常！"),
                Arguments.of(null, "url", 1, "参数异常！"),
                Arguments.of(1, "", null, "参数异常！"),
                Arguments.of(1, "", 1, "参数异常！"),
                Arguments.of(1, "url", null, "参数异常！"),
                Arguments.of(1, "url", 1, "success")
        );
    }

    @Test
    void testInfo() {
        Integer carouselId = 1;
        Carousel carousel = new Carousel();
        carousel.setCarouselId(carouselId);

        EasyMock.expect(newBeeMallCarouselService.getCarouselById(carouselId)).andReturn(carousel);
        EasyMock.replay(newBeeMallCarouselService);

        Result result = newBeeMallCarouselController.info(carouselId);

        assertEquals(ResultGenerator.genSuccessResult(carousel).getResultCode(), result.getResultCode());
        EasyMock.verify(newBeeMallCarouselService);
    }

    @Test
    void testDelete() {
        Integer[] ids = {1, 2, 3};

        EasyMock.expect(newBeeMallCarouselService.deleteBatch(ids)).andReturn(true);
        EasyMock.replay(newBeeMallCarouselService);

        Result result = newBeeMallCarouselController.delete(ids);

        assertEquals(ResultGenerator.genSuccessResult().getResultCode(), result.getResultCode());
        EasyMock.verify(newBeeMallCarouselService);
    }


}
