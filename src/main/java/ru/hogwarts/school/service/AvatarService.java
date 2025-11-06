package ru.hogwarts.school.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.slf4j.*;

import static io.swagger.v3.core.util.AnnotationsUtils.getExtensions;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {
    private AvatarRepository avatarRepository;
    private StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    @Autowired
    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    public void uploadAvatar(Long studID, MultipartFile avatarFile) throws IOException {
        logger.info("скачивание аватара запустилось");
        Student student = studentRepository.getReferenceById(studID);
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream in = avatarFile.getInputStream();
                OutputStream out = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bufIn = new BufferedInputStream(in, 1024);
                BufferedOutputStream bufOut = new BufferedOutputStream(out, 1024);
        ) {
            bufIn.transferTo(bufOut);
        }
        Avatar avatar = new Avatar();
        student.setAvatar(avatar);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setData(avatarFile.getBytes());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatarRepository.save(avatar);
        studentRepository.save(student);
    }

    private String getExtensions(String fileName) {
        logger.info("метод getExtensions запустился");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Avatar getAvatarById(Long id) {
        logger.info("метод getAvatarById запустился");
        return avatarRepository.getReferenceById(id);
    }

    public Page<Avatar> getAllPaged (Integer pageNumber, Integer pageSize){
        logger.info("метод getAllPaged запустился");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1,pageSize);
        return avatarRepository.findAll(pageRequest);
    }
}
