package com.example.controllers;

import com.example.exceptions.MyException;
import com.example.model.Gender;
import com.example.model.dto.VoterDTO;
import com.example.repository.interfaces.VoterRepository;
import com.example.services.interfaces.MyService;
import com.example.utils.FileManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/voters")
public class VoterController {
    private MyService myService;
    public VoterRepository voterRepository;

    public VoterController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/add")
    public String voterAdd(Model model) {
        try {
            model.addAttribute("voter", new VoterDTO());
            model.addAttribute("genders", Gender.values());
            model.addAttribute("constituencies", myService.getAllConstituencies());
            return "management/voters/add";
        } catch (Exception e) {
            throw new MyException("Voter Controller, add GET Exception", LocalDateTime.now());
        }
    }

    @PostMapping("/add")
    public String voterAddPost(@ModelAttribute VoterDTO voterDTO, HttpServletRequest request) {
        try {
            String appPath = request.getSession().getServletContext().getRealPath("/");
            String filename = FileManager.addResource(voterDTO.getMultipartFile(), appPath);
            voterDTO.setFilename(filename);
            myService.addVoter(voterDTO);
            return "redirect:/voters";
        } catch (Exception e) {
            throw new MyException("Voter Controller, add POST Exception", LocalDateTime.now());
        }
    }

    @GetMapping
    public String getAllVoters(Model model) {
        try {
            model.addAttribute("voters", myService.getAllVoters());
            return "management/voters/all";
        } catch (Exception e) {
            throw new MyException("VOTER CONTROLLER, GET ALL EXCEPTION", LocalDateTime.now());
        }
    }

    @GetMapping("/edit/{id}")
    public String voterEdit(Model model, @PathVariable Long id) {
        try {
            model.addAttribute("voters", voterRepository.findById(id));
            model.addAttribute("genders", Gender.values());
            model.addAttribute("constituencies", myService.getAllConstituencies());
        } catch (Exception e) {
            throw new MyException("WORKERS CONTROLLER, EDIT GET EXCEPTION", LocalDateTime.now());
        }
        return "management/voters/edit";
    }

    @PostMapping("/edit")
    public String voterPostEdit(@ModelAttribute VoterDTO voterDTO, HttpServletRequest request) {
        String appPath = request.getSession().getServletContext().getRealPath("/");
        String filename = FileManager.updateResource(voterDTO.getMultipartFile(), appPath, voterDTO.getFilename());
        voterDTO.setFilename(filename);
        myService.editVoter(voterDTO);

        return "redirect:/voters";
    }

}

