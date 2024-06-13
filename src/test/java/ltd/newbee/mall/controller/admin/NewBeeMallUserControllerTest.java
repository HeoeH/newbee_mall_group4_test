package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.entity.MallUser;
import ltd.newbee.mall.service.NewBeeMallUserService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class NewBeeMallUserControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private NewBeeMallUserService newBeeMallUserService;

    @Autowired
    @InjectMocks
    private NewBeeMallUserController newBeeMallUserController;

    List<MallUser> mockUserList = Arrays.asList(
            new MallUser(1L, "Nick 1", "Login 1", "PasswordMd5_1", "IntroduceSign 1", "Address 1", (byte) 0, (byte) 0, new Date()),
            new MallUser(2L, "Nick 2", "Login 2", "PasswordMd5_2", "IntroduceSign 2", "Address 2", (byte) 0, (byte) 0, new Date()),
            new MallUser(3L, "Nick 3", "Login 3", "PasswordMd5_3", "IntroduceSign 3", "Address 3", (byte) 0, (byte) 0, new Date()),
            new MallUser(4L, "Nick 4", "Login 4", "PasswordMd5_4", "IntroduceSign 4", "Address 4", (byte) 0, (byte) 0, new Date()),
            new MallUser(5L, "Nick 5", "Login 5", "PasswordMd5_5", "IntroduceSign 5", "Address 5", (byte) 0, (byte) 0, new Date()),
            new MallUser(6L, "Nick 6", "Login 6", "PasswordMd5_6", "IntroduceSign 6", "Address 6", (byte) 0, (byte) 0, new Date()),
            new MallUser(7L, "Nick 7", "Login 7", "PasswordMd5_7", "IntroduceSign 7", "Address 7", (byte) 0, (byte) 0, new Date()),
            new MallUser(8L, "Nick 8", "Login 8", "PasswordMd5_8", "IntroduceSign 8", "Address 8", (byte) 0, (byte) 0, new Date()),
            new MallUser(9L, "Nick 9", "Login 9", "PasswordMd5_9", "IntroduceSign 9", "Address 9", (byte) 0, (byte) 0, new Date()),
            new MallUser(10L, "Nick 10", "Login 10", "PasswordMd5_10", "IntroduceSign 10", "Address 10", (byte) 0, (byte) 0, new Date()),
            new MallUser(11L, "Nick 11", "Login 11", "PasswordMd5_11", "IntroduceSign 11", "Address 11", (byte) 0, (byte) 0, new Date()),
            new MallUser(12L, "Nick 12", "Login 12", "PasswordMd5_12", "IntroduceSign 12", "Address 12", (byte) 0, (byte) 0, new Date()),
            new MallUser(13L, "Nick 13", "Login 13", "PasswordMd5_13", "IntroduceSign 13", "Address 13", (byte) 0, (byte) 0, new Date()),
            new MallUser(14L, "Nick 14", "Login 14", "PasswordMd5_14", "IntroduceSign 14", "Address 14", (byte) 0, (byte) 0, new Date()),
            new MallUser(15L, "Nick 15", "Login 15", "PasswordMd5_15", "IntroduceSign 15", "Address 15", (byte) 0, (byte) 0, new Date()),
            new MallUser(16L, "Nick 16", "Login 16", "PasswordMd5_16", "IntroduceSign 16", "Address 16", (byte) 0, (byte) 0, new Date()),
            new MallUser(17L, "Nick 17", "Login 17", "PasswordMd5_17", "IntroduceSign 17", "Address 17", (byte) 0, (byte) 0, new Date()),
            new MallUser(18L, "Nick 18", "Login 18", "PasswordMd5_18", "IntroduceSign 18", "Address 18", (byte) 0, (byte) 0, new Date()),
            new MallUser(19L, "Nick 19", "Login 19", "PasswordMd5_19", "IntroduceSign 19", "Address 19", (byte) 0, (byte) 0, new Date()),
            new MallUser(20L, "Nick 20", "Login 20", "PasswordMd5_20", "IntroduceSign 20", "Address 20", (byte) 0, (byte) 0, new Date())
    );

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(newBeeMallUserController).build();
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @CsvSource({
            "1, 10, 200", // 正确的参数
            "'', 10, 500", // 缺少 page 参数
            "1, '', 500", // 缺少 limit 参数
            "'', '', 500" // 缺少 page 和 limit 参数
    })
    public void list(String page, String limit, int expectedStatus) throws Exception {
        Map<String, Object> params = new HashMap<>();
        if (!page.isEmpty()) {
            params.put("page", page);
        }
        if (!limit.isEmpty()) {
            params.put("limit", limit);
        }

        if (expectedStatus == 200) {
            PageQueryUtil pageUtil = new PageQueryUtil(params);

            PageResult<MallUser> pageResult = new PageResult<>(mockUserList, 20, 10, 1);
            given(newBeeMallUserService.getNewBeeMallUsersPage(pageUtil)).willReturn(pageResult);
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users/list")
                        .param("page", page)
                        .param("limit", limit)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resultCode").value(expectedStatus));
    }

    @ParameterizedTest
    @CsvSource({
            "'', 2, 500, '参数异常！'",         //1
            "'1,2', 2, 500, '操作非法！'",      //2
            "'', 1, 500, '参数异常！'",         //3
            "'1,2', 1, 200, 'SUCCESS'",       //4
            "'', 0, 500, '参数异常！'",         //5
            "'1,2', 1, 200, 'SUCCESS'",      //6
            "'', -1, 500, '参数异常！'",         //7
            "'1,2', -1, 500, '参数异常！'",        //8
            "'', 2, 500, '参数异常！'",           //9
            "'1,2', 2, 500, '操作非法！'",         //10
            "'', 1, 500, '参数异常！'",            //11
            "'1,2', 1, 500, '禁用失败！'",
            "'', 0, 500, '参数异常！'",
            "'1,2', 0, 500, '禁用失败！'",
            "'', -1, 500, '参数异常！'",
            "'1,2', -1, 500, '参数异常！'",
    })
    public void delete(String ids, int lockStatus, int expectedResultCode, String expectedMessage) throws Exception {
        // Convert ids string to array
        Integer[] idsArray = ids.isEmpty() ? new Integer[]{} : Arrays.stream(ids.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
        if (expectedResultCode == 200) {
            when(newBeeMallUserService.lockUsers(any(Integer[].class), anyInt())).thenReturn(true);
        } else if (expectedMessage.equals("禁用失败")) {
            when(newBeeMallUserService.lockUsers(any(Integer[].class), anyInt())).thenReturn(false);
        }

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/users/lock/{lockStatus}", lockStatus)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ids.isEmpty() ? "[]" : "[" + ids + "]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resultCode").value(expectedResultCode))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(expectedMessage));
    }


}