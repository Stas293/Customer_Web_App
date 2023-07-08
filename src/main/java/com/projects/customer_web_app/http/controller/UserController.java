package com.projects.customer_web_app.http.controller;

import com.projects.customer_web_app.dto.UserUpdateDto;
import com.projects.customer_web_app.dto.UserUpdatePasswordDto;
import com.projects.customer_web_app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/personal")
    public String personal(Model model) {
        model.addAttribute("authority", userService.getCurrentUser());
        return "personal";
    }

    @DeleteMapping("/image")
    public String deleteImage() {
        userService.deleteImage();
        return "redirect:/user/personal";
    }

    @GetMapping("/edit")
    public String edit(Model model) {
        model.addAttribute("account", userService.getUpdateUserDto());
        return "edit";
    }

    @PutMapping("/personal")
    public String update(@Valid @ModelAttribute("account") UserUpdateDto userUpdateDto,
                         BindingResult bindingResult,
                         Model model) {
        userService.update(userUpdateDto, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("Errors: {}", bindingResult.getAllErrors());
            model.addAttribute("account", userUpdateDto);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/user/edit";
        }
        return "redirect:/user/personal";
    }

    @GetMapping("/editPassword")
    public String editPassword(@ModelAttribute("dto") UserUpdatePasswordDto userUpdatePasswordDto) {
        return "editPassword";
    }

    @PutMapping("/editPassword")
    public String updatePassword(@Valid @ModelAttribute("dto") UserUpdatePasswordDto userUpdatePasswordDto,
                                 BindingResult bindingResult,
                                 Model model) {
        userService.updatePassword(userUpdatePasswordDto, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("Errors: {}", bindingResult.getAllErrors());
            model.addAttribute("dto", userUpdatePasswordDto);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "editPassword";
        }
        return "redirect:/user/personal";
    }
}
