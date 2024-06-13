//package ltd.newbee.mall.controller.admin;
//
//import ltd.newbee.mall.NewBeeMallPlusApplication;
//import ltd.newbee.mall.controller.mall.GoodsController;
//import ltd.newbee.mall.entity.NewBeeMallGoods;
//import ltd.newbee.mall.service.NewBeeMallGoodsService;
//import ltd.newbee.mall.util.PageQueryUtil;
//import ltd.newbee.mall.util.PageResult;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.mockito.InjectMocks;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//class NewBeeMallGoodsControllerTest {
//
//    private MockMvc mockMvc;
//
//    @MockBean
//    private NewBeeMallGoodsService newBeeMallGoodsService;
//
//    @Autowired
//    @InjectMocks
//    private NewBeeMallGoodsController newBeeMallGoodsController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(newBeeMallGoodsController).build();
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "1, 10, 200", // 正确的参数
//            "'', 10, 500", // 缺少 page 参数
//            "1, '', 500", // 缺少 limit 参数
//            "'', '', 500" // 缺少 page 和 limit 参数
//    })
//    public void list(String page, String limit, int expectedStatus) throws Exception {
//        Map<String, Object> params = new HashMap<>();
//        if (!page.isEmpty()) {
//            params.put("page", page);
//        }
//        if (!limit.isEmpty()) {
//            params.put("limit", limit);
//        }
//
//        if (expectedStatus == 200) {
//            PageQueryUtil pageUtil = new PageQueryUtil(params);
//            List<NewBeeMallGoods> mockGoodsList = Arrays.asList(
//                    new NewBeeMallGoods(
//                            1L,
//                            "Product 1",
//                            "Introduction 1",
//                            1L,
//                            "cover_img_1.jpg",
//                            "carousel_img_1.jpg",
//                            200,
//                            150,
//                            100,
//                            "tag1",
//                            (byte) 1,
//                            1,
//                            new Date(),
//                            1,
//                            new Date(),
//                            "Detail content 1"
//                    ),
//                    new NewBeeMallGoods(
//                            2L,
//                            "Product 2",
//                            "Introduction 2",
//                            2L,
//                            "cover_img_2.jpg",
//                            "carousel_img_2.jpg",
//                            300,
//                            250,
//                            200,
//                            "tag2",
//                            (byte) 1,
//                            1,
//                            new Date(),
//                            1,
//                            new Date(),
//                            "Detail content 2"
//                    )
//            );
//            PageResult<NewBeeMallGoods> pageResult = new PageResult<>(mockGoodsList, 20, 10, 1);
//            given(newBeeMallGoodsService.getNewBeeMallGoodsPage(pageUtil)).willReturn(pageResult);
//        }
//
//        mockMvc.perform(get("/admin/goods/list")
//                        .param("page", page)
//                        .param("limit", limit)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.resultCode").value(expectedStatus));
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//}