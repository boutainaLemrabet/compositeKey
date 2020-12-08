package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Task;
import com.mycompany.myapp.repository.TaskRepository;
import com.mycompany.myapp.service.TaskService;
import com.mycompany.myapp.service.dto.TaskDTO;
import com.mycompany.myapp.service.mapper.TaskMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Task}.
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        log.debug("Request to save Task : {}", taskDTO);
        Task task = taskMapper.toEntity(taskDTO);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Override
    public TaskDTO partialUpdate(TaskDTO taskDTO) {
        log.debug("Request to partially update Task : {}", taskDTO);

        return taskRepository
            .findById(taskDTO.getId())
            .map(
                existingTask -> {
                    if (taskDTO.getName() != null) {
                        existingTask.setName(taskDTO.getName());
                    }

                    if (taskDTO.getType() != null) {
                        existingTask.setType(taskDTO.getType());
                    }

                    if (taskDTO.getEndDate() != null) {
                        existingTask.setEndDate(taskDTO.getEndDate());
                    }

                    if (taskDTO.getCreatedAt() != null) {
                        existingTask.setCreatedAt(taskDTO.getCreatedAt());
                    }

                    if (taskDTO.getModifiedAt() != null) {
                        existingTask.setModifiedAt(taskDTO.getModifiedAt());
                    }

                    if (taskDTO.getDone() != null) {
                        existingTask.setDone(taskDTO.getDone());
                    }

                    if (taskDTO.getDescription() != null) {
                        existingTask.setDescription(taskDTO.getDescription());
                    }

                    if (taskDTO.getAttachment() != null) {
                        existingTask.setAttachment(taskDTO.getAttachment());
                    }
                    if (taskDTO.getAttachmentContentType() != null) {
                        existingTask.setAttachmentContentType(taskDTO.getAttachmentContentType());
                    }

                    if (taskDTO.getPicture() != null) {
                        existingTask.setPicture(taskDTO.getPicture());
                    }
                    if (taskDTO.getPictureContentType() != null) {
                        existingTask.setPictureContentType(taskDTO.getPictureContentType());
                    }

                    return existingTask;
                }
            )
            .map(taskRepository::save)
            .map(taskMapper::toDto)
            .get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> findAll() {
        log.debug("Request to get all Tasks");
        return taskRepository.findAll().stream().map(taskMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaskDTO> findOne(Long id) {
        log.debug("Request to get Task : {}", id);
        return taskRepository.findById(id).map(taskMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Task : {}", id);
        taskRepository.deleteById(id);
    }
}
