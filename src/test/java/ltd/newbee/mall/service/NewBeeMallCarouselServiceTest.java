package ltd.newbee.mall.service;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.entity.Carousel;
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

import jakarta.annotation.Resource;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class NewBeeMallCarouselServiceTest {

    @Resource
    private NewBeeMallCarouselService newBeeMallCarouselService;

    // 提供参数的方法
    public static Stream<Carousel> validData() {
        return Stream.of(
                new Carousel(1, "http://example.com/carousel1.jpg", "http://example.com/page1", 1, (byte) 0, new Date(), 1001, new Date(), 1001)
        );
    }

    public static Stream<Carousel> invalidData() {
        return Stream.of(
                // 上传轮播图文件为空，覆盖等价类4
                new Carousel(6, "", "http://example.com/page1", 1, (byte) 0, new Date(), 1001, new Date(), 1001),
                // 跳转链接为空，覆盖等价类5
                new Carousel(7, "http://example.com/carousel1.jpg", "", 1, (byte) 0, new Date(), 1001, new Date(), 1001),
                // 跳转链接长度>=100，覆盖等价类6
                new Carousel(8, "http://example.com/carousel1.jpg", "http://example.com/page11111111111111111111111111111111111111111111111111111111111111111111111111111", 1, (byte) 0, new Date(), 1001, new Date(), 1001),
                // 排序值<0 覆盖等价类7
                new Carousel(9, "http://example.com/carousel1.jpg", "http://example.com/page1", -1, (byte) 0, new Date(), 1001, new Date(), 1001),
                //排序值为null 覆盖等价类8
                new Carousel(10, "http://example.com/carousel1.jpg", "http://example.com/page1", null, (byte) 0, new Date(), 1001, new Date(), 1001)
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
    @MethodSource("validData")
    public void saveCarousel_valid(Carousel carousel) {
        Assertions.assertEquals(ServiceResultEnum.SUCCESS.getResult(), newBeeMallCarouselService.saveCarousel(carousel));
    }

    @ParameterizedTest
    @MethodSource("invalidData")
    public void saveCarousel_invalid(Carousel carousel) {
//        Carousel carousel = new Carousel(6, "", "http://example.com/page1", 1, (byte) 0, new Date(), 1001, new Date(), 1001);
        String result = newBeeMallCarouselService.saveCarousel(carousel);
        Assertions.assertEquals(ServiceResultEnum.SUCCESS.getResult(), result);
    }

    @Test
    public void updateCarousel_valid() {
        Carousel carousel = new Carousel(2, "http://example.com/carousel1.jpg", "http://example.com/page1", 1, (byte) 0, new Date(), 1001, new Date(), 1001);
        carousel.setCarouselUrl("http://example.com/carousel1_updated.jpg");
        carousel.setRedirectUrl("http://example.com/page1_updated");
        carousel.setCarouselRank(2);
        String result = newBeeMallCarouselService.updateCarousel(carousel);
        Assertions.assertEquals(ServiceResultEnum.SUCCESS.getResult(), result);
    }

    @Test
    public void updateCarousel_invalid() {
        Carousel carousel = new Carousel(999, "http://example.com/carousel1.jpg", "http://example.com/page1", 1, (byte) 0, new Date(), 1001, new Date(), 1001); // Non-existent carouselId
        String result = newBeeMallCarouselService.updateCarousel(carousel);
        Assertions.assertEquals(ServiceResultEnum.DATA_NOT_EXIST.getResult(), result);
    }

    @Test
    public void deleteBatch_valid() {
        Integer[] ids = {1, 2, 3};
        Boolean result = newBeeMallCarouselService.deleteBatch(ids);
        Assertions.assertTrue(result, "有效的ID数组应被成功删除");
    }

    @Test
    public void deleteBatch_invalid() {
        Integer[] ids = {};
        Boolean result = newBeeMallCarouselService.deleteBatch(ids);
        Assertions.assertFalse(result, "空的ID数组不应被删除");
    }

    @Test
    public void getCarouselById_valid() {
        Carousel carousel = newBeeMallCarouselService.getCarouselById(1);
        Assertions.assertNotNull(carousel, "存在的ID应返回轮播图对象");
    }

    @Test
    public void getCarouselById_invalid() {
        Carousel carousel = newBeeMallCarouselService.getCarouselById(999); // Non-existent ID
        Assertions.assertNull(carousel, "不存在的ID应返回null");
    }
}
