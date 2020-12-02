package com.shamy1st.soap.endpoints;

import com.shamy1st.courses.*;
import com.shamy1st.soap.entity.Course;
import com.shamy1st.soap.exception.CourseNotFoundException;
import com.shamy1st.soap.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Endpoint
public class CourseEndpoint {

    @Autowired
    private CourseService service;

    @PayloadRoot(namespace="http://shamy1st.com/courses", localPart="GetCourseRequest")
    @ResponsePayload
    public GetCourseResponse getCourse(@RequestPayload GetCourseRequest request) {
        Optional<Course> course = service.findById(request.getId());
        if(!course.isPresent())
            throw new CourseNotFoundException("Invalid Course ID: " + request.getId());
        GetCourseResponse response = new GetCourseResponse();
        response.setCourse(mapCourse(course.get()));
        return response;
    }

    private com.shamy1st.courses.Course mapCourse(Course course) {
        com.shamy1st.courses.Course courseResponse = new com.shamy1st.courses.Course();
        courseResponse.setId(course.getId());
        courseResponse.setName(course.getName());
        courseResponse.setDescription(course.getDescription());
        return courseResponse;
    }

    @PayloadRoot(namespace="http://shamy1st.com/courses", localPart="GetCoursesRequest")
    @ResponsePayload
    public GetCoursesResponse getCourses(@RequestPayload GetCoursesRequest request) {
        List<Course> courses = service.findAll();
        return mapCourses(courses);
    }

    private GetCoursesResponse mapCourses(List<Course> courses) {
        List<com.shamy1st.courses.Course> coursesResponse = new ArrayList<>();
        courses.forEach(course -> coursesResponse.add(mapCourse(course)));
        GetCoursesResponse response = new GetCoursesResponse();
        response.getCourse().addAll(coursesResponse);
        return response;
    }

    @PayloadRoot(namespace="http://shamy1st.com/courses", localPart="DeleteCourseRequest")
    @ResponsePayload
    public DeleteCourseResponse deleteCourse(@RequestPayload DeleteCourseRequest request) {
        DeleteCourseResponse response = new DeleteCourseResponse();
        response.setStatus(service.deleteById(request.getId()));
        return response;
    }
}