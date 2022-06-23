package com.ashuh.nusmoduleplanner.moduledetail.presentation.model;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Exam;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleCredit;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.post.Post;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UiModuleDetail {
    @NonNull
    private final String moduleCode;
    @NonNull
    private final String title;
    @NonNull
    private final ModuleCredit moduleCredit;
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
    private final Map<Semester, Exam> semesterToExam;
    @NonNull
    private final Set<Semester> semestersOffered;
    @NonNull
    private final List<Post> posts;

    public UiModuleDetail(@NonNull String moduleCode, @NonNull String title,
                          @NonNull ModuleCredit moduleCredit, @NonNull String department,
                          @NonNull String faculty, @NonNull String description,
                          @NonNull String prerequisite, @NonNull String coRequisite,
                          @NonNull String preclusion, @NonNull Map<Semester, Exam> semesterToExam,
                          @NonNull Set<Semester> semestersOffered, @NonNull List<Post> posts) {
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

    private static UiModuleDetail fromDomain(Module module) {
        String moduleCode = module.getModuleCode();
        String title = module.getTitle();
        ModuleCredit moduleCredit = module.getModuleCredit();
        String department = module.getDepartment();
        String faculty = module.getFaculty();
        String description = module.getDescription();
        String prerequisite = module.getPrerequisite();
        String coRequisite = module.getCoRequisite();
        String preclusion = module.getPreclusion();
        Map<Semester, Exam> semesterToExam = module.getExams();
        Set<Semester> semestersOffered = module.getSemesters();
        List<Post> posts = Collections.emptyList();
        return new UiModuleDetail(moduleCode, title, moduleCredit, department, faculty, description,
                prerequisite, coRequisite, preclusion, semesterToExam, semestersOffered, posts);
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
    public ModuleCredit getModuleCredit() {
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
    public Map<Semester, Exam> getExams() {
        return semesterToExam;
    }

    @NonNull
    public Set<Semester> getSemestersOffered() {
        return semestersOffered;
    }

    @NonNull
    public List<Post> getPosts() {
        return posts;
    }

    public static class Builder {
        private String moduleCode = "";
        private String title = "";
        private ModuleCredit moduleCredit = new ModuleCredit(0);
        private String department = "";
        private String faculty = "";
        private String description = "";
        private String prerequisite = "";
        private String coRequisite = "";
        private String preclusion = "";
        private Map<Semester, Exam> semesterToExam = Collections.emptyMap();
        private Set<Semester> semestersOffered = Collections.emptySet();
        private List<Post> posts = Collections.emptyList();

        public void withModule(Module module) {
            this.moduleCode = module.getModuleCode();
            this.title = module.getTitle();
            this.moduleCredit = module.getModuleCredit();
            this.department = module.getDepartment();
            this.faculty = module.getFaculty();
            this.description = module.getDescription();
            this.prerequisite = module.getPrerequisite();
            this.coRequisite = module.getCoRequisite();
            this.preclusion = module.getPreclusion();
            this.semesterToExam = module.getExams();
            this.semestersOffered = module.getSemesters();
        }

        public void withPosts(List<Post> posts) {
            this.posts = posts;
        }

        public UiModuleDetail build() {
            return new UiModuleDetail(moduleCode, title, moduleCredit, department, faculty,
                    description, prerequisite, coRequisite, preclusion, semesterToExam,
                    semestersOffered, posts);
        }
    }
}
