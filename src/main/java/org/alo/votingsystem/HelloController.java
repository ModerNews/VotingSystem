package org.alo.votingsystem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;


@RestController
public class HelloController {

    private AtomicLong counter = new AtomicLong();
    private AtomicLong vote_counter = new AtomicLong();

    @GetMapping("/")
    public String index(){
        return "Hello World";
    }

    @GetMapping("/user")
    public User user_info(@RequestParam(value="name", defaultValue="Gruzin") String name){
        return new User(counter.incrementAndGet(), name);
    }

    @PostMapping(value = "/group/add", consumes = "application/json", produces = "application/json")
    public ArrayList<Voter> send_vote(@RequestBody Group group){
        ArrayList<Voter> voters = new ArrayList<>();
        for(int i = 0; i < group.count; i++)
            voters.add(VoteService.saveVote(new DatabaseVote(vote_counter.incrementAndGet(), group.name)));
        return voters;
    }
}
