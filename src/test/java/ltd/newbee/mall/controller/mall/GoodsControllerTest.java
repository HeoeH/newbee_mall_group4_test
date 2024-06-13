package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GoodsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewBeeMallCategoryService categoryService;

    @MockBean
    private NewBeeMallGoodsService goodsService;

    @BeforeEach
    public void setUp() {
        // 初始化或设置Mock行为（如果需要）
    }

    private static MultiValueMap<String, String> toMultiValueMap(Map<String, Object> params) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        params.forEach((key, value) -> {
            if (value instanceof Collection) {
                ((Collection<?>) value).forEach(item -> multiValueMap.add(key, item.toString()));
            } else {
                multiValueMap.add(key, value.toString());
            }
        });
        return multiValueMap;
    }

    private static Stream<Map<String, Object>> provideSearchParams() {
        return Stream.of(
                // 正常搜索，有关键字
                new HashMap<String, Object>() {{
                    put("keyword", "test");
                    put("page", 1);
                }},
                // 分页测试
                new HashMap<String, Object>() {{
                    put("keyword", "test");
                    put("page", 2);
                }},
                // 排序测试
                new HashMap<String, Object>() {{
                    put("keyword", "test");
                    put("page", 1);
                    put("orderBy", "priceDesc");
                }},
                // 类别筛选
                new HashMap<String, Object>() {{
                    put("keyword", "test");
                    put("page", 1);
                    put("goodsCategoryId", 1L);
                }},
                // 空关键字，仅分页
                new HashMap<String, Object>() {{
                    put("page", 1);
                }}
        );
    }

    @ParameterizedTest
    @MethodSource("provideSearchParams")
    public void testSearchPageWithParams(Map<String, Object> params) throws Exception {
        MultiValueMap<String, String> multiValueParams = toMultiValueMap(params);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/search")
                        .params(multiValueParams))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("mall/search"))
                .andReturn();

        // 根据需要添加额外的断言，比如验证模型中的数据是否符合预期
        // 注意：实际断言内容需根据测试需求和返回结果具体实现
    }
}