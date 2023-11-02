package org.alo.votingsystem;

import org.alo.votingsystem.models.Token;

public class Voter {
    private long id;
    public String access_token;
    public Boolean voted;
    protected String target;
    public String group;

    public String generate_access_token(){
        Token token = new Token();
        return token.generateAccessToken(128);
    }

    public Voter(long id, String group) {
        this.id = id;
        this.group = group;
        this.access_token = generate_access_token();
        this.target = "";
        this.voted = false;
    }
}
