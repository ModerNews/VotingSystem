package org.alo.votingsystem.models;

import org.alo.votingsystem.requests.AuthorizationRequest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AuthGrant {

    private String code;
    private String state;
    private String client_id;
    private String redirect_uri;
    private String scope;
    private String code_challenge;

    public AuthGrant(String code, String state, String client_id, String redirect_uri, String scope, String code_challenge) {
        this.code = code;
        this.state = state;
        this.client_id = client_id;
        this.redirect_uri = redirect_uri;
        this.scope = scope;
        this.code_challenge = code_challenge;
    }

    public AuthGrant(String code, AuthorizationRequest authRequest) {
        this.code = code;
        this.state = authRequest.getState();
        this.client_id = authRequest.getClient_id();
        this.redirect_uri = authRequest.getRedirect_uri();
        this.scope = authRequest.getScope();
        this.code_challenge = authRequest.getCode_challenge();
    }

    public boolean verifyCodeVerifier(String code_verifier) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return Base64.getEncoder().encodeToString(digest.digest(code_verifier.getBytes())).equals(this.getCode_challenge());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getCode_challenge() {
        return code_challenge;
    }

    public void setCode_challenge(String code_challenge) {
        this.code_challenge = code_challenge;
    }
}
