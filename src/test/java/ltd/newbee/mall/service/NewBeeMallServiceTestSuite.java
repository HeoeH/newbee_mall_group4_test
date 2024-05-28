package ltd.newbee.mall.service;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        NewBeeMallCouponServiceTest.class,
        NewBeeMallCarouselServiceTest.class,
        NewBeeMallGoodsServiceTest.class,
        NewBeeMallCarouselServiceTest.class
})
public class NewBeeMallServiceTestSuite {
    // 这个类不需要任何代码
}