package ltd.newbee.mall.controller.mall;
import ltd.newbee.mall.controller.admin.NewBeeMallOrderControllerTest;
import ltd.newbee.mall.entity.NewBeeMallOrder;
import ltd.newbee.mall.service.impl.UpdateUserInfoServiceTest_WhiteBox;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginTest_WhiteBox.class,
        LogoutTest_WhiteBox.class,
        UpdateInfoTest_WhiteBox.class,
        PayOrderTest_WhiteBox.class,
        SaveOrderTest_WhiteBox.class,
        UpdateUserInfoServiceTest_WhiteBox.class
})
public class TestAll {
}


