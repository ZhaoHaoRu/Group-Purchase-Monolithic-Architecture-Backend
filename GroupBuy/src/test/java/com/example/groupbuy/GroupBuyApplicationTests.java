package com.example.groupbuy;

import com.example.groupbuy.utils.RsaUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GroupBuyApplicationTests {


    private String privateFilePath = "C:\\Users\\Lenovo\\.ssh\\id_key_rsa";
    private String publicFilePath = "C:\\Users\\Lenovo\\.ssh\\id_key_rsa.pub";

    @Test
    public void generateKey() throws Exception {
        RsaUtils.generateKey(publicFilePath,privateFilePath,"RobodLee",2048);
    }

    @Test
    public void getPublicKey() throws Exception {
        System.out.println(RsaUtils.getPublicKey(publicFilePath));
    }

    @Test
    public void getPrivateKey() throws Exception {
        System.out.println(RsaUtils.getPrivateKey(privateFilePath));
    }

}
