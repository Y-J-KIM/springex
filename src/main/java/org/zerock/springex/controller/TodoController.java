package org.zerock.springex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.springex.dto.TodoDTO;
import org.zerock.springex.service.TodoService;

import javax.validation.Valid;

@Controller
@Log4j2
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    //localhost:8080/todo/list
    @RequestMapping("/list")
    public void list(Model model) {
        log.info("list");
        model.addAttribute("dtoList", todoService.getAll());
    }

    //localhost:8080/todo/register (get)
    @GetMapping("/register")
    public void registerGet() {

        log.info("register get method");
    }
    //유저가 할일 입력하고 submit 버튼 누르면 /todo/register post
    @PostMapping("/register")
    public String registerPost(@Valid TodoDTO todoDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("Post todo register...");

        if (bindingResult.hasErrors()) {
            log.info("has errors....");
            return "redirect:/todo/register"; //유효성 검증 실패시 다시 register(입력화면)로 돌아감
        }
        //검증 성공 DB에 새 할일 저장
        log.info("todoDTO" + todoDTO);
        todoService.register(todoDTO);

        return "redirect:/todo/list";

    }
}