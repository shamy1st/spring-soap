package com.shamy1st.soap.service;

import com.shamy1st.courses.Status;
import com.shamy1st.soap.entity.Course;
import com.shamy1st.soap.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository repository;

    public Optional<Course> findById(Integer id) {
        return repository.findById(id);
    }

    public List<Course> findAll() {
        return repository.findAll();
    }

    public Status deleteById(Integer id) {
        Optional<Course> optional = findById(id);
        if(optional.isPresent()) {
            repository.deleteById(id);
            return Status.SUCCESS;
        }
        return Status.FAILURE;
    }
}