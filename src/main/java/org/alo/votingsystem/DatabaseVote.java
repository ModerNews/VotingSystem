package org.alo.votingsystem;

import javax.xml.crypto.Data;

public class DatabaseVote{
    public long id;
    public String target;

    public DatabaseVote(long id, String target) {
        this.target = target;
        this.id = id;
    }

// TODO: Vote base class and endpoint
//    public DatabaseVote(long id, Vote vote) {
//        this.target = vote.target;
//        this.id = id;
//    }
}
