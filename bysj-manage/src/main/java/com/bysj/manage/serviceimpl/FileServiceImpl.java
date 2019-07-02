package com.bysj.manage.serviceimpl;

import com.bysj.common.vo.PicUploadResult;
import com.bysj.manage.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private String dirPath="D:/bsImg/";
    //定义虚拟路径
    private String urlPathh="http://image.bysj.com/";

    /**
     *    1.校验上传的文件是否为图片 jpg|png|gif
     *    1.1(正则表达式)  ^开始 $结束 .除了回车和空格 +一个或者多个 *没有或者多个 ()组代表or或者
     *    2.判断文件是否为恶意程序 exe/rpm
     *    3.为了提高检索效率，一般采用份文件存储 层级最好不要超过5级
     *    4.为了防止文件名称重复，采用动态方式获取文件名称
     * @throws IOException
     */
    @Override
    public PicUploadResult uploadFile(MultipartFile uploadFile) {
        PicUploadResult result = new PicUploadResult();
        //1.校验上传的文件是否为图片 jpg|png|gif
        String fileName = uploadFile.getOriginalFilename();
        fileName = fileName.toLowerCase();
        if(!fileName.matches("^.+\\.(jpg|png|gif)$")) {
            result.setError(1);
        }
        //2.判断文件是否为恶意程序 exe/rpm
        try {
            BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());

            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            result.setHeight(String.valueOf(height));
            result.setWidth(String.valueOf(width));
            if(width == 0  || height == 0) {
                result.setError(1);  //不是图片
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result.setError(1);
        }

        /**
         * 3.为了提高检索效率，一般采用份文件存储 层级最好不要超过5级
         * yyyy/mm/dd/ 年月日
         */
        String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

        //4.为了防止文件名称重复，采用动态方式获取文件名称  UUID 时间毫秒数为值 2的32次方位数 +随机数0-999
        String uuid = UUID.randomUUID().toString().replace("-", "");
        int random = new Random().nextInt(1000);
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        String uuidFileName = uuid + random +fileType;

        try {
            //文件夹路径
            String fileDirPath = dirPath + dateDir;
            File fileDir = new File(fileDirPath);
            if(!fileDir.exists()) {
                fileDir.mkdirs();
            }

            //实现文件上传
            File realFilePath = new File(fileDirPath+"/"+uuidFileName);
            uploadFile.transferTo(realFilePath);
            /**
             * 图片回显虚拟路径
             */
            //String realUrlPath = urlPath + dateDir + "/" + uuidFileName;
            String realUrlPath = urlPathh + dateDir + "/" + uuidFileName;
            result.setUrl(realUrlPath);

        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }
}
