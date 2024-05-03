package org.zerock.springex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.springex.dto.PageRequestDTO;
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
//    @RequestMapping("/list")
//    public void list(Model model) {
//        log.info("list");
//
//        model.addAttribute("dtoList", todoService.getAll());
//    }

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

    @GetMapping({"/read", "/modify"} )
    public void read(Long tno,PageRequestDTO pageRequestDTO, Model model){
        TodoDTO todoDTO = todoService.getOne(tno);
        log.info("todoDTO: " + todoDTO);

        model.addAttribute("dto", todoDTO);
    }

    @PostMapping("/remove")
    public String remove(Long tno,PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes) {
        log.info("-----------------remove---------------");
        log.info("tno: " + tno);

        todoService.remove(tno); //삭제

        return "redirect:/todo/list" + pageRequestDTO.getLink();
    }

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO,
                         @Valid TodoDTO todoDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.info("수정 내용이 형식에 맞지 않음");
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
            redirectAttributes.addAttribute("tno",todoDTO.getTno());
            return "redirect:/todo/modify";
        }
        log.info("todoDTO: " + todoDTO);
        todoService.modify(todoDTO);    //수정하기

        redirectAttributes.addAttribute("tno",todoDTO.getTno());

        return "redirect:/todo/read";
    }

    @GetMapping("/list")
    public void list(@Valid PageRequestDTO pageRequestDTO,
                     BindingResult bindingResult, Model model){
        log.info(pageRequestDTO);

        if (bindingResult.hasErrors()) {
            pageRequestDTO = PageRequestDTO.builder().build(); //기본세팅 1페이지 화면당 10개
        }
        model.addAttribute("responseDTO", todoService.getList(pageRequestDTO));
    }
}
