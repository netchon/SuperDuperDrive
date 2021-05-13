package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllCredentialsByUserId(int userId){
        return credentialMapper.getAllCredentials(userId);
    }

    public int saveCredential(Credential credential){
        String generatedKey = encryptionService.generateKey();
        String encryptedPassword = (encryptionService.encryptValue(credential.getPassword(), generatedKey));
        credential.setPassword(encryptedPassword);
        credential.setKey(generatedKey);
        return credentialMapper.insertCredential(credential);
    }

    public int updateCredential(Credential credential){
        String generatedKey = encryptionService.generateKey();
        String encryptedPassword = (encryptionService.encryptValue(credential.getPassword(), generatedKey));
        credential.setPassword(encryptedPassword);
        credential.setKey(generatedKey);
        return credentialMapper.updateCredential(credential);
    }

    public void deleteCredentialById(int credentialId){
        credentialMapper.deleteCredentialById(credentialId);
    }

    public Credential getCredentialById(int credentialId){
        return credentialMapper.getCredentialById(credentialId);
    }

    public String decryptedData(int credentialId){
        Credential credential = getCredentialById(credentialId);
        return encryptionService.decryptValue(credential.getPassword(), credential.getKey());
    }


}
