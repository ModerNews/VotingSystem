package org.alo.votingsystem.requests;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.*;

public class AuthorizationRequest {

    @NotBlank(message="unsupported_response_type;response_type must be set to code")
    public String response_type;

    @NotBlank(message="server;client_id is required")
    public String client_id;

    @NotBlank(message="server;redirect_uri is required")
    @URL(message = "server;redirect_uri must be a valid URL")
    public String redirect_uri;

    public String scope;

    public String code_challenge;

    @NotBlank(message="invalid_request;grant_type must be set to pkce")
    public String grant_type;

    @NotBlank(message = "invalid_request;This is required to prevent CSRF attacks. Read more: https://tools.ietf.org/html/rfc6749#section-10.12")
    public String state;

    @AssertTrue
    private boolean grant_type_params_matched() {
        // Further cases may be added here
        switch (grant_type.trim().toLowerCase()) {
            case "pkce":
                if ((code_challenge == null) || (code_challenge.trim().length() == 0)) {
                    throw new IllegalArgumentException("invalid_request;" + String.format("%s is not a valid code_challenge", code_challenge));
                }
                break;
            default:
                throw new IllegalArgumentException("invalid_request;" + String.format("%s is not a valid grant_type", grant_type));
        }
        return true;
    }

    @AssertTrue(message="unsupported_response_type;response_type must be set to code")
    private boolean responseTypeCorrect() {
        if (response_type == null) {
            return false;
        }
        return response_type.equals("code");
    }

    @AssertTrue(message="server;client_id is not valid id")
    private boolean clientIdValid() {
        // TODO: implement client_id repository
        return client_id.equals("voting-system");
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean checkPredicates() {
        if (client_id == null || client_id.trim().length() == 0) {
            throw new IllegalArgumentException("server;client_id is required");
        }
        Assert.isTrue(clientIdValid(), "server;client_id is not valid id");
        // Validate using builtin patters from UriComponentsBuilder
        try {
            UriComponentsBuilder.fromUriString(redirect_uri.trim());
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException(String.format("server;%s is not a valid URL", redirect_uri));
        }
        return true;
    }

    public Boolean isValid() {
        boolean predicatesValid = checkPredicates();
        Assert.isTrue(responseTypeCorrect(), "unsupported_response_type;response_type must be set to code");


        if ((grant_type == null ) || (grant_type.trim().length() == 0)) {
            throw new IllegalArgumentException("invalid_request;" + String.format("%s is not a valid grant_type", grant_type));

        } else if (!grant_type_params_matched()){
            return null; // This will throw an exception in the predicate
        }

        if ((state == null) || (state.trim().length() == 0)) {
            throw new IllegalArgumentException("invalid_request;" + String.format("%s is not a valid state. State parameter is required to prevent CSRF attacks. Read more: https://tools.ietf.org/html/rfc6749#section-10.12", state));
        }
//        if ((scope == null) || (scope.trim().length() == 0)) {
//            error = true;
//            uriComponentsBuilder.queryParam("error", "invalid_request");
//            uriComponentsBuilder.queryParam("error_description", String.format("%s is not a valid scope", scope));
//        }
        return true;
    }
}
