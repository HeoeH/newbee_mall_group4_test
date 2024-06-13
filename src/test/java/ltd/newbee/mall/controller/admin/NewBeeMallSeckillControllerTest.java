package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.entity.NewBeeMallSeckill;
import ltd.newbee.mall.redis.RedisCache;
import ltd.newbee.mall.service.NewBeeMallSeckillService;
import ltd.newbee.mall.service.NewBeeMallUserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class NewBeeMallSeckillControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private NewBeeMallSeckillService newBeeMallSeckillService;

    @Mock
    private RedisCache redisCache;

    @Autowired
    @InjectMocks
    private NewBeeMallSeckillController newBeeMallSeckillController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(newBeeMallSeckillController).build();
    }

    @AfterEach
    void tearDown() {
    }


    @ParameterizedTest
    @MethodSource("provideValidSeckillData")
    void testUpdateSeckillSuccess(Long seckillId, Long goodsId, int seckillNum, int seckillPrice) throws Exception {
        NewBeeMallSeckill seckill = new NewBeeMallSeckill();
        seckill.setSeckillId(seckillId);
        seckill.setGoodsId(goodsId);
        seckill.setSeckillNum(seckillNum);
        seckill.setSeckillPrice(seckillPrice);
        seckill.setUpdateTime(new Date());

        when(newBeeMallSeckillService.updateSeckill(any(NewBeeMallSeckill.class))).thenReturn(true);

        String seckillJson = String.format("{ \"seckillId\": %d, \"goodsId\": %d, \"seckillNum\": %d, \"seckillPrice\": %d }", seckillId, goodsId, seckillNum, seckillPrice);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/seckill/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(seckillJson))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"resultCode\":200,\"message\":\"SUCCESS\"}"));

        verify(redisCache).setCacheObject(Constants.SECKILL_GOODS_STOCK_KEY + seckillId, seckillNum);
        verify(redisCache).deleteObject(Constants.SECKILL_GOODS_DETAIL + seckillId);
        verify(redisCache).deleteObject(Constants.SECKILL_GOODS_LIST);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidSeckillData")
    void testUpdateSeckillFailure(Long seckillId, Long goodsId, int seckillNum, int seckillPrice) throws Exception {
        String seckillJson = String.format("{ \"seckillId\": %d, \"goodsId\": %d, \"seckillNum\": %d, \"seckillPrice\": %d }", seckillId, goodsId, seckillNum, seckillPrice);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/seckill/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(seckillJson))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"resultCode\":500,\"message\":\"参数异常\"}"));

        verify(newBeeMallSeckillService, never()).updateSeckill(any(NewBeeMallSeckill.class));
        verify(redisCache, never()).setCacheObject(anyString(), any());
        verify(redisCache, never()).deleteObject(anyString());
    }


    @Test
    void testUpdateSeckillGoodsNotExist() throws Exception {
        Long seckillId = 1L;
        Long goodsId = 99999L;
        int seckillNum = 10;
        int seckillPrice = 100;

        NewBeeMallSeckill seckill = new NewBeeMallSeckill();
        seckill.setSeckillId(seckillId);
        seckill.setGoodsId(goodsId);
        seckill.setSeckillNum(seckillNum);
        seckill.setSeckillPrice(seckillPrice);
        seckill.setUpdateTime(new Date());

        when(newBeeMallSeckillService.updateSeckill(any(NewBeeMallSeckill.class)))
                .thenThrow(new RuntimeException(ServiceResultEnum.GOODS_NOT_EXIST.getResult()));

        String seckillJson = String.format("{ \"seckillId\": %d, \"goodsId\": %d, \"seckillNum\": %d, \"seckillPrice\": %d }", seckillId, goodsId, seckillNum, seckillPrice);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/seckill/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(seckillJson))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"resultCode\":500,\"message\":\"商品不存在！\"}"));

        verify(newBeeMallSeckillService).updateSeckill(any(NewBeeMallSeckill.class));
        verify(redisCache, never()).setCacheObject(anyString(), any());
        verify(redisCache, never()).deleteObject(anyString());
    }

    @Test
    void testUpdateSeckillDataNotExist() throws Exception {
        Long seckillId = 999999L;
        Long goodsId = 1L;
        int seckillNum = 10;
        int seckillPrice = 100;

        NewBeeMallSeckill seckill = new NewBeeMallSeckill();
        seckill.setSeckillId(seckillId);
        seckill.setGoodsId(goodsId);
        seckill.setSeckillNum(seckillNum);
        seckill.setSeckillPrice(seckillPrice);
        seckill.setUpdateTime(new Date());

        when(newBeeMallSeckillService.updateSeckill(any(NewBeeMallSeckill.class)))
                .thenThrow(new RuntimeException(ServiceResultEnum.DATA_NOT_EXIST.getResult()));

        String seckillJson = String.format("{ \"seckillId\": %d, \"goodsId\": %d, \"seckillNum\": %d, \"seckillPrice\": %d }", seckillId, goodsId, seckillNum, seckillPrice);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/seckill/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(seckillJson))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"resultCode\":500,\"message\":\"未查询到记录！\"}"));

        verify(newBeeMallSeckillService).updateSeckill(any(NewBeeMallSeckill.class));
        verify(redisCache, never()).setCacheObject(anyString(), any());
        verify(redisCache, never()).deleteObject(anyString());
    }


    private static Stream<Arguments> provideValidSeckillData() {
        return Stream.of(
                Arguments.of(1L, 1L, 10, 100),
                Arguments.of(2L, 2L, 20, 200)
        );
    }

    private static Stream<Arguments> provideInvalidSeckillData() {
        return Stream.of(
                Arguments.of(null, 1L, 10, -1), //1
                Arguments.of(1L, -1L, 10, -2),//2
                Arguments.of(1L, 1L, 0, -1),//3
                Arguments.of(1L, 1L, 10, -999), //4
                Arguments.of(null, -1L, 10, 100), //5
                Arguments.of(1L, 1L, -1, 100), //6
                Arguments.of(null, 1L, 0, -1), //7
                Arguments.of(null, 1L, 0, 10), //8
                Arguments.of(null, 1L, 10, -1), //9
                Arguments.of(null, 1L, 1, 10), //10
                Arguments.of(1L, -1L, -1, 0), //11
                Arguments.of(1L, -1L, -1, 10), //12
                Arguments.of(1L, -1L, 1, 0),  //13
                Arguments.of(1L, -1L, 1, 10), //14
                Arguments.of(1L, 1L, 0, -1), //15
                Arguments.of(1L, 1L, -1, 10), //16
                Arguments.of(1L, 1L, 1, 0)  //17
        );
    }


}