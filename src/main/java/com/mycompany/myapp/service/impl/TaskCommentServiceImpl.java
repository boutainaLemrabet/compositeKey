package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.TaskComment;
import com.mycompany.myapp.repository.TaskCommentRepository;
import com.mycompany.myapp.service.TaskCommentService;
import com.mycompany.myapp.service.dto.TaskCommentDTO;
import com.mycompany.myapp.service.mapper.TaskCommentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaskComment}.
 */
@Service
@Transactional
public class TaskCommentServiceImpl implements TaskCommentService {

    private final Logger log = LoggerFactory.getLogger(TaskCommentServiceImpl.class);

    private final TaskCommentRepository taskCommentRepository;

    private final TaskCommentMapper taskCommentMapper;

    public TaskCommentServiceImpl(TaskCommentRepository taskCommentRepository, TaskCommentMapper taskCommentMapper) {
        this.taskCommentRepository = taskCommentRepository;
        this.taskCommentMapper = taskCommentMapper;
    }

    @Override
    public TaskCommentDTO save(TaskCommentDTO taskCommentDTO) {
        log.debug("Request to save TaskComment : {}", taskCommentDTO);
        TaskComment taskComment = taskCommentMapper.toEntity(taskCommentDTO);
        taskComment = taskCommentRepository.save(taskComment);
        return taskCommentMapper.toDto(taskComment);
    }

    @Override
    public TaskCommentDTO partialUpdate(TaskCommentDTO taskCommentDTO) {
        log.debug("Request to partially update TaskComment : {}", taskCommentDTO);

        return taskCommentRepository
            .findById(taskCommentDTO.getId())
            .map(
                existingTaskComment -> {
                    if (taskCommentDTO.getValue() != null) {
                        existingTaskComment.setValue(taskCommentDTO.getValue());
                    }

                    return existingTaskComment;
                }
            )
            .map(taskCommentRepository::save)
            .map(taskCommentMapper::toDto)
            .get();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskCommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskComments");
        return taskCommentRepository.findAll(pageable).map(taskCommentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaskCommentDTO> findOne(Long id) {
        log.debug("Request to get TaskComment : {}", id);
        return taskCommentRepository.findById(id).map(taskCommentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaskComment : {}", id);
        taskCommentRepository.deleteById(id);
    }
}
