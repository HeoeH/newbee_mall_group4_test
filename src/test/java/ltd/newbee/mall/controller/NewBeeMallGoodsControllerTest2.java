package ltd.newbee.mall.controller;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.admin.NewBeeMallGoodsController;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class NewBeeMallGoodsControllerTest2 {

    private MockMvc mockMvc;

    @MockBean
    private NewBeeMallGoodsService newBeeMallGoodsService;

    @InjectMocks
    private NewBeeMallGoodsController newBeeMallGoodsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(newBeeMallGoodsController).build();
    }

    @ParameterizedTest
    @CsvSource({
            "1, 10, 3, 200, 'SUCCESS'",
            "1, 5, 10, 200, 'SUCCESS'"
    })
    void testList(String page, String limit, String configType, int expectedStatusCode, String expectedMessage) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("limit", limit);
        params.put("configType", configType);

        if (expectedStatusCode == 200) {
            PageQueryUtil pageUtil = new PageQueryUtil(params);
            given(newBeeMallGoodsService.getNewBeeMallGoodsPage(pageUtil)).willReturn(null);
        }

        mockMvc.perform(get("/admin/goods/list")
                        .param("page", page)
                        .param("limit", limit)
                        .param("configType", configType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(expectedStatusCode))
                .andExpect(jsonPath("$.resultCode").value(expectedStatusCode))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 'Test Goods', 'Intro', 'Tag', 100, 1, 80, 10, 0, 'img.png', 'Details', '参数异常！'",
            "1, '', 'Intro', 'Tag', 100, 1, 80, 10, 0, 'img.png', 'Details', '参数异常！'"
    })
    void testSave(Long goodsId, String goodsName, String goodsIntro, String tag, Integer originalPrice, Long goodsCategoryId, Integer sellingPrice, Integer stockNum, Byte goodsSellStatus, String goodsCoverImg, String goodsDetailContent, String expectedResult) throws Exception {
        NewBeeMallGoods newBeeMallGoods = new NewBeeMallGoods();
        newBeeMallGoods.setGoodsId(goodsId);
        newBeeMallGoods.setGoodsName(goodsName);
        newBeeMallGoods.setGoodsIntro(goodsIntro);
        newBeeMallGoods.setTag(tag);
        newBeeMallGoods.setOriginalPrice(originalPrice);
        newBeeMallGoods.setGoodsCategoryId(goodsCategoryId);
        newBeeMallGoods.setSellingPrice(sellingPrice);
        newBeeMallGoods.setStockNum(stockNum);
        newBeeMallGoods.setGoodsSellStatus(goodsSellStatus);
        newBeeMallGoods.setGoodsCoverImg(goodsCoverImg);
        newBeeMallGoods.setGoodsDetailContent(goodsDetailContent);

        given(newBeeMallGoodsService.saveNewBeeMallGoods(any(NewBeeMallGoods.class))).willReturn(expectedResult);

        mockMvc.perform(post("/admin/goods/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"goodsId\":" + goodsId + ", \"goodsName\":\"" + goodsName + "\", \"goodsIntro\":\"" + goodsIntro + "\", \"tag\":\"" + tag + "\", \"originalPrice\":" + originalPrice + ", \"goodsCategoryId\":" + goodsCategoryId + ", \"sellingPrice\":" + sellingPrice + ", \"stockNum\":" + stockNum + ", \"goodsSellStatus\":" + goodsSellStatus + ", \"goodsCoverImg\":\"" + goodsCoverImg + "\", \"goodsDetailContent\":\"" + goodsDetailContent + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(expectedResult.equals("SUCCESS") ? 200 : 500))
                .andExpect(jsonPath("$.message").value(expectedResult));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 'Updated Goods', 'Updated Intro', 'Updated Tag', 120, 2, 90, 15, 1, 'updated_img.png', 'Updated Details', '参数异常！'",
            "1, '', 'Updated Intro', 'Updated Tag', 120, 2, 90, 15, 1, 'updated_img.png', 'Updated Details', '参数异常！'"
    })
    void testUpdate(Long goodsId, String goodsName, String goodsIntro, String tag, Integer originalPrice, Long goodsCategoryId, Integer sellingPrice, Integer stockNum, Byte goodsSellStatus, String goodsCoverImg, String goodsDetailContent, String expectedResult) throws Exception {
        NewBeeMallGoods newBeeMallGoods = new NewBeeMallGoods();
        newBeeMallGoods.setGoodsId(goodsId);
        newBeeMallGoods.setGoodsName(goodsName);
        newBeeMallGoods.setGoodsIntro(goodsIntro);
        newBeeMallGoods.setTag(tag);
        newBeeMallGoods.setOriginalPrice(originalPrice);
        newBeeMallGoods.setGoodsCategoryId(goodsCategoryId);
        newBeeMallGoods.setSellingPrice(sellingPrice);
        newBeeMallGoods.setStockNum(stockNum);
        newBeeMallGoods.setGoodsSellStatus(goodsSellStatus);
        newBeeMallGoods.setGoodsCoverImg(goodsCoverImg);
        newBeeMallGoods.setGoodsDetailContent(goodsDetailContent);

        given(newBeeMallGoodsService.updateNewBeeMallGoods(any(NewBeeMallGoods.class))).willReturn(expectedResult);

        mockMvc.perform(post("/admin/goods/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"goodsId\":" + goodsId + ", \"goodsName\":\"" + goodsName + "\", \"goodsIntro\":\"" + goodsIntro + "\", \"tag\":\"" + tag + "\", \"originalPrice\":" + originalPrice + ", \"goodsCategoryId\":" + goodsCategoryId + ", \"sellingPrice\":" + sellingPrice + ", \"stockNum\":" + stockNum + ", \"goodsSellStatus\":" + goodsSellStatus + ", \"goodsCoverImg\":\"" + goodsCoverImg + "\", \"goodsDetailContent\":\"" + goodsDetailContent + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(expectedResult.equals("SUCCESS") ? 200 : 500))
                .andExpect(jsonPath("$.message").value(expectedResult));
    }
}
