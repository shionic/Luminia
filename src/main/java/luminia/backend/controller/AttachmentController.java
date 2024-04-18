package luminia.backend.controller;

import lombok.AllArgsConstructor;
import luminia.backend.dto.AttachmentDto;
import luminia.backend.exceptions.IllegalArgumentException;
import luminia.backend.models.Attachment;
import luminia.backend.services.AttachmentService;
import luminia.backend.services.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/attachment/")
public class AttachmentController {
    private AttachmentService service;
    private UserService userService;

    @PostMapping("/upload")
    public AttachmentDto upload(@RequestParam("file") MultipartFile file) {
        var usr = userService.getUser();
        Attachment attachment;
        try {
            attachment = service.create(usr.getEntity(), file.getOriginalFilename(), file.getContentType(), file.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to upload file");
        }
        return service.toDto(attachment);
    }
}
