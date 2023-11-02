package org.alo.votingsystem.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.alo.votingsystem.*;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;


@RestController
public class VoterController {

    private AtomicLong counter = new AtomicLong();
    private AtomicLong vote_counter = new AtomicLong();

//    @GetMapping("/user")
//    public User user_info(@RequestParam(value="name", defaultValue="Gruzin") String name){
//        return new User(counter.incrementAndGet(), name);
//    }

    @PostMapping(value = "/group/add", consumes = "application/json", produces = "application/json")
    public ArrayList<Voter> send_vote(@RequestBody Group group){
        ArrayList<Voter> voters = new ArrayList<>();
        for(int i = 0; i < group.count; i++)
            voters.add(VoteService.saveVote(new DatabaseVote(vote_counter.incrementAndGet(), group.name)));
        return voters;
    }

//    @GetMapping("/registration")
//    public String showRegistrationForm(Model model){
//        model.addAttribute("user", new UserDto());
//        return "registration";
//    }
//
//    @PostMapping("/registration")
//    public ModelAndView registerUserAccount(
//            @ModelAttribute("user") @Valid UserDto userDto,
//            HttpServletRequest request,
//            Errors errors) {
//
////        try {
////            User registered = userService.registerNewUserAccount(userDto);
////        } catch (UserAlreadyExistException uaeEx) {
////            mav.addObject("message", "An account for that username/email already exists.");
////            return mav;
////        }
//
//        throw new UnsupportedOperationException();
//    }
}
