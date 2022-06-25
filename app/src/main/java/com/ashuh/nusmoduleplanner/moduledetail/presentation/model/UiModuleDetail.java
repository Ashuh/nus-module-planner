package com.ashuh.nusmoduleplanner.moduledetail.presentation.model;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.post.Post;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UiModuleDetail {
    @NonNull
    private final String moduleCode;
    @NonNull
    private final String title;
    @NonNull
    private final String moduleCredit;
    @NonNull
    private final String department;
    @NonNull
    private final String faculty;
    @NonNull
    private final String description;
    @NonNull
    private final String prerequisite;
    @NonNull
    private final String coRequisite;
    @NonNull
    private final String preclusion;
    @NonNull
    private final Map<String, UiExam> semesterToExam;
    @NonNull
    private final List<String> semestersOffered;
    @NonNull
    private final List<UiPost> posts;

    public UiModuleDetail(@NonNull String moduleCode, @NonNull String title,
                          @NonNull String moduleCredit, @NonNull String department,
                          @NonNull String faculty, @NonNull String description,
                          @NonNull String prerequisite, @NonNull String coRequisite,
                          @NonNull String preclusion, @NonNull Map<String, UiExam> semesterToExam,
                          @NonNull List<String> semestersOffered, @NonNull List<UiPost> posts) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.moduleCredit = moduleCredit;
        this.department = department;
        this.faculty = faculty;
        this.description = description;
        this.prerequisite = prerequisite;
        this.coRequisite = coRequisite;
        this.preclusion = preclusion;
        this.semesterToExam = semesterToExam;
        this.semestersOffered = semestersOffered;
        this.posts = posts;
    }

    @NonNull
    public String getModuleCode() {
        return moduleCode;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getModuleCredit() {
        return moduleCredit;
    }

    @NonNull
    public String getDepartment() {
        return department;
    }

    @NonNull
    public String getFaculty() {
        return faculty;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public String getPrerequisite() {
        return prerequisite;
    }

    @NonNull
    public String getCoRequisite() {
        return coRequisite;
    }

    @NonNull
    public String getPreclusion() {
        return preclusion;
    }

    @NonNull
    public Map<String, UiExam> getExams() {
        return semesterToExam;
    }

    @NonNull
    public List<String> getSemestersOffered() {
        return semestersOffered;
    }

    @NonNull
    public List<UiPost> getPosts() {
        return posts;
    }

    public static class Builder {
        private String moduleCode = "";
        private String title = "";
        private String moduleCredit = "";
        private String department = "";
        private String faculty = "";
        private String description = "";
        private String prerequisite = "";
        private String coRequisite = "";
        private String preclusion = "";
        private Map<String, UiExam> semesterToExam = Collections.emptyMap();
        private List<String> semestersOffered = Collections.emptyList();
        private List<UiPost> posts = Collections.emptyList();

        public void withModule(Module module) {
            this.moduleCode = module.getModuleCode();
            this.title = module.getTitle();
            this.moduleCredit = module.getModuleCredit().toString();
            this.department = module.getDepartment();
            this.faculty = module.getFaculty();
            this.description = module.getDescription();
            this.prerequisite = module.getPrerequisite();
            this.coRequisite = module.getCoRequisite();
            this.preclusion = module.getPreclusion();
            this.semesterToExam = module.getExams().entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            e -> UiExam.fromDomain(e.getValue()),
                            (x, y) -> y,
                            LinkedHashMap::new)
                    );
            this.semestersOffered = module.getSemesters().stream()
                    .sorted()
                    .map(Semester::toString)
                    .collect(Collectors.toList());
        }

        public void withPosts(List<Post> posts, int primaryColor) {
            this.posts = posts.stream()
                    .map(post -> UiPost.fromDomain(post, primaryColor))
                    .collect(Collectors.toList());
        }

        public UiModuleDetail build() {
            return new UiModuleDetail(moduleCode, title, moduleCredit, department, faculty,
                    description, prerequisite, coRequisite, preclusion, semesterToExam,
                    semestersOffered, posts);
        }
    }
}
