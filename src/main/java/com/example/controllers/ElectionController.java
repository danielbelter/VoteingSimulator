package com.example.controllers;

import com.example.exceptions.MyException;
import com.example.model.dto.CandidateDTO;
import com.example.model.dto.VoterDTO;
import com.example.services.interfaces.MyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/elections")
public class ElectionController {
    private MyService service;

    public ElectionController(MyService service) {
        this.service = service;
    }

    @GetMapping("/vote/{n}")
    public String vote(@PathVariable Long n, Model model) {
        VoterDTO voterDTO = service.getNthVoter(n);
        model.addAttribute("voter", voterDTO);
        model.addAttribute("candidates", service.canditatesForVoter(voterDTO.getId()));
        model.addAttribute("voterNumber", n);

        return "elections/vote";
    }

    @PostMapping("/vote")
    public String votePost(@RequestParam Long candidateId, @RequestParam Long voterNumber) {


        int voterNumberInt = voterNumber.intValue();
        System.out.println("A = " + service.getAllVoters().size());
        System.out.println("B = " + voterNumberInt);
        CandidateDTO candidateDTO = service.addVoteForCandidate(candidateId);
        service.editCandidate(candidateDTO);

        if (service.getAllVoters().size() == voterNumberInt) {
            System.out.println("!!!!");
            return "redirect:/elections/results";
        }

        return "redirect:/elections/vote/" + (voterNumber + 1);
    }

    @GetMapping("/results")
    public String electionResults(Model  model) {
        try {
            model.addAttribute("candidates", service.electionResult());
            model.addAttribute("constituencies", service.getAllConstituencies());
            return "elections/results";
        } catch (Exception e) {
            throw new MyException("Election Controller, Exception", LocalDateTime.now());
        }
    }

    @GetMapping("/allresults/{id}")
    public String electionAllResults(@PathVariable  Long id, Model model) {
        model.addAttribute("candidates", service.electionResultForConstituency(id));
        return "elections/allresults";
    }
}
