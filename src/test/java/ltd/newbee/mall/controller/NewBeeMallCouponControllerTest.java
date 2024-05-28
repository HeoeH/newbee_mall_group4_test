package ltd.newbee.mall.controller;

import jakarta.servlet.http.HttpServletRequest;
import ltd.newbee.mall.controller.admin.NewBeeMallCouponController;
import ltd.newbee.mall.entity.NewBeeMallCoupon;
import ltd.newbee.mall.service.NewBeeMallCouponService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewBeeMallCouponControllerTest {

    private NewBeeMallCouponService newBeeMallCouponService;
    private HttpServletRequest request;
    private NewBeeMallCouponController newBeeMallCouponController;

    @BeforeEach
    void setUp() {
        newBeeMallCouponService = EasyMock.mock(NewBeeMallCouponService.class);
        request = EasyMock.mock(HttpServletRequest.class);
        newBeeMallCouponController = new NewBeeMallCouponController();
        newBeeMallCouponController.newBeeMallCouponService = newBeeMallCouponService;
    }

    @Test
    void testIndex() {
        request.setAttribute("path", "newbee_mall_coupon");
        EasyMock.expectLastCall().once();
        EasyMock.replay(request);

        String viewName = newBeeMallCouponController.index(request);

        assertEquals("admin/newbee_mall_coupon", viewName);
        EasyMock.verify(request);
    }

    @Test
    void testList() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", "1");
        params.put("limit", "10");

        PageQueryUtil pageUtil = new PageQueryUtil(params);

        EasyMock.expect(newBeeMallCouponService.getCouponPage(pageUtil)).andReturn(null);
        EasyMock.replay(newBeeMallCouponService);

        Result result = newBeeMallCouponController.list(params);

        assertEquals(ResultGenerator.genSuccessResult(null).getResultCode(), result.getResultCode());
        EasyMock.verify(newBeeMallCouponService);
    }

    @ParameterizedTest
    @MethodSource("provideTestSaveParameters")
    void testSave(NewBeeMallCoupon newBeeMallCoupon, String expectedResult) {
        EasyMock.expect(newBeeMallCouponService.saveCoupon(newBeeMallCoupon)).andReturn(Boolean.valueOf(expectedResult));
        EasyMock.replay(newBeeMallCouponService);

        Result result = newBeeMallCouponController.save(newBeeMallCoupon);

        assertEquals(ResultGenerator.genDmlResult(Boolean.parseBoolean(expectedResult)).getResultCode(), result.getResultCode());
        EasyMock.verify(newBeeMallCouponService);
    }

    private static Stream<Arguments> provideTestSaveParameters() {
        return Stream.of(
                Arguments.of(new NewBeeMallCoupon(), "success"),
                Arguments.of(new NewBeeMallCoupon(), "failure")
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestUpdateParameters")
    void testUpdate(NewBeeMallCoupon newBeeMallCoupon, String expectedResult) {
        newBeeMallCoupon.setUpdateTime(new Date());
        EasyMock.expect(newBeeMallCouponService.updateCoupon(newBeeMallCoupon)).andReturn(Boolean.valueOf(expectedResult));
        EasyMock.replay(newBeeMallCouponService);

        Result result = newBeeMallCouponController.update(newBeeMallCoupon);

        assertEquals(ResultGenerator.genDmlResult(Boolean.parseBoolean(expectedResult)).getResultCode(), result.getResultCode());
        EasyMock.verify(newBeeMallCouponService);
    }

    private static Stream<Arguments> provideTestUpdateParameters() {
        return Stream.of(
                Arguments.of(new NewBeeMallCoupon(), "success"),
                Arguments.of(new NewBeeMallCoupon(), "failure")
        );
    }

    @Test
    void testInfo() {
        Long couponId = 1L;
        NewBeeMallCoupon newBeeMallCoupon = new NewBeeMallCoupon();
        newBeeMallCoupon.setCouponId(couponId);

        EasyMock.expect(newBeeMallCouponService.getCouponById(couponId)).andReturn(newBeeMallCoupon);
        EasyMock.replay(newBeeMallCouponService);

        Result result = newBeeMallCouponController.Info(couponId);

        assertEquals(ResultGenerator.genSuccessResult(newBeeMallCoupon).getResultCode(), result.getResultCode());
        EasyMock.verify(newBeeMallCouponService);
    }

    @Test
    void testDelete() {
        Long couponId = 1L;

        EasyMock.expect(newBeeMallCouponService.deleteCouponById(couponId)).andReturn(Boolean.valueOf("success"));
        EasyMock.replay(newBeeMallCouponService);

        Result result = newBeeMallCouponController.delete(couponId);

        assertEquals(ResultGenerator.genDmlResult(Boolean.parseBoolean("success")).getResultCode(), result.getResultCode());
        EasyMock.verify(newBeeMallCouponService);
    }


}
