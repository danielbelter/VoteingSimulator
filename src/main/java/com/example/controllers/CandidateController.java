package com.example.controllers;

import com.example.exceptions.MyException;
import com.example.model.Gender;
import com.example.model.dto.CandidateDTO;
import com.example.model.dto.mappers.ModelMapper;
import com.example.services.interfaces.MyService;
import com.example.utils.FileManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/candidates")
public class CandidateController {
    private MyService service;
    private ModelMapper mapper;

    public CandidateController(MyService service) {
        this.service = service;
    }

    /**
     * add new candidate
     */
    @GetMapping("/add")
    public String candiadateAdd(Model model) {
        try {
            model.addAttribute("candidate", new CandidateDTO());
            model.addAttribute("genders", Gender.values());
            model.addAttribute("constituencies", service.getAllConstituencies());
            return "management/candidates/add";
        } catch (Exception e) {
            throw new MyException("Workers Controller, add get Exception", LocalDateTime.now());

        }
    }

    @PostMapping("/add")
    public String candidateAddPost(@ModelAttribute CandidateDTO candidateDTO, HttpServletRequest request) {
        try {
            String appPath = request.getSession().getServletContext().getRealPath("/");
            String filename = FileManager.addResource(candidateDTO.getMultipartFile(), appPath);
            candidateDTO.setFilename(filename);
            service.addCandidate(candidateDTO);
            return "redirect:/candidates";

        } catch (Exception e) {
            throw new MyException("Workers CONT, ADD POST EXC", LocalDateTime.now());
        }
    }

    @GetMapping
    public String getAllCandaidates(Model model) {
        try {
            model.addAttribute("candidates", service.getAllCandidates());
            return "management/candidates/all";
        } catch (Exception e) {
            throw new MyException("CANDIDATE CONTROLLER, GET ALL EXCEPTION", LocalDateTime.now());
        }
    }

    @GetMapping("/edit/{id}")
    public String candidateEdit(Model model, @PathVariable Long id) {
        try {
            model.addAttribute("candidate", service.findByIdCandidate(id));
            model.addAttribute("genders", Gender.values());
            model.addAttribute("constituencies", service.getAllConstituencies());
            return "management/candidates/edit";
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("candidate controller, edit get exception", LocalDateTime.now());
        }
    }

    @PostMapping("/edit")
    public String candidateEditPost(@ModelAttribute CandidateDTO candidateDTO, HttpServletRequest request) {
        try {
            String appPath = request.getSession().getServletContext().getRealPath("/");
            String filename = FileManager.updateResource(candidateDTO.getMultipartFile(), appPath, candidateDTO.getFilename());
            candidateDTO.setFilename(filename);
            service.editCandidate(candidateDTO);
            return "redirect:/candidates";
        } catch (Exception e) {
            throw new MyException("WORKERS CONTROLLER, EDIT POST EXCEPTION", LocalDateTime.now());
        }
    }


}
