package niuniu.javaweb.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import niuniu.javaweb.exception.ParamErrorException;
import niuniu.javaweb.mapper.MenuMapper;
import niuniu.javaweb.mapper.UserMapper;
import niuniu.javaweb.pojo.User;
import niuniu.javaweb.service.UserService;
import niuniu.javaweb.utils.FileUtil;
import niuniu.javaweb.utils.MenuUtils;
import niuniu.javaweb.utils.StringUtils;
import niuniu.javaweb.utils.UUIDUtils;
import niuniu.javaweb.utils.ocr.OCRUtil;
import niuniu.javaweb.utils.result.CommonResult;
import niuniu.javaweb.utils.tools.SeqTools;
import niuniu.javaweb.vo.MenuListVO;
import niuniu.javaweb.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.service.impl
 * @date 2023/2/7 21:58
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    MenuMapper menuMapper;

    private static String UPLOAD_PATH;

    @Value("${file.uploadPath}")
    public void setDestPath(String uploadPath) {
        this.UPLOAD_PATH = uploadPath;
    }

    /**
     * 获取用户信息
     *
     * @param username 用户编号
     * @return
     */
    @Override
    @Cacheable(cacheNames = "userInfo", key = "#username")
    public User getUser(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    /**
     * 更新头像
     *
     * @param avatar   头像
     * @param username 用户编号
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "userInfo", key = "#username")
    public CommonResult uploadAvatar(MultipartFile avatar, String username) {
        System.out.println("【文件上传】进入到文件上传方法");
        // 1.参数校验
        if (null == avatar || avatar.isEmpty()) {
            System.out.println("【文件上传】文件为空!");
            throw new ParamErrorException();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        // 2.上传文件
        String path = FileUtil.uploadAvatar(avatar, username);
        System.out.println(path);
//        把数据存到数据库中
        if (!StringUtils.isEmpty(path)) {
            if (userMapper.updateAvatar(username, path) > 0) {
                if (!user.getAvatar().equals("avatar_default.jpeg")) {
                    File delFile = new File(UPLOAD_PATH + "/Avatar/" + user.getAvatar());
                    delFile.delete();
                }
                return CommonResult.success(path);
            }
        }
        return CommonResult.failed();
    }

    /**
     * 获取用户权限
     *
     * @param username
     * @return
     */
    @Override
    @Cacheable(cacheNames = "userMenu", key = "#username")
    public MenuListVO getMenuList(String username) {
        return MenuUtils.buildTree(menuMapper.selectPermsByUserName(username));
    }

    /**
     * 创建新用户账号
     *
     * @param roleId
     * @return
     */
    @Override
    @Cacheable(cacheNames = "newUserName", key = "#roleId")
    public CommonResult createUserName(Integer roleId) throws Exception {
        return CommonResult.success(SeqTools.getNumber(String.valueOf(roleId)));
    }

    /**
     * 校验身份证是否可靠
     *
     * @param file
     * @param direction
     * @return
     */
    @Override
    public CommonResult validateIDCard(MultipartFile file, String direction) {
        int flag = 0;
        if (direction.equals("back")) flag++;
//        System.out.println("【文件上传】进入到文件上传方法");
        // 1.参数校验
        if (null == file || file.isEmpty()) {
//            System.out.println("【文件上传】文件为空!");
            throw new ParamErrorException();
        }
        // 2.上传文件
        String path = FileUtil.uploadIDCard(file, "validate", flag);
        // 3.通过OCR技术扫描身份证件
        OCRUtil ocrUtil = new OCRUtil();
        User user;
        File delFile = new File(path);

        try {
            user = ocrUtil.checkFile(path, flag);
//            System.out.println(user);
            //判断是否是身份证
            if (StringUtils.isEmpty(user.getIdcard()) && StringUtils.isEmpty(user.getAuthority())) {
                delFile.delete();
                return CommonResult.failed("请上传对应的证件");
            }
            //判断身份证方向是否上传正确
            if (!(StringUtils.isEmpty(user.getSign()) || StringUtils.isEmpty(user.getLose()) || StringUtils.isEmpty(user.getAuthority()))) {
                if (flag > 0) {
                    delFile.delete();
                    return CommonResult.failed("请上传对应的证件");
                }
            }
            if (StringUtils.isEmpty(user.getSign()) || StringUtils.isEmpty(user.getLose()) || StringUtils.isEmpty(user.getAuthority())) {
                if (flag == 0) {
                    delFile.delete();
                    return CommonResult.failed("请上传对应的证件");
                }
            }

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        delFile.delete();
        return CommonResult.success(direction);
    }

    /**
     * 确认信息
     *
     * @param font
     * @param back
     * @return
     */
    @Override
    public CommonResult confirmMsg(MultipartFile font, MultipartFile back) throws URISyntaxException, IOException {
        String s = UUIDUtils.generateShortUuid();
        String pathFont = FileUtil.uploadIDCard(font, "validate" + "_" + s, 1);
        String pathBack = FileUtil.uploadIDCard(back, "validate" + "_" + s, 0);
        User user = mergeUser(pathFont, pathBack);
        new File(pathFont).delete();
        new File(pathBack).delete();
        return CommonResult.success(user);
    }


    /**
     * 创建用户
     *
     * @param font
     * @param back
     * @param jsonUser
     * @param roleId
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames = "newUserName", key = "#roleId")
    public CommonResult insertUser(MultipartFile font, MultipartFile back, String jsonUser, Integer roleId) {
        User user = JSONObject.parseObject(jsonUser, User.class);
        System.out.println(user);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode("88888888"));
        userMapper.createUser(user);
        if (user.getUserId() != null) {
            FileUtil.uploadIDCard(font, user.getUsername() + "_" + user.getName(), 1);
            FileUtil.uploadIDCard(back, user.getUsername() + "_" + user.getName(), 0);
            return CommonResult.success(user);
        }
        return CommonResult.failed();
    }

    /**
     * 修改日期为 YYYY-MM-DD
     *
     * @param str
     * @return
     */
    private String builderTime(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.insert(6, "-");
        stringBuilder.insert(4, "-");
        return stringBuilder.toString();
    }

    /**
     * 按需查询全部用户信息
     *
     * @param username
     * @param name
     * @param houseName
     * @param currentPage
     * @param pageSize
     * @param state
     * @param orderState
     * @return
     */
    @Override
    public IPage<UserVO> getUserPage(String username, String name, String houseName, int currentPage, int pageSize, String state, String orderState) {
        Page<UserVO> page = new Page<>(currentPage, pageSize);
        QueryWrapper<UserVO> queryWrapper = new QueryWrapper<>();
        return userMapper.getUserPage(username, name, houseName, state, orderState, page, queryWrapper);
    }

    /**
     * 查询没有住房的用户
     *
     * @param username
     * @param name
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public IPage<UserVO> getNoHouseUserPage(String username, String name, int currentPage, int pageSize) {
        Page<UserVO> page = new Page<>(currentPage, pageSize);
        QueryWrapper<UserVO> queryWrapper = new QueryWrapper<>();
        return userMapper.getNoHouseUserPage(username, name, page, queryWrapper);
    }

    /**
     * 修改用户信息通过身份证
     *
     * @param font
     * @param back
     * @param username
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    @Override
    public CommonResult updateUserByIDCard(MultipartFile font, MultipartFile back, String username) throws URISyntaxException, IOException {
        String pathFont = FileUtil.uploadIDCard(font, "validate", 0);
        String pathBack = FileUtil.uploadIDCard(back, "validate", 1);
        User user = mergeUser(pathFont, pathBack);
        user.setUsername(username);
        System.out.println(user);
        System.out.println(username);
        if (userMapper.updateUserByIDCard(user) <= 0) return CommonResult.failed();
        File delFile1 = new File(pathFont);
        File delFile2 = new File(pathBack);
        delFile1.delete();
        delFile2.delete();
        return CommonResult.success(user);
    }

    /**
     * 更新用户手机/密码
     *
     * @param user
     * @return
     */
    @Override
    public CommonResult updateUser(User user) {
        if (!StringUtils.isEmpty(user.getPassword())) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        if (userMapper.updateUser(user) <= 0) return CommonResult.failed();
        return CommonResult.success();
    }

    /**
     * 合并user
     *
     * @param pathFont
     * @param pathBack
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    private User mergeUser(String pathFont, String pathBack) throws URISyntaxException, IOException {
        OCRUtil ocrUtil = new OCRUtil();

        User user = ocrUtil.checkFile(pathFont, 1);
        User user1 = ocrUtil.checkFile(pathBack, 0);
//        System.out.println(user);
//        System.out.println(user1);
        user.setBirthday(builderTime(user.getBirthday()));
        user.setAuthority(user1.getAuthority());
        user.setSign(builderTime(user1.getSign()));
        user.setLose(builderTime(user1.getLose()));
        return user;
    }

    /**
     * 修改用户状态
     *
     * @param userId
     * @param state
     * @return
     */
    @Override
    public CommonResult changeUserState(Integer userId, Integer state) {
        return userMapper.changeUserState(userId, state) > 0 ? CommonResult.success() : CommonResult.failed();
    }

    /**
     * 校验密码
     *
     * @param username
     * @return
     */
    @Override
    public CommonResult validatePassword(String username, String password) {
        System.out.println(username);
        return new BCryptPasswordEncoder().matches(password, userMapper.validatePassword(username)) ? CommonResult.success() : CommonResult.failed("密码有误");
    }

    /**
     * 获取用户名字
     *
     * @param userId
     * @return
     */
    @Override
    @Cacheable(cacheNames = "getName", key = "#userId")
    public CommonResult getName(Integer userId) {
        User user = userMapper.selectById(userId);
        System.out.println(user.getName());
        return CommonResult.success(user.getName());
    }

    /**
     * 删除住户
     *
     * @param userId
     * @return
     */
    @Override
    public CommonResult deleteUser(Integer userId) {
        return userMapper.deleteUser(userId) > 0 ? CommonResult.success() : CommonResult.failed();
    }
}
