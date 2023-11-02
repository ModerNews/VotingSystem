package org.alo.votingsystem.models;

import java.util.Random;
import java.util.stream.IntStream;

public class Token {

    public String generateAccessToken(int targetStringLength){
        int leftlimit=48; // numeral '0'
        int rightlimit=122; // letter 'z'

        Random random=new Random();
        IntStream access_token= random.ints(leftlimit,rightlimit+1)
                .filter(i->(i<=57||i>=65)&&(i<=90||i>=97))  //exclude not url safe chars
                .limit(targetStringLength);
        return access_token.collect(StringBuilder::new,StringBuilder::appendCodePoint,StringBuilder::append).toString();
    }
}
