package com.phycholee.blog.test;

import com.phycholee.blog.utils.EncryptUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by PhychoLee on 2017/4/12 22:41.
 * Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestEnrypt {


    @Test
    public void testPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "aaa";

        String salt = EncryptUtil.generateSalt();

        String encryptedPassword = EncryptUtil.encryptPassword(password, salt);

        boolean authenticate = EncryptUtil.authenticate(password, encryptedPassword, salt);

        System.out.println("salt: " + salt);

        System.out.println("encryptedPassword: " + encryptedPassword);

        System.out.println("authenticate result: " + authenticate);

    }

}
