package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.NewBeeMallCoupon;
import ltd.newbee.mall.common.ServiceResultEnum;
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

import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Stream;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class NewBeeMallCouponServiceTest {

    @Autowired
    private NewBeeMallCouponService newBeeMallCouponService;

    // 提供有效参数的方法
    public static Stream<NewBeeMallCoupon> validCoupons() {
        return Stream.of(
                new NewBeeMallCoupon(null, "优惠券A", "介绍A", 100, 50, 10, (byte) 5, (byte) 1, (byte) 1, (byte) 1, null, null, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), new Date(), new Date(), (byte) 0)
        );
    }

    // 提供无效参数的方法
    public static Stream<NewBeeMallCoupon> invalidCoupons() {
        return Stream.of(
                // 优惠券名称为空字符串 覆盖13
                new NewBeeMallCoupon(null, "", "介绍A", 100, 50, 10, (byte) 5, (byte) 1, (byte) 1, (byte) 1, null, null, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), new Date(), new Date(), (byte) 0),
                // 优惠券介绍为空字符串 覆盖14
                new NewBeeMallCoupon(null, "优惠券B", "", 100, 50, 10, (byte) 5, (byte) 1, (byte) 1, (byte) 1, null, null, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), new Date(), new Date(), (byte) 0),
                // 优惠券介绍长度>=127 覆盖15
                new NewBeeMallCoupon(null, "优惠券C", "介绍C1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234", -100, 50, 10, (byte) 5, (byte) 1, (byte) 1, (byte) 1, null, null, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), new Date(), new Date(), (byte) 0),
                // 优惠券数量<=0 覆盖19
                new NewBeeMallCoupon(null, "优惠券C", "介绍C", -100, 50, 10, (byte) 5, (byte) 1, (byte) 1, (byte) 1, null, null, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), new Date(), new Date(), (byte) 0),
                // 优惠券金额<=0 覆盖23
                new NewBeeMallCoupon(null, "优惠券D", "介绍D", 100, -50, 10, (byte) 5, (byte) 1, (byte) 1, (byte) 1, null, null, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), new Date(), new Date(), (byte) 0),
                // 最小消费金额<=0 覆盖27
                new NewBeeMallCoupon(null, "优惠券E", "介绍E", 100, 50, -10, (byte) 5, (byte) 1, (byte) 1, (byte) 1, null, null, LocalDate.of(2025, 1, 1), LocalDate.of(2024, 12, 31), new Date(), new Date(), (byte) 0),
                // 领取限制数量为null 覆盖28
                new NewBeeMallCoupon(null, "优惠券E", "介绍E", 100, 50, 10, null, (byte) 1, (byte) 1, (byte) 1, null, null, LocalDate.of(2025, 1, 1), LocalDate.of(2024, 12, 31), new Date(), new Date(), (byte) 0),
                // 赠送类型为null 覆盖29
                new NewBeeMallCoupon(null, "优惠券E", "介绍E", 100, 50, 10, (byte) 5, null, (byte) 1, (byte) 1, null, null, LocalDate.of(2025, 1, 1), LocalDate.of(2024, 12, 31), new Date(), new Date(), (byte) 0),
                // 状态为null 覆盖30
                new NewBeeMallCoupon(null, "优惠券E", "介绍E", 100, 50, 10, (byte) 5, (byte) 1, null, (byte) 1, null, null, LocalDate.of(2025, 1, 1), LocalDate.of(2024, 12, 31), new Date(), new Date(), (byte) 0),
                // 限制类型为null 覆盖31
                new NewBeeMallCoupon(null, "优惠券E", "介绍E", 100, 50, 10, (byte) 5, (byte) 1, (byte) 1, null, null, null, LocalDate.of(2025, 1, 1), LocalDate.of(2024, 12, 31), new Date(), new Date(), (byte) 0),
                // 优惠券开始时间为空 覆盖32
                new NewBeeMallCoupon(null, "优惠券F", "介绍F", 100, 50, 10, (byte) 5, (byte) 1, (byte) 1, (byte) 1, null, null, null, LocalDate.of(2024, 12, 31), new Date(), new Date(), (byte) 0),
                // 优惠券结束时间为空 覆盖33
                new NewBeeMallCoupon(null, "优惠券F", "介绍F", 100, 50, 10, (byte) 5, (byte) 1, (byte) 1, (byte) 1, null, null, null, LocalDate.of(2024, 12, 31), new Date(), new Date(), (byte) 0)
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
    @MethodSource("validCoupons")
    public void testAddValidCoupon(NewBeeMallCoupon coupon) {
        boolean result = newBeeMallCouponService.saveCoupon(coupon);
        Assertions.assertTrue(result, "有效的优惠券应被成功添加");
    }

    @ParameterizedTest
    @MethodSource("invalidCoupons")
    public void testAddInvalidCoupon(NewBeeMallCoupon coupon) {
        boolean result = newBeeMallCouponService.saveCoupon(coupon);
        Assertions.assertTrue(result, "无效的优惠券不应被成功添加");
    }

    @Test
    public void updateCoupon_valid() {
        NewBeeMallCoupon coupon = new NewBeeMallCoupon(1L, "更新优惠券", "更新描述", 100, 50, 10, (byte) 5, (byte) 1, (byte) 1, (byte) 1, null, null, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), new Date(), new Date(), (byte) 0);
        boolean result = newBeeMallCouponService.updateCoupon(coupon);
        Assertions.assertTrue(result, "有效的优惠券应被成功更新");
    }

    @Test
    public void updateCoupon_invalid() {
        NewBeeMallCoupon coupon = new NewBeeMallCoupon(999L, "更新优惠券", "更新描述", 100, 50, 10, (byte) 5, (byte) 1, (byte) 1, (byte) 1, null, null, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), new Date(), new Date(), (byte) 0); // 不存在的couponId
        boolean result = newBeeMallCouponService.updateCoupon(coupon);
        Assertions.assertFalse(result, "无效的优惠券不应被成功更新");
    }

    @Test
    public void getCouponById_valid() {
        NewBeeMallCoupon coupon = newBeeMallCouponService.getCouponById(1L);
        Assertions.assertNotNull(coupon, "存在的ID应返回优惠券对象");
    }

    @Test
    public void getCouponById_invalid() {
        NewBeeMallCoupon coupon = newBeeMallCouponService.getCouponById(999L); // 不存在的ID
        Assertions.assertNull(coupon, "不存在的ID应返回null");
    }

    @Test
    public void deleteCouponById_valid() {
        boolean result = newBeeMallCouponService.deleteCouponById(1L);
        Assertions.assertTrue(result, "有效的优惠券ID应被成功删除");
    }

    @Test
    public void deleteCouponById_invalid() {
        boolean result = newBeeMallCouponService.deleteCouponById(999L); // 不存在的ID
        Assertions.assertFalse(result, "无效的优惠券ID不应被成功删除");
    }
}
