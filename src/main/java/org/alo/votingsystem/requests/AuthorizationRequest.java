package org.alo.votingsystem.requests;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

public class AuthorizationRequest {

    @NotBlank
    public String response_type;

    @NotBlank
    public String client_id;

    @URL
    public String redirect_uri;

    public String scope;

    public String code_challenge;

    @NotBlank
    public String grant_type;

    @AssertTrue
    private boolean grant_type_params_matched() {
        if (grant_type.equals("pkce") && code_challenge != null){
            return code_challenge.trim().length() > 0;  // code_challenge @NotBlank
        }
        return false;
    }

    @AssertTrue
    private boolean response_type_correct() {
        return response_type.equals("code");
    }

    public String getResponse_type() {
        return response_type;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
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

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
