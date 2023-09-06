package org.alo.votingsystem;

import java.util.Random;
import java.util.stream.IntStream;

public class Voter {
    private long id;
    public String access_token;
    public Boolean voted;
    protected String target;
    public String group;

    public String generate_access_token(int targetStringLength){
        int leftlimit=48; // numeral '0'
        int rightlimit=122; // letter 'z'

        Random random=new Random();
        IntStream access_token= random.ints(leftlimit,rightlimit+1)
                .filter(i->(i<=57||i>=65)&&(i<=90||i>=97))  //exclude not url safe chars
                .limit(targetStringLength);
        return access_token.collect(StringBuilder::new,StringBuilder::appendCodePoint,StringBuilder::append).toString();
    }

    public String generate_access_token(){
        return generate_access_token(128);
    }

    public Voter(long id, String group) {
        this.id = id;
        this.group = group;
        this.access_token = generate_access_token();
        this.target = "";
        this.voted = false;
    }
}
