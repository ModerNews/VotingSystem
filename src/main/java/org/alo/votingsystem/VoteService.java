package org.alo.votingsystem;

public interface VoteService {
    public static Voter saveVote(DatabaseVote vote) {
        return new Voter(vote.id, vote.target);
    };
}
