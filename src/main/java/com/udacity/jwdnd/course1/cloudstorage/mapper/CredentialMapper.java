package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userId=#{userId}")
    List<Credential> getAllCredentials(int userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Update("UPDATE CREDENTIALS set url = #{url}, username = #{username}, key = #{key}, password=#{password} WHERE credentialId = #{credentialId}")
    int updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId=#{credentialId}")
    void deleteCredentialById(int credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId=#{credentialId}")
    Credential getCredentialById(int credentialId);

}
